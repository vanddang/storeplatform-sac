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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.purchase.client.common.vo.PrchsDtl;
import com.skplanet.storeplatform.purchase.client.order.vo.PrchsDtlMore;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchPurchaseSequenceAndDateRes;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchShoppingSpecialCountScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchSktPaymentScReq;

/**
 * 
 * 구매SC - 구매 (조회) 서비스 구현체
 * 
 * Updated on : 2014. 1. 20. Updated by : 이승택, nTels.
 */
@Service
public class PurchaseOrderSearchSCServiceImpl implements PurchaseOrderSearchSCService {

	@Autowired
	@Qualifier("scPurchase")
	private CommonDAO commonDAO;

	/**
	 * 
	 * <pre>
	 * 구매ID 생성을 위한 (Next) 시퀀스 값 및 현재일시 조회.
	 * </pre>
	 * 
	 * @return (Next) 시퀀스 값 및 현재일시
	 */
	@Override
	public SearchPurchaseSequenceAndDateRes searchPurchaseSequenceAndDate() {
		return this.commonDAO.queryForObject("PurchaseOrderSearch.searchPurchaseSequenceAndDate", null,
				SearchPurchaseSequenceAndDateRes.class);
	}

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
	@Override
	public Object searchSktLimitCondDetail(SearchSktPaymentScReq req) {
		// return this.commonDAO.queryForObject("PurchaseOrderSearch.searchSktLimitCondDetail", req, Object.class);
		return this.commonDAO.queryForObject("PurchaseOrderSearch.searchMonthlyPrchsCntDetail", req, Object.class);
	}

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
	@Override
	public Object searchSktLimitCondOnPaymentDetail(SearchSktPaymentScReq req) {
		return this.commonDAO
				.queryForObject("PurchaseOrderSearch.searchSktLimitCondOnPaymentDetail", req, Object.class);
	}

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
	@Override
	public Object searchSktAmountDetail(SearchSktPaymentScReq req) {
		return this.commonDAO.queryForObject("PurchaseOrderSearch.searchSktAmountDetail", req, Object.class);
	}

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
	@Override
	public Object searchSktAmountOnPaymentDetail(SearchSktPaymentScReq req) {
		return this.commonDAO.queryForObject("PurchaseOrderSearch.searchSktAmountOnPaymentDetail", req, Object.class);
	}

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
	@Override
	public Object searchSktRecvAmountDetail(SearchSktPaymentScReq req) {
		return this.commonDAO.queryForObject("PurchaseOrderSearch.searchSktRecvAmountDetail", req, Object.class);
	}

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
	@Override
	public List<Integer> searchShoppingSpecialCount(SearchShoppingSpecialCountScReq req) {
		return this.commonDAO
				.queryForList("PurchaseOrderSearch.searchShoppingSpecialPurchaseCount", req, Integer.class);
	}

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
	 *            변경할 구매상태코드
	 * @return 구매상세 목록
	 */
	@Override
	public List<PrchsDtlMore> searchPurchaseDtlListByStatus(String tenantId, String prchsId, String useTenantId,
			String useInsdUsermbrNo, String statusCd) {
		PrchsDtl prchsDtl = new PrchsDtl();
		prchsDtl.setTenantId(tenantId);
		prchsDtl.setPrchsId(prchsId);
		prchsDtl.setStatusCd(statusCd);

		// 구매DB 파티션
		prchsDtl.setUseTenantId(useTenantId);
		prchsDtl.setUseInsdUsermbrNo(useInsdUsermbrNo);

		return this.commonDAO.queryForList("PurchaseOrderSearch.searchPurchaseDtlListByStatus", prchsDtl,
				PrchsDtlMore.class);
	}
}
