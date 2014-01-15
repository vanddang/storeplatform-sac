package com.skplanet.storeplatform.sac.display.category.service;

import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryWebtoonReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryWebtoonRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

public interface CategoryWebtoonService {

	/**
	 * <pre>
	 * 일반 카테고리 웹툰 리스트 조회.
	 * </pre>
	 * 
	 * @param CategoryWebtoonReq
	 * @return CategoryWebtoonRes 리스트
	 */
	public CategoryWebtoonRes searchWebtoonList(SacRequestHeader header, CategoryWebtoonReq req);
}
