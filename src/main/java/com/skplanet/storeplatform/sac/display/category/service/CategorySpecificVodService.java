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

import com.skplanet.storeplatform.sac.client.display.vo.category.CategorySpecificSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategorySpecificSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 특정 상품 vod 조회 Service
 * 
 * Updated on : 2014. 2. 4. Updated by : 이승훈, 엔텔스.
 */
public interface CategorySpecificVodService {
	/**
	 * <pre>
	 * 특정 상품 vod 조회.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return CategorySpecificSacRes
	 */
	public CategorySpecificSacRes getSpecificVodList(CategorySpecificSacReq req, SacRequestHeader header);
}
