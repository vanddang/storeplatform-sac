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

import com.skplanet.storeplatform.purchase.client.history.vo.AutoPaymentCancelScRequest;
import com.skplanet.storeplatform.purchase.client.history.vo.AutoPaymentCancelScResponse;

/**
 * 자동결재해지예약/예약취소/해지 SAC Service 인터페이스
 * 
 * Updated on : 2013-09-01 Updated by :조용진, 엔텔스.
 */
public interface AutoPaymentCancelSacService {

	/**
	 * 자동결재해지예약/예약취소/해지 SAC Service.
	 * 
	 * @param autoPaymentCancelScRequest
	 *            요청정보
	 * @return AutoPaymentCancelScResponse
	 */
	public AutoPaymentCancelScResponse updateReservation(AutoPaymentCancelScRequest autoPaymentCancelScRequest);
}
