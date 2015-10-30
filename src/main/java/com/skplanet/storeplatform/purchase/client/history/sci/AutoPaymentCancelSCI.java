/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.history.sci;

import com.skplanet.storeplatform.framework.core.proxy.SCI;
import com.skplanet.storeplatform.purchase.client.history.vo.AutoPaymentCancelScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.AutoPaymentCancelScRes;

/**
 * 구매 컴포넌트 인터페이스
 * 
 * Updated on : 2014-01-15 Updated by : 조용진, 엔텔스.
 */
@SCI
public interface AutoPaymentCancelSCI {

	/**
	 * 자동결제 해지예약/해지예약취소/해지 요청.
	 * 
	 * @param autoPaymentCancelScRequest
	 *            자동결제 해지예약/해지예약취소/해지 요청.
	 * @return AutoPaymentCancelScResponse
	 */
	public AutoPaymentCancelScRes updateReservation(AutoPaymentCancelScReq autoPaymentCancelScRequest);
}
