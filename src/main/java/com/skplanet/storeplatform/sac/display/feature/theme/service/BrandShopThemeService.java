package com.skplanet.storeplatform.sac.display.feature.theme.service;

import com.skplanet.storeplatform.sac.client.display.vo.theme.BrandShopThemeReq;
import com.skplanet.storeplatform.sac.client.display.vo.theme.BrandShopThemeRes;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2013. 12. 23. Updated by : 이석희, SK 플래닛.
 */

public interface BrandShopThemeService {
	/**
	 * 
	 * <pre>
	 * 브랜드샵 테마 상품 리스트 조회.
	 * </pre>
	 * 
	 * @param brandShopThemeReq
	 *            brandShopThemeReq
	 * @return BrandShopThemeRes
	 */
	BrandShopThemeRes searchBrandShopThemeList(BrandShopThemeReq brandShopThemeReq);
}
