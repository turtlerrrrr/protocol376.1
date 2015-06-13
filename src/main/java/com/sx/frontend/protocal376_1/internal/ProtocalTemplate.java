package com.sx.frontend.protocal376_1.internal;

import com.sx.frontend.protocal376_1.CodecConfig;
import com.sx.frontend.protocal376_1.internal.ConfigParse.Constants;
import com.sx.frontend.protocal376_1.internal.ConfigParse.FieldGroup;
import com.sx.frontend.protocal376_1.internal.ConfigParse.IFieldNode;
import com.sx.frontend.protocal376_1.internal.ConfigParse.LoadHelper;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by PETER on 2015/3/14.
 * 加载规约模板
 */
public class ProtocalTemplate implements Constants {
    private SAXBuilder saxBuilder = new SAXBuilder();
    private final String CONFIG_FILE="protocal.xml";
    /**
     * 附件区中的字段组
     */
    private Map<String,FieldGroup> fieldGroupMap=new ConcurrentHashMap<>();
    /**
     * 按afn，pn索引数据域
     */
    private Map<String,FieldGroup> dataMap=new ConcurrentHashMap<>();
    /**
     * 按数据域名称索引数据域
     */
    private Map<String,FieldGroup> dataMapByName=new ConcurrentHashMap<>();
    /**
     * 事件按事件号索引
     */
    private Map<String,IFieldNode> eventMap=new ConcurrentHashMap<>();

    /**
     * 事件按名称索引
     */
    private Map<String,IFieldNode> eventMapByName=new ConcurrentHashMap<>();

    /**
     * 默认配置
     */
    private CodecConfig codecConfig;

    /**
     * 加载配置文件，配置文件入口protocal.xml
     * @param protocalName
     * @throws Exception
     */

    public void loadConfig(String protocalName) throws Exception{
        //加载文件
        InputStream stream=Thread.currentThread()
                .getContextClassLoader().getResourceAsStream(CONFIG_FILE);
        Document document=saxBuilder.build(stream);
        Element rootEl = document.getRootElement();
        //载入配置文件
        for(Element el:rootEl.getChildren()) {
            if (el.getAttributeValue(NAME).equals(protocalName)) {
                findConfigFiles(el,protocalName);
            }
        }
    }

    /**
     * 依次查找配置文件的文件
     *
     * @param element
     * @param protocalName
     * @throws Exception
     */
    public void findConfigFiles(Element element,String protocalName) throws Exception{
        Element files=element.getChild("files");
        for(Element el:files.getChildren()){
            InputStream stream=Thread.currentThread()
                    .getContextClassLoader().getResourceAsStream(String.format("%s/%s",protocalName,el.getText()));
            Document document=saxBuilder.build(stream);
            Element rootEl = document.getRootElement();
            if(rootEl.getName().equals(DATAS)){
                loadDataConfig(rootEl);
            }
            if(rootEl.getName().equals(EVENTS)){
                loadEventGroup(rootEl);
            }
            if(rootEl.getName().equals(FIELDGROUPS)){
                loadFieldGroupConfig(rootEl);
            }
        }
        Element params=element.getChild("params");
        loadCodecConfig(params);
    }

    /**
     * 载入默认配置文件
     * @param element
     */
    private void loadCodecConfig(Element element) throws Exception{
        codecConfig=new CodecConfig();
        if(element==null){
            return;
        }
        for(Element el:element.getChildren()){
            if(el.getName().equals("entry")){
                String key=el.getAttributeValue("key");
                String value=el.getAttributeValue("value");
                Field field=null;
                try {
                    field = codecConfig.getClass().getDeclaredField(key);
                }catch (Exception e){}
                if(field!=null){
                    String name = key.substring(0, 1).toUpperCase() + key.substring(1);
                    if("int".equals(field.getGenericType().toString())){
                        Method method=codecConfig.getClass().getMethod("set"+name,int.class);
                        method.invoke(codecConfig,Integer.parseInt(value));
                    }
                    if("class java.lang.String".equals(field.getGenericType().toString())){
                        Method method=codecConfig.getClass().getMethod("set"+name,String.class);
                        method.invoke(codecConfig,value);
                    }
                    if("boolean".equals(field.getGenericType().toString())){
                        Method method=codecConfig.getClass().getMethod("set"+name,boolean.class);
                        method.invoke(codecConfig,Boolean.parseBoolean(value));
                    }
                }

            }

        }
    }


    /**
     * 加载数据项
     * @param element
     * @throws Exception
     */
    private void loadDataConfig(Element element) throws Exception{
        Element encode=element.getChild(ENCODE);
        Element decode=element.getChild(DECODE);
        for(Element el1:element.getChildren()){
            if(DATA.equals(el1.getName())){
                FieldGroup fieldGroup= LoadHelper.loadFieldGroup(DATA, el1);
                //批量设置单解码或编码
                if(encode!=null){
                    fieldGroup.setDirection(1);
                }else if(decode!=null){
                    fieldGroup.setDirection(2);
                }

                //数据索引key
                String afnString=element.getAttributeValue(PATH).toLowerCase();
                String fnpnString=el1.getAttributeValue(PATH).toLowerCase();
                fieldGroup.setAfn(Integer.parseInt(
                        afnString.substring(afnString.indexOf("n")+1, afnString.indexOf("h")),16));
                fieldGroup.setFn(Integer.parseInt(
                        fnpnString.substring(fnpnString.indexOf("f")+1)));
                dataMap.put(afnString+fnpnString, fieldGroup);
                dataMapByName.put(fieldGroup.getName(),fieldGroup);
            }
        }
    }


    /**
     * 加载字段组
     * @param element
     * @throws Exception
     */
    private void loadFieldGroupConfig(Element element) throws Exception{
        for(Element el:element.getChildren()){
            FieldGroup fg=LoadHelper.loadFieldGroup(FIELDGROUP,el);
            fieldGroupMap.put(el.getAttributeValue(NAME),fg);
        }
    }


    /**
     * 加载事件定义
     * @param element
     * @throws Exception
     */
    private void loadEventGroup(Element element) throws Exception{
        for(Element el:element.getChildren()){
            FieldGroup fg=LoadHelper.loadFieldGroup(EVENT,el);
            eventMap.put(el.getAttributeValue(EVENTID),fg);
            eventMapByName.put(el.getAttributeValue(NAME),fg);
        }

    }

    public Map<String, FieldGroup> getFieldGroupMap() {
        return fieldGroupMap;
    }

    public Map<String, FieldGroup> getDataMap() {
        return dataMap;
    }

    public Map<String, FieldGroup> getDataMapByName() {
        return dataMapByName;
    }

    public Map<String, IFieldNode> getEventMap() {
        return eventMap;
    }

    public Map<String, IFieldNode> getEventMapByName() {
        return eventMapByName;
    }

    public CodecConfig getCodecConfig() {
        return codecConfig;
    }
}
