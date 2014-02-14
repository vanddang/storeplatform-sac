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
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.After;
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
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceExtraInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.RemoveDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.RemoveDeviceRes;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.user.service.DeviceService;

/**
 * 휴대기기 등록 Class Test.
 * 
 * Updated on : 2014. 1. 8. Updated by : 반범진, 지티소프트
 */
@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class CreateDeviceTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(CreateDeviceTest.class);

	@Autowired
	private DeviceService deviceService;

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

	String userAuthKey = "114127c7ef42667669819dad5df8d820c";
	String userKey = "US201401280706367180001249";
	String mdn = "01066786248";

	/**
	 * <pre>
	 * 휴대기기 등록 정상케이스.
	 * </pre>
	 */
	@Test
	public void shouldCreateDevice() {

		try {

			new TestCaseTemplate(this.mockMvc)
					.url("/member/user/createDevice/v1")
					.httpMethod(HttpMethod.POST)
					.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
					.addHeaders("Accept", "application/json")
					.addHeaders("x-planet-device-info",
							"model=\"SHW-M220L\",osVersion=\"1.0\",fwVersion=\"2.1.3_20101005f\",pkgVersion=\"com.skplanet.tstore.mobile/38\",rootDetection=\"no\"")
					.requestBody(new RequestBodySetter() {
						@Override
						public Object requestBody() {

							CreateDeviceReq req = new CreateDeviceReq();
							req.setUserAuthKey(CreateDeviceTest.this.userAuthKey);
							req.setUserKey(CreateDeviceTest.this.userKey);
							req.setRegMaxCnt("5");

							DeviceInfo deviceInfo = new DeviceInfo();
							deviceInfo.setUserKey(CreateDeviceTest.this.userKey);
							deviceInfo.setDeviceId(CreateDeviceTest.this.mdn);
							deviceInfo.setDeviceIdType("msisdn ");
							deviceInfo.setDeviceTelecom(MemberConstants.DEVICE_TELECOM_LGT);
							deviceInfo.setIsPrimary("N");
							deviceInfo.setIsAuthenticated("Y");
							deviceInfo.setAuthenticationDate(DateUtil.getDateString(new Date(), "yyyyMMddHHmmss"));
							deviceInfo.setIsUsed("Y");
							deviceInfo.setNativeId("358362045580844");
							deviceInfo.setDeviceAccount("vanddang@gmail.com");
							deviceInfo.setIsRecvSms("Y");
							deviceInfo.setDeviceNickName("SHP-110S(임시)");

							List<DeviceExtraInfo> deviceExtraInfoList = new ArrayList<DeviceExtraInfo>();
							DeviceExtraInfo deviceExtraInfo = new DeviceExtraInfo();
							deviceExtraInfo.setExtraProfile(MemberConstants.DEVICE_EXTRA_DODORYAUTH_DATE);
							deviceExtraInfo.setExtraProfileValue(DateUtil.getDateString(new Date(), "yyyyMMddHHmmss"));
							deviceExtraInfoList.add(deviceExtraInfo);

							deviceExtraInfo = new DeviceExtraInfo();
							deviceExtraInfo.setExtraProfile(MemberConstants.DEVICE_EXTRA_DODORYAUTH_YN);
							deviceExtraInfo.setExtraProfileValue("Y");
							deviceExtraInfoList.add(deviceExtraInfo);

							deviceExtraInfo = new DeviceExtraInfo();
							deviceExtraInfo.setExtraProfile(MemberConstants.DEVICE_EXTRA_OSVERSION);
							deviceExtraInfo.setExtraProfileValue("1.0");
							deviceExtraInfoList.add(deviceExtraInfo);

							deviceExtraInfo = new DeviceExtraInfo();
							deviceExtraInfo.setExtraProfile(MemberConstants.DEVICE_EXTRA_ROOTING_YN);
							deviceExtraInfo.setExtraProfileValue("N");
							deviceExtraInfoList.add(deviceExtraInfo);

							deviceInfo.setDeviceExtraInfoList(deviceExtraInfoList);
							req.setDeviceInfo(deviceInfo);

							try {
								ObjectMapper objMapper = new ObjectMapper();
								LOGGER.info("Request : {}", objMapper.writeValueAsString(req));
							} catch (Exception e) {
								e.printStackTrace();
							}

							return req;
						}
					}).success(CreateDeviceRes.class, new SuccessCallback() {
						@Override
						public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
							CreateDeviceRes res = (CreateDeviceRes) result;
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
	 * 등록 후 삭제 처리.
	 * </pre>
	 */
	@After
	public void after() {
		new TestCaseTemplate(this.mockMvc)
				.url("/member/user/removeDevice/v1")
				.httpMethod(HttpMethod.POST)
				.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.addHeaders("Accept", "application/json")
				.addHeaders("x-planet-device-info",
						"model=\"SHW-M220L\",osVersion=\"1.0\",fwVersion=\"2.1.3_20101005f\",pkgVersion=\"com.skplanet.tstore.mobile/38\",rootDetection=\"no\"")
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						RemoveDeviceReq req = new RemoveDeviceReq();
						req.setUserAuthKey(CreateDeviceTest.this.userAuthKey);
						req.setUserKey(CreateDeviceTest.this.userKey);
						req.setDeviceId(CreateDeviceTest.this.mdn);
						LOGGER.debug("request param : {}", req.toString());
						return req;
					}
				}).success(RemoveDeviceRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						RemoveDeviceRes res = (RemoveDeviceRes) result;
						LOGGER.debug("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}
}
