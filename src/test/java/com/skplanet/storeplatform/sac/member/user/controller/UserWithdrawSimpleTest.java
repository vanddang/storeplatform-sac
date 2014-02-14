package com.skplanet.storeplatform.sac.member.user.controller;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

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

import com.skplanet.storeplatform.framework.test.RequestBodySetter;
import com.skplanet.storeplatform.framework.test.SuccessCallback;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate.RunMode;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceExtraInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByAgreementRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateBySimpleReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.WithdrawReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.WithdrawRes;

@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserWithdrawSimpleTest {
	private static final Logger logger = LoggerFactory.getLogger(UserWithdrawSimpleTest.class);

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

	@Test
	public void acreateBySimpleDevice() throws Exception {

		new TestCaseTemplate(this.mockMvc)
				.url("/member/user/createBySimple/v1")
				.httpMethod(HttpMethod.POST)
				.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.addHeaders("Accept", "application/json")
				.addHeaders("x-planet-device-info",
						"model=\"SHW-M190S\",fwVersion=\"2.1.3_20101005f\",pkgVersion=\"com.skplanet.tstore.mobile/38\",rootDetection=\"no\"")
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {

						CreateBySimpleReq reqJson = new CreateBySimpleReq();

						// 사용자 아이디
						reqJson.setUserId("sacusertest1"); // 대문자 ID는 가입 불가 IDP 정책.
						reqJson.setUserPw("abcd1234");
						reqJson.setUserEmail("sacusertest1@yahoo.co.kr");

						// 단말 정보
						reqJson.setDeviceId("0101234567"); // 기기 ID
						reqJson.setDeviceIdType("msisdn"); // 기기 ID 타입
						reqJson.setDeviceTelecom("US012102"); // 통신사
						reqJson.setNativeId("A0000031648EE9"); // 기기 고유 ID (IMEI)
						reqJson.setDeviceAccount("sacuser01@yopmail.com"); // 기기 계정 (Gmail)
						reqJson.setJoinId("US002903"); // 가입채널코드
						reqJson.setIsRecvSms("Y"); // SMS 수신 여부

						// 단말 부가 정보 리스트
						List<DeviceExtraInfo> deviceExtraList = new ArrayList<DeviceExtraInfo>();
						DeviceExtraInfo deviceExtraInfo = new DeviceExtraInfo();
						deviceExtraInfo.setExtraProfile("US011407");
						deviceExtraInfo.setExtraProfileValue("3.0");

						deviceExtraList.add(deviceExtraInfo);
						reqJson.setDeviceExtraInfoList(deviceExtraList);

						return reqJson;
					}
				}).success(CreateByAgreementRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						CreateByAgreementRes res = (CreateByAgreementRes) result;
						assertThat(res.getUserKey(), notNullValue());
						System.out.println(res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 회원탈퇴 : ID
	 * </pre>
	 */
	@Test
	public void buserWithdrawSimple() {

		new TestCaseTemplate(this.mockMvc).url("/member/user/withdraw/v1").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						WithdrawReq req = new WithdrawReq();
						req.setUserId("sacusertest1");
						req.setUserAuthKey("114127c7ef42667669819dad5df8d820c");
						logger.debug("request param : {}", req.toString());
						return req;
					}
				}).success(WithdrawRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						WithdrawRes res = (WithdrawRes) result;
						assertThat(res.getUserKey(), notNullValue());
						logger.debug("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * userId && userAuthKey - both NULL
	 */
	@Test(expected = RuntimeException.class)
	public void cuserWithdrawError1() {

		new TestCaseTemplate(this.mockMvc).url("/member/user/withdraw/v1").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						WithdrawReq req = new WithdrawReq();
						req.setUserId("");
						req.setUserAuthKey("");
						logger.debug("request param : {}", req.toString());
						return req;
					}
				}).success(WithdrawRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						WithdrawRes res = (WithdrawRes) result;
						assertThat(res.getUserKey(), notNullValue());
						logger.debug("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * userId && userAuthKey - usrId is NULL
	 */
	@Test(expected = RuntimeException.class)
	public void cuserWithdrawError2() {

		new TestCaseTemplate(this.mockMvc).url("/member/user/withdraw/v1").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						WithdrawReq req = new WithdrawReq();
						req.setUserId("");
						req.setUserAuthKey("114127c7ef42667669819dad5df8d820c");
						logger.debug("request param : {}", req.toString());
						return req;
					}
				}).success(WithdrawRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						WithdrawRes res = (WithdrawRes) result;
						assertThat(res.getUserKey(), notNullValue());
						logger.debug("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * userId && userAuthKey - userAuthKey is Null
	 */
	@Test(expected = RuntimeException.class)
	public void cuserWithdrawError3() {

		new TestCaseTemplate(this.mockMvc).url("/member/user/withdraw/v1").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						WithdrawReq req = new WithdrawReq();
						req.setUserId("tstore44");
						req.setUserAuthKey("");
						logger.debug("request param : {}", req.toString());
						return req;
					}
				}).success(WithdrawRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						WithdrawRes res = (WithdrawRes) result;
						assertThat(res.getUserKey(), notNullValue());
						logger.debug("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * userId && userAuthKey is inValid
	 */
	@Test(expected = RuntimeException.class)
	public void cuserWithdrawError4() {

		new TestCaseTemplate(this.mockMvc).url("/member/user/withdraw/v1").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						WithdrawReq req = new WithdrawReq();
						req.setUserId("tstore44");
						req.setUserAuthKey("114127a7ff42667669819dad5df8d820c");
						logger.debug("request param : {}", req.toString());
						return req;
					}
				}).success(WithdrawRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						WithdrawRes res = (WithdrawRes) result;
						assertThat(res.getUserKey(), notNullValue());
						logger.debug("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

}
