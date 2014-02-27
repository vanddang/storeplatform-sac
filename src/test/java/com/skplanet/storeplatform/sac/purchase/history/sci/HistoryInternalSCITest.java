/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.history.sci;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.skplanet.storeplatform.sac.client.internal.purchase.history.sci.HistoryInternalSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryCountSacInReq;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryCountSacInRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryListSacInReq;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryListSacInRes;

/**
 * 구매내역 테스트 케이스
 * 
 * Updated on : 2014-01-17 Updated by : 조용진, 엔텔스.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class HistoryInternalSCITest {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HistoryInternalSCI sci;

	/**
	 * 구매내역조회.
	 */
	@Test
	public void searchHistoryListTest() {

		HistoryListSacInReq request = new HistoryListSacInReq();

		request.setTenantId("S01");
		request.setUserKey("US201402040504554230001487");
		request.setDeviceKey("DE201402040504557980000851");
		request.setStartDt("20000101000000");
		request.setEndDt("99991231235959");
		request.setPrchsProdHaveYn("Y");
		request.setOffset(1);
		request.setCount(10);

		HistoryListSacInRes response = this.sci.searchHistoryList(request);
		assertThat(response, notNullValue());

	}

	/**
	 * 구매건수조회.
	 */
	@Test
	public void searchHistoryCountTest() {

		HistoryCountSacInReq request = new HistoryCountSacInReq();
		HistoryCountSacInRes response = new HistoryCountSacInRes();

		request.setTenantId("S01");
		request.setUserKey("US201402040504554230001487");
		request.setDeviceKey("DE201402040504557980000851");
		request.setStartDt("20000101000000");
		request.setEndDt("99991231235959");
		request.setPrchsProdHaveYn("Y");

		response = this.sci.searchHistoryCount(request);
		assertThat(response, notNullValue());

	}

}
