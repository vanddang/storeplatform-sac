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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryListReq;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryListRes;
import com.skplanet.storeplatform.sac.purchase.history.service.HistoryListService;

@Controller
@RequestMapping(value = "/purchase/history")
public class HistoryListController {

	private static final Logger logger = LoggerFactory.getLogger(HistoryListController.class);

	@Autowired
	private HistoryListService historyListService;

	@RequestMapping(value = "/list/v1", method = RequestMethod.POST)
	@ResponseBody
	public HistoryListRes list(@RequestBody HistoryListReq historyListReq) {
		return this.historyListService.list(historyListReq);
	}
}
