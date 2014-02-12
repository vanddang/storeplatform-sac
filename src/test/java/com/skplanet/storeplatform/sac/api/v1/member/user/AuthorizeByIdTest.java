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
 * ID 기반 회원 인증 (One ID, IDP 회원)
 * 
 * Updated on : 2014. 1. 8. Updated by : 반범진, 지티소프트
 */
@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class AuthorizeByIdTest {

	private static final Logger logger = LoggerFactory.getLogger(AuthorizeByIdTest.class);

	@Autowired
	private LoginService loginService;

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void before() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void shouldAuthorizeById() throws Exception {

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
							req.setDeviceId("01001232112");
							req.setUserId("agreejoin003");
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
								logger.info("Request : {}", objMapper.writeValueAsString(req));
							} catch (Exception e) {
								e.printStackTrace();
							}

							return req;
						}
					}).success(AuthorizeByIdRes.class, new SuccessCallback() {
						@Override
						public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
							AuthorizeByIdRes res = (AuthorizeByIdRes) result;
							logger.info("response param : {}", res.toString());
						}
					}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void shouldAuthorizeByIdService() {
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

		AuthorizeByIdReq req = new AuthorizeByIdReq();
		req.setDeviceId("01001232112");
		req.setUserId("agreejoin003");
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
			logger.info("Request : {}", objMapper.writeValueAsString(req));

			AuthorizeByIdRes res = this.loginService.authorizeById(header, req);
			logger.info("res : {} " + res.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
