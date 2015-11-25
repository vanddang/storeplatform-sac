/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.promotion.sci;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.purchase.client.common.vo.PaymentPromotion;
import com.skplanet.storeplatform.purchase.client.promotion.sci.PurchasePromotionSCI;
import com.skplanet.storeplatform.purchase.promotion.service.PurchasePromotionService;

@LocalSCI
public class PurchasePromotionSCIController implements PurchasePromotionSCI {
	@Autowired
	private PurchasePromotionService purchasePromotionService;

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
	@Override
	public List<PaymentPromotion> searchPaymentPromotionList(String tenantId) {
		return this.purchasePromotionService.searchPaymentPromotionList(tenantId);
	}
}
