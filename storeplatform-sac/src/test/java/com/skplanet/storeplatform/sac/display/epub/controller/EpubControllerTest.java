package com.skplanet.storeplatform.sac.display.epub.controller;

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
import org.mockito.Mock;
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
public class EpubControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mvc;

	@Before
	public void before() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	@Test
	public void searchEpubChannel_시리즈_CT20_1() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("channelId", "H000400481"); //태양의 전설 바람의 노래
		param.put("deviceKey", "DE201402201711283140002222");
		param.put("userKey", "");
		String json = this.convertMapToJson(param);
		
		this.mvc.perform(
				post("/display/epub/channel/detail/v1")
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
				.header("x-sac-device-info", "model=\"SHW-M110S\", dpi=\"320\", resolution=\"480*720\", os=\"Android/4.0.4\", pkg=\"sac.store.skplanet.com/37\", svc=\"SAC_Client/4.3\"")
				.header("x-sac-network-info", "operator=\"unknown/unknown\", simOperator=\"450/05\", type=\"wifi\"")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"));
	}

	@Test
	public void searchEpubChannel_ebook_시리즈_CT20_2() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("channelId", "H001254069");
		param.put("deviceKey", "DE201402201711283140002222");
		param.put("userKey", "US201402201711282940003170");
		String json = this.convertMapToJson(param);
		
		this.mvc.perform(
				post("/display/epub/channel/detail/v1")
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
				.header("x-sac-device-info", "model=\"SHW-M110S\", dpi=\"320\", resolution=\"480*720\", os=\"Android/4.0.4\", pkg=\"sac.store.skplanet.com/37\", svc=\"SAC_Client/4.3\"")
				.header("x-sac-network-info", "operator=\"unknown/unknown\", simOperator=\"450/05\", type=\"wifi\"")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"));
	}
	
	@Test
	public void searchEpubChannel_ebook_단편_CT19() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("channelId", "H000494432"); // H000400481
		param.put("deviceKey", "DE201402201711283140002222");
		param.put("userKey", "US201402201711282940003170");
		String json = this.convertMapToJson(param);
		
		StopWatch stopWatch = new StopWatch(); 
    	stopWatch.start("searchEpubChannel_ebook_단편_CT19");
		this.mvc.perform(
				post("/display/epub/channel/detail/v1")
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
	public void searchEpubChannel_ebook_freeitem() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("channelId", "H000494432"); // 무료 아이템 확인
		param.put("deviceKey", "DE201402201711283140002222");
		param.put("userKey", "US201402201711282940003170");
		String json = this.convertMapToJson(param);
		
		StopWatch stopWatch = new StopWatch(); 
		stopWatch.start("searchEpubChannel_ebook");
		this.mvc.perform(
				post("/display/epub/channel/detail/v1")
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
	public void searchEpubChannel_ebook() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("channelId", "H000400481");
		param.put("deviceKey", "DE201402201711283140002222");
		param.put("userKey", "US201402201711282940003170");
		String json = this.convertMapToJson(param);

		StopWatch stopWatch = new StopWatch(); 
    	stopWatch.start("searchEpubChannel_ebook");
		this.mvc.perform(
				post("/display/epub/channel/detail/v1")
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
	public void searchEpubSeries_코믹시리즈_CT20() throws Exception {
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("channelId", "H000494432"); /*[세트 10~20%할인]열혈강호(1~60권 미완결)*/
		param.put("deviceKey", "DE201402201711283140002222");
		param.put("userKey", "US201402201711282940003170");
		//param.put("baseChapter", 10);
		param.put("orderedBy", "recent");
		param.put("offset", "1");
		param.put("count", "10");
		String json = this.convertMapToJson(param);
		
		StopWatch stopWatch = new StopWatch(); 
		stopWatch.start("searchEpubSeries_ebook_이북시리즈_CT20_1");
		this.mvc.perform(
				post("/display/epub/series/list/v1")
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
	public void searchEpubSeries_ebook_이북시리즈_CT20_baseChapter() throws Exception {
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("channelId", "H001553068");
		//param.put("bookTypeCd", "DP004301");
		param.put("deviceKey", "DE201402201711283140002222");
		param.put("userKey", "US201402201711282940003170");
		param.put("baseChapter", 10);
		param.put("orderedBy", "recent");
		param.put("offset", "1");
		param.put("count", "10");
		String json = this.convertMapToJson(param);
		
		StopWatch stopWatch = new StopWatch(); 
		stopWatch.start("searchEpubSeries_ebook_이북시리즈_CT20_1");
		this.mvc.perform(
				post("/display/epub/series/list/v1")
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
    public void searchEpubSeries_ebook_이북시리즈_CT20_1() throws Exception {
    	
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("channelId", "H000494432"); //H001553068
    	//param.put("bookTypeCd", "DP004301");
    	param.put("deviceKey", "DE201402201711283140002222");
    	param.put("userKey", "US201402201711282940003170");
    	param.put("orderedBy", "recent");
    	param.put("offset", "1");
    	param.put("count", "10");
    	String json = this.convertMapToJson(param);
    	
    	StopWatch stopWatch = new StopWatch(); 
    	stopWatch.start("searchEpubSeries_ebook_이북시리즈_CT20_1");
    	this.mvc.perform(
    			post("/display/epub/series/list/v1")
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
    public void searchEpubSeries_ebook_이북시리즈_CT20_2() throws Exception {
    	
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("channelId", "H001553068");
    	//param.put("bookTypeCd", "DP004301");
    	//param.put("deviceKey", "DE201402201711283140002222");
    	param.put("userKey", "US201402201711282940003170");
    	param.put("orderedBy", "recent");
    	param.put("offset", "1");
    	param.put("count", "10");
    	String json = this.convertMapToJson(param);
    	
    	StopWatch stopWatch = new StopWatch(); 
    	stopWatch.start("searchEpubSeries_ebook_이북시리즈_CT20_1");
    	this.mvc.perform(
    			post("/display/epub/series/list/v1")
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
    public void searchEpubSeries_ebook_이북시리즈_CT20_3() throws Exception {
    	
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("channelId", "H001553068");
    	//param.put("bookTypeCd", "DP004301");
    	//param.put("deviceKey", "DE201402201711283140002222");
    	//param.put("userKey", "US201402201711282940003170");
    	param.put("orderedBy", "recent");
    	param.put("offset", "1");
    	param.put("count", "10");
    	String json = this.convertMapToJson(param);
    	
    	StopWatch stopWatch = new StopWatch(); 
    	stopWatch.start("searchEpubSeries_ebook_이북시리즈_CT20_1");
    	this.mvc.perform(
    			post("/display/epub/series/list/v1")
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
    public void searchEpubSeries_ebook_이북시리즈_CT20_4() throws Exception {
    	
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("channelId", "H001553068");
    	//param.put("bookTypeCd", "DP004301");
    	//param.put("deviceKey", "DE201402201711283140002222");
    	//param.put("userKey", "US201402201711282940003170");
    	//param.put("orderedBy", "recent");
    	param.put("offset", "1");
    	param.put("count", "10");
    	String json = this.convertMapToJson(param);
    	
    	StopWatch stopWatch = new StopWatch(); 
    	stopWatch.start("searchEpubSeries_ebook_이북시리즈_CT20_1");
    	this.mvc.perform(
    			post("/display/epub/series/list/v1")
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
    public void searchEpubSeries_ebook_이북시리즈_CT20_5_bookTypeCd_DP004301() throws Exception {
    	
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("channelId", "H002618570");
    	param.put("bookTypeCd", "DP004302");
    	param.put("deviceKey", "DE201402201711283140002222");
    	param.put("userKey", "US201402201711282940003170");
    	param.put("orderedBy", "recent");
    	param.put("offset", "1");
    	param.put("count", "10");
    	String json = this.convertMapToJson(param);
    	
    	StopWatch stopWatch = new StopWatch(); 
    	stopWatch.start("searchEpubSeries_ebook_이북시리즈_CT20_1");
    	this.mvc.perform(
    			post("/display/epub/series/list/v1")
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
    public void searchEpubSeries_ebook_이북시리즈_CT20_6_bookTypeCd_DP004302() throws Exception {
    	
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("channelId", "H001553068");
    	param.put("bookTypeCd", "DP004302");
    	//param.put("deviceKey", "DE201402201711283140002222");
    	//param.put("userKey", "US201402201711282940003170");
    	//param.put("orderedBy", "recent");
    	param.put("offset", "1");
    	param.put("count", "10");
    	String json = this.convertMapToJson(param);
    	
    	StopWatch stopWatch = new StopWatch(); 
    	stopWatch.start("searchEpubSeries_ebook_이북시리즈_CT20_1");
    	this.mvc.perform(
    			post("/display/epub/series/list/v1")
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
    public void searchEpubSeries_ebook_이북시리즈_PlayStore동시제공() throws Exception {
    	
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("channelId", "H001251381"); //묵향
    	param.put("deviceKey", "DE201402201711283140002222");
    	param.put("userKey", "US201402201711282940003170");
    	param.put("orderedBy", "recent");
    	param.put("offset", "1");
    	param.put("count", "10");
    	String json = this.convertMapToJson(param);
    	
    	StopWatch stopWatch = new StopWatch(); 
    	stopWatch.start("searchEpubSeries_ebook_이북시리즈_CT20_1");
    	this.mvc.perform(
    			post("/display/epub/series/list/v1")
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
    
    public void searchEpubSeries_ebook() throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("channelId", "H000400481");
        //param.put("bookTypeCd", "DP004301");
        param.put("deviceKey", "DE201402201711283140002222");
        param.put("userKey", "US201402201711282940003170");
        param.put("orderedBy", "recent");
        param.put("offset", "1");
        param.put("count", "10");
        String json = this.convertMapToJson(param);

        this.mvc.perform(
                post("/display/epub/series/list/v1")
                        .accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
                        .header("x-sac-device-info", "model=\"SHW-M110S\", dpi=\"320\", resolution=\"480*720\", os=\"Android/4.0.4\", pkg=\"sac.store.skplanet.com/37\", svc=\"SAC_Client/4.3\"")
                        .header("x-sac-network-info", "operator=\"unknown/unknown\", simOperator=\"450/05\", type=\"wifi\"")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }
    

    @Test
    public void searchEpubChannel_comic() throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("channelId", "H900058721");
        param.put("deviceKey", "DE201402201711283140002222");
        param.put("userKey", "US201402201711282940003170");
        String json = this.convertMapToJson(param);

        this.mvc.perform(
                post("/display/epub/channel/detail/v1")
                        .accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
                        .header("x-sac-device-info", "model=\"SHW-M110S\", dpi=\"320\", resolution=\"480*720\", os=\"Android/4.0.4\", pkg=\"sac.store.skplanet.com/37\", svc=\"SAC_Client/4.3\"")
                        .header("x-sac-network-info", "operator=\"unknown/unknown\", simOperator=\"450/05\", type=\"wifi\"")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    @Test
    public void searchEpubSeries() throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("channelId", "H000044572");
        param.put("deviceKey", "DE201402201711283140002222");
        param.put("userKey", "US201402201711282940003170");
        param.put("orderedBy", "recent");
        param.put("offset", "1");
        param.put("count", "10");
        String json = this.convertMapToJson(param);

        this.mvc.perform(
                post("/display/epub/series/list/v1")
                        .accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
                        // TODO: Header 추가
                        .header("x-sac-device-info", "model=\"SHV-E110S\", dpi=\"320\", resolution=\"480*720\", os=\"Android/4.0.4\", pkgVersion=\"sac.store.skplanet.com/37\"")
                        .header("x-sac-network-info", "operator=\"unknown/unknown\", simOperator=\"450/05\", type=\"wifi\"")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

	private String convertMapToJson(Map<String, Object> param)
			throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper
				.setSerializationInclusion(JsonSerialize.Inclusion.NON_DEFAULT);
		return objectMapper.writeValueAsString(param);
	}
	
    @Test
    public void searchEpubSeries_ebook_이북_nonPayment() throws Exception {
    	
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("channelId", "H001251381"); //H001553068
    	//param.put("bookTypeCd", "DP004301");
    	param.put("deviceKey", "DE201402201711283140002222");
    	param.put("userKey", "US201402201711282940003170");
    	param.put("orderedBy", "nonPayment");
    	param.put("offset", "1");
    	param.put("count", "2");
    	String json = this.convertMapToJson(param);
    	
    	StopWatch stopWatch = new StopWatch(); 
    	stopWatch.start("searchEpubSeries_ebook_이북시리즈_CT20_1");
    	this.mvc.perform(
    			post("/display/epub/series/list/v1")
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
}
