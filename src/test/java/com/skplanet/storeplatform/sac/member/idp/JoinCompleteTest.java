/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.idp;

import java.util.HashMap;

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
import com.skplanet.storeplatform.sac.member.idp.service.IdpProvisionService;
import com.skplanet.storeplatform.sac.member.idp.vo.ProvisioningReq;
import com.skplanet.storeplatform.sac.member.idp.vo.ProvisioningRes;

/**
 * 
 * 가입 승인 만료 정보 Provisioning (유선, 통합 회원) 테스트.
 * 
 * Updated on : 2014. 2. 7. Updated by : 반범진, 지티소프트
 */
@ActiveProfiles(value = "local")

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class JoinCompleteTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(JoinCompleteTest.class);

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Autowired
	private IdpProvisionService idpService;

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Before
	public void before() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	/**
	 * 가입 승인 만료 정보 Provisioning (유선, 통합 회원).
	 */
	@Test
	public void joinCompleteTest01() {

		new TestCaseTemplate(this.mockMvc).url("/member/idp/provisioning/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
			@Override
			public Object requestBody() {
				ProvisioningReq req = new ProvisioningReq();
				req.setCmd("joinComplete");
				HashMap map = new HashMap();
				map.put("systemID", "W");
				map.put("tenantID", "S01");
				;
				map.put("user_key", "IF1526967420140128135555");
				req.setReqParam(map);

				LOGGER.info("request param : {}", req.toString());
				return req;
			}
		}).success(ProvisioningRes.class, new SuccessCallback() {
			@Override
			public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
				ProvisioningRes res = (ProvisioningRes) result;
				// res.get
				// assertThat(res.getSellerKey(), notNullValue());
				LOGGER.info("response param : {}", res.toString());
			}
		}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * 가입 승인 만료 정보 Provisioning (유선, 통합 회원).
	 */
	@Test
	public void joinCompleteTest02() {

		try {
			HashMap map = new HashMap();
			map.put("systemID", "W");
			map.put("tenantID", "S01");
			map.put("user_key", "IF1526967420140128135555");
			this.idpService.executeJoinComplete(map);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
