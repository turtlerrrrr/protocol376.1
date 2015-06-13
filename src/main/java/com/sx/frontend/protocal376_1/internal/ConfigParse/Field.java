package com.sx.frontend.protocal376_1.internal.ConfigParse;


import java.util.List;
import java.util.Map;
/**
 * Created by PETER on 2015/2/6.
 * 用于加载配置文件中field属性
 */
public class Field implements IFieldNode {

    private String name;

    /**
     * 字段注释
     */
    private String remark;

    /**
     * 字段类型
     */
    private String fieldType;

    /**
     * 字段类型参数，编码解码时的参数
     */
    private FieldTypeParam fieldTypeParam;

    /**
     * 字段值的实际意义描述
     */
    private Map<Integer,String> description;

    /**
     * 单位
     */
    private String unit;

    /**
     * 默认值
     */
    private String defaultValue;

    /**
     * 字段解码是否显示该字段
     */
    private boolean hidden=false;

    /**
     * 字段编码或者解码方向 0 编码和解码 ，1 只是编码 ，2 只是解码
     */
    private int direction=0;

    /**
     * 校验器
     */

    private List<Validator> validators;



    public String getRemark() {
        return remark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Map<Integer, String> getDescription() {
        return description;
    }

    public void setDescription(Map<Integer, String> description) {
        this.description = description;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public FieldTypeParam getFieldTypeParam() {
        return fieldTypeParam;
    }

    public void setFieldTypeParam(FieldTypeParam fieldTypeParam) {
        this.fieldTypeParam = fieldTypeParam;
    }

    public String getFieldType() {
        return fieldType;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public List<Validator> getValidators() {
        return validators;
    }

    public void setValidators(List<Validator> validators) {
        this.validators = validators;
    }

    @Override
    public String toString() {
        return "Field{" +
                "remark='" + remark + '\'' +
                ", fieldType='" + fieldType + '\'' +
                ", fieldTypeParam=" + fieldTypeParam +
                ", description=" + description +
                ", unit='" + unit + '\'' +
                ", defaultValue='" + defaultValue + '\'' +
                ", hidden=" + hidden +
                ", direction=" + direction +
                ", validators=" + validators +
                '}';
    }
}
