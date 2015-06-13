package rgm_376_1;

import com.sx.frontend.protocal376_1.IProtocal;
import com.sx.frontend.protocal376_1.Packet;
import com.sx.frontend.protocal376_1.ProtocalManagerFactory;
import org.apache.commons.codec.binary.Hex;
import org.junit.Before;
import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * Created by PETER on 2015/3/31.
 */
public class ProtocalManagerRgmEvent {
    private IProtocal protocalManagerRgm;

    @Before
    public void load() throws Exception {
        protocalManagerRgm = ProtocalManagerFactory.getProtocalManager("rgm_376_1");
    }

    @Test
    public void test0df81() throws Exception {
        String packet = "68ca00ca0068c400100100000e6100000100140b131434203609150415020001020303030303034444111122223333444444555555666666b416";
        packet = packet.replace(" ", "");
        ByteBuffer buffor = ByteBuffer.wrap(Hex.decodeHex(packet.toCharArray()));
        Packet rgmPacket = new Packet();
        protocalManagerRgm.decode(buffor, rgmPacket);
        System.out.print(rgmPacket);
    }


}
