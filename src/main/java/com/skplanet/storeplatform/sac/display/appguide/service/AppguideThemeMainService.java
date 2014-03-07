package com.skplanet.storeplatform.sac.display.appguide.service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.display.vo.appguide.AppguideSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.appguide.AppguideThemeSacReq;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * App guide 테마 추천 메인 Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 03. 05. Updated by : 윤주영, SK 플래닛.
 */
public interface AppguideThemeMainService {

	/**
	 * <pre>
	 * 테마 추천 메인 조회.
	 * </pre>
	 * 
	 * @param AppguideThemeSacReq
	 *            requestVO
	 * @param SacRequestHeader
	 *            requestHeader
	 * @return AppguideSacRes
	 */
	public AppguideSacRes searchThemeRecommendMain(AppguideThemeSacReq requestVO, SacRequestHeader requestHeader)
			throws StorePlatformException;
}
