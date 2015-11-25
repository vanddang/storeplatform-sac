package com.skplanet.storeplatform.sac.member.miscellaneous.controller;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertSame;
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
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetUaCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetUaCodeRes;
import com.skplanet.storeplatform.sac.member.common.util.TestConvertMapperUtils;

/**
 * UA 코드 정보 조회 JUnit Test.
 * 
 * Updated on : 2014. 1. 9. Updated by : 김다슬, 인크로스.
 */
@ActiveProfiles(value = "local")

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class GetUaCodeTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(GetUaCodeTest.class);

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	/** [REQUEST]. */
	private static GetUaCodeReq request;

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
		request = new GetUaCodeReq();
	}

	/**
	 * <pre>
	 * UA 코드 정보 조회.
	 * - 검색결과 존재. (정상 msisdn).
	 * </pre>
	 * 
	 */
	@Test
	public void testGetUaCodeByMsisdn() {
		new TestCaseTemplate(this.mockMvc).url("/member/miscellaneous/getUaCode/v1").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {

					@Override
					public Object requestBody() {
						request.setMsisdn("01001231116");
						LOGGER.debug("[REQUEST(SAC)] JSON : \n{}", TestConvertMapperUtils.convertObjectToJson(request));
						return request;
					}
				}).success(GetUaCodeRes.class, new SuccessCallback() {

					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						GetUaCodeRes response = (GetUaCodeRes) result;
						assertThat(response, notNullValue());
						LOGGER.debug("[RESPONSE(SAC)] : \n{}", TestConvertMapperUtils.convertObjectToJson(response));
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * UA 코드 정보 조회.
	 * - 검색결과 존재. (정상 deviceModelNo).
	 * </pre>
	 */
	@Test
	public void testGetUaCodeByDeviceModelNo() {
		new TestCaseTemplate(this.mockMvc).url("/member/miscellaneous/getUaCode/v1").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {

					@Override
					public Object requestBody() {
						// request.setDeviceModelNo("SCH-B750");
						request.setDeviceModelNo("ANY-PHONE-4MM");
						LOGGER.debug("[REQUEST(SAC)] JSON : \n{}", TestConvertMapperUtils.convertObjectToJson(request));
						return request;
					}
				}).success(GetUaCodeRes.class, new SuccessCallback() {

					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						GetUaCodeRes response = (GetUaCodeRes) result;
						assertThat(response, notNullValue());
						LOGGER.debug("[RESPONSE(SAC)] : \n{}", TestConvertMapperUtils.convertObjectToJson(response));
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
	}

	/**
	 * <pre>
	 * UA 코드 정보 조회.
	 * - 검색결과 존재. (msisdn, deviceModelNo 둘다 요청 시 : deviceModelNo로 검색됨).
	 * </pre>
	 */
	@Test
	public void testGetUaCodeByBoth() {
		new TestCaseTemplate(this.mockMvc).url("/member/miscellaneous/getUaCode/v1").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {

					@Override
					public Object requestBody() {
						request.setDeviceModelNo("SCH-B750");
						request.setMsisdn("01088902431");
						LOGGER.debug("[REQUEST(SAC)] JSON : \n{}", TestConvertMapperUtils.convertObjectToJson(request));
						return request;
					}
				}).success(GetUaCodeRes.class, new SuccessCallback() {

					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						GetUaCodeRes response = (GetUaCodeRes) result;
						assertThat(response, notNullValue());
						LOGGER.debug("[RESPONSE(SAC)] : \n{}", TestConvertMapperUtils.convertObjectToJson(response));
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
	}

	/**
	 * <pre>
	 * UA 코드 정보 조회.
	 * - Exception. (SAC_MEM_3004=유효하지 않은 MDN).
	 * </pre>
	 */
	@Test(expected = StorePlatformException.class)
	public void testExceptGetUaCodeByMsisdn() {
		new TestCaseTemplate(this.mockMvc).url("/member/miscellaneous/getUaCode/v1").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {

					@Override
					public Object requestBody() {
						request.setMsisdn("99999990e29b41d4a716446655012701");
						LOGGER.debug("[REQUEST(SAC)] JSON : \n{}", TestConvertMapperUtils.convertObjectToJson(request));
						return request;
					}
				}).success(GetUaCodeRes.class, new SuccessCallback() {

					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						GetUaCodeRes response = (GetUaCodeRes) result;
						assertThat(response, notNullValue());
						LOGGER.debug("[RESPONSE(SAC)] : \n{}", TestConvertMapperUtils.convertObjectToJson(response));
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
	}

	/**
	 * <pre>
	 * UA 코드 정보 조회.
	 * - Exception. (SC_MEM_9995=존재하지 않는 회원)
	 * </pre>
	 */
	@Test(expected = StorePlatformException.class)
	public void testExceptGetUaCodeByNonMemberMsisdn() {
		new TestCaseTemplate(this.mockMvc).url("/member/miscellaneous/getUaCode/v1").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {

					@Override
					public Object requestBody() {
						request.setMsisdn("01011112222");
						LOGGER.debug("[REQUEST(SAC)] JSON : \n{}", TestConvertMapperUtils.convertObjectToJson(request));
						return request;
					}
				}).success(GetUaCodeRes.class, new SuccessCallback() {

					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						GetUaCodeRes response = (GetUaCodeRes) result;
						assertThat(response, notNullValue());
						LOGGER.debug("[RESPONSE(SAC)] : \n{}", TestConvertMapperUtils.convertObjectToJson(response));
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
	}

	/**
	 * <pre>
	 * UA 코드 정보 조회.
	 * - Exception. (SAC_MEM_0001=필수 파라미터 미입력).
	 * </pre>
	 */
	@Test(expected = StorePlatformException.class)
	public void testBlankParameter() {
		new TestCaseTemplate(this.mockMvc).url("/member/miscellaneous/getUaCode/v1").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {

					@Override
					public Object requestBody() {
						LOGGER.debug("[REQUEST(SAC)] JSON : \n{}", TestConvertMapperUtils.convertObjectToJson(request));
						return request;
					}
				}).success(GetUaCodeRes.class, new SuccessCallback() {

					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						GetUaCodeRes response = (GetUaCodeRes) result;
						assertSame(response, null);
						LOGGER.debug("[RESPONSE(SAC)] : \n{}", TestConvertMapperUtils.convertObjectToJson(response));
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * UA 코드 정보 조회.
	 * - Exception. (SAC_MEM_3401=DeviceModelNo에 해당하는 UA코드 없음).
	 * </pre>
	 */
	@Test(expected = StorePlatformException.class)
	public void testExceptGetUaCodeByDeviceModelNo() {
		new TestCaseTemplate(this.mockMvc).url("/member/miscellaneous/getUaCode/v1").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {

					@Override
					public Object requestBody() {
						request.setDeviceModelNo("SCH-W777");
						LOGGER.debug("[REQUEST(SAC)] JSON : \n{}", TestConvertMapperUtils.convertObjectToJson(request));
						return request;
					}
				}).success(GetUaCodeRes.class, new SuccessCallback() {

					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						GetUaCodeRes response = (GetUaCodeRes) result;
						assertSame(response, null);
						LOGGER.debug("[RESPONSE(SAC)] : \n{}", TestConvertMapperUtils.convertObjectToJson(response));
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
	}
}
