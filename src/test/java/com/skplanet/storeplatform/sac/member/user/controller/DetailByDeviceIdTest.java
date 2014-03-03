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
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
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

import com.skplanet.storeplatform.framework.test.RequestBodySetter;
import com.skplanet.storeplatform.framework.test.SuccessCallback;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate.RunMode;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailByDeviceIdSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailByDeviceIdSacReq.PolicyCodeInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailByDeviceIdSacRes;
import com.skplanet.storeplatform.sac.member.common.constant.TestMemberConstant;
import com.skplanet.storeplatform.sac.member.common.util.TestConvertMapperUtils;

/**
 * DeviceId를 이용하여 회원 정보 조회 테스트.
 * 
 * Updated on : 2014. 2. 3. Updated by : 심대진, 다모아 솔루션.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DetailByDeviceIdTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mvc;

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Before
	public void before() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	/**
	 * <pre>
	 * DeviceID를 이용하여 회원정보 조회 테스트.
	 * </pre>
	 */
	@Test
	public void TEST_A_DeviceID를이용하여회원정보조회() {

		System.out.println("## >> " + new Exception().getStackTrace()[0].getMethodName());
		new TestCaseTemplate(this.mvc).url(TestMemberConstant.PREFIX_USER_PATH_REAL + "/detailByDeviceId/v1").httpMethod(HttpMethod.POST)
				.addHeaders("Accept", "application/json")
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {

						DetailByDeviceIdSacReq reqJson = new DetailByDeviceIdSacReq();

						reqJson.setDeviceId("01022223333");
						reqJson.setDeviceIdType("msisdn");

						/**
						 * 정책 리스트
						 */
						reqJson.setKey("01022223333");
						List<PolicyCodeInfo> policyCodeInfoList = new ArrayList<PolicyCodeInfo>();
						PolicyCodeInfo policyCodeInfo1 = new PolicyCodeInfo();
						policyCodeInfo1.setPolicyCode("OR003101");
						policyCodeInfoList.add(policyCodeInfo1);
						reqJson.setPolicyCodeList(policyCodeInfoList);

						TestConvertMapperUtils.convertObjectToJson(reqJson);
						return reqJson;
					}
				}).success(DetailByDeviceIdSacRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						DetailByDeviceIdSacRes res = (DetailByDeviceIdSacRes) result;
						assertThat(res.getIsOpmd(), notNullValue());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

}
