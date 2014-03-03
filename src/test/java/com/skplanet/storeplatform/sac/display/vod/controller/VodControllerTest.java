package com.skplanet.storeplatform.sac.display.vod.controller;

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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
	public void searchVodDetail_영화_recent() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("channelId", "H900525605");
		param.put("deviceKey", "DE201402201711283140002222");
		param.put("userKey", "US201402201711282940003170");
		param.put("orderedBy", "recent");
		param.put("offset", 1);
		param.put("count", 20);
		String json = this.convertMapToJson(param);

		this.mvc.perform(post("/display/vod/detail/v1")
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
				.header("x-sac-device-info", "model=\"SHW-M110S\", dpi=\"320\", resolution=\"480*720\", os=\"Android/4.0.4\", pkg=\"sac.store.skplanet.com/37\", svc=\"SAC_Client/4.3\"")
				.header("x-sac-device-info", "operator=\"unknown/unknown\", simOperator=\"450/05\", type=\"wifi\"")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				)
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json;charset=UTF-8"))
		;
	}

	@Test
	public void searchVodDetail_방송_recent() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("channelId", "H900537521");
		param.put("deviceKey", "DE201402201711283140002222");
		param.put("userKey", "US201402201711282940003170");
		param.put("orderedBy", "recent");
		param.put("offset", 1);
		param.put("count", 20);
		String json = this.convertMapToJson(param);

		this.mvc.perform(post("/display/vod/detail/v1")
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
				//TODO: Header 추가
                .header("x-sac-device-info", "model=\"SHW-M110S\", dpi=\"320\", resolution=\"480*720\", os=\"Android/4.0.4\", pkg=\"sac.store.skplanet.com/37\", svc=\"SAC_Client/4.3\"")
                .header("x-sac-device-info", "operator=\"unknown/unknown\", simOperator=\"450/05\", type=\"wifi\"")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				)
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json;charset=UTF-8"))
		;
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
                .header("x-sac-device-info", "model=\"SHW-M110S\", dpi=\"320\", resolution=\"480*720\", os=\"Android/4.0.4\", pkg=\"sac.store.skplanet.com/37\", svc=\"SAC_Client/4.3\"")
                .header("x-sac-device-info", "operator=\"unknown/unknown\", simOperator=\"450/05\", type=\"wifi\"")
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
                    .header("x-sac-device-info", "model=\"SHW-M110S\", dpi=\"320\", resolution=\"480*720\", os=\"Android/4.0.4\", pkg=\"sac.store.skplanet.com/37\", svc=\"SAC_Client/4.3\"")
                    .header("x-sac-device-info", "operator=\"unknown/unknown\", simOperator=\"450/05\", type=\"wifi\"")
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
                    .header("x-sac-device-info", "model=\"SHW-M110S\", dpi=\"320\", resolution=\"480*720\", os=\"Android/4.0.4\", pkg=\"sac.store.skplanet.com/37\", svc=\"SAC_Client/4.3\"")
                    .header("x-sac-device-info", "operator=\"unknown/unknown\", simOperator=\"450/05\", type=\"wifi\"")
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)
				)
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json;charset=UTF-8"))
		;
	}

	private String convertMapToJson(Map<String, Object> param) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_DEFAULT);
		return objectMapper.writeValueAsString(param);
	}
}
