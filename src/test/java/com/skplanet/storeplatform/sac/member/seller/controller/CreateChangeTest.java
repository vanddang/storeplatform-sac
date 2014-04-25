package com.skplanet.storeplatform.sac.member.seller.controller;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
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
import org.springframework.test.annotation.DirtiesContext;
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
import com.skplanet.storeplatform.member.client.seller.sci.SellerSCI;
import com.skplanet.storeplatform.sac.client.member.vo.common.PwReminder;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateChangeSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateChangeSacRes;
import com.skplanet.storeplatform.sac.member.common.constant.TestMemberConstant;
import com.skplanet.storeplatform.sac.member.common.util.TestConvertMapperUtils;

/**
 * 2.2.35. 판매자회원 전환가입.
 * 
 * Updated on : 2014. 4. 24. Updated by : Rejoice, Burkhan
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class CreateChangeTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(CreateChangeTest.class);

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Autowired
	private SellerSCI sellerSCI;

	/** [REQUEST]. */
	private static CreateChangeSacReq req;
	/** [RESPONSE]. */
	private static CreateChangeSacRes res;
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
		req = new CreateChangeSacReq();
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
	// @After
	public void after() {
		// Debug - 전환가입 결과
		LOGGER.debug("[RESPONSE(SAC)-전환가입] : \n{}", TestConvertMapperUtils.convertObjectToJson(res));
	}

	/**
	 * <pre>
	 * 판매자회원 전환가입.
	 * </pre>
	 */
	@Test
	public void createChangeSeller() {
		new TestCaseTemplate(this.mockMvc).url(TestMemberConstant.PREFIX_SELLER_PATH + "/createChange/v1")
				.addHeaders("x-store-auth-info", xStoreAuthInfo).httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						req.setSellerClass("US010101");
						req.setSellerPw("1234");
						req.setSellerTelecom("US001201");
						req.setIsRecvSms("Y");
						// req.setSellerEmail("abc@acd001.com");
						// req.setIsRecvEmail("N");
						req.setSellerSex("F");
						req.setSellerCountry("USA");
						req.setSellerLanguage("US004301");
						req.setIsDomestic("Y");
						req.setRealNameMethod("US011101");
						req.setSellerCI("1231323123");

						// 보안질문
						List<PwReminder> pwReminders = new ArrayList<PwReminder>();
						PwReminder pwReminder = new PwReminder();
						pwReminder.setAnswerString("temp");
						pwReminder.setQuestionId("Q123");
						pwReminder.setQuestionMessage("qwdwd");
						pwReminders.add(pwReminder);
						req.setPwReminderList(pwReminders);

						LOGGER.debug(TestConvertMapperUtils.convertObjectToJson(req));
						return req;
					}
				}).success(CreateChangeSacRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						res = (CreateChangeSacRes) result;
						assertThat(res.getSellerMbr(), notNullValue());
						assertEquals(res.getSellerMbr().getSellerKey(), req.getSellerKey());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
	}
}
