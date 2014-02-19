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
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateOcbInformationReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateOcbInformationRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.RemoveOcbInformationReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.RemoveOcbInformationRes;
import com.skplanet.storeplatform.sac.member.common.constant.TestMemberConstant;

/**
 * 회원 OCB 정보 삭제 테스트.
 * 
 * 테스트 시나리오.
 * 
 * 1. TEST_A : OCB 카드 정보를 등록한다.
 * 
 * 2. TEST_B : 등록된 OCB 카드 정보가 아닌 카드번호로 삭제를 시도한다. (ERROR CASE)
 * 
 * 3. TEST_C : 등록된 OCB 카드 정보를 삭제 한다.
 * 
 * Updated on : 2014. 2. 12. Updated by : 심대진, 다모아 솔루션.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RemoveOcbInformationTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mvc;

	/**
	 * <pre>
	 * 단위 테스트 안의 각 메소드가 실행될 때마다 실행 전에 수행.
	 * </pre>
	 */
	@Before
	public void before() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	/**
	 * <pre>
	 * 회원 OCB 정보 등록/수정.
	 * </pre>
	 */
	@Test
	public void TEST_A_OCB카드번호등록() {

		new TestCaseTemplate(this.mvc).url(TestMemberConstant.PREFIX_USER_PATH_REAL + "/createOcbInformation/v1").httpMethod(HttpMethod.POST)
				.addHeaders("Accept", "application/json")
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {

						CreateOcbInformationReq reqJson = new CreateOcbInformationReq();

						reqJson.setUserKey("US201401241550022950000616");
						reqJson.setCardNumber("3306-3306-3306-3306"); // 카드 번호 (암호화..??)
						/* 인증수단 코드 (OR003400 비인증, OR003401 카드번호인증, OR003402 주민번호인증) */
						reqJson.setAuthMethodCode("OR003401");

						return reqJson;
					}
				}).success(CreateOcbInformationRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						CreateOcbInformationRes res = (CreateOcbInformationRes) result;
						assertThat(res.getUserKey(), notNullValue());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * TEST ERROR CASE.
	 * 
	 * 회원 OCB 정보 삭제 (삭제가능한 유효한 카드번호가 아닐경우).
	 * </pre>
	 */
	@Test(expected = StorePlatformException.class)
	public void TEST_B_삭제가능한유효한카드번호가아닐경우() {

		new TestCaseTemplate(this.mvc).url(TestMemberConstant.PREFIX_USER_PATH_REAL + "/removeOcbInformation/v1").httpMethod(HttpMethod.POST)
				.addHeaders("Accept", "application/json")
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {

						RemoveOcbInformationReq reqJson = new RemoveOcbInformationReq();

						reqJson.setUserKey("US201401241550022950000616");
						reqJson.setCardNumber("11111111111111111111111111111111111111");

						return reqJson;
					}
				}).success(RemoveOcbInformationRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						RemoveOcbInformationRes res = (RemoveOcbInformationRes) result;
						assertThat(res.getUserKey(), notNullValue());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 회원 OCB 정보 삭제.
	 * </pre>
	 */
	@Test
	public void TEST_C_OCB카드번호삭제() {

		new TestCaseTemplate(this.mvc).url(TestMemberConstant.PREFIX_USER_PATH_REAL + "/removeOcbInformation/v1").httpMethod(HttpMethod.POST)
				.addHeaders("Accept", "application/json")
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {

						RemoveOcbInformationReq reqJson = new RemoveOcbInformationReq();

						reqJson.setUserKey("US201401241550022950000616");
						reqJson.setCardNumber("3306-3306-3306-3306");

						return reqJson;
					}
				}).success(RemoveOcbInformationRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						RemoveOcbInformationRes res = (RemoveOcbInformationRes) result;
						assertThat(res.getUserKey(), notNullValue());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

}
