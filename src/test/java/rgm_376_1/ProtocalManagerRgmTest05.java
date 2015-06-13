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
 * Created by PETER on 2015/3/13.
 */
public class ProtocalManagerRgmTest05 {
    private IProtocal protocalManagerRgm;
    @Before
    public void load() throws Exception{
        protocalManagerRgm= ProtocalManagerFactory.getProtocalManager("rgm_376_1");
    }
    @Test
    public void testDecodeSynchronizeTime() throws Exception {
        String packet = "68 6A 00 6A 00 68 4A 10 13 39 30 F6 05 F1 00 00" //头
                + "40 03 55 30 10 12 83 15 60 04 01 55 30 10 12 00 "   //pn fn
                + "50 16";
        packet=packet.replace(" ", "");
        ByteBuffer buffor = ByteBuffer.wrap(Hex.decodeHex(packet.toCharArray()));
        Packet rgmPacket=new Packet();
        int result=protocalManagerRgm.decode(buffor,rgmPacket);
        System.out.print(rgmPacket);
    }

    @Test
    public void testEncodeSynchronizeTime() throws Exception {
        String packet = "68 6A 00 6A 00 68 4A 10 13 39 30 F6 05 F1 00 00" //头
                + "40 03 55 30 10 12 83 15 60 04 01 55 30 10 12 00 "   //pn fn
                + "50 16";
        packet=packet.replace(" ", "");
        ByteBuffer buffor = ByteBuffer.wrap(Hex.decodeHex(packet.toCharArray()));
        Packet rgmPacket=new Packet();
        int result=protocalManagerRgm.decode(buffor,rgmPacket);
        BinPacket binPacket =new BinPacket();
        protocalManagerRgm.encode(rgmPacket, binPacket);
        System.out.print(Hex.encodeHex(binPacket.getByteBuffer().array()));

    }
}
