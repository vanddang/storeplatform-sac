/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.internal.purchase.shopping.sci;

import org.springframework.validation.annotation.Validated;

import com.skplanet.storeplatform.framework.core.proxy.SCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.shopping.vo.CouponUseStatusSacInReq;
import com.skplanet.storeplatform.sac.client.internal.purchase.shopping.vo.CouponUseStatusSacInRes;

/**
 * 쇼핑 쿠폰 SCI
 * 
 * Updated on : 2014. 2. 13. Updated by : nTels_cswoo81, nTels.
 */
@SCI
public interface ShoppingInternalSCI {

	/**
	 * 
	 * <pre>
	 * 쇼핑 쿠폰 사용 여부 조회.
	 * 0=미사용, 1=사용, 2=취소/환불.
	 * </pre>
	 * 
	 * @param couponUseStatusSacInReq
	 *            couponUseStatusSacInReq
	 * @return CouponUseStatusSacInRes
	 */
	CouponUseStatusSacInRes getCouponUseStatus(@Validated CouponUseStatusSacInReq couponUseStatusSacInReq);

}
