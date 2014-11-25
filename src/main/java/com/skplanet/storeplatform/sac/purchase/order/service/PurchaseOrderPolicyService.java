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

import com.skplanet.storeplatform.sac.purchase.order.vo.CheckPaymentPolicyParam;
import com.skplanet.storeplatform.sac.purchase.order.vo.CheckPaymentPolicyResult;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseOrderInfo;

/**
 * 
 * 구매 제한정책 체크 서비스
 * 
 * Updated on : 2014. 1. 22. Updated by : 이승택, nTels.
 */
public interface PurchaseOrderPolicyService {

	/**
	 * 
	 * <pre>
	 * 해당 테넌트상품분류코드 가 디바이스 기반 구매정책 인지 체크.
	 * </pre>
	 * 
	 * @param tenantId
	 *            테넌트 ID
	 * 
	 * @param tenantProdGrpCd
	 *            테넌트상품분류코드
	 * 
	 * @return 디바이스 기반 구매정책 여부: true-디바이스 기반, false-ID 기반
	 */
	public boolean isDeviceBasedPurchaseHistory(String tenantId, String tenantProdGrpCd);

	/**
	 * 
	 * <pre>
	 * T마일리지 적립 한도 조회.
	 * </pre>
	 * 
	 * @param tenantId
	 *            테넌트 ID
	 * 
	 * @param tenantProdGrpCd
	 *            테넌트상품분류코드
	 * 
	 * @return T마일리지 적립 한도
	 */
	public int searchtMileageSaveLimit(String tenantId, String tenantProdGrpCd);

	/**
	 * 
	 * <pre>
	 * T마일리지 적립 가능 결제수단 조회.
	 * </pre>
	 * 
	 * @param tenantId
	 *            테넌트 ID
	 * 
	 * @param tenantProdGrpCd
	 *            테넌트상품분류코드
	 * 
	 * @return T마일리지 적립 가능 결제수단
	 */
	public String searchtMileageSavePaymentMethod(String tenantId, String tenantProdGrpCd);

	/**
	 * 
	 * <pre>
	 * 구매(결제)차단 여부 체크.
	 * </pre>
	 * 
	 * @param tenantId
	 *            테넌트ID
	 * @param deviceId
	 *            MDN
	 * @param tenantProdGrpCd
	 *            테넌트상품분류코드
	 * @return 구매(결제) 차단 여부: true-차단됨, false-차단아님
	 */
	public boolean isBlockPayment(String tenantId, String deviceId, String tenantProdGrpCd);

	/**
	 * 
	 * <pre>
	 * 회원 정책 체크: TestMDN / 구매차단.
	 * </pre>
	 * 
	 * @param purchaseOrderInfo
	 *            구매주문 정보
	 */
	public void checkUserPolicy(PurchaseOrderInfo purchaseOrderInfo);

	/**
	 * 
	 * <pre>
	 * TEST MDN 여부 체크.
	 * </pre>
	 * 
	 * @param tenantId
	 *            테넌트ID
	 * @param deviceId
	 *            MDN
	 * @param tenantProdGrpCd
	 *            테넌트상품분류코드
	 * @return TEST MDN 여부: true-TEST MDN, false-TEST MDN 아님
	 */
	public boolean isTestMdn(String tenantId, String deviceId, String tenantProdGrpCd);

	/**
	 * 
	 * <pre>
	 * 결제수단 재정의 (가능수단 정의 & 제한금액/할인율 정의) 정보 조회
	 * </pre>
	 * 
	 * @param tenantId
	 *            테넌트 ID
	 * 
	 * @param tenantProdGrpCd
	 *            테넌트상품분류코드
	 * 
	 * @param prodKindCd
	 *            상품종류코드
	 * 
	 * @param prodId
	 *            상품별 정책 조회할 상품ID
	 * 
	 * @return 결제수단 재정의 (가능수단 정의 & 제한금액/할인율 정의) 정보
	 */
	public String getAvailablePaymethodAdjustInfo(String tenantId, String tenantProdGrpCd, String prodKindCd,
			String prodId);

	/**
	 * 
	 * <pre>
	 * 마일리지 적립 가능한 SKT시험폰 여부 체크.
	 * </pre>
	 * 
	 * @param tenantId
	 *            테넌트ID
	 * @param deviceId
	 *            MDN
	 * @param tenantProdGrpCd
	 *            테넌트상품분류코드
	 * @return 마일리지 적립 가능한 SKT시험폰 여부: true-적립 대상, false-적립 미대상
	 */
	public boolean isMileageSaveSktTestDevice(String tenantId, String deviceId, String tenantProdGrpCd);

	/**
	 * 
	 * <pre>
	 * 결제 정책 체크.
	 * </pre>
	 * 
	 * @param policyCheckParam
	 *            정책 체크 대상 데이터
	 * @return 정책 체크 결과
	 */
	public CheckPaymentPolicyResult checkPaymentPolicy(CheckPaymentPolicyParam policyCheckParam);

	/**
	 * 
	 * <pre>
	 * 결제수단 별 OCB 적립율 산정.
	 * </pre>
	 * 
	 * @param tenantId
	 *            테넌트 ID
	 * 
	 * @param telecom
	 *            통신사
	 * 
	 * @param tenantProdGrpCd
	 *            테넌트 상품 그룹 코드
	 * 
	 * @param sktTestOrSkpCorp
	 *            시험폰 또는 SKP법인폰 여부
	 * 
	 * @return 결제수단 별 OCB 적립율
	 */
	public String adjustOcbSaveInfo(String tenantId, String telecom, String tenantProdGrpCd, boolean sktTestOrSkpCorp);
}
