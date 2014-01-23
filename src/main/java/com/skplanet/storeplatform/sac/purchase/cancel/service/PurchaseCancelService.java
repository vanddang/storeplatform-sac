/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.cancel.service;

import com.skplanet.storeplatform.sac.purchase.cancel.vo.PurchaseCancelParam;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PurchaseCancelParamDetail;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PurchaseCancelResult;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PurchaseCancelResultDetail;
import com.skplanet.storeplatform.sac.purchase.common.vo.PurchaseCommonParam;

/**
 * 구매 취소 Service Interface.
 * 
 * Updated on : 2014. 1. 16. Updated by : nTels_cswoo81, nTels.
 */
public interface PurchaseCancelService {

	/**
	 * <pre>
	 * 구매 리스트 취소 요청.
	 * </pre>
	 * 
	 * @param purchaseCancelParam
	 *            구매 취소 요청 LIST VO.
	 * @return PurchaseCancelResult
	 */
	public PurchaseCancelResult cancelPurchaseList(PurchaseCancelParam purchaseCancelParam);

	/**
	 * <pre>
	 * 구매 취소 요청 처리.
	 * </pre>
	 * 
	 * @param purchaseCommonParam
	 *            구매 취소 요청 공통 VO.
	 * @param purchaseCancelParamDetail
	 *            구매 취소 요청 VO.
	 * @return PurchaseCancelResultDetail
	 */
	public PurchaseCancelResultDetail cancelPurchaseItem(PurchaseCommonParam purchaseCommonParam,
			PurchaseCancelParamDetail purchaseCancelParamDetail);

}
