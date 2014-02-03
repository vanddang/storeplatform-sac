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
import com.skplanet.storeplatform.sac.api.v1.member.constant.MemberTestConstant;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailByDeviceIdSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailByDeviceIdSacReq.PolicyCodeInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailByDeviceIdSacRes;

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
	 * 회원정보 조회 테스트.
	 * </pre>
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test
	public void test1_detailByDeviceId() throws Exception {

		new TestCaseTemplate(this.mvc).url(MemberTestConstant.PREFIX_USER_PATH_REAL + "/detailByDeviceId/v1").httpMethod(HttpMethod.POST)
				.addHeaders("Accept", "application/json")
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {

						DetailByDeviceIdSacReq reqJson = new DetailByDeviceIdSacReq();

						reqJson.setDeviceId("98923624159");
						reqJson.setDeviceIdType("msisdn");

						/**
						 * 정책 리스트
						 */
						reqJson.setKey("53");
						List<PolicyCodeInfo> policyCodeInfoList = new ArrayList<PolicyCodeInfo>();
						PolicyCodeInfo policyCodeInfo1 = new PolicyCodeInfo();
						policyCodeInfo1.setPolicyCode("3");
						PolicyCodeInfo policyCodeInfo2 = new PolicyCodeInfo();
						policyCodeInfo2.setPolicyCode("4");
						PolicyCodeInfo policyCodeInfo3 = new PolicyCodeInfo();
						policyCodeInfo3.setPolicyCode("5");
						policyCodeInfoList.add(policyCodeInfo1);
						policyCodeInfoList.add(policyCodeInfo2);
						policyCodeInfoList.add(policyCodeInfo3);
						reqJson.setPolicyCodeList(policyCodeInfoList);

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
