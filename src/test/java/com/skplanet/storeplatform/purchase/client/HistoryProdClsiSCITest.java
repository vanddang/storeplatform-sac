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

import com.skplanet.storeplatform.purchase.client.history.sci.HistoryProdClsiSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.HistoryProdClsiScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.HistoryProdClsiScRes;

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
public class HistoryProdClsiSCITest {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HistoryProdClsiSCI sci;

	@Autowired
	private MessageSourceAccessor messageSourceAccessor;

	/**
	 * 구매내역조회.
	 */
	@Test
	public void listHistoryTest() {

		HistoryProdClsiScReq request = new HistoryProdClsiScReq();

		request.setTenantId("S01");
		request.setStartDt("20000101010101");
		request.setEndDt("20140101010101");
		request.setProdId("H900000041");

		HistoryProdClsiScRes response = this.sci.searchHistoryProdClsiList(request);
		assertThat(response, notNullValue());

	}

}
