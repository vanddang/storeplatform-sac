/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.search.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.display.vo.search.SearchProductReq;
import com.skplanet.storeplatform.sac.client.display.vo.search.SearchProductRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.search.service.SearchVodBoxProductService;

/**
 * Meta 정보 조회 Prototype Controller
 * 
 * Updated on : 2014. 1. 27. Updated by : 오승민, 인크로스
 */
@Controller
@RequestMapping("/display/search/product")
public class SearchProductController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SearchVodBoxProductService searchVodBoxProductService;

	/**
	 * <pre>
	 * Vod 상품 조회.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return SearchProductRes
	 */
	@RequestMapping(value = "/vod", method = RequestMethod.GET)
	@ResponseBody
	public SearchProductRes searchVodBoxProdIdList(SearchProductReq req, SacRequestHeader header) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchVodBoxProdIdList Controller started!!");
		this.logger.debug("----------------------------------------------------------------");
		return this.searchVodBoxProductService.searchVodBoxProduct(req, header);
	}
}
