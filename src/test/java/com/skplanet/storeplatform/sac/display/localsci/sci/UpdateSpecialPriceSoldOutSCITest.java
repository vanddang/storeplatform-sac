package com.skplanet.storeplatform.sac.display.localsci.sci;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.UpdateSpecialPriceSoldOutSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.SpecialPriceSoldOutReq;

/**
 * 쇼핑 특가 상품에 대한 품절 여부(Sold Out) 등록
 * 
 * Updated on : 2014. 12. 04. Updated by : 김형식
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class UpdateSpecialPriceSoldOutSCITest {

	@Autowired
	private UpdateSpecialPriceSoldOutSCI updateSpecialPriceSoldOutSCI;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Test
	public void testSearchFreePassProductList() {
		SpecialPriceSoldOutReq req = new SpecialPriceSoldOutReq();
		req.setProductId("S930001011");
		req.setLang("ko");
		req.setTenantId("S01");

		this.updateSpecialPriceSoldOutSCI.updateSpecialPriceSoldOut(req);
	}
}
