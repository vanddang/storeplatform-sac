package com.skplanet.storeplatform.sac.purchase.cancel.service;

import com.skplanet.storeplatform.sac.client.purchase.cancel.vo.ImmediatelyUseStopSacReq;
import com.skplanet.storeplatform.sac.client.purchase.cancel.vo.ImmediatelyUseStopSacRes;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class ImmediatelyUseStopServiceImplTest extends TestCase {

    @Autowired
    private ImmediatelyUseStopService immediatelyUseStopService;

    @Test
    public void testUpdateUseStop_No_ReasonData() throws Exception {

        //given
        ImmediatelyUseStopSacReq sacReq = new ImmediatelyUseStopSacReq();
        sacReq.setTenantId("S01"); //
        sacReq.setPrchsId(""); //
        sacReq.setAdminId("local_test"); //
        sacReq.setUserKey(""); //
        sacReq.setReqPathCd("OR000400"); //
        sacReq.setReasonCd(null); // Optional
        sacReq.setReasonMsg(null); // Optional

        //when
        ImmediatelyUseStopSacRes sacRes = immediatelyUseStopService.updateUseStop(sacReq);

        //then
        assertNotNull(sacRes.getPrchsId());

    }

    @Test
    public void testUpdateUseStop_With_Reason() throws Exception {

        //given
        ImmediatelyUseStopSacReq sacReq = new ImmediatelyUseStopSacReq();
        sacReq.setTenantId("S01"); //
        sacReq.setPrchsId("15121415422001020563"); //
        sacReq.setAdminId("local_test"); //
        sacReq.setDrawbackAmt("1000"); //
        sacReq.setUserKey("US201512141540242380030798"); //  Optional..
        sacReq.setReqPathCd("OR000400"); //
        sacReq.setReasonCd("OR040400"); // Optional
        sacReq.setReasonMsg("local 테스트입니다."); // Optional

        //when
        ImmediatelyUseStopSacRes sacRes = immediatelyUseStopService.updateUseStop(sacReq);

        //then
        assertNotNull(sacRes.getPrchsId());

    }
}