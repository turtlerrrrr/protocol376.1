package com.sx.frontend.protocal376_1;

import java.util.HashMap;
import java.util.Map;
/**
 * Created by PETER on 2015/3/13.
 */
public class Packet {
    /**
     * 线路编号，0为终端本身
     */
    private Integer line;

    /**
     * 终端号
     */
    private String terminalAddress;

    /**
     * 命令名
     */
    private String command;

    /**
     * 数据
     */
    private Map<String,Object> data;

    public Packet(){
        data=new HashMap<>();
    }

    public Packet(String command, HashMap<String, Object> data) {
        this.command = command;
        this.data = data;
    }

    public Packet(Integer line, String command, Map<String, Object> data) {
        this.line = line;
        this.command = command;
        this.data = data;
    }

    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }

    public String getTerminalAddress() {
        return terminalAddress;
    }

    public void setTerminalAddress(String terminalAddress) {
        this.terminalAddress = terminalAddress;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RgmPacket{" +
                "line=" + line +
                ", terminalAddress='" + terminalAddress + '\'' +
                ", command='" + command + '\'' +
                ", data=" + data +
                '}';
    }
}
