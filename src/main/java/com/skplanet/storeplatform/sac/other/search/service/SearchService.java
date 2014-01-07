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
 * 검색 E/C연동 데이터를 가공하는 Service
 * 
 * Updated on : 2014. 1. 7. Updated by : 김현일, 인크로스
 */
public interface SearchService {
	/**
	 * 
	 * <pre>
	 * 검색 E/C연동 데이터를 가공한다.
	 * </pre>
	 * 
	 * @param searchReq
	 *            searchReq
	 * @return SearchRes
	 */
	public SearchRes search(SearchReq searchReq);
}
