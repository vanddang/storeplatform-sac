package com.skplanet.storeplatform.sac.display.feature.theme.service;

import com.skplanet.storeplatform.sac.client.display.vo.theme.ThemeEpubSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.theme.ThemeEpubSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2013. 12. 23. Updated by : 이석희, SK 플래닛.
 */

public interface ThemeEpubService {
	/**
	 * 
	 * <pre>
	 * 테마존 테마 상품 리스트 조회.
	 * </pre>
	 * 
	 * @param ThemeEpubSacReq
	 *            ThemeEpubSacReq
	 * @return BrandShopThemeRes
	 */
	ThemeEpubSacRes searchThemeEpubList(ThemeEpubSacReq req, SacRequestHeader header);
}
