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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.external.client.search.sci.SearchSCI;
import com.skplanet.storeplatform.external.client.search.vo.TstoreSearchRes;
import com.skplanet.storeplatform.external.client.search.vo.TstoreSearchV2Req;

@Component
public class SearchRepositoryV2Impl implements SearchRepositoryV2 {

	@Autowired
	private SearchSCI searchSCI;

	@Override
	public TstoreSearchRes search(TstoreSearchV2Req tstoreSearchV2Req) {
		return this.searchSCI.searchV2(tstoreSearchV2Req);
	}
}
