package com.skplanet.storeplatform.sac.display.openapi.service;

import com.skplanet.storeplatform.sac.client.display.vo.openapi.BestDownloadMMSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.BestDownloadMMSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * OpenApi Download Best Service
 * 
 * Updated on : 2014. 3. 17. Updated by : 이석희, 아이에스 플러스
 */

public interface BestDownloadMMService {
	/**
	 * 
	 * <pre>
	 * 멀티미디어 상품 목록 조회.
	 * </pre>
	 * 
	 * @param requestheader
	 *            requestheader
	 * @param bestDownloadAppSacReq
	 *            bestDownloadAppSacReq
	 * @return BestDownloadMMSacRes
	 */
	BestDownloadMMSacRes searchBestDownloadMMList(SacRequestHeader requestheader,
			BestDownloadMMSacReq bestDownloadAppSacReq);
}
