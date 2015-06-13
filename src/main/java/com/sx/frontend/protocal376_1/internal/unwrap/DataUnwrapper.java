package com.sx.frontend.protocal376_1.internal.unwrap;

import com.sx.frontend.protocal376_1.CodecConfig;
import com.sx.frontend.protocal376_1.exception.DecodingException;
import com.sx.frontend.protocal376_1.exception.InvalidConfigException;
import com.sx.frontend.protocal376_1.internal.ConfigParse.*;
import com.sx.frontend.protocal376_1.internal.ProtocalTemplate;
import com.sx.frontend.protocal376_1.internal.fieldGroupFormater.FormatterImpl;
import com.sx.frontend.protocal376_1.internal.fieldGroupFormater.IFormatter;
import com.sx.frontend.protocal376_1.internal.fieldType.Da;
import com.sx.frontend.protocal376_1.internal.fieldType.Dt;
import com.sx.frontend.protocal376_1.internal.fieldType.FieldTypeContext;
import com.sx.frontend.protocal376_1.internal.fieldType.IFieldType;
import com.sx.frontend.protocal376_1.internal.packetSegment.Control;
import com.sx.frontend.protocal376_1.internal.packetSegment.Data;
import com.sx.frontend.protocal376_1.internal.packetSegment.PacketSegmentContext;
import com.sx.frontend.protocal376_1.internal.packetSegment.SegmentEnum;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.nio.ByteBuffer;
import java.util.*;

/**
 * Created by PETER on 2015/3/26.
 */
public class DataUnwrapper extends Unwrapper implements Constants{
    private Da da=new Da();
    private Dt dt=new Dt();
    private FieldTypeParam dadtParam=new FieldTypeParam(2);

    private FieldTypeContext fieldTypeContext=new FieldTypeContext();
    private IFormatter formatter=new FormatterImpl();
    private Map<String,DecodeFieldGroup> decodeFieldGroupMap=new HashMap<>();
    private static final int AFN_EVENT=14;

    public DataUnwrapper(){
        init();
    }

    private void init(){
        decodeFieldGroupMap.put(LIST, new DecodeFieldGroup() {
            @Override
            public void execute(FieldGroup fieldGroup, Map<String, Object> dataContent,
                                ByteBuffer byteBuffer, ProtocalTemplate protocalTemplate) throws Exception {
                ExpressionParser parser = new SpelExpressionParser();
                EvaluationContext context = new StandardEvaluationContext();
                context.setVariable("data", dataContent);
                int numRef;
                try {
                    numRef = parser.parseExpression(fieldGroup.getRefNum()).
                            getValue(context, Integer.class);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new DecodingException(1111, "list数量解析失败:" + fieldGroup.getRefNum());
                }
                List<Object> listContent = new ArrayList<>();
                for (int i = 0; i < numRef; i++) {
                    Map<String, Object> many = decodeData(fieldGroup, byteBuffer, protocalTemplate);
                    //format转换
                    if (fieldGroup.getFormater() != null) {
                        listContent.add(formatter.union(fieldGroup.getFormater(), many));
                    } else {
                        listContent.add(many);
                    }

                }
                dataContent.put(LIST, listContent);
            }
        });

        decodeFieldGroupMap.put(FIELDGROUP, new DecodeFieldGroup() {
            @Override
            public void execute(FieldGroup fieldGroup,Map<String,Object> dataContent,
                                ByteBuffer byteBuffer,ProtocalTemplate protocalTemplate) throws Exception {
                Map fieldGroupContent=decodeData(fieldGroup,byteBuffer,protocalTemplate);
                //format转换
                if(fieldGroup.getFormater()!=null){
                    dataContent.put(fieldGroup.getName(),
                            formatter.union(fieldGroup.getFormater(),fieldGroupContent));
                }else {
                    dataContent.put(fieldGroup.getName(),fieldGroupContent);
                }
            }
        });

        decodeFieldGroupMap.put(REFFIELDGROUP, new DecodeFieldGroup() {
            @Override
            public void execute(FieldGroup fieldGroup, Map<String, Object> dataContent,
                                ByteBuffer byteBuffer, ProtocalTemplate protocalTemplate) throws Exception {
                String groupType = fieldGroup.getRefType();
                FieldGroup refGroup = protocalTemplate.getFieldGroupMap().get(groupType);
                Map fieldGroupContent = decodeData(refGroup, byteBuffer, protocalTemplate);
                //format转换
                if (refGroup.getFormater() != null) {
                    dataContent.put(fieldGroup.getName(),
                            formatter.union(refGroup.getFormater(), fieldGroupContent));
                } else {
                    dataContent.put(fieldGroup.getName(), fieldGroupContent);
                }
            }
        });
    }

    @Override
    public void decode(ByteBuffer in, PacketSegmentContext packetSegmentContext,
                       ProtocalTemplate protocalTemplate, CodecConfig codecConfig) throws Exception{
        Data dataSeg=(Data)packetSegmentContext.getSegment(SegmentEnum.data);
        Control control=(Control)packetSegmentContext.getSegment(SegmentEnum.control);
        Map<String,Object> dataContentMap=new HashMap<>();
        String command=decodeDataSeg(in,protocalTemplate,dataSeg,control);
        //固定剩余数据长度
        int tailLeft=4;
        if(control.getFcbOrAcd()==1){
            tailLeft+=2;
        }
        if(control.getTpV()==1){
            tailLeft+=6;
        }
        while(in.limit()-in.position()>=tailLeft){
            //确认否认报文不解多数据单元
            if(control.getAfn()==0){
                break;
            }
            //多数据单元解码
            dataContentMap.put(command,dataSeg.getData());
            dataSeg.reset();
            command=decodeDataSeg(in,protocalTemplate,dataSeg,control);
        }

        if(!dataContentMap.isEmpty()){
            //设置多数据单元数据
            dataContentMap.put(command,dataSeg.getData());
            dataSeg.setCommand("multipleCommand");
            dataSeg.setData(dataContentMap);
        }

        if(next!=null){
            next.decode(in,packetSegmentContext,protocalTemplate,codecConfig);
        }

    }

    private String decodeDataSeg(ByteBuffer in, ProtocalTemplate protocalTemplate,
                               Data dataSeg,Control control) throws Exception{
        Map<String,Object> dataContentMap;
        dataSeg.setPn(da.decode(in,dadtParam));
        dataSeg.setFn(dt.decode(in,dadtParam));

        String path=String.format("afn%02xhf%s",control.getAfn(),dataSeg.getFn());
        FieldGroup dataGroup=protocalTemplate.getDataMap().get(path);
        if(dataGroup==null){
            throw new DecodingException(1111,String.format("不支持的数据解码,afn=%s,fn=%s",
                    control.getAfn(),dataSeg.getFn()));
        }else{
            dataSeg.setCommand(dataGroup.getName());

            //如果是仅编码字段则编码，否则返回空
            if(dataGroup.getDirection()!=1) {
                if (AFN_EVENT == control.getAfn()) {
                    dataContentMap = decodeEvent(dataGroup, in, protocalTemplate);
                } else {
                    dataContentMap = decodeData(dataGroup, in, protocalTemplate);
                }
            }else{
                dataContentMap=new HashMap<>();
            }
            dataSeg.setData(dataContentMap);
        }

        return dataGroup.getName();
    }

    private Map decodeData(FieldGroup dataGroup,final ByteBuffer byteBuffer,ProtocalTemplate protocalTemplate) throws Exception{
        Map<String,Object> dataContent=new LinkedHashMap<>();
        Iterator dataIt=dataGroup.getChildNodes().entrySet().iterator();
        while (dataIt.hasNext()){
            Map.Entry entry=(Map.Entry)dataIt.next();
            IFieldNode fieldNode =(IFieldNode)entry.getValue();
            String name= (String) entry.getKey();
            if(fieldNode instanceof Field){
                Field field=(Field) fieldNode;
                if(field.getDirection()==1){
                    continue;
                }
                decodeField(name,field,dataContent,byteBuffer);
            }
            //解码group，递归解码
            if(fieldNode instanceof FieldGroup){
                FieldGroup fieldGroup=(FieldGroup) fieldNode;
                if(fieldGroup.getDirection()==1){
                    continue;
                }

                DecodeFieldGroup decodeFieldGroup=decodeFieldGroupMap.get(fieldGroup.getType());
                if(decodeFieldGroup!=null){
                    decodeFieldGroup.execute(fieldGroup,dataContent,byteBuffer,protocalTemplate);
                }

            }
        }
        return dataContent;
    }

    private void decodeField(String name,Field field,Map Content,final ByteBuffer byteBuffer) throws Exception{
        IFieldType fieldType =fieldTypeContext.get(field.getFieldType());
        if(fieldType==null){
            throw new InvalidConfigException(1111,"找不到fieldType:"+field.getName());
        }
        Object value= fieldType.decode(byteBuffer, field.getFieldTypeParam());
        if(!field.isHidden()){
            Content.put(name,value);
        }
    }

    private Map decodeEvent(FieldGroup dataGroup,ByteBuffer byteBuffer,
                            ProtocalTemplate protocalTemplate) throws Exception{
        Map<String,Object> event=new HashMap<>();
        //ec1
        byteBuffer.get();
        //ec2
        byteBuffer.get();
        int pm=byteBuffer.get();
        int pn=byteBuffer.get();
        //事件数量
        int eventNum=0;
        if(pn>=pm){
            eventNum=pn-pm;
        }else {
            eventNum=256+pn-pm;
        }
        for(int i=0;i<eventNum;i++){
            String id=String.valueOf(byteBuffer.get()&0xff);
            FieldGroup eventGroup=(FieldGroup)protocalTemplate.getEventMap().get(id);
            if(eventGroup==null){
                throw new DecodingException(1111,"不支持事件解码,事件号:"+id);
            }
            Map<String,Object> eventMap=decodeData(eventGroup,byteBuffer,protocalTemplate);
            //移除数据长度数据（配置文件载入时添加）
            eventMap.remove(LENGTH);
            event.put(eventGroup.getName(),eventMap);
        }
        return event;
    }


    private interface DecodeFieldGroup{
        void execute(FieldGroup fieldGroup,Map<String,Object> dataContent,
                     ByteBuffer byteBuffer,ProtocalTemplate protocalTemplate) throws Exception;
    }


}
