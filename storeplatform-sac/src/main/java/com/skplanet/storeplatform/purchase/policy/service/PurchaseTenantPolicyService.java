package com.skplanet.storeplatform.purchase.policy.service;

import com.skplanet.storeplatform.purchase.client.common.vo.TenantSalePolicy;

import java.util.List;

public interface PurchaseTenantPolicyService {

	/**
	 * 
	 * <pre>
	 * 해당 테넌트의 구매Part 정책 중 특정 정책만 조회한다.
	 * </pre>
	 * 
	 * @param tenantId
	 *            정책을 조회할 대상 테넌트 ID
	 * @param tenantProdGrpCd
	 *            정책 기준이 되는 테넌트 상품 그룹 코드
	 * @param procPatternCd
	 *            조회할 정책처리패턴
	 * @param extraUnitCd
	 * 			  추가 조회할 정책처리코드
	 * @param ignoreTenantProdGrpCd
	 *            정책 대상이 카테고리 무시 여부: true-해당 처리패턴의 모든 정책 조회, false-해당 처리패턴 정책 중 기준 테넌트 상품 그룹 코드에 속하는 정책 조회
	 * @return 해당 테넌트의 구매Part 정책 목록
	 */
	public List<TenantSalePolicy> searchTenantSalePolicyList(String tenantId, String tenantProdGrpCd,
			String procPatternCd, String extraUnitCd, boolean ignoreTenantProdGrpCd);

	/**
	 * 
	 * <pre>
	 * 해당 테넌트의 결제 정책 조회.
	 * </pre>
	 * 
	 * @param tenantId
	 *            정책을 조회할 대상 테넌트 ID
	 * @param tenantProdGrpCd
	 *            정책 기준이 되는 테넌트 상품 그룹 코드
	 * @param prodKindCd
	 *            상품종류코드
	 * @param prodId
	 *            상품별 정책 조회할 상품ID
	 * @param parentProdId
	 *            정책 조회할 모상품ID (인앱 경우 AID)
	 * @return 해당 테넌트의 구매Part 정책 목록
	 */
	public TenantSalePolicy searchPaymentPolicy(String tenantId, String tenantProdGrpCd, String prodKindCd,
			String prodId, String parentProdId);
}
