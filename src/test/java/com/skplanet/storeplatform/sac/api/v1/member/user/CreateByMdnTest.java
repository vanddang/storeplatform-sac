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

import com.skplanet.storeplatform.framework.test.RequestBodySetter;
import com.skplanet.storeplatform.framework.test.SuccessCallback;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate.RunMode;
import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.client.member.vo.common.AgreementInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceExtraInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByMdnReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByMdnRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.WithdrawReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.WithdrawRes;
import com.skplanet.storeplatform.sac.member.common.constant.TestMemberConstant;

/**
 * 모바일 전용 회원 가입 (MDN 회원 가입)
 * 
 * Updated on : 2014. 1. 16. Updated by : 심대진, 다모아 솔루션.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CreateByMdnTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(CreateByMdnTest.class);

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mvc;

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * void
	 */
	@Before
	public void before() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	/**
	 * <pre>
	 * MDN 회원 가입 테스트(법정대리인).
	 * </pre>
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Ignore
	@Test
	public void test1_createByMdn() throws Exception {

		new TestCaseTemplate(this.mvc).url(TestMemberConstant.PREFIX_USER_PATH_REAL + "/createByMdn/v1").httpMethod(HttpMethod.POST)
				.addHeaders("Accept", "application/json")
				.addHeaders("x-planet-device-info", "model=\"SHW-M190S\",fwVersion=\"2.1.3_20101005f\",pkgVersion=\"com.skplanet.tstore.mobile/38\",rootDetection=\"no\"")
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {

						CreateByMdnReq reqJson = new CreateByMdnReq();

						// 단말 정보
						reqJson.setDeviceId("01012346480"); // 기기 ID
						reqJson.setDeviceIdType("msisdn"); // 기기 ID 타입
						reqJson.setDeviceTelecom("US001202"); // 통신사
						reqJson.setNativeId("A0000031648EE9"); // 기기 고유 ID (IMEI)
						reqJson.setDeviceAccount("mdntest@gmail.com"); // 기기 계정 (Gmail)
						reqJson.setJoinId("US002903"); // 가입채널코드
						reqJson.setIsRecvSms("Y"); // SMS 수신 여부
						reqJson.setOwnBirth("20020328"); // 본인의 생년월일

						// 단말 부가 정보 리스트
						List<DeviceExtraInfo> deviceExtraList = new ArrayList<DeviceExtraInfo>();
						DeviceExtraInfo deviceExtraInfo = new DeviceExtraInfo();
						deviceExtraInfo.setExtraProfile("US011407");
						deviceExtraInfo.setExtraProfileValue("3.0");

						deviceExtraList.add(deviceExtraInfo);
						reqJson.setDeviceExtraInfoList(deviceExtraList);

						// 법정 대리인 정보 (isParent 값이 Y 일경우 등록 된다.)
						reqJson.setIsParent("Y"); // 법정대리인정보 등록 여부.
						reqJson.setParentRealNameMethod("US011101");
						reqJson.setParentName("심대진");
						reqJson.setParentType("F");
						reqJson.setParentDate(DateUtil.getToday());
						reqJson.setParentEmail("hkd@aaaa.com");
						reqJson.setParentBirthDay("19700331");
						reqJson.setParentTelecom("US001202");
						reqJson.setParentPhone("01088889999");
						reqJson.setParentCi("skpone0000132653GWyh3WsEm0FutitO5oSgC2/SgSrLKv5XohA8mxTNLitpB1 B9A3z5zrVHettHzKa5dpJA==");
						reqJson.setParentRealNameDate(DateUtil.getToday());
						reqJson.setParentRealNameSite("US011203"); // shop client 3.0

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
				}).success(CreateByMdnRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						CreateByMdnRes res = (CreateByMdnRes) result;
						assertThat(res.getUserKey(), notNullValue());
						LOGGER.debug(res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * MDN 회원 가입 테스트(법정대리인 없이).
	 * </pre>
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Ignore
	@Test
	public void test2_createByMdn() throws Exception {

		new TestCaseTemplate(this.mvc).url(TestMemberConstant.PREFIX_USER_PATH_REAL + "/createByMdn/v1").httpMethod(HttpMethod.POST)
				.addHeaders("Accept", "application/json")
				.addHeaders("x-planet-device-info", "model=\"SHW-M190S\",fwVersion=\"2.1.3_20101005f\",pkgVersion=\"com.skplanet.tstore.mobile/38\",rootDetection=\"no\"")
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {

						CreateByMdnReq reqJson = new CreateByMdnReq();

						// 단말 정보
						reqJson.setDeviceId("01002346470"); // 기기 ID
						reqJson.setDeviceIdType("msisdn"); // 기기 ID 타입
						reqJson.setDeviceTelecom("US001202"); // 통신사
						reqJson.setNativeId("A0000031648EE9"); // 기기 고유 ID (IMEI)
						reqJson.setDeviceAccount("mdntest@gmail.com"); // 기기 계정 (Gmail)
						reqJson.setJoinId("US002903"); // 가입채널코드
						reqJson.setIsRecvSms("Y"); // SMS 수신 여부
						reqJson.setOwnBirth("19820328"); // 본인의 생년월일

						// 단말 부가 정보 리스트
						List<DeviceExtraInfo> deviceExtraList = new ArrayList<DeviceExtraInfo>();
						DeviceExtraInfo deviceExtraInfo = new DeviceExtraInfo();
						deviceExtraInfo.setExtraProfile("US011407");
						deviceExtraInfo.setExtraProfileValue("3.0");

						deviceExtraList.add(deviceExtraInfo);
						reqJson.setDeviceExtraInfoList(deviceExtraList);

						// 법정 대리인 정보 (isParent 값이 Y 일경우 등록 된다.)
						reqJson.setIsParent("N"); // 법정대리인정보 등록 여부.
						reqJson.setParentRealNameMethod("");
						reqJson.setParentName("");
						reqJson.setParentType("");
						reqJson.setParentDate("");
						reqJson.setParentEmail("");
						reqJson.setParentBirthDay("");
						reqJson.setParentTelecom("");
						reqJson.setParentPhone("");
						reqJson.setParentCi("");
						reqJson.setParentRealNameDate("");
						reqJson.setParentRealNameSite(""); // shop client 3.0

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
				}).success(CreateByMdnRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						CreateByMdnRes res = (CreateByMdnRes) result;
						assertThat(res.getUserKey(), notNullValue());
						LOGGER.debug(res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * UUID 회원 가입 테스트.
	 * </pre>
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Ignore
	@Test
	public void test3_createByUuid() throws Exception {

		new TestCaseTemplate(this.mvc).url(TestMemberConstant.PREFIX_USER_PATH_REAL + "/createByMdn/v1").httpMethod(HttpMethod.POST)
				.addHeaders("Accept", "application/json")
				.addHeaders("x-planet-device-info", "model=\"SHW-M190S\",fwVersion=\"2.1.3_20101005f\",pkgVersion=\"com.skplanet.tstore.mobile/38\",rootDetection=\"no\"")
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {

						CreateByMdnReq reqJson = new CreateByMdnReq();

						// 단말 정보
						reqJson.setDeviceId("550e8400e29b41d4a716446655440000"); // 기기 ID
						reqJson.setDeviceIdType("uuid"); // 기기 ID 타입
						reqJson.setDeviceTelecom("US001207"); // 통신사 (uuid 일경우 ISO 고정)
						reqJson.setNativeId("A0000031648EE9"); // 기기 고유 ID (IMEI)
						reqJson.setDeviceAccount("mdntest@gmail.com"); // 기기 계정 (Gmail)
						reqJson.setJoinId("US002903"); // 가입채널코드
						reqJson.setIsRecvSms("Y"); // SMS 수신 여부
						reqJson.setOwnBirth("20020328"); // 본인의 생년월일

						// 단말 부가 정보
						DeviceExtraInfo deviceExtraInfo = new DeviceExtraInfo();
						deviceExtraInfo.setExtraProfile("US011407");
						deviceExtraInfo.setExtraProfileValue("3.0");

						// 법정 대리인 정보 (isParent 값이 Y 일경우 등록 된다.)
						reqJson.setIsParent("Y"); // 법정대리인정보 등록 여부.
						reqJson.setParentRealNameMethod("US011101");
						reqJson.setParentName("홍길동");
						reqJson.setParentType("F");
						reqJson.setParentDate(DateUtil.getToday());
						reqJson.setParentEmail("hkd@aaaa.com");
						reqJson.setParentBirthDay("19700331");
						reqJson.setParentTelecom("US001202");
						reqJson.setParentPhone("01088889999");
						reqJson.setParentCi("skpone0000132653GWyh3WsEm0FutitO5oSgC2/SgSrLKv5XohA8mxTNLitpB1 B9A3z5zrVHettHzKa5dpJA==");
						reqJson.setParentRealNameDate(DateUtil.getToday());
						reqJson.setParentRealNameSite("US011203"); // shop client 3.0

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
				}).success(CreateByMdnRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						CreateByMdnRes res = (CreateByMdnRes) result;
						assertThat(res.getUserKey(), notNullValue());
						LOGGER.debug(res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * MAC 회원 가입 테스트.
	 * </pre>
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Ignore
	@Test
	public void test4_createByMac() throws Exception {

		new TestCaseTemplate(this.mvc).url(TestMemberConstant.PREFIX_USER_PATH_REAL + "/createByMdn/v1").httpMethod(HttpMethod.POST)
				.addHeaders("Accept", "application/json")
				.addHeaders("x-planet-device-info", "model=\"SHW-M190S\",fwVersion=\"2.1.3_20101005f\",pkgVersion=\"com.skplanet.tstore.mobile/38\",rootDetection=\"no\"")
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {

						CreateByMdnReq reqJson = new CreateByMdnReq();

						// 단말 정보
						reqJson.setDeviceId("B102E3010201"); // 기기 ID
						reqJson.setDeviceIdType("mac"); // 기기 ID 타입
						reqJson.setDeviceTelecom("US001207"); // 통신사 (uuid 일경우 ISO 고정)
						reqJson.setNativeId("A0000031648EE9"); // 기기 고유 ID (IMEI)
						reqJson.setDeviceAccount("mdntest@gmail.com"); // 기기 계정 (Gmail)
						reqJson.setJoinId("US002903"); // 가입채널코드
						reqJson.setIsRecvSms("Y"); // SMS 수신 여부
						reqJson.setOwnBirth("20020328"); // 본인의 생년월일

						// 단말 부가 정보
						DeviceExtraInfo deviceExtraInfo = new DeviceExtraInfo();
						deviceExtraInfo.setExtraProfile("US011407");
						deviceExtraInfo.setExtraProfileValue("3.0");

						// 법정 대리인 정보 (isParent 값이 Y 일경우 등록 된다.)
						reqJson.setIsParent("Y"); // 법정대리인정보 등록 여부.
						reqJson.setParentRealNameMethod("US011101");
						reqJson.setParentName("홍길동");
						reqJson.setParentType("F");
						reqJson.setParentDate(DateUtil.getToday());
						reqJson.setParentEmail("hkd@aaaa.com");
						reqJson.setParentBirthDay("19700331");
						reqJson.setParentTelecom("US001202");
						reqJson.setParentPhone("01088889999");
						reqJson.setParentCi("skpone0000132653GWyh3WsEm0FutitO5oSgC2/SgSrLKv5XohA8mxTNLitpB1 B9A3z5zrVHettHzKa5dpJA==");
						reqJson.setParentRealNameDate(DateUtil.getToday());
						reqJson.setParentRealNameSite("US011203"); // shop client 3.0

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
				}).success(CreateByMdnRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						CreateByMdnRes res = (CreateByMdnRes) result;
						assertThat(res.getUserKey(), notNullValue());
						LOGGER.debug(res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 필수 약관 미동의 사용자 테스트.
	 * </pre>
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test(expected = RuntimeException.class)
	public void test5_errorTestCase() throws Exception {

		new TestCaseTemplate(this.mvc).url(TestMemberConstant.PREFIX_USER_PATH_REAL + "/createByMdn/v1").httpMethod(HttpMethod.POST)
				.addHeaders("Accept", "application/json")
				.addHeaders("x-planet-device-info", "model=\"SHW-M190S\",fwVersion=\"2.1.3_20101005f\",pkgVersion=\"com.skplanet.tstore.mobile/38\",rootDetection=\"no\"")
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {

						CreateByMdnReq reqJson = new CreateByMdnReq();

						// 단말 정보
						reqJson.setDeviceId("01076771470"); // 기기 ID
						reqJson.setDeviceIdType("msisdn"); // 기기 ID 타입
						reqJson.setDeviceTelecom("US001202"); // 통신사
						reqJson.setNativeId("A0000031648EE9"); // 기기 고유 ID (IMEI)
						reqJson.setDeviceAccount("mdntest@gmail.com"); // 기기 계정 (Gmail)
						reqJson.setJoinId("US002903"); // 가입채널코드
						reqJson.setIsRecvSms("Y"); // SMS 수신 여부
						reqJson.setOwnBirth("20020328"); // 본인의 생년월일

						// 법정 대리인 정보 (isParent 값이 Y 일경우 등록 된다.)
						reqJson.setIsParent("Y"); // 법정대리인정보 등록 여부.
						reqJson.setParentRealNameMethod("US011101");
						reqJson.setParentName("홍길동");
						reqJson.setParentType("F");
						reqJson.setParentDate(DateUtil.getToday());
						reqJson.setParentEmail("hkd@aaaa.com");
						reqJson.setParentBirthDay("19700331");
						reqJson.setParentTelecom("US001202");
						reqJson.setParentPhone("01088889999");
						reqJson.setParentCi("skpone0000132653GWyh3WsEm0FutitO5oSgC2/SgSrLKv5XohA8mxTNLitpB1 B9A3z5zrVHettHzKa5dpJA==");
						reqJson.setParentRealNameDate(DateUtil.getToday());
						reqJson.setParentRealNameSite("US011203"); // shop client 3.0

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
						agreement3.setIsExtraAgreement("N");
						AgreementInfo agreement4 = new AgreementInfo();
						agreement4.setExtraAgreementId("US010601");
						agreement4.setExtraAgreementVersion("0.1");
						agreement4.setIsExtraAgreement("N");

						agreementList.add(agreement1);
						agreementList.add(agreement2);
						agreementList.add(agreement3);
						agreementList.add(agreement4);
						reqJson.setAgreementList(agreementList);

						return reqJson;
					}
				}).success(CreateByMdnRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						CreateByMdnRes res = (CreateByMdnRes) result;
						assertThat(res.getUserKey(), notNullValue());
						LOGGER.debug(res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 회원 탈퇴 (MDN).
	 * </pre>
	 * 
	 * @throws Exception
	 *             void
	 */
	@Ignore
	@Test
	public void test6_withdrawMdn() throws Exception {

		new TestCaseTemplate(this.mvc).url(TestMemberConstant.PREFIX_USER_PATH_DEV + "/withdraw/v1").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						WithdrawReq req = new WithdrawReq();
						req.setDeviceId("550e8400e29b41d4a716446655440000");
						return req;
					}
				}).success(WithdrawRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						WithdrawRes res = (WithdrawRes) result;
						assertThat(res.getUserKey(), notNullValue());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

}
