package com.sx.frontend.protocal376_1.internal;

import com.sx.frontend.protocal376_1.CodecConfig;
import com.sx.frontend.protocal376_1.Packet;
import com.sx.frontend.protocal376_1.internal.ConfigParse.Constants;
import com.sx.frontend.protocal376_1.internal.packetSegment.*;
import com.sx.frontend.protocal376_1.internal.unwrap.*;

import java.nio.ByteBuffer;
import java.util.Iterator;

/**
 * Created by PETER on 2015/3/14.
 */
public class Decoder implements Constants{
    private PacketSegmentContext packetSegmentContext=new PacketSegmentContext();
    private ProtocalTemplate protocalTemplate;
    private CodecConfig codecConfig;
    private static final int SEQ_NOT_MATCH=2;
    private static final int TIMEOUT_REACH=4;
    private static final int HAVE_NEXT_FRAME=8;
    private static final int NEED_CONFIRM=16;
    private static final int INITIATIVE_UPLOAD=32;
    private static final int HAVE_EVENT=64;
    private static final int BROADCAST=128;
    //解码链
    private UnwrapperChain unwrapperChain=new UnwrapperChain();
    public Decoder(ProtocalTemplate protocalTemplate, CodecConfig codecConfig){
        this.protocalTemplate=protocalTemplate;
        this.codecConfig=codecConfig;
        //定义解码顺序
        unwrapperChain.add(new HeadTailUnwrapper());
        unwrapperChain.add(new ControlUnwrapper());
        unwrapperChain.add(new CipherUnwrapper());
        unwrapperChain.add(new DataUnwrapper());
        unwrapperChain.add(new AuxUnwrapper());

    }

    public int decode(final ByteBuffer in,Packet out,int pfc) throws Exception{
        unwrapperChain.decode(in,packetSegmentContext,protocalTemplate,codecConfig);
        int result=1;
        Control control=(Control)packetSegmentContext.getSegment(SegmentEnum.control);
        Auxiliary auxiliary=(Auxiliary)packetSegmentContext.getSegment(SegmentEnum.auxiliary);
        Data data=(Data)packetSegmentContext.getSegment(SegmentEnum.data);
        //设置解码结果
        out.setCommand(data.getCommand());
        out.setLine(data.getPn());
        out.setData(data.getData());
        out.setTerminalAddress(String.format("%s%s",control.getAddress1(),control.getAddress2()).toUpperCase());

        //设置返回值
        if(control.getPrm()==1 && control.getDir()==1){
            result+=INITIATIVE_UPLOAD;
            if(control.getIsNeedConfirm()==1){
                result+=NEED_CONFIRM;
            }
        }else{
            if(control.getSeq()!=(pfc&0x0f)){
                result+=SEQ_NOT_MATCH;
            }
        }




        if(control.getTpV()==1){
            if(System.currentTimeMillis()-auxiliary.getSendTime()>auxiliary.getTimeout()*60000){
                result+=TIMEOUT_REACH;
            }
        }

        if(control.getFir()==0){
            result+=HAVE_NEXT_FRAME;
        }


        if(control.getFcbOrAcd()==1){
            result+=HAVE_EVENT;
        }

        if(control.getIsGroup()==1){
            result+=BROADCAST;
        }
        //清空编码结果
        packetSegmentContext.reset();
        return result;
    }
}
