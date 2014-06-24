package com.skplanet.storeplatform.sac.display.download.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
import org.springframework.util.StopWatch;
import org.springframework.web.context.WebApplicationContext;

@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class DownloadControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mvc;

	@Before
	public void before() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	
	@Test
	public void getDownloadComicInfo() throws Exception {
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("deviceKey", "DE201403051349270180000124");
    	param.put("userKey", "US201403051349269830000552");
    	//param.put("productId", "H900481808"); //소장
    	param.put("productId", "H001497598"); //대여
    	String json = this.convertMapToJson(param);
    	
    	StopWatch stopWatch = new StopWatch(); 
    	stopWatch.start("getDownloadComicInfo_Comic 상품 정보 조회(for download)");
    	this.mvc.perform(
    			post("/display/download/comic/detail/v1")
    			.accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
    			.header("x-sac-device-info", "model=\"SHW-M110S\", dpi=\"320\", resolution=\"480*720\", os=\"Android/4.0.4\", pkg=\"sac.store.skplanet.com/37\", svc=\"SAC_Client/4.3\"")
    			.header("x-sac-network-info", "operator=\"unknown/unknown\", simOperator=\"450/05\", type=\"wifi\"")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(json))
    			.andDo(print())
    			.andExpect(status().isOk())
    			.andExpect(content().contentType("application/json;charset=UTF-8"));
    	
    	stopWatch.stop();
    	System.out.println(stopWatch.prettyPrint());
	}
	
	
	@Test
	public void getDownloadEbookInfo() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("deviceKey", "DE201403051349270180000124");
		param.put("userKey", "US201403051349269830000552");
		param.put("productId", "H001540016");
		param.put("idType", "episode");
		String json = this.convertMapToJson(param);
		
		StopWatch stopWatch = new StopWatch(); 
		stopWatch.start("getDownloadEbookInfo_ebook 상품 정보 조회(for download)");
		this.mvc.perform(
				post("/display/download/epub/detail/v1")
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
				.header("x-sac-device-info", "model=\"SHW-M110S\", dpi=\"320\", resolution=\"480*720\", os=\"Android/4.0.4\", pkg=\"sac.store.skplanet.com/37\", svc=\"SAC_Client/4.3\"")
				.header("x-sac-network-info", "operator=\"unknown/unknown\", simOperator=\"450/05\", type=\"wifi\"")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"));
		
		stopWatch.stop();
		System.out.println(stopWatch.prettyPrint());
	}


	private String convertMapToJson(Map<String, Object> param)
			throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper
				.setSerializationInclusion(JsonSerialize.Inclusion.NON_DEFAULT);
		return objectMapper.writeValueAsString(param);
	}
	
}
