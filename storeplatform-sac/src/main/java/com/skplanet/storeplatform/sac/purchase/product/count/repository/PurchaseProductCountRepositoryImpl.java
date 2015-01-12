/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.product.count.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.purchase.client.product.count.sci.PurchaseCountSCI;
import com.skplanet.storeplatform.purchase.client.product.count.vo.GetPrchsProdCntScReq;
import com.skplanet.storeplatform.purchase.client.product.count.vo.GetPrchsProdCntScRes;
import com.skplanet.storeplatform.purchase.client.product.count.vo.UpdatePrchsProdCntProcStatusScReq;
import com.skplanet.storeplatform.purchase.client.product.count.vo.UpdatePrchsProdCntProcStatusScRes;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.UpdatePurchaseCountSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.UpdatePurchaseCountSacReq;

/**
 * Class 설명
 * 
 * Updated on : 2014. 3. 27. Updated by : nTels_cswoo81, nTels.
 */
@Component
public class PurchaseProductCountRepositoryImpl implements PurchaseProductCountRepository {

	@Autowired
	private PurchaseCountSCI purchaseCountSCI;

	@Autowired
	private UpdatePurchaseCountSCI updatePurchaseCountSCI;

	@Override
	public UpdatePrchsProdCntProcStatusScRes updatePrchsProdCntProcStatus(
			UpdatePrchsProdCntProcStatusScReq updatePrchsProdCntProcStatusScReq) {
		return this.purchaseCountSCI.updatePrchsProdCntProcStatus(updatePrchsProdCntProcStatusScReq);
	}

	@Override
	public GetPrchsProdCntScRes getPrchsProdCnt(GetPrchsProdCntScReq getPrchsProdCntScReq) {
		return this.purchaseCountSCI.getPrchsProdCnt(getPrchsProdCntScReq);
	}

	@Override
	public void updatePurchaseCount(List<UpdatePurchaseCountSacReq> reqList) {
		this.updatePurchaseCountSCI.updatePurchaseCount(reqList);
	}

}
