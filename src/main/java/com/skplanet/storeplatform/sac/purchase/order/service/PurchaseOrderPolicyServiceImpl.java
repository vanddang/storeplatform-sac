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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.sac.purchase.order.dummy.service.DummyAdminServiceImpl;
import com.skplanet.storeplatform.sac.purchase.order.dummy.service.DummyMemberServiceImpl;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseOrder;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseOrderPolicy;

/**
 * 
 * 구매 제한정책 체크 서비스 구현체
 * 
 * Updated on : 2014. 1. 22. Updated by : 이승택, nTels.
 */
@Service
public class PurchaseOrderPolicyServiceImpl implements PurchaseOrderPolicyService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final PurchaseOrderPolicyManager policyManager = new PurchaseOrderPolicyManager();

	private final DummyMemberServiceImpl dummyMemberService = new DummyMemberServiceImpl();
	private final DummyAdminServiceImpl dummyAdminService = new DummyAdminServiceImpl();

	/**
	 * 
	 * <pre>
	 * (임시적) 제한정책 체크.
	 * </pre>
	 * 
	 * @param purchaseOrderInfo
	 *            구매주문 정보
	 */
	@Override
	public void checkPolicy(PurchaseOrder purchaseOrderInfo) {
		// List<PurchaseOrderChecker> checkerList = this.checkerManager.getCheckerList(null);
		//
		// for (PurchaseOrderChecker checker : checkerList) {
		// if (checker.isTarget(purchaseOrderInfo) == false) {
		// continue;
		// }
		//
		// if (checker.checkAndSetInfo(purchaseOrderInfo) == false) {
		// break;
		// }
		// }
		// TAKTODO::
		// - 제한정책 Rule 정의 필요 : 임시적으로 단순 ID값 비교에 따른 임시 처리
		// - 패턴에 의한 처리 프로세스로 변경 필요
		// - 상품 복수개 구매 시 처리

		boolean bCharge = purchaseOrderInfo.getRealTotAmt() > 0;
		boolean bSktTelecom = true;
		List<PurchaseOrderPolicy> policyList = this.policyManager.getPolicyList(bCharge, bSktTelecom);

		// String tenantProdGrpCd = null;
		for (PurchaseOrderPolicy policy : policyList) {
			switch (policy.getId()) {
			case PurchaseOrderPolicyManager.POLICY_ID_BLOCK: // 결제제한
				this.todo01(policy, purchaseOrderInfo);
				break;
			case PurchaseOrderPolicyManager.POLICY_ID_TESTMDN: // Test MDN
				this.todo02(policy, purchaseOrderInfo);
				break;
			case PurchaseOrderPolicyManager.POLICY_ID_SKTTESTMDN: // SKT 시험폰
				this.todo03(policy, purchaseOrderInfo);
				break;
			case PurchaseOrderPolicyManager.POLICY_ID_SKTCORPMDN: // SKT 법인폰
				this.todo04(policy, purchaseOrderInfo);
				break;
			case PurchaseOrderPolicyManager.POLICY_ID_SKPCORPMDN: // SKP 법인폰
				this.todo05(policy, purchaseOrderInfo);
				break;
			case PurchaseOrderPolicyManager.POLICY_ID_SKT_LIMIT: // SKT 후불 결제 한도
				this.todo06(policy, purchaseOrderInfo);
				break;
			case PurchaseOrderPolicyManager.POLICY_ID_SKT_SHOPPINGLIMIT: // 쇼핑상품 SKT 후불 결제 한도
				this.todo07(policy, purchaseOrderInfo);
				break;
			case PurchaseOrderPolicyManager.POLICY_ID_SKT_SENDLIMIT:
				this.todo08(policy, purchaseOrderInfo);
				break;
			case PurchaseOrderPolicyManager.POLICY_ID_SKT_RECVLIMIT:
				this.todo09(policy, purchaseOrderInfo);
				break;
			}
		}
	}

	/**
	 * <pre>
	 * 결제 차단 여부 체크.
	 * </pre>
	 * 
	 * @param policy
	 *            제한정책 정보
	 * @param purchaseInfo
	 *            구매요청 정보
	 * @return 체크진행 여부: true-체크진행 계속, false-체크진행 중지
	 */
	private boolean todo01(PurchaseOrderPolicy policy, PurchaseOrder purchaseInfo) {
		this.logger.debug("PRCHS,POLICY,DUMMY,BLOCK,START," + purchaseInfo);

		// 구매 차단 여부 조회 : 테넌트ID, 내부회원NO, 디바이스ID
		if (this.dummyMemberService.isBlock(purchaseInfo.getTenantId(), purchaseInfo.getUserKey(),
				purchaseInfo.getDeviceKey())) {

			purchaseInfo.getPolicyInfo().setbBlock(true);
			return false;
		}

		this.logger.debug("PRCHS,POLICY,DUMMY,BLOCK,END");

		return true;
	}

	/**
	 * 
	 * <pre>
	 * Test MDN 여부 체크.
	 * </pre>
	 * 
	 * @param policy
	 *            제한정책 정보
	 * @param purchaseInfo
	 *            구매요청 정보
	 * @return 체크진행 여부: true-체크진행 계속, false-체크진행 중지
	 */
	private boolean todo02(PurchaseOrderPolicy policy, PurchaseOrder purchaseInfo) {
		this.logger.debug("PRCHS,POLICY,DUMMY,TESTMDN,START," + purchaseInfo);

		// 테스트폰 여부 조회 : 테넌트ID, MDN
		if (this.dummyAdminService
				.isTestMdn(purchaseInfo.getTenantId(), purchaseInfo.getPurchaseMember().getDeviceId())) {

			purchaseInfo.getPolicyInfo().setbTestMdn(true);
			return false;
		}

		this.logger.debug("PRCHS,POLICY,DUMMY,TESTMDN,END");
		return true;
	}

	/**
	 * 
	 * <pre>
	 * SKT시험폰 여부 체크.
	 * </pre>
	 * 
	 * @param policy
	 *            제한정책 정보
	 * @param purchaseInfo
	 *            구매요청 정보
	 * @return 체크진행 여부: true-체크진행 계속, false-체크진행 중지
	 */
	private boolean todo03(PurchaseOrderPolicy policy, PurchaseOrder purchaseInfo) {
		this.logger.debug("PRCHS,POLICY,DUMMY,SKTTEST,START," + purchaseInfo);

		// SKT시험폰 여부 조회 : 테넌트ID, MDN
		if (this.dummyAdminService
				.isSktTest(purchaseInfo.getTenantId(), purchaseInfo.getPurchaseMember().getDeviceId())) {
			purchaseInfo.getPolicyInfo().setbSktTest(true);
			return false;
		}

		this.logger.debug("PRCHS,POLICY,DUMMY,SKTTEST,END");
		return true;
	}

	/**
	 * 
	 * <pre>
	 * SKT 법인폰 여부 체크.
	 * </pre>
	 * 
	 * @param policy
	 *            제한정책 정보
	 * @param purchaseInfo
	 *            구매요청 정보
	 * @return 체크진행 여부: true-체크진행 계속, false-체크진행 중지
	 */
	private boolean todo04(PurchaseOrderPolicy policy, PurchaseOrder purchaseInfo) {
		this.logger.debug("PRCHS,POLICY,DUMMY,SKTCORP,START," + purchaseInfo);

		// SKT 법인폰 여부 조회 : 테넌트ID, MDN
		if (this.dummyAdminService.isSktCorporate(purchaseInfo.getTenantId(), purchaseInfo.getPurchaseMember()
				.getDeviceId())) {
			purchaseInfo.getPolicyInfo().setbSktCorp(true);
			return false;
		}

		this.logger.debug("PRCHS,POLICY,DUMMY,SKTCORP,END");
		return true;
	}

	/**
	 * 
	 * <pre>
	 * SKP 법인폰 여부 체크.
	 * </pre>
	 * 
	 * @param policy
	 *            제한정책 정보
	 * @param purchaseInfo
	 *            구매요청 정보
	 * @return 체크진행 여부: true-체크진행 계속, false-체크진행 중지
	 */
	private boolean todo05(PurchaseOrderPolicy policy, PurchaseOrder purchaseInfo) {
		this.logger.debug("PRCHS,POLICY,DUMMY,SKPCORP,START," + purchaseInfo);

		// SKP 법인폰 여부 조회 : 테넌트ID, MDN
		if (this.dummyAdminService.isSkpCorporate(purchaseInfo.getTenantId(), purchaseInfo.getPurchaseMember()
				.getDeviceId())) {
			purchaseInfo.getPolicyInfo().setbSkpCorp(true);
			return false;
		}

		this.logger.debug("PRCHS,POLICY,DUMMY,SKPCORP,END");
		return true;
	}

	/**
	 * 
	 * <pre>
	 * SKT 후불 결제 한도 체크.
	 * </pre>
	 * 
	 * @param policy
	 *            제한정책 정보
	 * @param purchaseInfo
	 *            구매요청 정보
	 * @return 체크진행 여부: true-체크진행 계속, false-체크진행 중지
	 */
	private boolean todo06(PurchaseOrderPolicy policy, PurchaseOrder purchaseInfo) {
		this.logger.debug("PRCHS,POLICY,DUMMY,SKTLIMIT,START," + purchaseInfo);

		this.dummyAdminService.getSktLimit();

		this.logger.debug("PRCHS,POLICY,DUMMY,SKTLIMIT,END");
		return true;
	}

	/**
	 * 
	 * <pre>
	 * 쇼핑상품 SKT 후불 결제 한도 체크.
	 * </pre>
	 * 
	 * @param policy
	 *            제한정책 정보
	 * @param purchaseInfo
	 *            구매요청 정보
	 * @return 체크진행 여부: true-체크진행 계속, false-체크진행 중지
	 */
	private boolean todo07(PurchaseOrderPolicy policy, PurchaseOrder purchaseInfo) {
		this.logger.debug("PRCHS,POLICY,DUMMY,SHOPLIMIT,START," + purchaseInfo);

		this.dummyAdminService.getShoppingLimit();

		this.logger.debug("PRCHS,POLICY,DUMMY,SHOPLIMIT,END");
		return true;
	}

	/**
	 * 
	 * <pre>
	 * 선물 발신 SKT 후불 결제 한도 체크.
	 * </pre>
	 * 
	 * @param policy
	 *            제한정책 정보
	 * @param purchaseInfo
	 *            구매요청 정보
	 * @return 체크진행 여부: true-체크진행 계속, false-체크진행 중지
	 */
	private boolean todo08(PurchaseOrderPolicy policy, PurchaseOrder purchaseInfo) {
		this.logger.debug("PRCHS,POLICY,DUMMY,SENDLIMIT,START," + purchaseInfo);

		this.dummyAdminService.getGiftSendLimit();

		this.logger.debug("PRCHS,POLICY,DUMMY,SENDLIMIT,END");
		return true;
	}

	/**
	 * 
	 * <pre>
	 * 선물 수신 한도 체크.
	 * </pre>
	 * 
	 * @param policy
	 *            제한정책 정보
	 * @param purchaseInfo
	 *            구매요청 정보
	 * @return 체크진행 여부: true-체크진행 계속, false-체크진행 중지
	 */
	private boolean todo09(PurchaseOrderPolicy policy, PurchaseOrder purchaseInfo) {
		this.logger.debug("PRCHS,POLICY,DUMMY,RECVLIMIT,START," + purchaseInfo);

		this.dummyAdminService.getGiftRecvLimit();

		this.logger.debug("PRCHS,POLICY,DUMMY,RECVLIMIT,END");
		return true;
	}

}
