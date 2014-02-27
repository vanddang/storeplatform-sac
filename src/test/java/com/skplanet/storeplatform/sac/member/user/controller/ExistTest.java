package com.skplanet.storeplatform.sac.member.user.controller;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
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

import com.skplanet.storeplatform.framework.test.RequestBodySetter;
import com.skplanet.storeplatform.framework.test.SuccessCallback;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate.RunMode;
import com.skplanet.storeplatform.sac.client.member.vo.user.ExistReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ExistRes;

@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
	 * 회원 가입 여부 조회 (ID/MDN 기반) Parameter : userKey
	 * </pre>
	 */
	@Test
	public void A_TEST_정상_사용자키() {

		new TestCaseTemplate(this.mockMvc).url("/member/user/exist/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
			@Override
			public Object requestBody() {
				ExistReq req = new ExistReq();
				req.setUserKey("IW1023284651220101007215215");
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
	 * 회원 가입 여부 조회 (ID/MDN 기반) Parameter : userId
	 * </pre>
	 */
	@Test
	public void B_TEST_정상_사용자아이디() {
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
	public void C_TEST_정상_디바이스아이디() {
		new TestCaseTemplate(this.mockMvc).url("/member/user/exist/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
			@Override
			public Object requestBody() {
				ExistReq req = new ExistReq();
				req.setDeviceId("01012346489");
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
	public void D_TEST_정상_디바이스키() {
		new TestCaseTemplate(this.mockMvc).url("/member/user/exist/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
			@Override
			public Object requestBody() {
				ExistReq req = new ExistReq();
				req.setDeviceKey("DE201401271052494600000403");
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

	/* ========================= SAC Exception ================================= */
	/**
	 * <pre>
	 * 회원 가입 여부 조회 (ID/MDN 기반) Parameter : deviceKey
	 * </pre>
	 */
	@Test
	public void E_TEST_SAC_오류_파라미터없음() {
		try {
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

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* ========================= SC Exception ================================= */
	/**
	 * <pre>
	 * SC_MEM_0005=사용자키 없음
	 * </pre>
	 */
	@Test
	public void F_TEST_SC_오류_사용자키없음() {
		try {
			new TestCaseTemplate(this.mockMvc).url("/member/user/exist/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
				@Override
				public Object requestBody() {
					ExistReq req = new ExistReq();
					req.setUserKey("213123123");
					logger.debug("request param : {}", req.toString());
					return req;
				}
			}).success(ExistRes.class, new SuccessCallback() {
				@Override
				public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
					ExistRes res = (ExistRes) result;
				}
			}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
