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

import com.skplanet.storeplatform.purchase.client.history.vo.AutoPaymentCancelScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.AutoPaymentCancelScRes;

/**
 * 구매 서비스 인터페이스
 * 
 * Updated on : 2013-12-06 Updated by : 조용진, 엔텔스.
 */
public interface AutoPaymentCancelScService {

	/**
	 * 자동결제 해지예약/해지예약취소/해지 요청.
	 * 
	 * @param autoPaymentCancelScReq
	 *            요청정보
	 * @return AutoPaymentCancelScRes
	 */
	public AutoPaymentCancelScRes updateReservation(AutoPaymentCancelScReq autoPaymentCancelScReq);

}
