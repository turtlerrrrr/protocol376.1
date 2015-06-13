package com.sx.frontend.protocal376_1.internal.packetSegment;

/**
 * Created by PETER on 2015/3/25.
 */
public class Control extends Segment{
    /**
     * 控制域功能码
     */
    private int functionCode;
    /**
     * 帧计数有效位
     * <entry key="1" value="fcb位有效"></entry>
     * <entry key="0" value="fcb位无效"></entry>
     */
    private int fcv=0;
    /**
     * fcb帧计数位(下行)，acd要求访问位(上行)
     * <entry key="1" value="(下行)启用fcb，(上行)终端有重要事件"></entry>
     * <entry key="0" value="(下行)关闭fcb，(上行)终端无重要事件"></entry>
     */
    private int fcbOrAcd;
    /**
     * 启动标志位
     * <entry key="1" value="报文来自启动站"></entry>
     * <entry key="0" value="报文来自从动站"></entry>
     */
    private int prm;
    /**
     * 传输方向位
     * <entry key="1" value="上行报文"></entry>
     * <entry key="0" value="下行报文"></entry>
     */
    private int dir;
    /**
     * 行政区域码
     *
     */
    private String address1;
    /**
     * 终端地址
     */
    private String address2;
    /**
     * 是否组地址
     * <entry key="1" value="组地址"></entry>
     * <entry key="0" value="单地址"></entry>
     */
    private int isGroup=0;
    /**
     * 主站编码
     */
    private int msa;
    /**
     * 功能码
     */
    private int afn;
    /**
     * 帧序列
     */
    private int seq;
    /**
     * 是否需确认
     * <entry key="1" value="需要确认"></entry>
     * <entry key="0" value="不需要确认"></entry>
     */
    private int isNeedConfirm;
    /**
     * 是否报文第一帧
     * <entry key="1" value="报文第一帧"></entry>
     * <entry key="0" value="不是报文第一帧"></entry>
     */
    private int fin;
    /**
     * 是否报文最后一帧
     * <entry key="1" value="报文最后一帧"></entry>
     * <entry key="0" value="不是报文最后一帧"></entry>
     */
    private int fir;
    /**
     * 附加信息是否有时间标签
     * <entry key="1" value="有时间标签"></entry>
     * <entry key="0" value="无时间标签"></entry>
     */
    private int tpV;


    public int getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(int functionCode) {
        this.functionCode = functionCode;
    }

    public int getFcv() {
        return fcv;
    }

    public void setFcv(int fcv) {
        this.fcv = fcv;
    }

    public int getFcbOrAcd() {
        return fcbOrAcd;
    }

    public void setFcbOrAcd(int fcbOrAcd) {
        this.fcbOrAcd = fcbOrAcd;
    }

    public int getPrm() {
        return prm;
    }

    public void setPrm(int prm) {
        this.prm = prm;
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public int getIsGroup() {
        return isGroup;
    }

    public void setIsGroup(int isGroup) {
        this.isGroup = isGroup;
    }

    public int getMsa() {
        return msa;
    }

    public void setMsa(int msa) {
        this.msa = msa;
    }

    public int getAfn() {
        return afn;
    }

    public void setAfn(int afn) {
        this.afn = afn;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public int getIsNeedConfirm() {
        return isNeedConfirm;
    }

    public void setIsNeedConfirm(int isNeedConfirm) {
        this.isNeedConfirm = isNeedConfirm;
    }

    public int getFin() {
        return fin;
    }

    public void setFin(int fin) {
        this.fin = fin;
    }

    public int getFir() {
        return fir;
    }

    public void setFir(int fir) {
        this.fir = fir;
    }

    public int getTpV() {
        return tpV;
    }

    public void setTpV(int tpV) {
        this.tpV = tpV;
    }


    @Override
    public void reset() {
        functionCode=0;
        fcbOrAcd=0;
        prm=0;
        dir=0;
        address1="";
        address2="";
        isGroup=0;
        msa=0;
        afn=0;
        seq=0;
        isNeedConfirm=0;
        fin=0;
        fir=0;
        tpV=0;
        getBuffer().clear();
    }
}
