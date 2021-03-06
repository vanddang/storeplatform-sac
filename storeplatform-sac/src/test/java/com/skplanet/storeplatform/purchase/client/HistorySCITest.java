/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.purchase.client.history.sci.HistorySCI;
import com.skplanet.storeplatform.purchase.client.history.vo.HistoryCountScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.HistoryCountScRes;
import com.skplanet.storeplatform.purchase.client.history.vo.HistoryListScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.HistoryListScRes;

/**
 * 구매내역 테스트 케이스
 * 
 * Updated on : 2014-01-17 Updated by : 조용진, 엔텔스.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:purchase/spring-test/context-test.xml" })
@Transactional
@TransactionConfiguration
public class HistorySCITest {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HistorySCI sci;

	@Autowired
	private MessageSourceAccessor messageSourceAccessor;

	/**
	 * 구매내역조회.
	 */
	@Test
	public void searchHistoryListTest() {

		HistoryListScReq request = new HistoryListScReq();

		request.setTenantId("S01");
		request.setUserKey("US201402040504554230001487");
		request.setStartDt("20100101000000");
		request.setEndDt("99991231235959");
		request.setPrchsProdHaveYn("Y");
		request.setOffset(1);
		request.setCount(10);

		HistoryListScRes response = this.sci.searchHistoryList(request);
		assertThat(response, notNullValue());

	}

	/**
	 * 구매건수조회.
	 */
	@Test
	public void searchHistoryCountTest() {

		HistoryCountScReq request = new HistoryCountScReq();
		HistoryCountScRes response = new HistoryCountScRes();

		request.setTenantId("S01");
		request.setUserKey("US201402040504554230001487");
		request.setStartDt("20100101000000");
		request.setEndDt("99991231235959");
		request.setPrchsProdHaveYn("Y");

		response = this.sci.searchHistoryCount(request);
		assertThat(response, notNullValue());

	}

}
