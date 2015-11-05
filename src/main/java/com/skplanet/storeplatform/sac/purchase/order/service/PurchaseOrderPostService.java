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

import com.skplanet.storeplatform.purchase.client.order.vo.PrchsDtlMore;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.NotifyPaymentSacReq;

import java.util.List;
import java.util.Map;

/**
 * 
 * 구매 후처리 서비스
 * 
 * Updated on : 2014. 5. 28. Updated by : 이승택, nTels.
 */
public interface PurchaseOrderPostService {

	/**
	 * 
	 * <pre>
	 * 구매 후처리( 인터파크/씨네21, Tstore Noti).
	 * </pre>
	 * 
	 * @param prchsDtlMoreList
	 *            구매정보 목록
	 * @param notifyPaymentReq
	 *            결제처리결과 Noti 정보
	 */
	public void postPurchase(List<PrchsDtlMore> prchsDtlMoreList, NotifyPaymentSacReq notifyPaymentReq, Map<String, String> reservedDataMap);
}
