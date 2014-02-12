package com.skplanet.storeplatform.sac.member.seller.controller;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

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
import com.skplanet.storeplatform.sac.client.member.vo.seller.AuthorizeReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.AuthorizeRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyAccountInformationSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyAccountInformationSacRes;
import com.skplanet.storeplatform.sac.member.common.constant.TestMemberConstant;
import com.skplanet.storeplatform.sac.member.common.util.TestConvertMapperUtils;

/**
 * 2.2.11. 판매자 회원 정산 정보 수정
 * 
 * Updated on : 2014. 2. 10. Updated by : 김경복, 부르칸
 */
@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class ModifyAccountInformationTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ModifyAccountInformationTest.class);

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	/** LOGIN-[REQUEST]. */
	public static AuthorizeReq authorizeReq;
	/** LOGIN-[RESPONSE]. */
	public static AuthorizeRes authorizeRes;
	/** [HEADER]. */
	private static String xStoreAuthInfo;

	/** [REQUEST]. */
	public static ModifyAccountInformationSacReq req;

	/** [RESPONSE]. */
	public static ModifyAccountInformationSacRes res;

	/**
	 * <pre>
	 * 정보 수정을 위해 로그인 사전 작업.
	 * </pre>
	 */
	@Before
	public void before() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		// TODO 임시 [HEADER] 주입
		xStoreAuthInfo = "authKey=114127c7ef42667669819dad5df8d820c;ist=N";

		// [REQUEST] 초기화
		req = new ModifyAccountInformationSacReq();
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
			req.setSellerId(authorizeRes.getSellerMbr().getSellerId());
			req.setSellerKey(authorizeRes.getSellerMbr().getSellerKey());
			req.setSessionKey(authorizeRes.getSessionKey());
		} else {
			fail("수정 불가 회원입니다.");
		}
	}

	/**
	 * <pre>
	 * Test 결과 Debug Log.
	 * </pre>
	 */
	@After
	public void after() {
		// Debug
		LOGGER.debug("[RESPONSE(SAC)-회원정산정보 수정] : \n{}", TestConvertMapperUtils.convertObjectToJson(res));
	}

	@Test
	public void testModifyAccountInformation() {
		new TestCaseTemplate(this.mockMvc).url(TestMemberConstant.PREFIX_SELLER_PATH + "/modifyInformation/v1")
				.addHeaders("x-store-auth-info", xStoreAuthInfo).httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						req.setSellerClass("US010101");
						req.setSellerCategory("US011301");
						req.setSellerMainStatus("US010201");
						req.setSellerSubStatus("US010301");

						// Debug
						LOGGER.debug("[REQUEST (SAC)-회원기본정보 수정] : \n{}",
								TestConvertMapperUtils.convertObjectToJson(req));
						return req;
					}
				}).success(AuthorizeRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						res = (ModifyAccountInformationSacRes) result;
						assertThat(res.getSellerKey(), notNullValue());
						assertEquals(res.getSellerKey(), req.getSellerKey());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
	}
}
