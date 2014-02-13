/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.shopping.service;

import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingReq;
import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingRes;

/**
 * 쇼핑 판매건수 Interface
 * 
 * Updated on : 2014. 02. 13. Updated by : 김형식
 */
public interface ShoppingInternalService {

	/**
	 * 쇼핑 판매건수 기능을 제공한다.
	 * 
	 * @param request
	 *            쇼핑 정보 요청
	 * @return ShoppingRes
	 */
	public ShoppingRes searchShoppingInternal(ShoppingReq request);

}
