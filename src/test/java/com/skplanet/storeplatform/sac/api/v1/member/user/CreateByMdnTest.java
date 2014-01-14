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
import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.api.v1.member.constant.MemberTestConstant;
import com.skplanet.storeplatform.sac.client.member.vo.common.AgreementInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByMdnReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByMdnRes;

@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class CreateByMdnTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mvc;

	@Before
	public void before() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	/**
	 * <pre>
	 * 모바일 전용 회원 가입 (MDN 회원 가입)
	 * </pre>
	 * 
	 * @throws Exception
	 */
	@Test
	public void createByMdn() throws Exception {

		new TestCaseTemplate(this.mvc).url(MemberTestConstant.PREFIX_USER_PATH + "/createByMdn/v1").httpMethod(HttpMethod.POST)
				.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.addHeaders("Accept", "application/json")
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {

						CreateByMdnReq reqJson = new CreateByMdnReq();
						reqJson.setDeviceId("01088870008");
						reqJson.setDeviceIdType("msisdn");
						reqJson.setDeviceTelecom("US001201");
						reqJson.setJoinId("US002903");
						reqJson.setIsParent("N"); // 법정대리인정보 등록 여부.

						// 법정 대리인 정보 (isParent 값이 Y 일경우 등록 된다.)
						reqJson.setParentRealNameMethod("US011101");
						reqJson.setParentName("홍길동");
						reqJson.setParentType("F");
						reqJson.setParentDate(DateUtil.getToday());
						reqJson.setParentEmail("hkd@aaaa.com");
						reqJson.setParentBirthDay("19700331");
						reqJson.setParentTelecom("US001201");
						reqJson.setParentPhone("01088889999");
						reqJson.setParentCi("skpone0000132653GWyh3WsEm0FutitO5oSgC2/SgSrLKv5XohA8mxTNLitpB1 B9A3z5zrVHettHzKa5dpJA==");
						reqJson.setParentRealNameDate(DateUtil.getToday());
						reqJson.setParentRealNameSite("101");

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
				}).success(CreateByMdnRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						CreateByMdnRes res = (CreateByMdnRes) result;
						assertThat(res.getUserKey(), notNullValue());
						System.out.println(res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	// @Test
	// public void createByMdn() throws Exception {
	//
	// CreateByMdnReq reqJson = new CreateByMdnReq();
	// reqJson.setDeviceId("01090556567");
	// reqJson.setDeviceTelecom("US001201");
	// reqJson.setJoinId("US002903");
	// reqJson.setDeviceModelNo("LG-SH810");
	// reqJson.setOwnBirth("20020409");
	// reqJson.setParentType("");
	// reqJson.setRealNameMethod("US011101");
	// reqJson.setParentName("부모이름");
	// reqJson.setParentCI("skpone0000132653GWyh3WsEm0FutitO5oSgC2/SgSrLKv5XohA8mxTNLitpB1 B9A3z5zrVHettHzKa5dpJA==");
	// reqJson.setParentEmail("hkd@aaaa.com");
	// reqJson.setParentTelecom("US001201");
	// reqJson.setParentMdn("01088889999");
	// reqJson.setParentResident("local");
	// reqJson.setParentBirth("19700407");
	//
	// // 동의 정보
	// AgreementInfo agreementInfo = new AgreementInfo();
	// agreementInfo.setExtraAgreementId("asdasdasd");
	// agreementInfo.setExtraAgreementVersion("0.1");
	// agreementInfo.setIsExtraAgreement("Y");
	//
	// List<AgreementInfo> agreementList = new ArrayList<AgreementInfo>();
	// agreementList.add(agreementInfo);
	// reqJson.setAgreementList(agreementList);
	//
	// ObjectMapper mapper = new ObjectMapper();
	// String json = mapper.writeValueAsString(reqJson);
	// System.out.println(json);
	//
	// this.mvc.perform(post("/member/user/createByMdn/v1").content(json).accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
	// .andDo(print())
	// .andExpect(content().contentType("application/json;charset=UTF-8"))
	// .andExpect(status().isOk());
	//
	// }

}
