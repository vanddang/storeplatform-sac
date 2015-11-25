/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.history.service;

import java.util.List;

import com.skplanet.storeplatform.purchase.client.history.vo.PaymentListScRes;
import com.skplanet.storeplatform.purchase.client.history.vo.PaymentScReq;

/**
 * 구매내역 Interface
 * 
 * Updated on : 2014-01-21 Updated by : 조용진, 엔텔스.
 */
public interface PaymentSearchService {

	/**
	 * 
	 * <pre>
	 * 결제내역조회.
	 * </pre>
	 * 
	 * @param paymentScReq
	 *            결제내역 조회조건
	 * @return List<PaymentListScRes> 결제내역 조회결과
	 */
	public List<PaymentListScRes> searchPaymentList(PaymentScReq paymentScReq);
}
