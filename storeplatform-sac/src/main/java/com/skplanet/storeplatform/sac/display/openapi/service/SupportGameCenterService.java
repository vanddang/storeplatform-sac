/**
 * 
 */
package com.skplanet.storeplatform.sac.display.openapi.service;

import com.skplanet.storeplatform.sac.client.display.vo.openapi.SupportGameCenterSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.SupportGameCenterSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * OpenApi 게임센터 지원 여부 조회 Service
 * 
 * Updated on : 2014. 3. 12. Updated by : 백승현, 인크로스.
 */
public interface SupportGameCenterService {

	/**
	 * 
	 * <pre>
	 * 게임센터 지원 여부 조회(OpenApi).
	 * </pre>
	 * 
	 * @param SupportGameCenterSacReq
	 *            SupportGameCenterSacReq
	 * @param requestheader
	 *            requestheader
	 * @return SupportGameCenterSacRes
	 */
	SupportGameCenterSacRes searchSupportGameCenterByAid(SupportGameCenterSacReq supportGameCenterSacReq,
			SacRequestHeader requestheader);
}
