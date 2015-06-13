package com.sx.frontend.protocal376_1.internal.unwrap;

import com.sx.frontend.protocal376_1.CodecConfig;
import com.sx.frontend.protocal376_1.internal.ConfigParse.FieldTypeParam;
import com.sx.frontend.protocal376_1.internal.ProtocalTemplate;
import com.sx.frontend.protocal376_1.internal.fieldType.Da;
import com.sx.frontend.protocal376_1.internal.fieldType.Dt;
import com.sx.frontend.protocal376_1.internal.fieldType.HexString;
import com.sx.frontend.protocal376_1.internal.packetSegment.Control;
import com.sx.frontend.protocal376_1.internal.packetSegment.PacketSegmentContext;
import com.sx.frontend.protocal376_1.internal.packetSegment.SegmentEnum;

import java.nio.ByteBuffer;

/**
 * Created by PETER on 2015/3/25.
 */
public class ControlUnwrapper extends Unwrapper{
    private static final int HEAD_LENGTH=6;
    private HexString hexString=new HexString();
    private FieldTypeParam address1Param=new FieldTypeParam(2);
    private FieldTypeParam address2Param=new FieldTypeParam(2);
    @Override
    public void decode(ByteBuffer in, PacketSegmentContext packetSegmentContext,
                       ProtocalTemplate protocalTemplate, CodecConfig codecConfig) throws Exception{
        Control control=(Control)packetSegmentContext.getSegment(SegmentEnum.control);
        in.clear();
        in.position(HEAD_LENGTH);
        //控制域功能码,帧计数有效位,fcb帧计数位,启动标志位,传输方向位 解码
        byte b1=in.get();
        control.setFunctionCode(b1&0x0f);
        control.setFcv((b1>>4) & 0x01);
        control.setFcbOrAcd((b1>>5) & 0x01);
        control.setPrm((b1>>6) & 0x01);
        control.setDir((b1>>7) & 0x01);

        //地址解码
        address1Param.setLength(codecConfig.getDistinctLength());
        control.setAddress1(hexString.decode(in,address1Param));
        address2Param.setLength(codecConfig.getTerminalAddressLength());
        control.setAddress2(hexString.decode(in,address2Param));

        //主站地址，afn解码
        byte b2=in.get();
        control.setIsGroup(b2 & 0x01);
        control.setMsa((b2>>1) & 0x7f);
        control.setAfn(in.get());

        //帧序列，是否需确认，是否报文第一帧，是否报文最后一帧，附加信息是否有时间标签 解码
        byte b3=in.get();
        control.setSeq(b3 & 0x0f);
        control.setIsNeedConfirm((b3>>4) & 0x01);
        control.setFin((b3>>5) & 0x01);
        control.setFir((b3>>6) & 0x01);
        control.setTpV((b3>>7) & 0x01);


        if(next!=null){
            next.decode(in,packetSegmentContext,protocalTemplate,codecConfig);
        }
    }
}
