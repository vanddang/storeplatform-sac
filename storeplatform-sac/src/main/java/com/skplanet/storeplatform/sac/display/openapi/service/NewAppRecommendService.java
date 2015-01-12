package com.skplanet.storeplatform.sac.display.openapi.service;

import com.skplanet.storeplatform.sac.client.display.vo.openapi.NewAppRecommendSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.NewAppRecommendSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * OpenApi 신규 앱 추천 상품 리스트
 * 
 * Updated on : 2014. 2. 17. Updated by : 이석희, 인크로스
 */

public interface NewAppRecommendService {
	/**
	 * 
	 * <pre>
	 * 신규 앱 추천 상품 리스트 조회(OpenApi).
	 * </pre>
	 * 
	 * @param requestheader
	 *            requestheader
	 * @param newAppRecommendSacReq
	 *            newAppRecommendSacReq
	 * @return NewAppRecommendSacRes
	 */
	NewAppRecommendSacRes searchNewAppRecommendList(SacRequestHeader requestheader,
			NewAppRecommendSacReq newAppRecommendSacReq);
}
