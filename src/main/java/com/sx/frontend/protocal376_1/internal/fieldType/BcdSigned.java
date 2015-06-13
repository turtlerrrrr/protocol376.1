package com.sx.frontend.protocal376_1.internal.fieldType;


import com.sx.frontend.protocal376_1.internal.ConfigParse.FieldTypeParam;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

import java.nio.ByteBuffer;

/**
 * Created by PETER on 2015/2/5.
 * 有符号bcd码
 * 最高位为符号位
 */
public class BcdSigned implements IFieldType {
    private static final String INVALID="-999999999";
    private StringBuilder stringBuilder=new StringBuilder();
    private static final byte bmask=0x7f;
    private static final byte smask=(byte)0x80;
    @Override
    public byte[] encode(final Object value,final FieldTypeParam fieldTypeParam) throws Exception {
        int length=fieldTypeParam.getLength();
        byte[] out=new byte[length];
        long integerValue=(long)(Double.parseDouble(value.toString())/fieldTypeParam.getDegree());
        for(int i=0;i<length;i++){
            out[i]=(byte)intToBcd((int)(Math.abs(integerValue)/(long)Math.pow(100,i)%100));
        }
        if(integerValue<0){
            out[length-1]=(byte)(out[length-1]+smask);
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

        for(int i=0;i<length-1;i++){
            integerValue=integerValue+bcdToInt(value[i])*(long)Math.pow(100,i);
        }
        integerValue=integerValue+bcdToInt((value[length-1])&bmask)*(long)Math.pow(100,length-1);
        if((value[length-1]&smask)==-128){
            integerValue=integerValue*-1;
        }
        double out=integerValue*fieldTypeParam.getDegree();
        double acc=Math.log10(fieldTypeParam.getDegree());
        if(acc<0){
            int iacc=Math.abs((int)acc);
            String format="%."+iacc+"f";
            return String.format(format,out);
        }else{
            if(out<Long.MAX_VALUE && out>Long.MIN_VALUE){
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
