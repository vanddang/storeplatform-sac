package com.skplanet.storeplatform.sac.member.seller.controller;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.After;
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
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyWaitEmailSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyWaitEmailSacRes;
import com.skplanet.storeplatform.sac.member.common.constant.TestMemberConstant;

/**
 * 2.2.33. 가가입 이메일 수정.
 * 
 * Updated on : 2014. 3. 5. Updated by : Rejoice, Burkhan
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class ModifyWaitEmailTest {
	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	private static ModifyWaitEmailSacReq req;

	private static ModifyWaitEmailSacRes res;

	/**
	 * 
	 * <pre>
	 * TestCase 수행 전 실행 Method.
	 * </pre>
	 */
	@Before
	public void before() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		req = new ModifyWaitEmailSacReq();
	}

	/**
	 * <pre>
	 * TestCase 수행 후 실행 Method.
	 * </pre>
	 */
	@After
	public void after() {

	}

	/**
	 * <pre>
	 * 가가입 회원 이메일 수정.
	 * </pre>
	 */
	@Test
	public void testModifyWaitEmail() {
		new TestCaseTemplate(this.mockMvc).url(TestMemberConstant.PREFIX_SELLER_PATH + "/modifyWaitEmail/v1")
				.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						req.setSellerId("test00001");
						req.setNewEmailAddress("test00001@yopmail.com");
						return req;
					}
				}).success(ModifyWaitEmailSacRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						res = (ModifyWaitEmailSacRes) result;
						assertThat(res, notNullValue());
						assertThat(res.getSellerKey(), notNullValue());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
	}
}
