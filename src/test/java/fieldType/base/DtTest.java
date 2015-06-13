package fieldType.base;

import com.sx.frontend.protocal376_1.internal.ConfigParse.FieldTypeParam;
import com.sx.frontend.protocal376_1.internal.fieldType.Dt;
import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * Created by PETER on 2015/3/4.
 */
public class DtTest {
    Dt dt=new Dt();
    @Test
    public void testEncode() throws Exception {


    }
    @Test
    public void testDecode() throws Exception {
        byte[] a=new byte[]{(byte) 01,0};
        ByteBuffer byteBuffer=ByteBuffer.wrap(a);
        System.out.print(dt.decode(byteBuffer, new FieldTypeParam()));

    }
}
