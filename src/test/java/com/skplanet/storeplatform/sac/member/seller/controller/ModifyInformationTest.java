//package com.skplanet.storeplatform.sac.member.seller.controller;
//
//import static org.hamcrest.CoreMatchers.notNullValue;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertThat;
//import static org.junit.Assert.fail;
//
//import org.apache.commons.lang3.StringUtils;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.annotation.DirtiesContext.ClassMode;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import com.skplanet.storeplatform.framework.test.RequestBodySetter;
//import com.skplanet.storeplatform.framework.test.SuccessCallback;
//import com.skplanet.storeplatform.framework.test.TestCaseTemplate;
//import com.skplanet.storeplatform.framework.test.TestCaseTemplate.RunMode;
//import com.skplanet.storeplatform.sac.client.member.vo.seller.AuthorizeReq;
//import com.skplanet.storeplatform.sac.client.member.vo.seller.AuthorizeRes;
//import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyInformationSacReq;
//import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyInformationSacRes;
//import com.skplanet.storeplatform.sac.member.common.constant.TestMemberConstant;
//import com.skplanet.storeplatform.sac.member.common.util.TestConvertMapperUtils;
//
///**
// * 2.2.10. 판매자 회원 기본정보 수정 ,before - 로그인 [sellerId, sellerPW, expireDate] 필요
// * 
// * Updated on : 2014. 2. 3. Updated by : 김경복, 부르칸
// */
//@ActiveProfiles(value = "local")
//@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
//@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
//@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
//public class ModifyInformationTest {
//
//	private static final Logger LOGGER = LoggerFactory.getLogger(ModifyInformationTest.class);
//
//	@Autowired
//	private WebApplicationContext wac;
//
//	private MockMvc mockMvc;
//
//	/** [REQUEST]. */
//	private static ModifyInformationSacReq req;
//
//	/** [RESPONSE]. */
//	private static ModifyInformationSacRes res;
//
//	/** LOGIN-[REQUEST]. */
//	private static AuthorizeReq authorizeReq;
//	/** LOGIN-[RESPONSE]. */
//	private static AuthorizeRes authorizeRes;
//
//	/** [HEADER]. */
//	private static String xStoreAuthInfo;
//
//	/**
//	 * <pre>
//	 * before method.
//	 * </pre>
//	 */
//	@Before
//	public void before() {
//		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
//		// TODO 임시 [HEADER] 주입
//		xStoreAuthInfo = "authKey=114127c7ef42667669819dad5df8d820c;ist=N";
//
//		// [REQUEST] 초기화
//		req = new ModifyInformationSacReq();
//		authorizeReq = new AuthorizeReq();
//
//		// 로그인 데이터 주입
//		authorizeReq.setSellerId("changTest04");
//		authorizeReq.setSellerPW("1234");
//		authorizeReq.setExpireDate("2");
//
//		// 로그인 콜
//		new TestCaseTemplate(this.mockMvc).url(TestMemberConstant.PREFIX_SELLER_PATH + "/authorize/v1")
//				.addHeaders("x-store-auth-info", xStoreAuthInfo).httpMethod(HttpMethod.POST)
//				.requestBody(new RequestBodySetter() {
//					@Override
//					public Object requestBody() {
//						LOGGER.debug("[REQUEST(SAC)-회원인증] : \n{}",
//								TestConvertMapperUtils.convertObjectToJson(authorizeReq));
//						return authorizeReq;
//					}
//				}).success(AuthorizeRes.class, new SuccessCallback() {
//					@Override
//					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
//						authorizeRes = (AuthorizeRes) result;
//					}
//				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
//
//		// 로그인 결과 검증
//		if (StringUtils.equals(TestMemberConstant.USE_Y, authorizeRes.getIsLoginSuccess())) {
//			// 정보수정의 위한 데이터 주입
//			// req.setSellerId(authorizeReq.getSellerId());
//			req.setSellerKey(authorizeRes.getSellerMbr().getSellerKey());
//			req.setSessionKey(authorizeRes.getSessionKey());
//		} else {
//			fail("수정 불가 회원입니다.");
//		}
//	}
//
//	/**
//	 * <pre>
//	 * method 설명.
//	 * </pre>
//	 */
//	@After
//	public void after() {
//		// Debug
//		LOGGER.debug("[RESPONSE(SAC)-회원기본정보 수정] : \n{}", TestConvertMapperUtils.convertObjectToJson(res));
//	}
//
//	/**
//	 * <pre>
//	 * 기본정보 수정.
//	 * </pre>
//	 */
//	@Test
//	public void modifyInformationTest() {
//		new TestCaseTemplate(this.mockMvc).url(TestMemberConstant.PREFIX_SELLER_PATH + "/modifyInformation/v1")
//				.addHeaders("x-store-auth-info", xStoreAuthInfo).httpMethod(HttpMethod.POST)
//				.requestBody(new RequestBodySetter() {
//					@Override
//					public Object requestBody() {
//						req.setSellerTelecom("US001201");
//						req.setSellerCountry("ko");
//						req.setSellerLanguage("ko");
//						req.setSellerBirthDay("19900325");
//						req.setIsDomestic("Y");
//						req.setSellerPhone("010XXXX2345");
//						req.setSellerSex("M");
//						req.setSellerName("국내개인무료사용자2");
//						req.setSellerCompany("Supernatural");
//						req.setIsRecvSms("Y");
//						req.setIsRecvEmail("Y");
//						req.setRepEmail("");
//						req.setRepPhone("");
//						req.setRepPhoneArea("");
//						// Debug
//						LOGGER.debug("[REQUEST (SAC)-회원기본정보 수정] : \n{}",
//								TestConvertMapperUtils.convertObjectToJson(req));
//						return req;
//					}
//				}).success(AuthorizeRes.class, new SuccessCallback() {
//					@Override
//					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
//						res = (ModifyInformationSacRes) result;
//						assertThat(res.getSellerKey(), notNullValue());
//						assertEquals(res.getSellerKey(), req.getSellerKey());
//					}
//				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
//	}
// }
