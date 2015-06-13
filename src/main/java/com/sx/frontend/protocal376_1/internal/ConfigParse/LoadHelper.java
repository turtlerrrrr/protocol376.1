package com.sx.frontend.protocal376_1.internal.ConfigParse;

import org.jdom2.Element;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by PETER on 2015/2/14.
 */
public class LoadHelper implements Constants{

    /**
     * 加载Field
     * @param fieldElement field节点
     * @return
     */
    public static Field loadField(Element fieldElement){
        Field field=new Field();
        for(Element el1:fieldElement.getChildren()){
            field.setName(el1.getAttributeValue(NAME));
            //设置注释
            if(REMARK.equals(el1.getName())){
                field.setRemark(el1.getText());
            }

            //设置默认值
            if(DEFAULTVALUE.equals(el1.getName())){
                if(el1.getText().endsWith("H") || el1.getText().endsWith("h")){
                    String s=el1.getText().replace("H","").replace("h","");
                    s=String.valueOf(Integer.parseInt(s,16));
                    field.setDefaultValue(s);
                }else{
                    field.setDefaultValue(el1.getText());
                }
            }

            //设置是否隐藏
            if(HIDDEN.equals(el1.getName())){
                field.setHidden(true);
            }

            //设置字段类型
            if(FIELDTYPE.equals(el1.getName())){
                field.setFieldType(el1.getAttributeValue(NAME));
                FieldTypeParam fieldTypeParam=new FieldTypeParam();
                //设置字段参数
                for(Element el2:el1.getChildren()){
                    if(LENGTH.equals(el2.getName())){
                        fieldTypeParam.setLength(Integer.parseInt(el2.getValue()));
                    }
                    if(DEGREE.equals(el2.getName())){
                        fieldTypeParam.setDegree(Double.parseDouble(el2.getValue()));
                    }
                }
                field.setFieldTypeParam(fieldTypeParam);
            }

            //设置单位
            if(UNIT.equals(el1.getName())){
                field.setUnit(el1.getText());
            }

            //设置该字段仅编码
            if(ENCODE.equals(el1.getName())){
                field.setDirection(1);
            }

            //设置该字段仅解码
            if(DECODE.equals(el1.getName())){
                field.setDirection(2);
            }

            if(VALIDATOR.equals(el1.getName())){
                if(field.getValidators()==null){
                    field.setValidators(new ArrayList<Validator>());
                }
                List<Double> target=new ArrayList<>();
                for(String s:el1.getText().trim().split(",")){
                    target.add(Double.parseDouble(s));
                }
                field.getValidators().add(new Validator(el1.getAttributeValue(NAME),target));
            }

            //设置字段值的描述
            if(DESCRIPTION.equals(el1.getName())){
                Map<Integer,String> description=new LinkedHashMap<>();
                for(Element el2:el1.getChildren()){
                    if(ENTRY.equals(el2.getName())){
                        String key=el2.getAttributeValue(KEY);
                        if(key.indexOf("h")>0 || key.indexOf("H")>0){
                            key=key.replace("h","").replace("H","");
                            description.put(Integer.parseInt(key,16),el2.getAttributeValue(VALUE));
                        }else{
                            description.put(Integer.parseInt(key),el2.getAttributeValue(VALUE));
                        }

                    }
                }
                field.setDescription(description);
            }
        }
        return field;
    }

    /**
     * 加载字段组
     * @param groupType 字段组类型
     * @param fieldGroupElement fieldGroup节点
     * @return
     */
    public static FieldGroup loadFieldGroup(String groupType,Element fieldGroupElement){
        FieldGroup fieldGroup=new FieldGroup();
        Map<String,IFieldNode> childNodes=new LinkedHashMap<>();
        //如果是事件，添加长度字段
        if(EVENT.equals(groupType)){
            Field field=new Field();
            field.setName(LENGTH);
            field.setRemark(LENGTH);
            field.setFieldType("unsigned8");
            field.setFieldTypeParam(new FieldTypeParam(8));
            childNodes.put(LENGTH,field);
        }

        fieldGroup.setType(groupType);
        fieldGroup.setName(fieldGroupElement.getAttributeValue(NAME));
        for(Element el:fieldGroupElement.getChildren()){
            if(REMARK.equals(el.getName())){
                fieldGroup.setRemark(el.getValue());
            }
            //加载子节点
            if(FIELD.equals(el.getName())){
                childNodes.put(el.getAttributeValue(NAME),LoadHelper.loadField(el));
            }

            if(LIST.equals(el.getName()) || REFFIELDGROUP.equals(el.getName()) || FIELDGROUP.equals(el.getName())){
                childNodes.put(el.getAttributeValue(NAME),LoadHelper.loadFieldGroup(el.getName(),el));
            }

            if(FORMAT.equals(el.getName())){
                fieldGroup.setFormater(el.getValue());
            }
            //加载组参数
            if(REFNUM.equals(el.getName())){
                fieldGroup.setRefNum(el.getValue());
            }

            if(REFTYPE.equals(el.getName())){
                fieldGroup.setRefType(el.getValue());
            }

            //设置该字段组仅编码
            if(ENCODE.equals(el.getName())){
                fieldGroup.setDirection(1);
            }

            //设置该字段组仅解码
            if(DECODE.equals(el.getName())){
                fieldGroup.setDirection(2);
            }

        }
        fieldGroup.setChildNodes(childNodes);
        return fieldGroup;
    }
}
