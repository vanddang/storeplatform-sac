package com.skplanet.storeplatform.sac.member.miscellaneous.controller;

import static org.junit.Assert.assertEquals;

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
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmPhoneAuthorizationCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmPhoneAuthorizationCodeRes;
import com.skplanet.storeplatform.sac.member.common.util.TestConvertMapperUtils;
import com.skplanet.storeplatform.sac.member.miscellaneous.service.MiscellaneousService;

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
@TransactionConfiguration
@Transactional
public class ConfirmPhoneAuthorizationCodeTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfirmPhoneAuthorizationCodeTest.class);

	@Autowired
	private MiscellaneousService miscellaneousService;

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	/** [REQUEST]. */
	private static ConfirmPhoneAuthorizationCodeReq request;

	/**
	 * <pre>
	 * Initialize parameter before JUnit Test.
	 * </pre>
	 */
	@Before
	public void before() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		// [REQUEST] 초기화
		request = new ConfirmPhoneAuthorizationCodeReq();
	}

	/**
	 * <pre>
	 * 휴대폰 인증 코드 확인.
	 * - 정상 인증.
	 * </pre>
	 */
	@Test
	public void testConfirmPhoneAuthorizationCode() {
		new TestCaseTemplate(this.mockMvc).url("/member/miscellaneous/confirmPhoneAuthorizationCode/v1")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						request.setUserPhone("01020284280");
						request.setPhoneAuthCode("474631");
						request.setPhoneSign("9570dd977253469a8ad3c53439937f77");
						request.setTimeToLive("10");
						LOGGER.debug("[REQUEST(SAC)] JSON : \n{}", TestConvertMapperUtils.convertObjectToJson(request));
						return request;
					}
				}).success(ConfirmPhoneAuthorizationCodeRes.class, new SuccessCallback() {

					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						ConfirmPhoneAuthorizationCodeRes response = (ConfirmPhoneAuthorizationCodeRes) result;
						assertEquals(response.getUserPhone(), request.getUserPhone());
						LOGGER.debug("[RESPONSE(SAC)] : \n{}", TestConvertMapperUtils.convertObjectToJson(response));
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 휴대폰 인증 코드 확인.
	 * - 인증시간 만료.
	 * </pre>
	 */
	@Test
	public void testExeptConfirmPhoneAuthorizationCode() {
		new TestCaseTemplate(this.mockMvc).url("/member/miscellaneous/confirmPhoneAuthorizationCode/v1")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						request.setUserPhone("01020284280");
						request.setPhoneAuthCode("474631");
						request.setPhoneSign("9570dd977253469a8ad3c53439937f77");
						request.setTimeToLive("1");
						LOGGER.debug("[REQUEST(SAC)] JSON : \n{}", TestConvertMapperUtils.convertObjectToJson(request));
						return request;
					}
				}).success(ConfirmPhoneAuthorizationCodeRes.class, new SuccessCallback() {

					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						ConfirmPhoneAuthorizationCodeRes response = (ConfirmPhoneAuthorizationCodeRes) result;
						assertEquals(response.getUserPhone(), request.getUserPhone());
						LOGGER.debug("[RESPONSE(SAC)] : \n{}", TestConvertMapperUtils.convertObjectToJson(response));
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 휴대폰 인증 코드 확인.
	 * - Exception. (기존 인증된 인증 코드)
	 * </pre>
	 */
	@Test(expected = StorePlatformException.class)
	public void testExceptConfirmPhoneAuthorizationCode() {

		new TestCaseTemplate(this.mockMvc).url("/member/miscellaneous/confirmPhoneAuthorizationCode/v1")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {

					@Override
					public Object requestBody() {

						request.setUserPhone("01012344241");
						request.setPhoneAuthCode("528934");
						request.setPhoneSign("71a008abaa174b92aeb05bcc98403bee");
						request.setTimeToLive("3");
						LOGGER.debug("[REQUEST(SAC)] JSON : \n{}", TestConvertMapperUtils.convertObjectToJson(request));
						return request;
					}
				}).success(ConfirmPhoneAuthorizationCodeRes.class, new SuccessCallback() {

					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						ConfirmPhoneAuthorizationCodeRes response = (ConfirmPhoneAuthorizationCodeRes) result;
						assertEquals(response.getUserPhone(), request.getUserPhone());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
	}

}
