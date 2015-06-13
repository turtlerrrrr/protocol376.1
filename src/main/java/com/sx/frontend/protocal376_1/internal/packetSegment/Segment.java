package com.sx.frontend.protocal376_1.internal.packetSegment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PETER on 2015/3/25.
 */
public abstract class Segment {
    private List<byte[]> buffer=new ArrayList<>();

    public List<byte[]> getBuffer() {
        return buffer;
    }

    public void setBuffer(List<byte[]> buffer) {
        this.buffer = buffer;
    }

    public abstract void reset();
}
