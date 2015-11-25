package com.skplanet.storeplatform.sac.display.appzine.service;

import com.skplanet.storeplatform.sac.client.display.vo.appzine.AppzineAppListSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.appzine.AppzineAppListSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * AppzineAppList Service 인터페이스(CoreStoreBusiness).
 * 
 * Updated on : 2015. 8. 3. Updated by : 이태희.
 */
public interface AppzineAppListService {
	/**
	 * <pre>
	 * Appzine 앱 목록 조회.
	 * </pre>
	 * 
	 * @param requestVO
	 *            requestVO
	 * @param requestHeader
	 *            requestHeader
	 * @return AppzineAppListSacRes
	 */
	public AppzineAppListSacRes searchAppzineAppList(AppzineAppListSacReq requestVO, SacRequestHeader requestHeader);
}
