/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.api.v1.member.miscellaneous;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.skplanet.storeplatform.framework.test.RequestBodySetter;
import com.skplanet.storeplatform.framework.test.SuccessCallback;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate.RunMode;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetPhoneAuthorizationCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetPhoneAuthorizationCodeRes;

/**
 * 휴대폰 인증 SMS 발송 JUnit Test.
 * 
 * Updated on : 2014. 1. 17. Updated by : 김다슬, 인크로스.
 */
@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class GetPhoneAuthorizationCodeTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(GetPhoneAuthorizationCodeTest.class);

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Before
	public void before() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	/**
	 * <pre>
	 * 성공 CASE
	 * 정상 파라미터 전달.
	 * </pre>
	 */
	@Test
	public void simpleTest() {
		try {
			// 개발 TEST URL 맵핑되어 있음.
			new TestCaseTemplate(this.mockMvc).url("/member/miscellaneous/getPhoneAuthorizationCode/v1")
					.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {

						@Override
						public Object requestBody() {
							GetPhoneAuthorizationCodeReq request = new GetPhoneAuthorizationCodeReq();
							request.setSrcId("US004504"); // 휴대폰 인증 SMS
							request.setTeleSvcId("0"); // 단건 발송
							request.setUserPhone("01012344241");
							request.setUserTelecom("SKT");
							LOGGER.debug("request param : {}", request.toString());
							return request;
						}
					}).success(GetPhoneAuthorizationCodeRes.class, new SuccessCallback() {

						@Override
						public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
							GetPhoneAuthorizationCodeRes response = (GetPhoneAuthorizationCodeRes) result;
							LOGGER.debug("response param : {} ", response.toString());
						}
					}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * <pre>
	 * 실패 CASE
	 * 유효하지 않은 통신사 정보 전달.
	 * </pre>
	 */
	@Test
	public void invalidTelecomTest() {
		try {
			// 개발 TEST URL 맵핑되어 있음.
			new TestCaseTemplate(this.mockMvc).url("/member/miscellaneous/getPhoneAuthorizationCode/v1")
					.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {

						@Override
						public Object requestBody() {
							GetPhoneAuthorizationCodeReq request = new GetPhoneAuthorizationCodeReq();
							request.setSrcId("US004504"); // 휴대폰 인증 SMS
							request.setTeleSvcId("0"); // 단건 발송
							request.setUserPhone("01012344241");
							request.setUserTelecom("AAA");
							LOGGER.debug("request param : {}", request.toString());
							return request;
						}
					}).success(GetPhoneAuthorizationCodeRes.class, new SuccessCallback() {

						@Override
						public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
							GetPhoneAuthorizationCodeRes response = (GetPhoneAuthorizationCodeRes) result;
							LOGGER.debug("response param : {} ", response.toString());
						}
					}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
