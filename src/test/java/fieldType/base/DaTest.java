package fieldType.base;

import com.sx.frontend.protocal376_1.internal.ConfigParse.FieldTypeParam;
import com.sx.frontend.protocal376_1.internal.fieldType.Da;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * Created by PETER on 2015/2/6.
 */
public class DaTest {
    Da da=new Da();
    @Test
    public void testEncode() throws Exception {
        byte[] a=da.encode(2,new FieldTypeParam());
        System.out.print(Hex.encodeHex(a));
    }

    @Test
    public void testDecode() throws Exception {
        byte[] a=new byte[]{2,1};
        ByteBuffer byteBuffer=ByteBuffer.wrap(a);
        System.out.print(da.decode(byteBuffer, new FieldTypeParam()));
    }
}
