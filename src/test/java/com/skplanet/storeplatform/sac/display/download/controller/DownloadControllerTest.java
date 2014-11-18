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
	public void getDownloadAppInfo() throws Exception {
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("deviceKey", "DE201403051349270180000124");
    	param.put("userKey", "US201403051349269830000552");
    	param.put("productId", "0000412421");
    	param.put("filteredBy", "id");
    	String json = this.convertMapToJson(param);
    	
    	StopWatch stopWatch = new StopWatch(); 
    	stopWatch.start("getDownloadComicInfo_Download 앱 정보 조회(for download)");
    	this.mvc.perform(
    			post("/display/download/app/detail/v1")
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
	
	@Test
	public void downloadVodV2() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		/*
{
  "idType":"episode",
  "productId":"H002796738",
  "userKey": "IW1313866820140408161311",
  "deviceKey": "DE2014040816131582315039820"
}
		 */
		param.put("deviceKey", "DE2014040816131582315039820");
		param.put("userKey", "IW1313866820140408161311");
		param.put("productId", "H002796738");
		param.put("idType", "episode");
		String json = this.convertMapToJson(param);
		
		StopWatch stopWatch = new StopWatch(); 
		stopWatch.start("(V2) downloadVod Download Vod 정보 조회(for download)");
		this.mvc.perform(
				post("/display/download/vod/detail/v2")
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
	public void downloadVod() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		/*
{
  "idType":"episode",
  "productId":"H000044711",
   "userKey":"US201403051403029340000827",
   "deviceKey":"DE201403051403029520000402"
}
		 */
		param.put("deviceKey", "DE201403051403029520000402");
		param.put("userKey", "US201403051403029340000827");
		param.put("productId", "H000044711");
		param.put("idType", "episode");
		String json = this.convertMapToJson(param);
		
		StopWatch stopWatch = new StopWatch(); 
		stopWatch.start("downloadVod Download Vod 정보 조회(for download)");
		this.mvc.perform(
				post("/display/download/vod/detail/v1")
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
	public void getDownloadMusicInfo() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
/*
{
"deviceKey":"DE201403051349417010000130",
"userKey":"US201403051349416650000558",
"productId":"H900380891"
}
 */
		param.put("deviceKey", "DE201403051349270180000124");
		param.put("userKey", "US201403051349269830000552");
		param.put("productId", "H900380891");
		String json = this.convertMapToJson(param);
		
		StopWatch stopWatch = new StopWatch(); 
		stopWatch.start("getDownloadMusicInfo");
		this.mvc.perform(
				post("/display/download/music/detail/v1")
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
