/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.common.service;

import com.skplanet.storeplatform.sac.purchase.order.dummy.vo.DummyProduct;

/**
 * 
 * 전시Part 정보 조회 서비스 인터페이스
 * 
 * Updated on : 2014. 1. 22. Updated by : 이승택, nTels.
 */
public interface PurchaseDisplayPartService {

	/**
	 * 
	 * <pre>
	 * Dummy 상품 정보 조회.
	 * </pre>
	 * 
	 * @param tenantId
	 *            테넌트 ID
	 * @param systemId
	 *            시스템 ID
	 * @param prodId
	 *            상품 ID
	 * @param deviceModelCd
	 *            단말 모델 코드
	 * @return 상품 정보
	 */
	public DummyProduct searchDummyProductDetail(String tenantId, String systemId, String prodId, String deviceModelCd);
}
