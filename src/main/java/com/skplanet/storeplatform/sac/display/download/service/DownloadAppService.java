package com.skplanet.storeplatform.sac.display.download.service;

import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadAppReq;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadAppRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 1. 21. Updated by : 이석희, 인크로스
 */

public interface DownloadAppService {
	/**
	 * 
	 * <pre>
	 * Download 앱 정보 조회(for download).
	 * </pre>
	 * 
	 * @param requestheader
	 *            requestheader
	 * @param downloadAppReq
	 *            downloadAppReq
	 * @return DownloadAppRes
	 */
	DownloadAppRes searchDownloadApp(SacRequestHeader requestheader, DownloadAppReq downloadAppReq);
}
