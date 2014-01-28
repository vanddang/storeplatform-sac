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
import com.skplanet.storeplatform.purchase.client.history.vo.AutoPaymentCancelRequest;
import com.skplanet.storeplatform.purchase.client.history.vo.AutoPaymentCancelResponse;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.AutoPaymentCancelReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.AutoPaymentCancelRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
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
	 *            요청정보
	 * @param requestHeader
	 *            헤더정보
	 * @return AutoPaymentCancelRes 응답값
	 */
	@RequestMapping(value = "/history/reservation/modify/v1", method = RequestMethod.POST)
	public @ResponseBody
	AutoPaymentCancelRes modifyReservation(SacRequestHeader requestHeader,
			@RequestBody @Validated AutoPaymentCancelReq autoPaymentCancelReq, BindingResult bindingResult)
			throws Exception {

		TenantHeader header = requestHeader.getTenantHeader();
		// 필수값 체크
		if (bindingResult.hasErrors()) {
			List<FieldError> errors = bindingResult.getFieldErrors();
			for (FieldError error : errors) {
				throw new StorePlatformException("SAC_PUR_0001", error.getField());
			}
		}

		AutoPaymentCancelRequest rea = this.reqConvert(autoPaymentCancelReq, header);
		AutoPaymentCancelResponse autoPaymentCancelResponse = new AutoPaymentCancelResponse();

		autoPaymentCancelResponse = this.autoPaymentCancelService.modifyReservation(rea);
		AutoPaymentCancelRes autoPaymentCancelRes = this.resConvert(autoPaymentCancelResponse);
		return autoPaymentCancelRes;
	}

	/**
	 * reqConvert.
	 * 
	 * @param autoPaymentCancelReq
	 *            요청정보
	 * @param header
	 *            테넌트 헤더정보
	 * @return AutoPaymentCancelRequest
	 */
	private AutoPaymentCancelRequest reqConvert(AutoPaymentCancelReq autoPaymentCancelReq, TenantHeader header) {

		AutoPaymentCancelRequest req = new AutoPaymentCancelRequest();

		req.setTenantId(header.getTenantId());
		req.setSystemId(header.getSystemId());
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
	 *            요청정보
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
