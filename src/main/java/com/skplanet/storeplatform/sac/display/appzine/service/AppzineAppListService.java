package com.skplanet.storeplatform.sac.display.appzine.service;

import com.skplanet.storeplatform.sac.client.display.vo.appzine.AppzineAppListSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.appzine.AppzineAppListSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * AppzineAppListService 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 02. 10. Updated by : 유시혁.
 */
public interface AppzineAppListService {

	/**
	 * <pre>
	 * Appzine 앱 목록 조회.
	 * </pre>
	 * 
	 * @param requestVO
	 *            AppzineAppListSacRes
	 * @param requestHeader
	 *            SacRequestHeader
	 * @return AppzineAppListSacRes
	 */
	public AppzineAppListSacRes searchAppzineAppList(AppzineAppListSacReq requestVO, SacRequestHeader requestHeader);

}
