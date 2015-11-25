/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.product.count.sci;

import org.springframework.beans.factory.annotation.Autowired;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.purchase.client.product.count.sci.PurchaseCountSCI;
import com.skplanet.storeplatform.purchase.client.product.count.vo.GetPrchsProdCntScReq;
import com.skplanet.storeplatform.purchase.client.product.count.vo.GetPrchsProdCntScRes;
import com.skplanet.storeplatform.purchase.client.product.count.vo.InsertPurchaseProductCountScReq;
import com.skplanet.storeplatform.purchase.client.product.count.vo.InsertPurchaseProductCountScRes;
import com.skplanet.storeplatform.purchase.client.product.count.vo.UpdatePrchsProdCntProcStatusScReq;
import com.skplanet.storeplatform.purchase.client.product.count.vo.UpdatePrchsProdCntProcStatusScRes;
import com.skplanet.storeplatform.purchase.product.count.service.PurchaseCountSCService;

/**
 * 
 * 구매 상품 건수 처리 컨트롤러
 * 
 * Updated on : 2014. 3. 25. Updated by : 이승택, nTels.
 */
@LocalSCI
public class PurchaseCountSCIController implements PurchaseCountSCI {
	@Autowired
	private PurchaseCountSCService purchaseCountService;

	/**
	 * 
	 * <pre>
	 * 상품 건수 저장.
	 * </pre>
	 * 
	 * @param req
	 *            상품 건수 저장 요청 VO
	 * @return 상품 건수 저장 응답 VO
	 */
	@Override
	public InsertPurchaseProductCountScRes insertPurchaseProductCount(InsertPurchaseProductCountScReq req) {
		int insertCount = this.purchaseCountService.insertPurchaseProductCount(req);
		return new InsertPurchaseProductCountScRes(insertCount);
	}

	@Override
	public UpdatePrchsProdCntProcStatusScRes updatePrchsProdCntProcStatus(
			UpdatePrchsProdCntProcStatusScReq updatePrchsProdCntProcStatusScReq) {
		return this.purchaseCountService.updatePrchsProdCntProcStatus(updatePrchsProdCntProcStatusScReq);
	}

	@Override
	public GetPrchsProdCntScRes getPrchsProdCnt(GetPrchsProdCntScReq getPrchsProdCntScReq) {
		return this.purchaseCountService.getPrchsProdCnt(getPrchsProdCntScReq);
	}

}
