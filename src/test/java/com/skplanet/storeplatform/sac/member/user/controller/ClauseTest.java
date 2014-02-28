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
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailClauseSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListClauseSacRes;

@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClauseTest {
	private static final Logger logger = LoggerFactory.getLogger(ClauseTest.class);

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
	 * 약관목록 조회
	 * </pre>
	 */
	@Test
	public void A_TEST_정상_약관목록조회() {
		new TestCaseTemplate(this.mockMvc).url("/member/user/listClause/v1").httpMethod(HttpMethod.GET)
				.success(ListClauseSacRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						ListClauseSacRes res = (ListClauseSacRes) result;
						assertThat(res.getClauseList().get(0).getClauseItemCd(), notNullValue());
						logger.info("ListClauseSacRes : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 약관목록 상세 조회
	 * </pre>
	 */
	@Test
	public void B_TEST_정상_약관목록상세조회() {
		new TestCaseTemplate(this.mockMvc).url("/member/user/detailClause/v1?clauseItemCd=US010608").httpMethod(HttpMethod.GET)
				.success(DetailClauseSacRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						DetailClauseSacRes res = (DetailClauseSacRes) result;
						assertThat(res.getDetailClauseList().get(0).getClauseItemCd(), notNullValue());
						logger.info("DetailClauseSacRes : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 약관목록 상세 조회 : 잘못된 키
	 * </pre>
	 */
	@Test(expected = StorePlatformException.class)
	public void C_TEST_오류_SAC_약관목록상세조회_잘못된키() {
		new TestCaseTemplate(this.mockMvc).url("/member/user/detailClause/v1?clauseItemCd=US01060821").httpMethod(HttpMethod.GET)
				.success(DetailClauseSacRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						DetailClauseSacRes res = (DetailClauseSacRes) result;
						assertThat(res.getDetailClauseList().get(0).getClauseItemCd(), notNullValue());
						logger.info("DetailClauseSacRes : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 약관목록 상세 조회 : 파라미터 없음
	 * </pre>
	 */
	@Test(expected = StorePlatformException.class)
	public void D_TEST_오류_SAC_약관목록상세조회_파라미터없음() {
		new TestCaseTemplate(this.mockMvc).url("/member/user/detailClause/v1?clauseItemCd=").httpMethod(HttpMethod.GET)
				.success(DetailClauseSacRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						DetailClauseSacRes res = (DetailClauseSacRes) result;
						assertThat(res.getDetailClauseList().get(0).getClauseItemCd(), notNullValue());
						logger.info("DetailClauseSacRes : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

}
