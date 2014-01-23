package com.skplanet.storeplatform.sac.purchase.payment.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class CyberMoneyControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mvc;

	@Before
	public void before() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void testGetBalance() throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> map1 = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		Map<String, Object> map3 = new HashMap<String, Object>();

		map1.put("testList1", "1");
		map1.put("testList2", "2");
		map1.put("testList3", "3");
		map2.put("testList1", "1");
		map2.put("testList2", "2");
		map2.put("testList3", "3");
		map3.put("testList1", "1");
		map3.put("testList2", "2");
		map3.put("testList3", "3");

		list.add(map1);
		list.add(map2);
		list.add(map3);

		map.put("testVal1", "1");
		map.put("testVal2", "2");
		map.put("testVal3", "3");

		map.put("list", list);

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(map);

		this.mvc.perform(
				post("/purchase/payment/cyberMoney/get/v1?name=testName&id=123&pw=456")
						.accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
						.contentType(MediaType.parseMediaType("application/json;charset=UTF-8")).content(json))
				.andDo(print()).andExpect(status().isOk());
		/*
		 * .andExpect(content().contentType("application/json;charset=UTF-8")) .andExpect(jsonPath("$.id").value("#17"))
		 */
	}
}
