package fieldType.base;

import com.sx.frontend.protocal376_1.internal.ConfigParse.FieldTypeParam;
import com.sx.frontend.protocal376_1.internal.fieldType.Unsigned8;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import java.nio.ByteBuffer;

import static org.junit.Assert.assertEquals;

/**
 * Created by PETER on 2015/2/28.
 */
public class Unsigned8Test {
    private Unsigned8 unsigned8=new Unsigned8();
    @Test
    public void testEncode() throws Exception {
        FieldTypeParam fieldTypeParam=new FieldTypeParam();
        fieldTypeParam.setLength(4);
        ByteBuffer b=ByteBuffer.allocate(10);
        b.put(unsigned8.encode("9",fieldTypeParam));
        b.put(unsigned8.encode("9",fieldTypeParam));
        b.put(unsigned8.encode("9",fieldTypeParam));
        b.put(unsigned8.encode("9",fieldTypeParam));
        b.position(0);
        byte[] out=new byte[2];
        b.get(out);
        assertEquals("9999",new String(Hex.encodeHex(out)));
    }

    @Test
    public void testDecode() throws Exception {
        FieldTypeParam fieldTypeParam=new FieldTypeParam();
        fieldTypeParam.setLength(4);
        byte[] value=Hex.decodeHex(new char[]{'9','8','7','a'});
        ByteBuffer b=ByteBuffer.wrap(value);
        assertEquals("8", unsigned8.decode(b, fieldTypeParam));
        assertEquals("9",unsigned8.decode(b,fieldTypeParam));
        assertEquals("10",unsigned8.decode(b,fieldTypeParam));
        assertEquals("7",unsigned8.decode(b,fieldTypeParam));

    }
}
