/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.user.controller;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
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

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.test.RequestBodySetter;
import com.skplanet.storeplatform.framework.test.SuccessCallback;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate.RunMode;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyPasswordReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyPasswordRes;
import com.skplanet.storeplatform.sac.member.common.constant.TestMemberConstant;
import com.skplanet.storeplatform.sac.member.common.util.TestConvertMapperUtils;

/**
 * 회원 비밀번호 수정 테스트.
 * 
 * Updated on : 2014. 2. 17. Updated by : 심대진, 다모아 솔루션.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ModifyPasswordTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mvc;

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Before
	public void before() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	/**
	 * <pre>
	 * 회원 비밀번호 수정 테스트 (IDP 회원).
	 * </pre>
	 */
	@Test
	public void TEST_A_기존IDP회원비밀번호수정() {

		new TestCaseTemplate(this.mvc).url(TestMemberConstant.PREFIX_USER_PATH_REAL + "/modifyPassword/v1").httpMethod(HttpMethod.POST)
				.addHeaders("Accept", "application/json")
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {

						ModifyPasswordReq reqJson = new ModifyPasswordReq();

						/**
						 * 기존 IDP 회원
						 */
						reqJson.setUserKey("US201402181518403440002941");
						reqJson.setUserAuthKey("b29ef7ad8e279c67bdf4ce7cba019a0e3e9a6375");
						// reqJson.setUserAuthKey("4c77e935ee2f4eae8dcf2d22b199d504"); // Fixed userAuthKey
						reqJson.setOldPassword("12qwer"); // 기존 패스워드.
						reqJson.setNewPassword("12qwer"); // 신규 패스워드.

						TestConvertMapperUtils.convertObjectToJson(reqJson);
						return reqJson;
					}
				}).success(ModifyPasswordRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						ModifyPasswordRes res = (ModifyPasswordRes) result;
						assertThat(res.getUserKey(), notNullValue());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 회원 비밀번호 수정 테스트 (IDP 회원 - 비밀번호 일치 하지 않음).
	 * </pre>
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test(expected = StorePlatformException.class)
	public void TEST_B_기존IDP회원비밀번호일치하지않음() throws Exception {

		new TestCaseTemplate(this.mvc).url(TestMemberConstant.PREFIX_USER_PATH_REAL + "/modifyPassword/v1").httpMethod(HttpMethod.POST)
				.addHeaders("Accept", "application/json")
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {

						ModifyPasswordReq reqJson = new ModifyPasswordReq();

						/**
						 * 기존 IDP 회원
						 */
						reqJson.setUserKey("US201402181518403440002941");
						reqJson.setUserAuthKey("b29ef7ad8e279c67bdf4ce7cba019a0e3e9a6375");
						reqJson.setOldPassword("111111"); // 기존 패스워드.
						reqJson.setNewPassword("111111"); // 신규 패스워드.

						TestConvertMapperUtils.convertObjectToJson(reqJson);
						return reqJson;
					}
				}).success(ModifyPasswordRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						ModifyPasswordRes res = (ModifyPasswordRes) result;
						assertThat(res.getUserKey(), notNullValue());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 회원 비밀번호 수정 테스트 (통합 회원).
	 * </pre>
	 */
	@Test
	public void TEST_C_통합IDP회원비밀번호수정() {

		new TestCaseTemplate(this.mvc).url(TestMemberConstant.PREFIX_USER_PATH_REAL + "/modifyPassword/v1").httpMethod(HttpMethod.POST)
				.addHeaders("Accept", "application/json")
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {

						ModifyPasswordReq reqJson = new ModifyPasswordReq();

						/**
						 * 통합 IDP 회원
						 */
						reqJson.setUserKey("US201402061944577200001815");
						reqJson.setUserAuthKey("b29ef7ad8e279c67bdf4ce7cba019a0e3e9a6375");
						// reqJson.setUserAuthKey("4c77e935ee2f4eae8dcf2d22b199d504"); // Fixed userAuthKey
						reqJson.setOldPassword("!@34qwer"); // 기존 패스워드.
						reqJson.setNewPassword("!@34qwer"); // 신규 패스워드.

						TestConvertMapperUtils.convertObjectToJson(reqJson);
						return reqJson;
					}
				}).success(ModifyPasswordRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						ModifyPasswordRes res = (ModifyPasswordRes) result;
						assertThat(res.getUserKey(), notNullValue());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 회원 비밀번호 수정 테스트 (통합 회원 - 비밀번호 일치 하지 않음).
	 * </pre>
	 */
	@Test(expected = StorePlatformException.class)
	public void TEST_D_통합IDP회원비밀번호일치하지않음() {

		new TestCaseTemplate(this.mvc).url(TestMemberConstant.PREFIX_USER_PATH_REAL + "/modifyPassword/v1").httpMethod(HttpMethod.POST)
				.addHeaders("Accept", "application/json")
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {

						ModifyPasswordReq reqJson = new ModifyPasswordReq();

						/**
						 * 통합 IDP 회원
						 */
						reqJson.setUserKey("US201402061944577200001815");
						reqJson.setUserAuthKey("b29ef7ad8e279c67bdf4ce7cba019a0e3e9a6375");
						// reqJson.setUserAuthKey("4c77e935ee2f4eae8dcf2d22b199d504"); // Fixed userAuthKey
						reqJson.setOldPassword("111111"); // 기존 패스워드.
						reqJson.setNewPassword("111111"); // 신규 패스워드.

						TestConvertMapperUtils.convertObjectToJson(reqJson);
						return reqJson;
					}
				}).success(ModifyPasswordRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						ModifyPasswordRes res = (ModifyPasswordRes) result;
						assertThat(res.getUserKey(), notNullValue());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

}
