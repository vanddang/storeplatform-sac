/**
 *
 */
package com.skplanet.storeplatform.sac.display.menu.controller;

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

/**
 * IntegratedMenuControllerTest
 *
 * Updated on : 2015. 5. 18.
 * Updated by : 1002159
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class IntegratedMenuControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mvc;

	@Before
	public void before() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void tstore4Category() throws Exception {

//		String useGrdCd = "useGrdCd=PD004405";
		String useGrdCd = "";

		this.mvc.perform(
			get( String.format("/display/menu/integration/list/v1?upMenuId=DP17&%s", useGrdCd) )
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
				.header("x-sac-tenant-id", "S01")
				.header("x-sac-system-id", "S01-01014")
		)
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json;charset=UTF-8"))
		.andExpect(header().string("x-sac-result-code", "SUCC"))
		;
	}

	@Test
	public void ebookCategory() throws Exception {

		String useGrdCd = "";

		this.mvc.perform(
				get( String.format("/display/menu/integration/list/v1?upMenuId=genre&%s", useGrdCd) )
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
				.header("x-sac-tenant-id", "S01")
				.header("x-sac-system-id", "S01-01005")
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(header().string("x-sac-result-code", "SUCC"))
				;
	}

}
