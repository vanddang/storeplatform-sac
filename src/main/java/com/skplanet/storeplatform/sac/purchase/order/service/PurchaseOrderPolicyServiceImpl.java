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

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.sac.purchase.order.precheck.CheckerManager;
import com.skplanet.storeplatform.sac.purchase.order.precheck.PurchaseOrderChecker;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseOrder;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseOrderResult;

/**
 * 
 * 구매 제한정책 체크 서비스 구현체
 * 
 * Updated on : 2014. 1. 22. Updated by : 이승택, nTels.
 */
@Service
@Transactional
public class PurchaseOrderPolicyServiceImpl implements PurchaseOrderPolicyService {

	private final CheckerManager checkerManager = new CheckerManager();

	/**
	 * 
	 * <pre>
	 * 제한정책 체크.
	 * </pre>
	 * 
	 * @param purchaseOrderInfo
	 *            구매주문 정보
	 * @return 제한정책 체크 결과 정보: null-정상, not null-체크 오류 결과 정보
	 */
	@Override
	public PurchaseOrderResult checkPolicy(PurchaseOrder purchaseOrderInfo) {
		List<PurchaseOrderChecker> checkerList = this.checkerManager.getCheckerList(null);

		for (PurchaseOrderChecker checker : checkerList) {
			if (checker.isTarget(purchaseOrderInfo) == false) {
				continue;
			}

			if (checker.checkAndSetInfo(purchaseOrderInfo) == false) {
				break;
			}
		}

		return null;
	}
}
