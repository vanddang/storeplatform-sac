package com.skplanet.storeplatform.sac.api.v1.member.seller;

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
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.seller.sci.SellerSCI;
import com.skplanet.storeplatform.member.client.seller.sci.vo.RemoveSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.RemoveSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateStatusSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateStatusSellerResponse;
import com.skplanet.storeplatform.sac.api.v1.member.ConvertMapperUtils;
import com.skplanet.storeplatform.sac.api.v1.member.constant.MemberTestConstant;
import com.skplanet.storeplatform.sac.client.member.vo.common.AgreementInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.PwReminder;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateRes;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;

/**
 * 2.2.1. 판매자 회원 가입
 * 
 * Updated on : 2014. 1. 13. Updated by : 김경복, 부르칸.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class CreateSellerTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(CreateSellerTest.class);

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Autowired
	private SellerSCI sellerSCI;

	/** [REQUEST]. */
	private static CreateReq req;
	/** [RESPONSE]. */
	private static CreateRes res;
	/** [x-store-auth-info]. */
	private static String xStoreAuthInfo;

	/**
	 * 
	 * <pre>
	 * before method 설명.
	 * </pre>
	 */
	@Before
	public void before() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		// [REQUEST] 초기화
		req = new CreateReq();
		// TODO 임시 [HEADER] 주입
		xStoreAuthInfo = "authKey=114127c7ef42667669819dad5df8d820c;ist=N";
	}

	/**
	 * <pre>
	 * TestCase 가입한 회원을 탈퇴.
	 * 1. 회원상태 변경 [정상]
	 * 2. 회원탈퇴 요청
	 * </pre>
	 */
	@After
	public void after() {
		// Debug - 가입 결과
		LOGGER.debug("[RESPONSE(SAC)-가입] : \n{}", ConvertMapperUtils.convertObjectToJson(res));
		if (res != null) {
			CommonRequest commonRequest = new CommonRequest();
			commonRequest.setSystemID("S001");
			commonRequest.setTenantID("S01");

			// 정상업데이트 [REQUEST]
			UpdateStatusSellerRequest updateStatusSellerRequest = new UpdateStatusSellerRequest();
			updateStatusSellerRequest.setSellerID(res.getSellerMbr().getSellerId());
			updateStatusSellerRequest.setSellerMainStatus(MemberConstants.MAIN_STATUS_NORMAL);
			updateStatusSellerRequest.setSellerSubStatus(MemberConstants.SUB_STATUS_NORMAL);
			updateStatusSellerRequest.setCommonRequest(commonRequest);
			LOGGER.debug("==>>[SC] UpdateStatusSellerRequest.toString() : {}", updateStatusSellerRequest.toString());

			// SC-회원 메인 상태 정상 업데이트 CALL
			UpdateStatusSellerResponse updateStatusSellerResponse = this.sellerSCI
					.updateStatusSeller(updateStatusSellerRequest);

			// Debug - 상태변경[SC] 결과 코드, 메세지
			LOGGER.debug("[sellerSCI.updateStatusSeller()] CODE : {}, MESSAGE : {}", updateStatusSellerResponse
					.getCommonResponse().getResultCode(), updateStatusSellerResponse.getCommonResponse()
					.getResultMessage());

			// Debug - 정상회원으로 변경 결과
			LOGGER.debug("[RESPONSE(SC)-정상회원] : \n{}",
					ConvertMapperUtils.convertObjectToJson(updateStatusSellerResponse));

			// 탈퇴 [REQUEST]
			RemoveSellerRequest request = new RemoveSellerRequest();
			request.setCommonRequest(commonRequest);
			request.setSecedeReasonCode("US001801");
			request.setSecedeReasonMessage("ID 변경");
			request.setSellerKey(res.getSellerMbr().getSellerKey());

			// SC-회원 탈퇴 CALL
			RemoveSellerResponse response = this.sellerSCI.removeSeller(request);
			// Debug - 회원탈퇴[SC] 결과 코드, 메세지
			LOGGER.debug("[sellerSCI.removeSeller()] CODE : {}, MESSAGE : {}", response.getCommonResponse()
					.getResultCode(), response.getCommonResponse().getResultMessage());
			// Debug - 회원탈퇴 결과
			LOGGER.debug("[RESPONSE(SC)-회원탈퇴] : \n{}", ConvertMapperUtils.convertObjectToJson(response));
		}
	}

	/**
	 * <pre>
	 * 판매자 회원가입(개인/무료).
	 * </pre>
	 */
	@Test
	public void createSellerPersonNopay() {
		new TestCaseTemplate(this.mockMvc).url(MemberTestConstant.PREFIX_SELLER_PATH + "/create/v1")
				.addHeaders("x-store-auth-info", xStoreAuthInfo).httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						req.setSellerClass("US010101");
						req.setSellerId("sellerPersonNoPay33");
						// req.setSellerId("sellerTest0003");
						req.setSellerPW("1234");
						req.setSellerTelecom("US001201");
						req.setIsRecvSMS("Y");
						req.setSellerEmail("abc@acd.com");
						req.setIsRecvEmail("N");
						req.setSellerSex("F");
						req.setSellerCountry("USA");
						req.setSellerLanguage("US004301");
						req.setIsDomestic("Y");
						req.setRealNameMethod("US011101");
						req.setSellerName("개인무료");
						req.setSellerCI("1231323123");

						// 약관정보
						List<AgreementInfo> agreementList = new ArrayList<AgreementInfo>();
						AgreementInfo agreement = new AgreementInfo();
						agreement.setExtraAgreementId("US010607");
						agreement.setExtraAgreementVersion("0.1");
						agreement.setIsExtraAgreement("Y");
						agreementList.add(agreement);
						AgreementInfo agreement2 = new AgreementInfo();
						agreement2.setExtraAgreementId("US010608");
						agreement2.setExtraAgreementVersion("0.1");
						agreement2.setIsExtraAgreement("Y");
						agreementList.add(agreement2);
						AgreementInfo agreement3 = new AgreementInfo();
						agreement3.setExtraAgreementId("US010609");
						agreement3.setExtraAgreementVersion("0.1");
						agreement3.setIsExtraAgreement("Y");
						agreementList.add(agreement3);
						req.setAgreementList(agreementList);

						// 보안질문
						List<PwReminder> pwReminders = new ArrayList<PwReminder>();
						PwReminder pwReminder = new PwReminder();
						pwReminder.setAnswerString("temp");
						pwReminder.setQuestionID("Q123");
						pwReminder.setQuestionMessage("qwdwd");
						pwReminders.add(pwReminder);
						req.setPwReminderList(pwReminders);

						LOGGER.debug(ConvertMapperUtils.convertObjectToJson(req));
						return req;
					}
				}).success(CreateRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						res = (CreateRes) result;
						assertThat(res.getSellerMbr(), notNullValue());
						assertEquals(res.getSellerMbr().getSellerId(), req.getSellerId());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
	}

	/**
	 * <pre>
	 * 판매자 회원가입(개인/유료).
	 * </pre>
	 */
	@Test
	public void createSellerPersonPay() {

		new TestCaseTemplate(this.mockMvc).url(MemberTestConstant.PREFIX_SELLER_PATH + "/create/v1")
				.addHeaders("x-store-auth-info", xStoreAuthInfo).httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						req.setSellerClass("US010101");
						req.setSellerId("sellerPersonPay01");
						req.setSellerPW("1234");
						req.setSellerTelecom("US001201");
						req.setIsRecvSMS("Y");
						req.setSellerEmail("abc@acd.com");
						req.setIsRecvEmail("N");
						req.setSellerSex("F");
						req.setSellerCountry("USA");
						req.setSellerLanguage("US004301");
						req.setIsDomestic("Y");
						req.setRealNameMethod("US011101");
						req.setSellerName("개인유료");
						req.setSellerCI("1231323123");

						// 약관정보
						List<AgreementInfo> agreementList = new ArrayList<AgreementInfo>();
						AgreementInfo agreement = new AgreementInfo();
						agreement.setExtraAgreementId("US010607");
						agreement.setExtraAgreementVersion("0.1");
						agreement.setIsExtraAgreement("Y");
						agreementList.add(agreement);

						AgreementInfo agreement2 = new AgreementInfo();
						agreement2.setExtraAgreementId("US010608");
						agreement2.setExtraAgreementVersion("0.1");
						agreement2.setIsExtraAgreement("Y");
						agreementList.add(agreement2);

						AgreementInfo agreement3 = new AgreementInfo();
						agreement3.setExtraAgreementId("US010609");
						agreement3.setExtraAgreementVersion("0.1");
						agreement3.setIsExtraAgreement("Y");
						agreementList.add(agreement3);

						req.setAgreementList(agreementList);

						// 보안질문
						List<PwReminder> pwReminders = new ArrayList<PwReminder>();
						PwReminder pwReminder = new PwReminder();
						pwReminder.setAnswerString("temp");
						pwReminder.setQuestionID("Q123");
						pwReminder.setQuestionMessage("qwdwd");
						pwReminders.add(pwReminder);
						req.setPwReminderList(pwReminders);

						LOGGER.debug(ConvertMapperUtils.convertObjectToJson(req));
						return req;
					}
				}).success(CreateRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						res = (CreateRes) result;
						assertThat(res.getSellerMbr(), notNullValue());
						assertEquals(res.getSellerMbr().getSellerId(), req.getSellerId());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 판매자 회원가입(개인사업자/무료).
	 * </pre>
	 */
	@Test
	public void createSellerBusinessNoPay() {

		new TestCaseTemplate(this.mockMvc).url(MemberTestConstant.PREFIX_SELLER_PATH + "/create/v1")
				.addHeaders("x-store-auth-info", xStoreAuthInfo).httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						req.setSellerClass("US010102");
						req.setSellerId("sellerBusinessNoPay01");
						req.setSellerPW("1234");
						req.setSellerTelecom("US001201");
						req.setIsRecvSMS("Y");
						req.setSellerEmail("abc@acd.com");
						req.setIsRecvEmail("N");
						req.setSellerSex("F");
						req.setSellerCountry("USA");
						req.setSellerLanguage("US004301");
						req.setIsDomestic("Y");
						req.setRealNameMethod("US011101");
						req.setSellerName("개사무료");
						req.setSellerCI("1231323123");

						// 약관정보
						List<AgreementInfo> agreementList = new ArrayList<AgreementInfo>();
						AgreementInfo agreement = new AgreementInfo();
						agreement.setExtraAgreementId("US010607");
						agreement.setExtraAgreementVersion("0.1");
						agreement.setIsExtraAgreement("Y");
						agreementList.add(agreement);

						AgreementInfo agreement2 = new AgreementInfo();
						agreement2.setExtraAgreementId("US010608");
						agreement2.setExtraAgreementVersion("0.1");
						agreement2.setIsExtraAgreement("Y");
						agreementList.add(agreement2);

						AgreementInfo agreement3 = new AgreementInfo();
						agreement3.setExtraAgreementId("US010609");
						agreement3.setExtraAgreementVersion("0.1");
						agreement3.setIsExtraAgreement("Y");
						agreementList.add(agreement3);

						req.setAgreementList(agreementList);

						// 보안질문
						List<PwReminder> pwReminders = new ArrayList<PwReminder>();
						PwReminder pwReminder = new PwReminder();
						pwReminder.setAnswerString("temp");
						pwReminder.setQuestionID("Q123");
						pwReminder.setQuestionMessage("qwdwd");
						pwReminders.add(pwReminder);
						req.setPwReminderList(pwReminders);

						LOGGER.debug(ConvertMapperUtils.convertObjectToJson(req));
						return req;
					}
				}).success(CreateRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						res = (CreateRes) result;
						assertThat(res.getSellerMbr(), notNullValue());
						assertEquals(res.getSellerMbr().getSellerId(), req.getSellerId());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 판매자 회원가입(개인사업자/유료).
	 * </pre>
	 */
	@Test
	public void createSellerBusinessPay() {

		new TestCaseTemplate(this.mockMvc).url(MemberTestConstant.PREFIX_SELLER_PATH + "/create/v1")
				.addHeaders("x-store-auth-info", xStoreAuthInfo).httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						req.setSellerClass("US010102");
						req.setSellerId("sellerBusinessPay01");
						req.setSellerPW("1234");
						req.setSellerTelecom("US001201");
						req.setIsRecvSMS("Y");
						req.setSellerEmail("abc@acd.com");
						req.setIsRecvEmail("N");
						req.setSellerSex("F");
						req.setSellerCountry("USA");
						req.setSellerLanguage("US004301");
						req.setIsDomestic("Y");
						req.setRealNameMethod("US011101");
						req.setSellerName("개사유료");
						req.setSellerCI("1231323123");

						// 약관정보
						List<AgreementInfo> agreementList = new ArrayList<AgreementInfo>();
						AgreementInfo agreement = new AgreementInfo();
						agreement.setExtraAgreementId("US010607");
						agreement.setExtraAgreementVersion("0.1");
						agreement.setIsExtraAgreement("Y");
						agreementList.add(agreement);

						AgreementInfo agreement2 = new AgreementInfo();
						agreement2.setExtraAgreementId("US010608");
						agreement2.setExtraAgreementVersion("0.1");
						agreement2.setIsExtraAgreement("Y");
						agreementList.add(agreement2);

						AgreementInfo agreement3 = new AgreementInfo();
						agreement3.setExtraAgreementId("US010609");
						agreement3.setExtraAgreementVersion("0.1");
						agreement3.setIsExtraAgreement("Y");
						agreementList.add(agreement3);

						req.setAgreementList(agreementList);

						// 보안질문
						List<PwReminder> pwReminders = new ArrayList<PwReminder>();
						PwReminder pwReminder = new PwReminder();
						pwReminder.setAnswerString("temp");
						pwReminder.setQuestionID("Q123");
						pwReminder.setQuestionMessage("qwdwd");
						pwReminders.add(pwReminder);
						req.setPwReminderList(pwReminders);

						LOGGER.debug(ConvertMapperUtils.convertObjectToJson(req));
						return req;
					}
				}).success(CreateRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						res = (CreateRes) result;
						assertThat(res.getSellerMbr(), notNullValue());
						assertEquals(res.getSellerMbr().getSellerId(), req.getSellerId());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 판매자 회원가입(법인사업자/무료).
	 * </pre>
	 */
	@Test
	public void createSellerLegalBusinessNoPay() {

		new TestCaseTemplate(this.mockMvc).url(MemberTestConstant.PREFIX_SELLER_PATH + "/create/v1")
				.addHeaders("x-store-auth-info", xStoreAuthInfo).httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						req.setSellerClass("US010103");
						req.setSellerId("sellerLegalBusinessNoPay01");
						req.setSellerPW("1234");
						req.setSellerTelecom("US001201");
						req.setIsRecvSMS("Y");
						req.setSellerEmail("abc@acd.com");
						req.setIsRecvEmail("N");
						req.setSellerSex("F");
						req.setSellerCountry("USA");
						req.setSellerLanguage("US004301");
						req.setIsDomestic("Y");
						req.setRealNameMethod("US011101");
						req.setSellerName("법사무료");
						req.setSellerCI("1231323123");

						// 약관정보
						List<AgreementInfo> agreementList = new ArrayList<AgreementInfo>();
						AgreementInfo agreement = new AgreementInfo();
						agreement.setExtraAgreementId("US010607");
						agreement.setExtraAgreementVersion("0.1");
						agreement.setIsExtraAgreement("Y");
						agreementList.add(agreement);

						AgreementInfo agreement2 = new AgreementInfo();
						agreement2.setExtraAgreementId("US010608");
						agreement2.setExtraAgreementVersion("0.1");
						agreement2.setIsExtraAgreement("Y");
						agreementList.add(agreement2);

						AgreementInfo agreement3 = new AgreementInfo();
						agreement3.setExtraAgreementId("US010609");
						agreement3.setExtraAgreementVersion("0.1");
						agreement3.setIsExtraAgreement("Y");
						agreementList.add(agreement3);

						req.setAgreementList(agreementList);

						// 보안질문
						List<PwReminder> pwReminders = new ArrayList<PwReminder>();
						PwReminder pwReminder = new PwReminder();
						pwReminder.setAnswerString("temp");
						pwReminder.setQuestionID("Q123");
						pwReminder.setQuestionMessage("qwdwd");
						pwReminders.add(pwReminder);
						req.setPwReminderList(pwReminders);

						LOGGER.debug(ConvertMapperUtils.convertObjectToJson(req));
						return req;
					}
				}).success(CreateRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						res = (CreateRes) result;
						assertThat(res.getSellerMbr(), notNullValue());
						assertEquals(res.getSellerMbr().getSellerId(), req.getSellerId());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 판매자 회원가입(법인사업자/유료).
	 * </pre>
	 */
	@Test
	public void createSellerLegalBusinessPay() {

		new TestCaseTemplate(this.mockMvc).url(MemberTestConstant.PREFIX_SELLER_PATH + "/create/v1")
				.addHeaders("x-store-auth-info", xStoreAuthInfo).httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						req.setSellerClass("US010103");
						req.setSellerId("sellerLegalBusinessPay01");
						req.setSellerPW("1234");
						req.setSellerTelecom("US001201");
						req.setIsRecvSMS("Y");
						req.setSellerEmail("abc@acd.com");
						req.setIsRecvEmail("N");
						req.setSellerSex("F");
						req.setSellerCountry("USA");
						req.setSellerLanguage("US004301");
						req.setIsDomestic("Y");
						req.setRealNameMethod("US011101");
						req.setSellerName("법사유료");
						req.setSellerCI("1231323123");

						// 약관정보
						List<AgreementInfo> agreementList = new ArrayList<AgreementInfo>();
						AgreementInfo agreement = new AgreementInfo();
						agreement.setExtraAgreementId("US010607");
						agreement.setExtraAgreementVersion("0.1");
						agreement.setIsExtraAgreement("Y");
						agreementList.add(agreement);

						AgreementInfo agreement2 = new AgreementInfo();
						agreement2.setExtraAgreementId("US010608");
						agreement2.setExtraAgreementVersion("0.1");
						agreement2.setIsExtraAgreement("Y");
						agreementList.add(agreement2);

						AgreementInfo agreement3 = new AgreementInfo();
						agreement3.setExtraAgreementId("US010609");
						agreement3.setExtraAgreementVersion("0.1");
						agreement3.setIsExtraAgreement("Y");
						agreementList.add(agreement3);

						req.setAgreementList(agreementList);

						// 보안질문
						List<PwReminder> pwReminders = new ArrayList<PwReminder>();
						PwReminder pwReminder = new PwReminder();
						pwReminder.setAnswerString("temp");
						pwReminder.setQuestionID("Q123");
						pwReminder.setQuestionMessage("qwdwd");
						pwReminders.add(pwReminder);
						req.setPwReminderList(pwReminders);

						LOGGER.debug(ConvertMapperUtils.convertObjectToJson(req));
						return req;
					}
				}).success(CreateRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						res = (CreateRes) result;
						assertThat(res.getSellerMbr(), notNullValue());
						assertEquals(res.getSellerMbr().getSellerId(), req.getSellerId());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 판매자 회원가입(법인사업자/BP).
	 * </pre>
	 */
	@Test
	public void createSellerLegalBusinessBP() {

		new TestCaseTemplate(this.mockMvc).url(MemberTestConstant.PREFIX_SELLER_PATH + "/create/v1")
				.addHeaders("x-store-auth-info", xStoreAuthInfo).httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						req.setSellerClass("US010103");
						req.setSellerId("sellerLegalBusinessBP01");
						req.setSellerPW("1234");
						req.setSellerTelecom("US001201");
						req.setIsRecvSMS("Y");
						req.setSellerEmail("abc@acd.com");
						req.setIsRecvEmail("N");
						req.setSellerSex("F");
						req.setSellerCountry("USA");
						req.setSellerLanguage("US004301");
						req.setIsDomestic("Y");
						req.setRealNameMethod("US011101");
						req.setSellerName("법사BP");
						req.setSellerCI("1231323123");

						// 약관정보
						List<AgreementInfo> agreementList = new ArrayList<AgreementInfo>();
						AgreementInfo agreement = new AgreementInfo();
						agreement.setExtraAgreementId("US010607");
						agreement.setExtraAgreementVersion("0.1");
						agreement.setIsExtraAgreement("Y");
						agreementList.add(agreement);

						AgreementInfo agreement2 = new AgreementInfo();
						agreement2.setExtraAgreementId("US010608");
						agreement2.setExtraAgreementVersion("0.1");
						agreement2.setIsExtraAgreement("Y");
						agreementList.add(agreement2);

						AgreementInfo agreement3 = new AgreementInfo();
						agreement3.setExtraAgreementId("US010609");
						agreement3.setExtraAgreementVersion("0.1");
						agreement3.setIsExtraAgreement("Y");
						agreementList.add(agreement3);

						req.setAgreementList(agreementList);

						// 보안질문
						List<PwReminder> pwReminders = new ArrayList<PwReminder>();
						PwReminder pwReminder = new PwReminder();
						pwReminder.setAnswerString("temp");
						pwReminder.setQuestionID("Q123");
						pwReminder.setQuestionMessage("qwdwd");
						pwReminders.add(pwReminder);
						req.setPwReminderList(pwReminders);

						LOGGER.debug(ConvertMapperUtils.convertObjectToJson(req));
						return req;
					}
				}).success(CreateRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						res = (CreateRes) result;
						assertThat(res.getSellerMbr(), notNullValue());
						assertEquals(res.getSellerMbr().getSellerId(), req.getSellerId());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}
}
