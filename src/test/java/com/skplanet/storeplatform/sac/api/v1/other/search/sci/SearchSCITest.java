/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.api.v1.other.search.sci;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.external.client.search.sci.SearchSCI;
import com.skplanet.storeplatform.external.client.search.vo.EcSearchReq;
import com.skplanet.storeplatform.external.client.search.vo.EcSearchRes;

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
public class SearchSCITest {

	@Autowired
	private SearchSCI searchSCI;

	/**
	 * 
	 * <pre>
	 * 검색.
	 * </pre>
	 */
	@Test
	public void shouldSearch() {
		EcSearchReq ecSearchReq = new EcSearchReq();
		ecSearchReq.setQ("pc매니저");
		ecSearchReq.setCategory("ALL");
		ecSearchReq.setMeta17("I,M,V,H,B,E,C,J,A,D");
		ecSearchReq.setMeta77("Y");
		ecSearchReq.setOrder("R");
		ecSearchReq.setOffset(0);
		ecSearchReq.setCount(10);
		ecSearchReq.setAdult("Y");
		ecSearchReq.setRel("Y");
		ecSearchReq.setUvKey("38a942103867f40b7f421c9b3c3b4443");
		EcSearchRes ecSearchRes = this.searchSCI.search(ecSearchReq);
		assertNotNull(ecSearchRes);
	}
}
