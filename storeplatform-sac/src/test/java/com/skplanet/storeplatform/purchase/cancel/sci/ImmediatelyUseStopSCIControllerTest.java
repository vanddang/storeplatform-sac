package com.skplanet.storeplatform.purchase.cancel.sci;

import com.skplanet.storeplatform.purchase.client.cancel.sci.ImmediatelyUseStopSCI;
import com.skplanet.storeplatform.purchase.client.cancel.vo.ImmediatelyUseStopScReq;
import com.skplanet.storeplatform.purchase.client.cancel.vo.ImmediatelyUseStopScRes;
import com.skplanet.storeplatform.purchase.client.common.vo.PrchsDtl;
import com.skplanet.storeplatform.purchase.constant.PurchaseCDConstants;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.CollectionUtils;

import java.util.List;

@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/purchase/spring-test/context-test.xml" })
public class ImmediatelyUseStopSCIControllerTest extends TestCase {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ImmediatelyUseStopSCI immediatelyUseStopSCI;

    @Test
    public void testSearchPrchsDtl() throws Exception {
        //given
        ImmediatelyUseStopScReq scReq = new ImmediatelyUseStopScReq();
        scReq.setTenantId("S01");
        scReq.setPrchsId("15121416410301010607");
        scReq.setUserKey(null);
        scReq.setAdminId("admin");
        scReq.setDrawbackAmt("0");
        scReq.setReasonCd("");
        scReq.setReasonMsg("");
        scReq.setReqPathCd(PurchaseCDConstants.PRCHS_REQ_PATH_ADMIN);

        //when
        List<PrchsDtl> prchsDtlList = immediatelyUseStopSCI.searchPrchsDtl(scReq);

        //then
        assertTrue(!CollectionUtils.isEmpty(prchsDtlList));
    }

    @Test
    public void testSearchPrchsDtl_not_exists() throws Exception {
        //given
        ImmediatelyUseStopScReq scReq = new ImmediatelyUseStopScReq();
        scReq.setTenantId("S01");
        scReq.setPrchsId("123123123");
        scReq.setUserKey(null);
        scReq.setAdminId("admin");
        scReq.setDrawbackAmt("0");
        scReq.setReasonCd("");
        scReq.setReasonMsg("");
        scReq.setReqPathCd(PurchaseCDConstants.PRCHS_REQ_PATH_ADMIN);

        //when
        List<PrchsDtl> prchsDtlList = immediatelyUseStopSCI.searchPrchsDtl(scReq);

        //then
        assertTrue(CollectionUtils.isEmpty(prchsDtlList));
    }

    @Test
    public void testUpdateUseStop() throws Exception {
        //given
        ImmediatelyUseStopScReq scReq = new ImmediatelyUseStopScReq();
        scReq.setTenantId("S01");
        scReq.setPrchsId("15121416410301010607");
        scReq.setReqPathCd("OR000400");
        scReq.setUserKey("US201512141640463420030806");
        scReq.setAdminId("admin_tests");
        scReq.setDrawbackAmt("1500");
        scReq.setReasonCd("OR040400");
        scReq.setReasonMsg("테스트테스트 우리 테스트입니다.");
        scReq.setDwldStartDt("20151214164103");
        scReq.setDwldExprDt("99991231235959");

        //when
        ImmediatelyUseStopScRes scRes = immediatelyUseStopSCI.updateUseStop(scReq);

        //then
        assertNotNull(scRes.getPrchsId());
    }
}