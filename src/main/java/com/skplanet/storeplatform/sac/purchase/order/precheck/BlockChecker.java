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

import com.skplanet.storeplatform.sac.purchase.order.dummy.service.DummyMemberServiceImpl;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseOrder;

/**
 * 
 * 구매 차단 체크
 * 
 * Updated on : 2014. 1. 3. Updated by : 이승택, nTels.
 */
public class BlockChecker implements PurchaseOrderChecker {
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
		// 유료결제
		return (purchaseInfo.getRealTotAmt() > 0);
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
		this.logger.debug("PRCHS,DUMMY,BLOCK,START," + purchaseInfo);

		// 구매 차단 여부 조회 : 테넌트ID, 내부회원NO, 디바이스ID
		if (this.dummyService.isBlock(purchaseInfo.getTenantId(), purchaseInfo.getUserKey(),
				purchaseInfo.getDeviceKey())) {

			purchaseInfo.getPolicyInfo().setbBlock(true);
			return false;
		}

		this.logger.debug("PRCHS,DUMMY,BLOCK,END," + purchaseInfo);

		return true;
	}
}
