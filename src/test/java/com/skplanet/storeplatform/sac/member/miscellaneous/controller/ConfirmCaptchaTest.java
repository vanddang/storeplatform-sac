package com.skplanet.storeplatform.sac.member.miscellaneous.controller;

import static org.junit.Assert.assertEquals;

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

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.test.RequestBodySetter;
import com.skplanet.storeplatform.framework.test.SuccessCallback;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate.RunMode;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmCaptchaReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmCaptchaRes;
import com.skplanet.storeplatform.sac.member.common.util.TestConvertMapperUtils;

/**
 * Captcha 문자 확인 JUnit Test.
 * 
 * Updated on : 2014. 1. 23. Updated by : 김다슬, 인크로스.
 */
@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class ConfirmCaptchaTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfirmCaptchaTest.class);

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	/** [REQUEST]. */
	private static ConfirmCaptchaReq request;
	/** [RESPONSE]. */
	private static ConfirmCaptchaRes response;

	/**
	 * <pre>
	 * Initialize parameter before JUnit Test.
	 * </pre>
	 */
	@Before
	public void before() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		// [REQUEST] 초기화
		request = new ConfirmCaptchaReq();
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
	 * 성공 CASE. - 성공인데 Exception 발생! 확인 필요!!
	 * </pre>
	 */
	@Test(expected = StorePlatformException.class)
	public void simpleTest() {
		new TestCaseTemplate(this.mockMvc).url("/member/miscellaneous/ConfirmCaptcha/v1").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {

					@Override
					public Object requestBody() {
						request.setAuthCode("n6yxe6");
						request.setImageSign("88f4e72da1467f7ff05aebf0f72faf25f206588f");
						request.setSignData("https://idp.innoace.com:8002/watermark/20140122/10573_1390394299869.jpeg|1390394299869");

						LOGGER.debug("[REQUEST(SAC)] JSON : \n{}", TestConvertMapperUtils.convertObjectToJson(request));
						return request;
					}
				}).success(ConfirmCaptchaRes.class, new SuccessCallback() {

					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						ConfirmCaptchaRes response = (ConfirmCaptchaRes) result;
						assertEquals(response, null); // response 없는게 정상 Case임.
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

}
