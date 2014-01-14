package com.skplanet.storeplatform.sac.purchase.prototype;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.skplanet.storeplatform.purchase.client.prototype.vo.RequestPurchaseHistory;

@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class PurchasePrototypeControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mvc;

	@Before
	public void before() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void searchPurchaseList() throws Exception {
		RequestPurchaseHistory req = new RequestPurchaseHistory();

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
		System.out.println(reqJsonStr);

		this.mvc.perform(
				post("/purchase/prototype/list/v1").content(reqJsonStr)
						.contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
						.accept(MediaType.parseMediaType("application/json;charset=UTF-8"))).andDo(print())
				.andExpect(content().contentType("application/json;charset=UTF-8")).andExpect(status().isOk());
	}
}
