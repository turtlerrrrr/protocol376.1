package com.sx.frontend.protocal376_1.internal.fieldType;

import com.sx.frontend.protocal376_1.exception.EncodingException;
import com.sx.frontend.protocal376_1.internal.ConfigParse.FieldTypeParam;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

import java.nio.ByteBuffer;

/**
 * Created by PETER on 2015/2/5.
 */
public class HexString implements IFieldType {

    /**
     * 字符串长度不够，高位补0，太长，提示异常
     * @param value 输入值
     * @param fieldTypeParam 编码参数
     * @return
     * @throws Exception
     */
    @Override
    public byte[] encode(final Object value,final FieldTypeParam fieldTypeParam) throws Exception{
        String in=value.toString();
        if(in.length()/2>fieldTypeParam.getLength()){
            throw new EncodingException(1111,
                    String.format("string is too long '''%s''' for fix length is %s" ,value,fieldTypeParam.getLength())
            );
        }
        if(in.length()/2<fieldTypeParam.getLength()){
            in= StringUtils.leftPad(in,fieldTypeParam.getLength()*2,'0');
        }

        byte[] result=new byte[fieldTypeParam.getLength()];
        for(int i=0;i<fieldTypeParam.getLength()*2;i=i+2){
            result[fieldTypeParam.getLength()-i/2-1]=(byte)Integer.parseInt(in.substring(i,i+2),16);
        }
        return result;
    }

    @Override
    public String decode(final ByteBuffer byteBuffer,final FieldTypeParam fieldTypeParam) throws Exception{
        byte[] value=new byte[fieldTypeParam.getLength()];
        for(int i=0;i<fieldTypeParam.getLength();i++){
            value[fieldTypeParam.getLength()-i-1]=byteBuffer.get();
        }
        return new String(Hex.encodeHex(value));
    }

    @Override
    public void reset() {

    }
}
