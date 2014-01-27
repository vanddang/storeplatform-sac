/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.category.service;

import com.skplanet.storeplatform.sac.client.display.vo.category.CategorySpecificReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategorySpecificRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 특정 상품 조회 Service
 * 
 * Updated on : 2014. 1. 27. Updated by : 오승민, 인크로스.
 */
public interface CategorySpecificProductService {
	/**
	 * <pre>
	 * 특정 상품 조회.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return CategorySpecificRes
	 */
	public CategorySpecificRes getSpecificProductList(CategorySpecificReq req, SacRequestHeader header);
}
