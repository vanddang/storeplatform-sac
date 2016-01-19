/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.migration.sci;

import com.skplanet.storeplatform.framework.core.proxy.SCI;
import com.skplanet.storeplatform.purchase.client.order.vo.PurchaseTransferScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.PurchaseTransferScRes;
import com.skplanet.storeplatform.sac.client.purchase.migration.vo.PurchaseMigList;
import com.skplanet.storeplatform.sac.client.purchase.vo.migration.PurchaseMigInformationSacReq;

import java.util.List;

/**
 * 
 * 구매SC - 구매내역 이관 인터페이스
 * 
 * Updated on : 2015. 2. 5. Updated by : 양주원, nTels.
 */
@SCI
public interface PurchaseTransferSCI {

	/**
	 * 
	 * <pre>
	 * 구매내역 이관.
	 * </pre>
	 * 
	 * @param req
	 *            구매내역 이관 요청 VO
	 * @return 구매내역 이관 결과 응답 VO
	 */
	public PurchaseTransferScRes createPurchaseTransfer(PurchaseTransferScReq req);

	/**
	 * <pre>
	 * 구매이관정보 상태별 카운트.
	 * </pre>
	 *
	 * @param req
	 *            구매이관정보 조회 요청 VO
	 * @return 구매이관정보 상태별 카운트
	 */
	public int countMigrationListByStatus(PurchaseMigInformationSacReq req);

	/**
	 * <pre>
	 * 구매내역이관정보 목록 조회.
	 * </pre>
	 *
	 * @param req the req
	 * @return the list
	 */
	public List<PurchaseMigList> searchMigrationListByStatus(PurchaseMigInformationSacReq req);
}
