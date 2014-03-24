/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.shopping.service;

import com.skplanet.storeplatform.external.client.shopping.vo.BizCouponReq;
import com.skplanet.storeplatform.external.client.shopping.vo.BizCouponRes;

//import com.skplanet.storeplatform.external.client.shopping.vo.BizCouponReq;
//import com.skplanet.storeplatform.external.client.shopping.vo.BizCouponRes;

/**
 * 쇼핑쿠폰 Service Interface.
 * 
 * Updated on : 2014. 2. 3. Updated by : nTels_yjw, nTels.
 */
public interface ShoppingAsyncService {

	/**
	 * 
	 * <pre>
	 * 쇼핑쿠폰 사용유무 조회.
	 * </pre>
	 * 
	 * @param couponUseStatusSacParam
	 *            couponUseStatusSacParam
	 * @return CouponUseStatusSacResult
	 */
	public BizCouponRes getShoppingAsync(BizCouponReq request);

}
