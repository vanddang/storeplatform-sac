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
import org.junit.Ignore;
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

import com.skplanet.storeplatform.framework.test.RequestBodySetter;
import com.skplanet.storeplatform.framework.test.SuccessCallback;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate.RunMode;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyRes;
import com.skplanet.storeplatform.sac.member.common.constant.TestMemberConstant;

/**
 * 회원정보 수정 테스트.
 * 
 * Updated on : 2014. 1. 24. Updated by : 심대진, 다모아 솔루션.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ModifyTest {

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
	 * 회원정보 수정 테스트 (IDP 회원).
	 * </pre>
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test
	public void test1_modifyIdp() throws Exception {

		new TestCaseTemplate(this.mvc).url(TestMemberConstant.PREFIX_USER_PATH_REAL + "/modify/v1").httpMethod(HttpMethod.POST)
				.addHeaders("Accept", "application/json")
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {

						ModifyReq reqJson = new ModifyReq();

						/**
						 * 기존 IDP 회원
						 */
						reqJson.setUserKey("US201401231555153430000447");
						reqJson.setUserAuthKey("b29ef7ad8e279c67bdf4ce7cba019a0e3e9a6375");

						reqJson.setUserPhone("01011111112"); // 사용자 연락처 (Sync 대상 - IDP)
						reqJson.setIsRecvSms("N"); // SMS 수신 여부
						reqJson.setIsRecvEmail("N"); // 이메일 수신여부
						reqJson.setUserSex("M"); // 성별 (Sync 대상 - IDP)
						reqJson.setUserBirthDay("19820328"); // 사용자 생년월일 (Sync 대상 - IDP)
						reqJson.setUserZip("123123"); // 우편번호 (Sync 대상 - IDP|통합)
						reqJson.setUserAddress("경기도 성남시 분당구"); // 주소 (Sync 대상 - IDP|통합)
						reqJson.setUserDetailAddress("H스퀘어"); // 상세주소 (Sync 대상 - IDP|통합)
						reqJson.setUserCalendar("2"); // 생년월일 (1 : 양력, 2 : 음력)

						/**
						 * 외국인
						 */
						reqJson.setUserCity("Pittsburgh");
						reqJson.setUserState("Pennsylvania");

						return reqJson;
					}
				}).success(ModifyRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						ModifyRes res = (ModifyRes) result;
						assertThat(res.getUserKey(), notNullValue());
						System.out.println(res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 회원정보 수정 테스트 (통합 IDP 회원).
	 * </pre>
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Ignore
	@Test
	public void test2_modifyImIdp() throws Exception {

		new TestCaseTemplate(this.mvc).url(TestMemberConstant.PREFIX_USER_PATH_REAL + "/modify/v1").httpMethod(HttpMethod.POST)
				.addHeaders("Accept", "application/json")
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {

						ModifyReq reqJson = new ModifyReq();

						/**
						 * One ID 회원
						 */
						reqJson.setUserKey("US201401241550022950000616");
						reqJson.setUserAuthKey("01f3af5e6d8f7d4643c914cf7ae42b283270b232");

						reqJson.setIsRecvSms("N"); // SMS 수신 여부
						reqJson.setIsRecvEmail("N"); // 이메일 수신여부
						reqJson.setUserZip("123123"); // 우편번호 (Sync 대상 - IDP|통합)
						reqJson.setUserAddress("경기도 성남시 분당구"); // 주소 (Sync 대상 - IDP|통합)
						reqJson.setUserDetailAddress("H스퀘어"); // 상세주소 (Sync 대상 - IDP|통합)
						reqJson.setUserCalendar("2"); // 생년월일 (1 : 양력, 2 : 음력)

						/**
						 * 외국인
						 */
						reqJson.setUserCity("Pittsburgh");
						reqJson.setUserState("Pennsylvania");

						return reqJson;
					}
				}).success(ModifyRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						ModifyRes res = (ModifyRes) result;
						assertThat(res.getUserKey(), notNullValue());
						System.out.println(res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

}
