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

import java.util.ArrayList;
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
import com.skplanet.storeplatform.purchase.client.history.vo.PaymentScRequest;
import com.skplanet.storeplatform.purchase.client.history.vo.PaymentScResponse;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.PaymentListSacRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.PaymentSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.PaymentSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.purchase.history.service.PaymentSearchSacService;

/**
 * 구매 SAC 컨트롤러
 * 
 * Updated on : 2014-01-14 Updated by : 조용진, 엔텔스.
 */
@Controller
@RequestMapping(value = "/purchase")
public class PaymentListController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PaymentSearchSacService paymentSearchSacService;

	/**
	 * 결제내역 조회.
	 * 
	 * @param paymentSacReq
	 *            요청정보
	 * @param bindingResult
	 *            Validated Result
	 * @param requestHeader
	 *            헤더정보
	 * @return PaymentListSacRes 응답정보
	 */
	@RequestMapping(value = "/history/payment/search/v1", method = RequestMethod.POST)
	@ResponseBody
	public PaymentListSacRes searchPaymentList(@RequestBody @Validated PaymentSacReq paymentSacReq,
			BindingResult bindingResult, SacRequestHeader requestHeader) {
		// 필수값 체크
		if (bindingResult.hasErrors()) {
			List<FieldError> errors = bindingResult.getFieldErrors();
			for (FieldError error : errors) {
				throw new StorePlatformException("SAC_PUR_0001", error.getField());
			}
		}

		TenantHeader header = requestHeader.getTenantHeader();
		PaymentListSacRes paymentListSacRes = new PaymentListSacRes();
		List<PaymentScResponse> paymentListScResponse = new ArrayList<PaymentScResponse>();

		paymentListScResponse = this.paymentSearchSacService.searchPaymentList(this.reqConvert(paymentSacReq, header));
		paymentListSacRes.setPaymentListSacRes(this.resConvert(paymentListScResponse));
		return paymentListSacRes;
	}

	/**
	 * reqConvert.
	 * 
	 * @param paymentSacReq
	 *            요청정보
	 * @param header
	 *            테넌트 헤더정보
	 * @return PaymentScRequest
	 */
	private PaymentScRequest reqConvert(PaymentSacReq paymentSacReq, TenantHeader header) {

		this.logger.debug("@@@@@@reqConvert@@@@@@@");
		PaymentScRequest req = new PaymentScRequest();

		req.setTenantId(header.getTenantId());
		req.setInsdUsermbrNo(paymentSacReq.getInsdUsermbrNo());
		req.setInsdDeviceId(paymentSacReq.getInsdDeviceId());
		req.setPrchsId(paymentSacReq.getPrchsId());

		return req;
	}

	/**
	 * resConvert.
	 * 
	 * @param paymentListScResponse
	 *            요청정보
	 * @return List<PaymentSacRes>
	 */
	private List<PaymentSacRes> resConvert(List<PaymentScResponse> paymentListScResponse) {
		List<PaymentSacRes> res = new ArrayList<PaymentSacRes>();
		this.logger.debug("@@@@@@resConvert@@@@@@@");
		int size = paymentListScResponse.size();
		for (int i = 0; i < size; i++) {
			PaymentSacRes paymentSacRes = new PaymentSacRes();
			paymentSacRes.setPaymentMtdCd(paymentListScResponse.get(i).getPaymentMtdCd());
			paymentSacRes.setPaymentDt(paymentListScResponse.get(i).getPaymentDt());
			paymentSacRes.setPaymentAmt(paymentListScResponse.get(i).getPaymentAmt());
			paymentSacRes.setResvCol01(paymentListScResponse.get(i).getResvCol01());
			paymentSacRes.setResvCol02(paymentListScResponse.get(i).getResvCol02());
			paymentSacRes.setResvCol03(paymentListScResponse.get(i).getResvCol03());
			paymentSacRes.setResvCol04(paymentListScResponse.get(i).getResvCol04());
			paymentSacRes.setResvCol05(paymentListScResponse.get(i).getResvCol05());

			res.add(paymentSacRes);
		}

		return res;
	}
}
