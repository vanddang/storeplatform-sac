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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.purchase.client.history.vo.PaymentRequest;
import com.skplanet.storeplatform.purchase.client.history.vo.PaymentResponse;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.PaymentListRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.PaymentReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.PaymentRes;
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
	 * @param paymentReq
	 *            요청정보
	 * @param requestHeader
	 *            헤더정보
	 * @return PaymentListRes 응답정보
	 */
	@RequestMapping(value = "/history/payment/search/v1", method = RequestMethod.POST)
	@ResponseBody
	public PaymentListRes searchPayment(@RequestBody PaymentReq paymentReq, SacRequestHeader requestHeader) {
		TenantHeader header = requestHeader.getTenantHeader();
		PaymentListRes paymentListRes = new PaymentListRes();
		List<PaymentResponse> paymentResponse = new ArrayList<PaymentResponse>();

		if (header.getTenantId() == null || header.getTenantId() == "") {
			throw new StorePlatformException("SAC_PUR_0001", "TenantId");
		}
		if (paymentReq.getInsdUsermbrNo() == null || paymentReq.getInsdUsermbrNo() == "") {
			throw new StorePlatformException("SAC_PUR_0001", "InsdUsermbrNo");
		}
		if (paymentReq.getPrchsId() == null || paymentReq.getPrchsId() == "") {
			throw new StorePlatformException("SAC_PUR_0001", "PrchsId");
		}

		paymentResponse = this.paymentSearchSacService.searchPayment(this.reqConvert(paymentReq, header));
		paymentListRes.setPaymentListRes(this.resConvert(paymentResponse));
		return paymentListRes;
	}

	/**
	 * reqConvert.
	 * 
	 * @param paymentReq
	 *            요청정보
	 * @param header
	 *            테넌트 헤더정보
	 * @return PaymentRequest
	 */
	private PaymentRequest reqConvert(PaymentReq paymentReq, TenantHeader header) {

		this.logger.debug("@@@@@@reqConvert@@@@@@@");
		PaymentRequest req = new PaymentRequest();

		req.setTenantId(header.getTenantId());
		req.setInsdUsermbrNo(paymentReq.getInsdUsermbrNo());
		req.setInsdDeviceId(paymentReq.getInsdDeviceId());
		req.setPrchsId(paymentReq.getPrchsId());

		return req;
	}

	/**
	 * resConvert.
	 * 
	 * @param paymentResponse
	 *            요청정보
	 * @return List<PaymentRes>
	 */
	private List<PaymentRes> resConvert(List<PaymentResponse> paymentResponse) {
		List<PaymentRes> res = new ArrayList<PaymentRes>();
		this.logger.debug("@@@@@@resConvert@@@@@@@");
		int size = paymentResponse.size();
		for (int i = 0; i < size; i++) {
			PaymentRes paymentRes = new PaymentRes();
			paymentRes.setPaymentMtdCd(paymentResponse.get(i).getPaymentMtdCd());
			paymentRes.setPaymentDt(paymentResponse.get(i).getPaymentDt());
			paymentRes.setPaymentAmt(paymentResponse.get(i).getPaymentAmt());
			paymentRes.setResvCol01(paymentResponse.get(i).getResvCol01());
			paymentRes.setResvCol02(paymentResponse.get(i).getResvCol02());
			paymentRes.setResvCol03(paymentResponse.get(i).getResvCol03());
			paymentRes.setResvCol04(paymentResponse.get(i).getResvCol04());
			paymentRes.setResvCol05(paymentResponse.get(i).getResvCol05());

			res.add(paymentRes);
		}

		return res;
	}
}
