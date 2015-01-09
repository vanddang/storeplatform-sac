package com.skplanet.storeplatform.sac.purchase.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.skplanet.storeplatform.external.client.tstore.vo.TStoreCashBalanceEcRes;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.purchase.cancel.vo.PurchaseCancelByUserSacReq;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PrchsDtlSacParam;

@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class PurchaseCancelControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mvc;

	@Before
	public void before() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void testPurchaseCancel() throws Exception {

		String temp = "20140602";
		temp = StringUtils.substring(temp, 0, 6);

		ObjectMapper mapper = new ObjectMapper();

		String testJson = "{\"cashList\" : [], \"id\" : \"testId\"}";

		TStoreCashBalanceEcRes tStoreCashBalanceEcRes = mapper.readValue(testJson, TStoreCashBalanceEcRes.class);

		String resvCol03 = "CASH=;POINT=1234";
		String TCashCash = StringUtils.substringBetween(resvCol03, "CASH=", ";");
		String TCashPoint = StringUtils.substringBetween(resvCol03, "POINT=", ";");

		List<PrchsDtlSacParam> list = null;
		for (PrchsDtlSacParam prchsDtlSacParam : list) {

		}

		PurchaseCancelByUserSacReq purchaseCancelByUserSacReq = new PurchaseCancelByUserSacReq();
		/*
		 * List<PurchaseCancel> list = new ArrayList<PurchaseCancel>();
		 * 
		 * PurchaseCancel purchaseCancel = new PurchaseCancel(); purchaseCancel.setPrchsId("1");
		 * purchaseCancel.setCancelReqPathCd("2");
		 */

		// list.add(purchaseCancel);

		// purchaseCancelReq.setPrchsList(list);

		String json = mapper.writeValueAsString(purchaseCancelByUserSacReq);

		this.mvc.perform(
				post("/purchase/cancel/v1").accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
						.contentType(MediaType.parseMediaType("application/json;charset=UTF-8")).content(json))
				.andDo(print()).andExpect(status().isOk());
		/*
		 * .andExpect(content().contentType("application/json;charset=UTF-8")) .andExpect(jsonPath("$.id").value("#17"))
		 */
	}

}
