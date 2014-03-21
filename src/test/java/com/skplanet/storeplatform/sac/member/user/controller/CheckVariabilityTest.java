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
import com.skplanet.storeplatform.sac.client.member.vo.user.CheckVariabilityReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CheckVariabilityRes;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.user.service.LoginService;

/**
 * 변동성 체크 Class Test.
 * 
 * Updated on : 2014. 3. 13. Updated by : 반범진, 지티소프트
 */
@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class CheckVariabilityTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(CheckVariabilityTest.class);

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
	 * 변동성 체크 성공.
	 * </pre>
	 */
	@Test
	public void shouldAuthorizeSaveAndSyncByMacSuccess() {

		try {

			new TestCaseTemplate(this.mockMvc)
					.url("/member/user/checkVariability/v1")
					.httpMethod(HttpMethod.POST)
					.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
					.addHeaders("Accept", "application/json")
					.addHeaders("x-planet-device-info",
							"model=\"SHW-M110\",osVersion=\"1.1\",fwVersion=\"2.1.3_20101005f\",pkgVersion=\"com.skplanet.tstore.mobile/38\",rootDetection=\"no\"")
					.requestBody(new RequestBodySetter() {
						@Override
						public Object requestBody() {

							CheckVariabilityReq req = new CheckVariabilityReq();
							req.setDeviceId("01066786220");
							req.setDeviceTelecom(MemberConstants.DEVICE_TELECOM_LGT);
							req.setDeviceAccount("aaaabbb@gmail.com");
							req.setNativeId("A0000031648EE9");
							try {
								ObjectMapper objMapper = new ObjectMapper();
								LOGGER.info("Request : {}", objMapper.writeValueAsString(req));
							} catch (Exception e) {
								e.printStackTrace();
							}

							return req;
						}
					}).success(CheckVariabilityRes.class, new SuccessCallback() {
						@Override
						public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
							CheckVariabilityRes res = (CheckVariabilityRes) result;
							LOGGER.info("response param : {}", res.toString());
						}
					}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * <pre>
	 * 변동성 체크 실패 (통신사, 지메일이 불일치한 경우).
	 * </pre>
	 */
	@Test
	public void shouldAuthorizeSaveAndSyncByMacError() {

		try {

			new TestCaseTemplate(this.mockMvc)
					.url("/member/user/checkVariability/v1")
					.httpMethod(HttpMethod.POST)
					.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
					.addHeaders("Accept", "application/json")
					.addHeaders("x-planet-device-info",
							"model=\"SHW-M110\",osVersion=\"1.1\",fwVersion=\"2.1.3_20101005f\",pkgVersion=\"com.skplanet.tstore.mobile/38\",rootDetection=\"no\"")
					.requestBody(new RequestBodySetter() {
						@Override
						public Object requestBody() {

							CheckVariabilityReq req = new CheckVariabilityReq();
							req.setDeviceId("01048088874");
							req.setDeviceTelecom(MemberConstants.DEVICE_TELECOM_SKT);
							//req.setDeviceAccount("aaaabbb@gmail.com");
							//req.setNativeId("A0000031648EE9");
							try {
								ObjectMapper objMapper = new ObjectMapper();
								LOGGER.info("Request : {}", objMapper.writeValueAsString(req));
							} catch (Exception e) {
								e.printStackTrace();
							}

							return req;
						}
					}).success(CheckVariabilityRes.class, new SuccessCallback() {
						@Override
						public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
							CheckVariabilityRes res = (CheckVariabilityRes) result;
							LOGGER.info("response param : {}", res.toString());
						}
					}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
