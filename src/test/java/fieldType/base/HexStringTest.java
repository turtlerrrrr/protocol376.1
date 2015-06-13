package fieldType.base;


import com.sx.frontend.protocal376_1.internal.ConfigParse.FieldTypeParam;
import com.sx.frontend.protocal376_1.internal.fieldType.HexString;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import java.nio.ByteBuffer;

import static org.junit.Assert.assertEquals;

/**
 * Created by PETER on 2015/2/27.
 */
public class HexStringTest {
    private static HexString hexString=new HexString();

    @Test
    public void testEncode() throws Exception {
        String value="12ab34cd56ef";
        FieldTypeParam fieldTypeParam=new FieldTypeParam();
        fieldTypeParam.setLength(6);
        byte[] result=hexString.encode(value,fieldTypeParam);
        assertEquals("ef56cd34ab12",new String(Hex.encodeHex(result)));
    }

    @Test
    public void testDecode() throws Exception {
        byte[] result=new byte[]{(byte)0xff,0x01,0x05,0x0f};
        FieldTypeParam fieldTypeParam=new FieldTypeParam();
        fieldTypeParam.setLength(4);
        assertEquals("0f0501ff",hexString.decode(ByteBuffer.wrap(result), fieldTypeParam));
    }
}
