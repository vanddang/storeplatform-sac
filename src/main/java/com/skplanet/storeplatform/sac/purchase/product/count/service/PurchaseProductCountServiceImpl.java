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

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.purchase.client.common.vo.PrchsProdCnt;
import com.skplanet.storeplatform.purchase.client.product.count.vo.GetPrchsProdCntScReq;
import com.skplanet.storeplatform.purchase.client.product.count.vo.GetPrchsProdCntScRes;
import com.skplanet.storeplatform.purchase.client.product.count.vo.UpdatePrchsProdCntProcStatusScReq;
import com.skplanet.storeplatform.purchase.client.product.count.vo.UpdatePrchsProdCntProcStatusScRes;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.UpdatePurchaseCountSacReq;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;
import com.skplanet.storeplatform.sac.purchase.product.count.repository.PurchaseProductCountRepository;
import com.skplanet.storeplatform.sac.purchase.product.count.vo.PurchaseProductCountSacParam;
import com.skplanet.storeplatform.sac.purchase.product.count.vo.PurchaseProductCountSacResult;

/**
 * Class 설명
 * 
 * Updated on : 2014. 3. 27. Updated by : nTels_cswoo81, nTels.
 */
@Service
public class PurchaseProductCountServiceImpl implements PurchaseProductCountService {

	@Autowired
	private PurchaseProductCountRepository purchaseProductCountRepository;

	@Override
	public PurchaseProductCountSacResult executePurchaseProductCount(
			PurchaseProductCountSacParam purchaseProductCountSacParam) {

		// 총 처리 결과 값 저장 VO.
		PurchaseProductCountSacResult purchaseProductCountSacResult = new PurchaseProductCountSacResult();
		// PrchsProdCntProcStatus 상태 변경 요청값 셋팅.
		UpdatePrchsProdCntProcStatusScReq updatePrchsProdCntProcStatusScReq = new UpdatePrchsProdCntProcStatusScReq();
		updatePrchsProdCntProcStatusScReq.setGuid(purchaseProductCountSacParam.getGuid());
		// PrchsProdCntProcStatus 상태 변경 응답값.
		UpdatePrchsProdCntProcStatusScRes updatePrchsProdCntProcStatusScRes = null;

		int totCnt = 0;
		int successCnt = 0;
		int failCnt = 0;

		/** 호출 시 처리 할 구매 상품 건수 처리 상태 'R'로 변경. */
		updatePrchsProdCntProcStatusScReq.setCurrProcStatus(PurchaseConstants.PURCHASE_PRODUCT_COUNT_PROC_STATUS_NO);
		updatePrchsProdCntProcStatusScReq
				.setNewProcStatus(PurchaseConstants.PURCHASE_PRODUCT_COUNT_PROC_STATUS_RESERVE);
		updatePrchsProdCntProcStatusScRes = this.purchaseProductCountRepository
				.updatePrchsProdCntProcStatus(updatePrchsProdCntProcStatusScReq);
		// R로 변경한 건수 결과 totCnt에 셋팅.
		totCnt = updatePrchsProdCntProcStatusScRes.getResultCnt();

		while (true) {

			/** 처리 할 구매 상품 건수 guid가 일치하면서 상태가 'R'인 놈 처리할 건수만큼 조회 후 처리 상태 UPDATE로 변경. */
			GetPrchsProdCntScReq getPrchsProdCntScReq = new GetPrchsProdCntScReq();
			getPrchsProdCntScReq.setGuid(purchaseProductCountSacParam.getGuid());
			getPrchsProdCntScReq.setCurrProcStatus(PurchaseConstants.PURCHASE_PRODUCT_COUNT_PROC_STATUS_RESERVE);
			getPrchsProdCntScReq.setNewProcStatus(PurchaseConstants.PURCHASE_PRODUCT_COUNT_PROC_STATUS_UPDATE);
			getPrchsProdCntScReq.setPurCnt(purchaseProductCountSacParam.getPerCount());

			GetPrchsProdCntScRes getPrchsProdCntScRes = this.purchaseProductCountRepository
					.getPrchsProdCnt(getPrchsProdCntScReq);
			if (getPrchsProdCntScRes.getPrchsProdCntList() == null
					|| getPrchsProdCntScRes.getPrchsProdCntList().size() < 1) {
				break;
			}

			/** 전시에 상품 건수 업데이트 요청. */
			boolean apiRtn = false;
			List<UpdatePurchaseCountSacReq> reqList = new ArrayList<UpdatePurchaseCountSacReq>();
			for (PrchsProdCnt prchsProdCnt : getPrchsProdCntScRes.getPrchsProdCntList()) {
				UpdatePurchaseCountSacReq updatePurchaseCountSacReq = new UpdatePurchaseCountSacReq();
				updatePurchaseCountSacReq.setTenantId(prchsProdCnt.getTenantId());
				updatePurchaseCountSacReq.setProductId(prchsProdCnt.getProdId());
				if (StringUtils.equals(PurchaseConstants.PRCHS_STATUS_COMPT, prchsProdCnt.getStatusCd())) {
					updatePurchaseCountSacReq.setPurchaseCount(prchsProdCnt.getProdQty());
				} else {
					updatePurchaseCountSacReq.setPurchaseCount(-prchsProdCnt.getProdQty());
				}
				updatePurchaseCountSacReq.setSpcYn(prchsProdCnt.getSprcProdYn());
				updatePurchaseCountSacReq.setPurchaseDate(prchsProdCnt.getPrchsDt());

				reqList.add(updatePurchaseCountSacReq);
			}

			try {
				this.purchaseProductCountRepository.updatePurchaseCount(reqList);
				apiRtn = true;
			} catch (Exception e) {
				apiRtn = false;
			}

			/** 전시 처리 후 구매 상품 건수 처리 상태 'S'로 변경. */
			updatePrchsProdCntProcStatusScReq
					.setCurrProcStatus(PurchaseConstants.PURCHASE_PRODUCT_COUNT_PROC_STATUS_UPDATE);
			if (apiRtn) {
				updatePrchsProdCntProcStatusScReq
						.setNewProcStatus(PurchaseConstants.PURCHASE_PRODUCT_COUNT_PROC_STATUS_SUCCESS);
			} else {
				updatePrchsProdCntProcStatusScReq
						.setNewProcStatus(PurchaseConstants.PURCHASE_PRODUCT_COUNT_PROC_STATUS_FAIL);
			}
			updatePrchsProdCntProcStatusScRes = this.purchaseProductCountRepository
					.updatePrchsProdCntProcStatus(updatePrchsProdCntProcStatusScReq);

			if (apiRtn) {
				successCnt = successCnt + updatePrchsProdCntProcStatusScRes.getResultCnt();
			} else {
				failCnt = failCnt + updatePrchsProdCntProcStatusScRes.getResultCnt();
			}

		}

		purchaseProductCountSacResult.setTotCnt(totCnt);
		purchaseProductCountSacResult.setSuccessCnt(successCnt);
		purchaseProductCountSacResult.setFailCnt(failCnt);

		return purchaseProductCountSacResult;

	}

}
