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
import com.skplanet.storeplatform.sac.client.member.vo.common.AgreementInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyTermsAgreementReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyTermsAgreementRes;
import com.skplanet.storeplatform.sac.member.common.constant.TestMemberConstant;
import com.skplanet.storeplatform.sac.member.common.util.TestConvertMapperUtils;

/**
 * Store 약관동의 수정 테스트.
 * 
 * Updated on : 2014. 2. 3. Updated by : 심대진, 다모아 솔루션.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ModifyTermsAgreementTest {

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
	 * Store 약관동의 수정.
	 * </pre>
	 */
	@Test
	public void TEST_A_약관동의등록OR수정() {

		System.out.println("## >> " + new Exception().getStackTrace()[0].getMethodName());
		new TestCaseTemplate(this.mvc).url(TestMemberConstant.PREFIX_USER_PATH_REAL + "/modifyTermsAgreement/v1").httpMethod(HttpMethod.POST)
				.addHeaders("Accept", "application/json")
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {

						ModifyTermsAgreementReq reqJson = new ModifyTermsAgreementReq();

						reqJson.setUserKey("US201401241550022950000616");

						/**
						 * 약관 동의 리스트
						 */
						List<AgreementInfo> agreementList = new ArrayList<AgreementInfo>();
						AgreementInfo agreementInfo1 = new AgreementInfo();
						agreementInfo1.setExtraAgreementId("US010601"); // 약관코드
						agreementInfo1.setExtraAgreementVersion("0.4"); // 약관 버전
						agreementInfo1.setIsExtraAgreement("N"); // 약관 동의 여부
						agreementList.add(agreementInfo1);
						AgreementInfo agreementInfo2 = new AgreementInfo();
						agreementInfo2.setExtraAgreementId("US010602"); // 약관코드
						agreementInfo2.setExtraAgreementVersion("0.4"); // 약관 버전
						agreementInfo2.setIsExtraAgreement("N"); // 약관 동의 여부
						agreementList.add(agreementInfo2);
						AgreementInfo agreementInfo3 = new AgreementInfo();
						agreementInfo3.setExtraAgreementId("US010603"); // 약관코드
						agreementInfo3.setExtraAgreementVersion("0.4"); // 약관 버전
						agreementInfo3.setIsExtraAgreement("N"); // 약관 동의 여부
						agreementList.add(agreementInfo3);
						AgreementInfo agreementInfo4 = new AgreementInfo();
						agreementInfo4.setExtraAgreementId("US010604"); // 약관코드
						agreementInfo4.setExtraAgreementVersion("0.4"); // 약관 버전
						agreementInfo4.setIsExtraAgreement("N"); // 약관 동의 여부
						agreementList.add(agreementInfo4);
						reqJson.setAgreementList(agreementList);

						TestConvertMapperUtils.convertObjectToJson(reqJson);
						return reqJson;
					}
				}).success(ModifyTermsAgreementRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						ModifyTermsAgreementRes res = (ModifyTermsAgreementRes) result;
						assertThat(res.getUserKey(), notNullValue());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

}
