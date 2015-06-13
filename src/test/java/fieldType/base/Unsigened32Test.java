package fieldType.base;

import com.sx.frontend.protocal376_1.internal.ConfigParse.FieldTypeParam;
import com.sx.frontend.protocal376_1.internal.fieldType.Unsigned32;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import java.nio.ByteBuffer;

import static org.junit.Assert.assertEquals;

/**
 * Created by PETER on 2015/2/28.
 */
public class Unsigened32Test {
    private Unsigned32 unsigned32=new Unsigned32();

    @Test
    public void testEncode() throws Exception {
        FieldTypeParam fieldTypeParam=new FieldTypeParam();
        fieldTypeParam.setLength(16);
        ByteBuffer b=ByteBuffer.allocate(10);
        b.put(unsigned32.encode("39321",fieldTypeParam));
        b.put(unsigned32.encode("39321",fieldTypeParam));
        b.put(unsigned32.encode("39321",fieldTypeParam));
        b.put(unsigned32.encode("39321",fieldTypeParam));
        b.position(0);
        byte[] out=new byte[8];
        b.get(out);
        assertEquals("9999999999999999",new String(Hex.encodeHex(out)));
    }

    @Test
    public void testDecode() throws Exception {
        FieldTypeParam fieldTypeParam=new FieldTypeParam();
        fieldTypeParam.setLength(16);
        byte[] value=Hex.decodeHex(new char[]{'9','8','7','a','1','2','9','f','9','8','7','a','1','2','9','f'});
        ByteBuffer b=ByteBuffer.wrap(value);
        assertEquals("31384", unsigned32.decode(b, fieldTypeParam));
        assertEquals("40722", unsigned32.decode(b,fieldTypeParam));
        assertEquals("31384",unsigned32.decode(b,fieldTypeParam));
        assertEquals("40722", unsigned32.decode(b,fieldTypeParam));
    }
}
