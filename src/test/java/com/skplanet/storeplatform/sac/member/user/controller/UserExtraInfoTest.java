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
import com.skplanet.storeplatform.sac.client.member.vo.common.UserExtraInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.UserExtraInfoReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.UserExtraInfoRes;

@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserExtraInfoTest {
	private static final Logger logger = LoggerFactory.getLogger(UserExtraInfoTest.class);

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
	 * 회원 부가정보조회 Parameter : userKey
	 * </pre>
	 */
	@Test
	public void a_userExtraInfo() {
		new TestCaseTemplate(this.mockMvc).url("/member/user/listAdditionalInformation/v1?userKey=US201401221721168550000323")
				.httpMethod(HttpMethod.GET).success(UserExtraInfoRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						UserExtraInfoRes res = (UserExtraInfoRes) result;
						assertThat(res.getUserKey(), notNullValue());
						logger.info("UserExtraInfoRes response param : {}", res.toString());
						logger.info("{}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 회원 부가정보 등록/수정 테스트
	 * </pre>
	 * 
	 */
	@Test
	public void b_updateUserExtraInfo() {

		new TestCaseTemplate(this.mockMvc).url("/member/user/modifyAdditionalInformation/v1").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						UserExtraInfoReq req = new UserExtraInfoReq();
						req.setUserKey("US201401221721168550000323");

						// 회원 부가 정보 리스트
						List<UserExtraInfo> userExtraList = new ArrayList<UserExtraInfo>();

						UserExtraInfo user1 = new UserExtraInfo();
						user1.setExtraProfile("US010905");
						user1.setExtraProfileValue("111");

						UserExtraInfo user2 = new UserExtraInfo();
						user2.setExtraProfile("US010906");
						user2.setExtraProfileValue("222");

						userExtraList.add(user1);
						userExtraList.add(user2);

						req.setUserExtraInfoList(userExtraList);

						logger.debug("request param : {}", req.toString());
						return req;
					}
				}).success(UserExtraInfoRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						UserExtraInfoRes res = (UserExtraInfoRes) result;
						assertThat(res.getUserKey(), notNullValue());
						logger.debug("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 회원 부가정보 삭제 테스트
	 * </pre>
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test
	public void c_removeUserExtraInfo() {

		new TestCaseTemplate(this.mockMvc).url("/member/user/removeAdditionalInformation/v1").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						UserExtraInfoReq req = new UserExtraInfoReq();
						req.setUserKey("US201401221721168550000323");

						// 회원 부가 정보 삭제 리스트
						List<UserExtraInfo> userExtraList = new ArrayList<UserExtraInfo>();

						UserExtraInfo user1 = new UserExtraInfo();
						user1.setExtraProfile("US010905");

						UserExtraInfo user2 = new UserExtraInfo();
						user2.setExtraProfile("US010906");

						userExtraList.add(user1);
						userExtraList.add(user2);

						req.setUserExtraInfoList(userExtraList);

						logger.debug("request param : {}", req.toString());
						return req;
					}
				}).success(UserExtraInfoRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						UserExtraInfoRes res = (UserExtraInfoRes) result;
						assertThat(res.getUserKey(), notNullValue());
						logger.debug("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 회원 부가정보조회 Parameter : userKey is Null
	 * </pre>
	 */
	@Test(expected = StorePlatformException.class)
	public void d_userExtraInfoError() {
		new TestCaseTemplate(this.mockMvc).url("/member/user/listAdditionalInformation/v1?userKey=").httpMethod(HttpMethod.GET)
				.success(UserExtraInfoRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						UserExtraInfoRes res = (UserExtraInfoRes) result;
						assertThat(res.getUserKey(), notNullValue());
						logger.info("UserExtraInfoRes response param : {}", res.toString());
						logger.info("{}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 회원 부가정보 등록/수정 테스트 userKey is Null
	 * </pre>
	 * 
	 */
	@Test(expected = StorePlatformException.class)
	public void e_updateUserExtraInfoError() {

		new TestCaseTemplate(this.mockMvc).url("/member/user/modifyAdditionalInformation/v1").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						UserExtraInfoReq req = new UserExtraInfoReq();
						req.setUserKey("");

						// 회원 부가 정보 리스트
						List<UserExtraInfo> userExtraList = new ArrayList<UserExtraInfo>();

						UserExtraInfo user1 = new UserExtraInfo();
						user1.setExtraProfile("US010905");
						user1.setExtraProfileValue("111");

						UserExtraInfo user2 = new UserExtraInfo();
						user2.setExtraProfile("US010906");
						user2.setExtraProfileValue("222");

						userExtraList.add(user1);
						userExtraList.add(user2);

						req.setUserExtraInfoList(userExtraList);

						logger.debug("request param : {}", req.toString());
						return req;
					}
				}).success(UserExtraInfoRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						UserExtraInfoRes res = (UserExtraInfoRes) result;
						assertThat(res.getUserKey(), notNullValue());
						logger.debug("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 회원 부가정보 등록/수정 테스트 userInfoList is Null
	 * </pre>
	 * 
	 */
	@Test(expected = StorePlatformException.class)
	public void f_updateUserExtraInfoError() {

		new TestCaseTemplate(this.mockMvc).url("/member/user/modifyAdditionalInformation/v1").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						UserExtraInfoReq req = new UserExtraInfoReq();
						req.setUserKey("US201401221721168550000323");

						// 회원 부가 정보 리스트
						List<UserExtraInfo> userExtraList = new ArrayList<UserExtraInfo>();

						UserExtraInfo user1 = new UserExtraInfo();
						user1.setExtraProfile("");
						user1.setExtraProfileValue("");

						UserExtraInfo user2 = new UserExtraInfo();
						user2.setExtraProfile("");
						user2.setExtraProfileValue("");

						userExtraList.add(user1);
						userExtraList.add(user2);

						req.setUserExtraInfoList(userExtraList);

						logger.debug("request param : {}", req.toString());
						return req;
					}
				}).success(UserExtraInfoRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						UserExtraInfoRes res = (UserExtraInfoRes) result;
						assertThat(res.getUserKey(), notNullValue());
						logger.debug("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 회원 부가정보 삭제 테스트 userKey is Null
	 * </pre>
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test(expected = StorePlatformException.class)
	public void g_removeUserExtraInfoError() {

		new TestCaseTemplate(this.mockMvc).url("/member/user/removeAdditionalInformation/v1").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						UserExtraInfoReq req = new UserExtraInfoReq();
						req.setUserKey("");

						// 회원 부가 정보 삭제 리스트
						List<UserExtraInfo> userExtraList = new ArrayList<UserExtraInfo>();

						UserExtraInfo user1 = new UserExtraInfo();
						user1.setExtraProfile("US010905");

						UserExtraInfo user2 = new UserExtraInfo();
						user2.setExtraProfile("US010906");

						userExtraList.add(user1);
						userExtraList.add(user2);

						req.setUserExtraInfoList(userExtraList);

						logger.debug("request param : {}", req.toString());
						return req;
					}
				}).success(UserExtraInfoRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						UserExtraInfoRes res = (UserExtraInfoRes) result;
						assertThat(res.getUserKey(), notNullValue());
						logger.debug("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 회원 부가정보 삭제 테스트 userInfoList is Null
	 * </pre>
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test(expected = StorePlatformException.class)
	public void h_removeUserExtraInfoError() {

		new TestCaseTemplate(this.mockMvc).url("/member/user/removeAdditionalInformation/v1").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						UserExtraInfoReq req = new UserExtraInfoReq();
						req.setUserKey("US201401221721168550000323");

						// 회원 부가 정보 삭제 리스트
						List<UserExtraInfo> userExtraList = new ArrayList<UserExtraInfo>();

						UserExtraInfo user1 = new UserExtraInfo();
						user1.setExtraProfile("");

						UserExtraInfo user2 = new UserExtraInfo();
						user2.setExtraProfile("");

						userExtraList.add(user1);
						userExtraList.add(user2);

						req.setUserExtraInfoList(userExtraList);

						logger.debug("request param : {}", req.toString());
						return req;
					}
				}).success(UserExtraInfoRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						UserExtraInfoRes res = (UserExtraInfoRes) result;
						assertThat(res.getUserKey(), notNullValue());
						logger.debug("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 회원 부가정보 등록/수정 테스트 userInfoList is Invalid
	 * </pre>
	 * 
	 */
	@Test(expected = StorePlatformException.class)
	public void i_updateUserExtraInfoError() {

		new TestCaseTemplate(this.mockMvc).url("/member/user/modifyAdditionalInformation/v1").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						UserExtraInfoReq req = new UserExtraInfoReq();
						req.setUserKey("US201401221721168550000323");

						// 회원 부가 정보 리스트
						List<UserExtraInfo> userExtraList = new ArrayList<UserExtraInfo>();

						UserExtraInfo user1 = new UserExtraInfo();
						user1.setExtraProfile("123");
						user1.setExtraProfileValue("123");

						UserExtraInfo user2 = new UserExtraInfo();
						user2.setExtraProfile("123");
						user2.setExtraProfileValue("123");

						userExtraList.add(user1);
						userExtraList.add(user2);

						req.setUserExtraInfoList(userExtraList);

						logger.debug("request param : {}", req.toString());
						return req;
					}
				}).success(UserExtraInfoRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						UserExtraInfoRes res = (UserExtraInfoRes) result;
						assertThat(res.getUserKey(), notNullValue());
						logger.debug("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 회원 부가정보 삭제 테스트 userInfoList is Invalid
	 * </pre>
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test(expected = StorePlatformException.class)
	public void j_removeUserExtraInfoError() {

		new TestCaseTemplate(this.mockMvc).url("/member/user/removeAdditionalInformation/v1").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						UserExtraInfoReq req = new UserExtraInfoReq();
						req.setUserKey("US201401221721168550000323");

						// 회원 부가 정보 삭제 리스트
						List<UserExtraInfo> userExtraList = new ArrayList<UserExtraInfo>();

						UserExtraInfo user1 = new UserExtraInfo();
						user1.setExtraProfile("US004801");

						userExtraList.add(user1);

						req.setUserExtraInfoList(userExtraList);

						logger.debug("request param : {}", req.toString());
						return req;
					}
				}).success(UserExtraInfoRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						UserExtraInfoRes res = (UserExtraInfoRes) result;
						assertThat(res.getUserKey(), notNullValue());
						logger.debug("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}
}
