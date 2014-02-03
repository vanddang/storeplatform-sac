package com.skplanet.storeplatform.sac.display.download.service;

import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadComicSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadComicSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * DownloadComic Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 2. 03. Updated by : 이태희
 */
public interface DownloadComicService {
	/**
	 * <pre>
	 * Comic 상품 정보 조회(for download).
	 * </pre>
	 * 
	 * @param requestHeader
	 *            requestHeader
	 * @param downloadComicReq
	 *            downloadComicReq
	 * @return DownloadComicSacRes
	 */
	DownloadComicSacRes getDownloadComicInfo(SacRequestHeader requestHeader, DownloadComicSacReq downloadComicReq);
}
