/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.api.v1.member.user;

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

import com.skplanet.storeplatform.framework.test.RequestBodySetter;
import com.skplanet.storeplatform.framework.test.SuccessCallback;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate.RunMode;
import com.skplanet.storeplatform.sac.api.v1.member.constant.MemberTestConstant;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyRes;

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
public class UserModifyTest {

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
	 * 회원정보 수정 테스트.
	 * </pre>
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test
	public void test1_modify() throws Exception {

		new TestCaseTemplate(this.mvc).url(MemberTestConstant.PREFIX_USER_PATH_DEV + "/modify/v1").httpMethod(HttpMethod.POST)
				.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.addHeaders("Accept", "application/json")
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {

						ModifyReq reqJson = new ModifyReq();

						/**
						 * One ID 회원
						 */
						// reqJson.setUserKey("US201401241550022950000616");
						// reqJson.setUserAuthKey("01f3af5e6d8f7d4643c914cf7ae42b283270b232");

						/**
						 * 기존 IDP 회원
						 */
						reqJson.setUserKey("US201401231555153430000447");
						reqJson.setUserAuthKey("b29ef7ad8e279c67bdf4ce7cba019a0e3e9a6375");

						/**
						 * 사용자 연락처 정보...?? (휴대기기 정보 아님....)
						 */
						reqJson.setDeviceTelecom("US001202"); // 이동통신사
						reqJson.setUserPhoneCountry("82"); // 국가코드
						reqJson.setUserPhone("01011111112"); // 사용자 연락처

						reqJson.setIsRecvSms("N"); // SMS 수신 여부
						reqJson.setIsRecvEmail("Y"); // 이메일 수신여부
						reqJson.setUserSex("NODATA"); // 성별
						reqJson.setUserBirthDay("19820328"); // 사용자 생년월일
						reqJson.setUserZip("151919"); // 우편번호
						reqJson.setUserAddress("서울 관악구 낙성대동 서울대연구공원단지"); // 주소
						reqJson.setUserDetailAddress("SKT연구소"); // 상세주소

						reqJson.setUserCalendar("1"); // 생년월일 (1 : 양력, 2 : 음력)

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
