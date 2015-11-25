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

import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreatePurchaseSacReq;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseOrderInfo;

/**
 * 
 * 구매 적합성 체크 서비스 인터페이스
 * 
 * Updated on : 2014. 1. 22. Updated by : 이승택, nTels.
 */
public interface PurchaseOrderValidationService {

	/**
	 * 
	 * <pre>
	 * 구매요청 파라미터 적합성 체크.
	 * </pre>
	 * 
	 * @param req
	 *            구매 주문 정보
	 */
	public void validatePurchaseRequestParameter(CreatePurchaseSacReq req);

	/**
	 * 
	 * <pre>
	 * 비과금 구매요청 권한 체크.
	 * </pre>
	 * 
	 * @param prchsReqPathCd
	 *            구매 요청 경로 코드
	 */
	public void validateFreeChargeAuth(String prchsReqPathCd);

	/**
	 * 
	 * <pre>
	 * Biz 쿠폰 발급 요청 권한 체크.
	 * </pre>
	 * 
	 * @param prchsReqPathCd
	 *            구매 요청 경로 코드
	 */
	public void validateBizAuth(String prchsReqPathCd);

	/**
	 * 
	 * <pre>
	 * 회원 적합성 체크.
	 * </pre>
	 * 
	 * @param purchaseOrderInfo
	 *            구매 주문 정보
	 */
	public void validateMember(PurchaseOrderInfo purchaseOrderInfo);

	/**
	 * 
	 * <pre>
	 * 상품 적합성 체크.
	 * </pre>
	 * 
	 * @param purchaseOrderInfo
	 *            구매 주문 정보
	 */
	public void validateProduct(PurchaseOrderInfo purchaseOrderInfo);

	/**
	 * 
	 * <pre>
	 * 구매 적합성 체크: 상품&회원 결합 체크, 기구매체크, 쇼핑쿠폰 발급 가능여부 체크 등.
	 * </pre>
	 * 
	 * @param purchaseOrderInfo
	 *            구매 주문 정보
	 */
	public void validatePurchase(PurchaseOrderInfo purchaseOrderInfo);

}
