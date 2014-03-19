/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.payplanet.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.purchase.vo.payplanet.CultureSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.payplanet.CultureSacRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.payplanet.DotoriSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.payplanet.DotoriSacRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.payplanet.OkCashBagSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.payplanet.OkCashBagSacRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.payplanet.TMemberShipSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.payplanet.TMemberShipSacRes;
import com.skplanet.storeplatform.sac.purchase.payplanet.service.PaymentMethodPointSearchSacService;

/**
 * Culture point Controller.
 * 
 * Updated on : 2014. 3. 4. Updated by : 조용진, NTELS.
 */
@Controller
@RequestMapping(value = "/purchase/payplanet")
public class PaymentMethodPointSearchController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PaymentMethodPointSearchSacService paymentMethodPointSearchSacService;

	/**
	 * 
	 * <pre>
	 * 문화상품권 조회.
	 * </pre>
	 * 
	 * @param cultureSacReq
	 *            요청
	 * @return CultureSacRes
	 */
	@RequestMapping(value = "/culture/point/get/v1", method = RequestMethod.POST)
	@ResponseBody
	public CultureSacRes postCulture(@RequestBody @Validated CultureSacReq cultureSacReq) {
		return this.paymentMethodPointSearchSacService.postCulture(cultureSacReq);
	}

	/**
	 * 
	 * <pre>
	 * 도토리 조회.
	 * </pre>
	 * 
	 * @param dotoriSacReq
	 *            요청
	 * @return DotoriSacRes
	 */
	@RequestMapping(value = "/dotori/count/get/v1", method = RequestMethod.POST)
	@ResponseBody
	public DotoriSacRes postDotori(@RequestBody @Validated DotoriSacReq dotoriSacReq) {
		return this.paymentMethodPointSearchSacService.postDotori(dotoriSacReq);
	}

	/**
	 * 
	 * <pre>
	 * okcashbag point 조회.
	 * </pre>
	 * 
	 * @param okCashBagSacReq
	 *            요청
	 * @return OkCashBagSacRes
	 */
	@RequestMapping(value = "/ocb/point/get/v1", method = RequestMethod.POST)
	@ResponseBody
	public OkCashBagSacRes postOkCashBag(@RequestBody @Validated OkCashBagSacReq okCashBagSacReq) {
		return this.paymentMethodPointSearchSacService.postOkCashBag(okCashBagSacReq);
	}

	/**
	 * 
	 * <pre>
	 * TMemberShip point 조회.
	 * </pre>
	 * 
	 * @param tMemberShipSacReq
	 *            요청
	 * @return TMemberShipSacRes
	 */
	@RequestMapping(value = "/tms/point/get/v1", method = RequestMethod.POST)
	@ResponseBody
	public TMemberShipSacRes postTMemberShip(@RequestBody @Validated TMemberShipSacReq tMemberShipSacReq) {
		return this.paymentMethodPointSearchSacService.postTMemberShip(tMemberShipSacReq);
	}
}
