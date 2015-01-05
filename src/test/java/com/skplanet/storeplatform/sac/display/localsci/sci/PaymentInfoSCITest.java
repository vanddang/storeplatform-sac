package com.skplanet.storeplatform.sac.display.localsci.sci;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.PaymentInfoSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PaymentInfo;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PaymentInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PaymentInfoSacRes;

/**
 * 
 * PaymentInfo SCI Test
 * 
 * 결제 시 필요한 상품 메타 정보 조회
 * 
 * Updated on : 2014. 2. 27. Updated by : 홍지호, 엔텔스
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@Transactional
public class PaymentInfoSCITest {

	@Autowired
	private PaymentInfoSCI paymentInfoSCI;

	private final Logger log = LoggerFactory.getLogger(this.getClass());




    @Test
    public void integratedTest() {
        String[] prodIds = new String[]{"H000044182"};
        for (String prodId : prodIds) {
            invokeApi(prodId);
        }
    }

    private void invokeApi(String prodId) {
        PaymentInfoSacReq req = new PaymentInfoSacReq();
        List<String> prodIdList = new ArrayList<String>();
        prodIdList.add(prodId);
        req.setProdIdList(prodIdList);
        req.setTenantId("S01");
        req.setLangCd("ko");
        req.setDeviceModelCd("SHW-M100S");

        PaymentInfoSacRes res = this.paymentInfoSCI.searchPaymentInfo(req);
        List<PaymentInfo> paymentInfoList = res.getPaymentInfoList();
        printResult(paymentInfoList);
    }

    private void printResult(List<PaymentInfo> list) {
        for (PaymentInfo info : list) {
            this.log.debug("##### searchPaymentInfo paymentInfo : {}", info.toString());
        }
    }

}
