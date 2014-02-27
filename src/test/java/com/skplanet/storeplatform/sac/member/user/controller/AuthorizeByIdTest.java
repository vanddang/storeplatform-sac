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

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceExtraInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByIdReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByIdRes;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.user.service.LoginService;

/**
 * ID 기반 회원 인증 (One ID, IDP 회원).
 * 
 * Updated on : 2014. 1. 8. Updated by : 반범진, 지티소프트
 */
@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class AuthorizeByIdTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizeByIdTest.class);

	@Autowired
	private LoginService loginService;

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	/**
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
	 * IDP 계정 정상.
	 * </pre>
	 * 
	 */
	@Test
	public void shouldAuthorizeByIdSuccessIdpUser() {

		try {

			new TestCaseTemplate(this.mockMvc)
					.url("/member/user/authorizeById/v1")
					.httpMethod(HttpMethod.POST)
					.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
					.addHeaders("Accept", "application/json")
					.addHeaders("x-planet-device-info",
							"model=\"SHW-M110S\",osVersion=\"1.0\",fwVersion=\"2.1.3_20101005f\",pkgVersion=\"com.skplanet.tstore.mobile/38\",rootDetection=\"no\"")
					.requestBody(new RequestBodySetter() {
						@Override
						public Object requestBody() {
							AuthorizeByIdReq req = new AuthorizeByIdReq();
							req.setUserId("vanddangtest020");
							req.setUserPw("abcd1234");

							try {
								ObjectMapper objMapper = new ObjectMapper();
								LOGGER.info("Request : {}", objMapper.writeValueAsString(req));
							} catch (Exception e) {
								e.printStackTrace();
							}

							return req;
						}
					}).success(AuthorizeByIdRes.class, new SuccessCallback() {
						@Override
						public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
							AuthorizeByIdRes res = (AuthorizeByIdRes) result;
							LOGGER.info("response param : {}", res.toString());
						}
					}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * <pre>
	 * ONEID 계정 정상.
	 * </pre>
	 * 
	 */
	@Test
	public void shouldAuthorizeByIdSuccessOneIdUser() {

		try {

			new TestCaseTemplate(this.mockMvc)
					.url("/member/user/authorizeById/v1")
					.httpMethod(HttpMethod.POST)
					.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
					.addHeaders("Accept", "application/json")
					.addHeaders("x-planet-device-info",
							"model=\"SHW-M110S\",osVersion=\"1.0\",fwVersion=\"2.1.3_20101005f\",pkgVersion=\"com.skplanet.tstore.mobile/38\",rootDetection=\"no\"")
					.requestBody(new RequestBodySetter() {
						@Override
						public Object requestBody() {
							AuthorizeByIdReq req = new AuthorizeByIdReq();
							req.setUserId("sacuser0011");
							req.setUserPw("!@34qwer");

							try {
								ObjectMapper objMapper = new ObjectMapper();
								LOGGER.info("Request : {}", objMapper.writeValueAsString(req));
							} catch (Exception e) {
								e.printStackTrace();
							}

							return req;
						}
					}).success(AuthorizeByIdRes.class, new SuccessCallback() {
						@Override
						public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
							AuthorizeByIdRes res = (AuthorizeByIdRes) result;
							LOGGER.info("response param : {}", res.toString());
						}
					}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * <pre>
	 * IDP 계정 비밀번호 불일치.
	 * </pre>
	 * 
	 */
	@Test
	public void shouldAuthorizeByIdSuccessIdpUserPwdError() {

		try {

			new TestCaseTemplate(this.mockMvc)
					.url("/member/user/authorizeById/v1")
					.httpMethod(HttpMethod.POST)
					.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
					.addHeaders("Accept", "application/json")
					.addHeaders("x-planet-device-info",
							"model=\"SHW-M110S\",osVersion=\"1.0\",fwVersion=\"2.1.3_20101005f\",pkgVersion=\"com.skplanet.tstore.mobile/38\",rootDetection=\"no\"")
					.requestBody(new RequestBodySetter() {
						@Override
						public Object requestBody() {
							AuthorizeByIdReq req = new AuthorizeByIdReq();
							req.setUserId("vanddangtest020");
							req.setUserPw("abcd1231114");

							try {
								ObjectMapper objMapper = new ObjectMapper();
								LOGGER.info("Request : {}", objMapper.writeValueAsString(req));
							} catch (Exception e) {
								e.printStackTrace();
							}

							return req;
						}
					}).success(AuthorizeByIdRes.class, new SuccessCallback() {
						@Override
						public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
							AuthorizeByIdRes res = (AuthorizeByIdRes) result;
							LOGGER.info("response param : {}", res.toString());
						}
					}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * <pre>
	 * ONEID 계정 패스워드 불일치.
	 * </pre>
	 * 
	 */
	@Test
	public void shouldAuthorizeByIdSuccessOneIdUserPwdError() {

		try {

			new TestCaseTemplate(this.mockMvc)
					.url("/member/user/authorizeById/v1")
					.httpMethod(HttpMethod.POST)
					.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
					.addHeaders("Accept", "application/json")
					.addHeaders("x-planet-device-info",
							"model=\"SHW-M110S\",osVersion=\"1.0\",fwVersion=\"2.1.3_20101005f\",pkgVersion=\"com.skplanet.tstore.mobile/38\",rootDetection=\"no\"")
					.requestBody(new RequestBodySetter() {
						@Override
						public Object requestBody() {
							AuthorizeByIdReq req = new AuthorizeByIdReq();
							req.setUserId("sacuser0011");
							req.setUserPw("!@34qwer1111");

							try {
								ObjectMapper objMapper = new ObjectMapper();
								LOGGER.info("Request : {}", objMapper.writeValueAsString(req));
							} catch (Exception e) {
								e.printStackTrace();
							}

							return req;
						}
					}).success(AuthorizeByIdRes.class, new SuccessCallback() {
						@Override
						public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
							AuthorizeByIdRes res = (AuthorizeByIdRes) result;
							LOGGER.info("response param : {}", res.toString());
						}
					}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * <pre>
	 * 로그인 제한.
	 * </pre>
	 * 
	 */
	@Test
	public void shouldAuthorizeByIdSuccessLoginPause() {

		try {

			new TestCaseTemplate(this.mockMvc)
					.url("/member/user/authorizeById/v1")
					.httpMethod(HttpMethod.POST)
					.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
					.addHeaders("Accept", "application/json")
					.addHeaders("x-planet-device-info",
							"model=\"SHW-M110S\",osVersion=\"1.0\",fwVersion=\"2.1.3_20101005f\",pkgVersion=\"com.skplanet.tstore.mobile/38\",rootDetection=\"no\"")
					.requestBody(new RequestBodySetter() {
						@Override
						public Object requestBody() {
							AuthorizeByIdReq req = new AuthorizeByIdReq();
							req.setUserId("c6est11stci066");
							req.setUserPw("abcd1234");

							try {
								ObjectMapper objMapper = new ObjectMapper();
								LOGGER.info("Request : {}", objMapper.writeValueAsString(req));
							} catch (Exception e) {
								e.printStackTrace();
							}

							return req;
						}
					}).success(AuthorizeByIdRes.class, new SuccessCallback() {
						@Override
						public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
							AuthorizeByIdRes res = (AuthorizeByIdRes) result;
							LOGGER.info("response param : {}", res.toString());
						}
					}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * <pre>
	 * 직권중지.
	 * </pre>
	 * 
	 */
	@Test
	public void shouldAuthorizeByIdSuccessOfAuthPause() {

		try {

			new TestCaseTemplate(this.mockMvc)
					.url("/member/user/authorizeById/v1")
					.httpMethod(HttpMethod.POST)
					.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
					.addHeaders("Accept", "application/json")
					.addHeaders("x-planet-device-info",
							"model=\"SHW-M110S\",osVersion=\"1.0\",fwVersion=\"2.1.3_20101005f\",pkgVersion=\"com.skplanet.tstore.mobile/38\",rootDetection=\"no\"")
					.requestBody(new RequestBodySetter() {
						@Override
						public Object requestBody() {
							AuthorizeByIdReq req = new AuthorizeByIdReq();
							req.setUserId("c6est11stci067");
							req.setUserPw("abcd1234");

							try {
								ObjectMapper objMapper = new ObjectMapper();
								LOGGER.info("Request : {}", objMapper.writeValueAsString(req));
							} catch (Exception e) {
								e.printStackTrace();
							}

							return req;
						}
					}).success(AuthorizeByIdRes.class, new SuccessCallback() {
						@Override
						public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
							AuthorizeByIdRes res = (AuthorizeByIdRes) result;
							LOGGER.info("response param : {}", res.toString());
						}
					}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * <pre>
	 * OneID 미동의 회원 인증성공.
	 * </pre>
	 * 
	 */
	@Test
	public void shouldAuthorizeByIdSuccessNoAgreeUser() {

		try {

			new TestCaseTemplate(this.mockMvc)
					.url("/member/user/authorizeById/v1")
					.httpMethod(HttpMethod.POST)
					.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
					.addHeaders("Accept", "application/json")
					.addHeaders("x-planet-device-info",
							"model=\"SHW-M110S\",osVersion=\"1.0\",fwVersion=\"2.1.3_20101005f\",pkgVersion=\"com.skplanet.tstore.mobile/38\",rootDetection=\"no\"")
					.requestBody(new RequestBodySetter() {
						@Override
						public Object requestBody() {
							AuthorizeByIdReq req = new AuthorizeByIdReq();
							req.setUserId("agreejoin003");
							req.setUserPw("!qaz2wsx");

							try {
								ObjectMapper objMapper = new ObjectMapper();
								LOGGER.info("Request : {}", objMapper.writeValueAsString(req));
							} catch (Exception e) {
								e.printStackTrace();
							}

							return req;
						}
					}).success(AuthorizeByIdRes.class, new SuccessCallback() {
						@Override
						public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
							AuthorizeByIdRes res = (AuthorizeByIdRes) result;
							LOGGER.info("response param : {}", res.toString());
						}
					}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * <pre>
	 * OneID 미동의 회원 패스워드 미일치.
	 * </pre>
	 * 
	 */
	@Test
	public void shouldAuthorizeByIdSuccessNoAgreeUserPwdError() {

		try {

			new TestCaseTemplate(this.mockMvc)
					.url("/member/user/authorizeById/v1")
					.httpMethod(HttpMethod.POST)
					.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
					.addHeaders("Accept", "application/json")
					.addHeaders("x-planet-device-info",
							"model=\"SHW-M110S\",osVersion=\"1.0\",fwVersion=\"2.1.3_20101005f\",pkgVersion=\"com.skplanet.tstore.mobile/38\",rootDetection=\"no\"")
					.requestBody(new RequestBodySetter() {
						@Override
						public Object requestBody() {
							AuthorizeByIdReq req = new AuthorizeByIdReq();
							req.setUserId("agreejoin003");
							req.setUserPw("!qaz2wsx11");

							try {
								ObjectMapper objMapper = new ObjectMapper();
								LOGGER.info("Request : {}", objMapper.writeValueAsString(req));
							} catch (Exception e) {
								e.printStackTrace();
							}

							return req;
						}
					}).success(AuthorizeByIdRes.class, new SuccessCallback() {
						@Override
						public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
							AuthorizeByIdRes res = (AuthorizeByIdRes) result;
							LOGGER.info("response param : {}", res.toString());
						}
					}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * <pre>
	 * IDP 계정 잠금해지.
	 * </pre>
	 * 
	 */
	@Test
	public void shouldAuthorizeByIdSuccessRockCancelIdpUser() {

		try {

			new TestCaseTemplate(this.mockMvc)
					.url("/member/user/authorizeById/v1")
					.httpMethod(HttpMethod.POST)
					.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
					.addHeaders("Accept", "application/json")
					.addHeaders("x-planet-device-info",
							"model=\"SHW-M110S\",osVersion=\"1.0\",fwVersion=\"2.1.3_20101005f\",pkgVersion=\"com.skplanet.tstore.mobile/38\",rootDetection=\"no\"")
					.requestBody(new RequestBodySetter() {
						@Override
						public Object requestBody() {
							AuthorizeByIdReq req = new AuthorizeByIdReq();
							req.setUserId("vanddangtest020");
							req.setUserPw("abcd1234");
							req.setReleaseLock("Y");

							try {
								ObjectMapper objMapper = new ObjectMapper();
								LOGGER.info("Request : {}", objMapper.writeValueAsString(req));
							} catch (Exception e) {
								e.printStackTrace();
							}

							return req;
						}
					}).success(AuthorizeByIdRes.class, new SuccessCallback() {
						@Override
						public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
							AuthorizeByIdRes res = (AuthorizeByIdRes) result;
							LOGGER.info("response param : {}", res.toString());
						}
					}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * <pre>
	 * OneID 계정 잠금해지.
	 * </pre>
	 * 
	 */
	@Test
	public void shouldAuthorizeByIdSuccessRockCancelOneIdUser() {

		try {

			new TestCaseTemplate(this.mockMvc)
					.url("/member/user/authorizeById/v1")
					.httpMethod(HttpMethod.POST)
					.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
					.addHeaders("Accept", "application/json")
					.addHeaders("x-planet-device-info",
							"model=\"SHW-M110S\",osVersion=\"1.0\",fwVersion=\"2.1.3_20101005f\",pkgVersion=\"com.skplanet.tstore.mobile/38\",rootDetection=\"no\"")
					.requestBody(new RequestBodySetter() {
						@Override
						public Object requestBody() {
							AuthorizeByIdReq req = new AuthorizeByIdReq();
							req.setUserId("sacuser0011");
							req.setUserPw("!@34qwer");
							req.setReleaseLock("Y");

							try {
								ObjectMapper objMapper = new ObjectMapper();
								LOGGER.info("Request : {}", objMapper.writeValueAsString(req));
							} catch (Exception e) {
								e.printStackTrace();
							}

							return req;
						}
					}).success(AuthorizeByIdRes.class, new SuccessCallback() {
						@Override
						public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
							AuthorizeByIdRes res = (AuthorizeByIdRes) result;
							LOGGER.info("response param : {}", res.toString());
						}
					}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * <pre>
	 * 모든 휴대기기 정보 포함.
	 * </pre>
	 * 
	 */
	@Test
	public void shouldAuthorizeByIdSuccessAllDeviceInfo() {

		try {

			new TestCaseTemplate(this.mockMvc)
					.url("/member/user/authorizeById/v1")
					.httpMethod(HttpMethod.POST)
					.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
					.addHeaders("Accept", "application/json")
					.addHeaders("x-planet-device-info",
							"model=\"SHW-M110S\",osVersion=\"1.0\",fwVersion=\"2.1.3_20101005f\",pkgVersion=\"com.skplanet.tstore.mobile/38\",rootDetection=\"no\"")
					.requestBody(new RequestBodySetter() {
						@Override
						public Object requestBody() {
							AuthorizeByIdReq req = new AuthorizeByIdReq();
							req.setUserId("vanddangtest020");
							req.setUserPw("abcd1234");
							req.setDeviceId("01048088874");
							req.setDeviceTelecom(MemberConstants.DEVICE_TELECOM_SKT);
							req.setNativeId("012962008247725");
							//req.setIsNativeIdAuth("Y");

							List<DeviceExtraInfo> deviceExtraInfoList = new ArrayList<DeviceExtraInfo>();

							DeviceExtraInfo deviceExtraInfo = new DeviceExtraInfo();
							deviceExtraInfo.setExtraProfile(MemberConstants.DEVICE_EXTRA_OMPDOWNLOADER_YN);
							deviceExtraInfo.setExtraProfileValue("Y");
							deviceExtraInfoList.add(deviceExtraInfo);

							deviceExtraInfo = new DeviceExtraInfo();
							deviceExtraInfo.setExtraProfile(MemberConstants.DEVICE_EXTRA_STANDBYSCREEN_YN);
							deviceExtraInfo.setExtraProfileValue("Y");
							deviceExtraInfoList.add(deviceExtraInfo);

							deviceExtraInfo = new DeviceExtraInfo();
							deviceExtraInfo.setExtraProfile(MemberConstants.DEVICE_EXTRA_OMPSUPPORT_YN);
							deviceExtraInfo.setExtraProfileValue("Y");
							deviceExtraInfoList.add(deviceExtraInfo);

							deviceExtraInfo = new DeviceExtraInfo();
							deviceExtraInfo.setExtraProfile(MemberConstants.DEVICE_EXTRA_APPSTATISTICS_YN);
							deviceExtraInfo.setExtraProfileValue("Y");
							deviceExtraInfoList.add(deviceExtraInfo);

							deviceExtraInfo = new DeviceExtraInfo();
							deviceExtraInfo.setExtraProfile(MemberConstants.DEVICE_EXTRA_DODORYAUTH_DATE);
							deviceExtraInfo.setExtraProfileValue("Y");
							deviceExtraInfoList.add(deviceExtraInfo);

							deviceExtraInfo = new DeviceExtraInfo();
							deviceExtraInfo.setExtraProfile(MemberConstants.DEVICE_EXTRA_DODORYAUTH_DATE);
							deviceExtraInfo.setExtraProfileValue("20140214154500");
							deviceExtraInfoList.add(deviceExtraInfo);

							deviceExtraInfo = new DeviceExtraInfo();
							deviceExtraInfo.setExtraProfile(MemberConstants.DEVICE_EXTRA_EMBEDDED_YN);
							deviceExtraInfo.setExtraProfileValue("Y");
							deviceExtraInfoList.add(deviceExtraInfo);

							deviceExtraInfo = new DeviceExtraInfo();
							deviceExtraInfo.setExtraProfile(MemberConstants.DEVICE_EXTRA_ROOTING_YN);
							deviceExtraInfo.setExtraProfileValue("N");
							deviceExtraInfoList.add(deviceExtraInfo);

							deviceExtraInfo = new DeviceExtraInfo();
							deviceExtraInfo.setExtraProfile(MemberConstants.DEVICE_EXTRA_TCLOUD_SUPPORT_YN);
							deviceExtraInfo.setExtraProfileValue("Y");
							deviceExtraInfoList.add(deviceExtraInfo);

							req.setDeviceExtraInfoList(deviceExtraInfoList);

							try {
								ObjectMapper objMapper = new ObjectMapper();
								LOGGER.info("Request : {}", objMapper.writeValueAsString(req));
							} catch (Exception e) {
								e.printStackTrace();
							}

							return req;
						}
					}).success(AuthorizeByIdRes.class, new SuccessCallback() {
						@Override
						public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
							AuthorizeByIdRes res = (AuthorizeByIdRes) result;
							LOGGER.info("response param : {}", res.toString());
						}
					}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * <pre>
	 * nativeId 비교 성공 자사.
	 * </pre>
	 * 
	 */
	@Test
	public void shouldAuthorizeByIdSuccessSktNaviveId() {

		try {

			new TestCaseTemplate(this.mockMvc)
					.url("/member/user/authorizeById/v1")
					.httpMethod(HttpMethod.POST)
					.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
					.addHeaders("Accept", "application/json")
					.addHeaders("x-planet-device-info",
							"model=\"SHW-M110S\",osVersion=\"1.0\",fwVersion=\"2.1.3_20101005f\",pkgVersion=\"com.skplanet.tstore.mobile/38\",rootDetection=\"no\"")
					.requestBody(new RequestBodySetter() {
						@Override
						public Object requestBody() {
							AuthorizeByIdReq req = new AuthorizeByIdReq();
							req.setUserId("vanddangtest020");
							req.setUserPw("abcd1234");
							req.setDeviceId("01048088874");
							req.setDeviceTelecom(MemberConstants.DEVICE_TELECOM_SKT);
							req.setNativeId("012962008247725");
							//req.setIsNativeIdAuth("Y");
							try {
								ObjectMapper objMapper = new ObjectMapper();
								LOGGER.info("Request : {}", objMapper.writeValueAsString(req));
							} catch (Exception e) {
								e.printStackTrace();
							}

							return req;
						}
					}).success(AuthorizeByIdRes.class, new SuccessCallback() {
						@Override
						public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
							AuthorizeByIdRes res = (AuthorizeByIdRes) result;
							LOGGER.info("response param : {}", res.toString());
						}
					}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * <pre>
	 * nativeId 비교 성공 타사.
	 * </pre>
	 * 
	 */
	@Test
	public void shouldAuthorizeByIdSuccessNotSktNaviveId() {

		try {

			new TestCaseTemplate(this.mockMvc)
					.url("/member/user/authorizeById/v1")
					.httpMethod(HttpMethod.POST)
					.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
					.addHeaders("Accept", "application/json")
					.addHeaders("x-planet-device-info",
							"model=\"SHW-M110S\",osVersion=\"1.0\",fwVersion=\"2.1.3_20101005f\",pkgVersion=\"com.skplanet.tstore.mobile/38\",rootDetection=\"no\"")
					.requestBody(new RequestBodySetter() {
						@Override
						public Object requestBody() {
							AuthorizeByIdReq req = new AuthorizeByIdReq();
							req.setUserId("sacuser0007");
							req.setUserPw("!@34qwer");
							req.setDeviceId("01099012852");
							req.setDeviceTelecom(MemberConstants.DEVICE_TELECOM_KT);
							req.setNativeId("358362045580844");
							req.setIsNativeIdAuth("Y");
							try {
								ObjectMapper objMapper = new ObjectMapper();
								LOGGER.info("Request : {}", objMapper.writeValueAsString(req));
							} catch (Exception e) {
								e.printStackTrace();
							}

							return req;
						}
					}).success(AuthorizeByIdRes.class, new SuccessCallback() {
						@Override
						public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
							AuthorizeByIdRes res = (AuthorizeByIdRes) result;
							LOGGER.info("response param : {}", res.toString());
						}
					}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * <pre>
	 * nativeId 비교 에러 자사(ICAS IMEI와 단말 IMEI값이 불일치 합니다.).
	 * </pre>
	 * 
	 */
	@Test(expected = StorePlatformException.class)
	public void shouldAuthorizeByIdErrorSktNaviveId() {

		try {

			new TestCaseTemplate(this.mockMvc)
					.url("/member/user/authorizeById/v1")
					.httpMethod(HttpMethod.POST)
					.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
					.addHeaders("Accept", "application/json")
					.addHeaders("x-planet-device-info",
							"model=\"SHW-M110S\",osVersion=\"1.0\",fwVersion=\"2.1.3_20101005f\",pkgVersion=\"com.skplanet.tstore.mobile/38\",rootDetection=\"no\"")
					.requestBody(new RequestBodySetter() {
						@Override
						public Object requestBody() {
							AuthorizeByIdReq req = new AuthorizeByIdReq();
							req.setUserId("vanddangtest020");
							req.setUserPw("abcd1234");
							req.setDeviceId("01048088874");
							req.setDeviceTelecom(MemberConstants.DEVICE_TELECOM_SKT);
							req.setNativeId("358362045580842");
							//req.setIsNativeIdAuth("Y");
							try {
								ObjectMapper objMapper = new ObjectMapper();
								LOGGER.info("Request : {}", objMapper.writeValueAsString(req));
							} catch (Exception e) {
								e.printStackTrace();
							}

							return req;
						}
					}).success(AuthorizeByIdRes.class, new SuccessCallback() {
						@Override
						public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
							AuthorizeByIdRes res = (AuthorizeByIdRes) result;
							LOGGER.info("response param : {}", res.toString());
						}
					}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * <pre>
	 * nativeId 비교 에러 타사(DB IMEI와 단말 IMEI값이 불일치 합니다.).
	 * </pre>
	 * 
	 */
	@Test(expected = StorePlatformException.class)
	public void shouldAuthorizeByIdErrorNotSktNaviveId() {

		try {

			new TestCaseTemplate(this.mockMvc)
					.url("/member/user/authorizeById/v1")
					.httpMethod(HttpMethod.POST)
					.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
					.addHeaders("Accept", "application/json")
					.addHeaders("x-planet-device-info",
							"model=\"SHW-M110S\",osVersion=\"1.0\",fwVersion=\"2.1.3_20101005f\",pkgVersion=\"com.skplanet.tstore.mobile/38\",rootDetection=\"no\"")
					.requestBody(new RequestBodySetter() {
						@Override
						public Object requestBody() {
							AuthorizeByIdReq req = new AuthorizeByIdReq();
							req.setUserId("sacuser0007");
							req.setUserPw("!@34qwer");
							req.setDeviceId("01099012852");
							req.setDeviceTelecom(MemberConstants.DEVICE_TELECOM_KT);
							req.setNativeId("358362045580842");
							req.setIsNativeIdAuth("Y");
							try {
								ObjectMapper objMapper = new ObjectMapper();
								LOGGER.info("Request : {}", objMapper.writeValueAsString(req));
							} catch (Exception e) {
								e.printStackTrace();
							}

							return req;
						}
					}).success(AuthorizeByIdRes.class, new SuccessCallback() {
						@Override
						public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
							AuthorizeByIdRes res = (AuthorizeByIdRes) result;
							LOGGER.info("response param : {}", res.toString());
						}
					}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * <pre>
	 * nativeId 비교 에러 타사(DB IMEI와 단말 IMEI값이 불일치 합니다.).
	 * </pre>
	 * 
	 */
	@Test(expected = StorePlatformException.class)
	public void shouldAuthorizeByIdErrorNotSktNaviveId02() {

		try {

			new TestCaseTemplate(this.mockMvc)
					.url("/member/user/authorizeById/v1")
					.httpMethod(HttpMethod.POST)
					.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
					.addHeaders("Accept", "application/json")
					.addHeaders("x-planet-device-info",
							"model=\"SHW-M110S\",osVersion=\"1.0\",fwVersion=\"2.1.3_20101005f\",pkgVersion=\"com.skplanet.tstore.mobile/38\",rootDetection=\"no\"")
					.requestBody(new RequestBodySetter() {
						@Override
						public Object requestBody() {
							AuthorizeByIdReq req = new AuthorizeByIdReq();
							req.setUserId("sacuser0007");
							req.setUserPw("!@34qwer");
							req.setDeviceId("01099012852");
							req.setDeviceTelecom(MemberConstants.DEVICE_TELECOM_KT);
							req.setNativeId("358362045580842");
							//req.setIsNativeIdAuth("Y");
							List<DeviceExtraInfo> deviceExtraInfoList = new ArrayList<DeviceExtraInfo>();
							DeviceExtraInfo deviceExtraInfo = new DeviceExtraInfo();
							deviceExtraInfo.setExtraProfile(MemberConstants.DEVICE_EXTRA_ROOTING_YN);
							deviceExtraInfo.setExtraProfileValue("Y");
							deviceExtraInfoList.add(deviceExtraInfo);

							req.setDeviceExtraInfoList(deviceExtraInfoList);

							try {
								ObjectMapper objMapper = new ObjectMapper();
								LOGGER.info("Request : {}", objMapper.writeValueAsString(req));
							} catch (Exception e) {
								e.printStackTrace();
							}

							return req;
						}
					}).success(AuthorizeByIdRes.class, new SuccessCallback() {
						@Override
						public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
							AuthorizeByIdRes res = (AuthorizeByIdRes) result;
							LOGGER.info("response param : {}", res.toString());
						}
					}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * <pre>
	 * 회원정보 없음.
	 * </pre>
	 * 
	 */
	@Test(expected = StorePlatformException.class)
	public void shouldAuthorizeByIdErrorNoUser() {

		try {

			new TestCaseTemplate(this.mockMvc)
					.url("/member/user/authorizeById/v1")
					.httpMethod(HttpMethod.POST)
					.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
					.addHeaders("Accept", "application/json")
					.addHeaders("x-planet-device-info",
							"model=\"SHW-M110S\",osVersion=\"1.0\",fwVersion=\"2.1.3_20101005f\",pkgVersion=\"com.skplanet.tstore.mobile/38\",rootDetection=\"no\"")
					.requestBody(new RequestBodySetter() {
						@Override
						public Object requestBody() {
							AuthorizeByIdReq req = new AuthorizeByIdReq();
							req.setUserId("sacsimpleuser992702");
							req.setUserPw("!@34qwer1111");

							try {
								ObjectMapper objMapper = new ObjectMapper();
								LOGGER.info("Request : {}", objMapper.writeValueAsString(req));
							} catch (Exception e) {
								e.printStackTrace();
							}

							return req;
						}
					}).success(AuthorizeByIdRes.class, new SuccessCallback() {
						@Override
						public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
							AuthorizeByIdRes res = (AuthorizeByIdRes) result;
							LOGGER.info("response param : {}", res.toString());
						}
					}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * <pre>
	 * 통합IDP 미가입 회원.
	 * </pre>
	 * 
	 */
	@Test(expected = StorePlatformException.class)
	public void shouldAuthorizeByIdErrorIdpNoUser() {

		try {

			new TestCaseTemplate(this.mockMvc)
					.url("/member/user/authorizeById/v1")
					.httpMethod(HttpMethod.POST)
					.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
					.addHeaders("Accept", "application/json")
					.addHeaders("x-planet-device-info",
							"model=\"SHW-M110S\",osVersion=\"1.0\",fwVersion=\"2.1.3_20101005f\",pkgVersion=\"com.skplanet.tstore.mobile/38\",rootDetection=\"no\"")
					.requestBody(new RequestBodySetter() {
						@Override
						public Object requestBody() {
							AuthorizeByIdReq req = new AuthorizeByIdReq();
							req.setUserId("test1225");
							req.setUserPw("!@34qwer1111");

							try {
								ObjectMapper objMapper = new ObjectMapper();
								LOGGER.info("Request : {}", objMapper.writeValueAsString(req));
							} catch (Exception e) {
								e.printStackTrace();
							}

							return req;
						}
					}).success(AuthorizeByIdRes.class, new SuccessCallback() {
						@Override
						public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
							AuthorizeByIdRes res = (AuthorizeByIdRes) result;
							LOGGER.info("response param : {}", res.toString());
						}
					}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * <pre>
	 * 정상 케이스 서비스호출.
	 * </pre>
	 */
	@Test
	public void shouldAuthorizeByIdService() {
		TenantHeader tenantHeader = new TenantHeader();
		tenantHeader.setSystemId("S001");
		tenantHeader.setTenantId("S01");

		DeviceHeader deviceHeader = new DeviceHeader();
		//deviceHeader.setModel("SHW-M440S");
		deviceHeader.setModel("SHW-M110S");
		deviceHeader.setOs("1.0");

		SacRequestHeader header = new SacRequestHeader();
		header.setDeviceHeader(deviceHeader);
		header.setTenantHeader(tenantHeader);

		AuthorizeByIdReq req = new AuthorizeByIdReq();
		req.setDeviceId("01066786221");
		req.setUserId("vanddangtest020");
		req.setUserPw("abcd1234");
		req.setDeviceIdType("msisdn");
		req.setDeviceTelecom(MemberConstants.DEVICE_TELECOM_LGT);
		req.setDeviceAccount("vanddang@gmail.com");

		List<DeviceExtraInfo> deviceExtraInfoList = new ArrayList<DeviceExtraInfo>();
		DeviceExtraInfo deviceExtraInfo = new DeviceExtraInfo();
		deviceExtraInfo.setExtraProfile(MemberConstants.DEVICE_EXTRA_ROOTING_YN);
		deviceExtraInfo.setExtraProfileValue("N");
		deviceExtraInfoList.add(deviceExtraInfo);
		req.setDeviceExtraInfoList(deviceExtraInfoList);
		try {
			ObjectMapper objMapper = new ObjectMapper();
			LOGGER.info("Request : {}", objMapper.writeValueAsString(req));

			AuthorizeByIdRes res = this.loginService.executeAuthorizeById(header, req);
			LOGGER.info("res : {} " + res.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
