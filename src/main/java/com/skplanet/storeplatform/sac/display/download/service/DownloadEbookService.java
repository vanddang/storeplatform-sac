package com.skplanet.storeplatform.sac.display.download.service;

import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadEbookSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadEbookSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * DownloadEbook Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 1. 28. Updated by : 이태희
 */
public interface DownloadEbookService {
	/**
	 * <pre>
	 * ebook 상품 정보 조회(for download).
	 * </pre>
	 * 
	 * @param header
	 *            header
	 * @param ebookReq
	 *            ebookReq
	 * @return DownloadEbookSacRes
	 */
	DownloadEbookSacRes getDownloadEbookInfo(SacRequestHeader header, DownloadEbookSacReq ebookReq);
}
