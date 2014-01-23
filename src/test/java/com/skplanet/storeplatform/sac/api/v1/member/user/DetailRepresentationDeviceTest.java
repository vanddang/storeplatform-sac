package com.skplanet.storeplatform.sac.api.v1.member.user;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

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
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailRepresentationDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailRepresentationDeviceRes;

/**
 * Calss 설명
 * 
 * Updated on : 2014. 1. 16. Updated by : 강신완, 부르칸.
 */
@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class DetailRepresentationDeviceTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(DetailRepresentationDeviceTest.class);

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
	 * 대표단말 정보조회.
	 * </pre>
	 */
	@Test
	public void detailRepresentationDevice() throws Exception {

		new TestCaseTemplate(this.mockMvc).url("/member/user/detailRepresentationDevice/v1")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						DetailRepresentationDeviceReq req = new DetailRepresentationDeviceReq();
						req.setUserId("tstore44");
						req.setUserKey("IF1023002708420090928145937");
						LOGGER.debug("request param : {}", req.toString());
						return req;
					}
				}).success(DetailRepresentationDeviceRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						DetailRepresentationDeviceRes res = (DetailRepresentationDeviceRes) result;
						assertThat(res.getUserDeviceInfo().getDeviceKey(), notNullValue());
						LOGGER.debug("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * userId && userKey is Null
	 */
	@Test(expected = RuntimeException.class)
	public void detailRepresentationDeviceError1() throws Exception {

		new TestCaseTemplate(this.mockMvc).url("/member/user/detailRepresentationDevice/v1")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						DetailRepresentationDeviceReq req = new DetailRepresentationDeviceReq();
						req.setUserId("");
						req.setUserKey("");
						LOGGER.debug("request param : {}", req.toString());
						return req;
					}
				}).success(DetailRepresentationDeviceRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						DetailRepresentationDeviceRes res = (DetailRepresentationDeviceRes) result;
						assertThat(res.getUserDeviceInfo().getDeviceKey(), notNullValue());
						LOGGER.debug("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * userId is Null
	 */
	@Test(expected = RuntimeException.class)
	public void detailRepresentationDeviceError2() throws Exception {

		new TestCaseTemplate(this.mockMvc).url("/member/user/detailRepresentationDevice/v1")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						DetailRepresentationDeviceReq req = new DetailRepresentationDeviceReq();
						req.setUserId("");
						LOGGER.debug("request param : {}", req.toString());
						return req;
					}
				}).success(DetailRepresentationDeviceRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						DetailRepresentationDeviceRes res = (DetailRepresentationDeviceRes) result;
						assertThat(res.getUserDeviceInfo().getDeviceKey(), notNullValue());
						LOGGER.debug("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * userKey is Null
	 */
	@Test(expected = RuntimeException.class)
	public void detailRepresentationDeviceError3() throws Exception {

		new TestCaseTemplate(this.mockMvc).url("/member/user/detailRepresentationDevice/v1")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						DetailRepresentationDeviceReq req = new DetailRepresentationDeviceReq();
						req.setUserKey("");
						LOGGER.debug("request param : {}", req.toString());
						return req;
					}
				}).success(DetailRepresentationDeviceRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						DetailRepresentationDeviceRes res = (DetailRepresentationDeviceRes) result;
						assertThat(res.getUserDeviceInfo().getDeviceKey(), notNullValue());
						LOGGER.debug("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * userKey is inValid
	 */
	@Test(expected = RuntimeException.class)
	public void detailRepresentationDeviceError4() throws Exception {

		new TestCaseTemplate(this.mockMvc).url("/member/user/detailRepresentationDevice/v1")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						DetailRepresentationDeviceReq req = new DetailRepresentationDeviceReq();
						req.setUserKey("IF102300270842009092814593711");
						LOGGER.debug("request param : {}", req.toString());
						return req;
					}
				}).success(DetailRepresentationDeviceRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						DetailRepresentationDeviceRes res = (DetailRepresentationDeviceRes) result;
						assertThat(res.getUserDeviceInfo().getDeviceKey(), notNullValue());
						LOGGER.debug("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * userId is inValid
	 */
	@Test(expected = RuntimeException.class)
	public void detailRepresentationDeviceError5() throws Exception {

		new TestCaseTemplate(this.mockMvc).url("/member/user/detailRepresentationDevice/v1")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						DetailRepresentationDeviceReq req = new DetailRepresentationDeviceReq();
						req.setUserId("tstore4444");
						LOGGER.debug("request param : {}", req.toString());
						return req;
					}
				}).success(DetailRepresentationDeviceRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						DetailRepresentationDeviceRes res = (DetailRepresentationDeviceRes) result;
						assertThat(res.getUserDeviceInfo().getDeviceKey(), notNullValue());
						LOGGER.debug("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}
}
