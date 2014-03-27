/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.product.count.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.skplanet.storeplatform.purchase.client.product.count.vo.GetPrchsProdCntScReq;
import com.skplanet.storeplatform.purchase.client.product.count.vo.GetPrchsProdCntScRes;
import com.skplanet.storeplatform.purchase.client.product.count.vo.UpdatePrchsProdCntProcStatusScReq;
import com.skplanet.storeplatform.purchase.client.product.count.vo.UpdatePrchsProdCntProcStatusScRes;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;
import com.skplanet.storeplatform.sac.purchase.product.count.repository.PurchaseProductCountRepository;
import com.skplanet.storeplatform.sac.purchase.product.count.vo.PurchaseProductCountSacParam;
import com.skplanet.storeplatform.sac.purchase.product.count.vo.PurchaseProductCountSacResult;

/**
 * Class 설명
 * 
 * Updated on : 2014. 3. 27. Updated by : nTels_cswoo81, nTels.
 */
public class PurchaseProductCountServiceImpl implements PurchaseProductCountService {

	@Autowired
	private PurchaseProductCountRepository purchaseProductCountRepository;

	@Override
	public PurchaseProductCountSacResult executePurchaseProductCount(
			PurchaseProductCountSacParam purchaseProductCountSacParam) {

		PurchaseProductCountSacResult purchaseProductCountSacResult = new PurchaseProductCountSacResult();

		/** 호출 시 처리 할 구매 상품 건수 처리 상태 'R'로 변경. */
		UpdatePrchsProdCntProcStatusScReq updatePrchsProdCntProcStatusScReq = new UpdatePrchsProdCntProcStatusScReq();
		updatePrchsProdCntProcStatusScReq.setGuid(purchaseProductCountSacParam.getGuid());
		updatePrchsProdCntProcStatusScReq.setCurrProcStatus(PurchaseConstants.PURCHASE_PRODUCT_COUNT_PROC_STATUS_NO);
		updatePrchsProdCntProcStatusScReq
				.setNewProcStatus(PurchaseConstants.PURCHASE_PRODUCT_COUNT_PROC_STATUS_RESERVE);
		UpdatePrchsProdCntProcStatusScRes updatePrchsProdCntProcStatusScRes = this.purchaseProductCountRepository
				.updatePrchsProdCntProcStatus(updatePrchsProdCntProcStatusScReq);
		// R로 변경한 건수 결과 totCnt에 셋팅.
		purchaseProductCountSacResult.setTotCnt(updatePrchsProdCntProcStatusScRes.getResultCnt());

		while (true) {

			/** 처리 할 구매 상품 건수 guid가 일치하면서 상태가 'R'인 놈 처리할 건수만큼 조회. */
			GetPrchsProdCntScReq getPrchsProdCntScReq = new GetPrchsProdCntScReq();
			getPrchsProdCntScReq.setGuid(purchaseProductCountSacParam.getGuid());
			getPrchsProdCntScReq.setProcStatus(PurchaseConstants.PURCHASE_PRODUCT_COUNT_PROC_STATUS_RESERVE);
			getPrchsProdCntScReq.setPurCnt(purchaseProductCountSacParam.getPerCount());

			GetPrchsProdCntScRes getPrchsProdCntScRes = this.purchaseProductCountRepository
					.getPrchsProdCnt(getPrchsProdCntScReq);
			if (getPrchsProdCntScRes.getPrchsProdCntList() == null
					|| getPrchsProdCntScRes.getPrchsProdCntList().size() < 1) {
				break;
			}

			/** 전시에 상품 건수 업데이트 요청. */

			/** 전시 처리 후 구매 상품 건수 처리 상태 'S'로 변경. */

		}

		return purchaseProductCountSacResult;

	}

}
