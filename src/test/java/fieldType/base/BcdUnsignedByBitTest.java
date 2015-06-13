package fieldType.base;

import com.sx.frontend.protocal376_1.internal.ConfigParse.FieldTypeParam;
import com.sx.frontend.protocal376_1.internal.fieldType.BcdUnsignedByBit;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import java.nio.ByteBuffer;

import static org.junit.Assert.assertEquals;

/**
 * Created by PETER on 2015/3/12.
 */
public class BcdUnsignedByBitTest {
    private BcdUnsignedByBit bcdUnsignedByBit=new BcdUnsignedByBit();
    @Test
    public void testEncode() throws Exception {
        FieldTypeParam fieldTypeParam1=new FieldTypeParam();
        fieldTypeParam1.setLength(5);
        FieldTypeParam fieldTypeParam2=new FieldTypeParam();
        fieldTypeParam2.setLength(3);
        ByteBuffer b=ByteBuffer.allocate(10);
        b.put(bcdUnsignedByBit.encode("11",fieldTypeParam1));
        b.put(bcdUnsignedByBit.encode("6",fieldTypeParam2));
        b.position(0);
        byte[] out=new byte[1];
        b.get(out);
        assertEquals("d1",new String(Hex.encodeHex(out)));

    }
}
