/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.shopping.sci;

import java.util.Map;

import com.skplanet.storeplatform.framework.core.proxy.SCI;
import com.skplanet.storeplatform.purchase.client.shopping.vo.ShoppingAsyncItemSc;
import com.skplanet.storeplatform.purchase.client.shopping.vo.ShoppingAsyncReq;
import com.skplanet.storeplatform.purchase.client.shopping.vo.ShoppingAsyncRes;

/**
 * 쿠폰발급비동기응답 Interface
 * 
 * Updated on : 2013. 12. 13. Updated by : ntels_yjw, nTels.
 */
@SCI
public interface ShoppingAsyncSCI {

	/**
	 * 쿠폰발급비동기응답.
	 * 
	 * @param request
	 *            쿠폰정보
	 * @return ShoppingAsyncRes
	 */
	public ShoppingAsyncRes updateShoppingAsync(ShoppingAsyncReq request);

	public int updateShoppingAsyncItem(ShoppingAsyncItemSc request);

	public int updatePrchsStatus(ShoppingAsyncItemSc request);

	/**
	 * Biz쿠폰 발급 대상 조회.
	 * 
	 * @param prchsId
	 *            구매ID
	 * @return int
	 */
	public int searchPrchsIdCnt(Map<String, Object> request);

}
