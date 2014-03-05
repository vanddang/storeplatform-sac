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
import com.skplanet.storeplatform.framework.test.RequestBodySetter;
import com.skplanet.storeplatform.framework.test.SuccessCallback;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate.RunMode;
import com.skplanet.storeplatform.sac.client.member.vo.user.SetMainDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.SetMainDeviceRes;

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
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ModifyRepresentationDeviceTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ModifyRepresentationDeviceTest.class);

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
	 * 대표단말설정.
	 * </pre>
	 */
	@Test
	public void A_TEST_정상_대표단말설정_디바이스아이디() {
		new TestCaseTemplate(this.mockMvc).url("/member/user/modifyRepresentationDevice/v1").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						SetMainDeviceReq req = new SetMainDeviceReq();
						req.setUserKey("US201401271926064310001061");
						req.setDeviceId("01012345646");
						LOGGER.debug("request param : {}", req.toString());
						return req;
					}
				}).success(SetMainDeviceRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						SetMainDeviceRes res = (SetMainDeviceRes) result;
						assertThat(res.getDeviceKey(), notNullValue());
						LOGGER.debug("response param : {}", res.getDeviceKey());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 대표단말설정.
	 * </pre>
	 */
	@Test
	public void B_TEST_정상_대표단말설정_디바이스키() {
		new TestCaseTemplate(this.mockMvc).url("/member/user/modifyRepresentationDevice/v1").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						SetMainDeviceReq req = new SetMainDeviceReq();
						req.setUserKey("US201401271926064310001061");
						req.setDeviceKey("DE201402120522137350001556");
						LOGGER.debug("request param : {}", req.toString());
						return req;
					}
				}).success(SetMainDeviceRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						SetMainDeviceRes res = (SetMainDeviceRes) result;
						assertThat(res.getDeviceKey(), notNullValue());
						LOGGER.debug("response param : {}", res.getDeviceKey());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * 파라미터 없음
	 */
	@Test(expected = StorePlatformException.class)
	public void C_TEST_오류_파라미터없음() {
		new TestCaseTemplate(this.mockMvc).url("/member/user/modifyRepresentationDevice/v1").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						SetMainDeviceReq req = new SetMainDeviceReq();
						req.setUserKey("");
						req.setDeviceKey("");
						LOGGER.debug("request param : {}", req.toString());
						return req;
					}
				}).success(SetMainDeviceRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						SetMainDeviceRes res = (SetMainDeviceRes) result;
						assertThat(res.getDeviceKey(), notNullValue());
						LOGGER.debug("response param : {}", res.getDeviceKey());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * 유효하지 않은 디바이스 키
	 */
	@Test(expected = StorePlatformException.class)
	public void D_TEST_오류_유효하지않은디바이스키() {
		new TestCaseTemplate(this.mockMvc).url("/member/user/modifyRepresentationDevice/v1").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						SetMainDeviceReq req = new SetMainDeviceReq();
						req.setUserKey("US201401271926064310001061");
						req.setDeviceKey("11DE201401271926064620000520");
						LOGGER.debug("request param : {}", req.toString());
						return req;
					}
				}).success(SetMainDeviceRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						SetMainDeviceRes res = (SetMainDeviceRes) result;
						assertThat(res.getDeviceKey(), notNullValue());
						LOGGER.debug("response param : {}", res.getDeviceKey());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * 유효하지 않은 사용자 키
	 */
	@Test(expected = StorePlatformException.class)
	public void E_TEST_오류_유효하지않은사용자키() {
		new TestCaseTemplate(this.mockMvc).url("/member/user/modifyRepresentationDevice/v1").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						SetMainDeviceReq req = new SetMainDeviceReq();
						req.setUserKey("11US201401271926064310001061");
						req.setDeviceKey("DE201401271926064620000520");
						LOGGER.debug("request param : {}", req.toString());
						return req;
					}
				}).success(SetMainDeviceRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						SetMainDeviceRes res = (SetMainDeviceRes) result;
						assertThat(res.getDeviceKey(), notNullValue());
						LOGGER.debug("response param : {}", res.getDeviceKey());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}
}
