package com.skplanet.storeplatform.sac.display.feature.best.service;

import com.skplanet.storeplatform.sac.client.display.vo.best.BestDownloadSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.best.BestDownloadSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2013. 12. 18. Updated by : 이석희, SK 플래닛.
 */

public interface BestDownloadService {
	/**
	 * 
	 * <pre>
	 * BEST 다운로드 리스트 조회.
	 * </pre>
	 * 
	 * @param requestheader
	 *            requestheader
	 * @param bestDownloadReq
	 *            bestDownloadReq
	 * @return BestDownloadRes
	 */
	BestDownloadSacRes searchBestDownloadList(SacRequestHeader requestheader, BestDownloadSacReq bestDownloadReq);
}
