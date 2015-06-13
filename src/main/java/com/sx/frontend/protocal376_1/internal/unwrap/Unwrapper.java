package com.sx.frontend.protocal376_1.internal.unwrap;

import com.sx.frontend.protocal376_1.CodecConfig;
import com.sx.frontend.protocal376_1.internal.ProtocalTemplate;
import com.sx.frontend.protocal376_1.internal.packetSegment.PacketSegmentContext;

import java.nio.ByteBuffer;

/**
 * Created by PETER on 2015/3/25.
 */
public abstract class Unwrapper {
    protected Unwrapper next;
    abstract  void decode(ByteBuffer in,PacketSegmentContext packetSegmentContext,
                    ProtocalTemplate protocalTemplate,CodecConfig codecConfig) throws Exception;

    void setNext(Unwrapper unwrapper){
        next=unwrapper;
    }

}
