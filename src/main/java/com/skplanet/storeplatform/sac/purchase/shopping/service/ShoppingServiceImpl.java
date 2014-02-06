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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.sac.purchase.shopping.repository.ShoppingRepository;
import com.skplanet.storeplatform.sac.purchase.shopping.vo.CouponPublishAvailableSacParam;
import com.skplanet.storeplatform.sac.purchase.shopping.vo.CouponPublishAvailableSacResult;
import com.skplanet.storeplatform.sac.purchase.shopping.vo.CouponUseStatusSacParam;
import com.skplanet.storeplatform.sac.purchase.shopping.vo.CouponUseStatusSacResult;

/**
 * 쇼핑쿠폰 Service Implements.
 * 
 * Updated on : 2014. 2. 3. Updated by : nTels_cswoo81, nTels.
 */
@Service
public class ShoppingServiceImpl implements ShoppingService {

	@Autowired
	private ShoppingRepository shoppingRepository;

	@Override
	public CouponUseStatusSacResult getCouponUseStatus(CouponUseStatusSacParam couponUseStatusSacParam) {

		return this.shoppingRepository.getCouponUseStatus(couponUseStatusSacParam);

	}

	@Override
	public CouponPublishAvailableSacResult getCouponPublishAvailable(
			CouponPublishAvailableSacParam couponPublishAvailableSacParam) {

		return this.shoppingRepository.getCouponPublishAvailable(couponPublishAvailableSacParam);

	}

}
