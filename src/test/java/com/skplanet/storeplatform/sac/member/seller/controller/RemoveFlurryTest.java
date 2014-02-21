package com.skplanet.storeplatform.sac.member.seller.controller;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
import com.skplanet.storeplatform.sac.client.member.vo.seller.AuthorizeReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.AuthorizeRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.RemoveFlurrySacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.RemoveFlurrySacReq.FlurryAuth;
import com.skplanet.storeplatform.sac.client.member.vo.seller.RemoveFlurrySacRes;
import com.skplanet.storeplatform.sac.member.common.constant.TestMemberConstant;
import com.skplanet.storeplatform.sac.member.common.util.TestConvertMapperUtils;

/**
 * 2.2.30. Flurry 삭제
 * 
 * Updated on : 2014. 2. 20. Updated by : Rejoice, Burkhan
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class RemoveFlurryTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(RemoveFlurryTest.class);
	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	/** [REQUEST]. */
	private static RemoveFlurrySacReq req;
	/** [RESPONSE]. */
	private static RemoveFlurrySacRes res;

	/** LOGIN-[REQUEST]. */
	private static AuthorizeReq authorizeReq;
	/** LOGIN-[RESPONSE]. */
	private static AuthorizeRes authorizeRes;

	/** [HEADER]. */
	private static String xStoreAuthInfo;

	/**
	 * <pre>
	 * before method.
	 * </pre>
	 */
	@Before
	public void before() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		// TODO 임시 [HEADER] 주입
		xStoreAuthInfo = "authKey=114127c7ef42667669819dad5df8d820c;ist=N";

		// [REQUEST] 초기화
		req = new RemoveFlurrySacReq();
		authorizeReq = new AuthorizeReq();

		// 로그인 데이터 주입
		authorizeReq.setSellerId("sctest5");
		authorizeReq.setSellerPW("123456");
		authorizeReq.setExpireDate("2");

		// 로그인 콜
		new TestCaseTemplate(this.mockMvc).url(TestMemberConstant.PREFIX_SELLER_PATH + "/authorize/v1")
				.addHeaders("x-store-auth-info", xStoreAuthInfo).httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						LOGGER.debug("[REQUEST(SAC)-회원인증] : \n{}",
								TestConvertMapperUtils.convertObjectToJson(authorizeReq));
						return authorizeReq;
					}
				}).success(AuthorizeRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						authorizeRes = (AuthorizeRes) result;
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

		// 로그인 결과 검증
		if (StringUtils.equals(TestMemberConstant.USE_Y, authorizeRes.getIsLoginSuccess())) {
			// 정보수정의 위한 데이터 주입
			req.setSellerKey(authorizeRes.getSellerMbr().getSellerKey());
			req.setSessionKey(authorizeRes.getSessionKey());
		} else {
			fail("수정 불가 회원입니다.");
		}
	}

	@After
	public void after() {
		// Debug
		LOGGER.debug("[RESPONSE(SAC)-회원기본정보 수정] : \n{}", TestConvertMapperUtils.convertObjectToJson(res));
	}

	/**
	 * <pre>
	 * Flurry 삭제.
	 * </pre>
	 */
	@Test
	public void testRemoveFlurry() {
		new TestCaseTemplate(this.mockMvc).url(TestMemberConstant.PREFIX_SELLER_PATH + "/removeFlurry/v1")
				.addHeaders("x-store-auth-info", xStoreAuthInfo).httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						List<FlurryAuth> flurryAuths = new ArrayList<RemoveFlurrySacReq.FlurryAuth>();
						FlurryAuth flurryAuth1 = new FlurryAuth();
						flurryAuth1.setAccessCode("A1");
						flurryAuths.add(flurryAuth1);
						FlurryAuth flurryAuth2 = new FlurryAuth();
						flurryAuth2.setAccessCode("A2");
						flurryAuths.add(flurryAuth2);
						FlurryAuth flurryAuth3 = new FlurryAuth();
						flurryAuth3.setAccessCode("A3");
						flurryAuths.add(flurryAuth3);
						req.setFlurryAuthList(flurryAuths);
						// Debug
						LOGGER.debug("[REQUEST (SAC)-Flurry 삭제] : \n{}",
								TestConvertMapperUtils.convertObjectToJson(req));
						return req;
					}
				}).success(AuthorizeRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						res = (RemoveFlurrySacRes) result;
						assertThat(res.getSellerKey(), notNullValue());
						assertEquals(res.getSellerKey(), req.getSellerKey());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
	}
}
