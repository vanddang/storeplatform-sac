package com.skplanet.storeplatform.sac.display.category.service;

import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryVodBoxSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryVodBoxSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * CategoryVodBoxService 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 02. 10. Updated by : 유시혁.
 */
public interface CategoryVodBoxService {

	/**
	 * <pre>
	 * Vod Box 리스트 조회.
	 * </pre>
	 * 
	 * @param requestVO
	 *            CategoryVodBoxSacReq
	 * @param requestHeader
	 *            SacRequestHeader
	 * @return CategoryVodBoxSacRes
	 */
	public CategoryVodBoxSacRes searchVodBoxList(CategoryVodBoxSacReq requestVO, SacRequestHeader requestHeader);
}
