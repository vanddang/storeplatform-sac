/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.other.search.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

/**
 * 
 * 검색 컨트롤러 클래스 테스트
 * 
 * Updated on : 2014. 1. 6. Updated by : 김현일, 인크로스
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@WebAppConfiguration
@TransactionConfiguration
@Transactional
public class SearchControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mvc;

	/**
	 * 
	 * <pre>
	 * MockMVC 초기화.
	 * </pre>
	 */
	@Before
	public void init() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	/**
	 * 
	 * <pre>
	 * 검색.
	 * </pre>
	 * 
	 * @throws Exception
	 */
	@Test
	public void shouldSearch() throws Exception {

		this.mvc.perform(
				get("/other/search/v1").param("keywd", "pc매니저").param("adultYN", "Y").param("relSearchYN", "Y")
						.param("deviceId", "38a942103867f40b7f421c9b3c3b4443").param("deviceModelNo", "SHV-E160L")
						.param("orderedBy", "recent").param("offset", "0").param("count", "10")).andDo(print());

	}

}
