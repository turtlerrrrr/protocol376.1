package com.sx.frontend.protocal376_1.internal.wrap;

import com.sx.frontend.protocal376_1.CodecConfig;
import com.sx.frontend.protocal376_1.Packet;
import com.sx.frontend.protocal376_1.exception.EncodingException;
import com.sx.frontend.protocal376_1.exception.Preconditions;
import com.sx.frontend.protocal376_1.internal.ConfigParse.FieldGroup;
import com.sx.frontend.protocal376_1.internal.ConfigParse.FieldTypeParam;
import com.sx.frontend.protocal376_1.internal.ProtocalTemplate;
import com.sx.frontend.protocal376_1.internal.fieldType.Da;
import com.sx.frontend.protocal376_1.internal.fieldType.Dt;
import com.sx.frontend.protocal376_1.internal.fieldType.HexString;
import com.sx.frontend.protocal376_1.internal.packetSegment.Control;
import com.sx.frontend.protocal376_1.internal.packetSegment.Data;
import com.sx.frontend.protocal376_1.internal.packetSegment.PacketSegmentContext;
import com.sx.frontend.protocal376_1.internal.packetSegment.SegmentEnum;

import java.util.List;

/**
 * Created by PETER on 2015/3/24.
 */
public class ControlWrapper extends Wrapper{
    private HexString hexString=new HexString();
    private FieldTypeParam address1Param=new FieldTypeParam(2);
    private FieldTypeParam address2Param=new FieldTypeParam(2);
    private static final String MULTIPLECOMMAND="multipleCommand";


    /**
     * 设置参数值
     * @param in
     * @param control
     * @param protocalTemplate
     * @throws Exception
     */
    public void beforeEncode(Packet in,Control control,Data dataSeg,
                             ProtocalTemplate protocalTemplate,CodecConfig codecConfig) throws Exception{
        FieldGroup dataGroup=null;
        if(in.getCommand().equals(MULTIPLECOMMAND)){
            String command=in.getData().keySet().iterator().next();
            dataGroup=protocalTemplate.getDataMapByName().get(command);
        }else{
            dataGroup=protocalTemplate.getDataMapByName().get(in.getCommand());
        }

        if(dataGroup==null){
            throw new EncodingException(1111,"不支持命令号:"+in.getCommand());
        }

        int afn=dataGroup.getAfn();
        control.setAfn(afn);
        dataSeg.setFn(dataGroup.getFn());
        //线路设置
        if(in.getLine()==null){
            dataSeg.setPn(0);
        }else{
            dataSeg.setPn(in.getLine());
        }

        //设置是否需要确认
        if(afn==0x01 || afn==0x04 || afn==0x05 || afn==0x0F){
            control.setIsNeedConfirm(1);
        }else {
            control.setIsNeedConfirm(0);
        }

        //设置功能码
        switch(afn){
            case 0x00:
                control.setFunctionCode(0);
                break;
            case 0x01:
                control.setFunctionCode(1);
                break;
            case 0x02:
                control.setFunctionCode(9);
                break;
            case 0x04:
            case 0x05:
            case 0x06:
            case 0x0f:
                control.setFunctionCode(10);
                break;
            case 0x13:
                control.setFunctionCode(4);
                break;
            default:
                control.setFunctionCode(11);
        }

        //终端地址设置
        if(in.getTerminalAddress()==null){
            Preconditions.checkNotNull(codecConfig.getTerminalAddress(), 1111, "终端地址为空");
            control.setAddress1(codecConfig.getTerminalAddress().substring(0,4).toUpperCase());
            control.setAddress2(codecConfig.getTerminalAddress().substring(4).toUpperCase());
        }else{
            control.setAddress1(in.getTerminalAddress().substring(0, 4).toUpperCase());
            control.setAddress2(in.getTerminalAddress().substring(4).toUpperCase());
        }

        //设置广播地址
        if("FFFF".equals(control.getAddress1()) && "FFFF".equals(control.getAddress2())){
            control.setIsGroup(1);
        }else{
            control.setIsGroup(0);
        }
        //主站地址设置
        control.setMsa(codecConfig.getMsa());
        //关闭fcb
        control.setFcbOrAcd(0);
        //启动站
        control.setPrm(1);
        //下行报文
        control.setDir(0);
        //报文是第一帧
        control.setFin(1);
        //报文是最后一帧
        control.setFir(1);
        //是否有时间标识
        control.setTpV(codecConfig.isHaveTp()?1:0);

    }

    /**
     *
     * @param in
     * @param packetSegmentContext
     * @param protocalTemplate
     * @throws Exception
     */
    @Override
    public void encode(Packet in,PacketSegmentContext packetSegmentContext,
                       ProtocalTemplate protocalTemplate,CodecConfig codecConfig) throws Exception{
        Control control=(Control)packetSegmentContext.getSegment(SegmentEnum.control);
        Data dataSeg=(Data)packetSegmentContext.getSegment(SegmentEnum.data);
        beforeEncode(in, control, dataSeg, protocalTemplate, codecConfig);
        List<byte[]> buffer=control.getBuffer();
        //控制域功能码,帧计数有效位,fcb帧计数位,启动标志位,传输方向位 编码
        buffer.add(new byte[]{(byte) (control.getFunctionCode()+
                (control.getFcv()<<4)+(control.getFcbOrAcd()<<5)+(control.getPrm()<<6)+(control.getDir()<<7))});
        //地址编码
        address1Param.setLength(codecConfig.getDistinctLength());
        buffer.add(hexString.encode(control.getAddress1(),address1Param));
        address2Param.setLength(codecConfig.getTerminalAddressLength());
        buffer.add(hexString.encode(control.getAddress2(),address2Param));
        //主站地址，afn编码
        buffer.add(new byte[]{(byte) (control.getIsGroup()+(control.getMsa()<<1)),
                (byte)control.getAfn()});
        //帧序列，是否需确认，是否报文第一帧，是否报文最后一帧，附加信息是否有时间标签 编码
        buffer.add(new byte[]{(byte) ((control.getSeq()&0x0f)+(control.getIsNeedConfirm()<<4)+
                        (control.getFin()<<5)+(control.getFir()<<6)+(control.getTpV()<<7))});

        if(next!=null){
            next.encode(in,packetSegmentContext,protocalTemplate,codecConfig);
        }

    }

}
