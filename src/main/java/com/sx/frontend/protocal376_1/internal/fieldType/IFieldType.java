package com.sx.frontend.protocal376_1.internal.fieldType;


import com.sx.frontend.protocal376_1.internal.ConfigParse.FieldTypeParam;

import java.nio.ByteBuffer;

/**
 * Created by PETER on 2015/2/5.
 */
public interface IFieldType {
    /**
     * 字段编码
     * @param value 输入值
     * @param fieldTypeParam 编码参数
     * @return
     * @throws Exception
     */
    byte[] encode(final Object value,final FieldTypeParam fieldTypeParam) throws Exception;

    /**
     *
     * @param byteBuffer 字节缓冲
     * @param fieldTypeParam 解码参数
     * @return
     * @throws Exception
     */
    Object decode(final ByteBuffer byteBuffer,final FieldTypeParam fieldTypeParam) throws Exception;

    /**
     * 重置有状态的解码
     */
    void reset();
}
