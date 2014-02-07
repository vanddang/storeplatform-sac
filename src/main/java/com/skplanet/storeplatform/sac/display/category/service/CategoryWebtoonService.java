package com.skplanet.storeplatform.sac.display.category.service;

import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryWebtoonSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryWebtoonSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

public interface CategoryWebtoonService {

	/**
	 * <pre>
	 * 일반 카테고리 웹툰 리스트 조회.
	 * </pre>
	 * 
	 * @param CategoryWebtoonSacReq
	 * @return CategoryWebtoonRes 리스트
	 */
	public CategoryWebtoonSacRes searchWebtoonList(SacRequestHeader header, CategoryWebtoonSacReq req);
}
