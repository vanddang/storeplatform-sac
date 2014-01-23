/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.order.precheck;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreatePurchaseReqProduct;
import com.skplanet.storeplatform.sac.purchase.order.dummy.service.DummyDisplayServiceImpl;
import com.skplanet.storeplatform.sac.purchase.order.dummy.vo.DummyProduct;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseOrder;

/**
 * 
 * 상품 정보 체크
 * 
 * Updated on : 2014. 1. 3. Updated by : 이승택, nTels.
 */
public class ProductChecker implements PurchaseOrderChecker {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final DummyDisplayServiceImpl dummyService = new DummyDisplayServiceImpl();

	/**
	 * <pre>
	 * 체크 대상 여부 확인.
	 * </pre>
	 * 
	 * @param purchaseInfo
	 *            구매요청 정보
	 * @return 체크대상여부: true-체크대상, false-체크대상 아님
	 */
	@Override
	public boolean isTarget(PurchaseOrder purchaseInfo) {
		return true;
	}

	/**
	 * <pre>
	 * 구매 전처리 체크 및 필요한 정보 세팅.
	 * </pre>
	 * 
	 * @param purchaseInfo
	 *            구매요청 정보
	 * @return 체크진행 여부: true-체크진행 계속, false-체크진행 중지
	 */
	@Override
	public boolean checkAndSetInfo(PurchaseOrder purchaseInfo) {
		this.logger.debug("PRCHS,DUMMY,PRODUCT,START," + purchaseInfo);

		// 상품 정보 조회 : 테넌트ID, 시스템ID, 상품ID, 디바이스모델코드
		// tenantId, String prodId, String deviceModelCd
		String tenantId = purchaseInfo.getTenantId();
		String systemId = purchaseInfo.getSystemId();
		String deviceModelCd = purchaseInfo.getDeviceModelCd();

		List<DummyProduct> prodList = new ArrayList<DummyProduct>();
		for (CreatePurchaseReqProduct product : purchaseInfo.getCreatePurchaseReq().getProductList()) {
			prodList.add(this.dummyService.getProductInfo(tenantId, systemId, product.getProdId(), deviceModelCd));
		}

		purchaseInfo.setProductList(prodList);

		this.logger.debug("PRCHS,DUMMY,PRODUCT,END," + purchaseInfo);
		return true;
	}
}
