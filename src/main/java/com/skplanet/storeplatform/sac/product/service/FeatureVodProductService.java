package com.skplanet.storeplatform.sac.product.service;

import com.skplanet.storeplatform.sac.client.product.vo.feature.FeatureVodProductRequest;
import com.skplanet.storeplatform.sac.client.product.vo.feature.FeatureVodProductResponse;

/**
 * FeatureVodProduct Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2013. 12. 19. Updated by : 이태희, SK 플래닛.
 */
public interface FeatureVodProductService {
	/**
	 * <pre>
	 * Feature 상품(영화, 방송) 리스트 조회.
	 * </pre>
	 * 
	 * @param FeatureVodProductRequestVO
	 *            Request VO
	 * @return Feature 상품(영화, 방송) 리스트 조회
	 */
	FeatureVodProductResponse searchFeatureVodProductList(FeatureVodProductRequest requestVO);
}
