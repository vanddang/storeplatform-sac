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

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.purchase.client.history.vo.AutoPaymentCancelScRequest;
import com.skplanet.storeplatform.purchase.client.history.vo.AutoPaymentCancelScResponse;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.AutoPaymentCancelSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.AutoPaymentCancelSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
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
		if (bindingResult.hasErrors()) {
			List<FieldError> errors = bindingResult.getFieldErrors();
			for (FieldError error : errors) {
				throw new StorePlatformException("SAC_PUR_0001", error.getField());
			}
		}

		AutoPaymentCancelScRequest rea = this.reqConvert(autoPaymentCancelSacReq, header);
		AutoPaymentCancelScResponse autoPaymentCancelResponse = new AutoPaymentCancelScResponse();

		autoPaymentCancelResponse = this.autoPaymentCancelSacService.updateReservation(rea);
		AutoPaymentCancelSacRes autoPaymentCancelSacRes = this.resConvert(autoPaymentCancelResponse);

		return autoPaymentCancelSacRes;
	}

	/**
	 * reqConvert.
	 * 
	 * @param autoPaymentCancelSacReq
	 *            요청정보
	 * @param header
	 *            테넌트 헤더정보
	 * @return AutoPaymentCancelScRequest
	 */
	private AutoPaymentCancelScRequest reqConvert(AutoPaymentCancelSacReq autoPaymentCancelSacReq, TenantHeader header) {

		AutoPaymentCancelScRequest req = new AutoPaymentCancelScRequest();

		req.setTenantId(header.getTenantId());
		req.setSystemId(header.getSystemId());
		req.setInsdUsermbrNo(autoPaymentCancelSacReq.getInsdUsermbrNo());
		req.setInsdDeviceId(autoPaymentCancelSacReq.getInsdDeviceId());
		req.setPrchsId(autoPaymentCancelSacReq.getPrchsId());
		req.setClosedCd(autoPaymentCancelSacReq.getClosedCd());
		req.setClosedReasonCd(autoPaymentCancelSacReq.getClosedReasonCd());
		req.setClosedReqPathCd(autoPaymentCancelSacReq.getClosedReqPathCd());

		return req;
	}

	/**
	 * resConvert.
	 * 
	 * @param autoPaymentCancelScResponse
	 *            요청정보
	 * @return AutoPaymentCancelSacRes
	 */
	private AutoPaymentCancelSacRes resConvert(AutoPaymentCancelScResponse autoPaymentCancelScResponse) {
		AutoPaymentCancelSacRes res = new AutoPaymentCancelSacRes();
		this.logger.debug("@@@@@@resConvert@@@@@@@");
		res.setPrchsId(autoPaymentCancelScResponse.getPrchsId());
		res.setResultYn(autoPaymentCancelScResponse.getResultYn());

		return res;
	}

}
