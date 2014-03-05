package com.skplanet.storeplatform.sac.display.openapi.service;

import com.skplanet.storeplatform.sac.client.display.vo.openapi.SearchAppNameSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.SearchAppNameSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * OpenApi Download Best Service
 * 
 * Updated on : 2014. 2. 10. Updated by : 이석희, 인크로스
 */

public interface SearchAppNameService {
	/**
	 * 
	 * <pre>
	 * Download Best 상품 조회(OpenApi).
	 * </pre>
	 * 
	 * @param requestheader
	 *            requestheader
	 * @param SearchAppSacSacReq
	 *            SearchAppSacSacReq
	 * @return SearchAppSacSacRes
	 */
	SearchAppNameSacRes searchAppNameList(SacRequestHeader requestheader, SearchAppNameSacReq searchAppNameSacReq);
}
