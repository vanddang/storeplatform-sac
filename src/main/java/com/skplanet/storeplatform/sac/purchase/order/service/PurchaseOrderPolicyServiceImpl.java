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

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	private PurchaseTenantPolicyService policyService;

	@Autowired
	private PurchaseUapsRespository uapsRespository;

	/**
	 * 
	 * <pre>
	 * 테넌트 정책 체크.
	 * </pre>
	 * 
	 * @param purchaseOrderInfo
	 *            구매주문 정보
	 */
	@Override
	public void checkTenantPolicy(PurchaseOrderInfo purchaseOrderInfo) {
		List<PurchaseTenantPolicy> policyList = this.policyService.searchPurchaseTenantPolicyList(
				purchaseOrderInfo.getTenantId(), purchaseOrderInfo.getTenantProdGrpCd());

		// TAKTODO:: 항목이 많지 않으니 문자열 비교? 아니면 정수상수 선언 후 switch?
		for (PurchaseTenantPolicy policy : policyList) {
			if (StringUtils.equals(policy.getProcPatternCd(), PurchaseConstants.POLICY_PATTERN_STORE_TEST_DEVICE)) {
				this.checkStoreTestMdn(purchaseOrderInfo, policy); // CM011605: 비과금 결제 허용 (Test MDN)

			} else if (StringUtils.equals(policy.getProcPatternCd(), PurchaseConstants.POLICY_PATTERN_MEMBER_BLOCK_CD)) {
				this.checkBlock(purchaseOrderInfo, policy); // CM011611: 회원Part 구매 차단 정책코드

			} else if (StringUtils.equals(policy.getProcPatternCd(),
					PurchaseConstants.POLICY_PATTERN_DEVICE_BASED_PRCHSHST)) {
				this.checkDeviceBasePurchaseHistory(purchaseOrderInfo, policy); // CM011606: Device 기반 구매내역 관리

				// ///////////// TAKTODO:: 이하는 현재 위치에서 미사용, 일단 같이 작성.
			} else if (StringUtils.equals(policy.getProcPatternCd(), PurchaseConstants.POLICY_PATTERN_SKT_PRCHS_LIMIT)) {
				this.checkSktLimit(purchaseOrderInfo, policy); // CM011601: SKT후불 결제 한도제한

			} else if (StringUtils.equals(policy.getProcPatternCd(), PurchaseConstants.POLICY_PATTERN_SKT_RECV_LIMIT)) {
				this.checkSktRecvLimit(purchaseOrderInfo, policy); // CM011602: SKT후불 선물수신 한도제한

			} else if (StringUtils.equals(policy.getProcPatternCd(), PurchaseConstants.POLICY_PATTERN_CORP_DEVICE)) {
				this.checkCorporation(purchaseOrderInfo, policy); // CM011603: 법인 명의 제한 (법인폰)

			} else if (StringUtils.equals(policy.getProcPatternCd(), PurchaseConstants.POLICY_PATTERN_SKT_TEST_DEVICE)) {
				this.checkSktTestMdn(purchaseOrderInfo, policy); // CM011604: SKT 시험폰 결제 허용
			}
		}

	}

	/*
	 * 
	 * <pre> 테넌트 정책 처리패턴 CM011601: SKT후불 구매결제 한도제한. </pre>
	 * 
	 * @param purchaseOrderInfo 구매진행 정보
	 * 
	 * @param policy 테넌트 정책 정보
	 */
	private void checkSktLimit(PurchaseOrderInfo purchaseOrderInfo, PurchaseTenantPolicy policy) {
		this.logger.debug("PRCHS,ORDER,SAC,POLICY,START,{}", policy.getPolicyId());

		double checkVal = 0.0;

		SearchSktPaymentScReq sciReq = new SearchSktPaymentScReq();
		SearchSktPaymentScRes sciRes = null;

		sciReq.setTenantId(purchaseOrderInfo.getTenantId());
		sciReq.setUserKey(purchaseOrderInfo.getUserKey());
		sciReq.setDeviceKey(purchaseOrderInfo.getDeviceKey());
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
					return;
				}
			} else if (checkVal < Double.parseDouble(policy.getCondClsfValue())) {
				return;
			}
		}

		// (정책 체크 값) 정책 요소기준 조회
		sciRes = this.purchaseOrderSearchSCI.searchSktAmountDetail(sciReq);
		checkVal = (Double) sciRes.getVal();

		// TAKTODO:: 제한걸림
		if ((Double.parseDouble(policy.getApplyValue()) - checkVal) < purchaseOrderInfo.getRealTotAmt()) {
			this.logger.debug("PRCHS,ORDER,SAC,POLICY,APPLY_LIMIT,{}({}/{})", policy.getPolicyId(),
					purchaseOrderInfo.getRealTotAmt(), (Double.parseDouble(policy.getApplyValue()) - checkVal));
			;
		}

	}

	/*
	 * 
	 * <pre> 테넌트 정책 처리패턴 CM011602: SKT후불 선물수신 한도제한. </pre>
	 * 
	 * @param purchaseOrderInfo 구매진행 정보
	 * 
	 * @param policy 테넌트 정책 정보
	 */
	private void checkSktRecvLimit(PurchaseOrderInfo purchaseOrderInfo, PurchaseTenantPolicy policy) {
		this.logger.debug("PRCHS,ORDER,SAC,POLICY,START,{}", policy.getPolicyId());

		// 선물 여부 체크
		if (StringUtils.equals(purchaseOrderInfo.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD) == false) {
			return;
		}

		double checkVal = 0.0;

		SearchSktPaymentScReq sciReq = new SearchSktPaymentScReq();
		SearchSktPaymentScRes sciRes = null;

		sciReq.setTenantId(purchaseOrderInfo.getRecvTenantId()); // 수신자 기준 조회
		sciReq.setUserKey(purchaseOrderInfo.getRecvUserKey()); // 수신자 기준 조회
		sciReq.setDeviceKey(purchaseOrderInfo.getRecvDeviceKey()); // 수신자 기준 조회
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

		// TAKTODO:: 제한걸림
		if ((Double.parseDouble(policy.getApplyValue()) - checkVal) < purchaseOrderInfo.getRealTotAmt()) {
			this.logger.debug("PRCHS,ORDER,SAC,POLICY,APPLY_LIMIT,{}({}/{})", policy.getPolicyId(),
					purchaseOrderInfo.getRealTotAmt(), (Double.parseDouble(policy.getApplyValue()) - checkVal));
			;
		}
	}

	/*
	 * 
	 * <pre> 테넌트 정책 처리패턴 CM011603: 법인명의 제한. </pre>
	 * 
	 * @param purchaseOrderInfo 구매진행 정보
	 * 
	 * @param policy 테넌트 정책 정보
	 */
	private void checkCorporation(PurchaseOrderInfo purchaseOrderInfo, PurchaseTenantPolicy policy) {
		this.logger.debug("PRCHS,ORDER,SAC,POLICY,START,{}", policy.getPolicyId());

		boolean bCorp = false;

		try {
			bCorp = this.uapsRespository.searchUapsAuthorizeInfoByMdn(policy.getApplyValue(), purchaseOrderInfo
					.getPurchaseMember().getDeviceId());
		} catch (StorePlatformException e) {
			// 2014.02.12. 기준 : EC UAPS 에서 비정상 예외 시 9999 리턴, 그 외에는 조회결과 없음(9997) 또는 명의상태에 따른 코드.
			if (StringUtils.equals(e.getErrorInfo().getCode(), "EC_UAPS_9999")) {
				throw new StorePlatformException("SAC_PUR_0001", e);
			}
		}

		// 법인폰 구매제한 처리
		if (bCorp) {
			this.logger
					.debug("PRCHS,ORDER,SAC,POLICY,APPLY_LIMIT,{},{})", policy.getPolicyId(), policy.getApplyValue());
			throw new StorePlatformException("SAC_PUR_0001", "법인폰으로 구매할 수 없는 상품입니다.");
		}

	}

	/*
	 * 
	 * <pre> 테넌트 정책 처리패턴 CM011604: SKT 시험폰 결제 허용. </pre>
	 * 
	 * @param purchaseOrderInfo 구매진행 정보
	 * 
	 * @param policy 테넌트 정책 정보
	 */
	private void checkSktTestMdn(PurchaseOrderInfo purchaseOrderInfo, PurchaseTenantPolicy policy) {
		this.logger.debug("PRCHS,ORDER,SAC,POLICY,START,{}", policy.getPolicyId());

		// TAKTODO:: Store에 등록된 시험폰 WhiteList 조회
		;

		// 등록된 시험폰일 경우, 실제 SKT 시험폰 여부 확인
		if (true) {

			String svcTp = null;

			try {
				svcTp = this.uapsRespository.searchUapsMappingInfoByMdn(purchaseOrderInfo.getPurchaseMember()
						.getDeviceId());
			} catch (StorePlatformException e) {
				// 2014.02.12. 기준 : EC UAPS 에서 비정상 예외 시 9999 리턴, 그 외에는 조회결과 없음(9997) 또는 명의상태에 따른 코드.
				if (StringUtils.equals(e.getErrorInfo().getCode(), "EC_UAPS_9999")) {
					throw new StorePlatformException("SAC_PUR_0001", e);
				}
			}

			// 시험폰
			if (StringUtils.equals(svcTp, "12")) {
				this.logger.debug("PRCHS,ORDER,SAC,POLICY,APPLY_LIMIT,{})", policy.getPolicyId());

				// TAKTODO:: Setting
			}
		}
	}

	/*
	 * 
	 * <pre> 테넌트 정책 처리패턴 CM011605: 비과금 결제 허용(Test MDN). </pre>
	 * 
	 * @param purchaseOrderInfo 구매진행 정보
	 * 
	 * @param policy 테넌트 정책 정보
	 */
	private void checkStoreTestMdn(PurchaseOrderInfo purchaseOrderInfo, PurchaseTenantPolicy policy) {
		this.logger.debug("PRCHS,ORDER,SAC,POLICY,START,{}", policy.getPolicyId());

		// TAKTODO:: 회원의 정책 코드 비교

	}

	/*
	 * 
	 * <pre> 테넌트 정책 처리패턴 CM011606: Device기반 구매내역 관리처리. </pre>
	 * 
	 * @param purchaseOrderInfo 구매진행 정보
	 * 
	 * @param policy 테넌트 정책 정보
	 */
	private void checkDeviceBasePurchaseHistory(PurchaseOrderInfo purchaseOrderInfo, PurchaseTenantPolicy policy) {
		this.logger.debug("PRCHS,ORDER,SAC,POLICY,START,{}", policy.getPolicyId());

	}

	/*
	 * 
	 * <pre> 테넌트 정책 처리패턴 CM011611: 회원Part 구매차단 코드. </pre>
	 * 
	 * @param purchaseOrderInfo 구매진행 정보
	 * 
	 * @param policy 테넌트 정책 정보
	 */
	private void checkBlock(PurchaseOrderInfo purchaseOrderInfo, PurchaseTenantPolicy policy) {
		this.logger.debug("PRCHS,ORDER,SAC,POLICY,START,{}", policy.getPolicyId());

		// TAKTODO:: 회원의 정책 코드 비교

	}

}
