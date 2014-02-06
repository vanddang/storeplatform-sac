package com.skplanet.storeplatform.sac.api.v1.member.seller;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.skplanet.storeplatform.framework.test.RequestBodySetter;
import com.skplanet.storeplatform.framework.test.SuccessCallback;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate.RunMode;
import com.skplanet.storeplatform.sac.api.v1.member.ConvertMapperUtils;
import com.skplanet.storeplatform.sac.api.v1.member.constant.MemberTestConstant;
import com.skplanet.storeplatform.sac.client.member.vo.seller.AuthorizeRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyInformationReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyInformationRes;

/**
 * 2.2.10. 판매자 회원 기본정보 수정
 * 
 * Updated on : 2014. 2. 3. Updated by : 김경복, 부르칸
 */
public class ModifyInformationTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ModifyInformationTest.class);

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	/** [REQUEST]. */
	public static ModifyInformationReq req;

	/** [RESPONSE]. */
	public static ModifyInformationRes res;

	/** [x-store-auth-info]. */
	private static String xStoreAuthInfo;

	@Before
	public void before() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		// [REQUEST] 초기화
		req = new ModifyInformationReq();
		// TODO 임시 [HEADER] 주입
		xStoreAuthInfo = "authKey=114127c7ef42667669819dad5df8d820c;ist=N";
	}

	@After
	public void after() {
		// Debug
		LOGGER.debug("[RESPONSE(SAC)-회원기본정보 수정] : \n{}", ConvertMapperUtils.convertObjectToJson(res));
	}

	/**
	 * <pre>
	 * 기본정보 수정.
	 * </pre>
	 */
	@Test
	public void modifyInformationTest() {
		new TestCaseTemplate(this.mockMvc).url(MemberTestConstant.PREFIX_SELLER_PATH + "/modifyInformation/v1")
				.addHeaders("x-store-auth-info", xStoreAuthInfo).httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						// 국내, 개인(US010101), 무료(US011301), sctest1, 123456 , 국내개인무료사용자 , SE201402051026319660000583
						// sctest1, 123456
						// {
						// "sellerMbr" : {
						// "sellerKey" : "SE201402051026319660000583",
						// "sellerClass" : "US010101",
						// "sellerMainStatus" : "US010201",
						// "sellerSubStatus" : "US010301"
						// },
						// "loginFailCount" : "0",
						// "isLoginSuccess" : "Y",
						// "sessionKey" : "SE201402051026319660000583_Y6dOQGJLmr",
						// "expireDate" : "20140206162408",
						// "isSubSeller" : "N"
						// }
						req.setSessionKey("SE201402051026319660000583_Y6dOQGJLmr");
						req.setSellerKey("SE201402051026319660000583");
						req.setSellerClass("US010101");
						req.setSellerCategory("US011301");
						req.setSellerMainStatus("US010201");
						req.setSellerSubStatus("US010301");
						req.setSellerId("qatestqwe");
						req.setSellerCountry("ko");
						req.setSellerLanguage("ko");
						req.setIsDomestic("Y");
						// 법정대리인 동의 여부
						req.setIsParent("N");
						// req.setIsParent("Y");
						req.setParentRealNameMethod("US011101"); // 휴대폰 인증
						req.setParentType("abc");
						// 실명인증여부
						req.setIsRealName("N");
						// req.setIsRealName("Y");
						req.setSellerCI("");

						LOGGER.debug("[REQUEST (SAC)-회원기본정보 수정] : \n{}", ConvertMapperUtils.convertObjectToJson(req));
						return req;
					}
				}).success(AuthorizeRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						res = (ModifyInformationRes) result;
						assertThat(res.getSellerKey(), notNullValue());
						assertEquals(res.getSellerKey(), req.getSellerKey());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
	}
}
