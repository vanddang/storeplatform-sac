/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.order.service;

import com.skplanet.storeplatform.sac.client.purchase.vo.order.NotifyPaymentSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.PPNotifyPaymentSacReq;

/**
 * 구매인증 요청, 결제처리 결과 알림 처리 서비스 인터페이스 PayPlanet -> SAC 호출
 *
 * Updated on : 15. 4. 8. Updated by : 황민규, SK 플래닛.
 */
public interface PayPlanetService {

	/**
	 * 
	 * <pre>
	 * 결제 처리 결과 Noti.
	 * </pre>
	 * 
	 * @param ppNotifyPaymentSacReq
	 *            결제 결과 정보
	 * @param tenantId
	 *            테넌트 ID
	 * @return 결제 결과 처리 응답
	 */

	public NotifyPaymentSacReq notifyPayment(String tenantId, PPNotifyPaymentSacReq ppNotifyPaymentSacReq);
}
