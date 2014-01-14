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

import com.skplanet.storeplatform.framework.test.SuccessCallback;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate.RunMode;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetUaCodeRes;

/**
 * UA 코드 정보 조회 JUnit Test.
 * 
 * Updated on : 2014. 1. 9. Updated by : 김다슬, 인크로스.
 */
@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class GetUaCodeTest {
	private static final Logger logger = LoggerFactory.getLogger(GetUaCodeTest.class);

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	/**
	 * 
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
	 * 정상 msisdn이 Request Parameter로 넘어온 경우.
	 * </pre>
	 * 
	 */
	@Test
	public void requestMsisdnTest() {
		new TestCaseTemplate(this.mockMvc).url("/dev/member/miscellaneous/getUaCode/v1?msisdn=01088902431")
				.httpMethod(HttpMethod.GET).success(GetUaCodeRes.class, new SuccessCallback() {

					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						GetUaCodeRes res = (GetUaCodeRes) result;
						assertThat(res.getUaCd(), notNullValue());
						logger.debug("response :{}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
	}

	/**
	 * <pre>
	 * 성공 CASE
	 * deviceModelNo가 Request Parameter로 넘어온 경우.
	 * </pre>
	 */
	@Test
	public void requestDeviceModelNoTest() {
		new TestCaseTemplate(this.mockMvc)
				.url("/dev/member/miscellaneous/getUaCode/v1?deviceModelNo=SCH-B750&?msisdn=01088902431")
				.httpMethod(HttpMethod.GET).success(GetUaCodeRes.class, new SuccessCallback() {

					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						GetUaCodeRes res = (GetUaCodeRes) result;
						logger.debug("{}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
	}

	/**
	 * <pre>
	 * 성공 CASE
	 * msisdn, deviceModelNo 둘다 Request Parameter로 넘어온 경우.
	 * </pre>
	 */
	@Test
	public void requestMsisdnDeviceModelNoTest() {
		new TestCaseTemplate(this.mockMvc).url("/dev/member/miscellaneous/getUaCode/v1?deviceModelNo=SCH-B750")
				.httpMethod(HttpMethod.GET).success(GetUaCodeRes.class, new SuccessCallback() {

					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						GetUaCodeRes res = (GetUaCodeRes) result;
						logger.debug("{}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
	}

	/**
	 * <pre>
	 * 실패 CASE
	 * 유효하지 않은 MDN이 Request로 넘어온 경우.
	 * Biz.Logic - Exception 처리
	 * 
	 * [유효한 MDN]
	 * 1. 10자리 또는 11자리
	 * 2. 010/011/016/017/018
	 * </pre>
	 */
	@Test
	public void requestInvalidMsisdnTest() {
		new TestCaseTemplate(this.mockMvc).url("/dev/member/miscellaneous/getUaCode/v1?msisdn=0018890240")
				.httpMethod(HttpMethod.GET).success(GetUaCodeRes.class, new SuccessCallback() {

					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						GetUaCodeRes res = (GetUaCodeRes) result;
						logger.debug("{}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
	}

	/**
	 * <pre>
	 * 실패 CASE
	 * 회원 Table에 등록되지 않은 MDN이 Request로 넘어온 경우.
	 * Biz.Logic - Exception 처리
	 * </pre>
	 */
	@Test
	public void requestNonDbMsisdnTest() {
		new TestCaseTemplate(this.mockMvc).url("/dev/member/miscellaneous/getUaCode/v1?msisdn=01011112222")
				.httpMethod(HttpMethod.GET).success(GetUaCodeRes.class, new SuccessCallback() {

					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						GetUaCodeRes res = (GetUaCodeRes) result;
						logger.debug("{}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
	}

	/**
	 * <pre>
	 * 실패 CASE
	 * Request로 아무값도 넘어오지 않은 경우.
	 * Biz.Logic - Exception 처리
	 * </pre>
	 */
	@Test
	public void requestNonTest() {
		new TestCaseTemplate(this.mockMvc).url("/dev/member/miscellaneous/getUaCode/v1").httpMethod(HttpMethod.GET)
				.success(GetUaCodeRes.class, new SuccessCallback() {

					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						GetUaCodeRes res = (GetUaCodeRes) result;
						logger.debug("{}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
	}

	/**
	 * <pre>
	 * 실패 CASE
	 * Device Tabel에 없는 DeviceModelNo가 넘어온 경우.(UA코드가 없는 경우)
	 * Biz.Logic - Exception 처리
	 * </pre>
	 */
	@Test
	public void responseNonUaCodeTest() {
		new TestCaseTemplate(this.mockMvc).url("/dev/member/miscellaneous/getUaCode/v1?deviceModelNo=SCH-W777")
				.httpMethod(HttpMethod.GET).success(GetUaCodeRes.class, new SuccessCallback() {

					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						GetUaCodeRes res = (GetUaCodeRes) result;
						logger.debug("{}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
	}
}
