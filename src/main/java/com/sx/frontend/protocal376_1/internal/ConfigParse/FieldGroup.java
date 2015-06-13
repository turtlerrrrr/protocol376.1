package com.sx.frontend.protocal376_1.internal.ConfigParse;

import java.util.Map;

/**
 * Created by PETER on 2015/2/6.
 */
public class FieldGroup implements IFieldNode {
    /**
     * 字段组类型
     */
    private String type;

    /**
     * 字段组名称
     */
    private String name;

    /**
     * 字段组注释
     */
    private String remark;

    /**
     * 字段编码或者解码方向 0 编码和解码 ，1 只是编码 ，2 只是解码
     */
    private int direction=0;

    /**
     * list fieldgroup 的数量
     */
    private String refNum;

    /**
     * 引用 fieldgroup 的名称
     */
    private String refType;

    /**
     * data field 的 afn
     */
    private int afn;
    /**
     * data field 的 fn
     */
    private int fn;
    /**
     * 转换器
     */
    private String formater;
    /**
     * 字段组包含节点
     */
    private Map<String,IFieldNode> childNodes;

    public FieldGroup() {
    }

    public FieldGroup(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public String getRefNum() {
        return refNum;
    }

    public void setRefNum(String refNum) {
        this.refNum = refNum;
    }

    public String getRefType() {
        return refType;
    }

    public void setRefType(String refType) {
        this.refType = refType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Map<String, IFieldNode> getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(Map<String, IFieldNode> childNodes) {
        this.childNodes = childNodes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAfn() {
        return afn;
    }

    public void setAfn(int afn) {
        this.afn = afn;
    }

    public int getFn() {
        return fn;
    }

    public void setFn(int fn) {
        this.fn = fn;
    }

    public String getFormater() {
        return formater;
    }

    public void setFormater(String formater) {
        this.formater = formater;
    }

    @Override
    public String toString() {
        return "FieldGroup{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", direction=" + direction +
                ", refNum='" + refNum + '\'' +
                ", refType='" + refType + '\'' +
                ", childNodes=" + childNodes +
                '}';
    }
}
