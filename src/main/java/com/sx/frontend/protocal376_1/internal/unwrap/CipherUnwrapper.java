package com.sx.frontend.protocal376_1.internal.unwrap;
import com.sx.frontend.protocal376_1.CodecConfig;
import com.sx.frontend.protocal376_1.internal.ProtocalTemplate;
import com.sx.frontend.protocal376_1.internal.packetSegment.Control;
import com.sx.frontend.protocal376_1.internal.packetSegment.Head;
import com.sx.frontend.protocal376_1.internal.packetSegment.PacketSegmentContext;
import com.sx.frontend.protocal376_1.internal.packetSegment.SegmentEnum;

import java.nio.ByteBuffer;


/**
 * Created by PETER on 2015/5/4.
 */
public class CipherUnwrapper extends Unwrapper{
    private static final int CONTROL_LENGTH=10;
    private static final int HEAD_LENGTH=6;
    @Override
    public void decode(ByteBuffer in, PacketSegmentContext packetSegmentContext,
                       ProtocalTemplate protocalTemplate, CodecConfig codecConfig) throws Exception {
        if(in.get(0)==0x69){
            Control control=(Control)packetSegmentContext.getSegment(SegmentEnum.control);
            Head head=(Head)packetSegmentContext.getSegment(SegmentEnum.head);
            //固定剩余数据长度
            int tailLeft=4;
            if(control.getFcbOrAcd()==1){
                tailLeft+=2;
            }
            if(control.getTpV()==1){
                tailLeft+=6;
            }
            int bodyLength=head.getLength()-tailLeft-4-CONTROL_LENGTH;
            byte[] headft=new byte[CONTROL_LENGTH+HEAD_LENGTH];
            byte[] body=new byte[bodyLength];
            byte[] tailft=new byte[tailLeft];
            in.clear();
            in.get(headft);
            in.get(body);
            in.get(tailft);
            //解密
            byte[]  decrypted=body;
            ByteBuffer out=ByteBuffer.allocate(headft.length+decrypted.length+tailLeft);
            out.put(headft);
            out.put(decrypted);
            out.put(tailft);
            out.position(CONTROL_LENGTH+HEAD_LENGTH);
            if(next!=null){
                next.decode(out, packetSegmentContext, protocalTemplate, codecConfig);
            }

        }else{
            if(next!=null){
                next.decode(in, packetSegmentContext, protocalTemplate, codecConfig);
            }
        }


    }
}
