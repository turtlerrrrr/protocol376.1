package com.sx.frontend.protocal376_1.internal;


import com.google.gson.Gson;
import com.sx.frontend.protocal376_1.internal.ConfigParse.Constants;
import com.sx.frontend.protocal376_1.internal.ConfigParse.Field;
import com.sx.frontend.protocal376_1.internal.ConfigParse.FieldGroup;
import com.sx.frontend.protocal376_1.internal.ConfigParse.IFieldNode;

import java.util.*;

/**
 * Created by PETER on 2015/3/16.
 */
public class Inquire implements Constants{
//    private final static Logger logger = LoggerFactory
//            .getLogger(com.sx.frontend.protocal376_1.internal.Inquire.class);

    /**
     * 规约模板
     */
    private ProtocalTemplate protocalTemplate;
    private static Gson gson=new Gson();
    public Inquire(ProtocalTemplate protocalTemplate){
        this.protocalTemplate=protocalTemplate;
    }
    public String getFieldRemark(String command, String field) {
        String notfound="没有找到该字段";
        FieldGroup dataGroup=protocalTemplate.getDataMapByName().get(command);
        if(dataGroup==null){
            return notfound;
        }
        Object out=getContent(dataGroup, field, REMARK);
        if(out==null){
            return notfound;
        }else{
            return out.toString();
        }
    }

    private Object getContent(FieldGroup fieldGroup,String field,String contentType){
        Iterator dataIt=fieldGroup.getChildNodes().entrySet().iterator();
        while (dataIt.hasNext()) {
            Map.Entry entry = (Map.Entry) dataIt.next();
            IFieldNode fieldNode = (IFieldNode) entry.getValue();
            String name = (String) entry.getKey();
            if (fieldNode instanceof Field) {
                Field fd = (Field) fieldNode;
                if (name.equals(field)) {
                    if(contentType.equals(REMARK)){
                        return fd.getRemark();
                    }
                    if(contentType.equals(UNIT)){
                        return fd.getUnit();
                    }
                    if(contentType.equals(DESCRIPTION)){
                        return  fd.getDescription();
                    }

                }
            }
            //解码group，递归解码
            if (fieldNode instanceof FieldGroup) {
                FieldGroup fg = (FieldGroup) fieldNode;
                if (LIST.equals(fg.getType())) {
                    Object o=getContent(fg, field, contentType);
                    if(o==null){
                        continue;
                    }else{
                        return o;
                    }

                }
                if(FIELDGROUP.equals(fg.getType())){
                    Object o=getContent(fg, field, contentType);
                    if(o==null){
                        continue;
                    }else{
                        return o;
                    }
                }
                if (REFFIELDGROUP.equals(fg.getType())) {
                    String refType = fg.getRefType();
                    FieldGroup refGroup = protocalTemplate.getFieldGroupMap().get(refType);
                    Object o=getContent(refGroup, field, contentType);
                    if(o==null){
                        continue;
                    }else{
                        return o;
                    }
                }
            }
        }
        return null;
    }

    public String getFieldUnit(String command, String field) {
        String notfound="没有找到该字段或该字段没有单位";
        FieldGroup dataGroup=protocalTemplate.getDataMapByName().get(command);
        if(dataGroup==null){
            return notfound;
        }
        Object out=getContent(dataGroup,field,UNIT);
        if(out==null){
            return notfound;
        }else{
            return out.toString();
        }
    }


    public String getValueDescription(String command, String field, Integer value) {
        String notfound="没有找到该字段或该值没有解释";
        FieldGroup dataGroup=protocalTemplate.getDataMapByName().get(command);
        if(dataGroup==null){
            return notfound;
        }
        Map<Integer,String> out=(Map)getContent(dataGroup,field,DESCRIPTION);
        if(out==null){
            return notfound;
        }else{
            String dec=((Map)out).get(value).toString();
            if(dec==null){
                return notfound;
            }else{
                return dec;
            }
        }
    }


    public Map<Integer,String> getValueDescriptionList(String command, String field) {
        Map<Integer,String> notfound=new HashMap<>();
        FieldGroup dataGroup=protocalTemplate.getDataMapByName().get(command);
        if(dataGroup==null){
            return notfound;
        }
        Object out=getContent(dataGroup,field,DESCRIPTION);
        if(out==null){
            return notfound;
        }else{
            return (Map)out;
        }

    }


    public List<String> getDataList() {
        return new ArrayList<>(protocalTemplate.getDataMapByName().keySet());
    }


    public List<String> getEventList() {
        return new ArrayList<>(protocalTemplate.getEventMapByName().keySet());
    }

    public String getDataTemplate(String dataName) {
        FieldGroup dataGroup=protocalTemplate.getDataMapByName().get(dataName);
        String notfound="NOT_FOUND";
        if(dataGroup==null){
            return notfound;
        }
        Map temp=getTemplate(dataGroup);
        return gson.toJson(temp);
    }

    private Map getTemplate(FieldGroup dataGroup){
        Map<String,Object> dataContent=new LinkedHashMap<>();
        Iterator dataIt=dataGroup.getChildNodes().entrySet().iterator();
        while (dataIt.hasNext()){
            Map.Entry entry=(Map.Entry)dataIt.next();
            IFieldNode IFieldNode =(IFieldNode)entry.getValue();
            String name= (String) entry.getKey();
            if(IFieldNode instanceof Field){
                Field field=(Field) IFieldNode;
                if(field.getDirection()==2){
                    continue;
                }
                if(!field.isHidden()){
                    dataContent.put(name,field.getFieldType());
                }

            }
            //解码group，递归解码
            if(IFieldNode instanceof FieldGroup){
                FieldGroup fieldGroup=(FieldGroup) IFieldNode;
                if(fieldGroup.getDirection()==2){
                    continue;
                }
                if(LIST.equals(fieldGroup.getType())){
                    List<Object> listContent=new ArrayList<>();
                    if(fieldGroup.getFormater()!=null){
                        listContent.add(fieldGroup.getFormater());
                    }else{
                        Map<String,Object> many=getTemplate(fieldGroup);
                        listContent.add(many);
                    }
                    dataContent.put(LIST,listContent);
                }
                if(FIELDGROUP.equals(fieldGroup.getType())){
                    if(fieldGroup.getFormater()!=null){
                        dataContent.put(fieldGroup.getName(),fieldGroup.getFormater());
                    }else{
                        Map fieldGroupContent=getTemplate(fieldGroup);
                        dataContent.put(fieldGroup.getName(),fieldGroupContent);
                    }

                }
                if(REFFIELDGROUP.equals(fieldGroup.getType())){
                    String groupType=fieldGroup.getRefType();
                    FieldGroup refGroup=protocalTemplate.getFieldGroupMap().get(groupType);
                    if(refGroup.getFormater()!=null){
                        dataContent.put(fieldGroup.getName(),fieldGroup.getFormater());
                    }else{
                        Map fieldGroupContent=getTemplate(refGroup);
                        dataContent.put(fieldGroup.getName(),fieldGroupContent);
                    }

                }
            }
        }
        return dataContent;
    }
}
