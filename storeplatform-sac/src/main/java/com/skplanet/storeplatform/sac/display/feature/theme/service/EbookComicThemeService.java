package com.skplanet.storeplatform.sac.display.feature.theme.service;

import com.skplanet.storeplatform.sac.client.display.vo.theme.EbookComicThemeReq;
import com.skplanet.storeplatform.sac.client.display.vo.theme.EbookComicThemeRes;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2013. 12. 23. Updated by : 이석희, SK 플래닛.
 */

public interface EbookComicThemeService {

	/**
	 * 
	 * <pre>
	 * Ebook/코믹 테마 상품 리스트 조회.
	 * </pre>
	 * 
	 * @param ebookComicThemeReq
	 *            ebookComicThemeReq
	 * @return BrandShopThemeRes
	 */
	EbookComicThemeRes searchEbookComicThemeList(EbookComicThemeReq ebookComicThemeReq);
}
