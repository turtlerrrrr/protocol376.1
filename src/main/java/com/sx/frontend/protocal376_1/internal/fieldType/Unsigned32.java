package com.sx.frontend.protocal376_1.internal.fieldType;

import com.sx.frontend.protocal376_1.exception.InvalidConfigException;
import com.sx.frontend.protocal376_1.internal.ConfigParse.FieldTypeParam;

import java.nio.ByteBuffer;

/**
 * Created by PETER on 2015/2/5.
 */
public class Unsigned32 implements IFieldType {
    private static final int BITS_PER_UNIT=32;
    /**
     * 剩余的位数
     */
    private int _avail=BITS_PER_UNIT;
    private int buffer;
    private static final int[] bmask=new int[]{
            0x00,   0x01,   0x03,   0x07,   0x0f,   0x1f,    0x3f,    0x7f,
            0xff,   0x1ff,  0x3ff,  0x7ff,  0xfff,  0x1fff,  0x3fff,  0x7fff,
            0xffff, 0x1ffff,0x3ffff,0x7ffff,0xfffff,0x1fffff,0x3fffff,0x7fffff,
            0xffffff,0x1ffffff,0x3ffffff,0x7ffffff,0xfffffff,0x1fffffff,0x3fffffff,0x7fffffff,
            0xffffffff
    };

    @Override
    public byte[] encode(final Object value,final FieldTypeParam fieldTypeParam)  throws Exception {
        int length=fieldTypeParam.getLength();
        if(length==0){
            length=BITS_PER_UNIT;
        }
        if(length>_avail){
            throw new InvalidConfigException(1111,"配置文件错误，Unsigned32长度超过32");
        }
        buffer=buffer+(Integer.parseInt(value.toString())<<(BITS_PER_UNIT-_avail));
        if(length<_avail){
            _avail=_avail-length;
            return new byte[0];
        }else{
            _avail=BITS_PER_UNIT;
            int result=buffer;
            buffer=0;
            return new byte[]{(byte)result,(byte)(result>>8),(byte)(result>>16),(byte)(result>>24)};
        }
    }

    @Override
    public Integer decode(final ByteBuffer byteBuffer,final FieldTypeParam fieldTypeParam) throws Exception{
        int length=fieldTypeParam.getLength();
        if(length==0){
            length=BITS_PER_UNIT;
        }
        if(length>_avail){
            throw new InvalidConfigException(1111,"配置文件错误，Unsigned32长度超过32");
        }
        if(_avail==BITS_PER_UNIT){
            byte[] value=new byte[4];
            byteBuffer.get(value);
            buffer=(value[0]&0xff)+(value[1]<<8)+(value[2]<<16)+(value[3]<<24);
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
