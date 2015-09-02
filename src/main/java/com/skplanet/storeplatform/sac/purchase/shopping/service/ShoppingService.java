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

import com.skplanet.storeplatform.sac.purchase.shopping.vo.*;

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
	 * 쿠폰 사용 초기
	 *
	 * @param couponRestoreStatusSacParam the coupon restore status sac param
	 * @return the coupon restore status sac result
	 */
	public CouponRestoreStatusSacResult restoreCouponStatus(CouponRestoreStatusSacParam couponRestoreStatusSacParam);

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
	public CouponPublishAvailableSacV2Result getCouponPublishAvailableV2(
			CouponPublishAvailableSacV2Param couponPublishAvailableSacParam);

	/**
	 * 
	 * <pre>
	 * 쇼핑쿠폰 재고 조회.
	 * </pre>
	 * 
	 * @param couponStockSacParam
	 *            couponStockSacParam
	 * @return CouponStockSacResult
	 */
	// public CouponStockSacResult getCouponStock(CouponStockSacParam couponStockSacParam);

}
