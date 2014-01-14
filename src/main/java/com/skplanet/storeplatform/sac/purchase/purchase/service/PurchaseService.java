package com.skplanet.storeplatform.sac.purchase.purchase.service;

import com.skplanet.storeplatform.sac.purchase.purchase.vo.PrePurchaseInfo;

/**
 * 
 * 구매 서비스 인터페이스
 * 
 * Updated on : 2014. 1. 3. Updated by : 이승택, nTels.
 */
public interface PurchaseService {

	/**
	 * 
	 * <pre>
	 * 구매 전처리.
	 * </pre>
	 * 
	 * @param purchaseInfo
	 *            구매요청 정보
	 */
	public void checkPurchase(PrePurchaseInfo purchaseInfo);

	/**
	 * 
	 * <pre>
	 * 무료구매 처리.
	 * </pre>
	 * 
	 * @param purchaseInfo
	 *            구매요청 정보
	 */
	public void freePurchase(PrePurchaseInfo purchaseInfo);

	/**
	 * 
	 * <pre>
	 * 유료구매 - 결제Page 준비작업.
	 * </pre>
	 * 
	 * @param purchaseInfo
	 *            구매요청 정보
	 */
	public void setPaymentPageInfo(PrePurchaseInfo purchaseInfo);
}
