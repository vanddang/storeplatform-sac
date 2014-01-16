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
import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByMdnRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDeviceRes;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.member.user.service.DeviceService;

/**
 * 휴대기기 등록 Class Test
 * 
 * Updated on : 2014. 1. 8. Updated by : 반범진, 지티소프트
 */
@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class CreateDeviceTest {

	private static final Logger logger = LoggerFactory.getLogger(CreateDeviceTest.class);

	@Autowired
	private DeviceService deviceService;

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void before() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void shouldCreateDevice() {

		try {

			new TestCaseTemplate(this.mockMvc)
					.url("/dev/member/user/createDevice/v1")
					.httpMethod(HttpMethod.POST)
					.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
					.addHeaders("Accept", "application/json")
					.addHeaders("x-planet-device-info",
							"model=\"SHW-M220L\",osVersion=\"1.0\",fwVersion=\"2.1.3_20101005f\",pkgVersion=\"com.skplanet.tstore.mobile/38\",rootDetection=\"no\"")
					.requestBody(new RequestBodySetter() {
						@Override
						public Object requestBody() {

							CreateDeviceReq req = new CreateDeviceReq();
							req.setUserAuthKey("114127c7ef42667669819dad5df8d820c");
							req.setUserKey("US201401161859405900000107");
							req.setRegMaxCnt(5);

							DeviceInfo deviceInfo = new DeviceInfo();
							deviceInfo.setUserKey("US201401161859405900000107");
							deviceInfo.setDeviceId("01066786222");
							deviceInfo.setDeviceIdType("msisdn ");
							deviceInfo.setDeviceTelecom("US012102");
							deviceInfo.setNativeId("358362045580844");
							deviceInfo.setDeviceAccount("vanddang@gmail.com");
							deviceInfo.setIsRecvSms("Y");
							deviceInfo.setIsPrimary("N");
							deviceInfo.setDeviceNickName("SHP-110S(임시)");

							deviceInfo.setDotoriAuthDate(DateUtil.getToday());
							deviceInfo.setDotoriAuthYn("Y");
							deviceInfo.setOsVer("1.0");
							deviceInfo.setScVer("1.0");
							deviceInfo.setRooting("N");
							deviceInfo.setUacd("uacd");

							req.setDeviceInfo(deviceInfo);
							logger.info("request param : {}", req.toString());

							return req;
						}
					}).success(AuthorizeByMdnRes.class, new SuccessCallback() {
						@Override
						public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
							AuthorizeByMdnRes res = (AuthorizeByMdnRes) result;
							logger.info("response param : {}", res.toString());
						}
					}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void shouldCreateDeviceService() {
		TenantHeader tenantHeader = new TenantHeader();
		tenantHeader.setSystemId("S001");
		tenantHeader.setTenantId("S01");

		DeviceHeader deviceHeader = new DeviceHeader();
		deviceHeader.setModel("SHW-M220L");
		deviceHeader.setOsVersion("1.0");

		SacRequestHeader header = new SacRequestHeader();
		header.setDeviceHeader(deviceHeader);
		header.setTenantHeader(tenantHeader);

		CreateDeviceReq req = new CreateDeviceReq();
		req.setUserAuthKey("114127c7ef42667669819dad5df8d820c");
		req.setUserKey("US201401161859405900000107");
		req.setRegMaxCnt(5);

		DeviceInfo deviceInfo = new DeviceInfo();
		deviceInfo.setUserKey("US201401161859405900000107");
		deviceInfo.setDeviceId("01066786221");
		deviceInfo.setDeviceIdType("msisdn ");
		deviceInfo.setDeviceTelecom("US012102");
		deviceInfo.setNativeId("358362045580844");
		deviceInfo.setDeviceAccount("vanddang@gmail.com");
		deviceInfo.setIsRecvSms("Y");
		deviceInfo.setIsPrimary("N");
		deviceInfo.setDeviceNickName("SHP-110S(임시)");

		deviceInfo.setDotoriAuthDate(DateUtil.getToday());
		deviceInfo.setDotoriAuthYn("Y");
		deviceInfo.setOsVer("1.0");
		deviceInfo.setScVer("1.0");
		deviceInfo.setRooting("N");
		deviceInfo.setUacd("uacd");

		req.setDeviceInfo(deviceInfo);
		try {

			CreateDeviceRes res = this.deviceService.createDevice(header, req);
			logger.info("res : {} " + res.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
