package com.skplanet.storeplatform.sac.product.service;

import com.skplanet.storeplatform.sac.client.product.vo.best.BestDownloadResponse;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2013. 12. 18. Updated by : 이석희, SK 플래닛.
 */

public interface BestDownloadService {
	/**
	 * <pre>
	 * BEST 다운로드 리스트 조회
	 * </pre>
	 * 
	 * @param BestContentsRequestVO
	 *            BEST 다운로드 상품 리스트 요청 Value Object
	 * @return BEST 다운로드 상품 리스트
	 */
	BestDownloadResponse searchBestDownloadList(String listId, String imageSizeCd, String menuId, String b2bprod,
			String hdv, String drm, String prodGradeCd, String offset, String count);
}
