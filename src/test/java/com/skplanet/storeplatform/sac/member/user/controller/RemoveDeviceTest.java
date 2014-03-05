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

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
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
import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceExtraInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.RemoveDeviceListSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.RemoveDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.RemoveDeviceRes;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.user.service.DeviceService;

/**
 * 휴대기기 삭제 Class Test
 * 
 * Updated on : 2014. 1. 23. Updated by : 강신완, 부르칸.
 */
@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RemoveDeviceTest {

	private static final Logger logger = LoggerFactory.getLogger(RemoveDeviceTest.class);

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
	public void A_TEST_정상_디바이스삭제용_디바이스등록() {

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
							req.setUserAuthKey("114127c7ef42667669819dad5df8d820c");
							req.setUserKey("US201401221721168550000323");
							req.setRegMaxCnt("5");

							DeviceInfo deviceInfo = new DeviceInfo();
							deviceInfo.setUserKey("US201402252122571960003656");
							deviceInfo.setDeviceId("01098316893");
							deviceInfo.setDeviceIdType("msisdn ");
							deviceInfo.setDeviceTelecom(MemberConstants.DEVICE_TELECOM_LGT);
							deviceInfo.setDeviceModelNo("SHV-E300S");
							deviceInfo.setDeviceNickName("SHP-110S(임시)");
							deviceInfo.setNativeId("358362045580844");
							deviceInfo.setDeviceAccount("vanddang@gmail.com");
							deviceInfo.setIsPrimary("N");

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
							deviceExtraInfo.setExtraProfile(MemberConstants.DEVICE_EXTRA_SCVERSION);
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

	/**
	 * 휴대기기 삭제 - 일반
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Test
	public void B_TEST_정상_휴대기기삭제() {

		new TestCaseTemplate(this.mockMvc).url("/member/user/removeDevice/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
			@Override
			public Object requestBody() {
				RemoveDeviceReq req = new RemoveDeviceReq();
				req.setUserAuthKey("114127c7ef42667669819dad5df8d820c");
				req.setUserKey("US201401221721168550000323");

				RemoveDeviceListSacReq sacReq = new RemoveDeviceListSacReq();
				sacReq.setDeviceId("01098316893");
				List<RemoveDeviceListSacReq> deviceIdList = new ArrayList<RemoveDeviceListSacReq>();
				deviceIdList.add(sacReq);

				req.setDeviceIdList(deviceIdList);
				logger.debug("request param : {}", req.toString());
				return req;
			}
		}).success(RemoveDeviceRes.class, new SuccessCallback() {
			@Override
			public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
				RemoveDeviceRes res = (RemoveDeviceRes) result;
				assertThat(res.getDeviceKeyList().get(0).getDeviceKey(), notNullValue());
				logger.debug("response param : {}", res.toString());
			}
		}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * 휴대기기 삭제 - 대표단말
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Test(expected = StorePlatformException.class)
	public void C_TEST_오류_ID회원_등록된단말2개이상_삭제요청단말_대표단말() {

		new TestCaseTemplate(this.mockMvc).url("/member/user/removeDevice/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
			@Override
			public Object requestBody() {
				RemoveDeviceReq req = new RemoveDeviceReq();
				req.setUserAuthKey("90f18724118fa3046073e9a828511bb57813cbfe");
				req.setUserKey("US201402201456232610003160");

				RemoveDeviceListSacReq sacReq = new RemoveDeviceListSacReq();
				sacReq.setDeviceId("0101239631");
				List<RemoveDeviceListSacReq> deviceIdList = new ArrayList<RemoveDeviceListSacReq>();
				deviceIdList.add(sacReq);

				req.setDeviceIdList(deviceIdList);
				logger.debug("request param : {}", req.toString());
				return req;
			}
		}).success(RemoveDeviceRes.class, new SuccessCallback() {
			@Override
			public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
				RemoveDeviceRes res = (RemoveDeviceRes) result;
				assertThat(res.getDeviceKeyList().get(0).getDeviceKey(), notNullValue());
				logger.debug("response param : {}", res.toString());
			}
		}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * 휴대기기 삭제 - inValid DeviceId
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Test(expected = StorePlatformException.class)
	public void D_TEST_오류_유효하지않은_휴대기기() {

		new TestCaseTemplate(this.mockMvc).url("/member/user/removeDevice/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
			@Override
			public Object requestBody() {
				RemoveDeviceReq req = new RemoveDeviceReq();
				req.setUserAuthKey("90f18724118fa3046073e9a828511bb57813cbfe");
				req.setUserKey("US201402201456232610003160");

				RemoveDeviceListSacReq sacReq = new RemoveDeviceListSacReq();
				sacReq.setDeviceId("010123963111");
				List<RemoveDeviceListSacReq> deviceIdList = new ArrayList<RemoveDeviceListSacReq>();
				deviceIdList.add(sacReq);

				req.setDeviceIdList(deviceIdList);
				logger.debug("request param : {}", req.toString());
				return req;
			}
		}).success(RemoveDeviceRes.class, new SuccessCallback() {
			@Override
			public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
				RemoveDeviceRes res = (RemoveDeviceRes) result;
				assertThat(res.getDeviceKeyList().get(0).getDeviceKey(), notNullValue());
				logger.debug("response param : {}", res.toString());
			}
		}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * 휴대기기 삭제 - 파라미터 없음
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Test(expected = StorePlatformException.class)
	public void E_TEST_오류_파라미터없음() {

		new TestCaseTemplate(this.mockMvc).url("/member/user/removeDevice/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
			@Override
			public Object requestBody() {
				RemoveDeviceReq req = new RemoveDeviceReq();
				req.setUserAuthKey("");
				req.setUserKey("");

				RemoveDeviceListSacReq sacReq = new RemoveDeviceListSacReq();
				sacReq.setDeviceId("");
				List<RemoveDeviceListSacReq> deviceIdList = new ArrayList<RemoveDeviceListSacReq>();
				deviceIdList.add(sacReq);

				req.setDeviceIdList(deviceIdList);
				logger.debug("request param : {}", req.toString());
				return req;
			}
		}).success(RemoveDeviceRes.class, new SuccessCallback() {
			@Override
			public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
				RemoveDeviceRes res = (RemoveDeviceRes) result;
				assertThat(res.getDeviceKeyList().get(0).getDeviceKey(), notNullValue());
				logger.debug("response param : {}", res.toString());
			}
		}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * 휴대기기 삭제 - 모바일회원
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Test(expected = StorePlatformException.class)
	public void F_TEST_오류_모바일회원() {

		new TestCaseTemplate(this.mockMvc).url("/member/user/removeDevice/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
			@Override
			public Object requestBody() {
				RemoveDeviceReq req = new RemoveDeviceReq();
				req.setUserAuthKey("90f18724118fa3046073e9a828511bb57813cbfe");
				req.setUserKey("US201402070050515440001899");

				RemoveDeviceListSacReq sacReq = new RemoveDeviceListSacReq();
				sacReq.setDeviceId("01114021705");
				List<RemoveDeviceListSacReq> deviceIdList = new ArrayList<RemoveDeviceListSacReq>();
				deviceIdList.add(sacReq);

				req.setDeviceIdList(deviceIdList);
				logger.debug("request param : {}", req.toString());
				return req;
			}
		}).success(RemoveDeviceRes.class, new SuccessCallback() {
			@Override
			public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
				RemoveDeviceRes res = (RemoveDeviceRes) result;
				assertThat(res.getDeviceKeyList().get(0).getDeviceKey(), notNullValue());
				logger.debug("response param : {}", res.toString());
			}
		}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

}
