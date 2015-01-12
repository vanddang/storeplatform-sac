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
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyFlurrySacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyFlurrySacRes;
import com.skplanet.storeplatform.sac.member.common.constant.TestMemberConstant;

/**
 * 2.2.34. Flurry 단건 수정.
 * 
 * Updated on : 2014. 3. 5. Updated by : Rejoice, Burkhan
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class ModifyFlurryTest {
	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	private static ModifyFlurrySacReq req;

	private static ModifyFlurrySacRes res;

	/**
	 * 
	 * <pre>
	 * TestCase 수행 전 실행 Method.
	 * </pre>
	 */
	@Before
	public void before() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		req = new ModifyFlurrySacReq();
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
	 * Flurry 단건 수정.
	 * </pre>
	 */
	@Test
	public void testModifyFlurry() {
		new TestCaseTemplate(this.mockMvc).url(TestMemberConstant.PREFIX_SELLER_PATH + "/modifyFlurry/v1")
				.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						req.setAuthToken("awd23");
						req.setAccessCode("123");
						return req;
					}
				}).success(ModifyFlurrySacRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						res = (ModifyFlurrySacRes) result;
						assertThat(res, notNullValue());
						assertThat(res.getSellerKey(), notNullValue());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
	}
}
