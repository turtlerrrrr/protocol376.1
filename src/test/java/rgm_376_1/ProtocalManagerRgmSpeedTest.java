package rgm_376_1;

import com.sx.frontend.protocal376_1.BinPacket;
import com.sx.frontend.protocal376_1.IProtocal;
import com.sx.frontend.protocal376_1.Packet;
import com.sx.frontend.protocal376_1.ProtocalManagerFactory;
import org.apache.commons.codec.binary.Hex;
import org.junit.Before;
import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * Created by PETER on 2015/3/14.
 */
public class ProtocalManagerRgmSpeedTest {
    private IProtocal protocalManagerRgm;
    @Before
    public void load() throws Exception{
        protocalManagerRgm=ProtocalManagerFactory.getProtocalManager("rgm_376_1");
    }
    @Test
    public void testSingleThread() throws Exception {
        String packet = "68 12 01 12 01 68 4A 01 91 29 06 66 0A 70" //头
                + "00 00 02 01 "   //pn fn
                + "02 00"          //参数数量
                + "01 00 01 00 41 2A 11 11 11 11 11 11 C7 01 BD DE 19 00 00 C0 33 33 33 33 33 33 00 " //配置1
                + "02 00 02 00 42 2A 22 22 22 22 22 22 8E 03 7A BD 33 00 00 C0 55 55 55 55 55 55 00 " //配置2
                + "26 16"; //密码 结尾
        packet=packet.replace(" ", "");
        ByteBuffer buffor = ByteBuffer.wrap(Hex.decodeHex(packet.toCharArray()));
        long begin=System.currentTimeMillis();
        for(int i=0;i<480000;i++){
            Packet rgmPacket=new Packet();
            protocalManagerRgm.decode(buffor,rgmPacket);
            BinPacket binPacket =new BinPacket();
            protocalManagerRgm.encode(rgmPacket, binPacket);
        }
        long end=System.currentTimeMillis();
        System.out.print("计算时间="+((end-begin)/1000)+"秒");

    }
}
