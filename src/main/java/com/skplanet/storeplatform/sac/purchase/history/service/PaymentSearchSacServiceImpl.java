/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.history.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.purchase.client.history.sci.PaymentListSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.PaymentRequest;
import com.skplanet.storeplatform.purchase.client.history.vo.PaymentResponse;

/**
 * 선물확인 SAC Service 인터페이스 구현체
 * 
 * Updated on : 2014-01-21 Updated by :조용진, 엔텔스.
 */
@Service
@Transactional
public class PaymentSearchSacServiceImpl implements PaymentSearchSacService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PaymentListSCI paymentListSCI;

	/**
	 * 결제내역 조회.
	 * 
	 * @param paymentRequest
	 *            결제내역 조회 조건
	 * @return List<PaymentResponse>
	 */
	@Override
	public List<PaymentResponse> searchPayment(PaymentRequest paymentRequest) {

		return this.paymentListSCI.searchPayment(paymentRequest);
	}

}
