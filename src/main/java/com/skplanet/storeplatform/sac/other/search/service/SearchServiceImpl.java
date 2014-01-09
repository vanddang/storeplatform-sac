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

@Profile(value = { "stag", "real" })
@Service
public class SearchServiceImpl implements SearchService {

	@Override
	public SearchRes search(SearchReq searchReq) {
		return null;
	}
}
