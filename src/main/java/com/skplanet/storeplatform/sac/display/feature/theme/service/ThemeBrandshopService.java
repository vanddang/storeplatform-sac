package com.skplanet.storeplatform.sac.display.feature.theme.service;

import com.skplanet.storeplatform.sac.client.display.vo.theme.ThemeBrandshopSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.theme.ThemeBrandshopSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 브렌드샵 테마 상품 조회 Input Value Object.
 * 
 * Updated on : 2014. 2. 7. Updated by : 이승훈, 엔텔스.
 */

public interface ThemeBrandshopService {
	/**
	 * 
	 * <pre>
	 * 브렌드샵 테마 리스트 조회.
	 * </pre>
	 * 
	 * @param ThemeBrandshopSacReq
	 *            ThemeBrandshopSacReq
	 * @return BrandShopThemeRes
	 */
	ThemeBrandshopSacRes searchThemeBrandshopList(ThemeBrandshopSacReq req, SacRequestHeader header);
}
