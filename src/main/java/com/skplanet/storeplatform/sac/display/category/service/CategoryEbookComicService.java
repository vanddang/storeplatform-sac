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

import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryEbookComicSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryEbookComicSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * CategoryEbookComic Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 01. 06. Updated by : 이태희, SK 플래닛.
 */
public interface CategoryEbookComicService {
	/**
	 * 
	 * <pre>
	 * 일반 카테고리 ebook/만화 상품 조회.
	 * </pre>
	 * 
	 * @param CategoryAppReq
	 * @return CategoryAppRes
	 */
	CategoryEbookComicSacRes searchEbookComicList(CategoryEbookComicSacReq req, SacRequestHeader header);
}
