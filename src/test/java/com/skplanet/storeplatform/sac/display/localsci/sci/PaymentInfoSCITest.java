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

	/**
	 * <pre>
	 * 결제 시 필요한 In-App 상품 메타 정보 조회.
	 * </pre>
	 * 
	 */
	@Test
	public void searchInAppPaymentInfo() {
        PaymentInfoSacReq req = new PaymentInfoSacReq();
        List<String> prodIdList = new ArrayList<String>();
        prodIdList.add("0900062560");
        prodIdList.add("0000048502");
        req.setProdIdList(prodIdList);
        req.setTenantId("S01");
        req.setLangCd("ko");
        req.setDeviceModelCd("SHW-M100S");

        PaymentInfoSacRes res = this.paymentInfoSCI.searchPaymentInfo(req);
        List<PaymentInfo> paymentInfoList = res.getPaymentInfoList();
        printResult(paymentInfoList);
	}

	/**
	 * <pre>
	 * 결제 시 필요한 APP 상품 메타 정보 조회.
	 * </pre>
	 * 
	 */
	@Test
	public void searchAppPaymentInfo() {
        PaymentInfoSacReq req = new PaymentInfoSacReq();
        List<String> prodIdList = new ArrayList<String>();
        prodIdList.add("0000060161");
        // prodIdList.add("0000024009");
        // prodIdList.add("0000023890");
        req.setProdIdList(prodIdList);
        req.setTenantId("S01");
        req.setLangCd("ko");
        // req.setDeviceModelCd("SHW-M100S");

        PaymentInfoSacRes res = this.paymentInfoSCI.searchPaymentInfo(req);
        List<PaymentInfo> paymentInfoList = res.getPaymentInfoList();
        printResult(paymentInfoList);
	}

	/**
	 * <pre>
	 * 결제 시 필요한 eBook 상품 메타 정보 조회.
	 * </pre>
	 * 
	 */
	@Test
	public void searchEbookPaymentInfo() {
        PaymentInfoSacReq req = new PaymentInfoSacReq();
        List<String> prodIdList = new ArrayList<String>();
        prodIdList.add("H001493615"); // 이북
        req.setProdIdList(prodIdList);
        req.setTenantId("S01");
        req.setLangCd("ko");
        // req.setDeviceModelCd("SHW-M100S");

        PaymentInfoSacRes res = this.paymentInfoSCI.searchPaymentInfo(req);
        List<PaymentInfo> paymentInfoList = res.getPaymentInfoList();
        printResult(paymentInfoList);
	}

	/**
	 * <pre>
	 * 결제 시 필요한 쇼핑 상품 메타 정보 조회.
	 * </pre>
	 * 
	 */
	@Test
	public void searchShoppingPaymentInfo() {
        PaymentInfoSacReq req = new PaymentInfoSacReq();
        List<String> prodIdList = new ArrayList<String>();
        prodIdList.add("S930000331");
        // prodIdList.add("S900000579");
        req.setProdIdList(prodIdList);
        req.setTenantId("S01");
        req.setLangCd("ko");
        req.setDeviceModelCd("SHW-M100S");

        PaymentInfoSacRes res = this.paymentInfoSCI.searchPaymentInfo(req);
        List<PaymentInfo> paymentInfoList = res.getPaymentInfoList();
        printResult(paymentInfoList);
	}

	/**
	 * <pre>
	 * 결제 시 필요한 정액권 상품 메타 정보 조회.
	 * </pre>
	 * 
	 */
	@Test
	public void searchFreePassPaymentInfo() {
        PaymentInfoSacReq req = new PaymentInfoSacReq();
        List<String> prodIdList = new ArrayList<String>();
        prodIdList.add("F901000639");
        // prodIdList.add("H000043062");
        // prodIdList.add("H000043063");
        req.setProdIdList(prodIdList);
        req.setTenantId("S01");
        req.setLangCd("ko");
        req.setDeviceModelCd("SHW-M100S");

        PaymentInfoSacRes res = this.paymentInfoSCI.searchPaymentInfo(req);
        List<PaymentInfo> paymentInfoList = res.getPaymentInfoList();
        printResult(paymentInfoList);
    }

    @Test
    public void integratedTest() {
        String[] prodIds = new String[]{"0000657169", "0900000141", "H000400088","H000043059","H000043062","H001727890","H002725517","H001929394","S900027675", "H002643572"};
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
