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
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByMdnReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByMdnRes;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.user.service.LoginService;

/**
 * 모바일 전용 회원 인증 (MDN 인증) Class Test.
 * 
 * Updated on : 2014. 1. 8. Updated by : 반범진, 지티소프트
 */
@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class AuthorizeByMdnTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizeByMdnTest.class);

	@Autowired
	private LoginService loginService;
	@Autowired
	MemberCommonComponent commService;

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
	 * 정상 케이스.
	 * </pre>
	 */
	@Test
	public void shouldAuthorizeByMdn() {

		try {

			new TestCaseTemplate(this.mockMvc)
					.url("/member/user/authorizeByMdn/v1")
					.httpMethod(HttpMethod.POST)
					.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
					.addHeaders("Accept", "application/json")
					.addHeaders("x-planet-device-info",
							"model=\"SHW-M110\",osVersion=\"1.1\",fwVersion=\"2.1.3_20101005f\",pkgVersion=\"com.skplanet.tstore.mobile/38\",rootDetection=\"no\"")
					.requestBody(new RequestBodySetter() {
						@Override
						public Object requestBody() {

							AuthorizeByMdnReq req = new AuthorizeByMdnReq();
							req.setDeviceId("01066786225");
							req.setDeviceIdType("msisdn");
							req.setDeviceTelecom(MemberConstants.DEVICE_TELECOM_LGT);
							req.setNativeId("358362045580842");
							req.setIsAutoUpdate("N");
							req.setDeviceAccount("vanddang@gmail.com");

							List<DeviceExtraInfo> deviceExtraInfoList = new ArrayList<DeviceExtraInfo>();
							DeviceExtraInfo deviceExtraInfo = new DeviceExtraInfo();
							deviceExtraInfo.setExtraProfile(MemberConstants.DEVICE_EXTRA_SCVERSION);
							deviceExtraInfo.setExtraProfileValue("1.0");
							deviceExtraInfoList.add(deviceExtraInfo);

							deviceExtraInfo = new DeviceExtraInfo();
							deviceExtraInfo.setExtraProfile(MemberConstants.DEVICE_EXTRA_ROOTING_YN);
							deviceExtraInfo.setExtraProfileValue("N");
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
					}).success(AuthorizeByMdnRes.class, new SuccessCallback() {
						@Override
						public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
							AuthorizeByMdnRes res = (AuthorizeByMdnRes) result;
							LOGGER.info("response param : {}", res.toString());
						}
					}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * <pre>
	 * 하나의 MDN에 N개의 유효한 회원정보 존재시 Exception.
	 * </pre>
	 */
	@Test(expected = StorePlatformException.class)
	public void shouldAuthorizeByMdn02() {

		try {

			new TestCaseTemplate(this.mockMvc)
					.url("/member/user/authorizeByMdn/v1")
					.httpMethod(HttpMethod.POST)
					.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
					.addHeaders("Accept", "application/json")
					.addHeaders("x-planet-device-info",
							"model=\"SHW-M110\",osVersion=\"1.1\",fwVersion=\"2.1.3_20101005f\",pkgVersion=\"com.skplanet.tstore.mobile/38\",rootDetection=\"no\"")
					.requestBody(new RequestBodySetter() {
						@Override
						public Object requestBody() {

							AuthorizeByMdnReq req = new AuthorizeByMdnReq();
							req.setDeviceId("01066786221");
							req.setDeviceIdType("msisdn");
							req.setDeviceTelecom(MemberConstants.DEVICE_TELECOM_LGT);
							req.setNativeId("358362045580842");
							req.setIsAutoUpdate("N");
							req.setDeviceAccount("vanddang@gmail.com");

							List<DeviceExtraInfo> deviceExtraInfoList = new ArrayList<DeviceExtraInfo>();
							DeviceExtraInfo deviceExtraInfo = new DeviceExtraInfo();
							deviceExtraInfo.setExtraProfile(MemberConstants.DEVICE_EXTRA_SCVERSION);
							deviceExtraInfo.setExtraProfileValue("1.0");
							deviceExtraInfoList.add(deviceExtraInfo);

							deviceExtraInfo = new DeviceExtraInfo();
							deviceExtraInfo.setExtraProfile(MemberConstants.DEVICE_EXTRA_ROOTING_YN);
							deviceExtraInfo.setExtraProfileValue("N");
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
					}).success(AuthorizeByMdnRes.class, new SuccessCallback() {
						@Override
						public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
							AuthorizeByMdnRes res = (AuthorizeByMdnRes) result;
							LOGGER.info("response param : {}", res.toString());
						}
					}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * <pre>
	 * 정상케이스 서비스 호출.
	 * </pre>
	 */
	@Test
	public void shouldAuthorizeByMdnService() {

		TenantHeader tenantHeader = new TenantHeader();
		tenantHeader.setSystemId("S001");
		tenantHeader.setTenantId("S01");

		DeviceHeader deviceHeader = new DeviceHeader();
		//deviceHeader.setModel("SHW-M440S");
		deviceHeader.setModel("SHW-M110S");
		deviceHeader.setOsVersion("1.0");

		SacRequestHeader header = new SacRequestHeader();
		header.setDeviceHeader(deviceHeader);
		header.setTenantHeader(tenantHeader);

		AuthorizeByMdnReq req = new AuthorizeByMdnReq();
		req.setDeviceId("01066786225");
		req.setDeviceIdType("msisdn");
		req.setDeviceTelecom(MemberConstants.DEVICE_TELECOM_LGT);
		req.setNativeId("358362045580842");
		req.setDeviceAccount("vanddang444@gmail.com");
		req.setIsAutoUpdate("N");

		List<DeviceExtraInfo> deviceExtraInfoList = new ArrayList<DeviceExtraInfo>();
		DeviceExtraInfo deviceExtraInfo = new DeviceExtraInfo();
		deviceExtraInfo = new DeviceExtraInfo();
		deviceExtraInfo.setExtraProfile(MemberConstants.DEVICE_EXTRA_ROOTING_YN);
		deviceExtraInfo.setExtraProfileValue("N");
		deviceExtraInfoList.add(deviceExtraInfo);

		req.setDeviceExtraInfoList(deviceExtraInfoList);

		try {
			ObjectMapper objMapper = new ObjectMapper();
			LOGGER.info("Request : {}", objMapper.writeValueAsString(req));

			AuthorizeByMdnRes res = this.loginService.authorizeByMdn(header, req);
			LOGGER.info("res : {} " + res.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
