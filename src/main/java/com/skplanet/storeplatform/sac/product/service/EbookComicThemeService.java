package com.skplanet.storeplatform.sac.product.service;

import com.skplanet.storeplatform.sac.client.product.vo.theme.EbookComicThemeRequest;
import com.skplanet.storeplatform.sac.client.product.vo.theme.EbookComicThemeResponse;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2013. 12. 23. Updated by : 이석희, SK 플래닛.
 */

public interface EbookComicThemeService {
	/**
	 * <pre>
	 * Ebook/코믹 테마 리스트 조회
	 * </pre>
	 * 
	 * @param BestAppRequestVO
	 *            Ebook/코믹 테마 상품 리스트 요청 Value Object
	 * @return Ebook/코믹 테마 상품 리스트
	 */
	EbookComicThemeResponse searchEbookComicThemeList(EbookComicThemeRequest ebookComicThemeRequest);
}
