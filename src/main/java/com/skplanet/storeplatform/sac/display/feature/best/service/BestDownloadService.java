package com.skplanet.storeplatform.sac.display.feature.best.service;

import com.skplanet.storeplatform.sac.client.display.vo.best.BestDownloadSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.best.BestDownloadSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 2. 11. Updated by : 유시혁.
 */

public interface BestDownloadService {
	/**
	 * 
	 * <pre>
	 * BEST 다운로드 리스트 조회.
	 * </pre>
	 * 
	 * @param requestheader
	 *            공통헤더
	 * @param bestDownloadReq
	 *            파라미터
	 * @return BEST 다운로드 리스트
	 */
	BestDownloadSacRes searchBestDownloadList(SacRequestHeader requestheader, BestDownloadSacReq bestDownloadReq);
}
