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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.purchase.history.vo.MileageSaveSacReq;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.MileageSaveSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.purchase.common.util.PurchaseCommonUtils;
import com.skplanet.storeplatform.sac.purchase.history.service.MileageSaveService;

/**
 * T마일리지 Controller
 * 
 * Updated on : 2014-01-10 Updated by : 양주원, 엔텔스.
 */
@Controller
@RequestMapping(value = "/purchase/payment")
public class MileageSaveController {

	// private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MileageSaveService mileageSaveService;

	@Autowired
	private PurchaseCommonUtils purchaseCommonUtils;

	/**
	 * T마일리지 조회 기능을 제공한다.
	 * 
	 * @param request
	 *            T마일리지요청
	 * @param requestHeader
	 *            공통헤더정보
	 * @return MileageSaveSacRes
	 */
	@RequestMapping(value = "/mileage/list/v1", method = RequestMethod.POST)
	@ResponseBody
	public MileageSaveSacRes searchMileageSave(@RequestBody @Validated MileageSaveSacReq request,
			SacRequestHeader sacRequestHeader) {

		this.purchaseCommonUtils.setHeader(request, sacRequestHeader);

		return this.mileageSaveService.searchMileageSave(request);
	}

}
