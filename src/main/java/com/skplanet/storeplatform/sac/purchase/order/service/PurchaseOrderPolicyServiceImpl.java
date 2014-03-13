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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.skplanet.storeplatform.sac.purchase.common.service.PurchaseTenantPolicyService;
import com.skplanet.storeplatform.sac.purchase.common.vo.PurchaseTenantPolicy;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;
import com.skplanet.storeplatform.sac.purchase.order.repository.PurchaseUapsRespository;
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
	private PurchaseUapsRespository uapsRespository;

	// TAKTODO:: 임시용, 정책 테이블에 관련 정책 추가 후 정책 테이블 조회로 변경
	private static final List<String> TEMPORARY_ALLOW_MVNO_CODE_LIST; // 허용하는 MVNO 코드 목록
	private static final Map<String, String> TEMPORARY_PAYMETHOD_MAP;
	static {
		TEMPORARY_ALLOW_MVNO_CODE_LIST = new ArrayList<String>();
		TEMPORARY_ALLOW_MVNO_CODE_LIST.add("0");

		TEMPORARY_PAYMETHOD_MAP = new HashMap<String, String>();
		TEMPORARY_PAYMETHOD_MAP.put("OR006201DP00OR006311", "");
		TEMPORARY_PAYMETHOD_MAP.put("OR006211DP01OR006311", "21::50");
		TEMPORARY_PAYMETHOD_MAP.put("OR006211DP03OR006311", "21::20");
		TEMPORARY_PAYMETHOD_MAP.put("OR006211DP04OR006311", "21::20");
		TEMPORARY_PAYMETHOD_MAP.put("OR006211DP08OR006311", "21::20");
		TEMPORARY_PAYMETHOD_MAP.put("OR006211DP12OR006311", "21::20");
		TEMPORARY_PAYMETHOD_MAP.put("OR006212DP07OR006311", "21::50");
		TEMPORARY_PAYMETHOD_MAP.put("OR006212DP09OR006311", "21::50");
		TEMPORARY_PAYMETHOD_MAP.put("OR006212DP17OR006311", "21::50");
		TEMPORARY_PAYMETHOD_MAP.put("OR006212DP17OR006331", "14:0:0;20:0:0;21::50;22:0:0;23:0:0;24:0:0;25:0:0;26:0:0");
		TEMPORARY_PAYMETHOD_MAP.put("OR006212DP18OR006311", "21::50");
		TEMPORARY_PAYMETHOD_MAP.put("OR006212DP18OR006331", "14:0:0;20:0:0;21::50;22:0:0;23:0:0;24:0:0;25:0:0;26:0:0");
		TEMPORARY_PAYMETHOD_MAP.put("OR006213DP05OR006311", "21::20");
		TEMPORARY_PAYMETHOD_MAP.put("OR006213DP16OR006311", "21::20");
		TEMPORARY_PAYMETHOD_MAP.put("OR006214DP06OR006311", "21::20");
		TEMPORARY_PAYMETHOD_MAP.put("OR006214DP13OR006311", "21::20");
		TEMPORARY_PAYMETHOD_MAP.put("OR006212DP13OR006331", "21::20");
		TEMPORARY_PAYMETHOD_MAP.put("OR006214DP14OR006311", "21::20");
		TEMPORARY_PAYMETHOD_MAP.put("OR006212DP14OR006331", "21::20");
		TEMPORARY_PAYMETHOD_MAP.put("OR006214DP26OR006311", "21::20");
		TEMPORARY_PAYMETHOD_MAP.put("OR006214DP29OR006311", "21::20");
		TEMPORARY_PAYMETHOD_MAP.put("OR006221DP15OR006311", "20:0:0;21:0:0;22:0:0;23:0:0;24:0:0;25:0:0;26:0:0");
		TEMPORARY_PAYMETHOD_MAP.put("OR006221DP28OR006311", "20:0:0;21:0:0;22:0:0;23:0:0;24:0:0;25:0:0;26:0:0");
		TEMPORARY_PAYMETHOD_MAP.put("OR006231DP02OR006311", "21::20");
		TEMPORARY_PAYMETHOD_MAP.put("OR006311", "");
		TEMPORARY_PAYMETHOD_MAP.put("OR006321", "");
	}

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
		return (this.purchaseTenantPolicyService.searchPurchaseTenantPolicyList(tenantId, tenantProdGrpCd,
				PurchaseConstants.POLICY_PATTERN_DEVICE_BASED_PRCHSHST, false).size() > 0);
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
		// this.purchaseTenantPolicyService.searchPurchaseTenantPolicyList(tenantId, tenantProdGrpCd,
		// PurchaseConstants.POLICY_PATTERN_ADJUST_PAYMETHOD, false);

		return TEMPORARY_PAYMETHOD_MAP.get(tenantProdGrpCd);
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
		this.logger.debug("PRCHS,ORDER,SAC,POLICY,SKT,START,{}", policyCheckParam);
		List<PurchaseTenantPolicy> policyList = this.purchaseTenantPolicyService.searchPurchaseTenantPolicyList(
				policyCheckParam.getTenantId(), policyCheckParam.getTenantProdGrpCd());

		SktPaymentPolicyCheckResult policyResult = new SktPaymentPolicyCheckResult();
		policyResult.setSktRestAmt(Integer.MAX_VALUE);
		Double checkRest = null;
		for (PurchaseTenantPolicy policy : policyList) {
			if (StringUtils.equals(policy.getProcPatternCd(), PurchaseConstants.POLICY_PATTERN_SKT_PRCHS_LIMIT)) {
				if (policyResult.getSktRestAmt() <= 0.0) {
					continue;
				}

				checkRest = this.checkSktLimitRest(policy, policyCheckParam); // CM011601: SKT후불 결제 한도제한
				if (checkRest != null && checkRest.doubleValue() < policyResult.getSktRestAmt()) {
					policyResult.setSktRestAmt(checkRest.doubleValue());
				}

			} else if (StringUtils.equals(policy.getProcPatternCd(), PurchaseConstants.POLICY_PATTERN_SKT_RECV_LIMIT)) {
				if (StringUtils.isEmpty(policyCheckParam.getRecvTenantId())) { // CM011602: SKT후불 선물수신 한도제한
					continue;
				}
				if (policyResult.getSktRestAmt() <= 0.0) {
					continue;
				}

				checkRest = this.checkSktRecvLimit(policy, policyCheckParam);
				if (checkRest != null && checkRest.doubleValue() < policyResult.getSktRestAmt()) {
					policyResult.setSktRestAmt(checkRest.doubleValue());
				}

			} else if (StringUtils.equals(policy.getProcPatternCd(), PurchaseConstants.POLICY_PATTERN_CORP_DEVICE)) {
				if (policyResult.isCorporation()) { // CM011603: 법인 명의 제한 (법인폰)
					continue;
				}

				if (this.isCorporationMdn(policy.getApplyValue(), policyCheckParam.getDeviceId())) {
					policyResult.setCorporation(true);
					policyResult.setSkpCorporation(StringUtils.equals(policy.getApplyValue(),
							PurchaseConstants.SKP_CORPORATION_NO));
				}

			} else if (StringUtils.equals(policy.getProcPatternCd(), PurchaseConstants.POLICY_PATTERN_SKT_TEST_DEVICE)) {
				if (this.isSktTestMdn(policyCheckParam.getDeviceId())) { // CM011604: SKT 시험폰 제한
					policyResult.setSktTestMdn(true);
					policyResult.setSktTestMdnWhiteList(this.isSktTestMdnWhiteList(policyCheckParam.getTenantId(),
							policyCheckParam.getDeviceId()));
				}

			} else if (StringUtils.equals(policy.getProcPatternCd(), PurchaseConstants.POLICY_PATTERN_MVNO_ALLOW_CD)) {
				if (policyResult.isMvno()) { // CM0116xx: MVNO 제한
					continue;
				}

				policyResult.setMvno(this.isMvno(policy.getApplyValue(), policyCheckParam.getDeviceId()));
			}
		}

		if (policyResult.getSktRestAmt() < 0.0) {
			policyResult.setSktRestAmt(0.0);
		}

		this.logger.debug("PRCHS,ORDER,SAC,POLICY,SKT,END,{},{}", policyCheckParam.getDeviceKey(), policyResult);
		return policyResult;
	}

	// ========================

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
		if (purchaseOrderInfo.getRealTotAmt() == 0.0) {
			return;
		}

		// ----------------------------------------------------------------
		// Store 관리 Test MDN 체크 (비과금 결제)

		List<PurchaseTenantPolicy> policyList = this.purchaseTenantPolicyService.searchPurchaseTenantPolicyList(
				purchaseOrderInfo.getTenantId(), purchaseOrderInfo.getTenantProdGrpCd(),
				PurchaseConstants.POLICY_PATTERN_STORE_TEST_DEVICE_CD, false);

		if (policyList.size() > 0) {

			policyList.get(0).getApplyValue();

			purchaseOrderInfo.getTenantId();
			purchaseOrderInfo.getUserKey();
			purchaseOrderInfo.getDeviceKey();

			// purchaseOrderInfo.setTestMdn(true);
			// purchaseOrderInfo.setFreePaymentMtdCd("OR000698"); // 테스트폰 결제
			// purchaseOrderInfo.setRealTotAmt(0.0);
			// return;
		}

		// ----------------------------------------------------------------
		// 구매차단 체크

		policyList = this.purchaseTenantPolicyService.searchPurchaseTenantPolicyList(purchaseOrderInfo.getTenantId(),
				purchaseOrderInfo.getTenantProdGrpCd(), PurchaseConstants.POLICY_PATTERN_USER_BLOCK_CD, false);

		if (policyList.size() > 0) {

			policyList.get(0).getApplyValue();

			purchaseOrderInfo.getTenantId();
			purchaseOrderInfo.getUserKey();
			purchaseOrderInfo.getDeviceKey();

			// purchaseOrderInfo.setBlockPayment(true);
		}

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
		this.logger.debug("PRCHS,ORDER,SAC,POLICY,START,{}", policy.getPolicyId());

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
		if (StringUtils.isNotBlank(policy.getCondPeriodUnitCd())) {
			sciRes = this.purchaseOrderSearchSCI.searchSktLimitCondDetail(sciReq);
			checkVal = (Double) sciRes.getVal();

			if (Double.parseDouble(policy.getCondClsfValue()) == 0) {
				if (checkVal != 0) {
					return null;
				}
			} else if (checkVal < Double.parseDouble(policy.getCondClsfValue())) {
				return null;
			}
		}

		// (정책 체크 값) 정책 요소기준 조회
		sciRes = this.purchaseOrderSearchSCI.searchSktAmountDetail(sciReq);
		checkVal = (Double) sciRes.getVal();

		this.logger.debug("PRCHS,ORDER,SAC,POLICY,END,{},{}", policy.getPolicyId(),
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
		this.logger.debug("PRCHS,ORDER,SAC,POLICY,START,{}", policy.getPolicyId());

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

		this.logger.debug("PRCHS,ORDER,SAC,POLICY,END,{},{}", policy.getPolicyId(),
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
	 * @param mdn 법인폰 여부 조회할 MDN
	 * 
	 * @return 법인폰 여부: true-법인폰, false-해당 법인폰 아님
	 */
	private boolean isCorporationMdn(String corpNum, String mdn) {
		try {
			UserEcRes userEcRes = this.uapsRespository.searchUapsAuthorizeInfoByMdn(corpNum, mdn);
			if (userEcRes != null) {
				return true;
			}
		} catch (StorePlatformException e) {
			// 2014.02.12. 기준 : EC UAPS 에서 비정상 예외 시 9999 리턴, 그 외에는 조회결과 없음(9997) 또는 명의상태에 따른 코드.
			if (StringUtils.equals(e.getErrorInfo().getCode(), "EC_UAPS_9999")) {
				throw e;
			} // else는 skip처리가 정상
		}
		return false;
	}

	/*
	 * 
	 * <pre> SKT 시험폰 여부 조회. </pre>
	 * 
	 * @param mdn SKT 시험폰 여부 조회할 MDN
	 * 
	 * @return SKT 시험폰 여부: true-SKT 시험폰, false-SKT 시험폰 아님
	 */
	private boolean isSktTestMdn(String mdn) {
		UserEcRes userEcRes = this.uapsRespository.searchUapsMappingInfoByMdn(mdn);
		return (StringUtils.equals(userEcRes.getSvcTP(), PurchaseConstants.UAPS_SVC_TP_SKTTEST));
	}

	/*
	 * 
	 * <pre> Store 서비스 허용하는 시험폰 White List 등록 여부 조회. </pre>
	 * 
	 * @param tenantId 테넌트 ID
	 * 
	 * @param mdn 조회할 MDN
	 * 
	 * @return White List 등록 여부: true-White List 등록, false-White List 등록 안됨
	 */
	private boolean isSktTestMdnWhiteList(String tenantId, String mdn) {
		// TAKTODO:: White List 관리 확인 및 조회 처리
		return false;
	}

	/*
	 * 
	 * <pre> SKT 시험폰 여부 조회. </pre>
	 * 
	 * @param mdn SKT 시험폰 여부 조회할 MDN
	 * 
	 * @return SKT 시험폰 여부: true-SKT 시험폰, false-SKT 시험폰 아님
	 */
	private boolean isMvno(String allowMvnoCode, String mdn) {
		UserEcRes userEcRes = this.uapsRespository.searchUapsMappingInfoByMdn(mdn);
		return StringUtils.equals(userEcRes.getMvnoCD(), allowMvnoCode);
	}
}
