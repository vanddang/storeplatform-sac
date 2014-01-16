/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.order.dummy.service;

import com.skplanet.storeplatform.sac.purchase.order.dummy.vo.DummyProduct;

/**
 * 
 * Dummy 전시상품 서비스 구현체
 * 
 * Updated on : 2014. 1. 16. Updated by : 이승택, nTels.
 */
public class DummyProductServiceImpl {
	/**
	 * 
	 * <pre>
	 * 상품 정보 조회.
	 * </pre>
	 * 
	 * @param tenantId
	 *            테넌트ID
	 * @param prodId
	 *            상품ID
	 * @param deviceModelCd
	 *            단말모델코드
	 * @return Dummy 상품정보
	 */
	public DummyProduct getProductInfo(String tenantId, String prodId, String deviceModelCd) {
		DummyProduct dummy = new DummyProduct();
		dummy.setProdId(prodId);
		dummy.setProdAmt(0.0);
		dummy.setProdGrdCd("PD004401");
		dummy.setAvailableLimitPrchsPathCd(null);
		dummy.setSvcGrpCd("DP000201");
		dummy.setSvcTypeCd(null);
		dummy.setTopCategoryNo(null);
		dummy.setAvailableLimitCnt(0);
		dummy.setUsePeriodUnit("PD00310");
		dummy.setUsePeriodCnt(0);
		dummy.setbSell(true);
		dummy.setbLimitProd(false);
		dummy.setbDupleProd(false);
		dummy.setbMdnProd(false);
		dummy.setbSupport(true);

		return dummy;
	}
}
