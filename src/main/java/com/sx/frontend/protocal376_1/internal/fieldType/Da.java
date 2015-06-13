package com.sx.frontend.protocal376_1.internal.fieldType;


import com.sx.frontend.protocal376_1.internal.ConfigParse.FieldTypeParam;

import java.nio.ByteBuffer;

/**
 * Created by PETER on 2015/2/5.
 */
public class Da implements IFieldType {
    @Override
    public byte[] encode(final Object value,final FieldTypeParam fieldTypeParam) throws Exception {
        byte[] out=new byte[2];
        int pn=Integer.parseInt(value.toString());
        if(pn==0){
            out[0]=0;
            out[1]=0;
        }else{
            out[0]=(byte)(1 << (pn-1) % 8);
            out[1]=(byte)((pn) / 8+1);
        }
        return out;
    }

    @Override
    public Integer decode(final ByteBuffer byteBuffer,final FieldTypeParam fieldTypeParam) throws Exception {
        byte[] value=new byte[2];
        byteBuffer.get(value);
        if(value[0]==0 && value[1]==0){
            return 0;
        }else{
            int pn=(value[1]-1)*8;
            for(int i=0;i<8;i++){
                if((value[0]>>i==1)){
                    pn+=i+1;
                }
            }
            return pn;
        }
    }

    @Override
    public void reset() {

    }
}
