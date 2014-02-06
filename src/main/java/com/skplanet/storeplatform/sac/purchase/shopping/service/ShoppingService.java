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

import com.skplanet.storeplatform.sac.purchase.shopping.vo.CouponPublishAvailableSacParam;
import com.skplanet.storeplatform.sac.purchase.shopping.vo.CouponPublishAvailableSacResult;
import com.skplanet.storeplatform.sac.purchase.shopping.vo.CouponUseStatusSacParam;
import com.skplanet.storeplatform.sac.purchase.shopping.vo.CouponUseStatusSacResult;

/**
 * 쇼핑쿠폰 Service Interface.
 * 
 * Updated on : 2014. 2. 3. Updated by : nTels_cswoo81, nTels.
 */
public interface ShoppingService {

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
	public CouponUseStatusSacResult getCouponUseStatus(CouponUseStatusSacParam couponUseStatusSacParam);

	/**
	 * 
	 * <pre>
	 * 쇼핑쿠폰 발급가능여부 조회.
	 * </pre>
	 * 
	 * @param couponPublishAvailableSacParam
	 *            couponPublishAvailableSacParam
	 * @return CouponPublishAvailableSacResult
	 */
	public CouponPublishAvailableSacResult getCouponPublishAvailable(
			CouponPublishAvailableSacParam couponPublishAvailableSacParam);

}
