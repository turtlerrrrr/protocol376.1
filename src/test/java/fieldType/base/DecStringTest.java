package fieldType.base;

import com.sx.frontend.protocal376_1.internal.ConfigParse.FieldTypeParam;
import com.sx.frontend.protocal376_1.internal.fieldType.DecString;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import java.nio.ByteBuffer;

import static org.junit.Assert.assertEquals;

/**
 * Created by PETER on 2015/2/28.
 */
public class DecStringTest {
    private static DecString decString=new DecString();

    @Test
    public void testEncode() throws Exception {
        String value="12345678";
        FieldTypeParam fieldTypeParam=new FieldTypeParam();
        fieldTypeParam.setLength(3);
        byte[] result=decString.encode(value,fieldTypeParam);
        assertEquals("4e61bc",new String(Hex.encodeHex(result)));
    }

    @Test
    public void testDecode() throws Exception {
        byte[] result=new byte[]{(byte)0xff,0x01,0x05,0x0f};
        FieldTypeParam fieldTypeParam=new FieldTypeParam();
        fieldTypeParam.setLength(4);
        assertEquals("251986431",decString.decode(ByteBuffer.wrap(result), fieldTypeParam));
    }
}
