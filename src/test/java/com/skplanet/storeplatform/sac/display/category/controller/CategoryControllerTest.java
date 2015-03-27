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
	
	/**
	 * [I03000018] 2.4.1.2 특정 상품 EBOOK 조회
	 * @throws Exception
	 */
	@Test
	public void searchSpecificEbookList() throws Exception {
		this.mvc.perform(
				get("/display/category/specific/epub/list/v1?list=H900474171")
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
				.header("x-sac-device-info", "model=\"SHW-M110S\", dpi=\"320\", resolution=\"480*720\", os=\"Android/4.0.4\", pkg=\"sac.store.skplanet.com/37\", svc=\"SAC_Client/4.3\"")
				.header("x-sac-network-info", "operator=\"unknown/unknown\", simOperator=\"450/05\", type=\"wifi\"")
				.header("x-sac-interface-id", "I03000134")
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(header().string("x-sac-result-code", "SUCC"))
				;
	}
	
	/**
	 * 일반 카테고리 앱 상품 조회.
	 * @throws Exception
	 */
	@Test
	public void searchAppList() throws Exception {
		//2.4.1.4. 특정 상품 music 조회.
		this.mvc.perform(
				get("/display/category/specific/app/list/v1?list=0000415172")
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
				.header("x-sac-device-info", "model=\"SHW-M110S\", dpi=\"320\", resolution=\"480*720\", os=\"Android/4.0.4\", pkg=\"sac.store.skplanet.com/37\", svc=\"SAC_Client/4.3\"")
				.header("x-sac-network-info", "operator=\"unknown/unknown\", simOperator=\"450/05\", type=\"wifi\"")
				.header("x-sac-interface-id", "I03000134")
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(header().string("x-sac-result-code", "SUCC"))
				;
	}
	/**
	 * 특정 상품 Vod 조회.
	 * @throws Exception
	 */
	@Test
	public void searchSpecificVodList() throws Exception {
		//2.4.1.4. 특정 상품 music 조회.
		this.mvc.perform(
				get("/display/category/specific/vod/list/v1?list=H000043773")
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
	 * VOD 보관함
	 * @throws Exception
	 */
	@Test
	public void searchVodBoxList_방송() throws Exception {
		//		H102802804
		//		H102802811
		//		H102802840
		//		H102802825
		//		H102802847
		//		H102802863
		//		H102802873
		//		H102802877
		//		H102802880
		//		H102802883
		//		H102802887
		//		H102802891
		//		H102802894

		this.mvc.perform(
				get("/display/category/vodBox/list/v1?duration=30&filteredBy=duration&channelId=H102802804")
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
	
	@Test
	public void searchVodBoxList_영화() throws Exception {
		
		//		H102802792
		//		H102802805
		//		H102802812
		//		H102802816
		//		H102802822
		//		H102802827
		//		H102802831
		//		H102802837
		//		H102802841
		//		H102802848
		//		H102802852
		//		H102802859
		//		H102802862
		//		H102802867
		this.mvc.perform(
				get("/display/category/vodBox/list/v1?duration=30&filteredBy=duration&channelId=H102802883")
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
