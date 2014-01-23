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

import java.util.ArrayList;
import java.util.List;

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
import com.skplanet.storeplatform.sac.api.v1.member.constant.MemberTestConstant;
import com.skplanet.storeplatform.sac.client.member.vo.common.AgreementInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceExtraInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByAgreementReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByAgreementRes;

/**
 * ID 회원 약관 동의 가입 (One ID 회원) 테스트.
 * 
 * Updated on : 2014. 1. 13. Updated by : 심대진, 다모아 솔루션.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CreateByAgreementTest {

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
	 * ID 회원 약관 동의 가입 (One ID 회원) [[ 단말정보 미포함 ]].
	 * </pre>
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Ignore
	@Test
	public void test1_createByAgreementId() throws Exception {

		new TestCaseTemplate(this.mvc).url(MemberTestConstant.PREFIX_USER_PATH_REAL + "/createByAgreement/v1").httpMethod(HttpMethod.POST)
				.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.addHeaders("Accept", "application/json")
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {

						CreateByAgreementReq reqJson = new CreateByAgreementReq();

						// 사용자 아이디
						reqJson.setUserId("tlaeo004");

						// 단말 정보
						reqJson.setDeviceId(""); // 기기 ID
						reqJson.setDeviceIdType(""); // 기기 ID 타입
						reqJson.setDeviceTelecom(""); // 통신사
						reqJson.setNativeId(""); // 기기 고유 ID (IMEI)
						reqJson.setDeviceAccount(""); // 기기 계정 (Gmail)
						reqJson.setJoinId(""); // 가입채널코드
						reqJson.setIsRecvSms(""); // SMS 수신 여부

						// 동의 정보
						List<AgreementInfo> agreementList = new ArrayList<AgreementInfo>();
						AgreementInfo agreement1 = new AgreementInfo();
						agreement1.setExtraAgreementId("US010607");
						agreement1.setExtraAgreementVersion("0.1");
						agreement1.setIsExtraAgreement("Y");
						AgreementInfo agreement2 = new AgreementInfo();
						agreement2.setExtraAgreementId("US010608");
						agreement2.setExtraAgreementVersion("0.1");
						agreement2.setIsExtraAgreement("Y");
						AgreementInfo agreement3 = new AgreementInfo();
						agreement3.setExtraAgreementId("US010609");
						agreement3.setExtraAgreementVersion("0.1");
						agreement3.setIsExtraAgreement("Y");
						agreementList.add(agreement1);
						agreementList.add(agreement2);
						agreementList.add(agreement3);
						reqJson.setAgreementList(agreementList);

						return reqJson;
					}
				}).success(CreateByAgreementRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						CreateByAgreementRes res = (CreateByAgreementRes) result;
						assertThat(res.getUserKey(), notNullValue());
						System.out.println(res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * ID 회원 약관 동의 가입 (One ID 회원) [[ 단말정보 포함 MDN ]].
	 * </pre>
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test
	public void test2_createByAgreementDeviceMdn() throws Exception {

		new TestCaseTemplate(this.mvc).url(MemberTestConstant.PREFIX_USER_PATH_REAL + "/createByAgreement/v1").httpMethod(HttpMethod.POST)
				.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.addHeaders("Accept", "application/json")
				.addHeaders("x-planet-device-info", "model=\"SHW-M190S\",fwVersion=\"2.1.3_20101005f\",pkgVersion=\"com.skplanet.tstore.mobile/38\",rootDetection=\"no\"")
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {

						CreateByAgreementReq reqJson = new CreateByAgreementReq();

						// 사용자 아이디
						reqJson.setUserId("tlaeo004");

						// 단말 정보
						reqJson.setDeviceId("01090556567"); // 기기 ID
						reqJson.setDeviceIdType("msisdn"); // 기기 ID 타입
						reqJson.setDeviceTelecom("US012102"); // 통신사
						reqJson.setNativeId("A0000031648EE9"); // 기기 고유 ID (IMEI)
						reqJson.setDeviceAccount("tlaeo004@yahoo.co.kr"); // 기기 계정 (Gmail)
						reqJson.setJoinId("US002903"); // 가입채널코드
						reqJson.setIsRecvSms("Y"); // SMS 수신 여부

						// 단말 부가 정보 리스트
						List<DeviceExtraInfo> deviceExtraList = new ArrayList<DeviceExtraInfo>();
						DeviceExtraInfo deviceExtraInfo = new DeviceExtraInfo();
						deviceExtraInfo.setExtraProfile("US011407");
						deviceExtraInfo.setExtraProfileValue("3.0");

						deviceExtraList.add(deviceExtraInfo);
						reqJson.setDeviceExtraInfoList(deviceExtraList);

						// 동의 정보
						List<AgreementInfo> agreementList = new ArrayList<AgreementInfo>();
						AgreementInfo agreement1 = new AgreementInfo();
						agreement1.setExtraAgreementId("US010607");
						agreement1.setExtraAgreementVersion("0.1");
						agreement1.setIsExtraAgreement("Y");
						AgreementInfo agreement2 = new AgreementInfo();
						agreement2.setExtraAgreementId("US010608");
						agreement2.setExtraAgreementVersion("0.1");
						agreement2.setIsExtraAgreement("Y");
						AgreementInfo agreement3 = new AgreementInfo();
						agreement3.setExtraAgreementId("US010609");
						agreement3.setExtraAgreementVersion("0.1");
						agreement3.setIsExtraAgreement("Y");
						agreementList.add(agreement1);
						agreementList.add(agreement2);
						agreementList.add(agreement3);
						reqJson.setAgreementList(agreementList);

						return reqJson;
					}
				}).success(CreateByAgreementRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						CreateByAgreementRes res = (CreateByAgreementRes) result;
						assertThat(res.getUserKey(), notNullValue());
						System.out.println(res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 약관 미동의 테스트.
	 * </pre>
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Ignore
	@Test
	public void test4_errorTestCase() throws Exception {

		new TestCaseTemplate(this.mvc).url(MemberTestConstant.PREFIX_USER_PATH_REAL + "/createByAgreement/v1").httpMethod(HttpMethod.POST)
				.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.addHeaders("Accept", "application/json")
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {

						CreateByAgreementReq reqJson = new CreateByAgreementReq();

						// 사용자 아이디
						reqJson.setUserId("tlaeo00");

						// 단말 정보
						reqJson.setDeviceId(""); // 기기 ID
						reqJson.setDeviceIdType(""); // 기기 ID 타입
						reqJson.setDeviceTelecom(""); // 통신사
						reqJson.setNativeId(""); // 기기 고유 ID (IMEI)
						reqJson.setDeviceAccount(""); // 기기 계정 (Gmail)
						reqJson.setJoinId(""); // 가입채널코드
						reqJson.setIsRecvSms(""); // SMS 수신 여부

						// 동의 정보
						List<AgreementInfo> agreementList = new ArrayList<AgreementInfo>();
						AgreementInfo agreement1 = new AgreementInfo();
						agreement1.setExtraAgreementId("US010607");
						agreement1.setExtraAgreementVersion("0.1");
						agreement1.setIsExtraAgreement("Y");
						AgreementInfo agreement2 = new AgreementInfo();
						agreement2.setExtraAgreementId("US010608");
						agreement2.setExtraAgreementVersion("0.1");
						agreement2.setIsExtraAgreement("Y");
						AgreementInfo agreement3 = new AgreementInfo();
						agreement3.setExtraAgreementId("US010601");
						agreement3.setExtraAgreementVersion("0.1");
						agreement3.setIsExtraAgreement("Y");
						agreementList.add(agreement1);
						agreementList.add(agreement2);
						agreementList.add(agreement3);
						reqJson.setAgreementList(agreementList);

						return reqJson;
					}
				}).success(CreateByAgreementRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						CreateByAgreementRes res = (CreateByAgreementRes) result;
						assertThat(res.getUserKey(), notNullValue());
						System.out.println(res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

}
