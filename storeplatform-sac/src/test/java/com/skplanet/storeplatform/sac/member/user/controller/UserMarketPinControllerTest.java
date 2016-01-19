package com.skplanet.storeplatform.sac.member.user.controller;

import com.skplanet.storeplatform.framework.test.RequestBodySetter;
import com.skplanet.storeplatform.framework.test.SuccessCallback;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate.RunMode;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateUserMarketPinReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateUserMarketPinRes;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@ActiveProfiles(value = "local")

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserMarketPinControllerTest {
	private static final Logger logger = LoggerFactory.getLogger(UserMarketPinControllerTest.class);

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void before() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	/**
	 * I01000146 회원 Market PIN 등록
	 */
	@Test
	public void I01000146_회원_Market_PIN_등록() {
		final String testUserKey = "IW1313866820140408161311";
		final String testPinNo = "1234";
		new TestCaseTemplate(this.mockMvc).url("/member/user/createMarketPin/v1")
				.httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						CreateUserMarketPinReq req = new CreateUserMarketPinReq();
						req.setUserKey(testUserKey);
						req.setPinNo(testPinNo);

						logger.info("request param : {}", req.toString());
						return req;
					}
				})
				.success(CreateUserMarketPinRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						CreateUserMarketPinRes res = (CreateUserMarketPinRes) result;
						assertThat(res.getUserKey(), notNullValue());
						logger.info("CreateMarketPinRes : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
	}

}
