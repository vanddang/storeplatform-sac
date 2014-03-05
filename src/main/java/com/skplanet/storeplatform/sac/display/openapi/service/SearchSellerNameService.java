package com.skplanet.storeplatform.sac.display.openapi.service;

import com.skplanet.storeplatform.sac.client.display.vo.openapi.SearchSellerNameSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.SearchSellerNameSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * OpenApi Download Best Service
 * 
 * Updated on : 2014. 2. 10. Updated by : 이석희, 인크로스
 */

public interface SearchSellerNameService {
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
	SearchSellerNameSacRes searchSellerNameList(SacRequestHeader requestheader,
			SearchSellerNameSacReq searchSellerNameSacReq);
}
