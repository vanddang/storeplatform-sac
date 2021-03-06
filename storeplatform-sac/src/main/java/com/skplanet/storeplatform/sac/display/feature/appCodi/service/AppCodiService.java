package com.skplanet.storeplatform.sac.display.feature.appCodi.service;

import com.skplanet.storeplatform.sac.client.display.vo.feature.appCodi.AppCodiListSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.feature.appCodi.AppCodiSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.appCodi.AppCodiV2SacReq;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * App Codi Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 01. 28. Updated by : 윤주영, SK 플래닛.
 */
public interface AppCodiService {

	/**
	 * <pre>
	 * App Codi 조회.
	 * </pre>
	 * 
	 * @param AppCodiSacReq
	 *            requestVO
	 * @param SacRequestHeader
	 *            requestHeader
	 * @return AppCodiListRes
	 */
	public AppCodiListSacRes searchAppCodiList(AppCodiSacReq requestVO, SacRequestHeader requestHeader);

	/**
	 * <pre>
	 * App Codi V2 조회.
	 * </pre>
	 * 
	 * @param requestVO
	 *            requestVO
	 * @param requestHeader
	 *            requestHeader
	 * @return AppCodiListSacRes
	 */
	public AppCodiListSacRes searchAppCodiListV2(AppCodiV2SacReq requestVO, SacRequestHeader requestHeader);

}
