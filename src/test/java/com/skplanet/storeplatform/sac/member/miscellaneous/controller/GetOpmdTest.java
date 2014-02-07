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
	 * 정상 989로 시작하는 MDN이 Request로 올 경우.
	 * </pre>
	 * 
	 */
	@Test
	public void requestOpmdMsisdnTest() {
		new TestCaseTemplate(this.mockMvc).url("/member/miscellaneous/getOpmd/v1").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {

					@Override
					public Object requestBody() {
						GetOpmdReq request = new GetOpmdReq();
						request.setMsisdn("98999720228");
						LOGGER.debug("request param : {}", request.toString());
						return request;
					}
				}).success(GetOpmdRes.class, new SuccessCallback() {

					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						GetOpmdRes response = (GetOpmdRes) result;
						assertThat(response.getMsisdn(), notNullValue());
						LOGGER.debug("response param : {} ", response.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

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
		new TestCaseTemplate(this.mockMvc).url("/member/miscellaneous/getOpmd/v1").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {

					@Override
					public Object requestBody() {
						GetOpmdReq request = new GetOpmdReq();
						request.setMsisdn("01020284222");
						LOGGER.debug("request param : {}", request.toString());
						return request;
					}
				}).success(GetOpmdRes.class, new SuccessCallback() {

					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						GetOpmdRes response = (GetOpmdRes) result;
						assertThat(response.getMsisdn(), notNullValue());
						LOGGER.debug("response param : {} ", response.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 성공 CASE
	 * 유효하지 않은 MDN이 Request로 넘어온 경우.
	 * msisdn 외 다른 값(MAC-Address, Wifi, ... )이 오면 모번호 조회 하지 않고 그 값 그대로 내려줌.
	 * </pre>
	 * 
	 */
	@Test(expected = StorePlatformException.class)
	public void requestinvalidMsisdnTest() {
		new TestCaseTemplate(this.mockMvc).url("/member/miscellaneous/getOpmd/v1").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {

					@Override
					public Object requestBody() {
						GetOpmdReq request = new GetOpmdReq();
						request.setMsisdn("E1HHADEFVA9");
						LOGGER.debug("request param : {}", request.toString());
						return request;
					}
				}).success(GetOpmdRes.class, new SuccessCallback() {

					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						GetOpmdRes response = (GetOpmdRes) result;
						assertThat(response.getMsisdn(), notNullValue());
						LOGGER.debug("response param : {} ", response.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

}
