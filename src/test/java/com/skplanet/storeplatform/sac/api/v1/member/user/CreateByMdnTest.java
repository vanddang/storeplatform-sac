/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.api.v1.member.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

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

import com.skplanet.storeplatform.sac.client.member.vo.common.AgreementInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByMdnReq;

@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class CreateByMdnTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mvc;

	@Before
	public void before() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	/**
	 * <pre>
	 * 모바일 전용 회원 가입 (MDN 회원 가입)
	 * </pre>
	 * 
	 * @throws Exception
	 */
	@Test
	public void createByMdn() throws Exception {

		CreateByMdnReq reqJson = new CreateByMdnReq();
		reqJson.setDeviceId("01090556567");
		reqJson.setDeviceTelecom("US001201");
		reqJson.setJoinId("US002903");
		reqJson.setDeviceModelNo("LG-SH810");
		reqJson.setOwnBirth("20020409");
		reqJson.setParentType("");
		reqJson.setRealNameMethod("US011101");
		reqJson.setParentName("부모이름");
		reqJson.setParentCI("skpone0000132653GWyh3WsEm0FutitO5oSgC2/SgSrLKv5XohA8mxTNLitpB1 B9A3z5zrVHettHzKa5dpJA==");
		reqJson.setParentEmail("hkd@aaaa.com");
		reqJson.setParentTelecom("US001201");
		reqJson.setParentMdn("01088889999");
		reqJson.setParentResident("local");
		reqJson.setParentBirth("19700407");

		// 동의 정보
		AgreementInfo agreementInfo = new AgreementInfo();
		agreementInfo.setExtraAgreementId("asdasdasd");
		agreementInfo.setExtraAgreementVersion("0.1");
		agreementInfo.setIsExtraAgreement("Y");

		List<AgreementInfo> agreementList = new ArrayList<AgreementInfo>();
		agreementList.add(agreementInfo);
		reqJson.setAgreementList(agreementList);

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(reqJson);
		System.out.println(json);

		this.mvc.perform(post("/member/user/createByMdn/v1").content(json).accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andDo(print())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(status().isOk());

	}

}
