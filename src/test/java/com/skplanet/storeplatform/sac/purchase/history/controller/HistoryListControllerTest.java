package com.skplanet.storeplatform.sac.purchase.history.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryListSacReq;

@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class HistoryListControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mvc;

	@Before
	public void before() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void searchHistoryListTest() throws Exception {

		HistoryListSacReq request = new HistoryListSacReq();

		request.setTenantId("S01");
		request.setModel("SHW-M110S");
		request.setUserKey("US201402040504554230001487");
		request.setDeviceKey("DE201402040504557980000851");
		request.setStartDt("20000101000000");
		request.setEndDt("99991231235959");
		request.setPrchsProdHaveYn("Y");
		request.setOffset(1);
		request.setCount(10);

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(request);

		this.mvc.perform(
				post("/purchase/history/list/v1").accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
						.contentType(MediaType.parseMediaType("application/json;charset=UTF-8")).content(json))
				.andDo(print()).andExpect(status().isOk());
	}

}
