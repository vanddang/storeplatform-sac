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
import com.skplanet.storeplatform.sac.api.v1.member.constant.MemberTestConstant;
import com.skplanet.storeplatform.sac.client.member.vo.common.PwReminder;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CheckPasswordReminderQuestionReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CheckPasswordReminderQuestionRes;

@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
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
	public void CheckPasswordReminderQuestion() {

		new TestCaseTemplate(this.mockMvc)
				.url(MemberTestConstant.PREFIX_SELLER_PATH + "/checkPasswordReminderQuestion/v1")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						CheckPasswordReminderQuestionReq req = new CheckPasswordReminderQuestionReq();

						req.setSellerID("seller_test");

						// 보안질문
						List<PwReminder> pwReminders = new ArrayList<PwReminder>();
						PwReminder pwReminder = new PwReminder();
						pwReminder.setAnswerString("temp");
						pwReminder.setQuestionID("Q123");
						pwReminder.setQuestionMessage("qwdwd");
						pwReminder.setSellerID("seller_test");
						pwReminders.add(pwReminder);
						req.setpWReminderList(pwReminders);

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
