/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.order.sci;

import com.skplanet.storeplatform.framework.core.proxy.SCI;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchPurchaseListByStatusScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchPurchaseListByStatusScRes;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchPurchaseSequenceAndDateRes;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchShoppingSpecialCountScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchShoppingSpecialCountScRes;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchSktPaymentScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchSktPaymentScRes;

/**
 * 
 * 구매SC - 구매 (조회) 연동 인터페이스
 * 
 * Updated on : 2014. 1. 20. Updated by : 이승택, nTels.
 */
@SCI
public interface PurchaseOrderSearchSCI {

	/**
	 * 
	 * <pre>
	 * 구매ID 생성을 위한 (Next) 시퀀스 값 및 현재일시 조회.
	 * </pre>
	 * 
	 * @return (Next) 시퀀스 값 및 현재일시
	 */
	public SearchPurchaseSequenceAndDateRes searchPurchaseSequenceAndDate();

	/**
	 * 
	 * <pre>
	 * SKT 후불 결제 제한 조건구분값 조회.
	 * </pre>
	 * 
	 * @param req
	 *            SKT 후불 결제 제한 조건구분값 조회 요청 정보
	 * @return SKT 후불 결제 제한 조건구분값 조회 결과
	 */
	public SearchSktPaymentScRes searchSktLimitCondDetail(SearchSktPaymentScReq req);

	/**
	 * 
	 * <pre>
	 * SKT 후불 결제 금액 조회.
	 * </pre>
	 * 
	 * @param req
	 *            SKT 후불 결제 금액 조회 요청 정보
	 * @return SKT 후불 결제 금액 조회 결과
	 */
	public SearchSktPaymentScRes searchSktAmountDetail(SearchSktPaymentScReq req);

	/**
	 * 
	 * <pre>
	 * SKT 후불 선물수신 금액 조회.
	 * </pre>
	 * 
	 * @param req
	 *            SKT 후불 선물수신 금액 조회 요청 정보
	 * @return SKT 후불 선물수신 금액 조회 결과
	 */
	public SearchSktPaymentScRes searchSktRecvAmountDetail(SearchSktPaymentScReq req);

	/**
	 * 
	 * <pre>
	 * 쇼핑 특가상품 구매된 건수 조회.
	 * </pre>
	 * 
	 * @param req
	 *            쇼핑 특가상품 구매된 건수 조회 요청 정보
	 * @return 쇼핑 특가상품 구매된 건수 조회 결과
	 */
	public SearchShoppingSpecialCountScRes searchShoppingSpecialCount(SearchShoppingSpecialCountScReq req);

	// =====

	/**
	 * 
	 * <pre>
	 * 구매상태 기준의 구매정보 목록 조회.
	 * </pre>
	 * 
	 * @param req
	 *            조회정보
	 * @return 요청한 구매상태 기준의 구매정보 목록
	 */
	public SearchPurchaseListByStatusScRes searchPurchaseListByStatus(SearchPurchaseListByStatusScReq req);
}
