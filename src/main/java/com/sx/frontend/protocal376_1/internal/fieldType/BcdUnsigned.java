package com.sx.frontend.protocal376_1.internal.fieldType;


import com.sx.frontend.protocal376_1.internal.ConfigParse.FieldTypeParam;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

import java.nio.ByteBuffer;

/**
 * Created by PETER on 2015/2/5.
 * 无符号bcd码
 */
public class BcdUnsigned implements IFieldType {

    private static final String INVALID="-999999999";
    private StringBuilder stringBuilder=new StringBuilder();
    @Override
    public byte[] encode(final Object value,final FieldTypeParam fieldTypeParam) throws Exception{
        int length=fieldTypeParam.getLength();
        byte[] out=new byte[length];
        double in=Double.parseDouble(value.toString());
        long integerValue=Math.round(in / fieldTypeParam.getDegree());
        for(int i=0;i<length;i++){
            out[i]=(byte)intToBcd((int)(integerValue/(long)Math.pow(100,i)%100));
        }
        return out;
    }

    @Override
    public String decode(final ByteBuffer byteBuffer,final FieldTypeParam fieldTypeParam) throws Exception {
        int length=fieldTypeParam.getLength();
        long integerValue=0;
        byte[] value=new byte[length];
        byteBuffer.get(value);
        stringBuilder.delete(0,stringBuilder.length());
        stringBuilder.append(Hex.encodeHex(value));
        if(StringUtils.repeat("ee", length).equals(stringBuilder.toString())){
            return INVALID;
        }

        for(int i=0;i<length;i++){
            integerValue=integerValue+bcdToInt(value[i])*(long)Math.pow(100,i);
        }
        double out=integerValue*fieldTypeParam.getDegree();
        double acc=Math.log10(fieldTypeParam.getDegree());
        if(acc<0){
            int iacc=Math.abs((int)acc);
            String format="%."+iacc+"f";
            return String.format(format,out);
        }else{
            if(out<Long.MAX_VALUE){
                return String.valueOf((long)out);
            }else{
                return String.valueOf(out);
            }

        }

    }

    private int bcdToInt(int bcdValue){
        return (0x0f & bcdValue)+(0x0f & bcdValue>>4)*10;
    }

    private int intToBcd(int intValue){
        return (intValue/10)*16 + intValue%10;
    }

    @Override
    public void reset() {

    }
}
