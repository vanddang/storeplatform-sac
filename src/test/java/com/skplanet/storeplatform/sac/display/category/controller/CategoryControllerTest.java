package com.skplanet.storeplatform.sac.display.category.controller;

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
public class CategoryControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mvc;

	@Before
	public void before() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	
	//@Test
	public void searchWebtoonSeriesList() throws Exception {
		//2.4.4.웹툰 상품 조회-요일별
		this.mvc.perform(
				get("/display/category/webtoon/series/list/v1?weekDayCd=DP010101&offset=1&count=2")
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
	
	/**
	 * [I03000020] 2.4.1.4. 특정 상품 music 조회.
	 * @throws Exception
	 */
	@Test
	public void searchSpecificMusicList() throws Exception {
		//2.4.1.4. 특정 상품 music 조회.
		this.mvc.perform(
				get("/display/category/specific/music/list/v1?list=H001617943+H000400186+H001601609")
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
