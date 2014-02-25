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
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchPasswordSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchPasswordSacRes;

@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SearchPasswdTest {
	private static final Logger logger = LoggerFactory.getLogger(SearchPasswdTest.class);

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
	 * PW 찾기 : OneID 회원
	 * </pre>
	 * 
	 */
	@Test
	public void TEST_A_OneId회원() {

		new TestCaseTemplate(this.mockMvc).url("/member/user/searchPassword/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
			@Override
			public Object requestBody() {
				SearchPasswordSacReq req = new SearchPasswordSacReq();
				req.setUserId("swkang1471");

				logger.debug("request param : {}", req.toString());
				return req;
			}
		}).success(SearchPasswordSacRes.class, new SuccessCallback() {
			@Override
			public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
				SearchPasswordSacRes res = (SearchPasswordSacRes) result;
				assertThat(res.getSendMean(), notNullValue());
				logger.debug("response param : {}", res.toString());
			}
		}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * PW 찾기 : IDP 회원 ID + PHONE
	 * </pre>
	 * 
	 */
	@Test
	public void TEST_B_IDP회원_ID_PHONE() {

		new TestCaseTemplate(this.mockMvc).url("/member/user/searchPassword/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
			@Override
			public Object requestBody() {
				SearchPasswordSacReq req = new SearchPasswordSacReq();
				req.setUserId("vanddangtest021");
				req.setUserPhone("01099999997");

				logger.debug("request param : {}", req.toString());
				return req;
			}
		}).success(SearchPasswordSacRes.class, new SuccessCallback() {
			@Override
			public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
				SearchPasswordSacRes res = (SearchPasswordSacRes) result;
				assertThat(res.getSendMean(), notNullValue());
				logger.debug("response param : {}", res.toString());
			}
		}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * PW 찾기 : IDP 회원 ID + EMAIL
	 * </pre>
	 * 
	 */
	@Test
	public void TEST_C_IDP회원_ID_EMAIL() {

		new TestCaseTemplate(this.mockMvc).url("/member/user/searchPassword/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
			@Override
			public Object requestBody() {
				SearchPasswordSacReq req = new SearchPasswordSacReq();
				req.setUserId("vanddangtest021");
				req.setUserEmail("vanddang@gmail.com");

				logger.debug("request param : {}", req.toString());
				return req;
			}
		}).success(SearchPasswordSacRes.class, new SuccessCallback() {
			@Override
			public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
				SearchPasswordSacRes res = (SearchPasswordSacRes) result;
				assertThat(res.getSendMean(), notNullValue());
				logger.debug("response param : {}", res.toString());
			}
		}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * PW 찾기 : ID 미입력
	 * </pre>
	 * 
	 */
	@Test
	public void TEST_D_ID미입력() {
		try {
			new TestCaseTemplate(this.mockMvc).url("/member/user/searchPassword/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
				@Override
				public Object requestBody() {
					SearchPasswordSacReq req = new SearchPasswordSacReq();
					req.setUserId("");
					req.setUserPhone("vanddang@gmail.com");

					logger.debug("request param : {}", req.toString());
					return req;
				}
			}).success(SearchPasswordSacRes.class, new SuccessCallback() {
				@Override
				public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
					SearchPasswordSacRes res = (SearchPasswordSacRes) result;
					logger.debug("response param : {}", res.toString());
				}
			}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * <pre>
	 * PW 찾기 : userPhone && userEmail 미입력
	 * </pre>
	 * 
	 */
	@Test
	public void TEST_E_PHONE_EMAIL_미입력() {
		try {
			new TestCaseTemplate(this.mockMvc).url("/member/user/searchPassword/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
				@Override
				public Object requestBody() {
					SearchPasswordSacReq req = new SearchPasswordSacReq();
					req.setUserId("vanddangtest021");
					req.setUserPhone("");
					req.setUserEmail("");

					logger.debug("request param : {}", req.toString());
					return req;
				}
			}).success(SearchPasswordSacRes.class, new SuccessCallback() {
				@Override
				public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
					SearchPasswordSacRes res = (SearchPasswordSacRes) result;
					logger.debug("response param : {}", res.toString());
				}
			}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
