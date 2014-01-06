package com.skplanet.storeplatform.sac.display.related.service;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.skplanet.storeplatform.sac.client.display.vo.related.RelatedProductListRes;
import com.skplanet.storeplatform.sac.client.display.vo.related.RelatedProductReq;

/**
 * Similar Product List Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2013. 12. 24. Updated by : 윤주영, SK 플래닛.
 */
public interface SimilarProductListService {

	/**
	 * <pre>
	 * 유사 상품 리스트 조회.
	 * </pre>
	 * 
	 * @param RelatedProductReq
	 *            requestVO
	 * @return RelatedProductListResponse
	 */
	public RelatedProductListRes searchSimilarProductList(RelatedProductReq requestVO)
			throws JsonGenerationException, JsonMappingException, IOException, Exception;

}
