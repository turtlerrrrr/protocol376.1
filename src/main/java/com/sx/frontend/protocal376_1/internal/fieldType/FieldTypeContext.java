package com.sx.frontend.protocal376_1.internal.fieldType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by PETER on 2015/3/24.
 */

public class FieldTypeContext {
    private Map<String,IFieldType> fieldTypeMap=new ConcurrentHashMap<>();
    public FieldTypeContext(){
        fieldTypeMap.put("bcdSigned",new BcdSigned());
        fieldTypeMap.put("bcdUnsigned",new BcdUnsigned());
        fieldTypeMap.put("bcdUnsignedByBit",new BcdUnsignedByBit());
        fieldTypeMap.put("da",new Da());
        fieldTypeMap.put("dt",new Dt());
        fieldTypeMap.put("hexString",new HexString());
        fieldTypeMap.put("unsigned8",new Unsigned8());
        fieldTypeMap.put("unsigned16",new Unsigned16());
        fieldTypeMap.put("unsigned32",new Unsigned32());
        fieldTypeMap.put("ascii",new Ascii());
    }

    public IFieldType get(String name){
        return fieldTypeMap.get(name);
    }

    public void reset(){
        for(IFieldType ft:fieldTypeMap.values()){
            ft.reset();
        }
    }
}
