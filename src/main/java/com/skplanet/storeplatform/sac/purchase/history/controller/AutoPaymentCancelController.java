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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.purchase.client.history.vo.AutoPaymentCancelScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.AutoPaymentCancelScRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.AutoPaymentCancelSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.AutoPaymentCancelSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.purchase.common.util.PurchaseCommonUtils;
import com.skplanet.storeplatform.sac.purchase.history.service.AutoPaymentCancelSacService;

/**
 * 구매 SAC 컨트롤러
 * 
 * Updated on : 2014-01-14 Updated by : 조용진, 엔텔스.
 */
@Controller
@RequestMapping(value = "/purchase")
public class AutoPaymentCancelController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AutoPaymentCancelSacService autoPaymentCancelSacService;
	@Autowired
	private PurchaseCommonUtils purchaseCommonUtils;

	/**
	 * 자동결재해지예약/예약취소/해지 SAC.
	 * 
	 * @param autoPaymentCancelSacReq
	 *            요청정보
	 * @param bindingResult
	 *            Validated Result
	 * @param requestHeader
	 *            헤더정보
	 * @return AutoPaymentCancelSacRes 응답값
	 */
	@RequestMapping(value = "/history/reservation/update/v1", method = RequestMethod.POST)
	public @ResponseBody
	AutoPaymentCancelSacRes updateReservation(@RequestBody @Validated AutoPaymentCancelSacReq autoPaymentCancelSacReq,
			BindingResult bindingResult, SacRequestHeader requestHeader) {

		TenantHeader header = requestHeader.getTenantHeader();
		// 필수값 체크
		this.purchaseCommonUtils.getBindingValid(bindingResult);

		return this.resConvert(this.autoPaymentCancelSacService.updateReservation(this.reqConvert(
				autoPaymentCancelSacReq, header)));
	}

	/**
	 * reqConvert.
	 * 
	 * @param autoPaymentCancelSacReq
	 *            요청정보
	 * @param header
	 *            테넌트 헤더정보
	 * @return AutoPaymentCancelScReq
	 */
	private AutoPaymentCancelScReq reqConvert(AutoPaymentCancelSacReq autoPaymentCancelSacReq, TenantHeader header) {

		AutoPaymentCancelScReq req = new AutoPaymentCancelScReq();

		req.setTenantId(header.getTenantId());
		req.setSystemId(header.getSystemId());
		req.setUserKey(autoPaymentCancelSacReq.getUserKey());
		req.setDeviceKey(autoPaymentCancelSacReq.getDeviceKey());
		req.setPrchsId(autoPaymentCancelSacReq.getPrchsId());
		req.setClosedCd(autoPaymentCancelSacReq.getClosedCd());
		req.setClosedReasonCd(autoPaymentCancelSacReq.getClosedReasonCd());
		req.setClosedReqPathCd(autoPaymentCancelSacReq.getClosedReqPathCd());

		return req;
	}

	/**
	 * resConvert.
	 * 
	 * @param autoPaymentCancelScRes
	 *            요청정보
	 * @return AutoPaymentCancelSacRes
	 */
	private AutoPaymentCancelSacRes resConvert(AutoPaymentCancelScRes autoPaymentCancelScRes) {
		AutoPaymentCancelSacRes res = new AutoPaymentCancelSacRes();
		this.logger.debug("@@@@@@resConvert@@@@@@@");
		res.setPrchsId(autoPaymentCancelScRes.getPrchsId());
		res.setResultYn(autoPaymentCancelScRes.getResultYn());

		return res;
	}

}
