package com.skplanet.storeplatform.sac.display.feature.recommend.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
public class RecommendControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mvc;

	@Before
	public void before() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	
	@Test
	public void searchWebtoonList() throws Exception {
		//2.4.4.웹툰 상품 조회-웹툰 상품 조회 - 장르별
		this.mvc.perform(
				get("/display/feature/recommend/webtoonList/v1?menuId=DP26001&offset=1&count=100")
					.accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
					.header("x-sac-device-info", "model=\"SHW-M110S\", dpi=\"320\", resolution=\"480*720\", os=\"Android/4.0.4\", pkg=\"sac.store.skplanet.com/37\", svc=\"SAC_Client/4.3\"")
					.header("x-sac-network-info", "operator=\"unknown/unknown\", simOperator=\"450/05\", type=\"wifi\"")
		)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(header().string("x-sac-result-code", "SUCC"))
		;
	}

	
}
