package rgm_376_1;

import com.google.gson.Gson;
import com.sx.frontend.protocal376_1.BinPacket;
import com.sx.frontend.protocal376_1.IProtocal;
import com.sx.frontend.protocal376_1.Packet;
import com.sx.frontend.protocal376_1.ProtocalManagerFactory;
import org.apache.commons.codec.binary.Hex;
import org.junit.Before;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by PETER on 2015/3/20.
 */
public class ProtocalManagerRgmTest040a {
    private IProtocal protocalManagerRgm;
    @Before
    public void load() throws Exception{
        protocalManagerRgm= ProtocalManagerFactory.getProtocalManager("rgm_376_1");
    }
    @Test
    public void testIp() throws Exception {
        //"686204620468cb00100100000d6e0201010a45121604150101eeeeee0201020a45121604150101eeeeee0201040a45121604150101eeeeee0201080a45121604150101eeeeee0201100a45121604150101eeeeee0201200a45121604150101eeeeee0201400a45121604150101eeeeee0201800a45121604150101eeeeee0201010b4512160415010100220201020b45121604150101eeee0201040b45121604150101eeee0201080b451216041501010015000201100b45121604150101eeeeee0201200b45121604150101eeeeee0201400b45121604150101eeeeee0201800b45121604150101eeee0201010d45121604150101eeee0201020d45121604150101eeee0201040d45121604150101eeee0201080d45121604150101eeee0516";
        //"684a004a006888001001002c00e1000001000119441016053016"
        String packet="686204620468cb00100100000d6e0201010a45121604150101eeeeee0201020a45121604150101eeeeee0201040a45121604150101eeeeee0201080a45121604150101eeeeee0201100a45121604150101eeeeee0201200a45121604150101eeeeee0201400a45121604150101eeeeee0201800a45121604150101eeeeee0201010b4512160415010100220201020b45121604150101eeee0201040b45121604150101eeee0201080b451216041501010015000201100b45121604150101eeeeee0201200b45121604150101eeeeee0201400b45121604150101eeeeee0201800b45121604150101eeee0201010d45121604150101eeee0201020d45121604150101eeee0201040d45121604150101eeee0201080d45121604150101eeee0516";
        ByteBuffer buffor = ByteBuffer.wrap(Hex.decodeHex(packet.toCharArray()));
        Packet rgmPacket=new Packet();
        int result=protocalManagerRgm.decode(buffor,rgmPacket);
//        System.out.print(result);
        System.out.print(new Gson().toJson(rgmPacket));

    }

    @Test
    public void getp1() throws Exception {
        Packet rgmPacket=new Packet();
        Map<String,Object> dMap = new HashMap<String,Object>();
        rgmPacket.setLine(2);
        rgmPacket.setCommand("getControlParam1");
        rgmPacket.setTerminalAddress("00010001");
        rgmPacket.setData(dMap);
        BinPacket binPacket=new BinPacket();
        protocalManagerRgm.encode(rgmPacket,binPacket);
        System.out.print(Hex.encodeHex(binPacket.getByteBuffer().array()));
    }

}
