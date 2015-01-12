package com.skplanet.storeplatform.sac.display.appguide.service;

import com.skplanet.storeplatform.sac.client.display.vo.appguide.AppguideSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.appguide.AppguideThemeProdSacReq;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * App guide 테마 추천별 상품 목록 조회 Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 03. 06. Updated by : 윤주영, SK 플래닛.
 */
public interface AppguideThemeProductService {

	/**
	 * <pre>
	 * 테마 추천별 상품 목록 조회.
	 * </pre>
	 * 
	 * @param AppguideThemeProdSacReq
	 *            requestVO
	 * @param SacRequestHeader
	 *            requestHeader
	 * @return AppguideSacRes
	 */
	public AppguideSacRes searchThemeRecommendProductList(AppguideThemeProdSacReq requestVO,
			SacRequestHeader requestHeader);
}
