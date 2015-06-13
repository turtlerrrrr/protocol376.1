package com.sx.frontend.protocal376_1.internal.ConfigParse;

/**
 * Created by PETER on 2015/3/3.
 * 用于加载字段类型参数
 */
public class FieldTypeParam {
    /**
     * 字段长度
     */
    private int length=0;
    /**
     * bcd解码中的最低位，如一位小数则为0.1，百位为100，个位为1
     */
    private double degree=1;

    public FieldTypeParam(int length, double degree) {
        this.length = length;
        this.degree = degree;
    }

    public FieldTypeParam(int length) {
        this.length = length;
    }

    public FieldTypeParam(){

    }



    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public double getDegree() {
        return degree;
    }

    public void setDegree(double degree) {
        this.degree = degree;
    }

    @Override
    public String toString() {
        return "FieldTypeParam{" +
                "length=" + length +
                ", degree=" + degree +
                '}';
    }
}
