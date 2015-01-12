package com.skplanet.storeplatform.sac.display.localsci.sci;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.UpdatePurchaseCountSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.UpdatePurchaseCountSacReq;

/**
 * 
 * ProductInfo SCI Test.
 * 
 * 특가 상품 구매 건수 업데이트
 * 
 * Updated on : 2014. 2. 28. Updated by : 김형식 , 지티소프트
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class UpdatePurchaseCountSCITest_SpecialProd {

	@Autowired
	private UpdatePurchaseCountSCI updatePurchaseCountSCI;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * Test method for
	 * {@link com.skplanet.storeplatform.sac.display.localsci.sci.ProductInfoSCITest#testSearchFreePassProductList()}.
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@Test
	public void testUpdatePurchaseCountSCI() throws JsonGenerationException, JsonMappingException, IOException {
		List<UpdatePurchaseCountSacReq> listReq = new ArrayList<UpdatePurchaseCountSacReq>();

		UpdatePurchaseCountSacReq updatePurchaseCountSacReq = new UpdatePurchaseCountSacReq();
		updatePurchaseCountSacReq.setProductId("S900000717"); // SH
		updatePurchaseCountSacReq.setPurchaseCount(1);
		updatePurchaseCountSacReq.setSpcYn("Y");
		updatePurchaseCountSacReq.setPurchaseDate(DateUtil.getToday("yyyyMMdd"));
		updatePurchaseCountSacReq.setTenantId("S01");
		listReq.add(updatePurchaseCountSacReq);
		this.updatePurchaseCountSCI.updatePurchaseCount(listReq);
	}
}
