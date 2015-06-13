package com.sx.frontend.protocal376_1.internal.fieldType;

import com.sx.frontend.protocal376_1.exception.InvalidConfigException;
import com.sx.frontend.protocal376_1.internal.ConfigParse.FieldTypeParam;

import java.nio.ByteBuffer;

/**
 * Created by PETER on 2015/2/27.
 */
public class Unsigned16 implements IFieldType {
    private static final int BITS_PER_UNIT=16;
    /**
     * 剩余的位数
     */
    private int _avail=BITS_PER_UNIT;
    private short buffer;
    private static final int[] bmask=new int[]{
            0x00,   0x01,   0x03,   0x07,   0x0f,   0x1f,    0x3f,    0x7f,
            0xff,   0x1ff,  0x3ff,  0x7ff,  0xfff,  0x1fff,  0x3fff,  0x7fff,0xffff
    };
    @Override
    public byte[] encode(final Object value,final FieldTypeParam fieldTypeParam) throws Exception {
        int length=fieldTypeParam.getLength();
        if(length==0){
            length=BITS_PER_UNIT;
        }
        if(length>_avail){
            throw new InvalidConfigException(1111,"配置文件错误，Unsigned16长度超过16");
        }
        buffer=(short)(buffer+(Integer.parseInt(value.toString())<<(BITS_PER_UNIT-_avail)));
        if(length<_avail){
            _avail=_avail-length;
            return new byte[0];
        }else{
            _avail=BITS_PER_UNIT;
            short result=buffer;
            buffer=0;
            return new byte[]{(byte)result,(byte)(result>>8)};
        }
    }

    @Override
    public Integer decode(final ByteBuffer byteBuffer,final FieldTypeParam fieldTypeParam) throws Exception{
        int length=fieldTypeParam.getLength();
        if(length==0){
            length=BITS_PER_UNIT;
        }
        if(length>_avail){
            throw new InvalidConfigException(1111,"配置文件错误，Unsigned16长度超过16");
        }
        if(_avail==BITS_PER_UNIT){
            byte[] value=new byte[2];
            byteBuffer.get(value);
            buffer=(short)((value[0]&0xff)+(value[1]<<8));
        }
        int result=buffer&bmask[length];
        if(length<_avail){
            buffer>>=length;
            _avail-=length;
        }else{
            buffer=0;
            _avail=BITS_PER_UNIT;
        }
        return result;
    }

    @Override
    public void reset() {
        buffer=0;
        _avail=BITS_PER_UNIT;
    }
}
