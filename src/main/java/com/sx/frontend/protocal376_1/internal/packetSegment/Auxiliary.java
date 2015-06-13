package com.sx.frontend.protocal376_1.internal.packetSegment;

/**
 * Created by PETER on 2015/3/25.
 */
public class Auxiliary extends Segment{
    private String password;
    private int ec1;
    private int ec2;
    private int pfc;
    private long sendTime;
    private int timeout;

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getEc1() {
        return ec1;
    }

    public void setEc1(int ec1) {
        this.ec1 = ec1;
    }

    public int getEc2() {
        return ec2;
    }

    public void setEc2(int ec2) {
        this.ec2 = ec2;
    }

    public int getPfc() {
        return pfc;
    }

    public void setPfc(int pfc) {
        this.pfc = pfc;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    @Override
    public void reset() {
        password="";
        ec1=0;
        ec2=0;
        pfc=0;
        sendTime=0;
        timeout=0;
        getBuffer().clear();
    }
}
