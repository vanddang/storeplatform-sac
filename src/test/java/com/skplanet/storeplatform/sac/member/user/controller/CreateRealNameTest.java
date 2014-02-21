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
import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateRealNameReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateRealNameRes;
import com.skplanet.storeplatform.sac.member.common.constant.TestMemberConstant;
import com.skplanet.storeplatform.sac.member.common.util.TestConvertMapperUtils;

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
	 */
	@Test
	public void TEST_A_기존IDP회원실명인증등록_본인() {

		System.out.println("## >> " + new Exception().getStackTrace()[0].getMethodName());
		new TestCaseTemplate(this.mvc).url(TestMemberConstant.PREFIX_USER_PATH_REAL + "/createRealName/v1").httpMethod(HttpMethod.POST)
				.addHeaders("Accept", "application/json")
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {

						CreateRealNameReq reqJson = new CreateRealNameReq();

						/**
						 * 기존 IDP 회원
						 */
						reqJson.setUserKey("US201402061029476470001812");
						reqJson.setUserAuthKey("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

						reqJson.setIsOwn("OWN"); // 실명인증 대상
						reqJson.setRealNameDate(DateUtil.getToday("yyyyMMddHHmmss")); // 실명인증 일시
						reqJson.setUserCi("CICICICICICICICICICICI"); // CI
						reqJson.setUserDi("DIDIDIDIDIDIDIDIDIDIDI"); // DI
						reqJson.setRealNameMethod("US011101"); // 실명인증 수단코드 (휴대폰 인증, IPIN 인증)
						reqJson.setUserPhone("0101231232"); // 사용자 전화번호
						reqJson.setDeviceTelecom("US001201"); // 이동 통신사
						reqJson.setUserName("아무개"); // 사용자 이름
						reqJson.setUserBirthDay("19820324"); // 사용자 생년월일
						reqJson.setUserSex("M"); // 사용자 성별
						reqJson.setResident("local"); // 실명인증 대상 내•외국인 정보 (local : 내국인, foreign : 외국인)

						TestConvertMapperUtils.convertObjectToJson(reqJson);
						return reqJson;
					}
				}).success(CreateRealNameRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						CreateRealNameRes res = (CreateRealNameRes) result;
						assertThat(res.getUserKey(), notNullValue());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 실명인증정보 수정 테스트 (IDP회원 - 법정대리인).
	 * </pre>
	 */
	@Test
	public void TEST_B_기존IDP회원실명인증등록_법정대리인() {

		System.out.println("## >> " + new Exception().getStackTrace()[0].getMethodName());
		new TestCaseTemplate(this.mvc).url(TestMemberConstant.PREFIX_USER_PATH_REAL + "/createRealName/v1").httpMethod(HttpMethod.POST)
				.addHeaders("Accept", "application/json")
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {

						CreateRealNameReq reqJson = new CreateRealNameReq();

						/**
						 * 기존 IDP 회원
						 */
						reqJson.setUserKey("US201402061029476470001812");
						reqJson.setUserAuthKey("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

						reqJson.setIsOwn("PARENT"); // 실명인증 대상
						reqJson.setParentType("F"); // 법정대리인 관계코드 (F/M/O)
						reqJson.setParentEmail("tlaeo00@naver.com"); // 법정대리인 이메일
						reqJson.setRealNameDate(DateUtil.getToday("yyyyMMddHHmmss")); // 실명인증 일시
						reqJson.setUserCi("CICICICICICICICICICICI"); // CI
						reqJson.setUserDi("DIDIDIDIDIDIDIDIDIDIDI"); // DI
						reqJson.setRealNameMethod("US011101"); // 실명인증 수단코드 (휴대폰 인증, IPIN 인증)
						reqJson.setUserPhone("0101231232"); // 법정대리인 전화번호
						reqJson.setDeviceTelecom("US001201"); // 이동 통신사
						reqJson.setUserName("태백산"); // 사용자 이름
						reqJson.setUserBirthDay("19870506"); // 법정대리인 생년월일
						reqJson.setUserSex("M"); // 사용자 성별
						reqJson.setResident("local"); // 실명인증 대상 내•외국인 정보 (local : 내국인, foreign : 외국인)

						TestConvertMapperUtils.convertObjectToJson(reqJson);
						return reqJson;
					}
				}).success(CreateRealNameRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						CreateRealNameRes res = (CreateRealNameRes) result;
						assertThat(res.getUserKey(), notNullValue());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 실명인증정보 수정 테스트 (통합회원 - 본인).
	 * </pre>
	 */
	@Test
	public void TEST_C_통합IDP회원실명인증등록_본인() {

		System.out.println("## >> " + new Exception().getStackTrace()[0].getMethodName());
		new TestCaseTemplate(this.mvc).url(TestMemberConstant.PREFIX_USER_PATH_REAL + "/createRealName/v1").httpMethod(HttpMethod.POST)
				.addHeaders("Accept", "application/json")
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {

						CreateRealNameReq reqJson = new CreateRealNameReq();

						/**
						 * 통합 IDP 회원
						 */
						reqJson.setUserKey("US201402141711554720002579");
						reqJson.setUserAuthKey("b29ef7ad8e279c67bdf4ce7cba019a0e3e9a6375");

						reqJson.setIsOwn("OWN"); // 실명인증 대상
						reqJson.setRealNameDate(DateUtil.getToday("yyyyMMddHHmmss")); // 실명인증 일시
						reqJson.setUserCi("ABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJ00003631"); // CI
						reqJson.setUserDi("DIDIDIDIDIDIDIDIDIDIDIDIDIDIDIDIDIDIDIDIDDIDIDIDIDIDID0000003631"); // DI
						reqJson.setRealNameMethod("US011101"); // 실명인증 수단코드 (휴대폰 인증, IPIN 인증)
						reqJson.setUserPhone("01090556567"); // 사용자 전화번호
						reqJson.setDeviceTelecom("US001202"); // 이동 통신사
						reqJson.setUserName("홍말똥"); // 사용자 이름
						reqJson.setUserBirthDay("19790101"); // 사용자 생년월일
						reqJson.setUserSex("M"); // 사용자 성별
						reqJson.setResident("local"); // 실명인증 대상 내•외국인 정보 (local : 내국인, foreign : 외국인)

						TestConvertMapperUtils.convertObjectToJson(reqJson);
						return reqJson;
					}
				}).success(CreateRealNameRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						CreateRealNameRes res = (CreateRealNameRes) result;
						assertThat(res.getUserKey(), notNullValue());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 실명인증정보 수정 테스트 (통합회원 - 법정대리인).
	 * </pre>
	 */
	@Test
	public void TEST_D_통합IDP회원실명인증등록_법정대리인() {

		System.out.println("## >> " + new Exception().getStackTrace()[0].getMethodName());
		new TestCaseTemplate(this.mvc).url(TestMemberConstant.PREFIX_USER_PATH_REAL + "/createRealName/v1").httpMethod(HttpMethod.POST)
				.addHeaders("Accept", "application/json")
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {

						CreateRealNameReq reqJson = new CreateRealNameReq();

						/**
						 * 통합 IDP 회원
						 */
						reqJson.setUserKey("US201402141711554720002579");
						reqJson.setUserAuthKey("b29ef7ad8e279c67bdf4ce7cba019a0e3e9a6375");

						reqJson.setIsOwn("PARENT"); // 실명인증 대상
						reqJson.setRealNameDate(DateUtil.getToday("yyyyMMdd")); // 실명인증 일시 (YYYYMMDD)
						reqJson.setUserCi("ABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJ00003631"); // CI
						reqJson.setUserDi("DIDIDIDIDIDIDIDIDIDIDIDIDIDIDIDIDIDIDIDIDDIDIDIDIDIDID0000003631"); // DI
						reqJson.setRealNameMethod("US011101"); // 실명인증 수단코드 (휴대폰 인증, IPIN 인증)
						reqJson.setUserPhone("01090556567"); // 사용자 전화번호
						reqJson.setDeviceTelecom("US001202"); // 이동 통신사
						reqJson.setUserName("심대진"); // 사용자 이름
						reqJson.setUserBirthDay("20020101"); // 사용자 생년월일
						reqJson.setUserSex("M"); // 사용자 성별
						reqJson.setResident("local"); // 실명인증 대상 내•외국인 정보 (local : 내국인, foreign : 외국인)

						TestConvertMapperUtils.convertObjectToJson(reqJson);
						return reqJson;
					}
				}).success(CreateRealNameRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						CreateRealNameRes res = (CreateRealNameRes) result;
						assertThat(res.getUserKey(), notNullValue());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 실명인증정보 수정 테스트 (법인).
	 * </pre>
	 */
	@Test
	public void TEST_E_실명인증등록_법인단말인경우() {

		System.out.println("## >> " + new Exception().getStackTrace()[0].getMethodName());
		new TestCaseTemplate(this.mvc).url(TestMemberConstant.PREFIX_USER_PATH_REAL + "/createRealName/v1").httpMethod(HttpMethod.POST)
				.addHeaders("Accept", "application/json")
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {

						CreateRealNameReq reqJson = new CreateRealNameReq();

						/**
						 * 통합 IDP 회원
						 */
						reqJson.setUserKey("US201402141746333040002583");
						reqJson.setUserAuthKey("b29ef7ad8e279c67bdf4ce7cba019a0e3e9a6375");

						reqJson.setIsOwn("CORP"); // 실명인증 대상
						reqJson.setResident("local"); // 실명인증 대상 내•외국인 정보 (local : 내국인, foreign : 외국인)
						reqJson.setUserName("법인단말"); // 이름
						reqJson.setUserBirthDay("99991231"); // 생년월일

						TestConvertMapperUtils.convertObjectToJson(reqJson);
						return reqJson;

					}
				}).success(CreateRealNameRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						CreateRealNameRes res = (CreateRealNameRes) result;
						assertThat(res.getUserKey(), notNullValue());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * TEST ERROR CASE.
	 * 
	 * 실명인증정보 수정 테스트 (통합회원 - 이미 통합사이트에서 본인인증한 회원).
	 * </pre>
	 */
	@Test(expected = StorePlatformException.class)
	public void TEST_F_통합IDP회원실명인증등록_이미통합사이트에서본인인증한회원() {

		System.out.println("## >> " + new Exception().getStackTrace()[0].getMethodName());
		new TestCaseTemplate(this.mvc).url(TestMemberConstant.PREFIX_USER_PATH_REAL +
				"/createRealName/v1").httpMethod(HttpMethod.POST)
				.addHeaders("Accept", "application/json")
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {

						CreateRealNameReq reqJson = new CreateRealNameReq();

						/**
						 * 통합 IDP 회원
						 */
						// simdae12 (사이트에서 가입하고 이용동의 가입한다. 사이트 들어가서 본인인증 한다.)
						reqJson.setUserKey("US201402141746333040002583");
						reqJson.setUserAuthKey("b29ef7ad8e279c67bdf4ce7cba019a0e3e9a6375");

						reqJson.setIsOwn("OWN"); // 실명인증 대상
						reqJson.setRealNameDate(DateUtil.getToday("yyyyMMddHHmmss")); // 실명인증 일시
						reqJson.setUserCi("ABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJ00003631"); // CI
						reqJson.setUserDi("DIDIDIDIDIDIDIDIDIDIDIDIDIDIDIDIDIDIDIDIDDIDIDIDIDIDID0000003631"); // DI
						reqJson.setRealNameMethod("US011101"); // 실명인증 수단코드 (휴대폰 인증, IPIN 인증)
						reqJson.setUserPhone("01090556567"); // 사용자 전화번호
						reqJson.setDeviceTelecom("US001202"); // 이동 통신사
						reqJson.setUserName("홍길동"); // 사용자 이름
						reqJson.setUserBirthDay("19790101"); // 사용자 생년월일
						reqJson.setUserSex("M"); // 사용자 성별
						reqJson.setResident("local"); // 실명인증 대상 내•외국인 정보 (local : 내국인, foreign : 외국인)

						TestConvertMapperUtils.convertObjectToJson(reqJson);
						return reqJson;

					}
				}).success(CreateRealNameRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						CreateRealNameRes res = (CreateRealNameRes) result;
						assertThat(res.getUserKey(), notNullValue());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

}
