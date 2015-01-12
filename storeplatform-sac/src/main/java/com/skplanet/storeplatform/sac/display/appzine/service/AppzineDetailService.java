package com.skplanet.storeplatform.sac.display.appzine.service;

import com.skplanet.storeplatform.sac.client.display.vo.appzine.AppzineDetailSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.appzine.AppzineDetailSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * AppzineDetailService 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 02. 10. Updated by : 유시혁.
 */
public interface AppzineDetailService {

	/**
	 * <pre>
	 * Appzine 회차별 목록 조회.
	 * </pre>
	 * 
	 * @param requestVO
	 *            AppzineDetailSacRes
	 * @param requestHeader
	 *            SacRequestHeader
	 * @return AppzineDetailSacRes
	 */
	public AppzineDetailSacRes searchAppzineDetail(AppzineDetailSacReq requestVO, SacRequestHeader requestHeader);

}
