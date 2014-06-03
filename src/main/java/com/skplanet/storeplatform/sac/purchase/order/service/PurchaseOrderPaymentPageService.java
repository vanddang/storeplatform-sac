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

import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseOrderInfo;

/**
 * 
 * 결제Page 서비스
 * 
 * Updated on : 2014. 6. 2. Updated by : 이승택, nTels.
 */
public interface PurchaseOrderPaymentPageService {
	/**
	 * 
	 * <pre>
	 * 유료구매 - 결제Page 파라미터 세팅.
	 * </pre>
	 * 
	 * @param purchaseOrderInfo
	 *            구매요청 정보
	 */
	public void buildPaymentPageUrlParam(PurchaseOrderInfo purchaseOrderInfo);
}
