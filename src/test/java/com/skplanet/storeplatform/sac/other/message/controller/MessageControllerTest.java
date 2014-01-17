/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.other.message.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.skplanet.storeplatform.sac.other.message.common.MessageCommon;

/**
 * 
 * Message Controller Test
 * 
 * Updated on : 2014. 1. 15. Updated by : 김현일, 인크로스.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml", "classpath*:spring-test/context-mvc.xml" })
@WebAppConfiguration
@TransactionConfiguration
@Transactional
public class MessageControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mvc;

	/**
	 * 
	 * <pre>
	 * 초기화.
	 * </pre>
	 */
	@Before
	public void before() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	/**
	 * 
	 * <pre>
	 * Message Controller SMS 전송 기능 테스트.
	 * </pre>
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test
	public void testSmsSend() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String content = mapper.writeValueAsString(MessageCommon.getSmsSendReq());
		MvcResult result = this.mvc
				.perform(post("/other/message/sms/send/v1").contentType(MediaType.APPLICATION_JSON).content(content))
				.andDo(print()).andExpect(status().isOk()).andReturn();
		Map<String, String> resultMap = mapper.readValue(result.getResponse().getContentAsString(), Map.class);
		assertNotNull(resultMap);
		assertThat(resultMap.get("resultStatus"), is("success"));
	}
}
