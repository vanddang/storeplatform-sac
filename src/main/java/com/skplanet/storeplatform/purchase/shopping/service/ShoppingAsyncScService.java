/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.shopping.service;

import java.util.Map;

import com.skplanet.storeplatform.purchase.client.shopping.vo.ShoppingAsyncItemSc;
import com.skplanet.storeplatform.purchase.client.shopping.vo.ShoppingAsyncReq;

/**
 * 쿠폰발급비동기응답 Interface
 * 
 * Updated on : 2014-01-10 Updated by : ntels_yjw, nTels.
 */
public interface ShoppingAsyncScService {

	/**
	 * 쿠폰발급비동기응답.
	 * 
	 * @param request
	 *            쿠폰정보
	 * @return int
	 */
	public int updateShoppingAsync(ShoppingAsyncReq request);

	/**
	 * 쿠폰발급비동기응답.
	 * 
	 * @param request
	 *            쿠폰정보
	 * @return int
	 */
	public int updateShoppingAsyncItem(ShoppingAsyncItemSc request);

	/**
	 * 구매상태변경.
	 * 
	 * @param request
	 *            구매상세
	 * @return int
	 */
	public int updatePrchsStatus(ShoppingAsyncItemSc request);

	/**
	 * Biz쿠폰 발급 대상 조회.
	 * 
	 * @param request
	 *            구매상세
	 * @return int
	 */
	public int searchPrchsIdCnt(Map<String, Object> request);

}
