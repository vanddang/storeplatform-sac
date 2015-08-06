/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.common.service;

import com.skplanet.storeplatform.purchase.client.common.vo.TenantSalePolicy;
import com.skplanet.storeplatform.purchase.client.policy.sci.PurchaseTenantPolicySCI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 구매Part 에서 사용하는 테넌트 정책(테넌트 상품 그룹 코드 기준) 서비스 구현<br />
 * 구매/결제 제한 정책, 구매 취소 정책, 구매내역 정책 등 테넌트 관련 정책에 대한 서비스 구현
 * 
 * Updated on : 2014. 2. 5. Updated by : 이승택, nTels.
 */
@Service
public class PurchaseTenantPolicyServiceImpl implements PurchaseTenantPolicyService {

	@Autowired
	private PurchaseTenantPolicySCI purchaseTenantPolicySCI;

	/**
	 * 
	 * <pre>
	 * 해당 테넌트의 구매Part 정책목록을 정책ID를 Key로 갖는 Map 형태로 조회한다.
	 * </pre>
	 * 
	 * @param tenantId
	 *            정책을 조회할 대상 테넌트 ID
	 * @param tenantProdGrpCd
	 *            정책 기준이 되는 테넌트 상품 그룹 코드
	 * @return 해당 테넌트의 구매Part 정책 목록 (정책ID를 Key로, 관련 정책 목록을 Value로 갖는 Map형태)
	 */
	@Override
	public Map<String, List<TenantSalePolicy>> searchTenantSalePolicyListByMap(String tenantId, String tenantProdGrpCd) {
		List<TenantSalePolicy> policyList = this.searchTenantSalePolicyList(tenantId, tenantProdGrpCd);

		Map<String, List<TenantSalePolicy>> policyListMap = new HashMap<String, List<TenantSalePolicy>>();
		List<TenantSalePolicy> valuePolicyList = null;

		for (TenantSalePolicy policy : policyList) {
			valuePolicyList = policyListMap.containsKey(policy.getPolicyId()) ? policyListMap.get(policy.getPolicyId()) : new ArrayList<TenantSalePolicy>();

			valuePolicyList.add(policy);

			policyListMap.put(policy.getPolicyId(), valuePolicyList);
		}

		return policyListMap;
	}

	/**
	 * 
	 * <pre>
	 * 해당 테넌트의 구매Part 정책을 조회한다.
	 * </pre>
	 * 
	 * @param tenantId
	 *            정책을 조회할 대상 테넌트 ID
	 * @param tenantProdGrpCd
	 *            정책 기준이 되는 테넌트 상품 그룹 코드
	 * @return 해당 테넌트의 구매Part 정책 목록
	 */
	@Override
	public List<TenantSalePolicy> searchTenantSalePolicyList(String tenantId, String tenantProdGrpCd) {
		return this.searchTenantSalePolicyList(tenantId, tenantProdGrpCd, null, false);
	}

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
	 * @param ignoreTenantProdGrpCd
	 *            정책 대상이 카테고리 무시 여부: true-해당 처리패턴의 모든 정책 조회, false-해당 처리패턴 정책 중 기준 테넌트 상품 그룹 코드에 속하는 정책 조회
	 * @return 해당 테넌트의 구매Part 정책 목록
	 */
	@Override
	public List<TenantSalePolicy> searchTenantSalePolicyList(String tenantId, String tenantProdGrpCd,
			String procPatternCd, boolean ignoreTenantProdGrpCd) {
		return this.purchaseTenantPolicySCI.searchTenantSalePolicyList(tenantId, tenantProdGrpCd, procPatternCd, null,
				ignoreTenantProdGrpCd);
	}

	/**
	 * 추가 정책에 대해 조회한다.
	 *
	 * @param tenantId
	 *            정책을 조회할 대상 테넌트 ID
	 * @param extraUnitCd
	 * 			  추가 조회할 정책처리코드
	 * @return 추가 정책 목록
	 */
	@Override
	public List<TenantSalePolicy> searchTenantExtraSalePolicyList(String tenantId, String extraUnitCd) {
		return this.purchaseTenantPolicySCI.searchTenantSalePolicyList(tenantId, null, null, extraUnitCd, true);
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
	 *            정책 조회할 모상품ID (인앱 경우 AID)
	 * @return 해당 테넌트의 구매Part 정책 목록
	 */
	@Override
	public TenantSalePolicy searchPaymentPolicy(String tenantId, String tenantProdGrpCd, String prodKindCd,
			String prodId, String parentProdId) {
		return this.purchaseTenantPolicySCI.searchPaymentPolicy(tenantId, tenantProdGrpCd, prodKindCd, prodId,
				parentProdId);
	}

}
