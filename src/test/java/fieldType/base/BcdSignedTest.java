package fieldType.base;

import com.sx.frontend.protocal376_1.internal.ConfigParse.FieldTypeParam;
import com.sx.frontend.protocal376_1.internal.fieldType.BcdSigned;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import java.nio.ByteBuffer;

import static org.junit.Assert.assertEquals;

/**
 * Created by PETER on 2015/3/2.
 */
public class BcdSignedTest {
    private BcdSigned bcdSigned=new BcdSigned();
    @Test
    public void testEncode() throws Exception {
        String value="-123.4";
        FieldTypeParam fieldTypeParam=new FieldTypeParam();
        fieldTypeParam.setDegree(0.1);
        fieldTypeParam.setLength(2);
        byte[] out=bcdSigned.encode(value,fieldTypeParam);
        assertEquals("3492",new String(Hex.encodeHex(out)));
    }


    @Test
    public void testDecode() throws Exception {
        FieldTypeParam fieldTypeParam=new FieldTypeParam();
        fieldTypeParam.setDegree(0.1);
        fieldTypeParam.setLength(2);
        byte[] value=Hex.decodeHex(new char[]{'3','4','9','2'});
        assertEquals("-123.4",bcdSigned.decode(ByteBuffer.wrap(value), fieldTypeParam));
    }
}
