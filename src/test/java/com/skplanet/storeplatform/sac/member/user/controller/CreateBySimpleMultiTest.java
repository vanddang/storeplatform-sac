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

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.common.base.Strings;
import com.skplanet.storeplatform.framework.test.RequestBodySetter;
import com.skplanet.storeplatform.framework.test.SuccessCallback;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate.RunMode;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceExtraInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByAgreementRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateBySimpleReq;
import com.skplanet.storeplatform.sac.member.common.constant.TestMemberConstant;
import com.skplanet.storeplatform.sac.member.common.util.TestConvertMapperUtils;

/**
 * ID 회원 간편 가입 (IDP 회원) 테스트.
 * 
 * Updated on : 2014. 1. 13. Updated by : 심대진, 다모아 솔루션.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CreateBySimpleMultiTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mvc;

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Before
	public void before() throws Exception {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	/**
	 * <pre>
	 * ID 회원 간편 가입 (IDP 회원) [[ 단말정보 포함 ]].
	 * </pre>
	 */
	@Ignore
	@Test
	public void TEST_A_IDP간편가입_단말정보있음_500개() throws Exception {

		for (int i = 1; i <= 500; i++) {

			String number = Strings.padStart(i + "", 4, '0');

			final CreateBySimpleReq reqJson = new CreateBySimpleReq();

			// 사용자 아이디
			reqJson.setUserId("simpleuser" + number); // 대문자 ID는 가입 불가 IDP 정책.
			reqJson.setUserPw("12qwer");
			reqJson.setUserEmail("simpleuser" + number + "@naver.com");

			// 단말 정보
			reqJson.setDeviceId("0150000" + number); // 기기 ID
			reqJson.setDeviceIdType("msisdn"); // 기기 ID 타입
			reqJson.setDeviceTelecom("US001202"); // 통신사
			reqJson.setNativeId("A031648EE9" + number); // 기기 고유 ID (IMEI)
			reqJson.setDeviceAccount("simpleuser" + number + "@naver.com"); // 기기 계정 (Gmail)
			reqJson.setJoinId("US002903"); // 가입채널코드
			reqJson.setIsRecvSms("Y"); // SMS 수신 여부

			// 단말 부가 정보 리스트
			List<DeviceExtraInfo> deviceExtraList = new ArrayList<DeviceExtraInfo>();
			DeviceExtraInfo deviceExtraInfo = new DeviceExtraInfo();
			deviceExtraInfo.setExtraProfile("US011407");
			deviceExtraInfo.setExtraProfileValue("3.0");

			deviceExtraList.add(deviceExtraInfo);
			reqJson.setDeviceExtraInfoList(deviceExtraList);

			new TestCaseTemplate(this.mvc).url(TestMemberConstant.PREFIX_USER_PATH_REAL +
					"/createBySimple/v1").httpMethod(HttpMethod.POST)
					.addHeaders("Accept", "application/json")
					.addHeaders("x-sac-device-info", "model=\"SHW-M110S\",dpi=\"320\",resolution=\"480*720\",os=\"Android/4.0.4\",pkg=\"com.skt.skaf.A000Z00040/37\",svc=\"SHOPCLIENT/4.3\"")
					.requestBody(new RequestBodySetter() {
						@Override
						public Object requestBody() {
							TestConvertMapperUtils.convertObjectToJson(reqJson);
							return reqJson;
						}
					}).success(CreateByAgreementRes.class, new SuccessCallback() {
						@Override
						public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
							CreateByAgreementRes res = (CreateByAgreementRes) result;
							System.out.println(res.getUserKey() + ":" + reqJson.getUserId() + ":" + reqJson.getDeviceId());
						}
					}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

			/**
			 * IDP 에러 발생으로...
			 * 
			 * 정의된 시간(2초)내에 동일한 요청을 받았습니다.
			 */
			Thread.sleep(2500);

		}

	}

}
