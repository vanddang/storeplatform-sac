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

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.test.RequestBodySetter;
import com.skplanet.storeplatform.framework.test.SuccessCallback;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate.RunMode;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchIdSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchIdSacRes;

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
	 * ID 찾기 : IDP 회원
	 * </pre>
	 * 
	 */
	@Test
	public void a_searchId() {

		new TestCaseTemplate(this.mockMvc).url("/member/user/searchId/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
			@Override
			public Object requestBody() {
				SearchIdSacReq req = new SearchIdSacReq();
				req.setDeviceId("01023624159");

				logger.debug("request param : {}", req.toString());
				return req;
			}
		}).success(SearchIdSacRes.class, new SuccessCallback() {
			@Override
			public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
				SearchIdSacRes res = (SearchIdSacRes) result;
				assertThat(res.getUserId(), notNullValue());
				logger.debug("response param : {}", res.toString());
			}
		}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * ID 찾기 : MDN 회원
	 * </pre>
	 * 
	 */
	@Test(expected = StorePlatformException.class)
	public void b_searchId() {

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
				assertThat(res.getUserId(), notNullValue());
				logger.debug("response param : {}", res.toString());
			}
		}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * ID 찾기 : MDN 미입력
	 * </pre>
	 * 
	 */
	@Test(expected = StorePlatformException.class)
	public void c_searchId() {

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
				assertThat(res.getUserId(), notNullValue());
				logger.debug("response param : {}", res.toString());
			}
		}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

}
