/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.api.v1.purchase;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
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
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryListSacReq;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryListSacRes;
import com.skplanet.storeplatform.sac.purchase.history.service.HistoryListService;

/**
 * 구매 목록 테스트
 * 
 * Updated on : 2013-09-01 Updated by : 아키텍쳐팀, SK플래닛.
 */
@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@TransactionConfiguration
@Transactional
public class HistoryListTest {
	// private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Autowired
	private HistoryListService service;

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
	 * 구매 목록 단위테스트.
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test
	public void shouldObtainHistoryList() throws Exception {
		new TestCaseTemplate(this.mockMvc).url("/purchase/history/list/v1").httpMethod(HttpMethod.POST)

		.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						HistoryListSacReq req = new HistoryListSacReq();
						req.setTenantId("S01"); // 테넌트ID
						req.setInsdUsermbrNo("IF1423020847420091202152309"); // 사용자번호
						req.setPrchsStatusCd("OR000301"); // 구매상태 OR000301:구매완료 OR000302:구매취소 null:전체
						req.setStartDt("20100101000000"); // 조회시작일시
						req.setEndDt("20140113000000"); // 조회종료일시
						req.setHidingYn("N"); // 숨김여부 Y:숨김 N:비숨김 null:전체
						// req.setTenantProdGrpCd("GRP");

						// req.setPrchsProdType("send");

						return req;
					}
				}).success(HistoryListSacRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						HistoryListSacRes res = (HistoryListSacRes) result;

						assertThat(res, notNullValue());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
	}

	/**
	 * 구매 목록 단위테스트.
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test
	public void shouldObtainHistoryCount() throws Exception {
		new TestCaseTemplate(this.mockMvc).url("/purchase/history/count/v1").httpMethod(HttpMethod.POST)

		.addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						HistoryListSacReq req = new HistoryListSacReq();
						req.setTenantId("S01"); // 테넌트ID
						req.setInsdUsermbrNo("IF1423020847420091202152309"); // 사용자번호
						req.setPrchsStatusCd("OR000301"); // 구매상태 OR000301:구매완료 OR000302:구매취소 null:전체
						req.setStartDt("20100101000000"); // 조회시작일시
						req.setEndDt("20140113000000"); // 조회종료일시
						req.setHidingYn("N"); // 숨김여부 Y:숨김 N:비숨김 null:전체
						// req.setTenantProdGrpCd("GRP");

						// req.setPrchsProdType("send");

						return req;
					}
				}).success(HistoryListSacRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						HistoryListSacRes res = (HistoryListSacRes) result;

						assertThat(res, notNullValue());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);
	}
}
