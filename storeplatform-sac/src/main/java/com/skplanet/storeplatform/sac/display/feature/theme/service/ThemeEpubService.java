package com.skplanet.storeplatform.sac.display.feature.theme.service;

import com.skplanet.storeplatform.sac.client.display.vo.theme.ThemeEpubSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.theme.ThemeEpubSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 
 * 
 * Updated on : 2014. 2. 7. Updated by : 이승훈, 엔텔스.
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
