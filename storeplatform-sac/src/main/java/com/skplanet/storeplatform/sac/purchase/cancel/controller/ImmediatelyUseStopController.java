/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.cancel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.purchase.cancel.vo.ImmediatelyUseStopSacReq;
import com.skplanet.storeplatform.sac.client.purchase.cancel.vo.ImmediatelyUseStopSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.purchase.cancel.service.ImmediatelyUseStopService;
import com.skplanet.storeplatform.sac.purchase.common.util.PurchaseCommonUtils;

/**
 * 즉시이용정지 Controller
 * 
 * Updated on : 2014-01-10 Updated by : 양주원, 엔텔스.
 */
@Controller
@RequestMapping(value = "/purchase")
public class ImmediatelyUseStopController {

	@Autowired
	private ImmediatelyUseStopService immediatelyUseStopService;

	@Autowired
	private PurchaseCommonUtils purchaseCommonUtils;

	/**
	 * 즉시이용정지 기능을 제공한다.
	 * 
	 * @param request
	 *            즉시이용정지
	 * @param requestHeader
	 *            공통헤더정보
	 * @return ImmediatelyUseStopSacRes
	 */
	@RequestMapping(value = "/immediately/useStop/v1", method = RequestMethod.POST)
	@ResponseBody
	public ImmediatelyUseStopSacRes searchHistoryList(@RequestBody @Validated ImmediatelyUseStopSacReq request,
			SacRequestHeader sacRequestHeader) {

		this.purchaseCommonUtils.setHeader(request, sacRequestHeader);

		return this.immediatelyUseStopService.updateUseStop(request);
	}

}
