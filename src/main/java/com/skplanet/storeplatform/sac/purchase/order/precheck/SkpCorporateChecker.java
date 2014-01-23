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

import com.skplanet.storeplatform.sac.purchase.order.dummy.service.DummyAdminServiceImpl;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseOrder;

/**
 * 
 * SKP 법인폰 체크
 * 
 * Updated on : 2014. 1. 3. Updated by : 이승택, nTels.
 */
public class SkpCorporateChecker implements PurchaseOrderChecker {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final DummyAdminServiceImpl dummyService = new DummyAdminServiceImpl();

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
		this.logger.debug("PRCHS,DUMMY,SKPCORP,START," + purchaseInfo);

		// SKP 법인폰 여부 조회 : 테넌트ID, MDN
		if (this.dummyService
				.isSkpCorporate(purchaseInfo.getTenantId(), purchaseInfo.getPurchaseMember().getDeviceId())) {
			purchaseInfo.getPolicyInfo().setbSkpCorp(true);
			return false;
		}

		this.logger.debug("PRCHS,DUMMY,SKPCORP,END," + purchaseInfo);
		return true;
	}
}
