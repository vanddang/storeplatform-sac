/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.migration.sci;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.purchase.client.migration.sci.PurchaseTransferSCI;
import com.skplanet.storeplatform.purchase.client.order.vo.PurchaseTransferScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.PurchaseTransferScRes;
import com.skplanet.storeplatform.purchase.migration.service.PurchaseTransferSCService;
import com.skplanet.storeplatform.sac.client.purchase.migration.vo.PurchaseMigList;
import com.skplanet.storeplatform.sac.client.purchase.vo.migration.PurchaseMigInformationSacReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 구매SC - 구매내역 이관 컨트롤러
 * <p/>
 * Updated on : 2015. 2. 5. Updated by : 양주원, nTels.
 */
@LocalSCI
public class PurchaseTransferSCIController implements PurchaseTransferSCI {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PurchaseTransferSCService purchaseTransferSCService;

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
	@Override
	public PurchaseTransferScRes createPurchaseTransfer(PurchaseTransferScReq req) {

		// TB_구매상세 생성 (구매예약)
		int count = this.purchaseTransferSCService.createPurchaseTransfer(req);

		// Response
		PurchaseTransferScRes response = new PurchaseTransferScRes();
		response.setCount(count);
		return response;
	}

	/**
	 *
	 * <pre>
	 * 구매이관정보 상태별 카운트.
	 * </pre>
	 *
	 * @param req
	 *            구매이관정보 조회 요청 VO
	 * @return 구매이관정보 상태별 카운트
	 */
	@Override
	public int countMigrationListByStatus(PurchaseMigInformationSacReq req) {

		return this.purchaseTransferSCService.countMigrationListByStatus(req);
	}

	/**
	 * <pre>
	 * 구매내역이관정보 목록 조회.
	 * </pre>
	 *
	 * @param req the req
	 * @return the list
	 */
	@Override
	public List<PurchaseMigList> searchMigrationListByStatus(PurchaseMigInformationSacReq req) {

		return this.purchaseTransferSCService.searchMigrationListByStatus(req);
	}
}
