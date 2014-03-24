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
import java.util.Map;

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
	 * 해당 테넌트의 구매Part 정책목록을 정책ID를 Key로 갖는 Map 형태로 조회한다.
	 * </pre>
	 * 
	 * @param tenantId
	 *            정책을 조회할 대상 테넌트 ID
	 * @param tenantProdGrpCd
	 *            정책 기준이 되는 테넌트 상품 그룹 코드
	 * @return 해당 테넌트의 구매Part 정책 목록 (정책ID를 Key로, 관련 정책 목록을 Value로 갖는 Map형태)
	 */
	public Map<String, List<PurchaseTenantPolicy>> searchPurchaseTenantPolicyListByMap(String tenantId,
			String tenantProdGrpCd);

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
	 * @param procPatternCd
	 *            조회할 정책처리패턴
	 * @param ignoreTenantProdGrpCd
	 *            정책 대상이 카테고리 무시 여부: true-해당 처리패턴의 모든 정책 조회, false-해당 처리패턴 정책 중 기준 테넌트 상품 그룹 코드에 속하는 정책 조회
	 * @return 해당 테넌트의 구매Part 정책 목록
	 */
	public List<PurchaseTenantPolicy> searchPurchaseTenantPolicyList(String tenantId, String tenantProdGrpCd,
			String procPatternCd, boolean ignoreTenantProdGrpCd);
}
