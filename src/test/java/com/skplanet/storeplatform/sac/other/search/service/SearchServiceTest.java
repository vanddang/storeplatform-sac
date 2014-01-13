/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.other.search.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.sac.client.other.vo.search.SearchReq;
import com.skplanet.storeplatform.sac.client.other.vo.search.SearchRes;
import com.skplanet.storeplatform.sac.other.search.service.SearchService;

/**
 * 
 * 검색 서비스 클래스 테스트
 * 
 * Updated on : 2014. 1. 6. Updated by : 김현일, 인크로스
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@TransactionConfiguration
@Transactional
public class SearchServiceTest {

	@Autowired
	private SearchService searchService;

	/**
	 * 
	 * <pre>
	 * 검색.
	 * </pre>
	 */
	@Test
	public void shouldObtainSearch() {
		SearchReq searchReq = new SearchReq();
		// searchReq.setCategory("ALL");
		searchReq.setKeywd("pc매니저");
		searchReq.setAdultYN("Y");
		searchReq.setRelSearchYN("Y");
		searchReq.setDeviceId("38a942103867f40b7f421c9b3c3b4443");
		searchReq.setDeviceModelNo("SHV-E160L");
		searchReq.setOrderedBy("recent");
		searchReq.setOffset(0);
		searchReq.setCount(10);
		SearchRes searchRes = this.searchService.search(searchReq);

		assertNotNull(searchRes);

	}
}
