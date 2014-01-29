package com.skplanet.storeplatform.sac.display.download.service;

import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadEbookSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadEbookSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * DownloadEbookService Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 1. 28. Updated by : 이태희
 */
public interface DownloadEbookService {
	/**
	 * <pre>
	 * ebook 상품 정보 조회(for download).
	 * </pre>
	 * 
	 * @param requestHeader
	 *            requestHeader
	 * @param downloadEbookReq
	 *            requestHeader
	 * @return DownloadEbookRes
	 */
	DownloadEbookSacRes getDownloadEbookInfo(SacRequestHeader requestHeader, DownloadEbookSacReq downloadEbookReq);
}
