package com.skplanet.storeplatform.sac.display.feature.best.service;

import com.skplanet.storeplatform.sac.client.display.vo.best.BestAppReq;
import com.skplanet.storeplatform.sac.client.display.vo.best.BestAppRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2013. 12. 18. Updated by : 이석희, SK 플래닛.
 */

public interface BestAppService {
	/**
	 * <pre>
	 * BEST 앱 리스트 조회
	 * </pre>
	 * 
	 * @param BestAppRequestVO
	 *            BEST 앱 상품 리스트 요청 Value Object
	 * @return BEST 앱 상품 리스트
	 */
	BestAppRes searchBestAppList(SacRequestHeader requestheader, BestAppReq bestAppReq);
}
