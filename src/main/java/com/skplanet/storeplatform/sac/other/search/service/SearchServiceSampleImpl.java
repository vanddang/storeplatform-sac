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

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.sac.client.other.vo.search.Category;
import com.skplanet.storeplatform.sac.client.other.vo.search.Search;
import com.skplanet.storeplatform.sac.client.other.vo.search.SearchReq;
import com.skplanet.storeplatform.sac.client.other.vo.search.SearchRes;

@Profile(value = { "dev", "local" })
@Service
public class SearchServiceSampleImpl implements SearchService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SearchServiceSampleImpl.class);

	@Override
	public SearchRes search(SearchReq searchReq) {
		LOGGER.debug("searchReq : {}", searchReq);

		SearchRes searchRes = new SearchRes();

		List<Category> categoryList = new ArrayList<Category>();

		String[] catNm = new String[] { "DP00001", "DP00002", "DP00003", "DP00004", "DP00006" };
		String[] catCnt = new String[] { "50", "100", "150", "200", "250" };
		for (int i = 0; i < 5; i++) {
			Category category = new Category();
			category.setCategoryNm(catNm[i]);
			category.setCategoryCnt(catCnt[i]);
			categoryList.add(category);
		}

		searchRes.setCategoryList(categoryList);
		searchRes.setRelKeywd("");
		searchRes.setAutoTransKeywd("");
		searchRes.setOrgKeywd("");

		List<Search> productList = new ArrayList<Search>();
		productList.add(new Search());
		searchRes.setProductList(productList);

		LOGGER.debug("searchRes : {}", searchRes);
		return searchRes;
	}
}
