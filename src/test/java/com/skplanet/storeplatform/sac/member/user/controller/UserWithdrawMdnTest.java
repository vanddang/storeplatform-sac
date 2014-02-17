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

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.test.RequestBodySetter;
import com.skplanet.storeplatform.framework.test.SuccessCallback;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate.RunMode;
import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.client.member.vo.common.AgreementInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceExtraInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByMdnReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByMdnRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.WithdrawReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.WithdrawRes;

@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserWithdrawMdnTest {
	private static final Logger logger = LoggerFactory.getLogger(UserWithdrawMdnTest.class);

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
	 * MDN 회원 가입 테스트.
	 * </pre>
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test
	public void a_createByMdn() throws Exception {

		new TestCaseTemplate(this.mockMvc)
				.url("/member/user/createByMdn/v1")
				.httpMethod(HttpMethod.POST)
				.addHeaders("x-store-auth-info", "authKey=2af99ed5f1caa3f02ea7e12e6ba9861f4e7612ea;ist=N")
				.addHeaders("Accept", "application/json")
				.addHeaders("x-planet-device-info",
						"model=\"SHW-M190S\",fwVersion=\"2.1.3_20101005f\",pkgVersion=\"com.skplanet.tstore.mobile/38\",rootDetection=\"no\"")
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {

						CreateByMdnReq reqJson = new CreateByMdnReq();

						// 단말 정보
						reqJson.setDeviceId("01032641287"); // 기기 ID
						reqJson.setDeviceIdType("msisdn"); // 기기 ID 타입
						reqJson.setDeviceTelecom("US001202"); // 통신사
						reqJson.setNativeId("A0000031648EE9"); // 기기 고유 ID (IMEI)
						reqJson.setDeviceAccount("mdntest@gmail.com"); // 기기 계정 (Gmail)
						reqJson.setJoinId("US002903"); // 가입채널코드
						reqJson.setIsRecvSms("Y"); // SMS 수신 여부
						reqJson.setOwnBirth("20020328"); // 본인의 생년월일

						// 단말 부가 정보 리스트
						List<DeviceExtraInfo> deviceExtraList = new ArrayList<DeviceExtraInfo>();
						DeviceExtraInfo deviceExtraInfo = new DeviceExtraInfo();
						deviceExtraInfo.setExtraProfile("US011407");
						deviceExtraInfo.setExtraProfileValue("3.0");

						deviceExtraList.add(deviceExtraInfo);
						reqJson.setDeviceExtraInfoList(deviceExtraList);

						// 법정 대리인 정보 (isParent 값이 Y 일경우 등록 된다.)
						reqJson.setIsParent("Y"); // 법정대리인정보 등록 여부.
						reqJson.setParentRealNameMethod("US011101");
						reqJson.setParentName("홍길동");
						reqJson.setParentType("F");
						reqJson.setParentDate(DateUtil.getToday());
						reqJson.setParentEmail("hkd@aaaa.com");
						reqJson.setParentBirthDay("19700331");
						reqJson.setParentTelecom("US001202");
						reqJson.setParentPhone("01088889999");
						reqJson.setParentCi("skpone0000132653GWyh3WsEm0FutitO5oSgC2/SgSrLKv5XohA8mxTNLitpB1 B9A3z5zrVHettHzKa5dpJA==");
						reqJson.setParentRealNameDate(DateUtil.getToday());
						reqJson.setParentRealNameSite("US011203"); // shop client 3.0

						// 동의 정보
						List<AgreementInfo> agreementList = new ArrayList<AgreementInfo>();
						AgreementInfo agreement1 = new AgreementInfo();
						agreement1.setExtraAgreementId("US010607");
						agreement1.setExtraAgreementVersion("0.1");
						agreement1.setIsExtraAgreement("Y");
						AgreementInfo agreement2 = new AgreementInfo();
						agreement2.setExtraAgreementId("US010608");
						agreement2.setExtraAgreementVersion("0.1");
						agreement2.setIsExtraAgreement("Y");
						AgreementInfo agreement3 = new AgreementInfo();
						agreement3.setExtraAgreementId("US010609");
						agreement3.setExtraAgreementVersion("0.1");
						agreement3.setIsExtraAgreement("Y");

						agreementList.add(agreement1);
						agreementList.add(agreement2);
						agreementList.add(agreement3);
						reqJson.setAgreementList(agreementList);

						return reqJson;
					}
				}).success(CreateByMdnRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						CreateByMdnRes res = (CreateByMdnRes) result;
						assertThat(res.getUserKey(), notNullValue());
						logger.info(res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 회원탈퇴 : MDN/UUID 만 가능
	 * </pre>
	 */
	@Test
	public void b_userWithdrawMdn() {

		new TestCaseTemplate(this.mockMvc).url("/member/user/withdraw/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
			@Override
			public Object requestBody() {
				WithdrawReq req = new WithdrawReq();
				req.setDeviceId("01032641287");
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
	 * deviceId is null
	 */
	@Test(expected = StorePlatformException.class)
	public void c_userWithdrawError1() {

		new TestCaseTemplate(this.mockMvc).url("/member/user/withdraw/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
			@Override
			public Object requestBody() {
				WithdrawReq req = new WithdrawReq();
				req.setDeviceId("");
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
	 * deviceId is inValid
	 */
	@Test(expected = StorePlatformException.class)
	public void d_userWithdrawError5() {

		new TestCaseTemplate(this.mockMvc).url("/member/user/withdraw/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
			@Override
			public Object requestBody() {
				WithdrawReq req = new WithdrawReq();
				req.setDeviceId("0101234648011");
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
	 * inValid UUID
	 */
	@Test(expected = StorePlatformException.class)
	public void e_userWithdrawError7() {

		new TestCaseTemplate(this.mockMvc).url("/member/user/withdraw/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
			@Override
			public Object requestBody() {
				WithdrawReq req = new WithdrawReq();
				req.setDeviceId("E621E1F8-C36C-495A-93FC-0C247A3E6E5F");
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
