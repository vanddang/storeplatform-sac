package com.skplanet.storeplatform.sac.display.feature.brandshop.service;

import com.skplanet.storeplatform.sac.client.display.vo.brandshop.BrandshopSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.brandshop.BrandshopSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 브렌드샵 테마 상품 조회 Input Value Object.
 * 
 * Updated on : 2014. 2. 25. Updated by : 이승훈, 엔텔스.
 */

public interface BrandshopService {
	/**
	 * 
	 * <pre>
	 * 브렌드샵 테마 조회.
	 * </pre>
	 * 
	 * @param BrandshopSacReq
	 *            BrandshopSacReq
	 * @return BrandShopRes
	 */
	BrandshopSacRes searchBrandshop(BrandshopSacReq req, SacRequestHeader header);

	/**
	 * 
	 * <pre>
	 * 브렌드샵 테마 리스트 조회.
	 * </pre>
	 * 
	 * @param BrandshopSacReq
	 *            BrandshopSacReq
	 * @return BrandShopRes
	 */
	BrandshopSacRes searchBrandshopList(BrandshopSacReq req, SacRequestHeader header);
}
