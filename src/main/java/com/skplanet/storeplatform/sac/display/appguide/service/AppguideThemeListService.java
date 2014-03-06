package com.skplanet.storeplatform.sac.display.appguide.service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.display.vo.appguide.AppguideSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.appguide.AppguideSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * App guide 테마 추천 목록 Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 03. 06. Updated by : 윤주영, SK 플래닛.
 */
public interface AppguideThemeListService {

	/**
	 * <pre>
	 * 테마 추천 목록 조회.
	 * </pre>
	 * 
	 * @param AppguideSacReq
	 *            requestVO
	 * @param SacRequestHeader
	 *            requestHeader
	 * @return AppguideSacRes
	 */
	public AppguideSacRes searchThemeRecommendList(AppguideSacReq requestVO, SacRequestHeader requestHeader)
			throws StorePlatformException;
}
