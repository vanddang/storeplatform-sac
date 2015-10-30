/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.promotion.sci;

import java.util.List;

import com.skplanet.storeplatform.framework.core.proxy.SCI;
import com.skplanet.storeplatform.purchase.client.common.vo.PaymentPromotion;

/**
 * 
 * 결제 프로모션 인터페이스
 * 
 * Updated on : 2015. 3. 2. Updated by : 이승택, nTels.
 */
@SCI
public interface PurchasePromotionSCI {

	/**
	 * 
	 * <pre>
	 * 현재시각에 유효한 결제수단 프로모션 조회.
	 * </pre>
	 * 
	 * @param tenantId
	 *            테넌트 ID
	 * @return 결제수단 프로모션 목록
	 */
	public List<PaymentPromotion> searchPaymentPromotionList(String tenantId);
}
