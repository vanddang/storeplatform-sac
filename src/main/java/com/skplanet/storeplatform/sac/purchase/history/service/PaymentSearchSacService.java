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

import com.skplanet.storeplatform.purchase.client.history.vo.PaymentScRequest;
import com.skplanet.storeplatform.purchase.client.history.vo.PaymentScResponse;

/**
 * 구매 서비스 인터페이스
 * 
 * Updated on : 2013-12-06 Updated by : 조용진, 엔텔스.
 */
public interface PaymentSearchSacService {

	/**
	 * 결제내역 조회.
	 * 
	 * @param paymentScRequest
	 *            요청정보
	 * @return List<PaymentScResponse>
	 */
	public List<PaymentScResponse> searchPaymentList(PaymentScRequest paymentScRequest);
}
