package rgm_376_1;

import com.sx.frontend.protocal376_1.BinPacket;
import com.sx.frontend.protocal376_1.IProtocal;
import com.sx.frontend.protocal376_1.Packet;
import com.sx.frontend.protocal376_1.ProtocalManagerFactory;
import org.apache.commons.codec.binary.Hex;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2015-04-14.
 */
public class ProtocalManagerRgmTest04 {
    private IProtocal protocalManagerRgm;
    @Before
    public void load() throws Exception{
        protocalManagerRgm= ProtocalManagerFactory.getProtocalManager("rgm_376_1");
    }
    @Test
    public void testp1() throws Exception {
        Packet rgmPacket=new Packet();
        Map<String,Object> dMap = new HashMap<String,Object>();
        dMap.put("reSwitchOn", "1");
        dMap.put("gearReturn", "1");
        dMap.put("timingSwitch", "1");
        dMap.put("alertSound", "1");
        dMap.put("alertLight", "1");
        dMap.put("dataAlert", "1");
        rgmPacket.setLine(2);
        rgmPacket.setCommand("setControlParam1");
        rgmPacket.setTerminalAddress("00010001");
        rgmPacket.setData(dMap);
        BinPacket binPacket=new BinPacket();
        protocalManagerRgm.encode(rgmPacket,binPacket);
        System.out.print(Hex.encodeHex(binPacket.getByteBuffer().array()));
    }
}
