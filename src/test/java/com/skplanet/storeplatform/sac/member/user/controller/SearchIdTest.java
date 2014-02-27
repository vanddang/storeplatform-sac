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
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchIdSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchIdSacRes;

//import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;

@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SearchIdTest {
	private static final Logger logger = LoggerFactory.getLogger(SearchIdTest.class);

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
	 * ID 찾기 : DeviceId로 찾기
	 * </pre>
	 * 
	 */
	@Test
	public void A_TEST_정상_디바이스아이디() {

		new TestCaseTemplate(this.mockMvc).url("/member/user/searchId/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
			@Override
			public Object requestBody() {
				SearchIdSacReq req = new SearchIdSacReq();
				req.setDeviceId("01050368297");

				logger.debug("request param : {}", req.toString());
				return req;
			}
		}).success(SearchIdSacRes.class, new SuccessCallback() {
			@Override
			public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
				SearchIdSacRes res = (SearchIdSacRes) result;
				assertThat(res.getSearchIdList().get(0).getUserId(), notNullValue());
				logger.debug("response param : {}", res.toString());
			}
		}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * ID 찾기 : userEmail로 찾기
	 * </pre>
	 * 
	 */
	@Test
	public void B_TEST_정상_이메일() {

		new TestCaseTemplate(this.mockMvc).url("/member/user/searchId/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
			@Override
			public Object requestBody() {
				SearchIdSacReq req = new SearchIdSacReq();
				req.setUserEmail("sinwan123@yahoo.co.kr");

				logger.debug("request param : {}", req.toString());
				return req;
			}
		}).success(SearchIdSacRes.class, new SuccessCallback() {
			@Override
			public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
				SearchIdSacRes res = (SearchIdSacRes) result;
				assertThat(res.getSearchIdList().get(0).getUserId(), notNullValue());
				logger.debug("response param : {}", res.toString());
			}
		}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/* ============================ SAC Exception ============================ */
	/**
	 * <pre>
	 * 파라미터 미입력
	 * </pre>
	 * 
	 */
	@Test
	public void C_TESET_오류_파라미터미입력() {
		try {
			new TestCaseTemplate(this.mockMvc).url("/member/user/searchId/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
				@Override
				public Object requestBody() {
					SearchIdSacReq req = new SearchIdSacReq();
					req.setDeviceId("");

					logger.debug("request param : {}", req.toString());
					return req;
				}
			}).success(SearchIdSacRes.class, new SuccessCallback() {
				@Override
				public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
					SearchIdSacRes res = (SearchIdSacRes) result;
					//				assertThat(res.getUserId(), notNullValue());
					logger.debug("response param : {}", res.toString());
				}
			}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * <pre>
	 * ID 찾기 : UserEmail > 검색한 UserType 이 모바일인 경우
	 * </pre>
	 * 
	 */
	@Test
	public void D_TEST_오류_모바일회원() {
		try {
			new TestCaseTemplate(this.mockMvc).url("/member/user/searchId/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
				@Override
				public Object requestBody() {
					SearchIdSacReq req = new SearchIdSacReq();
					req.setUserEmail("dream00101@yopmail.com");

					logger.debug("request param : {}", req.toString());
					return req;
				}
			}).success(SearchIdSacRes.class, new SuccessCallback() {
				@Override
				public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
					SearchIdSacRes res = (SearchIdSacRes) result;
					//				assertThat(res.getUserId(), notNullValue());
					logger.debug("response param : {}", res.toString());
				}
			}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * <pre>
	 * ID 찾기 : DeviceId > 검색한 UserType 이 모바일인 경우
	 * </pre>
	 * 
	 */
	@Test
	public void E_TEST_오류_모바일회원() {
		try {
			new TestCaseTemplate(this.mockMvc).url("/member/user/searchId/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
				@Override
				public Object requestBody() {
					SearchIdSacReq req = new SearchIdSacReq();
					req.setDeviceId("01099012701");

					logger.debug("request param : {}", req.toString());
					return req;
				}
			}).success(SearchIdSacRes.class, new SuccessCallback() {
				@Override
				public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
					SearchIdSacRes res = (SearchIdSacRes) result;
					//				assertThat(res.getUserId(), notNullValue());
					logger.debug("response param : {}", res.toString());
				}
			}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* ============================ SC Exception ============================ */
	/**
	 * <pre>
	 * email 로 검색한 결과가 없을 경우.
	 * </pre>
	 * 
	 */
	@Test
	public void F_TEST_오류_이메일_검색결과없음() {
		try {
			new TestCaseTemplate(this.mockMvc).url("/member/user/searchId/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
				@Override
				public Object requestBody() {
					SearchIdSacReq req = new SearchIdSacReq();
					req.setUserEmail("");

					logger.debug("request param : {}", req.toString());
					return req;
				}
			}).success(SearchIdSacRes.class, new SuccessCallback() {
				@Override
				public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
					SearchIdSacRes res = (SearchIdSacRes) result;
					//				assertThat(res.getUserId(), notNullValue());
					logger.debug("response param : {}", res.toString());
				}
			}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * <pre>
	 * deviceId 로 검색한 결과가 없을 경우.
	 * </pre>
	 * 
	 */
	@Test
	public void G_TEST_오류_디바이스아이디_사용자키없음() {
		try {
			new TestCaseTemplate(this.mockMvc).url("/member/user/searchId/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
				@Override
				public Object requestBody() {
					SearchIdSacReq req = new SearchIdSacReq();
					req.setDeviceId("123456");

					logger.debug("request param : {}", req.toString());
					return req;
				}
			}).success(SearchIdSacRes.class, new SuccessCallback() {
				@Override
				public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
					SearchIdSacRes res = (SearchIdSacRes) result;
					//				assertThat(res.getUserId(), notNullValue());
					logger.debug("response param : {}", res.toString());
				}
			}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
