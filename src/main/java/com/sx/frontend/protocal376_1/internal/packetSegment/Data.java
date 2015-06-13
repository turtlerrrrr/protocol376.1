package com.sx.frontend.protocal376_1.internal.packetSegment;

import java.util.Map;

/**
 * Created by PETER on 2015/3/25.
 */
public class Data extends Segment{

    /**
     * 信息点pn
     */
    private int pn;
    /**
     * 信息类fn
     */
    private int fn;
    /**
     * 数据
     */
    private Map<String,Object> data;

    private String command;

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public int getPn() {
        return pn;
    }

    public void setPn(int pn) {
        this.pn = pn;
    }

    public int getFn() {
        return fn;
    }

    public void setFn(int fn) {
        this.fn = fn;
    }

    @Override
    public void reset() {
        pn=0;
        fn=0;
        command=null;
        data=null;
        getBuffer().clear();
    }
}
