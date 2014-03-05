package com.skplanet.storeplatform.sac.member.seller.controller;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateFlurrySacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateFlurrySacReq.FlurryAuth;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateFlurrySacRes;
import com.skplanet.storeplatform.sac.member.common.constant.TestMemberConstant;

/**
 * 2.2.32. Flurry 등록/수정.
 * 
 * Updated on : 2014. 3. 5. Updated by : Rejoice, Burkhan
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class CreateFlurryTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(CreateFlurryTest.class);

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	private static CreateFlurrySacReq req;

	private static CreateFlurrySacRes res;

	/**
	 * 
	 * <pre>
	 * TestCase 수행 전 실행 Method.
	 * </pre>
	 */
	@Before
	public void before() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		req = new CreateFlurrySacReq();
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
	 * Flurry 등록/수정.
	 * </pre>
	 */
	@Test
	public void testCreateFlurry() {
		new TestCaseTemplate(this.mockMvc).url(TestMemberConstant.PREFIX_SELLER_PATH + "/createFlurry/v1")
				.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						//
						req.setSessionKey("");
						req.setSellerKey("SE201401241304384640000211");

						List<FlurryAuth> flurryAuthList = new ArrayList<CreateFlurrySacReq.FlurryAuth>();

						FlurryAuth flurryAuth = new FlurryAuth();
						flurryAuth.setAuthToken("123");
						flurryAuth.setAccessCode("asd");
						flurryAuthList.add(flurryAuth);

						FlurryAuth flurryAuth2 = new FlurryAuth();
						flurryAuth2.setAuthToken("123");
						flurryAuthList.add(flurryAuth2);

						req.setFlurryAuthList(flurryAuthList);
						return req;
					}
				}).success(CreateFlurrySacRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						res = (CreateFlurrySacRes) result;
						assertThat(res, notNullValue());
						assertEquals(req.getSellerKey(), res.getSellerKey());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
	}
}
