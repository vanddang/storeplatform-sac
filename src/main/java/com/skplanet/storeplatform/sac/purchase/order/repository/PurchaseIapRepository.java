/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.order.repository;

/**
 * 
 * IAP Repository
 * 
 * Updated on : 2014. 7. 21. Updated by : 이승택, nTels.
 */
public interface PurchaseIapRepository {

	/**
	 * 
	 * <pre>
	 * IAP SKT후불 결제 금액 조회.
	 * </pre>
	 * 
	 * @param svcMangNo
	 *            조회할 SKT서비스관리번호
	 * @param queryMonth
	 *            조회할 월 (YYYYMM)
	 * @return IAP SKT후불 결제 금액
	 */
	public int inquiryBillingAmt(String svcMangNo, String queryMonth);
}
