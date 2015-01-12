package com.skplanet.storeplatform.sac.display.appguide.service;

import com.skplanet.storeplatform.sac.client.display.vo.appguide.AppguideIsfSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.appguide.AppguideSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * App guide Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 02. 26. Updated by : 윤주영, SK 플래닛.
 */
public interface AppguideIsfService {

	/**
	 * <pre>
	 * App guide 조회.
	 * </pre>
	 * 
	 * @param AppguideSacReq
	 *            requestVO
	 * @param SacRequestHeader
	 *            requestHeader
	 * @return AppguideSacRes
	 */
	public AppguideSacRes searchIsfRecommendList(AppguideIsfSacReq requestVO, SacRequestHeader requestHeader);

}
