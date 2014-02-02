package com.skplanet.storeplatform.sac.display.vod.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
public class VodControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mvc;

	@Before
	public void before() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void vodDetail_recent() throws Exception {
		this.mvc.perform(get("/display/vod/detail/v1")
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
				//TODO: Header 추가
					.header("x-sac-device-info", "model=\"SHW-M110S\", dpi=\"320\", resolution=\"480*720\", osVersion=\"Android/4.0.4\", pkgVersion=\"sac.store.skplanet.com/37")
				.param("channelId", "H090107970").param("orderedBy", "recent")
				)
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json;charset=UTF-8"))
		;
	}

	@Test
	public void vodDetail_regDate() throws Exception {
		this.mvc.perform(get("/display/vod/detail/v1")
					.accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
					//TODO: Header 추가
					.header("x-sac-device-info", "model=\"SHW-M110S\", dpi=\"320\", resolution=\"480*720\", osVersion=\"Android/4.0.4\", pkgVersion=\"sac.store.skplanet.com/37")
					.param("channelId", "H090107970").param("orderedBy", "regDate")
				)
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json;charset=UTF-8"))
		;
	}

	@Test
	public void vodDetail_nonPayment() throws Exception {
		this.mvc.perform(get("/display/vod/detail/v1")
					.accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
					//TODO: Header 추가
					.header("x-sac-device-info", "model=\"SHW-M110S\", dpi=\"320\", resolution=\"480*720\", osVersion=\"Android/4.0.4\", pkgVersion=\"sac.store.skplanet.com/37")
					.param("channelId", "H090107970").param("orderedBy", "nonPayment")
				)
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json;charset=UTF-8"))
		;
	}
}
