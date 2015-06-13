package com.sx.frontend.protocal376_1.internal.fieldType;


import com.sx.frontend.protocal376_1.exception.EncodingException;
import com.sx.frontend.protocal376_1.internal.ConfigParse.FieldTypeParam;

import java.nio.ByteBuffer;

/**
 * Created by PETER on 2015/3/20.
 * 编码解码Ascii码字符串
 */
public class Ascii implements IFieldType{

    /**
     * 输入字符串长度不满固定长度则补0
     * @param value 输入值 ascii字符串类型
     * @param fieldTypeParam 编码参数
     * @return
     * @throws Exception
     */
    @Override
    public byte[] encode(Object value, FieldTypeParam fieldTypeParam) throws Exception {
        byte[] result=new byte[fieldTypeParam.getLength()];
        byte[] string = value.toString().getBytes();
        if(string.length>fieldTypeParam.getLength()){
            throw new EncodingException(1111,
                    String.format("string is too long '''%s''' for fix length is %s" ,value,fieldTypeParam.getLength()));
        }else{
            System.arraycopy(string, 0, result, 0, string.length);
        }
        return result;
    }

    /**
     *
     * @param byteBuffer
     * @param fieldTypeParam
     * @return
     * @throws Exception
     */
    @Override
    public String decode(ByteBuffer byteBuffer, FieldTypeParam fieldTypeParam) throws Exception {
        char[] chars = new char[fieldTypeParam.getLength()];
        for (int i = 0; i < fieldTypeParam.getLength(); i++) {
            chars[i] = (char) byteBuffer.get();
        }
        return String.valueOf(chars).trim();
    }

    @Override
    public void reset() {

    }
}
