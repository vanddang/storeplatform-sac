package com.skplanet.storeplatform.sac.purchase.prototype;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.skplanet.storeplatform.purchase.client.prototype.vo.RequestPurchaseHistory;
import com.skplanet.storeplatform.sac.purchase.prototype.service.PurchasePrototypeService;

@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class PurchasePrototypeServiceImplTest {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PurchasePrototypeService purchasePrototypeService;

	/**
	 * 구매 목록 조회 테스트.
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test
	public void shouldObtainPurchaseList() throws Exception {
		RequestPurchaseHistory paramVO = new RequestPurchaseHistory();

		paramVO.setTenantId("S01");
		// paramVO.setMbrNo("IF102815948420090820103525");
		// paramVO.setDeviceNo("01046353524");
		paramVO.setMbrNo("MBR01");
		paramVO.setDeviceNo("MBR01_1");
		paramVO.setProdOwnType("mdn");
		paramVO.setStartDt("20100101");
		paramVO.setEndDt("20141231");
		paramVO.setStartRow(1);
		paramVO.setEndRow(100);

		this.purchasePrototypeService.searchPurchaseList(paramVO);

	}
}
