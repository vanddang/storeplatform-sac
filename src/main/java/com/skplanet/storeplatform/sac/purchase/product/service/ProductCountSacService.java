/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.product.service;

import com.skplanet.storeplatform.sac.purchase.product.vo.GetProductCountSacReq;
import com.skplanet.storeplatform.sac.purchase.product.vo.GetProductCountSacRes;
import com.skplanet.storeplatform.sac.purchase.product.vo.SetProductCountSacReq;
import com.skplanet.storeplatform.sac.purchase.product.vo.SetProductCountSacRes;

/**
 * 상품 구매가능 갯수 GET, SET 처리
 * 
 * Updated on : 2014. 2. 13. Updated by : 조용진, NTELS.
 */
public interface ProductCountSacService {

	/**
	 * 상품 구매가능 갯수 GET처리 .
	 * 
	 * @param getProductCountSacReq
	 *            요청정보
	 * @return GetProductCountSacRes
	 */
	GetProductCountSacRes searchProductCount(GetProductCountSacReq getProductCountSacReq);

	/**
	 * 상품 구매가능 갯수 SET처리 .
	 * 
	 * @param setProductCountSacReq
	 *            요청정보
	 * @return SetProductCountSacRes
	 */
	SetProductCountSacRes createProductCount(SetProductCountSacReq setProductCountSacReq);

}
