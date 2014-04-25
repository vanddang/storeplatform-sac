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
import com.skplanet.storeplatform.sac.client.member.vo.user.MbrOneidSacRes;

@ActiveProfiles(value = "local")

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SearchOneIdTest {
	private static final Logger logger = LoggerFactory.getLogger(SearchOneIdTest.class);

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
	 * OneId 정보조회 정상
	 * </pre>
	 */
	@Test
	public void A_TEST_정상_OneId정보조회_데이터있음() {
		new TestCaseTemplate(this.mockMvc).url("/member/user/searchOneIdInfo/v1?userKey=IF1523972220130926133834").httpMethod(HttpMethod.GET)
				.success(MbrOneidSacRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						MbrOneidSacRes res = (MbrOneidSacRes) result;
						assertThat(res.getIsRealName(), notNullValue());
						logger.info("MbrOneidSacRes : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * OneId 정보조회 데이터 없을 떄
	 * </pre>
	 */
	@Test
	public void B_TEST_정상_OneId정보조회_데이터없음() {
		new TestCaseTemplate(this.mockMvc).url("/member/user/searchOneIdInfo/v1?userKey=US201401271645589690000973").httpMethod(HttpMethod.GET)
				.success(MbrOneidSacRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						MbrOneidSacRes res = (MbrOneidSacRes) result;
						assertThat(res.getIsRealName(), notNullValue());
						logger.info("MbrOneidSacRes : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * OneId 정보조회 통합회원 아닐 떄
	 * </pre>
	 */
	@Test(expected = StorePlatformException.class)
	public void C_TEST_오류_SAC_OneId정보조회_통합회원아님() {
		new TestCaseTemplate(this.mockMvc).url("/member/user/searchOneIdInfo/v1?userKey=US201402070435490820001979").httpMethod(HttpMethod.GET)
				.success(MbrOneidSacRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						MbrOneidSacRes res = (MbrOneidSacRes) result;
						assertThat(res.getIsRealName(), notNullValue());
						logger.info("MbrOneidSacRes : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * OneId 정보조회 파라미터 없음
	 * </pre>
	 */
	@Test(expected = StorePlatformException.class)
	public void D_TEST_오류_SAC_OneId정보조회_파라미터없음() {
		new TestCaseTemplate(this.mockMvc).url("/member/user/searchOneIdInfo/v1?userKey=").httpMethod(HttpMethod.GET)
				.success(MbrOneidSacRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						MbrOneidSacRes res = (MbrOneidSacRes) result;
						assertThat(res.getIsRealName(), notNullValue());
						logger.info("MbrOneidSacRes : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * OneId 정보조회 회원키 없음
	 * </pre>
	 */
	@Test(expected = StorePlatformException.class)
	public void E_TEST_오류_SC_OneId정보조회_회원키없음() {
		new TestCaseTemplate(this.mockMvc).url("/member/user/searchOneIdInfo/v1?userKey=123456").httpMethod(HttpMethod.GET)
				.success(MbrOneidSacRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						MbrOneidSacRes res = (MbrOneidSacRes) result;
						assertThat(res.getIsRealName(), notNullValue());
						logger.info("MbrOneidSacRes : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

}
