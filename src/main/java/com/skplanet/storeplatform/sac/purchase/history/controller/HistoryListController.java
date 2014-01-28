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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryCountReq;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryCountRes;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryListReq;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryListRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.purchase.history.service.HistoryListService;

/**
 * 구매내역 Controller
 * 
 * Updated on : 2014-01-10 Updated by : 양주원, 엔텔스.
 */
@Controller
@RequestMapping(value = "/purchase/history")
public class HistoryListController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HistoryListService historyListService;

	/**
	 * 구매내역 조회 기능을 제공한다.
	 * 
	 * @param request
	 *            구매내역요청
	 * @param requestHeader
	 *            공통헤더정보
	 * @return HistoryListResponse
	 */
	@RequestMapping(value = "/list/v1", method = RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public HistoryListRes list(@RequestBody @Validated HistoryListReq request, SacRequestHeader requestHeader) {

		// tenantID, systemId Set
		TenantHeader tenantHeader = requestHeader.getTenantHeader();
		request.setTenantId(tenantHeader.getTenantId());
		request.setSystemId(tenantHeader.getSystemId());
		this.logger.debug("TenantHeader :: " + tenantHeader.toString());

		return this.historyListService.list(request, requestHeader);
	}

	/**
	 * 구매내역건수 조회 기능을 제공한다.
	 * 
	 * @param request
	 *            구매내역요청
	 * @param requestHeader
	 *            공통헤더정보
	 * @return HistoryListResponse
	 */
	@RequestMapping(value = "/count/v1", method = RequestMethod.POST)
	@ResponseBody
	public HistoryCountRes count(@RequestBody HistoryCountReq request, SacRequestHeader requestHeader) {

		// tenantID, systemId Set
		TenantHeader tenantHeader = requestHeader.getTenantHeader();
		request.setTenantId(tenantHeader.getTenantId());
		request.setSystemId(tenantHeader.getSystemId());
		this.logger.debug("TenantHeader :: " + tenantHeader.toString());

		return this.historyListService.count(request);
	}

}
