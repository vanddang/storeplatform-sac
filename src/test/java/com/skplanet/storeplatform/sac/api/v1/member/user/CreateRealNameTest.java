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
import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateRealNameReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateRealNameRes;
import com.skplanet.storeplatform.sac.member.common.constant.TestMemberConstant;

/**
 * 실명인증정보 수정 테스트.
 * 
 * Updated on : 2014. 2. 3. Updated by : 심대진, 다모아 솔루션.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CreateRealNameTest {

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
	 * 실명인증정보 수정 테스트 (IDP회원 - 본인).
	 * </pre>
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Ignore
	@Test
	public void test1_createRealName() throws Exception {

		new TestCaseTemplate(this.mvc).url(TestMemberConstant.PREFIX_USER_PATH_DEV + "/createRealName/v1").httpMethod(HttpMethod.POST)
				.addHeaders("Accept", "application/json")
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {

						CreateRealNameReq reqJson = new CreateRealNameReq();

						/**
						 * 기존 IDP 회원
						 */
						reqJson.setUserKey("US201402061029476470001812");

						reqJson.setIsOwn("OWN"); // 실명인증 대상
						reqJson.setRealNameDate(DateUtil.getToday("yyyyMMddHHmmss")); // 실명인증 일시
						reqJson.setUserCi("CICICICICICICICICICICI"); // CI
						reqJson.setUserDi("DIDIDIDIDIDIDIDIDIDIDI"); // DI
						reqJson.setRealNameMethod("US011101"); // 실명인증 수단코드 (휴대폰 인증, IPIN 인증)
						reqJson.setRealNameSite("US011203"); // 실명인증 사이트 코드
						reqJson.setUserPhone("0101231232"); // 사용자 전화번호
						reqJson.setDeviceTelecom("US001201"); // 이동 통신사
						reqJson.setUserName("아무개"); // 사용자 이름
						reqJson.setUserBirthDay("19820324"); // 사용자 생년월일
						reqJson.setUserSex("M"); // 사용자 성별

						return reqJson;
					}
				}).success(CreateRealNameRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						CreateRealNameRes res = (CreateRealNameRes) result;
						assertThat(res.getUserKey(), notNullValue());
						System.out.println(res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 실명인증정보 수정 테스트 (IDP회원 - 법정대리인).
	 * </pre>
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test
	public void test2_createRealName() throws Exception {

		new TestCaseTemplate(this.mvc).url(TestMemberConstant.PREFIX_USER_PATH_DEV + "/createRealName/v1").httpMethod(HttpMethod.POST)
				.addHeaders("Accept", "application/json")
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {

						CreateRealNameReq reqJson = new CreateRealNameReq();

						/**
						 * 기존 IDP 회원
						 */
						reqJson.setUserKey("US201402061029476470001812");

						reqJson.setIsOwn("PARENT"); // 실명인증 대상
						reqJson.setParentType("F"); // 법정대리인 관계코드 (F/M/O)
						reqJson.setParentEmail("tlaeo00@naver.com"); // 법정대리인 이메일
						reqJson.setRealNameDate(DateUtil.getToday("yyyyMMddHHmmss")); // 실명인증 일시
						reqJson.setUserCi("CICICICICICICICICICICI"); // CI
						reqJson.setUserDi("DIDIDIDIDIDIDIDIDIDIDI"); // DI
						reqJson.setRealNameMethod("US011101"); // 실명인증 수단코드 (휴대폰 인증, IPIN 인증)
						reqJson.setRealNameSite("US011203"); // 실명인증 사이트 코드
						reqJson.setUserPhone("0101231232"); // 법정대리인 전화번호
						reqJson.setDeviceTelecom("US001201"); // 이동 통신사
						reqJson.setUserName("태백산"); // 사용자 이름
						reqJson.setUserBirthDay("19870506"); // 법정대리인 생년월일

						return reqJson;
					}
				}).success(CreateRealNameRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						CreateRealNameRes res = (CreateRealNameRes) result;
						assertThat(res.getUserKey(), notNullValue());
						System.out.println(res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 실명인증정보 수정 테스트 (통합회원 - 본인).
	 * </pre>
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Ignore
	@Test
	public void test3_createRealName() throws Exception {

		new TestCaseTemplate(this.mvc).url(TestMemberConstant.PREFIX_USER_PATH_DEV + "/createRealName/v1").httpMethod(HttpMethod.POST)
				.addHeaders("Accept", "application/json")
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {

						CreateRealNameReq reqJson = new CreateRealNameReq();

						/**
						 * 통합 IDP 회원
						 */
						reqJson.setUserKey("US201402101325037050002133");
						reqJson.setUserAuthKey("b29ef7ad8e279c67bdf4ce7cba019a0e3e9a6375");

						reqJson.setIsOwn("OWN"); // 실명인증 대상
						reqJson.setRealNameDate(DateUtil.getToday("yyyyMMddHHmmss")); // 실명인증 일시
						reqJson.setUserCi("ABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJ00003631"); // CI
						reqJson.setUserDi("DIDIDIDIDIDIDIDIDIDIDIDIDIDIDIDIDIDIDIDIDDIDIDIDIDIDID0000003631"); // DI
						reqJson.setRealNameMethod("US011101"); // 실명인증 수단코드 (휴대폰 인증, IPIN 인증)
						reqJson.setRealNameSite("US011203"); // 실명인증 사이트 코드
						reqJson.setUserPhone("01090556567"); // 사용자 전화번호
						reqJson.setDeviceTelecom("US001202"); // 이동 통신사
						reqJson.setUserName("심대진"); // 사용자 이름
						reqJson.setUserBirthDay("19790101"); // 사용자 생년월일
						reqJson.setUserSex("M"); // 사용자 성별

						return reqJson;
					}
				}).success(CreateRealNameRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						CreateRealNameRes res = (CreateRealNameRes) result;
						assertThat(res.getUserKey(), notNullValue());
						System.out.println(res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 실명인증정보 수정 테스트 (통합회원 - 법정대리인).
	 * </pre>
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Ignore
	@Test
	public void test4_createRealName() throws Exception {

		new TestCaseTemplate(this.mvc).url(TestMemberConstant.PREFIX_USER_PATH_DEV + "/createRealName/v1").httpMethod(HttpMethod.POST)
				.addHeaders("Accept", "application/json")
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {

						CreateRealNameReq reqJson = new CreateRealNameReq();

						/**
						 * 통합 IDP 회원
						 */
						reqJson.setUserKey("US201402061958132010001823");
						reqJson.setUserAuthKey("b29ef7ad8e279c67bdf4ce7cba019a0e3e9a6375");

						reqJson.setIsOwn("PARENT"); // 실명인증 대상
						reqJson.setRealNameDate(DateUtil.getToday("yyyyMMdd")); // 실명인증 일시 (YYYYMMDD)
						reqJson.setUserCi("ABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJ00003631"); // CI
						reqJson.setUserDi("DIDIDIDIDIDIDIDIDIDIDIDIDIDIDIDIDIDIDIDIDDIDIDIDIDIDID0000003631"); // DI
						reqJson.setRealNameMethod("US011101"); // 실명인증 수단코드 (휴대폰 인증, IPIN 인증)
						reqJson.setRealNameSite("US011203"); // 실명인증 사이트 코드
						reqJson.setUserPhone("01090556567"); // 사용자 전화번호
						reqJson.setDeviceTelecom("US001202"); // 이동 통신사
						reqJson.setUserName("심대진"); // 사용자 이름
						reqJson.setUserBirthDay("20020101"); // 사용자 생년월일
						reqJson.setUserSex("M"); // 사용자 성별

						return reqJson;
					}
				}).success(CreateRealNameRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						CreateRealNameRes res = (CreateRealNameRes) result;
						assertThat(res.getUserKey(), notNullValue());
						System.out.println(res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

}
