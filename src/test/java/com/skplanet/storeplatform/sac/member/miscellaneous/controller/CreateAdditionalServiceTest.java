/**
 * 
 */
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
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.CreateAdditionalServiceReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.CreateAdditionalServiceRes;
import com.skplanet.storeplatform.sac.member.common.util.TestConvertMapperUtils;

/**
 * 부가서비스 가입
 * 
 * Updated on : 2014. 2. 6. Updated by : 김다슬, 인크로스.
 */
@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@TransactionConfiguration
@Transactional
public class CreateAdditionalServiceTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(CreateAdditionalServiceTest.class);

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	/** [REQUEST]. */
	private static CreateAdditionalServiceReq request;

	/**
	 * 
	 * <pre>
	 * Before method.
	 * </pre>
	 */
	@Before
	public void before() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		// [REQUEST] 초기화
		request = new CreateAdditionalServiceReq();
	}

	/**
	 * 
	 * <pre>
	 * 부가서비스 가입.
	 * - 신규 가입.
	 * </pre>
	 * 
	 */
	@Test
	public void testJoinAdditionalService() {
		new TestCaseTemplate(this.mockMvc).url("/member/miscellaneous/createAdditionalService/v1")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {

					@Override
					public Object requestBody() {
						request.setMsisdn("01048088874");
						request.setSvcCode("NA00004184");
						request.setSvcMngNum("");
						LOGGER.debug("[REQUEST(SAC)] JSON : \n{}", TestConvertMapperUtils.convertObjectToJson(request));
						return request;
					}
				}).success(CreateAdditionalServiceRes.class, new SuccessCallback() {

					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						CreateAdditionalServiceRes response = (CreateAdditionalServiceRes) result;
						assertEquals(response.getMsisdn(), request.getMsisdn());
						assertEquals(response.getClass(), request.getSvcCode());
						LOGGER.debug("[RESPONSE(SAC)] : \n{}", TestConvertMapperUtils.convertObjectToJson(response));
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
	}

	/**
	 * 
	 * <pre>
	 * 부가서비스 가입.
	 * - 기가입 (EC - Exception).
	 * </pre>
	 * 
	 */
	@Test(expected = StorePlatformException.class)
	public void testAdditionalServiceAlreadyJoined() {
		new TestCaseTemplate(this.mockMvc).url("/member/miscellaneous/createAdditionalService/v1")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {

					@Override
					public Object requestBody() {
						request.setMsisdn("01048088874");
						request.setSvcCode("NA00004184");
						request.setSvcMngNum("");
						LOGGER.debug("[REQUEST(SAC)] JSON : \n{}", TestConvertMapperUtils.convertObjectToJson(request));
						return request;
					}
				}).success(CreateAdditionalServiceRes.class, new SuccessCallback() {

					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						CreateAdditionalServiceRes response = (CreateAdditionalServiceRes) result;
						assertEquals(response.getMsisdn(), request.getMsisdn());
						assertEquals(response.getClass(), request.getSvcCode());
						LOGGER.debug("[RESPONSE(SAC)] : \n{}", TestConvertMapperUtils.convertObjectToJson(response));
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
	}
}
