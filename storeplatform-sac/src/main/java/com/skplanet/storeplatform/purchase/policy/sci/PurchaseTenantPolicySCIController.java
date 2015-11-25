/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.policy.sci;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.purchase.client.common.vo.TenantSalePolicy;
import com.skplanet.storeplatform.purchase.client.policy.sci.PurchaseTenantPolicySCI;
import com.skplanet.storeplatform.purchase.policy.service.PurchaseTenantPolicyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * The type Purchase tenant policy sCI controller.
 */
@LocalSCI
public class PurchaseTenantPolicySCIController implements PurchaseTenantPolicySCI {
	@Autowired
	private PurchaseTenantPolicyService purchaseTenantPolicyService;

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
	@Override
	public List<TenantSalePolicy> searchTenantSalePolicyList(String tenantId, String tenantProdGrpCd,
			String procPatternCd, String extraUnitCd, boolean ignoreTenantProdGrpCd) {
		return this.purchaseTenantPolicyService.searchTenantSalePolicyList(tenantId, tenantProdGrpCd, procPatternCd, extraUnitCd,
				ignoreTenantProdGrpCd);
	}

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
	 *            정책 조회할 모상품ID
	 * @return 해당 테넌트의 구매Part 정책 목록
	 */
	@Override
	public TenantSalePolicy searchPaymentPolicy(String tenantId, String tenantProdGrpCd, String prodKindCd,
			String prodId, String parentProdId) {
		return this.purchaseTenantPolicyService.searchPaymentPolicy(tenantId, tenantProdGrpCd, prodKindCd, prodId,
				parentProdId);
	}
}
