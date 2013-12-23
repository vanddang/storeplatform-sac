/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.api.v1.seller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.skplanet.storeplatform.framework.test.CompleteCallback;
import com.skplanet.storeplatform.framework.test.ErrorCallback;
import com.skplanet.storeplatform.framework.test.RequestBodySetter;
import com.skplanet.storeplatform.framework.test.SuccessCallback;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate.RunMode;
import com.skplanet.storeplatform.sac.client.user.vo.SellerSecedeResonListResponseVO;

/**
 * 판매자 탈퇴사유 리스트 조회 테스트
 * 
 * Updated on : 2013-12-23 Updated by : 반범진, 지티소프트.
 */
@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@TransactionConfiguration
@Transactional
public class SellerSecedeResonListTest {

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
	 * 회원 상세 단위테스트.
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test
	public void shouldObtainUserDetail() throws Exception {
		final int userId = 10001;

		new TestCaseTemplate(this.mockMvc)
				.url("/seller/secedeResonList/v1")
				.httpMethod(HttpMethod.POST)
				.addHeaders("x-store-auth-info",
						"authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						return null;

					}
				})
				.success(SellerSecedeResonListResponseVO.class,
						new SuccessCallback() {
							@Override
							public void success(Object result,
									HttpStatus httpStatus, RunMode runMode) {
								SellerSecedeResonListResponseVO resultVO = (SellerSecedeResonListResponseVO) result;
							}
						}, HttpStatus.OK, HttpStatus.ACCEPTED)
				.complete(new CompleteCallback() {
					@Override
					public void complete(HttpServletRequest request,
							HttpServletResponse response) {
						// JUnit Assert 작성
					}
				}).error(new ErrorCallback() {
					@Override
					public void error(Exception ex, HttpServletRequest request,
							HttpServletResponse response) {
						// JUnit Assert 작성
					}
				}).run(RunMode.PROTOBUF);
	}
}
