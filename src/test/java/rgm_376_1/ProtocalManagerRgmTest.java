package rgm_376_1;

import com.sx.frontend.protocal376_1.BinPacket;
import com.sx.frontend.protocal376_1.IProtocal;
import com.sx.frontend.protocal376_1.Packet;
import com.sx.frontend.protocal376_1.ProtocalManagerFactory;
import org.apache.commons.codec.binary.Hex;
import org.junit.Before;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by PETER on 2015/2/6.
 */
public class ProtocalManagerRgmTest {
    private IProtocal protocalManagerRgm;
    @Before
    public void load() throws Exception{
        protocalManagerRgm= ProtocalManagerFactory.getProtocalManager("rgm_376_1");
    }

    /**
     * 测试参数设置
     * @throws Exception
     */
    //测试漏保
    @Test
    public void testDecodeProtectorParam() throws Exception {
        String packet = "68 12 01 12 01 68 4A 01 91 29 06 66 0A 70" //头
                + "00 00 02 01 "   //pn fn
                + "02 00"          //参数数量
                + "01 00 01 00 41 2A 11 11 11 11 11 11 C7 01 BD DE 19 00 00 C0 33 33 33 33 33 33 00 " //配置1
                + "02 00 02 00 42 2A 22 22 22 22 22 22 8E 03 7A BD 33 00 00 C0 55 55 55 55 55 55 00 " //配置2
                + "26 16"; //密码 结尾
        packet=packet.replace(" ", "");
        ByteBuffer buffor = ByteBuffer.wrap(Hex.decodeHex(packet.toCharArray()));
        Packet rgmPacket=new Packet();
        protocalManagerRgm.decode(buffor,rgmPacket);
        System.out.print(rgmPacket);
    }

    @Test
    public void testEncodeProtectorParam() throws Exception {
        String packet = "68 52 01 52 01 68 4A 01 91 29 06 66 04 70" //头
                + "00 00 02 01 "   //pn fn
                + "02 00"          //参数数量
                + "01 00 05 00 41 2A 11 11 11 11 11 11 C7 01 BD DE 19 00 00 C0 33 33 33 33 33 33 00 " //配置1
                + "02 00 02 00 42 2A 22 22 22 22 22 22 8E 03 7A BD 33 00 00 C0 55 55 55 55 55 55 00 " //配置2
                + "F2 16"; //密码 结尾
        packet=packet.replace(" ", "");
        ByteBuffer buffor = ByteBuffer.wrap(Hex.decodeHex(packet.toCharArray()));
        Packet rgmPacket=new Packet();
        int result=protocalManagerRgm.decode(buffor,rgmPacket);
        BinPacket binPacket =new BinPacket();
        Map lb=(Map)((List)rgmPacket.getData().get("list")).get(0);
        lb.put("protectorIndex","1");
        protocalManagerRgm.encode(rgmPacket, binPacket);
        System.out.print(Hex.encodeHex(binPacket.getByteBuffer().array()));
    }


    /**
     * 测试
     */
    @Test
    public void testDecodeBreakerConnect() throws Exception{
        String packet="6877007700684a121239302c05600100010014000000000000000000000000000000007e16";
        ByteBuffer buffor = ByteBuffer.wrap(Hex.decodeHex(packet.toCharArray()));
        Packet rgmPacket=new Packet();
        protocalManagerRgm.decode(buffor, rgmPacket);
        System.out.print(rgmPacket);

    }

    @Test
    public void testEncodeBreakerConnect() throws Exception {
        Packet packet =new Packet();
        Map<String,Object> data=new HashMap<>();
        data.put("delayTime","20");
        packet.setCommand("control-breakerDisconnect");
        packet.setData(data);
        packet.setTerminalAddress("12112345");
        packet.setLine(1);
        BinPacket binPacket =new BinPacket();
        protocalManagerRgm.encode(packet, binPacket);
        System.out.print(Hex.encodeHex(binPacket.getByteBuffer().array()));
    }

    @Test
    public void testEncodeTryBreakerConnect() throws Exception {
        Packet packet =new Packet();
        Map<String,Object> data=new HashMap<>();
        data.put("delayTime1","30");
        data.put("delayTime2","30");
        data.put("delayTime3","30");
        packet.setCommand("control-breakerTrydisconnect");
        packet.setData(data);
        packet.setTerminalAddress("91910004");
        packet.setLine(1);
        BinPacket binPacket =new BinPacket();
        protocalManagerRgm.encode(packet, binPacket);
        System.out.print(Hex.encodeHex(binPacket.getByteBuffer().array()));
    }

    @Test
    public void testConfigLoad(){
        System.out.print(protocalManagerRgm.getCodecConfig());
    }



}
