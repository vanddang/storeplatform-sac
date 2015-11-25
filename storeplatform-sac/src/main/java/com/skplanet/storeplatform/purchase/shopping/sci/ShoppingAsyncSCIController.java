/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.shopping.sci;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.purchase.client.shopping.sci.ShoppingAsyncSCI;
import com.skplanet.storeplatform.purchase.client.shopping.vo.ShoppingAsyncItemSc;
import com.skplanet.storeplatform.purchase.client.shopping.vo.ShoppingAsyncReq;
import com.skplanet.storeplatform.purchase.client.shopping.vo.ShoppingAsyncRes;
import com.skplanet.storeplatform.purchase.shopping.service.ShoppingAsyncScService;

/**
 * 쿠폰발급비동기응답 Controller
 * 
 * Updated on : 2013. 12. 10. Updated by : ntels_yjw, nTels.
 */
@LocalSCI
public class ShoppingAsyncSCIController implements ShoppingAsyncSCI {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ShoppingAsyncScService service;

	/**
	 * 쿠폰발급비동기응답.
	 * 
	 * @param request
	 *            쿠폰정보
	 * @return ShoppingAsyncRes
	 */
	@Override
	public ShoppingAsyncRes updateShoppingAsync(ShoppingAsyncReq request) {

		this.logger.debug("### Shopping Async Request {} : " + request.toString());
		ShoppingAsyncRes response = new ShoppingAsyncRes();
		response.setCount(this.service.updateShoppingAsync(request));
		return response;

	}

	/**
	 * 쿠폰발급비동기응답.
	 * 
	 * @param request
	 *            쿠폰정보
	 * @return int
	 */
	@Override
	public int updateShoppingAsyncItem(ShoppingAsyncItemSc request) {
		return this.service.updateShoppingAsyncItem(request);

	}

	/**
	 * 구매상태변경.
	 * 
	 * @param request
	 *            구매상세
	 * @return int
	 */
	@Override
	public int updatePrchsStatus(ShoppingAsyncItemSc request) {
		return this.service.updatePrchsStatus(request);
	}

	/**
	 * Biz쿠폰 발급 대상 조회.
	 * 
	 * @param request
	 *            구매상세
	 * @return int
	 */
	@Override
	public int searchPrchsIdCnt(Map<String, Object> request) {
		return this.service.searchPrchsIdCnt(request);
	}
}
