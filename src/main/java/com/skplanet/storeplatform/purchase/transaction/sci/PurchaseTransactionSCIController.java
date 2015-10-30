/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.transaction.sci;

import org.springframework.beans.factory.annotation.Autowired;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.purchase.client.transaction.sci.PurchaseTransactionSCI;
import com.skplanet.storeplatform.purchase.client.transaction.vo.PurchaseTransactionScReq;
import com.skplanet.storeplatform.purchase.client.transaction.vo.PurchaseTransactionScRes;
import com.skplanet.storeplatform.purchase.transaction.service.PurchaseTransactionService;

/**
 * 구매 취소 SCI Controller.
 * 
 * Updated on : 2014. 1. 20. Updated by : nTels_cswoo81, nTels.
 */
@LocalSCI
public class PurchaseTransactionSCIController implements PurchaseTransactionSCI {

	@Autowired
	private PurchaseTransactionService purchaseTransactionService;

	@Override
	public PurchaseTransactionScRes searchTransaction(PurchaseTransactionScReq request) {
		return this.purchaseTransactionService.searchTransaction(request);
	}

	@Override
	public void createTransaction(PurchaseTransactionScReq request) {
		this.purchaseTransactionService.createTransaction(request);
	}

	@Override
	public int updateTransaction(PurchaseTransactionScReq request) {
		return this.purchaseTransactionService.updateTransaction(request);
	}

}
