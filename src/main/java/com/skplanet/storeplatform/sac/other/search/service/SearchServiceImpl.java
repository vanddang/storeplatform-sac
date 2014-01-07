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

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.sac.client.other.vo.search.SearchReq;
import com.skplanet.storeplatform.sac.client.other.vo.search.SearchRes;

/**
 * 
 * 검색 서비스 Class 구현체
 * 
 * Updated on : 2014. 1. 6. Updated by : 김현일, 인크로스
 */
@Profile(value = { "stag", "real" })
@Service
public class SearchServiceImpl implements SearchService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.other.search.service.SearchService#search(com.skplanet.storeplatform.sac.client
	 * .other.vo.search.SearchReq)
	 */
	@Override
	public SearchRes search(SearchReq searchReq) {
		// TODO Auto-generated method stub
		return null;
	}
}
