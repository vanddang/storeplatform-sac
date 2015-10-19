package com.skplanet.storeplatform.sac.display.download.service;

import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadVodSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadVodSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 1. 22. Updated by : 이석희, 인크로스
 */

public interface DownloadVodService {
	/**
	 * 
	 * <pre>
	 * Download Vod 정보 조회(for download).
	 * </pre>
	 * 
	 * @param requestheader
	 *            requestheader
	 * @param downloadVodReq
	 *            downloadVodReq
     * @param supportFhdVideo
     *            FHD 화질 지원여부
	 * @return DownloadVodRes
	 */
	DownloadVodSacRes searchDownloadVod(SacRequestHeader requestheader, DownloadVodSacReq downloadVodReq, boolean supportFhdVideo);
	
	/**
	 * 
	 * <pre>
	 * Download Vod 정보 조회 V3(for download).
	 * </pre>
	 * 
	 * @param requestheader
	 *            requestheader
	 * @param downloadVodReq
	 *            downloadVodReq
     * @param supportFhdVideo
     *            FHD 화질 지원여부
	 * @return DownloadVodRes
	 */
//	DownloadVodSacRes searchDownloadVodV3(SacRequestHeader requestheader, DownloadVodSacReq downloadVodReq, boolean supportFhdVideo);
}
