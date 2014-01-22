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
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByMdnReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByMdnRes;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.MemberConstants;
import com.skplanet.storeplatform.sac.member.user.service.LoginService;

/**
 * 모바일 전용 회원 인증 (MDN 인증) Class Test
 * 
 * Updated on : 2014. 1. 8. Updated by : 반범진, 지티소프트
 */
@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class AuthorizeByMdnTest {

	private static final Logger logger = LoggerFactory.getLogger(AuthorizeByMdnTest.class);

	@Autowired
	private LoginService loginService;
	@Autowired
	MemberCommonComponent commService; // 회원 공통 서비스

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void before() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	/**
	 * 정상
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
							req.setDeviceId("01020284280");
							req.setDeviceIdType("msisdn");
							req.setDeviceTelecom(MemberConstants.DEVICE_TELECOM_SKT);
							req.setNativeId("358362045580842");
							req.setRooting("N");
							req.setDeviceAccount("vanddang@gmail.com");
							req.setIsAutoUpdate("N");
							req.setScVer("1.1");

							ObjectMapper objMapper = new ObjectMapper();

							try {
								logger.info("Request : {}", objMapper.writeValueAsString(req));
							} catch (Exception e) {
								e.printStackTrace();
							}

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
			e.printStackTrace();
		}
	}

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
		req.setDeviceId("01020284280");
		req.setDeviceIdType("msisdn");
		req.setDeviceTelecom(MemberConstants.DEVICE_TELECOM_SKT);
		//req.setNativeId("358362045580842");
		req.setRooting("Y");
		req.setDeviceAccount("vanddang444@gmail.com");
		req.setIsAutoUpdate("N");
		req.setScVer("1.1");
		logger.info("request param : {}", req.toString());

		try {
			AuthorizeByMdnRes res = this.loginService.authorizeByMdn(header, req);
			logger.info("res : {} " + res.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
