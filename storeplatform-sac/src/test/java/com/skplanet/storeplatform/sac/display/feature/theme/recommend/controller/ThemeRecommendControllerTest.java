package com.skplanet.storeplatform.sac.display.feature.theme.recommend.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
public class ThemeRecommendControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mvc;

	@Before
	public void before() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	/**
	 * [I03000058] 2.8.2. 테마 추천 –상황별 추천
	 * <pre>
	 * method 설명.
	 * </pre>
	 * @throws Exception
	 */
	@Test
	public void searchThemeRecommendList() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("filteredBy", "long");
		param.put("userKey", "IW1313866820140408161311");
		param.put("msisdn", "11");
		String json = this.convertMapToJson(param);
		//[I03000058] 2.8.2. 테마 추천 –상황별 추천
		this.mvc.perform(
				post("/display/feature/theme/recommend/list/v1")
					.accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
					.header("x-sac-device-info", "model=\"SHW-M110S\", dpi=\"320\", resolution=\"480*720\", os=\"Android/4.0.4\", pkg=\"sac.store.skplanet.com/37\", svc=\"SAC_Client/4.3\"")
					.header("x-sac-network-info", "operator=\"unknown/unknown\", simOperator=\"450/05\", type=\"wifi\"")
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)
		)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(header().string("x-sac-result-code", "SUCC"))
		;
	}
	
	private String convertMapToJson(Map<String, Object> param) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_DEFAULT);
		return objectMapper.writeValueAsString(param);
	}
	
}
