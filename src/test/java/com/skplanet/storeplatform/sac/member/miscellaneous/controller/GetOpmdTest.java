package com.skplanet.storeplatform.sac.member.miscellaneous.controller;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
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
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetOpmdReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetOpmdRes;
import com.skplanet.storeplatform.sac.member.common.util.TestConvertMapperUtils;

/**
 * OPMD 모회선 번호 조회 JUnit Test.
 * 
 * Updated on : 2014. 1. 9. Updated by : 김다슬, 인크로스.
 */
@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class GetOpmdTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(GetOpmdTest.class);

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	/** [REQUEST]. */
	private static GetOpmdReq request;
	/** [RESPONSE]. */
	private static GetOpmdRes response;

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Before
	public void before() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		// [REQUEST] 초기화
		request = new GetOpmdReq();
	}

	/**
	 * <pre>
	 * OPMD 모회선 번호 조회.
	 * - 검색결과 존재. (989로 시작하는 MDN)
	 * </pre>
	 * 
	 */
	@Test
	public void testGetOpmdByOpmdMsisdn() {
		new TestCaseTemplate(this.mockMvc).url("/member/miscellaneous/getOpmd/v1").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {

					@Override
					public Object requestBody() {
						request.setMsisdn("98999720228");
						LOGGER.debug("[REQUEST(SAC)] JSON : \n{}", TestConvertMapperUtils.convertObjectToJson(request));
						return request;
					}
				}).success(GetOpmdRes.class, new SuccessCallback() {

					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						response = (GetOpmdRes) result;
						LOGGER.debug("[RESPONSE(SAC)] : \n{}", TestConvertMapperUtils.convertObjectToJson(response));
						assertThat(response.getMsisdn(), notNullValue());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * OPMD 모회선 번호 조회.
	 * - 검색결과 없음. (OPMD번호 아님, msisdn 그대로 반환.)
	 * </pre>
	 * 
	 */
	@Test
	public void testGetOpmdByMsisdn() {
		new TestCaseTemplate(this.mockMvc).url("/member/miscellaneous/getOpmd/v1").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {

					@Override
					public Object requestBody() {
						request.setMsisdn("01020284280");
						LOGGER.debug("[REQUEST(SAC)] JSON : \n{}", TestConvertMapperUtils.convertObjectToJson(request));
						return request;
					}
				}).success(GetOpmdRes.class, new SuccessCallback() {

					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						response = (GetOpmdRes) result;
						LOGGER.debug("[RESPONSE(SAC)] : \n{}", TestConvertMapperUtils.convertObjectToJson(response));
						assertEquals(response.getMsisdn(), request.getMsisdn());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * OPMD 모회선 번호 조회.
	 * - Exception. (유효하지 않은 MDN - msisdn 외 다른 값(MAC-Address, Wifi, ... )
	 * </pre>
	 * 
	 */
	@Test
	public void testExceptionGetOpmdByMsisdn() {
		try {
			new TestCaseTemplate(this.mockMvc).url("/member/miscellaneous/getOpmd/v1").httpMethod(HttpMethod.POST)
					.requestBody(new RequestBodySetter() {

						@Override
						public Object requestBody() {
							request.setMsisdn("E1HHADEFVA9");
							LOGGER.debug("[REQUEST(SAC)] JSON : \n{}",
									TestConvertMapperUtils.convertObjectToJson(request));
							return request;
						}
					}).success(GetOpmdRes.class, new SuccessCallback() {

						@Override
						public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
							response = (GetOpmdRes) result;
							assertThat(response.getMsisdn(), notNullValue());
						}
					}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
		} catch (StorePlatformException e) {
			assertEquals("SAC_MEM_3004", e.getErrorInfo().getCode());
			LOGGER.info("\nerror >> ", e);

		}

	}

}
