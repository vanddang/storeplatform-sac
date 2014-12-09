package com.skplanet.storeplatform.sac.display.category.service;

import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryShoppingSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryShoppingSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * CategorySpecificShoppingService Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 12. 07. Updated by : 김형식, SK 플래닛.
 */
public interface CategorySpecificShoppingService {

	/**
	 * 
	 * <pre>
	 * 카테고리 쇼핑 상세 조회.
	 * </pre>
	 * 
	 * @param req
	 *            파라미터
	 * @param header
	 *            공통헤더
	 * @return 일반 카테고리 쇼핑 상테
	 */
	public CategoryShoppingSacRes searchSpecificShoppingDetail(SacRequestHeader header, CategoryShoppingSacReq req);
}
