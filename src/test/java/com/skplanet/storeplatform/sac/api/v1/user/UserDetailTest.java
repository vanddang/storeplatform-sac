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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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
import com.skplanet.storeplatform.sc.client.vo.UserSearchVO;

/**
 * 회원 상세 테스트
 * 
 * Updated on : 2013-09-01 Updated by : 아키텍쳐팀, SK플래닛.
 */
@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@TransactionConfiguration
@Transactional
public class UserDetailTest {

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

		new TestCaseTemplate(this.mockMvc).url("/bypass/user/detail/1").httpMethod(HttpMethod.POST)
				.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						UserSearchVO userVO = new UserSearchVO();

						userVO.setId(userId);
						userVO.setName("name");
						userVO.setEmail("email");
						userVO.setRealName("realname");

						return userVO;
					}
				}).success(UserSearchVO.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						UserSearchVO resultVO = (UserSearchVO) result;

						assertThat(resultVO.getId(), is(userId));
						assertThat(resultVO.getName(), is("Name_1"));
						assertThat(resultVO.getEmail(), is("10001@sk.com"));
						assertThat(resultVO.getRealName(), is("Real Name_1"));
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).complete(new CompleteCallback() {
					@Override
					public void complete(HttpServletRequest request, HttpServletResponse response) {
						// JUnit Assert 작성
					}
				}).error(new ErrorCallback() {
					@Override
					public void error(Exception ex, HttpServletRequest request, HttpServletResponse response) {
						// JUnit Assert 작성
					}
				}).run(RunMode.JSON, RunMode.PROTOBUF);
	}
}
