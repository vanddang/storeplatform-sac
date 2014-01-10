package com.skplanet.storeplatform.sac.api.v1.member.miscellaneous;

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
	private static final Logger logger = LoggerFactory.getLogger(GetOpmdTest.class);

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
		new TestCaseTemplate(this.mockMvc).url("/member/miscellaneous/getOpmd/v1?msisdn=01020021222")
				.httpMethod(HttpMethod.GET).success(GetOpmdRes.class, new SuccessCallback() {

					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						GetOpmdRes res = (GetOpmdRes) result;
						logger.info("response :{}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
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
		new TestCaseTemplate(this.mockMvc).url("/member/miscellaneous/getOpmd/v1?msisdn=9890021222")
				.httpMethod(HttpMethod.GET).success(GetOpmdRes.class, new SuccessCallback() {

					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						GetOpmdRes res = (GetOpmdRes) result;
						logger.info("response :{}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
	}

	/**
	 * <pre>
	 * 실패 CASE
	 * 유효하지 않은 MDN이 Request로 넘어온 경우.
	 * </pre>
	 * 
	 */
	@Test
	public void requestinvalidMsisdnTest() {
		new TestCaseTemplate(this.mockMvc).url("/member/miscellaneous/getOpmd/v1?msisdn=11122220000")
				.httpMethod(HttpMethod.GET).success(GetOpmdRes.class, new SuccessCallback() {

					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						GetOpmdRes res = (GetOpmdRes) result;
						logger.info("response :{}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
	}

	/**
	 * <pre>
	 * 실패 CASE
	 * 유효하지 않은 OPMD MDN이 Request로 넘어온 경우.
	 * </pre>
	 * 
	 */
	@Test
	public void requestinvalidOpmdMsisdnTest() {
		new TestCaseTemplate(this.mockMvc).url("/member/miscellaneous/getOpmd/v1?msisdn=98911021222")
				.httpMethod(HttpMethod.GET).success(GetOpmdRes.class, new SuccessCallback() {

					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						GetOpmdRes res = (GetOpmdRes) result;
						logger.info("response :{}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
	}

	/** 회원 TB에 없는 MDN 일 경우. */

}
