package rgm_376_1;

import com.sx.frontend.protocal376_1.IProtocal;
import com.sx.frontend.protocal376_1.ProtocalManagerFactory;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by PETER on 2015/3/17.
 */
public class ProtocalManagerRgmTestInquire {
    private IProtocal protocalManagerRgm;
    @Before
    public void load() throws Exception{
        protocalManagerRgm= ProtocalManagerFactory.getProtocalManager("rgm_376_1");
    }
    @Test
    public void testGetRemark() throws Exception {
        System.out.print(protocalManagerRgm.getFieldRemark("getResidualCurrentProtectorConfig","configNum"));
        System.out.print("\n");
        System.out.print(protocalManagerRgm.getFieldRemark("getResidualCurrentProtectorConfig","password"));
        System.out.print("\n");
        System.out.print(protocalManagerRgm.getFieldRemark("control-synchronizeTime","week"));
        System.out.print("\n");
    }

    @Test
    public void testGetUnit() throws Exception {
        System.out.print(protocalManagerRgm.getFieldUnit("ratedCurrentConfig","ratedCurrentConfig"));
        System.out.print("\n");
        System.out.print(protocalManagerRgm.getFieldUnit("leakage_protection_of_residual_current_curve","residualCurrent"));
        System.out.print("\n");
    }

    @Test
    public void testGetValueDesc() throws Exception {
        System.out.print(protocalManagerRgm.getValueDescription("residualCurrentProtectorConfig","speed",5));
        System.out.print("\n");
    }

    @Test
    public void testGetValueDescList() throws Exception {
        System.out.print(protocalManagerRgm.getValueDescriptionList("residualCurrentProtectorConfig", "speed"));
        System.out.print("\n");
    }

    @Test
    public void testGetDataList() throws Exception {
        System.out.print(protocalManagerRgm.getDataList());
        System.out.print("\n");
    }

    @Test
    public void testGetEventList() throws Exception {
        System.out.print(protocalManagerRgm.getEventList());
        System.out.print("\n");
    }

    @Test
    public void testGetTemplate() throws Exception {
        System.out.print(protocalManagerRgm.getDataTemplate("control-breakerConnect"));
        System.out.print("\n");
        System.out.print(protocalManagerRgm.getDataTemplate("setMsIp"));
        System.out.print("\n");
    }


}
