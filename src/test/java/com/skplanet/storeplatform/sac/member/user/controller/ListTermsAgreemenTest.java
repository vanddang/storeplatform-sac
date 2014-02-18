package com.skplanet.storeplatform.sac.member.user.controller;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
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

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.test.SuccessCallback;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate.RunMode;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListTermsAgreementSacRes;

@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ListTermsAgreemenTest {
	private static final Logger logger = LoggerFactory.getLogger(ListTermsAgreemenTest.class);

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
	 * Store 약관동의목록 조회 정상
	 * </pre>
	 */
	@Test
	public void a_listTermsAgreement() {
		new TestCaseTemplate(this.mockMvc).url("/member/user/listTermsAgreement/v1?userKey=US201401241840125650000649").httpMethod(HttpMethod.GET)
				.success(ListTermsAgreementSacRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						ListTermsAgreementSacRes res = (ListTermsAgreementSacRes) result;
						assertThat(res.getUserKey(), notNullValue());
						logger.info("ListTermsAgreementSacRes param : {}", res.toString());
						logger.info("{}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * Store 약관동의목록 조회 Parameter 없음
	 * </pre>
	 */
	@Test(expected = StorePlatformException.class)
	public void b_listTermsAgreement() {
		new TestCaseTemplate(this.mockMvc).url("/member/user/listTermsAgreement/v1?userKey=").httpMethod(HttpMethod.GET)
				.success(ListTermsAgreementSacRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						ListTermsAgreementSacRes res = (ListTermsAgreementSacRes) result;
						assertThat(res.getUserKey(), notNullValue());
						logger.info("ListTermsAgreementSacRes param : {}", res.toString());
						logger.info("{}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * Store 약관동의목록 조회 Parameter 없음
	 * </pre>
	 */
	@Test(expected = StorePlatformException.class)
	public void c_listTermsAgreement() {
		new TestCaseTemplate(this.mockMvc).url("/member/user/listTermsAgreement/v1?userKey=US20140124184012565000064911").httpMethod(HttpMethod.GET)
				.success(ListTermsAgreementSacRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						ListTermsAgreementSacRes res = (ListTermsAgreementSacRes) result;
						assertThat(res.getUserKey(), notNullValue());
						logger.info("ListTermsAgreementSacRes param : {}", res.toString());
						logger.info("{}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

}
