/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.order.repository;

import com.skplanet.storeplatform.external.client.shopping.vo.BizCouponPublishEcReq;

/**
 * 
 * Biz 쿠폰발급요청 repository
 * 
 * Updated on : 2014. 2. 27. Updated by : ntels_yjw, nTels.
 */
public interface PurchaseShoppingOrderRepository {

	/**
	 * 
	 * <pre>
	 * Biz쿠폰 발급 요청.
	 * </pre>
	 * 
	 * @param request
	 *            쿠폰발급요청
	 * @return void
	 */
	public void createBizCouponPublish(BizCouponPublishEcReq request);

}
