/**
 * 
 */
package com.skplanet.storeplatform.sac.member.user.sci.controller;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.skplanet.storeplatform.framework.test.RequestBodySetter;
import com.skplanet.storeplatform.framework.test.SuccessCallback;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate.RunMode;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchOrderUserByDeviceIdSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchOrderUserByDeviceIdSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserDeviceSac;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserDeviceSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserDeviceSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserPayplanetSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserPayplanetSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.common.util.MockRequestAttributeInitializer;

/**
 * 회원정보 관련 내부메소드 호출 SCI Controller Test.
 * 
 * Updated on : 2014. 2. 25. Updated by : 김다슬, 인크로스.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@WebAppConfiguration
@TransactionConfiguration
@Transactional
public class SearchUserSCIControllerTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchUserSCIControllerTest.class);

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mvc;

	/**
	 * 
	 * <pre>
	 * 초기화.
	 * </pre>
	 */
	@Before
	public void before() {
		SacRequestHeader sacRequestHeader = new SacRequestHeader();
		TenantHeader tenantHeader = new TenantHeader();
		tenantHeader.setTenantId("S01");
		tenantHeader.setSystemId("S001");
		sacRequestHeader.setTenantHeader(tenantHeader);

		MockRequestAttributeInitializer.init(SacRequestHeader.class.getName(), sacRequestHeader);
		this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void testSearchUserByUserKey() throws Exception {
		new TestCaseTemplate(this.mvc).url("/member/user/sci/searchUserByUserKey").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						SearchUserSacReq searchUserSacReq = new SearchUserSacReq();
						List<String> userKeyList = new ArrayList<String>();
						userKeyList.add("IW1023284651220101007215215"); // 회원정보에 등록된 deviceId가 한 개.
						userKeyList.add("IM142100006719244201304082142"); // 회원정보에 등록된 deviceId가 여러개.
						userKeyList.add("IW1024171529820110627132506"); // 회원정보 없음.

						searchUserSacReq.setUserKeyList(userKeyList);
						return searchUserSacReq;
					}
				}).success(SearchUserSacRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						SearchUserSacRes res = (SearchUserSacRes) result;
						LOGGER.info("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/** =============== UserKey 이용한 결제페이지 노출정보조회 =============== */
	@Test
	public void TEST_USERKEY_정상_사용자_결제페이지_노출정보조회_통신과금정보없음() throws Exception {
		new TestCaseTemplate(this.mvc).url("/member/user/sci/searchUserPayplanet").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						SearchUserPayplanetSacReq searchUserSacReq = new SearchUserPayplanetSacReq();
						searchUserSacReq.setUserKey("IF102158916420090711152643");

						return searchUserSacReq;
					}
				}).success(SearchUserPayplanetSacRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						SearchUserPayplanetSacRes res = (SearchUserPayplanetSacRes) result;
						LOGGER.info("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	@Test
	public void TEST_USERKEY_정상_사용자_결제페이지_노출정보조회_통신과금정보있음() throws Exception {
		new TestCaseTemplate(this.mvc).url("/member/user/sci/searchUserPayplanet").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						SearchUserPayplanetSacReq searchUserSacReq = new SearchUserPayplanetSacReq();
						searchUserSacReq.setUserKey("US201402071133550360001951");

						return searchUserSacReq;
					}
				}).success(SearchUserPayplanetSacRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						SearchUserPayplanetSacRes res = (SearchUserPayplanetSacRes) result;
						LOGGER.info("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	@Test
	public void TEST_USERKEY_정상_사용자_결제페이지_노출정보조회_통합회원이고_OCB이용약관정보없음() throws Exception {
		new TestCaseTemplate(this.mvc).url("/member/user/sci/searchUserPayplanet").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						SearchUserPayplanetSacReq searchUserSacReq = new SearchUserPayplanetSacReq();
						searchUserSacReq.setUserKey("US201402071133550360001951");

						return searchUserSacReq;
					}
				}).success(SearchUserPayplanetSacRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						SearchUserPayplanetSacRes res = (SearchUserPayplanetSacRes) result;
						LOGGER.info("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	@Test
	public void TEST_USERKEY_정상_사용자_결제페이지_노출정보조회_통합회원아님() throws Exception {
		new TestCaseTemplate(this.mvc).url("/member/user/sci/searchUserPayplanet").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						SearchUserPayplanetSacReq searchUserSacReq = new SearchUserPayplanetSacReq();
						searchUserSacReq.setUserKey("IW1023857942220110414141217");
						searchUserSacReq.setDeviceKey("");

						return searchUserSacReq;
					}
				}).success(SearchUserPayplanetSacRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						SearchUserPayplanetSacRes res = (SearchUserPayplanetSacRes) result;
						LOGGER.info("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/** =============== DeviceKey 이용한 결제페이지 노출정보조회 =============== */
	@Test
	public void TEST_DeviceKey_정상_사용자_결제페이지_노출정보조회_통신과금정보없음() throws Exception {
		new TestCaseTemplate(this.mvc).url("/member/user/sci/searchUserPayplanet").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						SearchUserPayplanetSacReq searchUserSacReq = new SearchUserPayplanetSacReq();
						searchUserSacReq.setDeviceKey("01092733218");

						return searchUserSacReq;
					}
				}).success(SearchUserPayplanetSacRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						SearchUserPayplanetSacRes res = (SearchUserPayplanetSacRes) result;
						LOGGER.info("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	@Test
	public void TEST_DeviceKey_정상_사용자_결제페이지_노출정보조회_통신과금정보있음() throws Exception {
		new TestCaseTemplate(this.mvc).url("/member/user/sci/searchUserPayplanet").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						SearchUserPayplanetSacReq searchUserSacReq = new SearchUserPayplanetSacReq();
						searchUserSacReq.setDeviceKey("DE201403061448548830002894");

						return searchUserSacReq;
					}
				}).success(SearchUserPayplanetSacRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						SearchUserPayplanetSacRes res = (SearchUserPayplanetSacRes) result;
						LOGGER.info("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	@Test
	public void TEST_DeviceKey_정상_사용자_결제페이지_노출정보조회_통합회원이고_OCB이용약관정보없음() throws Exception {
		new TestCaseTemplate(this.mvc).url("/member/user/sci/searchUserPayplanet").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						SearchUserPayplanetSacReq searchUserSacReq = new SearchUserPayplanetSacReq();
						searchUserSacReq.setDeviceKey("DE201403061448548830002894");

						return searchUserSacReq;
					}
				}).success(SearchUserPayplanetSacRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						SearchUserPayplanetSacRes res = (SearchUserPayplanetSacRes) result;
						LOGGER.info("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	@Test
	public void TEST_DeviceKey_정상_사용자_결제페이지_노출정보조회_통합회원아님() throws Exception {
		new TestCaseTemplate(this.mvc).url("/member/user/sci/searchUserPayplanet").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						SearchUserPayplanetSacReq searchUserSacReq = new SearchUserPayplanetSacReq();
						searchUserSacReq.setDeviceKey("01031241569");
						searchUserSacReq.setUserKey("");

						return searchUserSacReq;
					}
				}).success(SearchUserPayplanetSacRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						SearchUserPayplanetSacRes res = (SearchUserPayplanetSacRes) result;
						LOGGER.info("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/** ==================== DevceKey를 이용한 회원정보&디바이스정보 조회 ============ */
	@Test
	public void A_TEST_정상_DeviceKeyList_검색() throws Exception {
		new TestCaseTemplate(this.mvc).url("/member/user/sci/searchUserByDeviceKey").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						SearchUserDeviceSacReq searchUserDeviceSacReq = new SearchUserDeviceSacReq();

						List<SearchUserDeviceSac> userDeviceList = new ArrayList<SearchUserDeviceSac>();

						SearchUserDeviceSac schUserDevice = new SearchUserDeviceSac();
						schUserDevice.setDeviceKey("DE201402131645572670001658");
						schUserDevice.setUserKey("US201402131645569940002421");

						userDeviceList.add(schUserDevice);

						searchUserDeviceSacReq.setSearchUserDeviceReqList(userDeviceList);

						return searchUserDeviceSacReq;
					}
				}).success(SearchUserDeviceSacRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						SearchUserDeviceSacRes res = (SearchUserDeviceSacRes) result;
						LOGGER.info("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	@Test
	public void B_TEST_정상_DeviceKeyList_검색_일부검색결과있음() throws Exception {
		new TestCaseTemplate(this.mvc).url("/member/user/sci/searchUserByDeviceKey").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						SearchUserDeviceSacReq searchUserDeviceSacReq = new SearchUserDeviceSacReq();

						List<SearchUserDeviceSac> userDeviceList = new ArrayList<SearchUserDeviceSac>();

						SearchUserDeviceSac schUserDevice = new SearchUserDeviceSac();
						schUserDevice.setDeviceKey("DE201402120522137350001556");
						schUserDevice.setUserKey("US201401271926064310001061");

						SearchUserDeviceSac schUserDevice1 = new SearchUserDeviceSac();
						schUserDevice1.setDeviceKey("DE20140213164557267000165811");
						schUserDevice1.setUserKey("US20140213164556994000242111");

						userDeviceList.add(schUserDevice);
						userDeviceList.add(schUserDevice1);

						searchUserDeviceSacReq.setSearchUserDeviceReqList(userDeviceList);

						return searchUserDeviceSacReq;
					}
				}).success(SearchUserDeviceSacRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						SearchUserDeviceSacRes res = (SearchUserDeviceSacRes) result;
						LOGGER.info("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	@Test
	public void B_TEST_정상_DeviceKeyList_검색_실명인증() throws Exception {
		new TestCaseTemplate(this.mvc).url("/member/user/sci/searchUserByDeviceKey").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						SearchUserDeviceSacReq searchUserDeviceSacReq = new SearchUserDeviceSacReq();

						List<SearchUserDeviceSac> userDeviceList = new ArrayList<SearchUserDeviceSac>();

						SearchUserDeviceSac schUserDevice = new SearchUserDeviceSac();
						schUserDevice.setDeviceKey("01034669429");
						schUserDevice.setUserKey("IW1425162260520130327171813");

						userDeviceList.add(schUserDevice);

						searchUserDeviceSacReq.setSearchUserDeviceReqList(userDeviceList);

						return searchUserDeviceSacReq;
					}
				}).success(SearchUserDeviceSacRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						SearchUserDeviceSacRes res = (SearchUserDeviceSacRes) result;
						LOGGER.info("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	//
	// @Test(expected = StorePlatformException.class)
	// public void C_TEST_오류_DeviceKeyList_검색_검색결과없음() throws Exception {
	// new
	// TestCaseTemplate(this.mvc).url("/member/user/sci/searchUserByDeviceKey").httpMethod(HttpMethod.POST).requestBody(new
	// RequestBodySetter() {
	// @Override
	// public Object requestBody() {
	// SearchUserDeviceSacReq searchUserDeviceSacReq = new SearchUserDeviceSacReq();
	// List<String> deviceKeyList = new ArrayList<String>();
	// deviceKeyList.add("DE20140212040954148000155211");
	// deviceKeyList.add("DE20140212052213735000155611");
	// deviceKeyList.add("DE20140212145618389000155811");
	//
	// searchUserDeviceSacReq.setDeviceKeyList(deviceKeyList);
	// return searchUserDeviceSacReq;
	// }
	// }).success(SearchUserDeviceSacRes.class, new SuccessCallback() {
	// @Override
	// public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
	// SearchUserDeviceSacRes res = (SearchUserDeviceSacRes) result;
	// LOGGER.info("response param : {}", res.toString());
	// }
	// }, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
	//
	// }
	/**
	 * <pre>
	 * deviceId, 구매일시로 최근 회원정보(탈퇴포함) 조회.
	 * </pre>
	 */
	@Test
	public void testSearchOrderUserByDeviceId() {
		new TestCaseTemplate(this.mvc).url("/member/user/sci/searchOrderUserByDeviceId").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						SearchOrderUserByDeviceIdSacReq req = new SearchOrderUserByDeviceIdSacReq();
						req.setDeviceId("01048088874");
						req.setOrderDt("20140324160000");
						return req;
					}
				}).success(SearchOrderUserByDeviceIdSacRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						SearchOrderUserByDeviceIdSacRes res = (SearchOrderUserByDeviceIdSacRes) result;
						LOGGER.info("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

}
