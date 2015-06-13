package com.sx.frontend.protocal376_1.internal;


import com.sx.frontend.protocal376_1.BinPacket;
import com.sx.frontend.protocal376_1.CodecConfig;
import com.sx.frontend.protocal376_1.IProtocal;
import com.sx.frontend.protocal376_1.Packet;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by PETER on 2015/2/5.
 */
public class ProtocalImpl implements IProtocal {
//    private final static Logger logger = LoggerFactory
//            .getLogger(ProtocalImpl.class);
    private Decoder decoder;
    private Encoder encoder;
    private Inquire inquire;
    private CodecConfig codecConfig;

    private static final int SEQ_NOT_MATCH=2;
    private static final int TIMEOUT_REACH=4;
    private static final int HAVE_NEXT_FRAME=8;

    //0-idle,1-waitResponse
    private int protocalStatus=0;
    private String lastEncodedTerminalAddress;
    //上次编码的返回值
    private int lastReturn=1;
    //帧序号记录器
    private int pfc=0;

    public ProtocalImpl(ProtocalTemplate protocalTemplate, CodecConfig codecConfig){
        this.codecConfig=codecConfig;
        inquire=new Inquire(protocalTemplate);
        decoder=new Decoder(protocalTemplate,codecConfig);
        encoder=new Encoder(protocalTemplate,codecConfig);
    }

    public ProtocalImpl(ProtocalTemplate protocalTemplate){
        this(protocalTemplate,(CodecConfig)protocalTemplate.getCodecConfig().clone());
    }

    @Override
    public CodecConfig getCodecConfig() {
        return codecConfig;
    }

    @Override
    public synchronized int encode(Packet in,BinPacket out) throws Exception{
        //流程控制
        if(codecConfig.isProcessControl()) {
            if (protocalStatus == 0) {
                pfc++;
                lastReturn = encoder.encode(in, out, pfc);
                protocalStatus = 1;
                if (in.getTerminalAddress() == null) {
                    lastEncodedTerminalAddress = codecConfig.getTerminalAddress();
                } else {
                    lastEncodedTerminalAddress = in.getTerminalAddress();
                }
            }
            return lastReturn;
        }else {
            pfc++;
            return encoder.encode(in, out, pfc);
        }

    }

    @Override
    public synchronized int decode(ByteBuffer in, Packet out) throws Exception {
        int result=decoder.decode(in,out,pfc);
        //帧序号不匹配。pfc重置
        if((SEQ_NOT_MATCH&result)==SEQ_NOT_MATCH
                || (TIMEOUT_REACH&result)==TIMEOUT_REACH){
            pfc=0;
        }

        //非同一终端，无返回数据
        if(lastEncodedTerminalAddress!=null &&
                !lastEncodedTerminalAddress.equals(out.getTerminalAddress())){
            out.setCommand("");
            out.setData(new HashMap<String, Object>());
        }

        //收到回复帧且是最后一帧，编码打开
        if((HAVE_NEXT_FRAME&result)!=HAVE_NEXT_FRAME){
            protocalStatus=0;
        }else{
            //多帧,帧序号递增
            pfc++;
        }

        return result;
    }



    @Override
    public String getFieldRemark(String command, String field) {
        return inquire.getFieldRemark(command,field);
    }

    @Override
    public String getFieldUnit(String command, String field) {
        return inquire.getFieldUnit(command,field);
    }

    @Override
    public String getValueDescription(String command, String field, Integer value) {
        return inquire.getValueDescription(command, field, value);
    }

    @Override
    public Map<Integer, String> getValueDescriptionList(String command, String field) {
        return inquire.getValueDescriptionList(command,field);
    }

    @Override
    public List<String> getDataList() {
        return inquire.getDataList();
    }

    @Override
    public List<String> getEventList() {
        return inquire.getEventList();
    }

    @Override
    public String getDataTemplate(String command) {
        return inquire.getDataTemplate(command);
    }
}
