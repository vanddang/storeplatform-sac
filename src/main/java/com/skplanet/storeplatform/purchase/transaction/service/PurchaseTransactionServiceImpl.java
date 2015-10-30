/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.transaction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.purchase.client.transaction.vo.PurchaseTransactionScReq;
import com.skplanet.storeplatform.purchase.client.transaction.vo.PurchaseTransactionScRes;

/**
 * 트랜잭션 관련 Service Implements.
 * 
 * Updated on : 2014. 1. 20. Updated by : nTels_cswoo81, nTels.
 */
@Service
public class PurchaseTransactionServiceImpl implements PurchaseTransactionService {

	@Autowired
	@Qualifier("scPurchase")
	private CommonDAO scPurchaseDAO;

	@Override
	public PurchaseTransactionScRes searchTransaction(PurchaseTransactionScReq request) {
		return this.scPurchaseDAO.queryForObject("Transaction.searchTransaction", request,
				PurchaseTransactionScRes.class);
	}

	@Override
	public void createTransaction(PurchaseTransactionScReq request) {
		try {
			this.scPurchaseDAO.insert("Transaction.createTransaction", request);
		} catch (DuplicateKeyException e) {
			throw new StorePlatformException("SAC_PUR_8105");
		}
	}

	@Override
	public int updateTransaction(PurchaseTransactionScReq request) {
		return this.scPurchaseDAO.update("Transaction.updateTransaction", request);
	}

}
