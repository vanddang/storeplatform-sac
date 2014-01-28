/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.order;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreatePurchaseReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreatePurchaseReqProduct;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;

/**
 * 
 * 구매 처리 컨트롤러 테스트
 * 
 * Updated on : 2014. 1. 16. Updated by : 이승택, nTels.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class PurchaseOrderControllerTest {
	@Autowired
	private WebApplicationContext wac;

	private MockMvc mvc;

	/**
	 */
	@Before
	public void before() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	/**
	 * <pre>
	 * 무료상품 구매 테스트.
	 * </pre>
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test
	public void testFreePurchase() throws Exception {
		CreatePurchaseReq req = new CreatePurchaseReq();

		req.setInsdUsermbrNo("TEST_MBR_NO_1"); // 내부 회원 번호
		req.setInsdDeviceId("1"); // 내부 디바이스 ID
		req.setPrchsReqPathCd("OR000401"); // 구매 요청 경로 코드
		req.setPrchsCaseCd(PurchaseConstants.PRCHS_CASE_PURCHASE_CD); // 구매 유형 코드
		req.setCurrencyCd("ko"); // 통화 코드
		req.setTotAmt(0.0);
		req.setClientIp("127.0.0.1"); // 클라이언트 IP
		req.setNetworkTypeCd("DP004401"); // 네트워크 타입 코드
		req.setMid("MID01");
		req.setAuthKey("MID01_KEY01");
		req.setResultUrl("http://localhost:8080/tenant/completePurchase");

		List<CreatePurchaseReqProduct> productList = new ArrayList<CreatePurchaseReqProduct>();
		productList.add(new CreatePurchaseReqProduct("0000044819", "GRP-1", 0.0, 1));
		productList.add(new CreatePurchaseReqProduct("0000044820", "GRP-1", 0.0, 1));
		req.setProductList(productList);

		ObjectMapper mapper = new ObjectMapper();
		String reqJsonStr = mapper.writeValueAsString(req);

		this.mvc.perform(
				post("/purchase/order/create/v1").content(reqJsonStr)
						.contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
						.accept(MediaType.parseMediaType("application/json;charset=UTF-8"))).andDo(print())
				.andExpect(content().contentType("application/json;charset=UTF-8")).andExpect(status().isOk());
	}
}
