package com.skplanet.storeplatform.sac.api.v1.member.seller;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.After;
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

import com.skplanet.storeplatform.framework.test.RequestBodySetter;
import com.skplanet.storeplatform.framework.test.SuccessCallback;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate.RunMode;
import com.skplanet.storeplatform.sac.api.v1.member.ConvertMapperUtils;
import com.skplanet.storeplatform.sac.api.v1.member.constant.MemberTestConstant;
import com.skplanet.storeplatform.sac.client.member.vo.seller.AuthorizeReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.AuthorizeRes;

/**
 * 2.2.3. 판매자 회원 인증
 * 
 * Updated on : 2014. 1. 14. Updated by : 김경복, 부르칸.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class AuthorizeTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizeTest.class);

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	/** [REQUEST]. */
	public static AuthorizeReq authorizeReq;

	/** [RESPONSE]. */
	public static AuthorizeRes authorizeRes;

	/**
	 * 
	 * <pre>
	 * TestCase 수행 전 실행 Method.
	 * 인증 요청 [REQUEST] 객체 생성
	 * </pre>
	 */
	@Before
	public void before() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		// [REQUEST] 초기화
		authorizeReq = new AuthorizeReq();
	}

	/**
	 * <pre>
	 * TestCase 수행 후 실행 Method.
	 * 인증 요청 결과 LOGGER 실행
	 * </pre>
	 */
	@After
	public void after() {
		// Debug
		LOGGER.debug("[RESPONSE(SAC)-회원인증] : \n{}", ConvertMapperUtils.convertObjectToJson(authorizeRes));
	}

	/**
	 * <pre>
	 * 회원 인증(주계정).
	 * </pre>
	 */
	@Test
	public void authorize() {

		new TestCaseTemplate(this.mockMvc).url(MemberTestConstant.PREFIX_SELLER_PATH + "/authorize/v1")
				.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						authorizeReq.setSellerId("sctest1");
						authorizeReq.setSellerPW("123456");
						authorizeReq.setExpireDate("1");
						return authorizeReq;
					}
				}).success(AuthorizeRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						authorizeRes = (AuthorizeRes) result;
						assertThat(authorizeRes.getSellerMbr(), notNullValue());
						assertEquals("Y", authorizeRes.getIsLoginSuccess());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 회원 인증(부계정).
	 * </pre>
	 */
	@Test
	public void authorizeSub() {

		new TestCaseTemplate(this.mockMvc).url(MemberTestConstant.PREFIX_SELLER_PATH + "/authorize/v1")
				.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						authorizeReq.setSellerId("subsctest1");
						authorizeReq.setSellerPW("123456");
						authorizeReq.setExpireDate("1");
						return authorizeReq;
					}
				}).success(AuthorizeRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						authorizeRes = (AuthorizeRes) result;
						assertThat(authorizeRes.getSellerMbr(), notNullValue());
						assertEquals("Y", authorizeRes.getIsLoginSuccess());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * ID 정보가 없을경우.
	 * </pre>
	 */
	@Test
	public void authorizeNonId() {

		new TestCaseTemplate(this.mockMvc).url(MemberTestConstant.PREFIX_SELLER_PATH + "/authorize/v1")
				.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						authorizeReq.setSellerId("qatestqwe123");
						authorizeReq.setSellerPW("LmmYUGEXDpT/HvybdNVG7nfq8KwfR5EFtcrXCne9LVs=");
						LOGGER.debug("request param : {}", authorizeReq.toString());
						return authorizeReq;
					}
				}).success(AuthorizeRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						authorizeRes = (AuthorizeRes) result;
						assertThat(authorizeRes.getSellerMbr(), notNullValue());
						LOGGER.debug("response param : {}", authorizeRes.getSellerMbr().toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * ID는 있고, PW이 다를경우.
	 * </pre>
	 */
	@Test
	public void authorizeNonPw() {

		new TestCaseTemplate(this.mockMvc).url(MemberTestConstant.PREFIX_SELLER_PATH + "/authorize/v1")
				.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {

						authorizeReq.setSellerId("qatestqwe");
						authorizeReq.setSellerPW("LmmYUGEXDpT/HvybdNVG7nfq8KwfR5EFtcrXCne9LVs=12313123");
						LOGGER.debug("request param : {}", authorizeReq.toString());
						return authorizeReq;
					}
				}).success(AuthorizeRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						authorizeRes = (AuthorizeRes) result;
						assertThat(authorizeRes.getSellerMbr(), notNullValue());
						LOGGER.debug("response param : {}", authorizeRes.getSellerMbr().toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * ID / PW 둘다 다를경우.
	 * </pre>
	 */
	@Test
	public void authorizeNonIdPw() {

		new TestCaseTemplate(this.mockMvc).url(MemberTestConstant.PREFIX_SELLER_PATH + "/authorize/v1")
				.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {

						authorizeReq.setSellerId("qatestqwe123123123");
						authorizeReq.setSellerPW("LmmYUGEXDpT/HvybdNVG7nfq8KwfR5EFtcrXCne9LVs=123123");
						LOGGER.debug("request param : {}", authorizeReq.toString());
						return authorizeReq;
					}
				}).success(AuthorizeRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						authorizeRes = (AuthorizeRes) result;
						assertThat(authorizeRes.getSellerMbr(), notNullValue());
						LOGGER.debug("response param : {}", authorizeRes.getSellerMbr().toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 정상/전환신청.
	 * </pre>
	 */
	@Test
	public void authorizeTurnMotion() {

		new TestCaseTemplate(this.mockMvc).url(MemberTestConstant.PREFIX_SELLER_PATH + "/authorize/v1")
				.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {

						authorizeReq.setSellerId("devadmin");
						authorizeReq.setSellerPW("kGaBfCSoIkTKHrCe/PqPpIbZj1Qiysfh3JavZblwztc=");
						LOGGER.debug("request param : {}", authorizeReq.toString());
						return authorizeReq;
					}
				}).success(AuthorizeRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						authorizeRes = (AuthorizeRes) result;
						assertThat(authorizeRes.getSellerMbr(), notNullValue());
						LOGGER.debug("response param : {}", authorizeRes.getSellerMbr().toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 정상/전환재신청.
	 * </pre>
	 */
	@Test
	public void authorizeSevenMotionAgain() {

		new TestCaseTemplate(this.mockMvc).url(MemberTestConstant.PREFIX_SELLER_PATH + "/authorize/v1")
				.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {

						authorizeReq.setSellerId("testsign");
						authorizeReq.setSellerPW("6uYY9nPngiCgDEkMKWL6znlgclEKMkrSQnZNY/irBtg=");
						LOGGER.debug("request param : {}", authorizeReq.toString());
						return authorizeReq;
					}
				}).success(AuthorizeRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						authorizeRes = (AuthorizeRes) result;
						assertThat(authorizeRes.getSellerMbr(), notNullValue());
						LOGGER.debug("response param : {}", authorizeRes.getSellerMbr().toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 정상/전환거절.
	 * </pre>
	 */
	@Test
	public void authorizeTurnReject() {

		new TestCaseTemplate(this.mockMvc).url(MemberTestConstant.PREFIX_SELLER_PATH + "/authorize/v1")
				.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {

						authorizeReq.setSellerId("stg01");
						authorizeReq.setSellerPW("TrmVG3NjqCtRJK0KmzCNuqHYNG/W5KrCoQ3IyvRUVaU=");
						LOGGER.debug("request param : {}", authorizeReq.toString());
						return authorizeReq;
					}
				}).success(AuthorizeRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						authorizeRes = (AuthorizeRes) result;
						assertThat(authorizeRes.getSellerMbr(), notNullValue());
						LOGGER.debug("response param : {}", authorizeRes.getSellerMbr().toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 탈퇴/탈퇴신청.
	 * </pre>
	 */
	@Test
	public void authorizeSecedeMotion() {

		new TestCaseTemplate(this.mockMvc).url(MemberTestConstant.PREFIX_SELLER_PATH + "/authorize/v1")
				.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {

						authorizeReq.setSellerId("signtest");
						authorizeReq.setSellerPW("6uYY9nPngiCgDEkMKWL6znlgclEKMkrSQnZNY/irBtg=");
						LOGGER.debug("request param : {}", authorizeReq.toString());
						return authorizeReq;
					}
				}).success(AuthorizeRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						authorizeRes = (AuthorizeRes) result;
						assertThat(authorizeRes.getSellerMbr(), notNullValue());
						LOGGER.debug("response param : {}", authorizeRes.getSellerMbr().toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 탈퇴/탈퇴완료.
	 * </pre>
	 */
	@Test
	public void authorizeSecedeFinish() {

		new TestCaseTemplate(this.mockMvc).url(MemberTestConstant.PREFIX_SELLER_PATH + "/authorize/v1")
				.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {

						authorizeReq.setSellerId("su004");
						authorizeReq.setSellerPW("cS1DhNOYV5JbVO+uLyOk9zltEQEOJFwc9/srL8z02dA=");
						LOGGER.debug("request param : {}", authorizeReq.toString());
						return authorizeReq;
					}
				}).success(AuthorizeRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						authorizeRes = (AuthorizeRes) result;
						assertThat(authorizeRes.getSellerMbr(), notNullValue());
						LOGGER.debug("response param : {}", authorizeRes.getSellerMbr().toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 가가입/가입승인만료.
	 * </pre>
	 */
	@Test
	public void authorizeJoinApplyEtc() {

		new TestCaseTemplate(this.mockMvc).url(MemberTestConstant.PREFIX_SELLER_PATH + "/authorize/v1")
				.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {

						authorizeReq.setSellerId("tbjh");
						authorizeReq.setSellerPW("gGYxFfhiyyUZF/IBX3HdXSQn7YR/oCsKYvuhQ2j1gD8=");
						LOGGER.debug("request param : {}", authorizeReq.toString());
						return authorizeReq;
					}
				}).success(AuthorizeRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						authorizeRes = (AuthorizeRes) result;
						assertThat(authorizeRes.getSellerMbr(), notNullValue());
						LOGGER.debug("response param : {}", authorizeRes.getSellerMbr().toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 가가입/가입승인대기.
	 * </pre>
	 */
	@Test
	public void authorizeJoinApplyWating() {

		new TestCaseTemplate(this.mockMvc).url(MemberTestConstant.PREFIX_SELLER_PATH + "/authorize/v1")
				.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {

						authorizeReq.setSellerId("qatest105");
						authorizeReq.setSellerPW("cS1DhNOYV5JbVO+uLyOk9zltEQEOJFwc9/srL8z02dA=");
						LOGGER.debug("request param : {}", authorizeReq.toString());
						return authorizeReq;
					}
				}).success(AuthorizeRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						authorizeRes = (AuthorizeRes) result;
						assertThat(authorizeRes.getSellerMbr(), notNullValue());
						LOGGER.debug("response param : {}", authorizeRes.getSellerMbr().toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 가가입/이메일변경승인대기.
	 * </pre>
	 */
	@Test
	public void authorizeEmailCertWating() {

		new TestCaseTemplate(this.mockMvc).url(MemberTestConstant.PREFIX_SELLER_PATH + "/authorize/v1")
				.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {

						authorizeReq.setSellerId("qatest019");
						authorizeReq.setSellerPW("cS1DhNOYV5JbVO+uLyOk9zltEQEOJFwc9/srL8z02dA=");
						LOGGER.debug("request param : {}", authorizeReq.toString());
						return authorizeReq;
					}
				}).success(AuthorizeRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						authorizeRes = (AuthorizeRes) result;
						assertThat(authorizeRes.getSellerMbr(), notNullValue());
						LOGGER.debug("response param : {}", authorizeRes.getSellerMbr().toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 일시정지/계정잠금.
	 * </pre>
	 */
	@Test
	public void authorizeLoginPause() {

		new TestCaseTemplate(this.mockMvc).url(MemberTestConstant.PREFIX_SELLER_PATH + "/authorize/v1")
				.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {

						authorizeReq.setSellerId("wqatest1");
						authorizeReq.setSellerPW("cS1DhNOYV5JbVO+uLyOk9zltEQEOJFwc9/srL8z02dA=");
						LOGGER.debug("request param : {}", authorizeReq.toString());
						return authorizeReq;
					}
				}).success(AuthorizeRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						authorizeRes = (AuthorizeRes) result;
						assertThat(authorizeRes.getSellerMbr(), notNullValue());
						LOGGER.debug("response param : {}", authorizeRes.getSellerMbr().toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}
}
