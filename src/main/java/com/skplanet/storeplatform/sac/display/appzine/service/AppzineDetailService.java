package com.skplanet.storeplatform.sac.display.appzine.service;

import com.skplanet.storeplatform.sac.client.display.vo.appzine.AppzineDetailSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.appzine.AppzineDetailSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * AppzineDetail Service 인터페이스(CoreStoreBusiness).
 * 
 * Updated on : 2015. 8. 3. Updated by : 이태희.
 */
public interface AppzineDetailService {
	/**
	 * <pre>
	 * Appzine 상세정보 조회.
	 * </pre>
	 * 
	 * @param requestVO
	 *            requestVO
	 * @param requestHeader
	 *            requestHeader
	 * @return AppzineDetailSacRes
	 */
	public AppzineDetailSacRes searchAppzineDetail(AppzineDetailSacReq requestVO, SacRequestHeader requestHeader);
}
