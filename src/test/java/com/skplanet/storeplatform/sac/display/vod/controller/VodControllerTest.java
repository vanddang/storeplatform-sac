package com.skplanet.storeplatform.sac.display.vod.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
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
	public void searchVodDetail_recent() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("channelId", "H000044250");
		param.put("orderedBy", "recent");
		param.put("offset", 1);
		param.put("count", 20);
		String json = this.convertMapToJson(param);

		this.mvc.perform(post("/display/vod/detail/v1")
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
				//TODO: Header 추가
				.header("x-sac-device-info", "model=\"SHW-M110S\", dpi=\"320\", resolution=\"480*720\", osVersion=\"Android/4.0.4\", pkgVersion=\"sac.store.skplanet.com/37\"")
				//.param("channelId", "H090108973").param("orderedBy", "recent").param("offset", "1").param("count", "20")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				)
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json;charset=UTF-8"))
		;
	}

	@Test
	public void searchVodDetail_regDate() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("channelId", "H000044250");
		param.put("orderedBy", "regDate");
		param.put("offset", 1);
		param.put("count", 20);
		String json = this.convertMapToJson(param);

		this.mvc.perform(post("/display/vod/detail/v1")
					.accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
					//TODO: Header 추가
					.header("x-sac-device-info", "model=\"SHW-M110S\", dpi=\"320\", resolution=\"480*720\", osVersion=\"Android/4.0.4\", pkgVersion=\"sac.store.skplanet.com/37\"")
					//.param("channelId", "H000044250").param("orderedBy", "regDate")
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)
				)
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json;charset=UTF-8"))
		;
	}

	@Test
	public void searchVodDetail_nonPayment() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("channelId", "H000044250");
		param.put("orderedBy", "nonPayment");
		param.put("offset", 1);
		param.put("count", 20);
		String json = this.convertMapToJson(param);


		this.mvc.perform(post("/display/vod/detail/v1")
					.accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
					//TODO: Header 추가
					.header("x-sac-device-info", "model=\"SHW-M110S\", dpi=\"320\", resolution=\"480*720\", osVersion=\"Android/4.0.4\", pkgVersion=\"sac.store.skplanet.com/37\"")
					.contentType(MediaType.APPLICATION_JSON)
					//.param("channelId", "H000044250").param("orderedBy", "nonPayment")
					.content(json)
				)
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json;charset=UTF-8"))
		;
	}

	private String convertMapToJson(Map<String, Object> param) throws IOException,
			JsonGenerationException, JsonMappingException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_DEFAULT);
		String json = objectMapper.writeValueAsString(param);
		return json;
	}
}
