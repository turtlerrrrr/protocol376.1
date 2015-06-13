package fieldType.base;

import com.sx.frontend.protocal376_1.internal.ConfigParse.FieldTypeParam;
import com.sx.frontend.protocal376_1.internal.fieldType.Unsigned16;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import java.nio.ByteBuffer;

import static org.junit.Assert.assertEquals;

/**
 * Created by PETER on 2015/2/28.
 */
public class Unsigened16Test {
    private Unsigned16 unsigned16=new Unsigned16();
    @Test
    public void testEncode() throws Exception {
        FieldTypeParam fieldTypeParam=new FieldTypeParam();
        fieldTypeParam.setLength(8);
        ByteBuffer b=ByteBuffer.allocate(10);
        b.put(unsigned16.encode("153",fieldTypeParam));
        b.put(unsigned16.encode("153",fieldTypeParam));
        b.put(unsigned16.encode("153",fieldTypeParam));
        b.put(unsigned16.encode("153",fieldTypeParam));
        b.position(0);
        byte[] out=new byte[4];
        b.get(out);
        assertEquals("99999999",new String(Hex.encodeHex(out)));
    }

    @Test
    public void testDecode() throws Exception {
        FieldTypeParam fieldTypeParam=new FieldTypeParam();
        fieldTypeParam.setLength(8);
        byte[] value=Hex.decodeHex(new char[]{'9','8','7','a','1','2','9','f'});
        ByteBuffer b=ByteBuffer.wrap(value);
        assertEquals("152", unsigned16.decode(b, fieldTypeParam));
        assertEquals("122", unsigned16.decode(b,fieldTypeParam));
        assertEquals("18",unsigned16.decode(b,fieldTypeParam));
        assertEquals("159", unsigned16.decode(b,fieldTypeParam));

    }
}
