/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.order.precheck;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;
import com.skplanet.storeplatform.sac.purchase.order.dummy.service.DummyMemberServiceImpl;
import com.skplanet.storeplatform.sac.purchase.order.dummy.vo.DummyMember;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseOrder;

/**
 * 
 * 회원 정보 체크
 * 
 * Updated on : 2014. 1. 3. Updated by : 이승택, nTels.
 */
public class MemberChecker implements PurchaseOrderChecker {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final DummyMemberServiceImpl dummyService = new DummyMemberServiceImpl();

	/**
	 * <pre>
	 * 체크 대상 여부 확인.
	 * </pre>
	 * 
	 * @param purchaseInfo
	 *            구매요청 정보
	 * @return 체크대상여부: true-체크대상, false-체크대상 아님
	 */
	@Override
	public boolean isTarget(PurchaseOrder purchaseInfo) {
		return true;
	}

	/**
	 * <pre>
	 * 구매 전처리 체크 및 필요한 정보 세팅.
	 * </pre>
	 * 
	 * @param purchaseInfo
	 *            구매요청 정보
	 * @return 체크진행 여부: true-체크진행 계속, false-체크진행 중지
	 */
	@Override
	public boolean checkAndSetInfo(PurchaseOrder purchaseInfo) {
		this.logger.debug("PRCHS,DUMMY,MEMBER,START," + purchaseInfo);

		// 회원 정보 조회 : 테넌트ID, 시스템ID, 내부회원NO, 디바이스ID
		DummyMember member = this.dummyService.getUserInfo(purchaseInfo.getTenantId(), purchaseInfo.getSystemId(),
				purchaseInfo.getUserKey(), purchaseInfo.getDeviceKey());

		this.logger.debug("DUMMY,MEMBER," + member);
		purchaseInfo.setPurchaseMember(member);

		// 선물 경우 - 수신자 정보 조회
		if (PurchaseConstants.PRCHS_CASE_GIFT_CD.equals(purchaseInfo.getPrchsCaseCd())) {
			member = this.dummyService.getUserInfo(purchaseInfo.getRecvTenantId(), purchaseInfo.getSystemId(),
					purchaseInfo.getRecvUserKey(), purchaseInfo.getRecvDeviceKey());

			this.logger.debug("DUMMY,MEMBER,RECV," + member);
			purchaseInfo.setRecvMember(member);
		}

		this.logger.debug("PRCHS,DUMMY,MEMBER,END," + purchaseInfo);
		return true;
	}
}
