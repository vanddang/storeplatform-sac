package com.skplanet.storeplatform.sac.api.v1.member.seller;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

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

	/**
	 * 
	 * <pre>
	 * before method 설명.
	 * </pre>
	 */
	@Before
	public void before() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	/**
	 * <pre>
	 * 판매자 회원가입.
	 * </pre>
	 */
	@Test
	public void createSeller() {

		new TestCaseTemplate(this.mockMvc).url(MemberTestConstant.PREFIX_SELLER_PATH + "/create/v1")
				.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						CreateReq req = new CreateReq();

						req.setSellerClass("US010101");
						req.setSellerCategory("US011301");
						req.setSellerMainStatus("US010201");
						req.setSellerSubStatus("US010301");
						req.setSellerId("seller_test1234");
						req.setSellerPW("awdawe123dw2");
						req.setSellerTelecom("US001201");
						req.setIsRecvSMS("Y");
						req.setSellerEmail("abc@acd.com");
						req.setIsRecvEmail("N");
						req.setSellerSex("F");
						req.setSellerCountry("USA");
						req.setSellerLanguage("US004301");
						req.setIsForeign("Y");
						req.setRealNameMethod("US011101");

						req.setIsRealName("N");
						req.setSellerName("김삼순");
						req.setSellerCI("1231323123");

						// 약관정보
						List<AgreementInfo> agreementList = new ArrayList<AgreementInfo>();
						AgreementInfo agreement = new AgreementInfo();
						agreement.setExtraAgreementId("a");
						agreement.setExtraAgreementVersion("a");
						agreement.setIsExtraAgreement("a");
						agreementList.add(agreement);
						req.setAgreementList(agreementList);
						// 보안질문
						List<PwReminder> pwReminders = new ArrayList<PwReminder>();
						PwReminder pwReminder = new PwReminder();
						pwReminder.setAnswerString("temp");
						pwReminder.setQuestionID("Q123");
						pwReminder.setQuestionMessage("qwdwd");
						pwReminders.add(pwReminder);
						req.setPwReminderList(pwReminders);

						LOGGER.debug("request param : {}", req.toString());
						return req;
					}
				}).success(CreateRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						CreateRes res = (CreateRes) result;
						assertThat(res.getSellerMbr(), notNullValue());
						LOGGER.debug("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}
}
