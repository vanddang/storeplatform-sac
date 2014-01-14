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
import org.junit.Test;
import org.junit.runner.RunWith;
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
import com.skplanet.storeplatform.sac.client.member.vo.common.AgreementInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByAgreementReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByAgreementRes;

/**
 * ID 회원 약관 동의 가입 (One ID 회원) 테스트.
 * 
 * Updated on : 2014. 1. 13. Updated by : 심대진, 다모아 솔루션.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class CreateByAgreementTest {

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
	 * ID 회원 약관 동의 가입 (One ID 회원).
	 * </pre>
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test
	public void createByAgreement() throws Exception {

		new TestCaseTemplate(this.mvc).url(MemberTestConstant.PREFIX_USER_PATH + "/createByAgreement/v1").httpMethod(HttpMethod.POST)
				.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.addHeaders("Accept", "application/json")
				.addHeaders("x-planet-device-info", "model=\"SHW-M190S\",fwVersion=\"2.1.3_20101005f\",pkgVersion=\"com.skplanet.tstore.mobile/38\",rootDetection=\"no\"")
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {

						CreateByAgreementReq reqJson = new CreateByAgreementReq();
						reqJson.setDeviceId("01088870008");
						reqJson.setDeviceIdType("msisdn");
						reqJson.setUserId("test1234");
						reqJson.setDeviceTelecom("US001201");
						reqJson.setJoinId("US002903");

						// 동의 정보
						List<AgreementInfo> agreementList = new ArrayList<AgreementInfo>();
						AgreementInfo agreement1 = new AgreementInfo();
						agreement1.setExtraAgreementId("US010607");
						agreement1.setExtraAgreementVersion("0.1");
						agreement1.setIsExtraAgreement("Y");
						AgreementInfo agreement2 = new AgreementInfo();
						agreement2.setExtraAgreementId("US010608");
						agreement2.setExtraAgreementVersion("0.1");
						agreement2.setIsExtraAgreement("Y");
						AgreementInfo agreement3 = new AgreementInfo();
						agreement3.setExtraAgreementId("US010609");
						agreement3.setExtraAgreementVersion("0.1");
						agreement3.setIsExtraAgreement("Y");
						agreementList.add(agreement1);
						agreementList.add(agreement2);
						agreementList.add(agreement3);
						reqJson.setAgreementList(agreementList);

						return reqJson;
					}
				}).success(CreateByAgreementRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						CreateByAgreementRes res = (CreateByAgreementRes) result;
						assertThat(res.getUserKey(), notNullValue());
						System.out.println(res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

}
