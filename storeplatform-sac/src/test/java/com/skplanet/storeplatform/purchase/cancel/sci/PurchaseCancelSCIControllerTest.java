package com.skplanet.storeplatform.purchase.cancel.sci;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.skplanet.storeplatform.purchase.client.cancel.sci.PurchaseCancelSCI;
import com.skplanet.storeplatform.purchase.client.cancel.vo.PurchaseScReq;
import com.skplanet.storeplatform.purchase.client.cancel.vo.PurchaseScRes;

@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/purchase/spring-test/context-test.xml" })
public class PurchaseCancelSCIControllerTest extends AbstractTransactionalJUnit4SpringContextTests {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PurchaseCancelSCI purchaseCancelSCI;

	/**
	 * 
	 * <pre>
	 * 테스트 데이터 세팅.
	 * </pre>
	 */
	@Before
	public void init() {

	}

	/**
	 * 
	 * <pre>
	 * 테스트 구매 상세 리스트 조회(구매 취소).
	 * </pre>
	 */
	@Test
	public void TestGetPurchaseDtlList() {

		PurchaseScReq purchaseDtlListScReq = new PurchaseScReq();

		purchaseDtlListScReq.setTenantId("S01");
		purchaseDtlListScReq.setSystemId("systemId123");
		purchaseDtlListScReq.setUserKey("userKey123");
		purchaseDtlListScReq.setDeviceKey("deviceKey123");
		purchaseDtlListScReq.setPrchsId("TAK100122117819361");

		PurchaseScRes purchaseDtlListScRes = this.purchaseCancelSCI.getPurchase(purchaseDtlListScReq);

		this.logger.debug("getPrchs result : {}", purchaseDtlListScRes.getPrchs());
		this.logger.debug("getPrchsDtlList result : {}", purchaseDtlListScRes.getPrchsDtlList());
		this.logger.debug("getPaymentList result : {}", purchaseDtlListScRes.getPaymentList());

	}

}
