package com.skplanet.storeplatform.sac.display.feature.best.service;

import com.skplanet.storeplatform.sac.client.display.vo.best.BestAppSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.best.BestAppSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2013. 12. 18. Updated by : 이석희, SK 플래닛.
 */

public interface BestAppService {
	/**
	 * 
	 * <pre>
	 * BEST 앱 리스트 조회.
	 * </pre>
	 * 
	 * @param requestheader
	 *            공통헤더
	 * @param bestAppReq
	 *            파라미터
	 * @return BEST 앱 리스트
	 */
	BestAppSacRes searchBestAppList(SacRequestHeader requestheader, BestAppSacReq bestAppReq);
}
