/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.api.v1.mypage;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

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
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.skplanet.storeplatform.framework.test.RequestBodySetter;
import com.skplanet.storeplatform.framework.test.SuccessCallback;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate.RunMode;
import com.skplanet.storeplatform.sac.client.user.vo.MyPagePurchaseVO;

/**
 * 마이페이지 구매 테스트
 * 
 * Updated on : 2013-09-01 Updated by : 아키텍쳐팀, SK플래닛.
 */
@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring/context-*.xml", "classpath*:/integration/context-*.xml" })
public class SearchMypagePurchaseTest extends AbstractTransactionalJUnit4SpringContextTests {

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
	 * 마이페이지 구매 단위테스트.
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test
	public void shouldObtainMyPagePurchase() throws Exception {

		new TestCaseTemplate(this.mockMvc).url("/entity/user/mypage/purchase/1").httpMethod(HttpMethod.POST)
				.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						MyPagePurchaseVO myPagePurchaseVO = new MyPagePurchaseVO();
						myPagePurchaseVO.setPayId("999999999999");
						myPagePurchaseVO.setId(Integer.MAX_VALUE);
						myPagePurchaseVO.setPid("A123456789");
						myPagePurchaseVO.setNum(Double.MAX_VALUE);
						myPagePurchaseVO.setPurchaseId("111");

						return myPagePurchaseVO;
					}
				}).success(MyPagePurchaseVO.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						MyPagePurchaseVO myPagePurchaseVO = (MyPagePurchaseVO) result;

						assertThat(myPagePurchaseVO.getPurchaseId(), notNullValue());
						assertThat(myPagePurchaseVO.getId(), is(Integer.MAX_VALUE));
						assertThat(myPagePurchaseVO.getNum(), is(Double.MAX_VALUE));
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED)
				// .run(RunMode.JSON);
				.run(RunMode.PROTOBUF);

	}
}
