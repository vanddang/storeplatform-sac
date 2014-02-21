package com.skplanet.storeplatform.sac.display.feature.theme.service;

import com.skplanet.storeplatform.sac.client.display.vo.theme.ThemeThemeZoneSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.theme.ThemeThemeZoneSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 
 * 
 * Updated on : 2014. 2. 21. Updated by : 이승훈, 엔텔스.
 */

public interface ThemeThemeZoneService {
	/**
	 * 
	 * <pre>
	 * 테마존 테마 상품 리스트 조회.
	 * </pre>
	 * 
	 * @param ThemeThemeZoneSacReq
	 *            ThemeThemeZoneSacReq
	 * @return BrandShopThemeRes
	 */
	ThemeThemeZoneSacRes searchThemeThemeZoneList(ThemeThemeZoneSacReq req, SacRequestHeader header);
}
