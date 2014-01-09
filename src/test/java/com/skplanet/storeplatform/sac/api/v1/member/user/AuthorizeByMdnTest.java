package com.skplanet.storeplatform.sac.api.v1.member.user;

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
import org.springframework.web.context.WebApplicationContext;

import com.skplanet.storeplatform.framework.test.RequestBodySetter;
import com.skplanet.storeplatform.framework.test.SuccessCallback;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate.RunMode;
import com.skplanet.storeplatform.sac.api.v1.member.seller.LockAccountTest;
import com.skplanet.storeplatform.sac.client.member.vo.seller.LockAccountRes;

/**
 * 모바일 전용 회원 인증 (MDN 인증) Class Test
 * 
 * Updated on : 2014. 1. 8. Updated by : 반범진, 지티소프트
 */
@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class AuthorizeByMdnTest {

	private static final Logger logger = LoggerFactory
			.getLogger(LockAccountTest.class);

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Test
	public void shouldAuthorizeByMdn() {
		new TestCaseTemplate(this.mockMvc).url("/member/seller/lockAccount/v1")
				.httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {

						return null;
					}
				}).success(LockAccountRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus,
							RunMode runMode) {

					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}
}
