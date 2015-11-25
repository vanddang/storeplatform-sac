/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.order.service;

import java.util.List;

import com.skplanet.storeplatform.purchase.client.order.vo.PrchsDtlMore;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchPurchaseSequenceAndDateRes;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchShoppingSpecialCountScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchSktPaymentScReq;

/**
 * 
 * 구매SC - 구매 (조회) 서비스 인터페이스
 * 
 * Updated on : 2014. 1. 20. Updated by : 이승택, nTels.
 */
public interface PurchaseOrderSearchSCService {

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
	 * SKT 후불 결제 제한 조건구분값 조회 (구매상세 테넌트상품그룹코드 조건).
	 * </pre>
	 * 
	 * @param req
	 *            SKT 후불 결제 제한 조건구분값 조회 요청 정보
	 * @return SKT 후불 결제 제한 조건구분값 조회 결과
	 */
	public Object searchSktLimitCondDetail(SearchSktPaymentScReq req);

	/**
	 * 
	 * <pre>
	 * SKT 후불 결제 제한 조건구분값 조회 (결제내역 기준).
	 * </pre>
	 * 
	 * @param req
	 *            SKT 후불 결제 제한 조건구분값 조회 요청 정보
	 * @return SKT 후불 결제 제한 조건구분값 조회 결과
	 */
	public Object searchSktLimitCondOnPaymentDetail(SearchSktPaymentScReq req);

	/**
	 * 
	 * <pre>
	 * SKT 후불 결제 금액 조회 (구매상세 테넌트상품그룹코드 조건).
	 * </pre>
	 * 
	 * @param req
	 *            SKT 후불 결제 금액 조회 요청 정보
	 * @return SKT 후불 결제 금액 조회 결과
	 */
	public Object searchSktAmountDetail(SearchSktPaymentScReq req);

	/**
	 * 
	 * <pre>
	 * SKT 후불 결제 금액 조회 (결제내역 기준).
	 * </pre>
	 * 
	 * @param req
	 *            SKT 후불 결제 금액 조회 요청 정보
	 * @return SKT 후불 결제 금액 조회 결과
	 */
	public Object searchSktAmountOnPaymentDetail(SearchSktPaymentScReq req);

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
	public Object searchSktRecvAmountDetail(SearchSktPaymentScReq req);

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
	public List<Integer> searchShoppingSpecialCount(SearchShoppingSpecialCountScReq req);

	// ==============================

	/**
	 * 
	 * <pre>
	 * 구매상세 목록 조회.
	 * </pre>
	 * 
	 * @param tenantId
	 *            테넌트 ID
	 * @param prchsId
	 *            구매 ID
	 * @param useTenantId
	 *            이용자 테넌트 ID
	 * @param useInsdUsermbrNo
	 *            이용자 회원 내부관리 번호
	 * @param statusCd
	 *            조회할 구매상태코드
	 * @return 구매상세 목록
	 */
	public List<PrchsDtlMore> searchPurchaseDtlListByStatus(String tenantId, String prchsId, String useTenantId,
			String useInsdUsermbrNo, String statusCd);
}
