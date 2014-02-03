package com.skplanet.storeplatform.sac.display.download.service;

import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadMusicSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadMusicSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 1. 22. Updated by : 이석희, 인크로스
 */

public interface DownloadMusicService {
	/**
	 * 
	 * <pre>
	 * Download Music 정보 조회(for download).
	 * </pre>
	 * 
	 * @param requestheader
	 *            requestheader
	 * @param downloadMusicReq
	 *            downloadMusicReq
	 * @return DownloadMusicRes
	 */
	DownloadMusicSacRes searchDownloadMusic(SacRequestHeader requestheader, DownloadMusicSacReq downloadMusicSacReq);
}
