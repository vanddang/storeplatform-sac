package com.skplanet.storeplatform.sac.api.v1.member.seller;

import static org.hamcrest.CoreMatchers.is;
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
import com.skplanet.storeplatform.sac.api.v1.member.ConvertMapperUtil;
import com.skplanet.storeplatform.sac.api.v1.member.constant.MemberTestConstant;
import com.skplanet.storeplatform.sac.client.member.vo.common.AgreementInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.PwReminder;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateRes;

/**
 * 판매자 회원 가입
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

	public String sellerId;

	/**
	 * 
	 * <pre>
	 * before method 설명.
	 * </pre>
	 */
	@Before
	public void before() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		this.sellerId = "";
	}

	@After
	public void after() {
		LOGGER.debug("sellerId : {}", this.sellerId);
	}

	/**
	 * <pre>
	 * 판매자 회원가입(개인/무료).
	 * </pre>
	 */
	@Test
	public void createSellerPersonNopay() {

		new TestCaseTemplate(this.mockMvc).url(MemberTestConstant.PREFIX_SELLER_PATH + "/create/v1")
				.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						CreateReq req = new CreateReq();
						req.setSellerClass("US010101");
						req.setSellerCategory("US011301");
						req.setSellerId("sellerPersonNoPay01");
						req.setSellerPW("1234");
						req.setSellerTelecom("US001201");
						req.setIsRecvSMS("Y");
						req.setSellerEmail("abc@acd.com");
						req.setIsRecvEmail("N");
						req.setSellerSex("F");
						req.setSellerCountry("USA");
						req.setSellerLanguage("US004301");
						req.setIsForeign("Y");
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

						CreateSellerTest.this.sellerId = req.getSellerId();
						LOGGER.debug(CreateSellerTest.this.sellerId);
						LOGGER.debug(ConvertMapperUtil.convertObjectToJson(req));
						return req;
					}
				}).success(CreateRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						CreateRes res = (CreateRes) result;
						assertThat(res.getSellerMbr(), notNullValue());
						assertThat(res.getSellerMbr().getSellerClass(), is("US010101"));
						assertThat(res.getSellerMbr().getSellerCategory(), is("US011301"));
						assertEquals(res.getSellerMbr().getSellerId(), "sellerPersonNoPay01");
						LOGGER.debug("response param : {}", res.toString());
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
				.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						CreateReq req = new CreateReq();

						req.setSellerClass("US010101");
						req.setSellerCategory("US011302");
						req.setSellerId("sellerPersonPay01");
						req.setSellerPW("1234");
						req.setSellerTelecom("US001201");
						req.setIsRecvSMS("Y");
						req.setSellerEmail("abc@acd.com");
						req.setIsRecvEmail("N");
						req.setSellerSex("F");
						req.setSellerCountry("USA");
						req.setSellerLanguage("US004301");
						req.setIsForeign("Y");
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

						LOGGER.debug(ConvertMapperUtil.convertObjectToJson(req));
						return req;
					}
				}).success(CreateRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						CreateRes res = (CreateRes) result;
						assertThat(res.getSellerMbr(), notNullValue());
						assertThat(res.getSellerMbr().getSellerClass(), is("US010101"));
						assertThat(res.getSellerMbr().getSellerCategory(), is("US011302"));
						assertEquals(res.getSellerMbr().getSellerId(), "sellerPersonPay01");
						LOGGER.debug("response param : {}", res.toString());
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
				.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						CreateReq req = new CreateReq();

						req.setSellerClass("US010102");
						req.setSellerCategory("US011301");
						req.setSellerId("sellerBusinessNoPay01");
						req.setSellerPW("1234");
						req.setSellerTelecom("US001201");
						req.setIsRecvSMS("Y");
						req.setSellerEmail("abc@acd.com");
						req.setIsRecvEmail("N");
						req.setSellerSex("F");
						req.setSellerCountry("USA");
						req.setSellerLanguage("US004301");
						req.setIsForeign("Y");
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

						LOGGER.debug(ConvertMapperUtil.convertObjectToJson(req));
						return req;
					}
				}).success(CreateRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						CreateRes res = (CreateRes) result;
						assertThat(res.getSellerMbr(), notNullValue());
						assertThat(res.getSellerMbr().getSellerClass(), is("US010102"));
						assertThat(res.getSellerMbr().getSellerCategory(), is("US011301"));
						assertEquals(res.getSellerMbr().getSellerId(), "sellerBusinessNoPay01");
						LOGGER.debug("response param : {}", res.toString());
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
				.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						CreateReq req = new CreateReq();

						req.setSellerClass("US010102");
						req.setSellerCategory("US011302");
						req.setSellerId("sellerBusinessNoPay01");
						req.setSellerPW("1234");
						req.setSellerTelecom("US001201");
						req.setIsRecvSMS("Y");
						req.setSellerEmail("abc@acd.com");
						req.setIsRecvEmail("N");
						req.setSellerSex("F");
						req.setSellerCountry("USA");
						req.setSellerLanguage("US004301");
						req.setIsForeign("Y");
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

						LOGGER.debug(ConvertMapperUtil.convertObjectToJson(req));
						return req;
					}
				}).success(CreateRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						CreateRes res = (CreateRes) result;
						assertThat(res.getSellerMbr(), notNullValue());
						assertEquals(res.getSellerMbr().getSellerId(), "seller_test1234");
						LOGGER.debug("response param : {}", res.toString());
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
				.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						CreateReq req = new CreateReq();

						req.setSellerClass("US010103");
						req.setSellerCategory("US011301");
						req.setSellerId("sellerLegalBusinessNoPay01");
						req.setSellerPW("1234");
						req.setSellerTelecom("US001201");
						req.setIsRecvSMS("Y");
						req.setSellerEmail("abc@acd.com");
						req.setIsRecvEmail("N");
						req.setSellerSex("F");
						req.setSellerCountry("USA");
						req.setSellerLanguage("US004301");
						req.setIsForeign("Y");
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

						LOGGER.debug(ConvertMapperUtil.convertObjectToJson(req));
						return req;
					}
				}).success(CreateRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						CreateRes res = (CreateRes) result;
						assertThat(res.getSellerMbr(), notNullValue());
						assertEquals(res.getSellerMbr().getSellerId(), "seller_test1234");
						LOGGER.debug("response param : {}", res.toString());
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
				.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						CreateReq req = new CreateReq();

						req.setSellerClass("US010103");
						req.setSellerCategory("US011302");
						req.setSellerId("sellerLegalBusinessPay01");
						req.setSellerPW("1234");
						req.setSellerTelecom("US001201");
						req.setIsRecvSMS("Y");
						req.setSellerEmail("abc@acd.com");
						req.setIsRecvEmail("N");
						req.setSellerSex("F");
						req.setSellerCountry("USA");
						req.setSellerLanguage("US004301");
						req.setIsForeign("Y");
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

						LOGGER.debug(ConvertMapperUtil.convertObjectToJson(req));
						return req;
					}
				}).success(CreateRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						CreateRes res = (CreateRes) result;
						assertThat(res.getSellerMbr(), notNullValue());
						assertEquals(res.getSellerMbr().getSellerId(), "seller_test1234");
						LOGGER.debug("response param : {}", res.toString());
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
				.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						CreateReq req = new CreateReq();

						req.setSellerClass("US010103");
						req.setSellerCategory("US011303");
						req.setSellerId("sellerLegalBusinessBP01");
						req.setSellerPW("1234");
						req.setSellerTelecom("US001201");
						req.setIsRecvSMS("Y");
						req.setSellerEmail("abc@acd.com");
						req.setIsRecvEmail("N");
						req.setSellerSex("F");
						req.setSellerCountry("USA");
						req.setSellerLanguage("US004301");
						req.setIsForeign("Y");
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

						LOGGER.debug(ConvertMapperUtil.convertObjectToJson(req));
						return req;
					}
				}).success(CreateRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						CreateRes res = (CreateRes) result;
						assertThat(res.getSellerMbr(), notNullValue());
						assertEquals(res.getSellerMbr().getSellerId(), "seller_test1234");
						LOGGER.debug("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}
}
