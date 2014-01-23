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

import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseOrder;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseOrderResult;

/**
 * 
 * 구매 제한정책 체크 서비스
 * 
 * Updated on : 2014. 1. 22. Updated by : 이승택, nTels.
 */
public interface PurchaseOrderPolicyService {

	/**
	 * 
	 * <pre>
	 * 제한정책 체크.
	 * </pre>
	 * 
	 * @param purchaseOrderInfo
	 *            구매주문 정보
	 * @return 제한정책 체크 결과 정보: null-정상, not null-체크 오류 결과 정보
	 */
	public PurchaseOrderResult checkPolicy(PurchaseOrder purchaseOrderInfo);
}
