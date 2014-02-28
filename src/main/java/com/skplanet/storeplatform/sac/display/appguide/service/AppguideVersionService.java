package com.skplanet.storeplatform.sac.display.appguide.service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.display.vo.appguide.AppguideSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.appguide.AppguideSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * App guide Version Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 02. 28. Updated by : 윤주영, SK 플래닛.
 */
public interface AppguideVersionService {

	/**
	 * <pre>
	 * 버전 정보 조회.
	 * </pre>
	 * 
	 * @param AppguideSacReq
	 *            requestVO
	 * @param SacRequestHeader
	 *            requestHeader
	 * @return AppguideSacRes
	 */
	public AppguideSacRes searchVersion(AppguideSacReq requestVO, SacRequestHeader requestHeader)
			throws StorePlatformException;
}
