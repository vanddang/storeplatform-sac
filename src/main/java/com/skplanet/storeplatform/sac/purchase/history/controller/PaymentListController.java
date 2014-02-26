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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.skplanet.storeplatform.purchase.client.history.vo.PaymentListScRes;
import com.skplanet.storeplatform.purchase.client.history.vo.PaymentScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.PaymentScRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.PaymentListSacRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.PaymentSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.PaymentSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.purchase.common.util.PurchaseCommonUtils;
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
	@Autowired
	private PurchaseCommonUtils purchaseCommonUtils;

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
	public Map<String, List<PaymentListSacRes>> searchPaymentList(@RequestBody @Validated PaymentSacReq paymentSacReq,
			BindingResult bindingResult, SacRequestHeader requestHeader) {
		// 필수값 체크
		this.purchaseCommonUtils.getBindingValid(bindingResult);

		TenantHeader header = requestHeader.getTenantHeader();

		Map<String, List<PaymentListSacRes>> res = new HashMap<String, List<PaymentListSacRes>>();
		res.put("paymentList",
				this.resConvert(this.paymentSearchSacService.searchPaymentList(this.reqConvert(paymentSacReq, header))));
		return res;
	}

	/**
	 * reqConvert.
	 * 
	 * @param paymentSacReq
	 *            요청정보
	 * @param header
	 *            테넌트 헤더정보
	 * @return PaymentScReq
	 */
	private PaymentScReq reqConvert(PaymentSacReq paymentSacReq, TenantHeader header) {

		this.logger.debug("@@@@@@reqConvert@@@@@@@");
		PaymentScReq req = new PaymentScReq();

		req.setTenantId(header.getTenantId());

		req.setPrchsId(paymentSacReq.getPrchsIdList());

		return req;
	}

	/**
	 * resConvert.
	 * 
	 * @param paymentListScRes
	 *            요청정보
	 * @return List<PaymentListSacRes>
	 */
	private List<PaymentListSacRes> resConvert(List<PaymentListScRes> paymentListScRes) {
		this.logger.debug("@@@@@@resConvert@@@@@@@");
		// 리턴리스트
		List<PaymentListSacRes> resList = new ArrayList<PaymentListSacRes>();

		for (PaymentListScRes list : paymentListScRes) {

			// 결재내역
			PaymentListSacRes paymentListSacRes = new PaymentListSacRes();
			// 결내역리스트
			List<PaymentSacRes> paymentList = new ArrayList<PaymentSacRes>();
			// 구매ID셋팅
			paymentListSacRes.setPrchsId(list.getPrchsId());

			for (PaymentScRes paymentScRes : list.getPaymentList()) {

				PaymentSacRes paymentSacRes = new PaymentSacRes();
				paymentSacRes.setPaymentAmt(paymentScRes.getPaymentAmt());
				paymentSacRes.setPaymentMtdCd(paymentScRes.getPaymentMtdCd());
				paymentList.add(paymentSacRes);

			}
			paymentListSacRes.setPaymentList(paymentList);

			resList.add(paymentListSacRes);
		}

		return resList;
	}
}
