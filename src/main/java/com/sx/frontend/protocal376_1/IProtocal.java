package com.sx.frontend.protocal376_1;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

/**
 * Created by PETER on 2015/2/5.
 */
public interface IProtocal {
    /**
     * 规约解码
     * @param in
     * @param out
     * @return
     * 返回值0，解码失败，抛出异常
     * 返回值1，解码成功
     * 返回值2，帧序号不一致
     * 返回值4，返回帧有时间标识，并且该报文已超时
     * 返回值8，需等待后续帧
     * 返回值16,需要回复确认帧
     * 返回值32,主动上传帧
     * 返回值64，有事件上报
     * 返回值128，该报文是广播报文
     *
     */
    int decode(final ByteBuffer in,Packet out) throws Exception;

    /**
     * 规约编码
     * @param in
     * @param out
     * @return
     * 返回值0，编码失败，抛出异常
     * 返回值1，编码成功
     * 返回值2，需等待确认帧
     * 返回值4, 需要等待回复
     */
    int encode(Packet in,BinPacket out) throws Exception;

    /**
     * 获取解码配置
     * @return
     */

    CodecConfig getCodecConfig();

    /**
     * 获取字段的描述
     * @param field 字段名
     * @return 字段的中文描述
     */
    String getFieldRemark(String command,String field);

    /**
     * 获取字段的单位
     * @param field 字段名
     * @return 字段的单位
     */
    String getFieldUnit(String command,String field);

    /**
     * 获取数据的描述
     * @param field 字段名
     * @param value 字段值
     * @return 数据的中文描述
     */
    String getValueDescription(String command,String field, Integer value);

    /**
     * 获取字段的可枚举列表
     * @param field 字段名
     * @return 枚举列表
     */
    Map<Integer,String> getValueDescriptionList(String command,String field);

    /**
     * 获取支持数据项列表
     * @return 所有数据项名称
     */
    List<String> getDataList();

    /**
     * 获取支持事件列表
     * @return
     */
    List<String> getEventList();

    /**
     * 获取数据项的输入流模板
     * @param command 数据项名称
     * @return 输入流模板
     */
    String getDataTemplate(String command);
}
