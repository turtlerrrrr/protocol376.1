package com.sx.frontend.protocal376_1.internal.wrap;

import com.sx.frontend.protocal376_1.CodecConfig;
import com.sx.frontend.protocal376_1.Packet;
import com.sx.frontend.protocal376_1.internal.ProtocalTemplate;
import com.sx.frontend.protocal376_1.internal.packetSegment.Control;
import com.sx.frontend.protocal376_1.internal.packetSegment.Data;
import com.sx.frontend.protocal376_1.internal.packetSegment.PacketSegmentContext;
import com.sx.frontend.protocal376_1.internal.packetSegment.SegmentEnum;

import java.util.List;

/**
 * Created by PETER on 2015/5/5.
 */
public class CipherWrapper extends Wrapper{

    @Override
    void encode(Packet in, PacketSegmentContext packetSegmentContext, ProtocalTemplate protocalTemplate, CodecConfig codecConfig) throws Exception {
        Control control=(Control)packetSegmentContext.getSegment(SegmentEnum.control);
        Data dataSeg=(Data)packetSegmentContext.getSegment(SegmentEnum.data);
        int afn=control.getAfn();

        if(afn==0x04 || afn==0x05){
            List<byte[]> body=dataSeg.getBuffer();
            //加密
            List<byte[]> crypted=body;
            dataSeg.getBuffer().clear();
            dataSeg.getBuffer().addAll(crypted);
        }
        if(next!=null){
            next.encode(in,packetSegmentContext,protocalTemplate,codecConfig);
        }
    }
}
