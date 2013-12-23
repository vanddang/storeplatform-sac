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

/**
 * 웹툰 상품 조회 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2013-12-19 Updated by : 김형식, gtsoft.
 */
public interface ApiCouponService {
	/**
	 * <pre>
	 * 일반/특정 상품 카테고리 리스트 조회.
	 * </pre>
	 * 
	 * @param productCategoryReqVO
	 *            일반/특정 상품 카테고리 Value Object
	 * @return 일반/특정 상품 카테고리 리스트
	 */
	CouponResponseInfo doLoadBalance(CouponParameterInfo requestVO);

}
