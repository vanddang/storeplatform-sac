package com.skplanet.storeplatform.sac.display.category.service;

import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryWebtoonSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryWebtoonSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * CategoryWebtoonService Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 01. 03. Updated by : 김형식, SK 플래닛.
 */
public interface CategoryWebtoonService {

	/**
	 * 
	 * <pre>
	 * 일반 카테고리 웹툰 리스트 조회.
	 * </pre>
	 * 
	 * @param req
	 *            파라미터
	 * @param header
	 *            공통헤더
	 * @return 일반 카테고리 웹툰 리스트
	 */
	public CategoryWebtoonSacRes searchWebtoonList(SacRequestHeader header, CategoryWebtoonSacReq req);
}
