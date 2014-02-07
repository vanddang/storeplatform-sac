package com.skplanet.storeplatform.sac.display.feature.theme.service;

import com.skplanet.storeplatform.sac.client.display.vo.theme.ThemeBrandshopSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.theme.ThemeBrandshopSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 브렌드샵 테마 상품 조회 Input Value Object.
 * 
 * Updated on : 2013. 12. 23. Updated by : 이석희, SK 플래닛.
 */

public interface ThemeBrandshopService {
	/**
	 * 
	 * <pre>
	 * 테마존 테마 상품 리스트 조회.
	 * </pre>
	 * 
	 * @param ThemeBrandshopSacReq
	 *            ThemeBrandshopSacReq
	 * @return BrandShopThemeRes
	 */
	ThemeBrandshopSacRes searchThemeBrandshopList(ThemeBrandshopSacReq req, SacRequestHeader header);
}
