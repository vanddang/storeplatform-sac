package com.skplanet.storeplatform.sac.display.category.service;

import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryVoucherSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryVoucherSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * CategorySpecificVoucherService Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2015. 05. 19. Updated by : 김형식, SK 플래닛.
 */
public interface CategorySpecificVoucherService {

	/**
	 * 
	 * <pre>
	 * 카테고리 이용권 상세 조회.
	 * </pre>
	 * 
	 * @param req
	 *            파라미터
	 * @param header
	 *            공통헤더
	 * @return 일반 카테고리 이용권 상테
	 */
	public CategoryVoucherSacRes searchSpecificVoucherDetail(SacRequestHeader header, CategoryVoucherSacReq req);
}
