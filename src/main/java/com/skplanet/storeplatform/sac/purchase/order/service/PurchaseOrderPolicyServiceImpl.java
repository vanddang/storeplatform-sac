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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.sap.sci.SapPurchaseSCI;
import com.skplanet.storeplatform.external.client.sap.vo.CheckPurchasePolicyEcReq;
import com.skplanet.storeplatform.external.client.sap.vo.CheckPurchasePolicyEcRes;
import com.skplanet.storeplatform.external.client.sap.vo.CheckPurchasePolicyInfoEc;
import com.skplanet.storeplatform.external.client.uaps.vo.UserEcRes;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.exception.vo.ErrorInfo;
import com.skplanet.storeplatform.purchase.client.common.vo.TenantSalePolicy;
import com.skplanet.storeplatform.purchase.client.order.sci.PurchaseOrderSCI;
import com.skplanet.storeplatform.purchase.client.order.sci.PurchaseOrderSearchSCI;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchSktPaymentScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchSktPaymentScRes;
import com.skplanet.storeplatform.sac.client.internal.member.miscellaneous.vo.IndividualPolicyInfoSac;
import com.skplanet.storeplatform.sac.purchase.common.service.PurchaseTenantPolicyService;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;
import com.skplanet.storeplatform.sac.purchase.order.repository.PurchaseIapRepository;
import com.skplanet.storeplatform.sac.purchase.order.repository.PurchaseMemberRepository;
import com.skplanet.storeplatform.sac.purchase.order.repository.PurchaseUapsRepository;
import com.skplanet.storeplatform.sac.purchase.order.vo.CheckPaymentPolicyParam;
import com.skplanet.storeplatform.sac.purchase.order.vo.CheckPaymentPolicyResult;
import com.skplanet.storeplatform.sac.purchase.order.vo.PaymethodAdjustPolicyInfo;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseOrderInfo;

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
	private SapPurchaseSCI sapPurchaseSCI;
	@Autowired
	private PurchaseOrderSearchSCI purchaseOrderSearchSCI;
	@Autowired
	private PurchaseTenantPolicyService purchaseTenantPolicyService;
	@Autowired
	private PurchaseMemberRepository purchaseMemberRepository;
	@Autowired
	private PurchaseUapsRepository uapsRepository;
	@Autowired
	private PurchaseIapRepository iapRepository;

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
		return CollectionUtils.isNotEmpty(this.purchaseTenantPolicyService.searchTenantSalePolicyList(tenantId,
				tenantProdGrpCd, PurchaseConstants.POLICY_PATTERN_DEVICE_BASED_PRCHSHST, false));
	}

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
	@Override
	public int searchtMileageSaveLimit(String tenantId, String tenantProdGrpCd) {
		List<TenantSalePolicy> policyList = this.purchaseTenantPolicyService.searchTenantSalePolicyList(tenantId,
				tenantProdGrpCd, PurchaseConstants.POLICY_PATTERN_TMILEAGE_SAVE_LIMIT, false);
		return CollectionUtils.isEmpty(policyList) ? 0 : Integer.parseInt(policyList.get(0).getApplyValue());
	}

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
	@Override
	public String searchtMileageSavePaymentMethod(String tenantId, String tenantProdGrpCd) {
		List<TenantSalePolicy> policyList = this.purchaseTenantPolicyService.searchTenantSalePolicyList(tenantId,
				tenantProdGrpCd, PurchaseConstants.POLICY_PATTERN_TMILEAGE_SAVE_PAYMENT_METHOD, false);
		return CollectionUtils.isEmpty(policyList) ? "" : policyList.get(0).getApplyValue();
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
		List<TenantSalePolicy> policyList = this.purchaseTenantPolicyService.searchTenantSalePolicyList(tenantId,
				tenantProdGrpCd, PurchaseConstants.POLICY_PATTERN_USER_BLOCK_CD, false);

		if (CollectionUtils.isEmpty(policyList)) {
			return false;
		}

		List<String> policyCodeList = new ArrayList<String>();
		for (TenantSalePolicy policy : policyList) {
			policyCodeList.add(policy.getApplyValue());
		}

		// 회원Part 사용자 정책 조회
		Map<String, IndividualPolicyInfoSac> policyResMap = this.purchaseMemberRepository.getPurchaseUserPolicy(
				tenantId, deviceId, policyCodeList);

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
		List<TenantSalePolicy> policyList = this.purchaseTenantPolicyService.searchTenantSalePolicyList(tenantId,
				tenantProdGrpCd, PurchaseConstants.POLICY_PATTERN_STORE_TEST_DEVICE_CD, false);

		if (CollectionUtils.isEmpty(policyList)) {
			return false;
		}

		List<String> policyCodeList = new ArrayList<String>();
		for (TenantSalePolicy policy : policyList) {
			policyCodeList.add(policy.getApplyValue());
		}

		// 회원Part 사용자 정책 조회
		Map<String, IndividualPolicyInfoSac> policyResMap = this.purchaseMemberRepository.getPurchaseUserPolicy(
				tenantId, deviceId, policyCodeList);

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
		this.logger.info("PRCHS,ORDER,SAC,POLICY,USER,START,{}", ReflectionToStringBuilder.toString(
				purchaseOrderInfo.getPurchaseUser(), ToStringStyle.SHORT_PREFIX_STYLE));

		if (purchaseOrderInfo.getRealTotAmt() == 0.0) {
			return;
		}

		// 비과금 (TEST MDN) 결제 여부
		if (this.isTestMdn(purchaseOrderInfo.getTenantId(), purchaseOrderInfo.getPurchaseUser().getDeviceId(),
				purchaseOrderInfo.getTenantProdGrpCd())) {
			purchaseOrderInfo.setTestMdn(true);
			purchaseOrderInfo.setFreePaymentMtdCd(PurchaseConstants.PAYMENT_METHOD_STORE_TEST_DEVICE);
			purchaseOrderInfo.setRealTotAmt(0.0);

			return;
		}

		// 구매차단 여부
		// if (this.isBlockPayment(purchaseOrderInfo.getTenantId(), purchaseOrderInfo.getPurchaseUser().getDeviceId(),
		// purchaseOrderInfo.getTenantProdGrpCd())) {
		// purchaseOrderInfo.setBlockPayment(true);
		// }
	}

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
	@Override
	public boolean isMileageSaveSktTestDevice(String tenantId, String deviceId, String tenantProdGrpCd) {

		// TAKTODO:: QA지원을 위한 임시 하드코딩

		// 테넌트 구매차단 정책 조회
		List<TenantSalePolicy> policyList = this.purchaseTenantPolicyService.searchTenantSalePolicyList(tenantId,
				tenantProdGrpCd, "TEMP_001", false);

		if (CollectionUtils.isEmpty(policyList)) {
			return false;
		}

		String policyValue = policyList.get(0).getApplyValue();
		if (StringUtils.isBlank(policyValue)) {
			return false;
		}

		for (String val : policyValue.split(";")) {
			if (StringUtils.equals(val, deviceId)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 
	 * <pre>
	 * 결제 정책 체크.
	 * </pre>
	 * 
	 * @param checkPaymentPolicyParam
	 *            정책 체크 대상 데이터
	 * @return 정책 체크 결과
	 */
	@Override
	public CheckPaymentPolicyResult checkPaymentPolicy(CheckPaymentPolicyParam checkPaymentPolicyParam) {
		this.logger.info("PRCHS,ORDER,SAC,POLICY,START,{}",
				ReflectionToStringBuilder.toString(checkPaymentPolicyParam, ToStringStyle.SHORT_PREFIX_STYLE));

		// --------------------------------------------------------------------------------------------------
		// 관련 정책 목록 조회

		Map<String, List<TenantSalePolicy>> policyListMap = this.purchaseTenantPolicyService
				.searchTenantSalePolicyListByMap(checkPaymentPolicyParam.getTenantId(),
						checkPaymentPolicyParam.getTenantProdGrpCd());

		// SAP 결제정책 조회 : 연동
		List<String> sapPolicyCdList = null;
		if (policyListMap.containsKey(PurchaseConstants.POLICY_ID_SAP_CHECK_POLICY)) {

			sapPolicyCdList = this.checkSapPurchasePolicy(checkPaymentPolicyParam.getTenantId(),
					checkPaymentPolicyParam.getMarketDeviceKey(), checkPaymentPolicyParam.getDeviceKeyAuth());

			if (CollectionUtils.isNotEmpty(sapPolicyCdList)
					&& sapPolicyCdList.contains(PurchaseConstants.SAP_POLICY_LIMIT_ALL)) { // 모든결제차단
				throw new StorePlatformException("SAC_PUR_6103");
			}

			policyListMap.remove(PurchaseConstants.POLICY_ID_SAP_CHECK_POLICY);
		}
		checkPaymentPolicyParam.setSapPolicyCdList(sapPolicyCdList);

		// --------------------------------------------------------------------------------------------------
		// 결제 정책 체크

		CheckPaymentPolicyResult checkPaymentPolicyResult = null;

		// 통신사 후불 결제 여부 : 테넌트 별 통신사 결제 여부
		boolean bTelecomPaymethod = StringUtils.equals(checkPaymentPolicyParam.getTelecom(),
				PurchaseConstants.TENANT_DEFAULT_TELECOM_MAP.get(checkPaymentPolicyParam.getTenantId()));

		// 후불/소액 기본 정책 정보 (결제수단 재정의 정책 미고려)
		String phonePaymethodBaseInfo = null;

		if (bTelecomPaymethod) { // 후불 사용하는 경우

			// 후불 관련 정책 체크: 정책에 의해 후불 제한되면 바로 return
			checkPaymentPolicyResult = this.checkPhonePaymentPolicy(checkPaymentPolicyParam, policyListMap);

			// 통신사 결제 처리 타입
			if (checkPaymentPolicyResult.isTelecomTestMdn()) {
				if (checkPaymentPolicyResult.isTelecomTestMdnWhiteList()) {
					checkPaymentPolicyResult.setDeferredPaymentType(PurchaseConstants.DEFERRED_PAYMENT_TYPE_TESTDEVICE);
					checkPaymentPolicyResult.setPhoneRestAmt(checkPaymentPolicyParam.getPaymentTotAmt());
				} else {
					checkPaymentPolicyResult.setDeferredPaymentType(PurchaseConstants.DEFERRED_PAYMENT_TYPE_ETCSERVICE);
				}
			} else if (checkPaymentPolicyResult.isCorporation() || checkPaymentPolicyResult.isMvno()) {
				checkPaymentPolicyResult.setDeferredPaymentType(PurchaseConstants.DEFERRED_PAYMENT_TYPE_ETCSERVICE);
			} else {
				checkPaymentPolicyResult.setDeferredPaymentType(PurchaseConstants.DEFERRED_PAYMENT_TYPE_NORMAL);
			}

			// 통신사 후불 재조정
			if (checkPaymentPolicyResult.getPhoneRestAmt() <= 0.0
					|| StringUtils.equals(checkPaymentPolicyResult.getDeferredPaymentType(),
							PurchaseConstants.DEFERRED_PAYMENT_TYPE_ETCSERVICE)) {
				phonePaymethodBaseInfo = "11:0:0;12:0:0";

			} else {
				phonePaymethodBaseInfo = "11:" + checkPaymentPolicyResult.getPhoneRestAmt() + ":100;12:0:0";
			}

		} else { // 소액 결제 사용 하는 경우 : 12는 별다른 정책 체크는 없음

			checkPaymentPolicyResult = new CheckPaymentPolicyResult();
			checkPaymentPolicyResult.setDeferredPaymentType(PurchaseConstants.DEFERRED_PAYMENT_TYPE_NORMAL);

			phonePaymethodBaseInfo = "11:0:0";
		}

		// 이용가능 결제수단 재정의 (결제수단 재정의 정책 기반)
		checkPaymentPolicyResult.setPaymentAdjInfo(this.adjustPaymethod(checkPaymentPolicyParam,
				phonePaymethodBaseInfo, bTelecomPaymethod));

		this.logger.info("PRCHS,ORDER,SAC,POLICY,END,{}",
				ReflectionToStringBuilder.toString(checkPaymentPolicyResult, ToStringStyle.SHORT_PREFIX_STYLE));

		return checkPaymentPolicyResult;
	}

	// ========================================================================================================================

	/*
	 * 
	 * <pre> SAP 결제정책 조회. </pre>
	 * 
	 * @param tenantId 테넌트ID
	 * 
	 * @param marketDeviceKey 통신사 디바이스 Key
	 * 
	 * @param deviceKeyAuth 통신사 디바이스 Key 인증값
	 * 
	 * @return 조회된 결제정책 코드값 리스트
	 */
	private List<String> checkSapPurchasePolicy(String tenantId, String marketDeviceKey, String deviceKeyAuth) {
		CheckPurchasePolicyEcReq checkPurchasePolicyEcReq = new CheckPurchasePolicyEcReq();
		checkPurchasePolicyEcReq.setTenantId(tenantId);
		checkPurchasePolicyEcReq.setDeviceKey(marketDeviceKey);
		checkPurchasePolicyEcReq.setDeviceKeyAuth(deviceKeyAuth);

		this.logger.info("PRCHS,ORDER,SAC,POLICY,SAP,REQ,{}",
				ReflectionToStringBuilder.toString(checkPurchasePolicyEcReq, ToStringStyle.SHORT_PREFIX_STYLE));

		CheckPurchasePolicyEcRes checkPurchasePolicyEcRes = null;
		try {
			checkPurchasePolicyEcRes = this.sapPurchaseSCI.checkPurchasePolicy(checkPurchasePolicyEcReq);
		} catch (Exception e) {
			this.logger.info("PRCHS,ORDER,SAC,POLICY,SAP,RES,EXCEPTION,", e);

			if (e instanceof StorePlatformException) {
				ErrorInfo errorInfo = ((StorePlatformException) e).getErrorInfo();
				throw new StorePlatformException("SAC_PUR_7221", errorInfo.getCode(), errorInfo.getMessage());

			} else {
				throw new StorePlatformException("SAC_PUR_7220", e);
			}
		}

		this.logger.info("PRCHS,ORDER,SAC,POLICY,SAP,RES,{}",
				ReflectionToStringBuilder.toString(checkPurchasePolicyEcRes, ToStringStyle.SHORT_PREFIX_STYLE));

		List<String> policyCodeList = null;
		if (CollectionUtils.isNotEmpty(checkPurchasePolicyEcRes.getPolicyList())) {
			policyCodeList = new ArrayList<String>();

			for (CheckPurchasePolicyInfoEc policy : checkPurchasePolicyEcRes.getPolicyList()) {
				policyCodeList.add(policy.getPolicyCode());
			}
		}

		this.logger.info("PRCHS,ORDER,SAC,POLICY,SAP,POLICYLIST,{}", policyCodeList);
		return policyCodeList;
	}

	/*
	 * 
	 * <pre> 통신사 후불 결제 진행 시 관련 정책 체크. </pre>
	 * 
	 * @param checkPaymentPolicyParam 정책 체크 대상 데이터
	 * 
	 * @param policyListMap 조회한 결제정책
	 * 
	 * @return 정책 체크 결과
	 */
	private CheckPaymentPolicyResult checkPhonePaymentPolicy(CheckPaymentPolicyParam checkPaymentPolicyParam,
			Map<String, List<TenantSalePolicy>> policyListMap) {
		this.logger.info("PRCHS,ORDER,SAC,POLICY,PHONE,START,{}",
				ReflectionToStringBuilder.toString(checkPaymentPolicyParam, ToStringStyle.SHORT_PREFIX_STYLE));

		CheckPaymentPolicyResult checkPaymentPolicyResult = new CheckPaymentPolicyResult();
		checkPaymentPolicyResult.setPhoneLimitType(PurchaseConstants.PHONE_ADJUST_REASON_NO_LIMIT);

		boolean bTstore = StringUtils.equals(checkPaymentPolicyParam.getTenantId(), PurchaseConstants.TENANT_ID_TSTORE);

		// --------------------------------------------------------------------------------------------------
		// UAPS Mapping정보 조회

		UserEcRes sktUapsMappingInfo = null;

		if (bTstore) {
			sktUapsMappingInfo = this.uapsRepository.searchUapsMappingInfoByMdn(checkPaymentPolicyParam.getDeviceId());

			// SKT 서비스 관리번호 세팅 : SKT 후불 결제금액 체크
			checkPaymentPolicyParam.setSktSvcMangNo(sktUapsMappingInfo.getSvcMngNum());
			this.logger.info("PRCHS,ORDER,SAC,POLICY,PHONE,SVCNUM,{}", sktUapsMappingInfo.getSvcMngNum());
		}

		List<TenantSalePolicy> policyList = null;

		// --------------------------------------------------------------------------------------------------
		// MVNO 체크

		if (policyListMap.containsKey(PurchaseConstants.POLICY_ID_MVNO_ALLOW_CD)) {
			policyList = policyListMap.get(PurchaseConstants.POLICY_ID_MVNO_ALLOW_CD);

			boolean bMvno = false;
			for (TenantSalePolicy policy : policyList) {
				bMvno = this.isMvno(checkPaymentPolicyParam.getTenantId(), sktUapsMappingInfo, policy.getApplyValue(),
						checkPaymentPolicyParam.getDeviceId());

				if (bMvno == false) {
					break;
				}
			}

			checkPaymentPolicyResult.setMvno(bMvno);

			if (bMvno) {
				checkPaymentPolicyResult.setPhoneLimitType(PurchaseConstants.PHONE_ADJUST_REASON_MVNO);
				return checkPaymentPolicyResult;
			}

			policyListMap.remove(PurchaseConstants.POLICY_ID_MVNO_ALLOW_CD);
		}

		// --------------------------------------------------------------------------------------------------
		// 법인폰 체크

		if (bTstore) { // Tstore : OCB 적립 제한을 위한 SKP법인폰 기본 조회
			if (this.isCorporationMdn(checkPaymentPolicyParam.getTenantId(), PurchaseConstants.SKP_CORPORATION_NO,
					checkPaymentPolicyParam.getDeviceId())) {
				checkPaymentPolicyResult.setSkpCorporation(true);
			}
		}

		boolean bAllowDevice = false; // 시험폰(법인폰) 제한 상품에 대해 구매 허용하는 디바이스 여부

		// 법인폰 제한 정책 체크

		if (policyListMap.containsKey(PurchaseConstants.POLICY_ID_CORP_DEVICE)) {
			policyList = policyListMap.get(PurchaseConstants.POLICY_ID_CORP_DEVICE);

			boolean bLimit = false;
			for (TenantSalePolicy policy : policyList) {
				// SKP법인폰은 위에서 이미 체크
				if (StringUtils.equals(policy.getApplyValue(), PurchaseConstants.SKP_CORPORATION_NO)) {
					bLimit = checkPaymentPolicyResult.isSkpCorporation();
				} else {
					bLimit = this.isCorporationMdn(checkPaymentPolicyParam.getTenantId(), policy.getApplyValue(),
							checkPaymentPolicyParam.getDeviceId());
				}

				if (bLimit) {
					break;
				}
			}

			// 제한 정책에 걸렸을 경우, 허용된 디바이스면 통과
			if (bLimit) {
				if (policyListMap.containsKey(PurchaseConstants.POLICY_ID_ALLOW_PURCHASE_DEVICE_CD)) {
					policyList = policyListMap.get(PurchaseConstants.POLICY_ID_ALLOW_PURCHASE_DEVICE_CD);

					for (TenantSalePolicy policy : policyList) {
						if (this.isAllowPurchaseCorpDevice(policy, checkPaymentPolicyParam)) {
							bAllowDevice = true;
							break;
						}
					}

					policyListMap.remove(PurchaseConstants.POLICY_ID_ALLOW_PURCHASE_DEVICE_CD);
				}

				if (bAllowDevice == false) {
					checkPaymentPolicyResult.setCorporation(true);
					checkPaymentPolicyResult.setPhoneLimitType(PurchaseConstants.PHONE_ADJUST_REASON_CORP);
					return checkPaymentPolicyResult;
				}
			}

			policyListMap.remove(PurchaseConstants.POLICY_ID_CORP_DEVICE);
		}

		// --------------------------------------------------------------------------------------------------
		// SKT 시험폰 체크 (참고, SKT시험폰이라면 100% 법인폰)

		// 회원측 관리 SAP 시험폰 정책 조회
		if (policyListMap.containsKey(PurchaseConstants.POLICY_ID_SAP_TEST_DEVICE)) {
			policyList = policyListMap.get(PurchaseConstants.POLICY_ID_SAP_TEST_DEVICE);

			policyListMap.remove(PurchaseConstants.POLICY_ID_SAP_TEST_DEVICE);
		} else {
			policyList = null;
		}

		if (this.isTelecomTestMdn(checkPaymentPolicyParam.getTenantId(), sktUapsMappingInfo,
				checkPaymentPolicyParam.getDeviceId(), policyList, checkPaymentPolicyParam.getSapPolicyCdList())) {
			checkPaymentPolicyResult.setTelecomTestMdn(true);
			boolean bWhite = false;

			if (bAllowDevice) {
				bWhite = true;

			} else {
				// CM011604: 서비스 허용 SKT 시험폰
				if (policyListMap.containsKey(PurchaseConstants.POLICY_ID_PHONE_TEST_DEVICE)) {
					policyList = policyListMap.get(PurchaseConstants.POLICY_ID_PHONE_TEST_DEVICE);

					for (TenantSalePolicy policy : policyList) {
						if (this.isTelecomTestMdnWhiteList(checkPaymentPolicyParam.getTenantId(),
								policy.getApplyValue(), checkPaymentPolicyParam.getDeviceId())) {
							bWhite = true;
							break;
						}
					}
				}
			}

			checkPaymentPolicyResult.setTelecomTestMdnWhiteList(bWhite);
			checkPaymentPolicyResult
					.setPhoneLimitType(bWhite ? PurchaseConstants.PHONE_ADJUST_REASON_SKTTEST_ALLOW : PurchaseConstants.PHONE_ADJUST_REASON_SKTTEST_NOT_ALLOW);

			return checkPaymentPolicyResult;
		}

		// --------------------------------------------------------------------------------------------------
		// 여기서부터는 ::: 후불 한도 제한 체크

		checkPaymentPolicyResult.setPhoneRestAmt(checkPaymentPolicyParam.getPaymentTotAmt());

		Double phoneRestAmtObj = null;

		// --
		// SAP 결제정책 체크

		if (CollectionUtils.isNotEmpty(checkPaymentPolicyParam.getSapPolicyCdList())) {
			if (checkPaymentPolicyParam.getSapPolicyCdList().contains(PurchaseConstants.SAP_POLICY_LIMIT_PHONEBILL)) {
				checkPaymentPolicyResult.setPhoneLimitType(PurchaseConstants.PHONE_ADJUST_REASON_SAP_LIMIT_PHONEBILL);
				checkPaymentPolicyResult.setPhoneRestAmt(0.0);

				return checkPaymentPolicyResult;

			} else if (checkPaymentPolicyParam.getSapPolicyCdList().contains(
					PurchaseConstants.SAP_POLICY_LIMIT_IAP_PHONEBILL)
					&& StringUtils.startsWith(checkPaymentPolicyParam.getTenantProdGrpCd(),
							PurchaseConstants.TENANT_PRODUCT_GROUP_IAP)) {
				checkPaymentPolicyResult
						.setPhoneLimitType(PurchaseConstants.PHONE_ADJUST_REASON_SAP_LIMIT_IAP_PHONEBILL);
				checkPaymentPolicyResult.setPhoneRestAmt(0.0);

				return checkPaymentPolicyResult;
			}
		}

		// --
		// 후불 쇼핑상품 한도금액 제한

		if (policyListMap.containsKey(PurchaseConstants.POLICY_ID_PHONE_SHOPPING_PRCHS_LIMIT)) {
			policyList = policyListMap.get(PurchaseConstants.POLICY_ID_PHONE_SHOPPING_PRCHS_LIMIT);

			// 회원 측 강제설정 적용
			for (TenantSalePolicy policy : policyList) {
				if (StringUtils.equals(policy.getProcPatternCd(),
						PurchaseConstants.POLICY_PATTERN_PHONE_SHOPPING_PRCHS_USER_LIMIT) == false) {
					continue;
				}

				phoneRestAmtObj = this.checkPhoneShoppingUserLimitRest(policy, checkPaymentPolicyParam);

				if (phoneRestAmtObj != null) {
					if (phoneRestAmtObj.doubleValue() < checkPaymentPolicyResult.getPhoneRestAmt()) {
						checkPaymentPolicyResult
								.setPhoneLimitType(PurchaseConstants.PHONE_ADJUST_REASON_SHOPPING_PRCHS_USERPART_LIMIT);
						checkPaymentPolicyResult.setPhoneRestAmt(phoneRestAmtObj.doubleValue());
					}

					if (phoneRestAmtObj.doubleValue() <= 0.0) {
						checkPaymentPolicyResult.setPhoneRestAmt(0.0);
						return checkPaymentPolicyResult;
					}
				}
			}

			// 잔여 한도 조회 적용 : 회원 측 강제적용 값이 없는 경우에만 진행
			if (phoneRestAmtObj == null) {

				for (TenantSalePolicy policy : policyList) {

					if (StringUtils.equals(policy.getProcPatternCd(),
							PurchaseConstants.POLICY_PATTERN_PHONE_PRCHS_LIMIT)) { // CM011601: SKT후불 결제 한도제한

						phoneRestAmtObj = this.checkPhoneLimitRest(policy, checkPaymentPolicyParam);

						if (phoneRestAmtObj != null
								&& phoneRestAmtObj.doubleValue() < checkPaymentPolicyResult.getPhoneRestAmt()) {
							checkPaymentPolicyResult
									.setPhoneLimitType(PurchaseConstants.PHONE_ADJUST_REASON_SHOPPING_LIMIT);
							checkPaymentPolicyResult.setPhoneRestAmt(phoneRestAmtObj.doubleValue());

							if (phoneRestAmtObj.doubleValue() <= 0.0) {
								checkPaymentPolicyResult.setPhoneRestAmt(0.0);
								return checkPaymentPolicyResult;
							}
						}

					} else if (StringUtils.equals(policy.getProcPatternCd(),
							PurchaseConstants.POLICY_PATTERN_PHONE_RECV_LIMIT)) { // CM011602: SKT후불 선물수신 한도제한

						if (StringUtils.isEmpty(checkPaymentPolicyParam.getRecvTenantId())) {
							continue;
						}

						phoneRestAmtObj = this.checkPhoneRecvLimit(policy, checkPaymentPolicyParam);

						if (phoneRestAmtObj != null
								&& phoneRestAmtObj.doubleValue() < checkPaymentPolicyResult.getPhoneRestAmt()) {
							checkPaymentPolicyResult
									.setPhoneLimitType(PurchaseConstants.PHONE_ADJUST_REASON_SHOPPING_RECV_LIMIT);
							checkPaymentPolicyResult.setPhoneRestAmt(phoneRestAmtObj.doubleValue());

							if (phoneRestAmtObj.doubleValue() <= 0.0) {
								checkPaymentPolicyResult.setPhoneRestAmt(0.0);
								return checkPaymentPolicyResult;
							}
						}

					}
				}
			}

			policyListMap.remove(PurchaseConstants.POLICY_ID_PHONE_SHOPPING_PRCHS_LIMIT);
		}

		// --
		// 통신사 후불 한도금액 제한

		if (policyListMap.containsKey(PurchaseConstants.POLICY_ID_PHONE_PRCHS_LIMIT)) {
			policyList = policyListMap.get(PurchaseConstants.POLICY_ID_PHONE_PRCHS_LIMIT);

			for (TenantSalePolicy policy : policyList) {
				// CM011601: SKT후불 결제 한도제한
				if (StringUtils.equals(policy.getProcPatternCd(), PurchaseConstants.POLICY_PATTERN_PHONE_PRCHS_LIMIT) == false) {
					continue;
				}

				phoneRestAmtObj = this.checkPhoneLimitRest(policy, checkPaymentPolicyParam);

				if (phoneRestAmtObj != null
						&& phoneRestAmtObj.doubleValue() < checkPaymentPolicyResult.getPhoneRestAmt()) {
					checkPaymentPolicyResult.setPhoneLimitType(PurchaseConstants.PHONE_ADJUST_REASON_LIMIT);
					checkPaymentPolicyResult.setPhoneRestAmt(phoneRestAmtObj.doubleValue());

					if (phoneRestAmtObj.doubleValue() <= 0.0) {
						checkPaymentPolicyResult.setPhoneRestAmt(0.0);
						return checkPaymentPolicyResult;
					}
				}
			}

			policyListMap.remove(PurchaseConstants.POLICY_ID_PHONE_PRCHS_LIMIT);
		}

		// --
		// SKT 후불 선물수신 한도금액 제한

		if (StringUtils.isNotBlank(checkPaymentPolicyParam.getRecvTenantId())) {

			// 쇼핑상품 선물수신 한도 회원별 강제적용

			if (policyListMap.containsKey(PurchaseConstants.POLICY_ID_PHONE_SHOPPING_RECV_LIMIT)) {
				policyList = policyListMap.get(PurchaseConstants.POLICY_ID_PHONE_SHOPPING_RECV_LIMIT);

				// 회원 측 강제설정 적용
				for (TenantSalePolicy policy : policyList) {
					if (StringUtils.equals(policy.getProcPatternCd(),
							PurchaseConstants.POLICY_PATTERN_PHONE_SHOPPING_RECV_USER_LIMIT) == false) { // CM011615
						continue;
					}

					phoneRestAmtObj = this.checkPhoneShoppingUserLimitRest(policy, checkPaymentPolicyParam);

					if (phoneRestAmtObj != null) {
						if (phoneRestAmtObj.doubleValue() < checkPaymentPolicyResult.getPhoneRestAmt()) {
							checkPaymentPolicyResult
									.setPhoneLimitType(PurchaseConstants.PHONE_ADJUST_REASON_SHOPPING_RECV_USERPART_LIMIT);
							checkPaymentPolicyResult.setPhoneRestAmt(phoneRestAmtObj.doubleValue());
						}

						if (phoneRestAmtObj.doubleValue() <= 0.0) {
							checkPaymentPolicyResult.setPhoneRestAmt(0.0);
							return checkPaymentPolicyResult;
						}
					}
				}
			}

			// 기본 선물수신 한도금액 제한

			if (policyListMap.containsKey(PurchaseConstants.POLICY_ID_PHONE_RECV_LIMIT)) {
				policyList = policyListMap.get(PurchaseConstants.POLICY_ID_PHONE_RECV_LIMIT);

				for (TenantSalePolicy policy : policyList) {
					if (StringUtils
							.equals(policy.getProcPatternCd(), PurchaseConstants.POLICY_PATTERN_PHONE_RECV_LIMIT) == false) {
						continue;
					}

					phoneRestAmtObj = this.checkPhoneRecvLimit(policy, checkPaymentPolicyParam);

					if (phoneRestAmtObj != null
							&& phoneRestAmtObj.doubleValue() < checkPaymentPolicyResult.getPhoneRestAmt()) {
						checkPaymentPolicyResult.setPhoneLimitType(PurchaseConstants.PHONE_ADJUST_REASON_RECV_LIMIT);
						checkPaymentPolicyResult.setPhoneRestAmt(phoneRestAmtObj.doubleValue());

						if (phoneRestAmtObj.doubleValue() <= 0.0) {
							checkPaymentPolicyResult.setPhoneRestAmt(0.0);
							return checkPaymentPolicyResult;
						}
					}
				}
			}
		}

		if (checkPaymentPolicyResult.getPhoneRestAmt() > checkPaymentPolicyParam.getPaymentTotAmt()) {
			checkPaymentPolicyResult.setPhoneRestAmt(checkPaymentPolicyParam.getPaymentTotAmt());
		}

		this.logger.info("PRCHS,ORDER,SAC,POLICY,PHONE,END,{},{}", checkPaymentPolicyParam.getDeviceKey(),
				ReflectionToStringBuilder.toString(checkPaymentPolicyResult, ToStringStyle.SHORT_PREFIX_STYLE));

		return checkPaymentPolicyResult;
	}

	/*
	 * 
	 * <pre> 결제차단 MVNO 회선 여부 조회. </pre>
	 * 
	 * @param tenantId 테넌트 ID
	 * 
	 * @param sktUapsMappingInfo SKT UAPS Mapping Info
	 * 
	 * @param deviceId 결제차단 MVNO 회선 여부 조회할 MDN
	 * 
	 * @return 결제차단 MVNO 회선 여부: true-결제 차단, false-결제 허용
	 */
	private boolean isMvno(String tenantId, UserEcRes sktUapsMappingInfo, String allowMvnoCode, String deviceId) {
		this.logger.info("PRCHS,ORDER,SAC,POLICY,MVNO,START,{},{}", deviceId, allowMvnoCode);

		boolean bMvno = false;

		String userMvnoCd = null;

		if (StringUtils.equals(tenantId, PurchaseConstants.TENANT_ID_TSTORE)) {
			if (sktUapsMappingInfo == null) {
				sktUapsMappingInfo = this.uapsRepository.searchUapsMappingInfoByMdn(deviceId);
			}

			userMvnoCd = sktUapsMappingInfo.getMvnoCD();
			bMvno = (StringUtils.equals(userMvnoCd, allowMvnoCode) == false);
		}

		this.logger.info("PRCHS,ORDER,SAC,POLICY,MVNO,END,{},{},{},{}", deviceId, userMvnoCd, allowMvnoCode, bMvno);

		return bMvno;
	}

	/*
	 * 
	 * <pre> 법인폰 여부 조회. </pre>
	 * 
	 * @param tenantId 테넌트 ID
	 * 
	 * @param corpNum 법인번호
	 * 
	 * @param deviceId 법인폰 여부 조회할 MDN
	 * 
	 * @return 법인폰 여부: true-법인폰, false-해당 법인폰 아님
	 */
	private boolean isCorporationMdn(String tenantId, String corpNum, String deviceId) {
		this.logger.info("PRCHS,ORDER,SAC,POLICY,CORP,START,{},{}", corpNum, deviceId);

		if (StringUtils.equals(tenantId, PurchaseConstants.TENANT_ID_TSTORE)) {

			try {
				UserEcRes userEcRes = this.uapsRepository.searchUapsAuthorizeInfoByMdn(corpNum, deviceId);
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
		}

		this.logger.info("PRCHS,ORDER,SAC,POLICY,CORP,END,false");

		return false;
	}

	/*
	 * 
	 * <pre> 쇼핑상품 구매 허용된 시험폰(법인폰) 여부 조회. </pre>
	 * 
	 * @param policy 테넌트 정책 정보
	 * 
	 * @param checkPaymentPolicyParam 정책 체크 대상 데이터
	 * 
	 * @return 쇼핑상품 구매 허용된 시험폰(법인폰) 여부
	 */
	private boolean isAllowPurchaseCorpDevice(TenantSalePolicy policy, CheckPaymentPolicyParam checkPaymentPolicyParam) {
		this.logger.info("PRCHS,ORDER,SAC,POLICY,ALLOWPURCHASE,START,{}({})", policy.getPolicyId(),
				policy.getPolicySeq());

		// ----------------------------------------------------------------
		// 회원Part 사용자 정책 조회

		String memberPolicyCd = policy.getApplyValue();

		List<String> policyCodeList = new ArrayList<String>();
		policyCodeList.add(memberPolicyCd);

		Map<String, IndividualPolicyInfoSac> policyResMap = this.purchaseMemberRepository.getPurchaseUserPolicy(
				checkPaymentPolicyParam.getTenantId(), checkPaymentPolicyParam.getDeviceId(), policyCodeList);

		boolean bApply = false;

		if (policyResMap != null && policyResMap.containsKey(memberPolicyCd)) {
			bApply = true;
		}

		this.logger.info("PRCHS,ORDER,SAC,POLICY,ALLOWPURCHASE,END,{}({}),{}", policy.getPolicyId(),
				policy.getPolicySeq(), bApply);

		return bApply;
	}

	/*
	 * 
	 * <pre> SKT 시험폰 여부 조회. </pre>
	 * 
	 * @param tenantId 테넌트 ID
	 * 
	 * @param sktUapsMappingInfo SKT UAPS Mapping Info
	 * 
	 * @param deviceId SKT 시험폰 여부 조회할 MDN
	 * 
	 * @param policyList 회원정책으로 관리하는 SAP 통신사 시험폰 정책 목록
	 * 
	 * @return SKT 시험폰 여부: true-SKT 시험폰, false-SKT 시험폰 아님
	 */
	private boolean isTelecomTestMdn(String tenantId, UserEcRes sktUapsMappingInfo, String deviceId,
			List<TenantSalePolicy> policyList, List<String> sapPolicyCdList) {
		boolean bTelecomTestMdn = false;

		// UAPS 조회
		this.logger.info("PRCHS,ORDER,SAC,POLICY,TELECOMTEST,START,{}", deviceId);

		if (StringUtils.equals(tenantId, PurchaseConstants.TENANT_ID_TSTORE)) {

			if (sktUapsMappingInfo == null) {
				sktUapsMappingInfo = this.uapsRepository.searchUapsMappingInfoByMdn(deviceId);
			}

			bTelecomTestMdn = StringUtils.equals(sktUapsMappingInfo.getSvcTP(), PurchaseConstants.UAPS_SVC_TP_SKTTEST);
		}

		this.logger.info("PRCHS,ORDER,SAC,POLICY,TELECOMTEST,END,{},{}", deviceId, bTelecomTestMdn);

		if (bTelecomTestMdn) {
			return true;
		}

		// SAP 결제정책 조회 체크
		if (CollectionUtils.isNotEmpty(sapPolicyCdList)) {
			if (sapPolicyCdList.contains(PurchaseConstants.SAP_POLICY_TEST_PHONE)) {
				return true;
			}
		}

		// 회원DB 관리 조회
		if (CollectionUtils.isNotEmpty(policyList)) {
			this.logger.info("PRCHS,ORDER,SAC,POLICY,TELECOMTEST,MEMBER,START,{}", deviceId);

			for (TenantSalePolicy policy : policyList) {
				if (this.isSapTestMdn(tenantId, policy.getApplyValue(), deviceId)) {
					bTelecomTestMdn = true;
					break;
				}
			}

			this.logger.info("PRCHS,ORDER,SAC,POLICY,TELECOMTEST,MEMBER,END,{},{}", deviceId, bTelecomTestMdn);
		}

		return bTelecomTestMdn;
	}

	/*
	 * 
	 * <pre> Store 서비스 허용하는 시험폰 White List 등록 여부 조회. </pre>
	 * 
	 * @param memberPolicyCd 회원Part에서 관리하는 시험폰 정책코드
	 * 
	 * @param tenantId 테넌트 ID
	 * 
	 * @param deviceId 조회할 MDN
	 * 
	 * @return White List 등록 여부: true-White List 등록, false-White List 등록 안됨
	 */
	private boolean isTelecomTestMdnWhiteList(String tenantId, String memberPolicyCd, String deviceId) {

		// 2014.10.15. PASS 코드 적용
		if (StringUtils.equals(memberPolicyCd, "PASS")) {
			return true;
		}

		List<String> policyCodeList = new ArrayList<String>();
		policyCodeList.add(memberPolicyCd);

		Map<String, IndividualPolicyInfoSac> policyResMap = this.purchaseMemberRepository.getPurchaseUserPolicy(
				tenantId, deviceId, policyCodeList);

		if (policyResMap != null && policyResMap.containsKey(memberPolicyCd)) {
			return true;
		}

		return false;
	}

	/*
	 * 
	 * <pre> SAP지원으로 회원정책에 등록된 시험폰 여부 조회. </pre>
	 * 
	 * @param memberPolicyCd 회원Part에서 관리하는 시험폰 정책코드
	 * 
	 * @param tenantId 테넌트 ID
	 * 
	 * @param deviceId 조회할 MDN
	 * 
	 * @return (통신사) 시험폰 여부
	 */
	private boolean isSapTestMdn(String tenantId, String memberPolicyCd, String deviceId) {

		List<String> policyCodeList = new ArrayList<String>();
		policyCodeList.add(memberPolicyCd);

		Map<String, IndividualPolicyInfoSac> policyResMap = this.purchaseMemberRepository.getPurchaseUserPolicy(
				tenantId, deviceId, policyCodeList);

		if (policyResMap != null && policyResMap.containsKey(memberPolicyCd)) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * 
	 * <pre> 회원Part 강제적용 : 통신사 후불 쇼핑상품 구매결제 한도제한 남은 금액 조회. </pre>
	 * 
	 * @param policy 테넌트 정책 정보
	 * 
	 * @param checkPaymentPolicyParam 정책 체크 대상 데이터
	 * 
	 * @return 남은 후불 결제 가능 금액
	 */
	private Double checkPhoneShoppingUserLimitRest(TenantSalePolicy policy,
			CheckPaymentPolicyParam checkPaymentPolicyParam) {
		this.logger.info("PRCHS,ORDER,SAC,POLICY,USERLIMIT,START,{}({})", policy.getPolicyId(), policy.getPolicySeq());

		// ----------------------------------------------------------------
		// 회원Part 사용자 정책 조회

		String memberPolicyCd = policy.getApplyValue();

		List<String> policyCodeList = new ArrayList<String>();
		policyCodeList.add(memberPolicyCd);

		boolean bCheckRecv = StringUtils.equals(policy.getPolicyId(),
				PurchaseConstants.POLICY_ID_PHONE_SHOPPING_RECV_LIMIT);

		// 구매자/수신자 체크
		String deviceId = bCheckRecv ? checkPaymentPolicyParam.getRecvDeviceId() : checkPaymentPolicyParam
				.getDeviceId();

		Map<String, IndividualPolicyInfoSac> policyResMap = this.purchaseMemberRepository.getPurchaseUserPolicy(
				checkPaymentPolicyParam.getTenantId(), deviceId, policyCodeList);

		if (policyResMap == null || policyResMap.containsKey(memberPolicyCd) == false) {
			this.logger.info("PRCHS,ORDER,SAC,POLICY,USERLIMIT,END,{}({}),null", policy.getPolicyId(),
					policy.getPolicySeq());
			return null;
		}

		IndividualPolicyInfoSac individualPolicyInfoSac = policyResMap.get(memberPolicyCd);

		this.logger.info("PRCHS,ORDER,SAC,POLICY,USERLIMIT,MEMBER,{}({}),{}", policy.getPolicyId(),
				policy.getPolicySeq(), individualPolicyInfoSac.getLimitAmount());

		// ----------------------------------------------------------------
		// 회원 구매/선물수신 금액 조회

		Double restVal = Double.parseDouble(individualPolicyInfoSac.getLimitAmount());

		if (StringUtils.equals(checkPaymentPolicyParam.getTenantId(), PurchaseConstants.TENANT_ID_TSTORE)) {

			// 당월 구매(선물수신) 금액
			SearchSktPaymentScReq sciReq = new SearchSktPaymentScReq();
			SearchSktPaymentScRes sciRes = null;

			sciReq.setTenantProdGrpCd(policy.getTenantProdGrpCd());
			sciReq.setApplyUnitCd("CM011301"); // 금액
			sciReq.setCondUnitCd("CM011408"); // 당월
			sciReq.setCondValue("1"); // 1

			if (bCheckRecv) {
				sciReq.setTenantId(checkPaymentPolicyParam.getRecvTenantId());
				sciReq.setUserKey(checkPaymentPolicyParam.getRecvUserKey());
				sciReq.setDeviceKey(checkPaymentPolicyParam.getRecvDeviceKey());

				sciRes = this.purchaseOrderSearchSCI.searchSktRecvAmountDetail(sciReq);
				this.logger.info("PRCHS,ORDER,SAC,POLICY,USERLIMIT,GIFT,{}", sciRes.getVal());

			} else {
				sciReq.setTenantId(checkPaymentPolicyParam.getTenantId());
				sciReq.setUserKey(checkPaymentPolicyParam.getUserKey());
				sciReq.setDeviceKey(checkPaymentPolicyParam.getDeviceKey());
				sciReq.setSvcMangNo(checkPaymentPolicyParam.getSktSvcMangNo());

				sciRes = this.purchaseOrderSearchSCI.searchSktAmountDetail(sciReq);
				this.logger.info("PRCHS,ORDER,SAC,POLICY,USERLIMIT,PRCHS,{}", sciRes.getVal());
			}

			restVal = Double.parseDouble(individualPolicyInfoSac.getLimitAmount())
					- ((Double) sciRes.getVal()).doubleValue();

			this.logger.info("PRCHS,ORDER,SAC,POLICY,USERLIMIT,END,{}({}),{}", policy.getPolicyId(),
					policy.getPolicySeq(), restVal);
		}

		return restVal;
	}

	/*
	 * 
	 * <pre> 통신사 후불 구매결제 한도제한 남은 금액 조회. </pre>
	 * 
	 * @param policy 테넌트 정책 정보
	 * 
	 * @param checkPaymentPolicyParam 정책 체크 대상 데이터
	 * 
	 * @return 남은 후불 결제 가능 금액
	 */
	private Double checkPhoneLimitRest(TenantSalePolicy policy, CheckPaymentPolicyParam checkPaymentPolicyParam) {
		this.logger.info("PRCHS,ORDER,SAC,POLICY,START,{}({})", policy.getPolicyId(), policy.getPolicySeq());

		double checkVal = 0.0;

		SearchSktPaymentScReq sciReq = new SearchSktPaymentScReq();
		SearchSktPaymentScRes sciRes = null;

		sciReq.setTenantId(checkPaymentPolicyParam.getTenantId());
		sciReq.setUserKey(checkPaymentPolicyParam.getUserKey());
		sciReq.setDeviceKey(checkPaymentPolicyParam.getDeviceKey());
		sciReq.setTenantProdGrpCd(policy.getTenantProdGrpCd());
		sciReq.setApplyUnitCd(policy.getApplyUnitCd());
		sciReq.setCondUnitCd(policy.getCondUnitCd());
		sciReq.setCondValue(policy.getCondValue());
		sciReq.setCondClsfUnitCd(policy.getCondClsfUnitCd());
		sciReq.setCondPeriodUnitCd(policy.getCondPeriodUnitCd());
		sciReq.setCondPeriodValue(policy.getCondPeriodValue());
		sciReq.setSvcMangNo(checkPaymentPolicyParam.getSktSvcMangNo()); // SKT 서비스 관리번호

		// (정책 적용조건) 과금조건 조회
		// 쇼핑상품 구매건수 조회용도 이며, 전월 단위의 건수 조회인 경우만 처리

		if (StringUtils.equals(checkPaymentPolicyParam.getTenantId(), PurchaseConstants.TENANT_ID_TSTORE)) {

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
					this.logger.info("PRCHS,ORDER,SAC,POLICY,END,{}({}),pass", policy.getPolicyId(),
							policy.getPolicySeq());
					return null;
				}
			}

			// (정책 체크 값) 정책 요소기준 조회
			sciRes = this.purchaseOrderSearchSCI.searchSktAmountDetail(sciReq);
			checkVal = (Double) sciRes.getVal();

		}

		this.logger.info("PRCHS,ORDER,SAC,POLICY,END,{}({}),{},{}", policy.getPolicyId(), policy.getPolicySeq(),
				checkVal, (Double.parseDouble(policy.getApplyValue()) - checkVal));
		return (Double.parseDouble(policy.getApplyValue()) - checkVal);
	}

	/*
	 * 
	 * <pre> 통신사 후불 선물수신 한도제한 남은 금액 조회. </pre>
	 * 
	 * @param policy 테넌트 정책 정보
	 * 
	 * @param checkPaymentPolicyParam 정책 체크 대상 데이터
	 * 
	 * @return 남은 후불 선물수신 가능 금액
	 */
	private Double checkPhoneRecvLimit(TenantSalePolicy policy, CheckPaymentPolicyParam checkPaymentPolicyParam) {
		this.logger.info("PRCHS,ORDER,SAC,POLICY,START,{}({})", policy.getPolicyId(), policy.getPolicySeq());

		double checkVal = 0.0;

		SearchSktPaymentScReq sciReq = new SearchSktPaymentScReq();
		SearchSktPaymentScRes sciRes = null;

		sciReq.setTenantId(checkPaymentPolicyParam.getRecvTenantId()); // 수신자 기준 조회
		sciReq.setUserKey(checkPaymentPolicyParam.getRecvUserKey()); // 수신자 기준 조회
		sciReq.setDeviceKey(checkPaymentPolicyParam.getRecvDeviceKey()); // 수신자 기준 조회
		sciReq.setTenantProdGrpCd(policy.getTenantProdGrpCd());
		sciReq.setApplyUnitCd(policy.getApplyUnitCd());
		sciReq.setCondUnitCd(policy.getCondUnitCd());
		sciReq.setCondValue(policy.getCondValue());
		sciReq.setCondClsfUnitCd(policy.getCondClsfUnitCd());
		sciReq.setCondPeriodUnitCd(policy.getCondPeriodUnitCd());
		sciReq.setCondPeriodValue(policy.getCondPeriodValue());

		// 수신 금액 조회
		if (StringUtils.equals(checkPaymentPolicyParam.getTenantId(), PurchaseConstants.TENANT_ID_TSTORE)) {
			sciRes = this.purchaseOrderSearchSCI.searchSktRecvAmountDetail(sciReq);
			checkVal = (Double) sciRes.getVal();
		}

		this.logger.info("PRCHS,ORDER,SAC,POLICY,END,{}({}),{}", policy.getPolicyId(), policy.getPolicySeq(),
				(Double.parseDouble(policy.getApplyValue()) - checkVal));
		return (Double.parseDouble(policy.getApplyValue()) - checkVal);
	}

	/*
	 * 
	 * <pre> 결제 수단 재정의. </pre>
	 * 
	 * @param phonePaymethodBaseInfo 후불/소액 기본 정책 정보
	 * 
	 * @param checkPaymentPolicyParam 정책 체크 대상 데이터
	 * 
	 * @return 재정의 된 결제 수단 정보
	 */
	private String adjustPaymethod(CheckPaymentPolicyParam checkPaymentPolicyParam, String phonePaymethodBaseInfo,
			boolean bTelecomPaymethod) {
		String tenantId = checkPaymentPolicyParam.getTenantId();
		// String systemId = checkPaymentPolicyParam.getSystemId();
		String tenantProdGrpCd = checkPaymentPolicyParam.getTenantProdGrpCd();
		String prodCaseCd = checkPaymentPolicyParam.getProdCaseCd();
		String cmpxProdClsfCd = checkPaymentPolicyParam.getCmpxProdClsfCd();
		double payAmt = checkPaymentPolicyParam.getPaymentTotAmt();
		String prodId = checkPaymentPolicyParam.getProdId();
		String parentProdId = checkPaymentPolicyParam.getParentProdId();

		// 상품타입: 쇼핑 경우 - VOD/이북 정액상품 경우 추가
		String prodKindCd = StringUtils.isNotBlank(prodCaseCd) ? prodCaseCd : (StringUtils.isNotBlank(cmpxProdClsfCd) ? cmpxProdClsfCd : null);

		// ---------------------------------------------------------------------------------------------------
		// 결제수단 별 가능 거래금액/비율 조정 정책 조회

		TenantSalePolicy paymentPolicy = this.purchaseTenantPolicyService.searchPaymentPolicy(tenantId,
				tenantProdGrpCd, prodKindCd, prodId, parentProdId);
		if (paymentPolicy == null) {
			throw new StorePlatformException("SAC_PUR_7103");
		}

		String paymethodAdjustInfo = StringUtils.defaultString(paymentPolicy.getApplyValue(), "");
		this.logger.info("PRCHS,ORDER,SAC,POLICY,ADJUST,PRE,{},{}", phonePaymethodBaseInfo, paymethodAdjustInfo);

		List<PaymethodAdjustPolicyInfo> paymethodAdjustPolicyList = new ArrayList<PaymethodAdjustPolicyInfo>();

		String[] arInfo = null;

		if (StringUtils.isNotBlank(paymethodAdjustInfo)) {
			for (String paymethodInfo : paymethodAdjustInfo.split(";")) {
				arInfo = paymethodInfo.split(":");

				PaymethodAdjustPolicyInfo policyInfo = new PaymethodAdjustPolicyInfo();
				policyInfo.setPaymethodCode(arInfo[0]);
				policyInfo.setAvailAmt(Double.parseDouble(arInfo[1].replaceAll("MAXAMT", String.valueOf(payAmt))));
				policyInfo.setAvailPer((int) (Double.parseDouble(arInfo[2])));

				paymethodAdjustPolicyList.add(policyInfo);
			}
		}

		// ---------------------------------------------------------------------------------------------------
		// 휴대폰 정책 결정

		StringBuffer sbPaymethodAdjustInfoWithoutPhone = new StringBuffer(64);
		String phonePaymethodInfo = null;

		List<String> phoneCodeList = new ArrayList<String>();
		phoneCodeList.add("11");
		phoneCodeList.add("12");

		String baseTargetPhoneCode = bTelecomPaymethod ? "11" : "12";

		// 결제수단 정책에서 휴대폰 정책 추출
		PaymethodAdjustPolicyInfo targetPhonePolicy = null;

		PaymethodAdjustPolicyInfo paymethodPolicy = null;

		Iterator<PaymethodAdjustPolicyInfo> itr = paymethodAdjustPolicyList.iterator();

		while (itr.hasNext()) {
			paymethodPolicy = itr.next();

			if (StringUtils.equals(baseTargetPhoneCode, paymethodPolicy.getPaymethodCode())) {
				targetPhonePolicy = paymethodPolicy;
			}

			if (phoneCodeList.contains(paymethodPolicy.getPaymethodCode())) {
				itr.remove();

			} else {
				if (sbPaymethodAdjustInfoWithoutPhone.length() > 0) {
					sbPaymethodAdjustInfoWithoutPhone.append(";");
				}
				sbPaymethodAdjustInfoWithoutPhone.append(paymethodPolicy.getPaymethodCode()).append(":")
						.append(paymethodPolicy.getAvailAmt()).append(":").append(paymethodPolicy.getAvailPer());
			}
		}

		paymethodAdjustInfo = sbPaymethodAdjustInfoWithoutPhone.toString(); // 휴대폰들 제거

		// // 휴대폰 기본 정책 추출
		// PaymethodAdjustPolicyInfo baseTargetPhonePolicy = null;
		//
		// PaymethodAdjustPolicyInfo basePhonePolicy11 = null;
		// PaymethodAdjustPolicyInfo basePhonePolicy12 = null;
		//
		// if (phonePaymethodBaseInfo != null) {
		// for (String phoneCode : phoneCodeList) {
		// int codePos = phonePaymethodBaseInfo.indexOf(phoneCode + ":");
		// if (codePos >= 0) {
		// int sepPos = phonePaymethodBaseInfo.indexOf(";", codePos);
		// String baseInfo = null;
		// if (sepPos >= 0) {
		// baseInfo = phonePaymethodBaseInfo.substring(codePos, sepPos);
		// } else {
		// baseInfo = phonePaymethodBaseInfo.substring(codePos);
		// }
		// arInfo = baseInfo.split(":");
		//
		// if (StringUtils.equals(phoneCode, "11")) {
		// basePhonePolicy11 = new PaymethodAdjustPolicyInfo();
		// basePhonePolicy11.setPaymethodCode(arInfo[0]);
		// basePhonePolicy11.setAvailAmt(Double.parseDouble(arInfo[1]));
		// basePhonePolicy11.setAvailPer((int) (Double.parseDouble(arInfo[2])));
		//
		// } else if (StringUtils.equals(phoneCode, "12")) {
		// basePhonePolicy12 = new PaymethodAdjustPolicyInfo();
		// basePhonePolicy12.setPaymethodCode(arInfo[0]);
		// basePhonePolicy12.setAvailAmt(Double.parseDouble(arInfo[1]));
		// basePhonePolicy12.setAvailPer((int) (Double.parseDouble(arInfo[2])));
		// }
		// }
		// }
		//
		// if (bTelecomPaymethod) {
		// baseTargetPhonePolicy = basePhonePolicy11;
		// } else {
		// baseTargetPhonePolicy = basePhonePolicy12;
		// }
		//
		// }

		// 결제정책 & 기본정책 비교 처리
		PaymethodAdjustPolicyInfo resultPhonePolicy = null;

		if (targetPhonePolicy == null) { // 결제수단 정책에 없는 경우
			phonePaymethodInfo = phonePaymethodBaseInfo;

		} else { // 결제수단 정책에 있는 경우: 이용 가능한 금액/퍼센트 비교 처리

			// 휴대폰 기본 정책 추출

			PaymethodAdjustPolicyInfo baseTargetPhonePolicy = null;

			if (phonePaymethodBaseInfo != null) {

				int codePos = phonePaymethodBaseInfo.indexOf(baseTargetPhoneCode + ":");
				if (codePos >= 0) {
					int sepPos = phonePaymethodBaseInfo.indexOf(";", codePos);
					String baseInfo = null;
					if (sepPos >= 0) {
						baseInfo = phonePaymethodBaseInfo.substring(codePos, sepPos);
					} else {
						baseInfo = phonePaymethodBaseInfo.substring(codePos);
					}

					arInfo = baseInfo.split(":");

					baseTargetPhonePolicy = new PaymethodAdjustPolicyInfo();
					baseTargetPhonePolicy.setPaymethodCode(arInfo[0]);
					baseTargetPhonePolicy.setAvailAmt(Double.parseDouble(arInfo[1]));
					baseTargetPhonePolicy.setAvailPer((int) (Double.parseDouble(arInfo[2])));
				}
			}

			// 결제정책과 비교
			resultPhonePolicy = new PaymethodAdjustPolicyInfo();

			resultPhonePolicy.setPaymethodCode(targetPhonePolicy.getPaymethodCode());
			resultPhonePolicy
					.setAvailAmt(Math.min(baseTargetPhonePolicy.getAvailAmt(), targetPhonePolicy.getAvailAmt()));
			resultPhonePolicy
					.setAvailPer(Math.min(baseTargetPhonePolicy.getAvailPer(), targetPhonePolicy.getAvailPer()));

			phonePaymethodInfo = resultPhonePolicy.getPaymethodCode() + ":" + resultPhonePolicy.getAvailAmt() + ":"
					+ resultPhonePolicy.getAvailPer();

			if (bTelecomPaymethod) {
				phonePaymethodInfo = phonePaymethodInfo + ";12:0:0";
			} else {
				phonePaymethodInfo = "11:0:0;" + phonePaymethodInfo;
			}

		}

		// ---------------------------------------------------------------------------------------------------
		// TAKTODO:: String 제거 PaymethodAdjustPolicyInfo 사용?
		// 최종 결제 정책 결정

		StringBuffer sbPaymethodInfo = new StringBuffer(64);

		sbPaymethodInfo.append(phonePaymethodInfo).append(";"); // 휴대폰

		if (paymethodAdjustInfo.contains("13:0:0") == false) { // SAP 결제정책 : 신용카드 제한
			if (CollectionUtils.isNotEmpty(checkPaymentPolicyParam.getSapPolicyCdList())
					&& checkPaymentPolicyParam.getSapPolicyCdList().contains(PurchaseConstants.SAP_POLICY_LIMIT_CREDIT)) {
				sbPaymethodInfo.append("13:0:0;");
				int pos13 = paymethodAdjustInfo.startsWith("13:") ? 0 : (paymethodAdjustInfo.indexOf(";13:") + 1);
				if (pos13 >= 0) {
					String info13 = "";
					int endPos = paymethodAdjustInfo.indexOf(";", pos13);
					if (endPos >= 0) {
						info13 = paymethodAdjustInfo.substring(pos13, endPos);
					} else {
						info13 = paymethodAdjustInfo.substring(pos13);
					}
					paymethodAdjustInfo = paymethodAdjustInfo.replaceAll(info13, "");
				}
			}
		}

		sbPaymethodInfo.append(paymethodAdjustInfo.replaceAll("MAXAMT", String.valueOf(payAmt)));

		String paymethodInfo = sbPaymethodInfo.toString();

		// 상품 별 또는 모상품 별 지정한 것들은 정책 그대로. 기본 카테고리 정책이면 상품 별 예외 하드코딩 정책 적용
		if (StringUtils.equals(paymentPolicy.getPolicyId(), PurchaseConstants.POLICY_ID_PAYMETHOD_ADJUST)) {

			// 시리즈 패스: PayPin 허용
			if (StringUtils.equals(cmpxProdClsfCd, PurchaseConstants.FIXRATE_PROD_TYPE_VOD_SERIESPASS)) {
				paymethodInfo = paymethodInfo.replaceAll("14:0:0;", "").replaceAll(";14:0:0", "");
			}

			// 인앱 자동결제 상품의 경우(서버2서버 자동결제 포함), 휴대폰결제/신용카드만 노출되며 T멤버십은 호핀 앱에서만 노출한다.
			if (StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_IAP)) {

				if (checkPaymentPolicyParam.isAutoPrchs() || checkPaymentPolicyParam.isS2sAutoPrchs()) {
					StringBuffer sbPaymethodAdjInfo = new StringBuffer(128);
					int pos = paymethodInfo.indexOf("11:");
					if (pos >= 0) {
						sbPaymethodAdjInfo.append(paymethodInfo.substring(pos, paymethodInfo.indexOf(";", pos)))
								.append(";");
					}
					pos = paymethodInfo.indexOf("12:");
					if (pos >= 0) {
						sbPaymethodAdjInfo.append(paymethodInfo.substring(pos, paymethodInfo.indexOf(";", pos)))
								.append(";");
					}
					pos = paymethodInfo.indexOf("13:");
					if (pos >= 0) {
						sbPaymethodAdjInfo.append(paymethodInfo.substring(pos, paymethodInfo.indexOf(";", pos)))
								.append(";");
					}

					if (PurchaseConstants.HOPPIN_AID_LIST.contains(checkPaymentPolicyParam.getParentProdId())) {
						pos = paymethodInfo.indexOf("21:");
						if (pos >= 0) {
							sbPaymethodAdjInfo.append(paymethodInfo.substring(pos, paymethodInfo.indexOf(";", pos)))
									.append(";");
						}
					} else {
						sbPaymethodAdjInfo.append("21:0:0;");
					}

					sbPaymethodAdjInfo.append("14:0:0;20:0:0;22:0:0;23:0:0;24:0:0;25:0:0;26:0:0;27:0:0;30:0:0");

					paymethodInfo = sbPaymethodAdjInfo.toString();
				}

			}
		}

		return StringUtils.endsWith(paymethodInfo, ";") ? StringUtils.substring(paymethodInfo, 0,
				paymethodInfo.length() - 1) : paymethodInfo;
	}

	/**
	 * 
	 * <pre>
	 * 결제수단 재정의 (가능수단 정의 & 제한금액/할인율 정의) 정보 조회
	 * </pre>
	 * 
	 * @param tenantId
	 *            테넌트 ID
	 * @param tenantProdGrpCd
	 *            테넌트상품분류코드
	 * @param prodKindCd
	 *            상품종류코드
	 * @param prodId
	 *            상품별 정책 조회할 상품ID
	 * @param parentProdId
	 *            상품별 정책 조회할 모상품ID (인앱 경우 AID)
	 * 
	 * @return 결제수단 재정의 (가능수단 정의 & 제한금액/할인율 정의) 정보
	 */
	@Override
	public String getAvailablePaymethodAdjustInfo(String tenantId, String tenantProdGrpCd, String prodKindCd,
			String prodId, String parentProdId) {
		TenantSalePolicy policy = this.purchaseTenantPolicyService.searchPaymentPolicy(tenantId, tenantProdGrpCd,
				prodKindCd, prodId, parentProdId);

		if (policy != null) {
			return StringUtils.defaultString(policy.getApplyValue(), "");
		}

		return null;
	}

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
	 * @param iapProdCase
	 *            IAP 상품 유형
	 * 
	 * @param sktTestOrSkpCorp
	 *            시험폰 또는 SKP법인폰 여부
	 * 
	 * @return 결제수단 별 OCB 적립율
	 */
	@Override
	public String adjustOcbSaveInfo(String tenantId, String telecom, String tenantProdGrpCd, String iapProdCase,
			boolean sktTestOrSkpCorp) {

		// IAP자동결제상품, 쇼핑상품, VOD정액제 상품, 게임캐쉬 정액 상품 제외
		if ((StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_IAP) && StringUtils.equals(
				iapProdCase, "PB0006"))
				|| StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_SHOPPING)
				|| StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_DTL_MOVIE_FIXRATE)
				|| StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_DTL_TV_FIXRATE)
				|| StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_DTL_GAMECASH_FIXRATE)) {
			return "";

		} else {
			StringBuffer sbOcbAccum = new StringBuffer(64);

			if (StringUtils.equals(tenantId, PurchaseConstants.TENANT_ID_TSTORE)) {

				if (StringUtils.equals(telecom, PurchaseConstants.TELECOM_SKT)) {
					sbOcbAccum.append(sktTestOrSkpCorp ? "11:0.0;" : "11:0.1;"); // 시험폰, SKP법인폰 결제 제외
				} else {
					sbOcbAccum.append("12:0.1;"); // 다날
				}

				sbOcbAccum.append("13:0.1;14:0.1;25:0.1"); // 신용카드, PayPin, T store Cash

			} else if (StringUtils.equals(tenantId, PurchaseConstants.TENANT_ID_OLLEH)) {
				sbOcbAccum.append(sktTestOrSkpCorp ? "11:0.0;" : "11:0.1;"); // 시험폰, SKP법인폰 결제 제외
				sbOcbAccum.append("13:0.1"); // 신용카드

			} else if (StringUtils.equals(tenantId, PurchaseConstants.TENANT_ID_UPLUS)) {
				sbOcbAccum.append(sktTestOrSkpCorp ? "11:0.0" : "11:0.1"); // 시험폰, SKP법인폰 결제 제외
			}

			return sbOcbAccum.toString();
		}
	}
}
