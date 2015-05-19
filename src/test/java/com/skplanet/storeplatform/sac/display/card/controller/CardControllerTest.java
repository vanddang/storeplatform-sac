/**
 *
 */
package com.skplanet.storeplatform.sac.display.card.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.codehaus.jackson.map.ObjectMapper;
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

import com.skplanet.storeplatform.sac.client.display.vo.card.CardDetailSacReq;

/**
 * 카드 테스트
 *
 * Updated on : 2015. 5. 18.
 * Updated by : 1002159
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class CardControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mvc;

	@Before
	public void before() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void basic() throws Exception {

		CardDetailSacReq req = new CardDetailSacReq();

		req.setId( "CRD0000001" );

		String postBody = new ObjectMapper().writeValueAsString(req);

		this.mvc.perform(
			post( "/display/card/detail/v1" )
				.contentType(MediaType.APPLICATION_JSON)
				.content(postBody)
				.header("x-sac-tenant-id", "S01")
				.header("x-sac-system-id", "S01-01014")
		)
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json;charset=UTF-8"))
		.andExpect(header().string("x-sac-result-code", "SUCC"))
		;
	}

}
