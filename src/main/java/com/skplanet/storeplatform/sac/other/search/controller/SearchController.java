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

import com.skplanet.storeplatform.external.client.search.vo.TstoreSearchReq;
import com.skplanet.storeplatform.external.client.search.vo.TstoreSearchRes;
import com.skplanet.storeplatform.sac.other.search.service.SearchService;

/**
 * 
 * Search Controller
 * 
 * Updated on : 2014. 1. 13. Updated by : 김현일, 인크로스.
 */
@Controller
@RequestMapping(value = "/other/search")
public class SearchController {

	@Autowired
	private SearchService searchService;

	/**
	 * 
	 * <pre>
	 * Search Controller 기능.
	 * </pre>
	 * 
	 * @param tstoreSearchReq
	 *            tstoreSearchReq
	 * @return TstoreSearchRes
	 */
	@RequestMapping(value = "/v1", method = RequestMethod.GET)
	@ResponseBody
	public TstoreSearchRes search(TstoreSearchReq tstoreSearchReq) {
		// 시스템 구분..
		return this.searchService.search(tstoreSearchReq);
	}

}
