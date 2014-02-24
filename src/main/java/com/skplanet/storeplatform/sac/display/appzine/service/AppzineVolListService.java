package com.skplanet.storeplatform.sac.display.appzine.service;

import com.skplanet.storeplatform.sac.client.display.vo.appzine.AppzineVolListSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.appzine.AppzineVolListSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * AppzineVolListService 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 02. 10. Updated by : 유시혁.
 */
public interface AppzineVolListService {

	/**
	 * <pre>
	 * Appzine 회차별 목록 조회.
	 * </pre>
	 * 
	 * @param requestVO
	 *            AppzineVolListSacRes
	 * @param requestHeader
	 *            SacRequestHeader
	 * @return AppzineVolListSacRes
	 */
	public AppzineVolListSacRes searchAppzineVolList(AppzineVolListSacReq requestVO, SacRequestHeader requestHeader);

}
