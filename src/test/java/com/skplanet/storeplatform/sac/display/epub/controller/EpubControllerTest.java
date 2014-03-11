package com.skplanet.storeplatform.sac.display.epub.controller;

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
public class EpubControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mvc;

	@Before
	public void before() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void searchEpubChannel_ebook() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("channelId", "H900066655");
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
    public void searchEpubSeries_ebook() throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("channelId", "H900066685");
        param.put("bookTypeCd", "DP004301");
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
                        .header("x-sac-device-info", "model=\"SHV-E110S\", dpi=\"320\", resolution=\"480*720\", osVersion=\"Android/4.0.4\", pkgVersion=\"sac.store.skplanet.com/37\"")
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
}
