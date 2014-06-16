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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.uaps.vo.UserEcRes;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.purchase.client.order.sci.PurchaseOrderSCI;
import com.skplanet.storeplatform.purchase.client.order.sci.PurchaseOrderSearchSCI;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchSktPaymentScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchSktPaymentScRes;
import com.skplanet.storeplatform.sac.client.internal.member.miscellaneous.vo.IndividualPolicyInfoSac;
import com.skplanet.storeplatform.sac.purchase.common.service.PurchaseTenantPolicyService;
import com.skplanet.storeplatform.sac.purchase.common.vo.PurchaseTenantPolicy;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;
import com.skplanet.storeplatform.sac.purchase.order.repository.PurchaseMemberRepository;
import com.skplanet.storeplatform.sac.purchase.order.repository.PurchaseUapsRepository;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseOrderInfo;
import com.skplanet.storeplatform.sac.purchase.order.vo.SktPaymentPolicyCheckParam;
import com.skplanet.storeplatform.sac.purchase.order.vo.SktPaymentPolicyCheckResult;

/**
 * 
 * 구매 제한정책 체크 서비스 구현체
 * 
 * Updated on : 2014. 1. 22. Updated by : 이승택, nTels.
 */
@Service
public class PurchaseOrderPolicyServiceImpl implements PurchaseOrderPolicyService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PurchaseOrderSCI purchaseOrderSCI;
	@Autowired
	private PurchaseOrderSearchSCI purchaseOrderSearchSCI;

	@Autowired
	private PurchaseTenantPolicyService purchaseTenantPolicyService;

	@Autowired
	private PurchaseMemberRepository purchaseMemberRepository;

	@Autowired
	private PurchaseUapsRepository uapsRespository;

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
	@Override
	public boolean isDeviceBasedPurchaseHistory(String tenantId, String tenantProdGrpCd) {
		return CollectionUtils.isNotEmpty(this.purchaseTenantPolicyService.searchPurchaseTenantPolicyList(tenantId,
				tenantProdGrpCd, PurchaseConstants.POLICY_PATTERN_DEVICE_BASED_PRCHSHST, false));
	}

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
	@Override
	public boolean isBlockPayment(String tenantId, String deviceId, String tenantProdGrpCd) {

		// 테넌트 구매차단 정책 조회
		List<PurchaseTenantPolicy> policyList = this.purchaseTenantPolicyService.searchPurchaseTenantPolicyList(
				tenantId, tenantProdGrpCd, PurchaseConstants.POLICY_PATTERN_USER_BLOCK_CD, false);

		if (CollectionUtils.isEmpty(policyList)) {
			return false;
		}

		List<String> policyCodeList = new ArrayList<String>();
		for (PurchaseTenantPolicy policy : policyList) {
			policyCodeList.add(policy.getApplyValue());
		}

		// 회원Part 사용자 정책 조회
		Map<String, IndividualPolicyInfoSac> policyResMap = this.purchaseMemberRepository.getPurchaseUserPolicy(
				deviceId, policyCodeList);

		if (policyResMap == null) {
			return false;
		}

		// 정책 체크
		IndividualPolicyInfoSac individualPolicyInfoSac = null;

		for (String key : policyResMap.keySet()) {
			if (policyCodeList.contains(key) == false) {
				continue;
			}

			individualPolicyInfoSac = policyResMap.get(key);

			if (StringUtils.equals(individualPolicyInfoSac.getIsUsed(), PurchaseConstants.USE_Y)) {
				this.logger.info("PRCHS,ORDER,SAC,POLICY,BLOCK,{}", deviceId);
				return true;
			}

			policyResMap.remove(key);
		}

		return false;
	}

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
	@Override
	public boolean isTestMdn(String tenantId, String deviceId, String tenantProdGrpCd) {

		// 테넌트 구매차단 정책 조회
		List<PurchaseTenantPolicy> policyList = this.purchaseTenantPolicyService.searchPurchaseTenantPolicyList(
				tenantId, tenantProdGrpCd, PurchaseConstants.POLICY_PATTERN_STORE_TEST_DEVICE_CD, false);

		if (CollectionUtils.isEmpty(policyList)) {
			return false;
		}

		List<String> policyCodeList = new ArrayList<String>();
		for (PurchaseTenantPolicy policy : policyList) {
			policyCodeList.add(policy.getApplyValue());
		}

		// 회원Part 사용자 정책 조회
		Map<String, IndividualPolicyInfoSac> policyResMap = this.purchaseMemberRepository.getPurchaseUserPolicy(
				deviceId, policyCodeList);

		if (policyResMap == null) {
			return false;
		}

		// 정책 체크
		IndividualPolicyInfoSac individualPolicyInfoSac = null;

		for (String key : policyResMap.keySet()) {
			if (policyCodeList.contains(key) == false) {
				continue;
			}

			individualPolicyInfoSac = policyResMap.get(key);

			// if (StringUtils.equals(individualPolicyInfoSac.getIsUsed(), PurchaseConstants.USE_Y)
			// && StringUtils.equals(individualPolicyInfoSac.getValue(), PurchaseConstants.USE_Y)) {
			if (StringUtils.equals(individualPolicyInfoSac.getIsUsed(), PurchaseConstants.USE_Y)) {
				// 테스트폰 결제
				this.logger.info("PRCHS,ORDER,SAC,POLICY,TESTMDN,{}", deviceId);
				return true;
			}

			policyResMap.remove(key);
		}

		return false;
	}

	/**
	 * 
	 * <pre>
	 * 회원 정책 체크: TestMDN / 구매차단.
	 * </pre>
	 * 
	 * @param purchaseOrderInfo
	 *            구매주문 정보
	 */
	@Override
	public void checkUserPolicy(PurchaseOrderInfo purchaseOrderInfo) {
		this.logger.info("PRCHS,ORDER,SAC,POLICY,USER,START,{}", purchaseOrderInfo.getPurchaseUser());

		if (purchaseOrderInfo.getRealTotAmt() == 0.0) {
			return;
		}

		// 비과금 (TEST MDN) 결제 여부
		if (this.isTestMdn(purchaseOrderInfo.getTenantId(), purchaseOrderInfo.getPurchaseUser().getDeviceId(),
				purchaseOrderInfo.getTenantProdGrpCd())) {
			purchaseOrderInfo.setTestMdn(true);
			purchaseOrderInfo.setFreePaymentMtdCd(PurchaseConstants.PAYMENT_METHOD_STORE_TEST_DEVICE);
			purchaseOrderInfo.setRealTotAmt(0.0);
		}

		// 구매차단 여부
		// if( isBlockPayment(purchaseOrderInfo.getTenantId(), purchaseOrderInfo.getPurchaseUser().getDeviceId(),
		// purchaseOrderInfo.getTenantProdGrpCd())) {
		// purchaseOrderInfo.setBlockPayment(true);
		// }
	}

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
	 * @return 결제수단 재정의 (가능수단 정의 & 제한금액/할인율 정의) 정보
	 */
	@Override
	public String getAvailablePaymethodAdjustInfo(String tenantId, String tenantProdGrpCd) {
		List<PurchaseTenantPolicy> policyList = this.purchaseTenantPolicyService.searchPurchaseTenantPolicyList(
				tenantId, tenantProdGrpCd, PurchaseConstants.POLICY_PATTERN_ADJUST_PAYMETHOD, false);

		if (CollectionUtils.isNotEmpty(policyList)) {
			return StringUtils.defaultString(policyList.get(0).getApplyValue(), "");
		}

		return null;
	}

	/**
	 * 
	 * <pre>
	 * SKT 후불 결제 진행 시 관련 정책 체크.
	 * </pre>
	 * 
	 * @param policyCheckParam
	 *            정책 체크 대상 데이터
	 * @return 정책 체크 결과
	 */
	@Override
	public SktPaymentPolicyCheckResult checkSktPaymentPolicy(SktPaymentPolicyCheckParam policyCheckParam) {
		/* 정책 처리 -- 처리패턴에 의한 처리에서 특정 처리패턴의 순차 처리로 변경. */
		/* 추후 정책 구조 변경을 보며, 처리 방법 변경 고려 */

		this.logger.info("PRCHS,ORDER,SAC,POLICY,SKT,START,{}", policyCheckParam);

		// --------------------------------------------------------------------------------------------------
		// UAPS Mapping정보 조회

		UserEcRes userEcRes = this.uapsRespository.searchUapsMappingInfoByMdn(policyCheckParam.getDeviceId());

		// --------------------------------------------------------------------------------------------------
		// 관련 정책 목록 조회

		Map<String, List<PurchaseTenantPolicy>> policyListMap = this.purchaseTenantPolicyService
				.searchPurchaseTenantPolicyListByMap(policyCheckParam.getTenantId(),
						policyCheckParam.getTenantProdGrpCd());

		List<PurchaseTenantPolicy> policyList = null;

		SktPaymentPolicyCheckResult policyResult = new SktPaymentPolicyCheckResult();
		policyResult.setSktLimitType(PurchaseConstants.SKT_ADJUST_REASON_NO_LIMIT);

		// --------------------------------------------------------------------------------------------------
		// MVNO 체크

		if (policyListMap.containsKey(PurchaseConstants.POLICY_ID_MVNO_ALLOW_CD)) {
			policyList = policyListMap.get(PurchaseConstants.POLICY_ID_MVNO_ALLOW_CD);

			for (PurchaseTenantPolicy policy : policyList) {
				if (this.isMvno(userEcRes, policy.getApplyValue(), policyCheckParam.getDeviceId())) {
					policyResult.setMvno(true);

					policyResult.setSktLimitType(PurchaseConstants.SKT_ADJUST_REASON_MVNO);
					return policyResult;
				}
			}

			policyListMap.remove(PurchaseConstants.POLICY_ID_MVNO_ALLOW_CD);
		}

		// --------------------------------------------------------------------------------------------------
		// 법인폰 체크

		// OCB 적립 제한을 위한 SKP법인폰 기본 조회

		if (this.isCorporationMdn(PurchaseConstants.SKP_CORPORATION_NO, policyCheckParam.getDeviceId())) {
			policyResult.setSkpCorporation(true);
		}

		// 법인폰 제한 정책 체크

		if (policyListMap.containsKey(PurchaseConstants.POLICY_ID_CORP_DEVICE)) {
			policyList = policyListMap.get(PurchaseConstants.POLICY_ID_CORP_DEVICE);

			for (PurchaseTenantPolicy policy : policyList) {
				if ((policyResult.isSkpCorporation() && StringUtils.equals(policy.getApplyValue(),
						PurchaseConstants.SKP_CORPORATION_NO))
						|| this.isCorporationMdn(policy.getApplyValue(), policyCheckParam.getDeviceId())) {
					policyResult.setCorporation(true);
					policyResult.setSktLimitType(PurchaseConstants.SKT_ADJUST_REASON_CORP);
					return policyResult;
				}
			}

			policyListMap.remove(PurchaseConstants.POLICY_ID_CORP_DEVICE);
		}

		// --------------------------------------------------------------------------------------------------
		// SKT 시험폰 체크

		// 2014.06.09 상용 적용 : SKT 시험폰은 모두 허용으로.

		// if (this.isSktTestMdn(userEcRes, policyCheckParam.getDeviceId())) {
		// policyResult.setSktTestMdn(true);
		// policyResult.setSktTestMdnWhiteList(true);
		// policyResult.setSktLimitType(PurchaseConstants.SKT_ADJUST_REASON_SKTTEST_ALLOW);
		//
		// return policyResult;
		// }

		if (this.isSktTestMdn(userEcRes, policyCheckParam.getDeviceId())) {
			policyResult.setSktTestMdn(true);
			boolean bWhite = false;

			// CM011604: 서비스 허용 SKT 시험폰
			if (policyListMap.containsKey(PurchaseConstants.POLICY_ID_SKT_TEST_DEVICE)) {
				policyList = policyListMap.get(PurchaseConstants.POLICY_ID_SKT_TEST_DEVICE);

				for (PurchaseTenantPolicy policy : policyList) {
					if (this.isSktTestMdnWhiteList(policy.getApplyValue(), policyCheckParam.getDeviceId())) {
						bWhite = true;
						break;
					}
				}
			}

			policyResult.setSktTestMdnWhiteList(bWhite);
			policyResult
					.setSktLimitType(bWhite ? PurchaseConstants.SKT_ADJUST_REASON_SKTTEST_ALLOW : PurchaseConstants.SKT_ADJUST_REASON_SKTTEST_NOT_ALLOW);

			return policyResult;
		}

		// --------------------------------------------------------------------------------------------------
		// SKT 후불 쇼핑상품 한도금액 제한

		policyResult.setSktRestAmt(policyCheckParam.getPaymentTotAmt());

		Double sktRestAmtObj = null;

		if (policyListMap.containsKey(PurchaseConstants.POLICY_ID_SKT_SHOPPING_PRCHS_LIMIT)) {
			policyList = policyListMap.get(PurchaseConstants.POLICY_ID_SKT_SHOPPING_PRCHS_LIMIT);

			// 회원 측 강제설정 적용
			for (PurchaseTenantPolicy policy : policyList) {
				if (StringUtils.equals(policy.getProcPatternCd(),
						PurchaseConstants.POLICY_PATTERN_SKT_SHOPPING_PRCHS_USER_LIMIT) == false) {
					continue;
				}

				sktRestAmtObj = this.checkSktShoppingUserLimitRest(policy, policyCheckParam);

				if (sktRestAmtObj != null) {
					if (sktRestAmtObj.doubleValue() < policyResult.getSktRestAmt()) {
						policyResult.setSktLimitType(PurchaseConstants.SKT_ADJUST_REASON_SHOPPING_PRCHS_USERPART_LIMIT);
					}

					policyResult.setSktRestAmt(sktRestAmtObj.doubleValue());

					if (sktRestAmtObj.doubleValue() <= 0.0) {
						policyResult.setSktRestAmt(0.0);
						return policyResult;
					}
				}
			}

			// 잔여 한도 조회 적용 : 회원 측 강제적용 값이 없는 경우에만 진행
			if (sktRestAmtObj == null) {

				for (PurchaseTenantPolicy policy : policyList) {
					if (StringUtils.equals(policy.getProcPatternCd(), PurchaseConstants.POLICY_PATTERN_SKT_PRCHS_LIMIT)) {

						sktRestAmtObj = this.checkSktLimitRest(policy, policyCheckParam); // CM011601: SKT후불 결제 한도제한

						if (sktRestAmtObj != null && sktRestAmtObj.doubleValue() < policyResult.getSktRestAmt()) {
							policyResult.setSktLimitType(PurchaseConstants.SKT_ADJUST_REASON_SHOPPING_LIMIT);
							policyResult.setSktRestAmt(sktRestAmtObj.doubleValue());

							if (sktRestAmtObj.doubleValue() <= 0.0) {
								policyResult.setSktRestAmt(0.0);
								return policyResult;
							}
						}

					} else if (StringUtils.equals(policy.getProcPatternCd(),
							PurchaseConstants.POLICY_PATTERN_SKT_RECV_LIMIT)) {
						if (StringUtils.isEmpty(policyCheckParam.getRecvTenantId())) { // CM011602: SKT후불 선물수신 한도제한
							continue;
						}

						sktRestAmtObj = this.checkSktRecvLimit(policy, policyCheckParam);

						if (sktRestAmtObj != null && sktRestAmtObj.doubleValue() < policyResult.getSktRestAmt()) {
							policyResult.setSktLimitType(PurchaseConstants.SKT_ADJUST_REASON_SHOPPING_RECV_LIMIT);
							policyResult.setSktRestAmt(sktRestAmtObj.doubleValue());

							if (sktRestAmtObj.doubleValue() <= 0.0) {
								policyResult.setSktRestAmt(0.0);
								return policyResult;
							}
						}

					}
				}
			}

			policyListMap.remove(PurchaseConstants.POLICY_ID_SKT_SHOPPING_PRCHS_LIMIT);
		}

		// --------------------------------------------------------------------------------------------------
		// SKT 후불 한도금액 제한

		if (policyListMap.containsKey(PurchaseConstants.POLICY_ID_SKT_PRCHS_LIMIT)) {
			policyList = policyListMap.get(PurchaseConstants.POLICY_ID_SKT_PRCHS_LIMIT);

			for (PurchaseTenantPolicy policy : policyList) {
				if (StringUtils.equals(policy.getProcPatternCd(), PurchaseConstants.POLICY_PATTERN_SKT_PRCHS_LIMIT) == false) {
					continue;
				}

				sktRestAmtObj = this.checkSktLimitRest(policy, policyCheckParam); // CM011601: SKT후불 결제 한도제한

				if (sktRestAmtObj != null && sktRestAmtObj.doubleValue() < policyResult.getSktRestAmt()) {
					policyResult.setSktLimitType(PurchaseConstants.SKT_ADJUST_REASON_LIMIT);
					policyResult.setSktRestAmt(sktRestAmtObj.doubleValue());

					if (sktRestAmtObj.doubleValue() <= 0.0) {
						policyResult.setSktRestAmt(0.0);
						return policyResult;
					}
				}
			}

			policyListMap.remove(PurchaseConstants.POLICY_ID_SKT_PRCHS_LIMIT);
		}

		// --------------------------------------------------------------------------------------------------
		// SKT 후불 선물수신 한도금액 제한

		if (StringUtils.isNotBlank(policyCheckParam.getRecvTenantId())) {

			// 쇼핑상품 선물수신 한도 회원별 강제적용

			if (policyListMap.containsKey(PurchaseConstants.POLICY_ID_SKT_SHOPPING_RECV_LIMIT)) {
				policyList = policyListMap.get(PurchaseConstants.POLICY_ID_SKT_SHOPPING_RECV_LIMIT);

				// 회원 측 강제설정 적용
				for (PurchaseTenantPolicy policy : policyList) {
					if (StringUtils.equals(policy.getProcPatternCd(),
							PurchaseConstants.POLICY_PATTERN_SKT_SHOPPING_RECV_USER_LIMIT) == false) { // CM011615
						continue;
					}

					sktRestAmtObj = this.checkSktShoppingUserLimitRest(policy, policyCheckParam);

					if (sktRestAmtObj != null) {
						if (sktRestAmtObj.doubleValue() < policyResult.getSktRestAmt()) {
							policyResult
									.setSktLimitType(PurchaseConstants.SKT_ADJUST_REASON_SHOPPING_RECV_USERPART_LIMIT);
						}

						policyResult.setSktRestAmt(sktRestAmtObj.doubleValue());

						if (sktRestAmtObj.doubleValue() <= 0.0) {
							policyResult.setSktRestAmt(0.0);
							return policyResult;
						}
					}
				}
			}

			// 기본 선물수신 한도금액 제한

			if (policyListMap.containsKey(PurchaseConstants.POLICY_ID_SKT_RECV_LIMIT)) {
				policyList = policyListMap.get(PurchaseConstants.POLICY_ID_SKT_RECV_LIMIT);

				for (PurchaseTenantPolicy policy : policyList) {
					if (StringUtils.equals(policy.getProcPatternCd(), PurchaseConstants.POLICY_PATTERN_SKT_RECV_LIMIT) == false) {
						continue;
					}

					sktRestAmtObj = this.checkSktRecvLimit(policy, policyCheckParam);

					if (sktRestAmtObj != null && sktRestAmtObj.doubleValue() < policyResult.getSktRestAmt()) {
						policyResult.setSktLimitType(PurchaseConstants.SKT_ADJUST_REASON_RECV_LIMIT);
						policyResult.setSktRestAmt(sktRestAmtObj.doubleValue());

						if (sktRestAmtObj.doubleValue() <= 0.0) {
							policyResult.setSktRestAmt(0.0);
							return policyResult;
						}
					}
				}
			}
		}

		if (policyResult.getSktRestAmt() > policyCheckParam.getPaymentTotAmt()) {
			policyResult.setSktRestAmt(policyCheckParam.getPaymentTotAmt());
		}

		this.logger.info("PRCHS,ORDER,SAC,POLICY,SKT,END,{},{}", policyCheckParam.getDeviceKey(), policyResult);
		return policyResult;
	}

	// ========================

	/*
	 * 
	 * <pre> 회원Part 강제적용 : SKT후불 쇼핑상품 구매결제 한도제한 남은 금액 조회. </pre>
	 * 
	 * @param policy 테넌트 정책 정보
	 * 
	 * @param policyCheckParam 정책 체크 대상 데이터
	 * 
	 * @return 남은 SKT 후불 결제 가능 금액
	 */
	private Double checkSktShoppingUserLimitRest(PurchaseTenantPolicy policy,
			SktPaymentPolicyCheckParam policyCheckParam) {
		this.logger.info("PRCHS,ORDER,SAC,POLICY,USERLIMIT,START,{}({})", policy.getPolicyId(), policy.getPolicySeq());

		// ----------------------------------------------------------------
		// 회원Part 사용자 정책 조회

		String memberPolicyCd = policy.getApplyValue();

		List<String> policyCodeList = new ArrayList<String>();
		policyCodeList.add(memberPolicyCd);

		// 구매자/수신자 체크
		String deviceId = StringUtils.equals(policy.getPolicyId(), PurchaseConstants.POLICY_ID_SKT_SHOPPING_RECV_LIMIT) ? policyCheckParam
				.getRecvDeviceId() : policyCheckParam.getDeviceId();

		Map<String, IndividualPolicyInfoSac> policyResMap = this.purchaseMemberRepository.getPurchaseUserPolicy(
				deviceId, policyCodeList);

		if (policyResMap == null || policyResMap.containsKey(memberPolicyCd) == false) {
			this.logger.info("PRCHS,ORDER,SAC,POLICY,USERLIMIT,END,{}({}),null", policy.getPolicyId(),
					policy.getPolicySeq());
			return null;
		}

		IndividualPolicyInfoSac individualPolicyInfoSac = policyResMap.get(memberPolicyCd);

		if (StringUtils.equals(individualPolicyInfoSac.getIsUsed(), PurchaseConstants.USE_Y) == false) {
			this.logger.info("PRCHS,ORDER,SAC,POLICY,USERLIMIT,END,{}({}),NotUsed", policy.getPolicyId(),
					policy.getPolicySeq());
			return null;
		}

		this.logger.info("PRCHS,ORDER,SAC,POLICY,USERLIMIT,END,{}({}),{}", policy.getPolicyId(), policy.getPolicySeq(),
				Double.parseDouble(individualPolicyInfoSac.getLimitAmount()) - policyCheckParam.getPaymentTotAmt());
		return Double.parseDouble(individualPolicyInfoSac.getLimitAmount()) - policyCheckParam.getPaymentTotAmt();
	}

	/*
	 * 
	 * <pre> SKT후불 구매결제 한도제한 남은 금액 조회. </pre>
	 * 
	 * @param policy 테넌트 정책 정보
	 * 
	 * @param policyCheckParam 정책 체크 대상 데이터
	 * 
	 * @return 남은 SKT 후불 결제 가능 금액
	 */
	private Double checkSktLimitRest(PurchaseTenantPolicy policy, SktPaymentPolicyCheckParam policyCheckParam) {
		this.logger.info("PRCHS,ORDER,SAC,POLICY,START,{}({})", policy.getPolicyId(), policy.getPolicySeq());

		double checkVal = 0.0;

		SearchSktPaymentScReq sciReq = new SearchSktPaymentScReq();
		SearchSktPaymentScRes sciRes = null;

		sciReq.setTenantId(policyCheckParam.getTenantId());
		sciReq.setUserKey(policyCheckParam.getUserKey());
		sciReq.setDeviceKey(policyCheckParam.getDeviceKey());
		sciReq.setTenantProdGrpCd(policy.getTenantProdGrpCd());
		sciReq.setApplyUnitCd(policy.getApplyUnitCd());
		sciReq.setCondUnitCd(policy.getCondUnitCd());
		sciReq.setCondValue(policy.getCondValue());
		sciReq.setCondClsfUnitCd(policy.getCondClsfUnitCd());
		sciReq.setCondPeriodUnitCd(policy.getCondPeriodUnitCd());
		sciReq.setCondPeriodValue(policy.getCondPeriodValue());

		// (정책 적용조건) 과금조건 조회
		// 쇼핑상품 구매건수 조회용도 이며, 전월 단위의 건수 조회인 경우만 처리
		if (StringUtils.isNotBlank(policy.getCondPeriodUnitCd())
				&& StringUtils.equals(policy.getCondPeriodUnitCd(),
						PurchaseConstants.POLICY_PRECONDITION_PERIOD_UNIT_CD_PREMONTH)
				&& StringUtils.equals(policy.getCondClsfUnitCd(),
						PurchaseConstants.POLICY_PRECONDITION_CLSF_UNIT_CD_COUNT)) {
			sciRes = this.purchaseOrderSearchSCI.searchSktLimitCondDetail(sciReq); // null 또는 1
			checkVal = (sciRes.getVal() == null ? 0 : (Double) sciRes.getVal());

			if (Double.parseDouble(policy.getCondClsfValue()) == 0) {
				if (checkVal != 0) {
					this.logger.info("PRCHS,ORDER,SAC,POLICY,END,{}({}),null", policy.getPolicyId(),
							policy.getPolicySeq());
					return null;
				}
			} else if (checkVal < Double.parseDouble(policy.getCondClsfValue())) {
				this.logger.info("PRCHS,ORDER,SAC,POLICY,END,{}({}),pass", policy.getPolicyId(), policy.getPolicySeq());
				return null;
			}
		}

		// (정책 체크 값) 정책 요소기준 조회
		sciRes = this.purchaseOrderSearchSCI.searchSktAmountDetail(sciReq);
		checkVal = (Double) sciRes.getVal();

		this.logger.info("PRCHS,ORDER,SAC,POLICY,END,{}({}),{}", policy.getPolicyId(), policy.getPolicySeq(),
				(Double.parseDouble(policy.getApplyValue()) - checkVal));
		return (Double.parseDouble(policy.getApplyValue()) - checkVal);
	}

	/*
	 * 
	 * <pre> SKT후불 선물수신 한도제한 남은 금액 조회. </pre>
	 * 
	 * @param policy 테넌트 정책 정보
	 * 
	 * @param policyCheckParam 정책 체크 대상 데이터
	 * 
	 * @return 남은 SKT 후불 선물수신 가능 금액
	 */
	private Double checkSktRecvLimit(PurchaseTenantPolicy policy, SktPaymentPolicyCheckParam policyCheckParam) {
		this.logger.info("PRCHS,ORDER,SAC,POLICY,START,{}({})", policy.getPolicyId(), policy.getPolicySeq());

		double checkVal = 0.0;

		SearchSktPaymentScReq sciReq = new SearchSktPaymentScReq();
		SearchSktPaymentScRes sciRes = null;

		sciReq.setTenantId(policyCheckParam.getRecvTenantId()); // 수신자 기준 조회
		sciReq.setUserKey(policyCheckParam.getRecvUserKey()); // 수신자 기준 조회
		sciReq.setDeviceKey(policyCheckParam.getRecvDeviceKey()); // 수신자 기준 조회
		sciReq.setTenantProdGrpCd(policy.getTenantProdGrpCd());
		sciReq.setApplyUnitCd(policy.getApplyUnitCd());
		sciReq.setCondUnitCd(policy.getCondUnitCd());
		sciReq.setCondValue(policy.getCondValue());
		sciReq.setCondClsfUnitCd(policy.getCondClsfUnitCd());
		sciReq.setCondPeriodUnitCd(policy.getCondPeriodUnitCd());
		sciReq.setCondPeriodValue(policy.getCondPeriodValue());

		// (정책 체크 값) 정책 요소기준 조회
		sciRes = this.purchaseOrderSearchSCI.searchSktRecvAmountDetail(sciReq);
		checkVal = (Double) sciRes.getVal();

		this.logger.info("PRCHS,ORDER,SAC,POLICY,END,{}({}),{}", policy.getPolicyId(), policy.getPolicySeq(),
				(Double.parseDouble(policy.getApplyValue()) - checkVal));
		return (Double.parseDouble(policy.getApplyValue()) - checkVal);
	}

	/*
	 * 
	 * <pre> 법인폰 여부 조회. </pre>
	 * 
	 * @param policy 테넌트 정책 정보
	 * 
	 * @param corpNum 법인번호
	 * 
	 * @param deviceId 법인폰 여부 조회할 MDN
	 * 
	 * @return 법인폰 여부: true-법인폰, false-해당 법인폰 아님
	 */
	private boolean isCorporationMdn(String corpNum, String deviceId) {
		this.logger.info("PRCHS,ORDER,SAC,POLICY,CORP,START,{},{}", corpNum, deviceId);

		try {
			UserEcRes userEcRes = this.uapsRespository.searchUapsAuthorizeInfoByMdn(corpNum, deviceId);
			if (userEcRes != null) {
				this.logger.info("PRCHS,ORDER,SAC,POLICY,CORP,END,true");
				return true;
			}
		} catch (StorePlatformException e) {
			// 2014.02.12. 기준 : EC UAPS 에서 비정상 예외 시 9999 리턴, 그 외에는 조회결과 없음(9997) 또는 명의상태에 따른 코드.
			if (StringUtils.equals(e.getErrorInfo().getCode(), "EC_UAPS_9999")) {
				throw e;
			} // else는 skip처리가 정상
		}
		this.logger.info("PRCHS,ORDER,SAC,POLICY,CORP,END,false");
		return false;
	}

	/*
	 * 
	 * <pre> SKT 시험폰 여부 조회. </pre>
	 * 
	 * @param uapsUserMappingInfo UAPS Mapping Info
	 * 
	 * @param deviceId SKT 시험폰 여부 조회할 MDN
	 * 
	 * @return SKT 시험폰 여부: true-SKT 시험폰, false-SKT 시험폰 아님
	 */
	private boolean isSktTestMdn(UserEcRes uapsUserMappingInfo, String deviceId) {
		this.logger.info("PRCHS,ORDER,SAC,POLICY,SKTTEST,START,{}", deviceId);

		if (uapsUserMappingInfo == null) {
			uapsUserMappingInfo = this.uapsRespository.searchUapsMappingInfoByMdn(deviceId);
		}

		this.logger.info("PRCHS,ORDER,SAC,POLICY,SKTTEST,END,{},{}", deviceId,
				(StringUtils.equals(uapsUserMappingInfo.getSvcTP(), PurchaseConstants.UAPS_SVC_TP_SKTTEST)));

		return (StringUtils.equals(uapsUserMappingInfo.getSvcTP(), PurchaseConstants.UAPS_SVC_TP_SKTTEST));
	}

	/*
	 * 
	 * <pre> Store 서비스 허용하는 시험폰 White List 등록 여부 조회. </pre>
	 * 
	 * @param memberPolicyCd 회원Part에서 관리하는 시험폰 정책코드
	 * 
	 * @param deviceId 조회할 MDN
	 * 
	 * @return White List 등록 여부: true-White List 등록, false-White List 등록 안됨
	 */
	private boolean isSktTestMdnWhiteList(String memberPolicyCd, String deviceId) {
		return true; // 2014.06.09: SKT시험폰 항상 허용됨

		// List<String> policyCodeList = new ArrayList<String>();
		// policyCodeList.add(memberPolicyCd);
		//
		// Map<String, IndividualPolicyInfoSac> policyResMap = this.purchaseMemberRepository.getPurchaseUserPolicy(
		// deviceId, policyCodeList);
		//
		// if (policyResMap == null || policyResMap.containsKey(memberPolicyCd) == false) {
		// return false;
		// }
		//
		// IndividualPolicyInfoSac individualPolicyInfoSac = policyResMap.get(memberPolicyCd);
		//
		// if (StringUtils.equals(individualPolicyInfoSac.getIsUsed(), PurchaseConstants.USE_Y)) {
		// return true;
		// }
		//
		// return false;
	}

	/*
	 * 
	 * <pre> 결제차단 MVNO 회선 여부 조회. </pre>
	 * 
	 * @param uapsUserMappingInfo UAPS Mapping Info
	 * 
	 * @param deviceId 결제차단 MVNO 회선 여부 조회할 MDN
	 * 
	 * @return 결제차단 MVNO 회선 여부: true-결제 차단, false-결제 허용
	 */
	private boolean isMvno(UserEcRes uapsUserMappingInfo, String allowMvnoCode, String deviceId) {
		this.logger.info("PRCHS,ORDER,SAC,POLICY,MVNO,START,{},{}", deviceId, allowMvnoCode);

		if (uapsUserMappingInfo == null) {
			uapsUserMappingInfo = this.uapsRespository.searchUapsMappingInfoByMdn(deviceId);
		}

		this.logger.info("PRCHS,ORDER,SAC,POLICY,MVNO,END,{},{},{},{}", deviceId, uapsUserMappingInfo.getMvnoCD(),
				allowMvnoCode, (StringUtils.equals(uapsUserMappingInfo.getMvnoCD(), allowMvnoCode) == false));

		return (StringUtils.equals(uapsUserMappingInfo.getMvnoCD(), allowMvnoCode) == false);
	}
}
