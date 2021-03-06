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
import com.skplanet.storeplatform.sac.client.member.vo.seller.CheckPasswordReminderQuestionReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CheckPasswordReminderQuestionReq.PwReminder;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CheckPasswordReminderQuestionRes;
import com.skplanet.storeplatform.sac.member.common.constant.TestMemberConstant;

/**
 * Password 보안 질문 확인.
 * 
 * Updated on : 2014. 4. 24. Updated by : Rejoice, Burkhan
 */
@ActiveProfiles(value = "local")

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class CheckPasswordReminderQuestionTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(CheckPasswordReminderQuestionTest.class);

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Before
	public void before() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	/**
	 * <pre>
	 * Password 보안 질문 확인.
	 * </pre>
	 */
	@Test
	public void checkPasswordReminderQuestion() {

		new TestCaseTemplate(this.mockMvc)
				.url(TestMemberConstant.PREFIX_SELLER_PATH + "/checkPasswordReminderQuestion/v1")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						CheckPasswordReminderQuestionReq req = new CheckPasswordReminderQuestionReq();

						req.setSellerId("seller_test");

						// 보안질문
						List<PwReminder> pwReminders = new ArrayList<PwReminder>();
						PwReminder pwReminder = new PwReminder();
						pwReminder.setAnswerString("Seoul - Korea");
						pwReminder.setQuestionId("QUESTION4");
						pwReminders.add(pwReminder);
						req.setPwReminderList(pwReminders);

						LOGGER.debug("request param : {}", req.toString());
						return req;
					}
				}).success(CheckPasswordReminderQuestionRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						CheckPasswordReminderQuestionRes res = (CheckPasswordReminderQuestionRes) result;
						assertThat(res.getIsCorrect(), notNullValue());
						LOGGER.debug("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

}
