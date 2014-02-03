package com.skplanet.storeplatform.sac.api.v1.member.seller;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

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
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.seller.sci.SellerSCI;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateStatusSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateStatusSellerResponse;
import com.skplanet.storeplatform.sac.api.v1.member.ConvertMapperUtils;
import com.skplanet.storeplatform.sac.api.v1.member.constant.MemberTestConstant;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ConfirmReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ConfirmRes;

/**
 * 2.2.14. 판매자 회원 계정 승인
 * 
 * Updated on : 2014. 1. 24. Updated by : 김경복, 부르칸
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class ConfirmTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfirmTest.class);

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Autowired
	private SellerSCI sellerSCI;

	/** [REQUEST]. */
	private static ConfirmReq req;

	/** [RESPONSE]. */
	private static ConfirmRes res;

	private static String caseType = "BAD";

	/**
	 * 
	 * <pre>
	 * TestCase 수행 전 실행 Method.
	 * 인증 요청 [REQUEST] 객체 생성
	 * </pre>
	 */
	@Before
	public void before() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		req = new ConfirmReq();
	}

	/**
	 * <pre>
	 * TestCase 수행 후 실행 Method.
	 * 인증 요청 결과 LOGGER 실행
	 * </pre>
	 */
	@After
	public void after() {
		LOGGER.debug("[RESPONSE] : \n{}", ConvertMapperUtils.convertObjectToJson(res));
		// TestCase [정상] Test일 경우만 회원 상태 원복
		if ("NORMAL".equals(caseType)) {
			CommonRequest commonRequest = new CommonRequest();
			commonRequest.setSystemID("S001");
			commonRequest.setTenantID("S01");
			UpdateStatusSellerRequest updateStatusSellerRequest = new UpdateStatusSellerRequest();
			updateStatusSellerRequest.setCommonRequest(commonRequest);
			UpdateStatusSellerResponse updateStatusSellerResponse = this.sellerSCI
					.updateStatusSeller(updateStatusSellerRequest);
			LOGGER.debug("[SellerSCI.updateStatusSeller] CODE : {}, MESSAGE : {}", updateStatusSellerResponse
					.getCommonResponse().getResultCode(), updateStatusSellerResponse.getCommonResponse()
					.getResultMessage());
		}
	}

	/**
	 * <pre>
	 * 계정승인  - 정상(가가입 상태일경우).
	 * </pre>
	 */
	@Test
	public void confirm() {
		new TestCaseTemplate(this.mockMvc).url(MemberTestConstant.PREFIX_SELLER_PATH + "/authorize/v1")
				.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						req.setSellerKey("");
						return req;
					}
				}).success(ConfirmRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						res = (ConfirmRes) result;
						assertThat(res, notNullValue());
						assertEquals(req.getSellerKey(), res.getSellerKey());
						caseType = "NORMAL";
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
	}

	/**
	 * <pre>
	 * 계정 승인  - 회원 메인 상태가 가가입이 아닌경우.
	 * </pre>
	 */
	@Test
	public void confirmBadStatus() {
		new TestCaseTemplate(this.mockMvc).url(MemberTestConstant.PREFIX_SELLER_PATH + "/authorize/v1")
				.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						req.setSellerKey("SE201401271147460550000319");
						return req;
					}
				}).success(ConfirmRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						res = (ConfirmRes) result;
						assertThat(res, notNullValue());
						assertEquals(req.getSellerKey(), res.getSellerKey());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
	}

}
