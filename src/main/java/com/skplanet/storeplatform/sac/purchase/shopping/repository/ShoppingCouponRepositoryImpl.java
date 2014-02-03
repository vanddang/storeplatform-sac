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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.external.client.shopping.sci.CouponSCI;
import com.skplanet.storeplatform.sac.purchase.shopping.vo.UseStatusSacParam;
import com.skplanet.storeplatform.sac.purchase.shopping.vo.UseStatusSacResult;

/**
 * 쇼핑쿠폰 Repository Implements.
 * 
 * Updated on : 2014. 2. 3. Updated by : nTels_cswoo81, nTels.
 */
@Component
public class ShoppingCouponRepositoryImpl implements ShoppingCouponRepository {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CouponSCI couponSCI;

	@Override
	public UseStatusSacResult getUseStatus(UseStatusSacParam useStatusSacParam) {

		return null;

	}

}
