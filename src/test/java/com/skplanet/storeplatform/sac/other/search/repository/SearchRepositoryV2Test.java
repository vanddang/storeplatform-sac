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

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import com.skplanet.storeplatform.external.client.search.vo.TstoreSearchV2Req;
//
//@ActiveProfiles(value = "local")
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
//@TransactionConfiguration
//@Transactional

public class SearchRepositoryV2Test {

	// @Autowired
	// private SearchRepositoryV2 searchRepositoryV2;

	@Test
	public void testSearch() throws JsonGenerationException, JsonMappingException, IOException {
		TstoreSearchV2Req tstoreSearchV2Req = new TstoreSearchV2Req();
		tstoreSearchV2Req.setGubun("3");
		tstoreSearchV2Req.setMeta1("P");
		tstoreSearchV2Req.setQ("pc매니저");
		tstoreSearchV2Req.setCategory("ALL");
		tstoreSearchV2Req.setAdult("Y");
		tstoreSearchV2Req.setCount(10);
		tstoreSearchV2Req.setOrder("R");
		tstoreSearchV2Req.setMeta17("I,M,V,H,B,E,C,J,A,D");
		tstoreSearchV2Req.setRel("Y");
		tstoreSearchV2Req.setUvKey("38a942103867f40b7f421c9b3c3b4443&");
		tstoreSearchV2Req.setMeta77("Y");

		ObjectMapper mapper = new ObjectMapper();
		String input = mapper.writeValueAsString(tstoreSearchV2Req);
		System.out.println(input);

		// String result = this.searchRepositoryV2.search(tstoreSearchV2Req);

		// System.out.println(result);

	}
}
