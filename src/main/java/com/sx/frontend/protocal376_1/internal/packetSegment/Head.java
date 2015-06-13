package com.sx.frontend.protocal376_1.internal.packetSegment;

/**
 * Created by PETER on 2015/3/25.
 */
public class Head extends Segment{
    private int length;
    private int version;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }


    @Override
    public void reset() {
        length=0;
        version=0;
        getBuffer().clear();
    }
}
