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

import com.skplanet.storeplatform.sac.client.other.vo.search.SearchReq;
import com.skplanet.storeplatform.sac.client.other.vo.search.SearchRes;

/**
 * 
 * T Store 검색 Service
 * 
 * Updated on : 2014. 1. 13. Updated by : 김현일, 인크로스.
 */
public interface SearchService {

	/**
	 * 
	 * <pre>
	 * T Store 검색 데이터를 가공.
	 * </pre>
	 * 
	 * @param searchReq
	 *            searchReq
	 * @return SearchRes
	 */
	public SearchRes search(SearchReq searchReq);
}
