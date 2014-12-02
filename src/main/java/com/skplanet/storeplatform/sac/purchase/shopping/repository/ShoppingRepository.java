/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.shopping.repository;

import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PaymentInfoSacRes;
import com.skplanet.storeplatform.sac.purchase.shopping.vo.CouponPublishAvailableSacParam;
import com.skplanet.storeplatform.sac.purchase.shopping.vo.CouponPublishAvailableSacResult;
import com.skplanet.storeplatform.sac.purchase.shopping.vo.CouponPublishAvailableSacV2Param;
import com.skplanet.storeplatform.sac.purchase.shopping.vo.CouponPublishAvailableSacV2Result;
import com.skplanet.storeplatform.sac.purchase.shopping.vo.CouponUseStatusSacParam;
import com.skplanet.storeplatform.sac.purchase.shopping.vo.CouponUseStatusSacResult;

/**
 * 쇼핑쿠폰 Repository Interface.
 * 
 * Updated on : 2014. 2. 3. Updated by : nTels_cswoo81, nTels.
 */
public interface ShoppingRepository {

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
	 * @param couponPublishAvailableSacParam
	 *            couponPublishAvailableSacParam
	 * @return CouponPublishAvailableSacResult
	 */
	// public CouponStockSacResult getCouponStock(CouponStockSacParam couponStockSacParam);

	/**
	 * 
	 * <pre>
	 * 상품ID로 상품정보 조회
	 * </pre>
	 * 
	 * @param couponPublishAvailableSacParam
	 *            couponPublishAvailableSacParam
	 * @return CouponPublishAvailableSacResult
	 */
	public PaymentInfoSacRes searchPaymentInfo(CouponPublishAvailableSacParam couponPublishAvailableSacParam);

}
