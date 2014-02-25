package com.skplanet.storeplatform.sac.member.miscellaneous.controller;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.After;
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
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetEmailAuthorizationCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetEmailAuthorizationCodeRes;
import com.skplanet.storeplatform.sac.member.common.util.TestConvertMapperUtils;

/**
 * 이메일 인증 코드 생성 Junit Test.
 * 
 * Updated on : 2014. 1. 23. Updated by : 김다슬, 인크로스.
 */
@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class GetEmailAuthorizationCodeTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(GetEmailAuthorizationCodeTest.class);

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	/** [REQUEST]. */
	private static GetEmailAuthorizationCodeReq request;
	/** [RESPONSE]. */
	private static GetEmailAuthorizationCodeRes response;

	/**
	 * <pre>
	 * Initialize parameter before JUnit Test.
	 * </pre>
	 */
	@Before
	public void before() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		// [REQUEST] 초기화
		request = new GetEmailAuthorizationCodeReq();
	}

	/**
	 * <pre>
	 * After method.
	 * </pre>
	 */
	@After
	public void after() {
		// Debug [RESPONSE-SAC]
		LOGGER.debug("[RESPONSE(SAC)] : \n{}", TestConvertMapperUtils.convertObjectToJson(response));
	}

	/**
	 * <pre>
	 * 이메일 인증 코드 생성.
	 * - 최초 발급.
	 * </pre>
	 */
	@Test
	public void testGetEmailAuthorizationCode() {
		new TestCaseTemplate(this.mockMvc).url("/member/miscellaneous/getEmailAuthorizationCode/v1")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {

					@Override
					public Object requestBody() {
						request.setUserEmail("vanddang@gmail.com");
						request.setUserKey("US201402170712090930002827");
						LOGGER.debug("[REQUEST(SAC)] JSON : \n{}", TestConvertMapperUtils.convertObjectToJson(request));
						return request;
					}
				}).success(GetEmailAuthorizationCodeRes.class, new SuccessCallback() {

					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						GetEmailAuthorizationCodeRes response = (GetEmailAuthorizationCodeRes) result;
						assertThat(response.getEmailAuthCode(), notNullValue());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
	}

	/**
	 * <pre>
	 * 이메일 인증 코드 생성.
	 * - 인증코드 발급되어있으나 인증되지 않은 코드 내려줌.
	 * </pre>
	 */
	@Test
	public void testGetEmailAuthorizationCode2() {
		new TestCaseTemplate(this.mockMvc).url("/member/miscellaneous/getEmailAuthorizationCode/v1")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {

					@Override
					public Object requestBody() {
						request.setUserEmail("vanddang@gmail.com");
						request.setUserKey("US201402170712090930002827");
						LOGGER.debug("[REQUEST(SAC)] JSON : \n{}", TestConvertMapperUtils.convertObjectToJson(request));
						return request;
					}
				}).success(GetEmailAuthorizationCodeRes.class, new SuccessCallback() {

					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						GetEmailAuthorizationCodeRes response = (GetEmailAuthorizationCodeRes) result;
						assertThat(response.getEmailAuthCode(), notNullValue());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
	}

}
