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
	 * 1. 정상 msisdn이 Request Parameter로 넘어온 경우.
	 * </pre>
	 * 
	 */
	@Test
	public void requestMsisdnTest() {
		new TestCaseTemplate(this.mockMvc).url("/member/miscellaneous/getUaCode/v1?msisdn=01088902431")
				.httpMethod(HttpMethod.GET).success(GetUaCodeRes.class, new SuccessCallback() {

					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						GetUaCodeRes res = (GetUaCodeRes) result;
						assertThat(res.getUaCd(), notNullValue());
						logger.info("response :{}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
	}

	/**
	 * <pre>
	 * 성공 CASE
	 * 2. deviceModelNo가 Request Parameter로 넘어온 경우.
	 * </pre>
	 */
	@Test
	public void requestDeviceModelNoTest() {
		new TestCaseTemplate(this.mockMvc)
				.url("/member/miscellaneous/getUaCode/v1?deviceModelNo=SCH-B750&?msisdn=01088902431")
				.httpMethod(HttpMethod.GET).success(GetUaCodeRes.class, new SuccessCallback() {

					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						GetUaCodeRes res = (GetUaCodeRes) result;
						logger.info("{}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
	}

	/**
	 * <pre>
	 * 성공 CASE
	 * 3. msisdn, deviceModelNo 둘다 Request Parameter로 넘어온 경우.
	 * </pre>
	 */
	@Test
	public void requestMsisdnDeviceModelNoTest() {
		new TestCaseTemplate(this.mockMvc).url("/member/miscellaneous/getUaCode/v1?deviceModelNo=SCH-B750")
				.httpMethod(HttpMethod.GET).success(GetUaCodeRes.class, new SuccessCallback() {

					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						GetUaCodeRes res = (GetUaCodeRes) result;
						logger.info("{}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
	}

	/** 4. MDN 형태 오류 (10자리 또는 11자리가 아닌 경우) - 실패 */
	@Test
	public void requestInvalidMsisdnTest() {
		new TestCaseTemplate(this.mockMvc).url("/member/miscellaneous/getUaCode/v1?msisdn=010889024")
				.httpMethod(HttpMethod.GET).success(GetUaCodeRes.class, new SuccessCallback() {

					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						GetUaCodeRes res = (GetUaCodeRes) result;
						logger.info("{}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
	}
	/** 5. MDN, DeviceModelNo 둘다 파라미터로 넘어오지 않은 경우 - 실패 */
	/** 6. 회원 테이블에 없는 MDN일 경우 - 실패 */
	/** 7. UA 코드가 없을 경우 ★ 중요!! ★ */
	/** 8. SC 회원 API 응답 실패 ??? */
	/** Exception에 ErrorCode & ErrorMessage 정의 하기 */

}
