/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.api;

import com.skplanet.storeplatform.sac.api.vo.CouponParameterInfo;
import com.skplanet.storeplatform.sac.api.vo.CouponResponseInfo;

public interface ApiCouponService {
	/**
	 * <pre>
	 * 쇼핑 상품 전처리.
	 * </pre>
	 * 
	 * @param CouponParameterInfo
	 *            쇼핑 상품 전처리
	 * @return CouponResponseInfo
	 */
	CouponResponseInfo doLoadBalance(CouponParameterInfo requestVO);

}
