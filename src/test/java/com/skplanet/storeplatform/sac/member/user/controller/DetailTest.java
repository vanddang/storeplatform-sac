package com.skplanet.storeplatform.sac.member.user.controller;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.codehaus.jackson.map.ObjectMapper;
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
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchExtentReq;

@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class DetailTest {
	private static final Logger logger = LoggerFactory.getLogger(DetailTest.class);

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
	 * 회원 정보 조회 Parameter : userKey
	 * 사용자 정보(Y), 단말 정보(Y), 약관동의 정보(Y), 법정대리인 정보(Y), 징계 정보(Y), 실명인증 정보(Y)
	 * </pre>
	 */
	@Test
	public void TEST_A_회원정보조회_UserKey() {

		new TestCaseTemplate(this.mockMvc).url("/member/user/detail/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
			@Override
			public Object requestBody() {
				DetailReq req = new DetailReq();
				SearchExtentReq searchExtent = new SearchExtentReq();
				searchExtent.setUserInfoYn("Y");
				searchExtent.setDeviceInfoYn("Y");
				searchExtent.setAgreementInfoYn("Y");
				searchExtent.setMbrAuthInfoYn("Y");
				searchExtent.setMbrPnshInfoYn("Y");
				searchExtent.setMbrLglAgentInfoYn("Y");

				req.setUserKey("IM142100008461807201305271845");
				req.setSearchExtent(searchExtent);

				logger.debug("request param : {}", req.toString());
				return req;
			}
		}).success(DetailRes.class, new SuccessCallback() {
			@Override
			public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
				DetailRes res = (DetailRes) result;

				assertThat(res.getUserKey(), notNullValue());
				logger.info("response param : {}", res.toString());
			}
		}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 회원 정보 조회 Parameter : userId
	 * 사용자 정보(Y), 단말 정보(Y), 약관동의 정보(Y), 법정대리인 정보(Y), 징계 정보(Y), 실명인증 정보(Y)
	 * </pre>
	 */
	@Test
	public void TEST_B_회원정보조회_UserId() {
		new TestCaseTemplate(this.mockMvc).url("/member/user/detail/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
			@Override
			public Object requestBody() {
				DetailReq req = new DetailReq();
				SearchExtentReq searchExtent = new SearchExtentReq();
				searchExtent.setUserInfoYn("Y");
				searchExtent.setDeviceInfoYn("Y");
				searchExtent.setAgreementInfoYn("Y");
				searchExtent.setMbrAuthInfoYn("Y");
				searchExtent.setMbrPnshInfoYn("Y");
				searchExtent.setMbrLglAgentInfoYn("Y");

				req.setUserId("sacsimpleuser020694");
				req.setSearchExtent(searchExtent);

				logger.debug("request param : {}", req.toString());
				return req;
			}
		}).success(DetailRes.class, new SuccessCallback() {
			@Override
			public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
				DetailRes res = (DetailRes) result;

				assertThat(res.getUserKey(), notNullValue());
				logger.info("response param : {}", res.toString());
			}
		}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
	}

	/**
	 * <pre>
	 * 회원 정보 조회 Parameter : deviceId
	 * 사용자 정보(Y), 단말 정보(Y), 약관동의 정보(Y), 법정대리인 정보(Y), 징계 정보(Y), 실명인증 정보(Y)
	 * </pre>
	 */
	@Test
	public void TEST_C_회원정보조회_DeviceId() {
		new TestCaseTemplate(this.mockMvc).url("/member/user/detail/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
			@Override
			public Object requestBody() {
				DetailReq req = new DetailReq();
				SearchExtentReq searchExtent = new SearchExtentReq();
				searchExtent.setUserInfoYn("Y");
				searchExtent.setDeviceInfoYn("Y");
				searchExtent.setAgreementInfoYn("Y");
				searchExtent.setMbrAuthInfoYn("Y");
				searchExtent.setMbrPnshInfoYn("Y");
				searchExtent.setMbrLglAgentInfoYn("Y");

				req.setDeviceId("01714020605");
				req.setSearchExtent(searchExtent);

				logger.debug("request param : {}", req.toString());
				return req;
			}
		}).success(DetailRes.class, new SuccessCallback() {
			@Override
			public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
				DetailRes res = (DetailRes) result;

				assertThat(res.getUserKey(), notNullValue());
				logger.info("response param : {}", res.toString());
			}
		}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 회원 정보 조회 Parameter : deviceKey
	 * 사용자 정보(Y), 단말 정보(Y), 약관동의 정보(Y), 법정대리인 정보(Y), 징계 정보(Y), 실명인증 정보(Y)
	 * </pre>
	 */
	@Test
	public void TEST_D_회원정보조회_DeviceKey() {
		new TestCaseTemplate(this.mockMvc).url("/member/user/detail/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
			@Override
			public Object requestBody() {
				DetailReq req = new DetailReq();
				SearchExtentReq searchExtent = new SearchExtentReq();
				searchExtent.setUserInfoYn("Y");
				searchExtent.setDeviceInfoYn("Y");
				searchExtent.setAgreementInfoYn("Y");
				searchExtent.setMbrAuthInfoYn("Y");
				searchExtent.setMbrPnshInfoYn("Y");
				searchExtent.setMbrLglAgentInfoYn("Y");

				req.setDeviceKey("DE201402120522137350001556");
				req.setSearchExtent(searchExtent);

				logger.debug("request param : {}", req.toString());
				return req;
			}
		}).success(DetailRes.class, new SuccessCallback() {
			@Override
			public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
				DetailRes res = (DetailRes) result;

				assertThat(res.getUserKey(), notNullValue());
				logger.info("response param : {}", res.toString());
			}
		}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
	}

	/* ========================= Exception ================================= */
	/**
	 * <pre>
	 * 회원 정보 조회 Parameter : userKey
	 * </pre>
	 */
	@Test
	public void TEST_E_회원정보조회_UserKeyNon() {
		try {

			new TestCaseTemplate(this.mockMvc).url("/member/user/detail/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {

				@Override
				public Object requestBody() {
					DetailReq req = new DetailReq();
					req.setUserKey("");
					logger.debug("request param : {}", req.toString());

					try {
						ObjectMapper objMapper = new ObjectMapper();
						logger.info("Request : {}", objMapper.writeValueAsString(req));
					} catch (Exception e) {
						e.printStackTrace();
					}

					return req;
				}
			}).success(DetailRes.class, new SuccessCallback() {
				@Override
				public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
					DetailRes res = (DetailRes) result;
					logger.info("response param : {}", res.toString());

				}
			}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * <pre>
	 * 회원 정보 조회 Parameter : userId
	 * </pre>
	 */
	@Test
	public void TEST_F_회원정보조회_UserIdNon() {
		try {

			new TestCaseTemplate(this.mockMvc).url("/member/user/detail/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
				@Override
				public Object requestBody() {
					DetailReq req = new DetailReq();
					req.setUserId("");
					logger.debug("request param : {}", req.toString());
					return req;
				}
			}).success(DetailRes.class, new SuccessCallback() {
				@Override
				public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
					DetailRes res = (DetailRes) result;
					logger.info("response param : {}", res.toString());
				}
			}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * <pre>
	 * 회원 정보 조회 Parameter : deviceId
	 * </pre>
	 */
	@Test
	public void TEST_G_회원정보조회_DeviceIdNon() {
		try {
			new TestCaseTemplate(this.mockMvc).url("/member/user/detail/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
				@Override
				public Object requestBody() {
					DetailReq req = new DetailReq();
					req.setDeviceId("");
					logger.debug("request param : {}", req.toString());
					return req;
				}
			}).success(DetailRes.class, new SuccessCallback() {
				@Override
				public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
					DetailRes res = (DetailRes) result;
					logger.info("response param : {}", res.toString());
				}
			}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * <pre>
	 * 회원 정보 조회 Parameter : deviceKey
	 * </pre>
	 */
	@Test
	public void TEST_H_회원정보조회_DeviceKeyNon() {
		try {

			new TestCaseTemplate(this.mockMvc).url("/member/user/detail/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
				@Override
				public Object requestBody() {
					DetailReq req = new DetailReq();
					req.setDeviceKey("");
					logger.debug("request param : {}", req.toString());
					return req;
				}
			}).success(DetailRes.class, new SuccessCallback() {
				@Override
				public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
					DetailRes res = (DetailRes) result;
					logger.info("response param : {}", res.toString());
				}
			}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ======================================= 정보 조회 범위별 ========================
	/**
	 * <pre>
	 * 회원 정보 조회 Parameter : userKey
	 * 사용자 정보(N), 단말 정보(N), 약관동의 정보(N), 법정대리인 정보(N), 징계 정보(N), 실명인증 정보(N)
	 * </pre>
	 */
	@Test
	public void TEST_I_회원정보조회_기본조회() {

		new TestCaseTemplate(this.mockMvc).url("/member/user/detail/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
			@Override
			public Object requestBody() {
				DetailReq req = new DetailReq();
				SearchExtentReq searchExtent = new SearchExtentReq();
				searchExtent.setUserInfoYn("N");
				searchExtent.setDeviceInfoYn("N");
				searchExtent.setAgreementInfoYn("N");
				searchExtent.setMbrAuthInfoYn("N");
				searchExtent.setMbrPnshInfoYn("N");
				searchExtent.setMbrLglAgentInfoYn("N");

				req.setUserKey("IW1023284651220101007215215");
				req.setSearchExtent(searchExtent);

				logger.debug("request param : {}", req.toString());
				return req;
			}
		}).success(DetailRes.class, new SuccessCallback() {
			@Override
			public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
				DetailRes res = (DetailRes) result;

				assertThat(res.getUserKey(), notNullValue());
				logger.info("response param : {}", res.toString());
			}
		}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 회원 정보 조회 Parameter : userKey
	 * 사용자 정보(N), 단말 정보(Y), 약관동의 정보(Y), 법정대리인 정보(Y), 징계 정보(Y), 실명인증 정보(Y)
	 * </pre>
	 */
	@Test
	public void TEST_J_회원정보조회_사용자정보_제외() {

		new TestCaseTemplate(this.mockMvc).url("/member/user/detail/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
			@Override
			public Object requestBody() {
				DetailReq req = new DetailReq();
				SearchExtentReq searchExtent = new SearchExtentReq();
				searchExtent.setUserInfoYn("N");
				searchExtent.setDeviceInfoYn("Y");
				searchExtent.setAgreementInfoYn("Y");
				searchExtent.setMbrAuthInfoYn("Y");
				searchExtent.setMbrPnshInfoYn("Y");
				searchExtent.setMbrLglAgentInfoYn("Y");

				req.setUserKey("IW1023284651220101007215215");
				req.setSearchExtent(searchExtent);

				logger.debug("request param : {}", req.toString());
				return req;
			}
		}).success(DetailRes.class, new SuccessCallback() {
			@Override
			public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
				DetailRes res = (DetailRes) result;

				assertThat(res.getUserKey(), notNullValue());
				logger.info("response param : {}", res.toString());
			}
		}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 회원 정보 조회 Parameter : userKey
	 * 사용자 정보(Y), 단말 정보(N), 약관동의 정보(Y), 법정대리인 정보(Y), 징계 정보(Y), 실명인증 정보(Y)
	 * </pre>
	 */
	@Test
	public void TEST_K_회원정보조회_디바이스정보_제외() {

		new TestCaseTemplate(this.mockMvc).url("/member/user/detail/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
			@Override
			public Object requestBody() {
				DetailReq req = new DetailReq();
				SearchExtentReq searchExtent = new SearchExtentReq();
				searchExtent.setUserInfoYn("Y");
				searchExtent.setDeviceInfoYn("N");
				searchExtent.setAgreementInfoYn("Y");
				searchExtent.setMbrAuthInfoYn("Y");
				searchExtent.setMbrPnshInfoYn("Y");
				searchExtent.setMbrLglAgentInfoYn("Y");

				req.setUserKey("IW1023284651220101007215215");
				req.setSearchExtent(searchExtent);

				logger.debug("request param : {}", req.toString());
				return req;
			}
		}).success(DetailRes.class, new SuccessCallback() {
			@Override
			public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
				DetailRes res = (DetailRes) result;

				assertThat(res.getUserKey(), notNullValue());
				logger.info("response param : {}", res.toString());
			}
		}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 회원 정보 조회 Parameter : userKey
	 * 사용자 정보(Y), 단말 정보(Y), 약관동의 정보(N), 법정대리인 정보(Y), 징계 정보(Y), 실명인증 정보(Y)
	 * </pre>
	 */
	@Test
	public void TEST_L_회원정보조회_약관동의_제외() {

		new TestCaseTemplate(this.mockMvc).url("/member/user/detail/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
			@Override
			public Object requestBody() {
				DetailReq req = new DetailReq();
				SearchExtentReq searchExtent = new SearchExtentReq();
				searchExtent.setUserInfoYn("Y");
				searchExtent.setDeviceInfoYn("Y");
				searchExtent.setAgreementInfoYn("N");
				searchExtent.setMbrAuthInfoYn("Y");
				searchExtent.setMbrPnshInfoYn("Y");
				searchExtent.setMbrLglAgentInfoYn("Y");

				req.setUserKey("IW1023284651220101007215215");
				req.setSearchExtent(searchExtent);

				logger.debug("request param : {}", req.toString());
				return req;
			}
		}).success(DetailRes.class, new SuccessCallback() {
			@Override
			public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
				DetailRes res = (DetailRes) result;

				assertThat(res.getUserKey(), notNullValue());
				logger.info("response param : {}", res.toString());
			}
		}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 회원 정보 조회 Parameter : userKey
	 * 사용자 정보(Y), 단말 정보(Y), 약관동의 정보(Y), 법정대리인 정보(N), 징계 정보(Y), 실명인증 정보(Y)
	 * </pre>
	 */
	@Test
	public void TEST_M_회원정보조회_법정대리인_제외() {

		new TestCaseTemplate(this.mockMvc).url("/member/user/detail/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
			@Override
			public Object requestBody() {
				DetailReq req = new DetailReq();
				SearchExtentReq searchExtent = new SearchExtentReq();
				searchExtent.setUserInfoYn("Y");
				searchExtent.setDeviceInfoYn("Y");
				searchExtent.setAgreementInfoYn("N");
				searchExtent.setMbrAuthInfoYn("Y");
				searchExtent.setMbrPnshInfoYn("Y");
				searchExtent.setMbrLglAgentInfoYn("N");

				req.setUserKey("IW1023284651220101007215215");
				req.setSearchExtent(searchExtent);

				logger.debug("request param : {}", req.toString());
				return req;
			}
		}).success(DetailRes.class, new SuccessCallback() {
			@Override
			public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
				DetailRes res = (DetailRes) result;

				assertThat(res.getUserKey(), notNullValue());
				logger.info("response param : {}", res.toString());
			}
		}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 회원 정보 조회 Parameter : userKey
	 * 사용자 정보(Y), 단말 정보(Y), 약관동의 정보(Y), 법정대리인 정보(Y), 징계 정보(N), 실명인증 정보(Y)
	 * </pre>
	 */
	@Test
	public void TEST_N_회원정보조회_징계정보_제외() {

		new TestCaseTemplate(this.mockMvc).url("/member/user/detail/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
			@Override
			public Object requestBody() {
				DetailReq req = new DetailReq();
				SearchExtentReq searchExtent = new SearchExtentReq();
				searchExtent.setUserInfoYn("Y");
				searchExtent.setDeviceInfoYn("Y");
				searchExtent.setAgreementInfoYn("Y");
				searchExtent.setMbrAuthInfoYn("Y");
				searchExtent.setMbrPnshInfoYn("N");
				searchExtent.setMbrLglAgentInfoYn("N");

				req.setUserKey("IW1023284651220101007215215");
				req.setSearchExtent(searchExtent);

				logger.debug("request param : {}", req.toString());
				return req;
			}
		}).success(DetailRes.class, new SuccessCallback() {
			@Override
			public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
				DetailRes res = (DetailRes) result;

				assertThat(res.getUserKey(), notNullValue());
				logger.info("response param : {}", res.toString());
			}
		}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 회원 정보 조회 Parameter : userKey
	 * 사용자 정보(Y), 단말 정보(Y), 약관동의 정보(Y), 법정대리인 정보(Y), 징계 정보(Y), 실명인증 정보(N)
	 * </pre>
	 */
	@Test
	public void TEST_O_회원정보조회_실명인증_제외() {

		new TestCaseTemplate(this.mockMvc).url("/member/user/detail/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
			@Override
			public Object requestBody() {
				DetailReq req = new DetailReq();
				SearchExtentReq searchExtent = new SearchExtentReq();
				searchExtent.setUserInfoYn("Y");
				searchExtent.setDeviceInfoYn("Y");
				searchExtent.setAgreementInfoYn("Y");
				searchExtent.setMbrAuthInfoYn("N");
				searchExtent.setMbrPnshInfoYn("N");
				searchExtent.setMbrLglAgentInfoYn("N");

				req.setUserKey("IW1023284651220101007215215");
				req.setSearchExtent(searchExtent);

				logger.debug("request param : {}", req.toString());
				return req;
			}
		}).success(DetailRes.class, new SuccessCallback() {
			@Override
			public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
				DetailRes res = (DetailRes) result;

				assertThat(res.getUserKey(), notNullValue());
				logger.info("response param : {}", res.toString());
			}
		}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}
}
