package com.sx.frontend.protocal376_1.internal;

import com.sx.frontend.protocal376_1.BinPacket;
import com.sx.frontend.protocal376_1.CodecConfig;
import com.sx.frontend.protocal376_1.Packet;
import com.sx.frontend.protocal376_1.exception.Preconditions;
import com.sx.frontend.protocal376_1.internal.ConfigParse.Constants;
import com.sx.frontend.protocal376_1.internal.packetSegment.*;
import com.sx.frontend.protocal376_1.internal.wrap.*;

import java.nio.ByteBuffer;
import java.util.Iterator;

/**
 * Created by PETER on 2015/3/14.
 */
public class Encoder implements Constants{
    private PacketSegmentContext packetSegmentContext=new PacketSegmentContext();
    private ProtocalTemplate protocalTemplate;
    private CodecConfig codecConfig;
    //编码链
    private WrapperChain wrapperChain=new WrapperChain();
    public Encoder(ProtocalTemplate protocalTemplate, CodecConfig codecConfig){
        this.protocalTemplate=protocalTemplate;
        this.codecConfig=codecConfig;
        //定义编码顺序
        wrapperChain.add(new ControlWrapper());
        wrapperChain.add(new DataWrapper());
        wrapperChain.add(new CipherWrapper());
        wrapperChain.add(new AuxWrapper());
        wrapperChain.add(new HeadTailWrapper());
    }

    public int encode(Packet in,BinPacket out,int pfc) throws Exception{
        Preconditions.checkNotNull(in.getCommand(),1111, "命令号不能为空");
        Preconditions.checkNotNull(in.getData(),1111,"数据体不能为空");
        //参数设置
        Head head=(Head)packetSegmentContext.getSegment(SegmentEnum.head);
        //规约版本设置
        head.setVersion(codecConfig.getProtocalVersion());
        Auxiliary auxiliary=(Auxiliary)packetSegmentContext.getSegment(SegmentEnum.auxiliary);
        Control control=(Control)packetSegmentContext.getSegment(SegmentEnum.control);
        //时间标识编码
        if(codecConfig.isHaveTp()){
            //超时设置
            auxiliary.setTimeout(codecConfig.getTimeout());
            //帧序号设置
            auxiliary.setPfc(pfc);
        }
        //帧序号设置
        control.setSeq(pfc);

        //编码操作
        wrapperChain.encode(in, packetSegmentContext, protocalTemplate,codecConfig);
        int length=head.getLength()+8;
        ByteBuffer byteBuffer=ByteBuffer.allocate(length);

        //遍历获取编码结果
        Iterator<Segment> segmentIterator=packetSegmentContext.getIterator();
        while (segmentIterator.hasNext()){
            Segment segment=segmentIterator.next();
            for(byte[] bs:segment.getBuffer()){
                byteBuffer.put(bs);
            }
        }
        out.setByteBuffer(byteBuffer);

        //返回控制信息
        int result=1;
        if(control.getIsNeedConfirm()==1){
            result=2;
        }

        //清空编码结果
        packetSegmentContext.reset();
        return result;
    }
}
