/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.transaction.sci;

import com.skplanet.storeplatform.framework.core.proxy.SCI;
import com.skplanet.storeplatform.purchase.client.transaction.vo.PurchaseTransactionScReq;
import com.skplanet.storeplatform.purchase.client.transaction.vo.PurchaseTransactionScRes;

/**
 * 구매 취소 SCI
 * 
 * Updated on : 2014. 1. 20. Updated by : nTels_cswoo81, nTels.
 */
@SCI
public interface PurchaseTransactionSCI {

	/**
	 * 
	 * <pre>
	 * 프로세스 트랙잭션 조회.
	 * </pre>
	 * 
	 * @param PurchaseTransactionScReq
	 *            request
	 * @return PurchaseTransactionScRes
	 */
	PurchaseTransactionScRes searchTransaction(PurchaseTransactionScReq request);

	/**
	 * 
	 * <pre>
	 * 프로세스 트랙잭션 등록.
	 * </pre>
	 * 
	 * @param PurchaseTransactionScReq
	 *            request
	 * @return void
	 */
	void createTransaction(PurchaseTransactionScReq request);

	/**
	 * 
	 * <pre>
	 * 프로세스 트랙잭션 수정
	 * </pre>
	 * 
	 * @param PurchaseTransactionScReq
	 *            request
	 * @return Integer
	 */
	int updateTransaction(PurchaseTransactionScReq request);

}
