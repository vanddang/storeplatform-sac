/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.other.search.common;

import java.net.URI;
import java.net.URISyntaxException;

import com.skplanet.storeplatform.external.client.search.vo.TstoreSearchReq;

/**
 * 
 * Search Common
 * 
 * Updated on : 2014. 1. 16. Updated by : 김현일, 인크로스.
 */
public class SearchCommon {

	public static final String Q = "pc매니저";
	public static final String CATEGORY = "ALL";
	public static final String META17 = "I,M,V,H,B,E,C,J,A,D";
	public static final String META77 = "Y";
	public static final String ORDER = "R";
	public static final int OFFSET = 0;
	public static final int COUNT = 10;
	public static final String ADULT = "Y";
	public static final String REL = "Y";
	public static final String UVKEY = "38a942103867f40b7f421c9b3c3b4443";

	/**
	 * 
	 * <pre>
	 * 검색 URI 생성.
	 * </pre>
	 * 
	 * @return URI
	 */
	public static URI getURI() {
		try {
			return new URI(
					"http://172.21.0.44:19000/tstore/1.0/search?key=f7fae3546df3736d49e33d7655a1cae9&q=pc&category=DP000503&adult=DP000503&count=5&order=R&gubun=3&meta1=P&meta17=I,M,V,H,B,E,C,J,A,D&rel=Y&uvKey=38a942103867f40b7f421c9b3c3b4443%26");
		} catch (URISyntaxException e) {
			throw new RuntimeException("");
		}
	}

	/**
	 * 
	 * <pre>
	 * TstoreSearchReq VO 생성.
	 * </pre>
	 * 
	 * @return TstoreSearchReq
	 */
	public static TstoreSearchReq getTstoreSearchReq() {
		TstoreSearchReq tstoreSearchReq = new TstoreSearchReq();
		tstoreSearchReq.setQ(Q);
		tstoreSearchReq.setCategory(CATEGORY);
		tstoreSearchReq.setMeta17(META17);
		tstoreSearchReq.setMeta77(META77);
		tstoreSearchReq.setOrder(ORDER);
		tstoreSearchReq.setOffset(OFFSET);
		tstoreSearchReq.setCount(COUNT);
		tstoreSearchReq.setAdult(ADULT);
		tstoreSearchReq.setRel(REL);
		tstoreSearchReq.setUvKey(UVKEY);
		return tstoreSearchReq;
	}
}
