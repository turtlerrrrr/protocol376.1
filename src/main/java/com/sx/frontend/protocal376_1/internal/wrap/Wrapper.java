package com.sx.frontend.protocal376_1.internal.wrap;

import com.sx.frontend.protocal376_1.CodecConfig;
import com.sx.frontend.protocal376_1.Packet;
import com.sx.frontend.protocal376_1.internal.ProtocalTemplate;
import com.sx.frontend.protocal376_1.internal.packetSegment.PacketSegmentContext;

/**
 * Created by PETER on 2015/3/24.
 */
public abstract class Wrapper {
    protected Wrapper next;
    abstract void encode(Packet in,PacketSegmentContext packetSegmentContext,
                ProtocalTemplate protocalTemplate,CodecConfig codecConfig) throws Exception;

    public void setNext(Wrapper next) {
        this.next = next;
    }
}
