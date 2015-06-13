package com.sx.frontend.protocal376_1.internal.packetSegment;

/**
 * Created by PETER on 2015/3/25.
 */
public class Tail extends Segment{
    private int checkSum;

    public int getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(int checkSum) {
        this.checkSum = checkSum;
    }

    @Override
    public void reset() {
        checkSum=0;
        getBuffer().clear();
    }
}
