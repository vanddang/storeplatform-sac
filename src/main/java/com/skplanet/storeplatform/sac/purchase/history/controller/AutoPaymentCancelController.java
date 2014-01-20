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

import com.skplanet.storeplatform.purchase.client.history.vo.AutoPaymentCancelRequest;
import com.skplanet.storeplatform.purchase.client.history.vo.AutoPaymentCancelResponse;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.AutoPaymentCancelReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.AutoPaymentCancelRes;
import com.skplanet.storeplatform.sac.purchase.history.service.AutoPaymentCancelService;

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
	private AutoPaymentCancelService autoPaymentCancelService;

	/**
	 * 자동결재해지예약/예약취소/해지 SAC.
	 * 
	 * @param autoPaymentCancelReq
	 *            자동결재해지예약/예약취소/해지 SAC
	 * @return AutoPaymentCancelRes
	 */
	@RequestMapping(value = "/history/reservation/modify/v1", method = RequestMethod.POST)
	@ResponseBody
	public AutoPaymentCancelRes modifyReservation(@RequestBody AutoPaymentCancelReq autoPaymentCancelReq) {

		AutoPaymentCancelRequest rea = this.reqConvert(autoPaymentCancelReq);
		AutoPaymentCancelResponse autoPaymentCancelResponse = new AutoPaymentCancelResponse();

		autoPaymentCancelResponse = this.autoPaymentCancelService.modifyReservation(rea);
		AutoPaymentCancelRes autoPaymentCancelRes = this.resConvert(autoPaymentCancelResponse);
		return autoPaymentCancelRes;
	}

	/**
	 * reqConvert.
	 * 
	 * @param autoPaymentCancelReq
	 *            reqConvert
	 * @return AutoPaymentCancelRequest
	 */
	private AutoPaymentCancelRequest reqConvert(AutoPaymentCancelReq autoPaymentCancelReq) {

		AutoPaymentCancelRequest req = new AutoPaymentCancelRequest();

		req.setTenantId(autoPaymentCancelReq.getTenantId());
		req.setInsdUsermbrNo(autoPaymentCancelReq.getInsdUsermbrNo());
		req.setInsdDeviceId(autoPaymentCancelReq.getInsdDeviceId());
		req.setPrchsId(autoPaymentCancelReq.getPrchsId());
		req.setClosedCd(autoPaymentCancelReq.getClosedCd());
		req.setClosedReasonCd(autoPaymentCancelReq.getClosedReasonCd());
		req.setClosedReqPathCd(autoPaymentCancelReq.getClosedReqPathCd());

		return req;
	}

	/**
	 * resConvert.
	 * 
	 * @param autoPaymentCancelResponse
	 *            resConvert
	 * @return AutoPaymentCancelRes
	 */
	private AutoPaymentCancelRes resConvert(AutoPaymentCancelResponse autoPaymentCancelResponse) {
		AutoPaymentCancelRes res = new AutoPaymentCancelRes();
		this.logger.debug("@@@@@@resConvert@@@@@@@");
		res.setPrchsId(autoPaymentCancelResponse.getPrchsId());
		res.setResultYn(autoPaymentCancelResponse.getResultYn());

		return res;
	}

}
