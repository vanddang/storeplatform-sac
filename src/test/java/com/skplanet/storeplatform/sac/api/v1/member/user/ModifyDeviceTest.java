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
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyDeviceReq;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
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
public class ModifyDeviceTest {

	private static final Logger logger = LoggerFactory.getLogger(ModifyDeviceTest.class);

	@Autowired
	private DeviceService deviceService;

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void before() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@After
	public void after() {

	}

	@Test
	public void shouldCreateDevice() {

		try {

			new TestCaseTemplate(this.mockMvc)
					.url("/member/user/modifyDevice/v1")
					.httpMethod(HttpMethod.POST)
					.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
					.addHeaders("Accept", "application/json")
					.addHeaders("x-planet-device-info",
							"model=\"SHW-M220L\",osVersion=\"1.0\",fwVersion=\"2.1.3_20101005f\",pkgVersion=\"com.skplanet.tstore.mobile/38\",rootDetection=\"no\"")
					.requestBody(new RequestBodySetter() {
						@Override
						public Object requestBody() {
							ModifyDeviceReq req = new ModifyDeviceReq();
							req.setUserAuthKey("263c4ed6240966f9d28b33650161c843fb37bed9");
							req.setUserKey("US201401221721168550000323");
							req.setDeviceKey("DE201401221008185490000164");

							DeviceInfo deviceInfo = new DeviceInfo();
							deviceInfo.setDeviceId("01066786220");
							deviceInfo.setUserKey("US201401161113423010000110");
							deviceInfo.setDeviceIdType("msisdn ");
							deviceInfo.setDeviceTelecom(MemberConstants.DEVICE_TELECOM_LGT);
							deviceInfo.setIsPrimary("N");
							deviceInfo.setIsAuthenticated("Y");
							deviceInfo.setAuthenticationDate(DateUtil.getDateString(new Date(), "yyyyMMddHHmmss"));
							deviceInfo.setIsUsed("Y");
							deviceInfo.setNativeId("358362045580844");
							deviceInfo.setDeviceAccount("vanddang@gmail.com");
							deviceInfo.setIsRecvSms("Y");

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

							deviceInfo.setUserDeviceExtraInfo(deviceExtraInfoList);
							req.setDeviceInfo(deviceInfo);

							try {
								ObjectMapper objMapper = new ObjectMapper();
								logger.info("Request : {}", objMapper.writeValueAsString(req));
							} catch (Exception e) {
								e.printStackTrace();
							}

							return req;
						}
					}).success(CreateDeviceRes.class, new SuccessCallback() {
						@Override
						public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
							CreateDeviceRes res = (CreateDeviceRes) result;
							logger.info("response param : {}", res.toString());
						}
					}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
