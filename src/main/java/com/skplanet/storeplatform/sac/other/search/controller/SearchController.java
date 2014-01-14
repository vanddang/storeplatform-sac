/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.other.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.external.client.search.vo.TstoreSearchRes;
import com.skplanet.storeplatform.external.client.search.vo.TstoreSearchV2Req;
import com.skplanet.storeplatform.sac.client.other.vo.search.SearchReq;
import com.skplanet.storeplatform.sac.client.other.vo.search.SearchRes;
import com.skplanet.storeplatform.sac.other.search.service.SearchService;
import com.skplanet.storeplatform.sac.other.search.service.SearchServiceV2;

/**
 * 
 * T Store 검색 Controller
 * 
 * Updated on : 2014. 1. 13. Updated by : 김현일, 인크로스.
 */
@Controller
@RequestMapping(value = "/other/search")
public class SearchController {

	@Autowired
	private SearchService searchService;

	@Autowired
	private SearchServiceV2 searchServiceV2;

	/**
	 * 
	 * <pre>
	 * T Store 검색 기능.
	 * </pre>
	 * 
	 * @param searchReq
	 *            searchReq
	 * @return SearchRes
	 */
	@RequestMapping(value = "/v1", method = RequestMethod.GET)
	@ResponseBody
	public SearchRes search(SearchReq searchReq) {
		// 시스템 구분..
		return this.searchService.search(searchReq);
	}

	@RequestMapping(value = "/v2", method = RequestMethod.GET)
	@ResponseBody
	public TstoreSearchRes searchV2(TstoreSearchV2Req tstoreSearchV2Req) {
		// 시스템 구분..
		return this.searchServiceV2.search(tstoreSearchV2Req);
	}
}
