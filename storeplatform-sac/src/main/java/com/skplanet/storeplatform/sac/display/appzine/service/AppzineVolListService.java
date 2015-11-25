package com.skplanet.storeplatform.sac.display.appzine.service;

import com.skplanet.storeplatform.sac.client.display.vo.appzine.AppzineVolListSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.appzine.AppzineVolListSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * AppzineVolList Service 인터페이스(CoreStoreBusiness).
 * 
 * Updated on : 2015. 8. 3. Updated by : 이태희.
 */
public interface AppzineVolListService {
	/**
	 * <pre>
	 * Appzine 회차별 목록 조회.
	 * </pre>
	 * 
	 * @param requestVO
	 *            requestVO
	 * @param requestHeader
	 *            requestHeader
	 * @return AppzineVolListSacRes
	 */
	public AppzineVolListSacRes searchAppzineVolList(AppzineVolListSacReq requestVO, SacRequestHeader requestHeader);
}
