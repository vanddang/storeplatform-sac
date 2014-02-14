package com.skplanet.storeplatform.sac.member.user.controller;

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
import com.skplanet.storeplatform.sac.client.member.vo.user.ExistReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ExistRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.GetProvisioningHistoryReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.GetProvisioningHistoryRes;

@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class GetProvisioningHistoryTest {
	private static final Logger logger = LoggerFactory.getLogger(GetProvisioningHistoryTest.class);

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
	 * 프로비저닝 이력 조회
	 * </pre>
	 */
	@Test
	public void getProvisioningHistory() {

		new TestCaseTemplate(this.mockMvc).url("/member/user/getProvisioningHistory/v1").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						GetProvisioningHistoryReq req = new GetProvisioningHistoryReq();
						req.setDeviceId("01055116046");
						logger.debug("request param : {}", req.toString());
						return req;
					}
				}).success(GetProvisioningHistoryRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						GetProvisioningHistoryRes res = (GetProvisioningHistoryRes) result;

						assertThat(res.getUserKey(), notNullValue());
						logger.info("response param : {}", res.toString());
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
		new TestCaseTemplate(this.mockMvc).url("/member/user/exist/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
			@Override
			public Object requestBody() {
				ExistReq req = new ExistReq();
				req.setUserId("shop_3292");
				logger.debug("request param : {}", req.toString());
				return req;
			}
		}).success(ExistRes.class, new SuccessCallback() {
			@Override
			public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
				ExistRes res = (ExistRes) result;

				assertThat(res.getUserKey(), notNullValue());
				logger.info("response param : {}", res.toString());
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
		new TestCaseTemplate(this.mockMvc).url("/member/user/exist/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
			@Override
			public Object requestBody() {
				ExistReq req = new ExistReq();
				req.setDeviceId("01094993599");
				logger.debug("request param : {}", req.toString());
				return req;
			}
		}).success(ExistRes.class, new SuccessCallback() {
			@Override
			public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
				ExistRes res = (ExistRes) result;

				assertThat(res.getUserKey(), notNullValue());
				logger.info("response param : {}", res.toString());
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
		new TestCaseTemplate(this.mockMvc).url("/member/user/exist/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
			@Override
			public Object requestBody() {
				ExistReq req = new ExistReq();
				req.setDeviceKey("DE201401221858516600000160");
				logger.debug("request param : {}", req.toString());
				return req;
			}
		}).success(ExistRes.class, new SuccessCallback() {
			@Override
			public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
				ExistRes res = (ExistRes) result;

				assertThat(res.getUserKey(), notNullValue());
				logger.info("response param : {}", res.toString());
			}
		}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
	}

	/* ========================= Exception ================================= */
	/**
	 * <pre>
	 * 회원 가입 여부 조회 (ID/MDN 기반) Parameter : userKey
	 * </pre>
	 */
	@Test(expected = StorePlatformException.class)
	public void existUserKeyException() {

		new TestCaseTemplate(this.mockMvc).url("/member/user/exist/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
			@Override
			public Object requestBody() {
				ExistReq req = new ExistReq();
				req.setUserKey("");
				logger.debug("request param : {}", req.toString());
				return req;
			}
		}).success(ExistRes.class, new SuccessCallback() {
			@Override
			public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
				ExistRes res = (ExistRes) result;

			}
		}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 회원 가입 여부 조회 (ID/MDN 기반) Parameter : userId
	 * </pre>
	 */
	@Test(expected = StorePlatformException.class)
	public void existUserIdException() {
		new TestCaseTemplate(this.mockMvc).url("/member/user/exist/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
			@Override
			public Object requestBody() {
				ExistReq req = new ExistReq();
				req.setUserId("");
				logger.debug("request param : {}", req.toString());
				return req;
			}
		}).success(ExistRes.class, new SuccessCallback() {
			@Override
			public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
				ExistRes res = (ExistRes) result;
			}
		}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
	}

	/**
	 * <pre>
	 * 회원 가입 여부 조회 (ID/MDN 기반) Parameter : deviceId
	 * </pre>
	 */
	@Test(expected = StorePlatformException.class)
	public void existDeviceIdException() {
		new TestCaseTemplate(this.mockMvc).url("/member/user/exist/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
			@Override
			public Object requestBody() {
				ExistReq req = new ExistReq();
				req.setDeviceId("");
				logger.debug("request param : {}", req.toString());
				return req;
			}
		}).success(ExistRes.class, new SuccessCallback() {
			@Override
			public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
				ExistRes res = (ExistRes) result;
			}
		}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 회원 가입 여부 조회 (ID/MDN 기반) Parameter : deviceKey
	 * </pre>
	 */
	@Test(expected = StorePlatformException.class)
	public void existDeviceKeyException() {
		new TestCaseTemplate(this.mockMvc).url("/member/user/exist/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
			@Override
			public Object requestBody() {
				ExistReq req = new ExistReq();
				req.setDeviceKey("");
				logger.debug("request param : {}", req.toString());
				return req;
			}
		}).success(ExistRes.class, new SuccessCallback() {
			@Override
			public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
				ExistRes res = (ExistRes) result;
			}
		}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
	}
}
