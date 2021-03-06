/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.migration.service;

import com.skplanet.storeplatform.purchase.client.order.vo.PurchaseTransferScReq;
import com.skplanet.storeplatform.sac.client.purchase.migration.vo.PurchaseMigList;
import com.skplanet.storeplatform.sac.client.purchase.vo.migration.PurchaseMigInformationSacReq;

import java.util.List;

/**
 * 
 * 구매SC - 구매내역 이관 서비스 인터페이스
 * 
 * Updated on : 2015. 2. 5. Updated by : 양주원, nTels.
 */
public interface PurchaseTransferSCService {

	/**
	 * 
	 * <pre>
	 * 구매내역 이관.
	 * </pre>
	 * 
	 * @param req
	 *            구매예약 정보 목록
	 * @return 예약된 갯수
	 */
	public int createPurchaseTransfer(PurchaseTransferScReq req);

	int countMigrationListByStatus(PurchaseMigInformationSacReq req);

	List<PurchaseMigList> searchMigrationListByStatus(PurchaseMigInformationSacReq req);
}
