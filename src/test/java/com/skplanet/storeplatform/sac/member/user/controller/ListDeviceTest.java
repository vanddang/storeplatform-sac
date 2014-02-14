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
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.member.user.service.DeviceService;

/**
 * 휴대기기 목록조회 Class Test
 * 
 * Updated on : 2014. 1. 8. Updated by : 반범진, 지티소프트
 */
@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class ListDeviceTest {

	private static final Logger logger = LoggerFactory.getLogger(AuthorizeByIdTest.class);

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void before() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Autowired
	private DeviceService deviceService;

	@Test
	public void shouldDeviceListByUserKey() {

		try {

			new TestCaseTemplate(this.mockMvc)
					.url("/member/user/listDevice/v1")
					.httpMethod(HttpMethod.POST)
					.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
					.addHeaders("Accept", "application/json")
					.addHeaders("x-planet-device-info",
							"model=\"SHW-M110S\",osVersion=\"1.0\",fwVersion=\"2.1.3_20101005f\",pkgVersion=\"com.skplanet.tstore.mobile/38\",rootDetection=\"no\"")
					.requestBody(new RequestBodySetter() {
						@Override
						public Object requestBody() {

							ListDeviceReq req = new ListDeviceReq();
							req.setUserKey("US201401161113423010000110");
							req.setIsMainDevice("N");

							ObjectMapper objMapper = new ObjectMapper();

							try {
								logger.info("Request : {}", objMapper.writeValueAsString(req));
							} catch (Exception e) {
								e.printStackTrace();
							}

							return req;
						}
					}).success(ListDeviceRes.class, new SuccessCallback() {
						@Override
						public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
							ListDeviceRes res = (ListDeviceRes) result;
							logger.info("response param : {}", res.toString());
						}
					}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void shouldDeviceListByUserId() {

		try {

			new TestCaseTemplate(this.mockMvc)
					.url("/member/user/listDevice/v1")
					.httpMethod(HttpMethod.POST)
					.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
					.addHeaders("Accept", "application/json")
					.addHeaders("x-planet-device-info",
							"model=\"SHW-M110S\",osVersion=\"1.0\",fwVersion=\"2.1.3_20101005f\",pkgVersion=\"com.skplanet.tstore.mobile/38\",rootDetection=\"no\"")
					.requestBody(new RequestBodySetter() {
						@Override
						public Object requestBody() {

							ListDeviceReq req = new ListDeviceReq();
							req.setUserId("watermin0210");
							req.setIsMainDevice("Y");

							ObjectMapper objMapper = new ObjectMapper();

							try {
								logger.info("Request : {}", objMapper.writeValueAsString(req));
							} catch (Exception e) {
								e.printStackTrace();
							}

							return req;
						}
					}).success(ListDeviceRes.class, new SuccessCallback() {
						@Override
						public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
							ListDeviceRes res = (ListDeviceRes) result;
							logger.info("response param : {}", res.toString());
						}
					}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void shouldDeviceListByDeviceId() {

		try {

			new TestCaseTemplate(this.mockMvc)
					.url("/member/user/listDevice/v1")
					.httpMethod(HttpMethod.POST)
					.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
					.addHeaders("Accept", "application/json")
					.addHeaders("x-planet-device-info",
							"model=\"SHW-M110S\",osVersion=\"1.0\",fwVersion=\"2.1.3_20101005f\",pkgVersion=\"com.skplanet.tstore.mobile/38\",rootDetection=\"no\"")
					.requestBody(new RequestBodySetter() {
						@Override
						public Object requestBody() {

							ListDeviceReq req = new ListDeviceReq();
							req.setUserKey("US201401161113423010000110");
							req.setDeviceId("01020284280");

							try {
								ObjectMapper objMapper = new ObjectMapper();
								logger.info("Request : {}", objMapper.writeValueAsString(req));
							} catch (Exception e) {
								e.printStackTrace();
							}

							return req;
						}
					}).success(ListDeviceRes.class, new SuccessCallback() {
						@Override
						public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
							ListDeviceRes res = (ListDeviceRes) result;
							logger.info("response param : {}", res.toString());
						}
					}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void shouldDeviceListByDeviceKey() {

		try {

			new TestCaseTemplate(this.mockMvc)
					.url("/member/user/listDevice/v1")
					.httpMethod(HttpMethod.POST)
					.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
					.addHeaders("Accept", "application/json")
					.addHeaders("x-planet-device-info",
							"model=\"SHW-M110S\",osVersion=\"1.0\",fwVersion=\"2.1.3_20101005f\",pkgVersion=\"com.skplanet.tstore.mobile/38\",rootDetection=\"no\"")
					.requestBody(new RequestBodySetter() {
						@Override
						public Object requestBody() {

							ListDeviceReq req = new ListDeviceReq();
							req.setUserKey("US201401161113423010000110");
							req.setDeviceKey("DE201401161113425370000050");

							try {
								ObjectMapper objMapper = new ObjectMapper();
								logger.info("Request : {}", objMapper.writeValueAsString(req));
							} catch (Exception e) {
								e.printStackTrace();
							}

							return req;
						}
					}).success(ListDeviceRes.class, new SuccessCallback() {
						@Override
						public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
							ListDeviceRes res = (ListDeviceRes) result;
							logger.info("response param : {}", res.toString());
						}
					}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void shouldDeviceListService() {

		TenantHeader tenantHeader = new TenantHeader();
		tenantHeader.setSystemId("S001");
		tenantHeader.setTenantId("S01");

		SacRequestHeader header = new SacRequestHeader();
		header.setTenantHeader(tenantHeader);

		ListDeviceReq req = new ListDeviceReq();
		req.setUserKey("US2014012217211685500003231");
		req.setDeviceId("01066786220");
		//req.setDeviceKey("DE201401161113425370000050");
		//req.setUserId("01020284280");
		//req.setIsMainDevice("Y");
		try {
			ObjectMapper objMapper = new ObjectMapper();
			logger.info("Request : {}", objMapper.writeValueAsString(req));
			ListDeviceRes res = this.deviceService.listDevice(header, req);
			logger.info("::: res : {}", res.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
