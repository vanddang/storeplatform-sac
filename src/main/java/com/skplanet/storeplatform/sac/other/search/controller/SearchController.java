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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.other.vo.search.SearchReq;
import com.skplanet.storeplatform.sac.client.other.vo.search.SearchRes;
import com.skplanet.storeplatform.sac.other.search.service.SearchService;

/**
 * 
 * 통합검색/카테고리검색 기능을 제공하는 Controller
 * 
 * Updated on : 2014. 1. 7. Updated by : 김현일, 인크로스
 */
@Controller
@RequestMapping(value = "/other/search")
public class SearchController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SearchController.class);

	@Autowired
	private SearchService searchService;

	/**
	 * 
	 * <pre>
	 * 검색 결과를 제공한다.
	 * </pre>
	 * 
	 * @param searchReq
	 *            searchReq
	 * @return SearchRes
	 */
	@RequestMapping(value = "/v1")
	@ResponseBody
	public SearchRes search(SearchReq searchReq) {
		return this.searchService.search(searchReq);
	}
}
