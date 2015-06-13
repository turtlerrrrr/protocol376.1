package fieldType.base;

import com.sx.frontend.protocal376_1.internal.ConfigParse.FieldTypeParam;
import com.sx.frontend.protocal376_1.internal.fieldType.BcdUnsigned;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import java.nio.ByteBuffer;

import static org.junit.Assert.assertEquals;

/**
 * Created by PETER on 2015/2/28.
 */
public class BcdUnsignedTest{
    private BcdUnsigned bcdUnsigned=new BcdUnsigned();
    @Test
    public void testEncode() throws Exception {
        String value="111.1";
        FieldTypeParam fieldTypeParam=new FieldTypeParam();
        fieldTypeParam.setDegree(0.1);
        fieldTypeParam.setLength(2);
        byte[] out=bcdUnsigned.encode(value,fieldTypeParam);
//        System.out.print(Hex.encodeHex(out));
    }


    @Test
    public void testDecode() throws Exception {
        FieldTypeParam fieldTypeParam=new FieldTypeParam();
        fieldTypeParam.setDegree(0.1);
        fieldTypeParam.setLength(2);
        byte[] value=Hex.decodeHex(new char[]{'9','8','7','1'});
        assertEquals("719.8",bcdUnsigned.decode(ByteBuffer.wrap(value), fieldTypeParam));
    }
}
