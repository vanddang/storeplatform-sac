package com.skplanet.storeplatform.sac.member.seller.controller;

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

import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.framework.test.RequestBodySetter;
import com.skplanet.storeplatform.framework.test.SuccessCallback;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate.RunMode;
import com.skplanet.storeplatform.sac.client.member.vo.common.PwReminder;
import com.skplanet.storeplatform.sac.client.member.vo.seller.AuthorizeReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.AuthorizeRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ConfirmReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DuplicateByIdEmailReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DuplicateByIdEmailRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.WithdrawReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.WithdrawRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.constant.TestMemberConstant;
import com.skplanet.storeplatform.sac.member.common.util.TestConvertMapperUtils;
import com.skplanet.storeplatform.sac.member.seller.service.SellerSearchService;
import com.skplanet.storeplatform.sac.member.seller.service.SellerService;

/**
 * 판매자 회원 탈퇴
 * 
 * Updated on : 2014. 1. 14. Updated by : 한서구, 부르칸.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class WithdrawTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(WithdrawTest.class);

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Autowired
	private SellerService sellerService;

	@Autowired
	private SellerSearchService sellerSearchService;

	public static TenantHeader tenantHeader;
	public static SacRequestHeader header;

	/** [RESPONSE]. */
	private static AuthorizeRes authorizeRes;

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Before
	public void before() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

		tenantHeader = new TenantHeader();
		tenantHeader.setSystemId("S00-02001");
		tenantHeader.setTenantId("S00");

		header = new SacRequestHeader();
		header.setTenantHeader(tenantHeader);

		// 1.판매자 회원 가입
		int sellerIdNum = 1;
		CreateReq createReq = new CreateReq();

		createReq.setSellerClass("US010101");
		createReq.setSellerId("junit0" + sellerIdNum);
		createReq.setSellerPw("1234qwer");
		createReq.setSellerTelecom("US001201");
		createReq.setIsRecvSms("Y");
		createReq.setSellerEmail(createReq.getSellerId() + "@yopmail.com");
		createReq.setIsRecvEmail("N");
		createReq.setSellerSex("F");
		createReq.setSellerCountry("USA");
		createReq.setSellerLanguage("US004301");
		createReq.setIsDomestic("Y");
		createReq.setRealNameMethod("US011101");
		createReq.setSellerName("개인무료");
		createReq.setSellerCI("1231323123");
		// 보안질문
		List<PwReminder> pwReminders = new ArrayList<PwReminder>();
		PwReminder pwReminder = new PwReminder();
		pwReminder.setAnswerString("temp");
		pwReminder.setQuestionId("Q123");
		pwReminder.setQuestionMessage("qwdwd");
		pwReminders.add(pwReminder);
		createReq.setPwReminderList(pwReminders);

		// 1-1. 아이디 및 이메일 중복 조회
		DuplicateByIdEmailReq duplicateByIdEmailReq = new DuplicateByIdEmailReq();
		duplicateByIdEmailReq.setKeyType("id");
		String isRegistered = MemberConstants.USE_Y;
		while (StringUtils.equals(MemberConstants.USE_Y, isRegistered)) {
			duplicateByIdEmailReq.setKeyString(createReq.getSellerId());
			DuplicateByIdEmailRes duplicateByIdEmailRes = this.sellerSearchService.duplicateByIdEmail(header,
					duplicateByIdEmailReq);
			isRegistered = duplicateByIdEmailRes.getIsRegistered();
			if (MemberConstants.USE_Y.equals(isRegistered)) {
				createReq.setSellerId("junit0" + (sellerIdNum + 1));
				createReq.setSellerEmail(createReq.getSellerId() + "@yopmail.com");
			}
		}
		CreateRes createRes = this.sellerService.regSeller(header, createReq);

		// 2.판매자회원 계정승인
		ConfirmReq confirmReq = new ConfirmReq();
		confirmReq.setSellerKey(createRes.getSellerMbr().getSellerKey());
		this.sellerService.confirm(header, confirmReq);

		// 3.판매자회원 인증
		AuthorizeReq authorizeReq = new AuthorizeReq();
		authorizeReq.setSellerId(createReq.getSellerId());
		authorizeReq.setSellerPw(createReq.getSellerPw());
		authorizeReq.setExpireDate("1");

		authorizeRes = this.sellerService.authorize(header, authorizeReq);
		LOGGER.debug(TestConvertMapperUtils.convertObjectToJson(authorizeRes));
	}

	/**
	 * <pre>
	 * 판매자 회원 탈퇴.
	 * </pre>
	 */
	@Test
	public void withdrawSeller() {

		new TestCaseTemplate(this.mockMvc).url(TestMemberConstant.PREFIX_SELLER_PATH + "/withdraw/v1")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						WithdrawReq req = new WithdrawReq();
						req.setSellerKey(authorizeRes.getSellerMbr().getSellerKey());
						req.setSessionKey(authorizeRes.getSessionKey());
						req.setSecedeReasonCode("US010401");
						req.setSecedeReasonMessage("ID 변경");
						LOGGER.debug("request param : {}", req.toString());
						return req;
					}
				}).success(WithdrawRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						WithdrawRes res = (WithdrawRes) result;
						assertThat(res.getSellerKey(), notNullValue());
						LOGGER.debug("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

}
