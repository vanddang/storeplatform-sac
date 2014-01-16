package com.skplanet.storeplatform.sac.api.v1.member.user;

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
import com.skplanet.storeplatform.sac.client.member.vo.user.ExistRes;

@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class ExistTest {
	private static final Logger logger = LoggerFactory.getLogger(ExistTest.class);

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
	 * 회원 가입 여부 조회 (ID/MDN 기반) Parameter Null1
	 * </pre>
	 */
	@Test
	public void existNull1() {
		new TestCaseTemplate(this.mockMvc).url("/member/user/exist/v1").httpMethod(HttpMethod.GET)
				.success(ExistRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						ExistRes res = (ExistRes) result;
						logger.info("{}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 회원 가입 여부 조회 (ID/MDN 기반) Parameter Null2
	 * </pre>
	 */
	@Test
	public void existNull2() {
		new TestCaseTemplate(this.mockMvc).url("/member/user/exist/v1?userKey=&userId=&deviceId=&deviceKey=")
				.httpMethod(HttpMethod.GET).success(ExistRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						ExistRes res = (ExistRes) result;
						logger.info("{}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 회원 가입 여부 조회 (ID/MDN 기반) Parameter : userKey
	 * </pre>
	 */
	@Test
	public void existUserKey() {
		new TestCaseTemplate(this.mockMvc).url("/member/user/exist/v1?userKey=IF1023002708420090928145937")
				.httpMethod(HttpMethod.GET).success(ExistRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						ExistRes res = (ExistRes) result;
						logger.info("{}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 회원 가입 여부 조회 (ID/MDN 기반) Parameter : userId
	 * </pre>
	 */
	@Test
	public void existUserId() {
		new TestCaseTemplate(this.mockMvc).url("/member/user/exist/v1?userId=tstore44").httpMethod(HttpMethod.GET)
				.success(ExistRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						ExistRes res = (ExistRes) result;
						logger.info("{}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 회원 가입 여부 조회 (ID/MDN 기반) Parameter : deviceId
	 * </pre>
	 */
	@Test
	public void existDeviceId() {
		new TestCaseTemplate(this.mockMvc).url("/member/user/exist/v1?deviceId=01088902431").httpMethod(HttpMethod.GET)
				.success(ExistRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						ExistRes res = (ExistRes) result;
						logger.info("{}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 회원 가입 여부 조회 (ID/MDN 기반) Parameter : deviceKey
	 * </pre>
	 */
	@Test
	public void existDeviceKey() {
		new TestCaseTemplate(this.mockMvc).url("/member/user/exist/v1?deviceKey=01088902431")
				.httpMethod(HttpMethod.GET).success(ExistRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						ExistRes res = (ExistRes) result;
						logger.info("{}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}
}
