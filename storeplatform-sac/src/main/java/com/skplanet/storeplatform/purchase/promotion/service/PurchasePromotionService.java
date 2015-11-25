package com.skplanet.storeplatform.purchase.promotion.service;

import java.util.List;

import com.skplanet.storeplatform.purchase.client.common.vo.PaymentPromotion;

public interface PurchasePromotionService {

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
