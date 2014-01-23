/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.cancel.repository;

import com.skplanet.storeplatform.sac.purchase.cancel.vo.PurchaseCancelParamDetail;
import com.skplanet.storeplatform.sac.purchase.common.vo.PurchaseCommonParam;

/**
 * 구매 취소 repository Interface.
 * 
 * Updated on : 2014. 1. 16. Updated by : nTels_cswoo81, nTels.
 */
public interface PurchaseCancelRepository {

	/**
	 * <pre>
	 * 구매 취소 전 구매상세 내역 조회.
	 * </pre>
	 * 
	 * @param purchaseCommonParam
	 *            구매 취소 요청 공통 VO.
	 * @param purchaseCancelParamDetail
	 *            구매 취소 요청 VO.
	 * @return PurchaseCancelParamDetail
	 */
	public PurchaseCancelParamDetail getPurchaseDtlHistoryList(PurchaseCommonParam purchaseCommonParam,
			PurchaseCancelParamDetail purchaseCancelParamDetail);

	/**
	 * <pre>
	 * 구매 취소 DB UPDATE.
	 * </pre>
	 * 
	 * @param purchaseCommonParam
	 *            구매 취소 요청 공통 VO.
	 * @param purchaseCancelParamDetail
	 *            구매 취소 요청 VO.
	 * @return PurchaseCancelParamDetail
	 */
	public PurchaseCancelParamDetail cancelPurchase(PurchaseCommonParam purchaseCommonParam,
			PurchaseCancelParamDetail purchaseCancelParamDetail);

	/**
	 * 
	 * <pre>
	 * 구매 취소 후 라이센스 삭제 요청.
	 * </pre>
	 * 
	 * @param purchaseCommonParam
	 *            구매 취소 요청 공통 VO.
	 * @param purchaseCancelParamDetail
	 *            구매 취소 요청 VO.
	 * @return PurchaseCancelParamDetail
	 * @return PurchaseCancelParamDetail
	 */
	public PurchaseCancelParamDetail removeLicense(PurchaseCommonParam purchaseCommonParam,
			PurchaseCancelParamDetail purchaseCancelParamDetail);

}
