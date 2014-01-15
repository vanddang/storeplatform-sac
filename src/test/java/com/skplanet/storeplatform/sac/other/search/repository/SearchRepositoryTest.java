/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.other.search.repository;

import static org.junit.Assert.assertNotNull;

import java.net.URISyntaxException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.external.client.search.vo.TstoreSearchRes;
import com.skplanet.storeplatform.sac.other.search.common.SearchCommon;

/**
 * 
 * 검색 외부연동 SCI 테스트
 * 
 * Updated on : 2014. 1. 6. Updated by : 김현일, 인크로스
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@TransactionConfiguration
@Transactional
public class SearchRepositoryTest {

	@Autowired
	private SearchRepository searchRepository;

	@Test
	public void testSearch() throws URISyntaxException {
		// URL 복사해서 넣기.
		TstoreSearchRes result = this.searchRepository.search(SearchCommon.getTstoreSearchReq());
		assertNotNull(result);
	}
}
