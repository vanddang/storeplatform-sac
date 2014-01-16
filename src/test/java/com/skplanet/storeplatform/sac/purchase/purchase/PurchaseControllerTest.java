package com.skplanet.storeplatform.sac.purchase.purchase;

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

import com.skplanet.storeplatform.sac.client.purchase.vo.purchase.CreatePurchaseReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.purchase.PurchaseProduct;

@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class PurchaseControllerTest {
	@Autowired
	private WebApplicationContext wac;

	private MockMvc mvc;

	@Before
	public void before() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void testFreePurchase() throws Exception {
		CreatePurchaseReq req = new CreatePurchaseReq();

		req.setTenantId("S01"); // 테넌트 ID
		req.setInsdUsermbrNo("TEST_MBR_NO_1"); // 내부 회원 번호
		req.setInsdDeviceId("1"); // 내부 디바이스 ID
		req.setPrchsReqPathCd("OR000401"); // 구매 요청 경로 코드
		req.setCurrencyCd("ko"); // 통화 코드
		req.setTotAmt(0.0);
		req.setClientIp("127.0.0.1"); // 클라이언트 IP
		req.setNetworkTypeCd("DP004401"); // 네트워크 타입 코드

		List<PurchaseProduct> productList = new ArrayList<PurchaseProduct>();
		productList.add(new PurchaseProduct("0000044819", 0.0, 1, "GRP-1"));
		productList.add(new PurchaseProduct("0000044820", 0.0, 1, "GRP-1"));
		req.setProductList(productList);

		ObjectMapper mapper = new ObjectMapper();
		String reqJsonStr = mapper.writeValueAsString(req);

		this.mvc.perform(
				post("/purchase/purchase/create/v1").content(reqJsonStr)
						.contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
						.accept(MediaType.parseMediaType("application/json;charset=UTF-8"))).andDo(print())
				.andExpect(content().contentType("application/json;charset=UTF-8")).andExpect(status().isOk());
	}
}
