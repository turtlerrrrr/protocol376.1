package com.sx.frontend.protocal376_1;

/**
 * Created by PETER on 2015/3/24.
 */
public class CodecConfig implements Cloneable{
    /**
     * 主站编号
     */
    private int msa=1;

    /**
     * 终端地址
     * 16进制字符串格式
     */
    private String terminalAddress;
    /**
     * 终端通信密码
     */
    private String password="00000000000000000000000000000000";

    /**
     * 是否添加时标
     * 默认添加
     */

    private boolean haveTp=true;

    /**
     * 时标的超时时间
     * 默认5分钟
     */
    private int timeout=5;

    /**
     * 是否打开流程控制
     */
    private boolean processControl=true;

    /**
     * 规约版本
     */
    private int protocalVersion=3;

    /**
     * 地区码长度
     */
    private int distinctLength=2;

    /**
     * 终端地址吗长度
     */

    private int terminalAddressLength=2;

    @Override
    public Object clone() {
        CodecConfig cloned=null;
        try {
            cloned = (CodecConfig) super.clone();
            cloned.terminalAddress = new String(terminalAddress);
            cloned.password = new String(password);
        }catch (Exception e){}
        return cloned;
    }


    public int getMsa() {
        return msa;
    }

    public void setMsa(int msa) {
        this.msa = msa;
    }

    public String getTerminalAddress() {
        return terminalAddress;
    }

    public void setTerminalAddress(String terminalAddress) {
        this.terminalAddress = terminalAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isHaveTp() {
        return haveTp;
    }

    public void setHaveTp(boolean haveTp) {
        this.haveTp = haveTp;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public boolean isProcessControl() {
        return processControl;
    }

    public void setProcessControl(boolean processControl) {
        this.processControl = processControl;
    }

    public int getProtocalVersion() {
        return protocalVersion;
    }

    public void setProtocalVersion(int protocalVersion) {
        this.protocalVersion = protocalVersion;
    }

    public int getDistinctLength() {
        return distinctLength;
    }

    public void setDistinctLength(int distinctLength) {
        this.distinctLength = distinctLength;
    }

    public int getTerminalAddressLength() {
        return terminalAddressLength;
    }

    public void setTerminalAddressLength(int terminalAddressLength) {
        this.terminalAddressLength = terminalAddressLength;
    }

    @Override
    public String toString() {
        return "CodecConfig{" +
                "msa=" + msa +
                ", terminalAddress='" + terminalAddress + '\'' +
                ", password='" + password + '\'' +
                ", haveTp=" + haveTp +
                ", timeout=" + timeout +
                ", processControl=" + processControl +
                ", protocalVersion=" + protocalVersion +
                ", distinctLength=" + distinctLength +
                ", terminalAddressLength=" + terminalAddressLength +
                '}';
    }
}
