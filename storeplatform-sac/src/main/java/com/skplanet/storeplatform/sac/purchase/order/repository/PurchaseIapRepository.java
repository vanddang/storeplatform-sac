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
	 * @param mdn
	 *            조회할 MDN
	 * @param svcMangNo
	 *            조회할 SKT서비스관리번호
	 * @param queryMonth
	 *            조회할 월 (YYYYMM)
	 * @return IAP SKT후불 결제 금액
	 */
	public int inquiryBillingAmt(String mdn, String svcMangNo, String queryMonth);

	/**
	 * 
	 * <pre>
	 * IAP S2S 상품 가격 조회.
	 * </pre>
	 * 
	 * @param url
	 *            BP사 서버 URL
	 * @param reqTime
	 *            요청시간
	 * @param aid
	 *            App ID
	 * @param prodId
	 *            IAP 상품 ID
	 * @param tid
	 *            TID
	 * @return IAP S2S 상품 가격
	 */
	public Double searchIapS2SPrice(String url, String reqTime, String aid, String prodId, String tid);
}
