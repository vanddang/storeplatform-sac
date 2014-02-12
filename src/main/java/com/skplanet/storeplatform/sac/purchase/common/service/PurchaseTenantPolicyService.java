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

import java.util.List;

import com.skplanet.storeplatform.sac.purchase.common.vo.PurchaseTenantPolicy;

/**
 * 
 * 구매 파트에서 사용하는 테넌트 정책(테넌트 상품 그룹 코드 기준) 서비스 인터페이스<br />
 * 구매/결제 제한 정책, 구매 취소 정책, 구매내역 정책 등 테넌트 관련 정책에 대한 서비스
 * 
 * Updated on : 2014. 2. 5. Updated by : 이승택, nTels.
 */
public interface PurchaseTenantPolicyService {

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
	public List<PurchaseTenantPolicy> searchPurchaseTenantPolicyList(String tenantId, String tenantProdGrpCd);

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
	 * @param policyId
	 *            조회할 정책ID
	 * @return 해당 테넌트의 구매Part 정책 목록
	 */
	public List<PurchaseTenantPolicy> searchPurchaseTenantPolicyList(String tenantId, String tenantProdGrpCd,
			String policyId);
}
