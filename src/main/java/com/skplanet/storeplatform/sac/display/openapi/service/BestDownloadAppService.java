package com.skplanet.storeplatform.sac.display.openapi.service;

import com.skplanet.storeplatform.sac.client.display.vo.openapi.BestDownloadAppSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.BestDownloadAppSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * OpenApi Download Best Service
 * 
 * Updated on : 2014. 2. 10. Updated by : 이석희, 인크로스
 */

public interface BestDownloadAppService {
	/**
	 * 
	 * <pre>
	 * Download Best 상품 조회(OpenApi).
	 * </pre>
	 * 
	 * @param requestheader
	 *            requestheader
	 * @param BestDownloadAppSacReq
	 *            BestDownloadAppSacReq
	 * @return BestDownloadAppSacRes
	 */
	BestDownloadAppSacRes searchBestDownloadAppList(SacRequestHeader requestheader,
			BestDownloadAppSacReq bestDownloadAppSacReq);
}
