/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.history.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryCountSacReq;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryCountSacRes;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryListSacReq;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryListSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.purchase.common.util.PurchaseCommonUtils;
import com.skplanet.storeplatform.sac.purchase.history.service.HistoryListService;

/**
 * 구매내역 Controller
 * 
 * Updated on : 2014-01-10 Updated by : 양주원, 엔텔스.
 */
@Controller
@RequestMapping(value = "/purchase/history")
public class HistoryListController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HistoryListService historyListService;

	@Autowired
	private PurchaseCommonUtils purchaseCommonUtils;

	/**
	 * 구매내역 조회 기능을 제공한다.
	 * 
	 * @param request
	 *            구매내역요청
	 * @param requestHeader
	 *            공통헤더정보
	 * @return HistoryListSacRes
	 */
	@RequestMapping(value = "/list/v1", method = RequestMethod.POST)
	@ResponseBody
	public HistoryListSacRes searchHistoryList(@RequestBody @Validated HistoryListSacReq request,
			SacRequestHeader sacRequestHeader) {

		// this.purchaseCommonUtils.getBindingValid(bindingResult);
		this.purchaseCommonUtils.setHeader(request, sacRequestHeader);

		return this.historyListService.searchHistoryList(request);
	}

	/**
	 * 구매건수 조회 기능을 제공한다.
	 * 
	 * @param request
	 *            구매내역요청
	 * @param requestHeader
	 *            공통헤더정보
	 * @return HistoryCountSacRes
	 */
	@RequestMapping(value = "/count/v1", method = RequestMethod.POST)
	@ResponseBody
	public HistoryCountSacRes searchHistoryCount(@RequestBody HistoryCountSacReq request,
			SacRequestHeader sacRequestHeader) {

		// this.purchaseCommonUtils.getBindingValid(bindingResult);
		this.purchaseCommonUtils.setHeader(request, sacRequestHeader);

		return this.historyListService.searchHistoryCount(request);
	}

}
