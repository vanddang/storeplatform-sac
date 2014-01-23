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
 * 구매 적합성 체크 서비스 인터페이스
 * 
 * Updated on : 2014. 1. 22. Updated by : 이승택, nTels.
 */
public interface PurchaseOrderValidationService {

	/**
	 * 
	 * <pre>
	 * 회원 적합성 체크.
	 * </pre>
	 * 
	 * @param purchaseOrderInfo
	 *            구매 주문 정보
	 * @return 회원 적합성 체크 결과 정보: null-정상, not null-체크 오류 결과 정보
	 */
	public PurchaseOrderResult validateMember(PurchaseOrder purchaseOrderInfo);

	/**
	 * 
	 * <pre>
	 * 상품 적합성 체크.
	 * </pre>
	 * 
	 * @param purchaseOrderInfo
	 *            구매 주문 정보
	 * @return 상품 적합성 체크 결과 정보: null-정상, not null-체크 오류 결과 정보
	 */
	public PurchaseOrderResult validateProduct(PurchaseOrder purchaseOrderInfo);

	/**
	 * 
	 * <pre>
	 * 구매 적합성 체크.
	 * </pre>
	 * 
	 * @param purchaseOrderInfo
	 *            구매 주문 정보
	 * @return 구매 적합성 체크 결과 정보: null-정상, not null-체크 오류 결과 정보
	 */
	public PurchaseOrderResult validatePurchase(PurchaseOrder purchaseOrderInfo);

}
