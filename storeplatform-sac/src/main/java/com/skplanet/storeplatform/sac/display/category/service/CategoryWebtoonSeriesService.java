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

import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryWebtoonSeriesSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryWebtoonSeriesSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 웹툰 회차별 목록 조회.
 * 
 * Updated on : 2014. 2. 4. Updated by : 이승훈, 엔텔스.
 */
public interface CategoryWebtoonSeriesService {
	/**
	 * <pre>
	 * 특정 상품 vod 조회.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return CategoryWebtoonSeriesSacRes
	 */
	public CategoryWebtoonSeriesSacRes getCategoryWebtoonSeriesList(CategoryWebtoonSeriesSacReq req,
			SacRequestHeader header);
}
