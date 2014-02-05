package com.skplanet.storeplatform.sac.api.v1.member.user;

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
import com.skplanet.storeplatform.sac.client.member.vo.user.SupportAomRes;

@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GetSupportAomTest {
	private static final Logger logger = LoggerFactory.getLogger(GetSupportAomTest.class);

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
	 * 단말 AOM 확인
	 * </pre>
	 */
	@Test
	public void getSupportAom() {
		new TestCaseTemplate(this.mockMvc).url("/member/user/getSupportAom/v1?userKey=US201401241840125650000649&deviceId=01001232112")
				.httpMethod(HttpMethod.GET).success(SupportAomRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						SupportAomRes res = (SupportAomRes) result;
						assertThat(res.getIsAomSupport(), notNullValue());
						logger.info("단말 AOM 확인 response param : {}", res.toString());
						logger.info("{}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 단말 AOM 확인 - UserKey Null
	 * </pre>
	 */
	@Test(expected = StorePlatformException.class)
	public void getSupportAomExceptionUserKey() {
		new TestCaseTemplate(this.mockMvc).url("/member/user/getSupportAom/v1?userKey=&deviceId=01001232112").httpMethod(HttpMethod.GET)
				.success(SupportAomRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						SupportAomRes res = (SupportAomRes) result;
						assertThat(res.getIsAomSupport(), notNullValue());
						logger.info("단말 AOM 확인 response param : {}", res.toString());
						logger.info("{}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 단말 AOM 확인 - DeviceId Null
	 * </pre>
	 */
	@Test(expected = StorePlatformException.class)
	public void getSupportAomExceptionDeviceId() {
		new TestCaseTemplate(this.mockMvc).url("/member/user/getSupportAom/v1?userKey=US201401241840125650000649&deviceId=")
				.httpMethod(HttpMethod.GET).success(SupportAomRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						SupportAomRes res = (SupportAomRes) result;
						assertThat(res.getIsAomSupport(), notNullValue());
						logger.info("단말 AOM 확인 response param : {}", res.toString());
						logger.info("{}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

}
