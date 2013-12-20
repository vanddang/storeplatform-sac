/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.api.v1.user;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.skplanet.storeplatform.framework.test.RequestBodySetter;
import com.skplanet.storeplatform.framework.test.SuccessCallback;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate.RunMode;
import com.skplanet.storeplatform.sac.client.user.vo.UserJoinMdnRequestVO;
import com.skplanet.storeplatform.sac.client.user.vo.UserJoinMdnResponseVO;

/**
 * 회원 가입 MDN
 * 
 * Updated on : 2013. 12. 19. Updated by : 심대진, 다모아 솔루션.
 */
@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@TransactionConfiguration
@Transactional
public class UserJoinMdnTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

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
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@After
	public void after() {
	}

	/**
	 * 회원 가입 MDN 단위테스트.
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test
	public void shouldObtainUserDetail() throws Exception {

		new TestCaseTemplate(this.mockMvc).url("/user/join/joinMdn/v1").httpMethod(HttpMethod.POST)
				.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						UserJoinMdnRequestVO reqVo = new UserJoinMdnRequestVO();
						reqVo.setBirth("03월27일");
						return reqVo;
					}
				}).success(UserJoinMdnResponseVO.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {

						System.out.println("### HTTP STATUS : " + httpStatus);

						UserJoinMdnResponseVO resultObj = (UserJoinMdnResponseVO) result;
						System.out.println("result : " + resultObj.toString());

						// assertEquals("ABCDE", resultValue.getTenantId());
					}
				}, HttpStatus.OK).run(RunMode.PROTOBUF);
	}
}
