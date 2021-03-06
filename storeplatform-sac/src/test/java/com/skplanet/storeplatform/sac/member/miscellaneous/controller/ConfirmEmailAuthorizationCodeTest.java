/**
 * 
 */
package com.skplanet.storeplatform.sac.member.miscellaneous.controller;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

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
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.test.RequestBodySetter;
import com.skplanet.storeplatform.framework.test.SuccessCallback;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate.RunMode;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmEmailAuthorizationCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmEmailAuthorizationCodeRes;
import com.skplanet.storeplatform.sac.member.common.util.TestConvertMapperUtils;

/**
 * 이메일 인증코드 확인 JUnit Test.
 * 
 * Updated on : 2014. 1. 23. Updated by : 김다슬, 인크로스.
 */
@ActiveProfiles(value = "local")

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@TransactionConfiguration
@Transactional
public class ConfirmEmailAuthorizationCodeTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfirmEmailAuthorizationCodeTest.class);

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	/** [REQUEST]. */
	private static ConfirmEmailAuthorizationCodeReq request;

	/**
	 * <pre>
	 * Initialize parameter before JUnit Test.
	 * </pre>
	 */
	@Before
	public void before() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		// [REQUEST] 초기화
		request = new ConfirmEmailAuthorizationCodeReq();
	}

	/**
	 * <pre>
	 * 이메일 인증코드 확인
	 * - 최초 인증
	 * </pre>
	 */
	@Test
	public void testConfirmedEmailAuthCode() {
		new TestCaseTemplate(this.mockMvc).url("/member/miscellaneous/confirmEmailAuthorizationCode/v1")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {

					@Override
					public Object requestBody() {
						request.setEmailAuthCode("0964bc58dd29425fb1fbafbd218bb863");
						LOGGER.debug("[REQUEST(SAC)] JSON : \n{}", TestConvertMapperUtils.convertObjectToJson(request));
						return request;
					}
				}).success(ConfirmEmailAuthorizationCodeRes.class, new SuccessCallback() {

					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						ConfirmEmailAuthorizationCodeRes response = (ConfirmEmailAuthorizationCodeRes) result;
						assertThat(response.getUserEmail(), notNullValue());
						assertThat(response.getUserKey(), notNullValue());
						LOGGER.debug("[RESPONSE(SAC)] JSON : \n{}",
								TestConvertMapperUtils.convertObjectToJson(response));
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 이메일 인증코드 확인.
	 * - 인증코드 발급되어있으나 인증되지 않은 코드.
	 * </pre>
	 */
	@Test
	public void testConfirmedEmailAuthCode2() {
		new TestCaseTemplate(this.mockMvc).url("/member/miscellaneous/confirmEmailAuthorizationCode/v1")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {

					@Override
					public Object requestBody() {
						request.setEmailAuthCode("0964bc58dd29425fb1fbafbd218bb863");
						LOGGER.debug("[REQUEST(SAC)] JSON : \n{}", TestConvertMapperUtils.convertObjectToJson(request));
						return request;
					}
				}).success(ConfirmEmailAuthorizationCodeRes.class, new SuccessCallback() {

					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						ConfirmEmailAuthorizationCodeRes response = (ConfirmEmailAuthorizationCodeRes) result;
						assertThat(response.getUserEmail(), notNullValue());
						assertThat(response.getUserKey(), notNullValue());
						LOGGER.debug("[RESPONSE(SAC)] JSON : \n{}",
								TestConvertMapperUtils.convertObjectToJson(response));
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 성공 CASE
	 * 기 인증 회원.
	 * </pre>
	 */
	@Test(expected = StorePlatformException.class)
	public void testExceptConfirmedEmailAuthCode() {
		new TestCaseTemplate(this.mockMvc).url("/member/miscellaneous/confirmEmailAuthorizationCode/v1")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {

					@Override
					public Object requestBody() {
						request.setEmailAuthCode("c4a566d90d2a4440991eca80f404611a");
						LOGGER.debug("[REQUEST(SAC)] JSON : \n{}", TestConvertMapperUtils.convertObjectToJson(request));
						return request;
					}
				}).success(ConfirmEmailAuthorizationCodeRes.class, new SuccessCallback() {

					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						ConfirmEmailAuthorizationCodeRes response = (ConfirmEmailAuthorizationCodeRes) result;
						assertThat(response.getUserEmail(), notNullValue());
						assertThat(response.getUserKey(), notNullValue());
						LOGGER.debug("[RESPONSE(SAC)] JSON : \n{}",
								TestConvertMapperUtils.convertObjectToJson(response));
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}
}
