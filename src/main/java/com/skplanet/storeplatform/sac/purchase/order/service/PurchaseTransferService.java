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

import com.skplanet.storeplatform.sac.client.purchase.vo.order.PurchaseTransferSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.PurchaseTransferSacRes;

/**
 * 
 * SAP 구매내역 이관 서비스 인터페이스
 * 
 * Updated on : 2015. 2. 4. Updated by : 양주원, nTels.
 */
public interface PurchaseTransferService {

	/**
	 * 
	 * <pre>
	 * 구매내역 이관 처리.
	 * </pre>
	 * 
	 * @param request
	 *            구매이관요청 정보
	 * @return CreatePurchaseSacRes
	 */
	public PurchaseTransferSacRes createPurchaseTransfer(PurchaseTransferSacReq request);

}
