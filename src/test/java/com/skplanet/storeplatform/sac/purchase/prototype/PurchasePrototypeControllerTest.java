/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.prototype;

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

import com.skplanet.storeplatform.sac.client.purchase.vo.prototype.CheckPurchaseReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.prototype.MyPagePurchaseHistoryReq;

/**
 * 
 * [Prototype] 구매내역 컨트롤러 테스트
 * 
 * Updated on : 2014. 1. 16. Updated by : 이승택, nTels.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class PurchasePrototypeControllerTest {

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
	 * 
	 * <pre>
	 * 구매내역 조회 테스트.
	 * </pre>
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test
	public void searchPurchaseList() throws Exception {
		MyPagePurchaseHistoryReq req = new MyPagePurchaseHistoryReq();

		req.setTenantId("S01"); // 테넌트 ID
		// req.setMbrNo("IF102815948420090820103525");
		// req.setDeviceNo("01046353524");
		req.setMbrNo("MBR01");
		req.setDeviceNo("MBR01_1");
		req.setProdOwnType("mdn");
		req.setStartDt("20100101");
		req.setEndDt("20141231");
		req.setStartRow(1);
		req.setEndRow(100);

		ObjectMapper mapper = new ObjectMapper();
		String reqJsonStr = mapper.writeValueAsString(req);

		this.mvc.perform(
				post("/purchase/prototype/list/v1").content(reqJsonStr)
						.contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
						.accept(MediaType.parseMediaType("application/json;charset=UTF-8"))).andDo(print())
				.andExpect(content().contentType("application/json;charset=UTF-8")).andExpect(status().isOk());
	}

	/**
	 * 
	 * <pre>
	 * 기구매 체크 테스트.
	 * </pre>
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test
	public void checkPurchase() throws Exception {
		CheckPurchaseReq req = new CheckPurchaseReq();

		req.setTenantId("S01"); // 테넌트 ID
		req.setMbrNo("MBR01");
		req.setDeviceNo("MBR01_1");

		List<String> prodIdList = new ArrayList<String>();
		prodIdList.add("0000027381");
		prodIdList.add("0000122080");
		prodIdList.add("H900725970");
		prodIdList.add("0000413250");
		prodIdList.add("H000044541");
		prodIdList.add("H000040313");

		req.setProdIdList(prodIdList);

		ObjectMapper mapper = new ObjectMapper();
		String reqJsonStr = mapper.writeValueAsString(req);

		this.mvc.perform(
				post("/purchase/prototype/check/v1").content(reqJsonStr)
						.contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
						.accept(MediaType.parseMediaType("application/json;charset=UTF-8"))).andDo(print())
				.andExpect(content().contentType("application/json;charset=UTF-8")).andExpect(status().isOk());
	}
}
