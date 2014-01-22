/**
 * 
 */
package com.skplanet.storeplatform.sac.api.v1.member.miscellaneous;

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
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmPhoneAuthorizationCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetPhoneAuthorizationCodeRes;

/**
 * 휴대폰 인증 코드 확인 JUnit Test.
 * 
 * Updated on : 2014. 1. 17. Updated by : 김다슬, 인크로스.
 */
@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class ConfirmPhoneAuthorizationCodeTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfirmPhoneAuthorizationCodeTest.class);

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
	 * Restoration parameter after JUnit Test.
	 * </pre>
	 */
	@After
	public void after() {
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
			new TestCaseTemplate(this.mockMvc).url("/member/miscellaneous/ConfirmPhoneAuthorizationCode/v1")
					.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {

						@Override
						public Object requestBody() {
							ConfirmPhoneAuthorizationCodeReq request = new ConfirmPhoneAuthorizationCodeReq();
							request.setUserPhone("01012344241");
							request.setPhoneAuthCode("707539");
							request.setPhoneSign("f59e3e2bee644efa8922cfc4e23787df");
							request.setTimeToLive("3");
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
	 * Exception.
	 * 기존 인증된 인증 코드.
	 * </pre>
	 */
	@Test
	public void terminatedAuthCodeTest() {
		try {
			new TestCaseTemplate(this.mockMvc).url("/member/miscellaneous/ConfirmPhoneAuthorizationCode/v1")
					.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {

						@Override
						public Object requestBody() {
							ConfirmPhoneAuthorizationCodeReq request = new ConfirmPhoneAuthorizationCodeReq();
							request.setUserPhone("01012344241");
							request.setPhoneAuthCode("805531");
							request.setPhoneSign("b3685c54bf7c491d8a97cc5211449864");
							request.setTimeToLive("3");
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
