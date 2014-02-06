/**
 * 
 */
package com.skplanet.storeplatform.sac.api.v1.member.miscellaneous;

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
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.test.RequestBodySetter;
import com.skplanet.storeplatform.framework.test.SuccessCallback;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate.RunMode;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmEmailAuthorizationCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmEmailAuthorizationCodeRes;

/**
 * 이메일 인증코드 확인 JUnit Test.
 * 
 * Updated on : 2014. 1. 23. Updated by : 김다슬, 인크로스.
 */
@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class ConfirmEmailAuthorizationCodeTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfirmEmailAuthorizationCodeTest.class);

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	/**
	 * <pre>
	 * Initialize parameter before JUnit Test.
	 * </pre>
	 */
	@Before
	public void before() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	/**
	 * <pre>
	 * 성공 CASE
	 * 정상 파라미터 전달. - exception??? 왜
	 * </pre>
	 */
	@Test
	public void simpleTest() {
		new TestCaseTemplate(this.mockMvc).url("/member/miscellaneous/ConfirmEmailAuthorizationCode/v1")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {

					@Override
					public Object requestBody() {
						ConfirmEmailAuthorizationCodeReq request = new ConfirmEmailAuthorizationCodeReq();
						request.setEmailAuthCode("84f9f1febcfe4d129e84138cc7baf3da");
						LOGGER.debug("request param : {}", request.toString());
						return request;
					}
				}).success(ConfirmEmailAuthorizationCodeRes.class, new SuccessCallback() {

					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						ConfirmEmailAuthorizationCodeRes response = (ConfirmEmailAuthorizationCodeRes) result;
						assertThat(response.getUserEmail(), notNullValue());
						assertThat(response.getUserKey(), notNullValue());
						LOGGER.debug("response param : {} ", response.toString());
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
	public void confirmedAuthUserTest() {
		new TestCaseTemplate(this.mockMvc).url("/member/miscellaneous/ConfirmEmailAuthorizationCode/v1")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {

					@Override
					public Object requestBody() {
						ConfirmEmailAuthorizationCodeReq request = new ConfirmEmailAuthorizationCodeReq();
						request.setEmailAuthCode("c4a566d90d2a4440991eca80f404611a");
						LOGGER.debug("request param : {}", request.toString());
						return request;
					}
				}).success(ConfirmEmailAuthorizationCodeRes.class, new SuccessCallback() {

					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						ConfirmEmailAuthorizationCodeRes response = (ConfirmEmailAuthorizationCodeRes) result;
						assertThat(response.getUserEmail(), notNullValue());
						assertThat(response.getUserKey(), notNullValue());
						LOGGER.debug("response param : {} ", response.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}
}
