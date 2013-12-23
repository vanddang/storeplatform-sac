package com.skplanet.storeplatform.sac.product.service;

import com.skplanet.storeplatform.sac.client.product.vo.best.BestContentsResponseVO;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2013. 12. 18. Updated by : 이석희, SK 플래닛.
 */

public interface BestContentsService {
	/**
	 * <pre>
	 * BEST 컨텐츠 리스트 조회
	 * </pre>
	 * 
	 * @param BestContentsRequestVO
	 *            BEST 컨텐츠 상품 리스트 요청 Value Object
	 * @return BEST 컨텐츠 상품 리스트
	 */
	BestContentsResponseVO searchBestContentsList(String listId, String imageSizeCd, String fiteredBy, String b2bprod,
			String hdv, String drm, String prodGradeCd, String offset, String count);
}
