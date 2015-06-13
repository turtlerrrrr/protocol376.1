package rgm_376_1;

import com.sx.frontend.protocal376_1.BinPacket;
import com.sx.frontend.protocal376_1.IProtocal;
import com.sx.frontend.protocal376_1.Packet;
import com.sx.frontend.protocal376_1.ProtocalManagerFactory;
import org.apache.commons.codec.binary.Hex;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by PETER on 2015/3/20.
 */
public class ProtocalManagerRgmTest0d {
    private IProtocal protocalManagerRgm;
    @Before
    public void load() throws Exception{
        protocalManagerRgm= ProtocalManagerFactory.getProtocalManager("rgm_376_1");
    }
    @Test
    public void test0df81() throws Exception {
        Packet packet =new Packet();
        Map<String,Object> data=new HashMap<>();
        Map<String,Object> time=new HashMap<>();
        time.put("minute",15);
        time.put("minute",15);
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
    public void test0df10() throws Exception {
        Packet packet =new Packet();
        Map<String,Object> dMap = new HashMap<String,Object>();
        dMap.put("configNum", 5);
        List<Map> list=new ArrayList<Map>();
        for(int i=0;i<5;i++){
            Map m=new HashMap();
            m.put("index",i);
            list.add(m);
        }
        dMap.put("list", list);
        packet.setCommand("getResidualCurrentProtectorConfig");
        packet.setData(dMap);
        packet.setTerminalAddress("91910004");
        packet.setLine(1);
        BinPacket binPacket =new BinPacket();
        protocalManagerRgm.encode(packet, binPacket);
        System.out.print(Hex.encodeHex(binPacket.getByteBuffer().array()));

    }

    @Test
    public void testM() throws Exception {
        Packet packet =new Packet();
        Map<String,Object> commands=new HashMap<>();
        Map<String,Object> data=new HashMap<>();
        data.put("ts","15/4/16 12:45");
        data.put("frozenDensity","1");
        data.put("pointNumber","1");
        packet.setCommand("multipleCommand");
        commands.put("b_phase_measurement_of_power_factor_curve", data);
        commands.put("zero_phase_measurement_of_current_curve",data);
        commands.put("c_phase_measurement_of_current_curve",data);
        commands.put("b_phase_measured_power_curve",data);
        packet.setData(commands);
        packet.setTerminalAddress("91910004");
        packet.setLine(2);
        BinPacket binPacket =new BinPacket();
        protocalManagerRgm.encode(packet, binPacket);
        System.out.print(Hex.encodeHex(binPacket.getByteBuffer().array()));
    }

}
