package com.skplanet.storeplatform.sac.product.service;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.skplanet.storeplatform.sac.client.product.vo.related.RelatedProductListResponse;
import com.skplanet.storeplatform.sac.client.product.vo.related.RelatedProductRequest;

/**
 * 특정 작가별 Product List Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2013. 12. 24. Updated by : 윤주영, SK 플래닛.
 */
public interface ArtistProductListService {

	/**
	 * <pre>
	 * 특정 작가별 상품 리스트 조회.
	 * </pre>
	 * 
	 * @param RelatedProductRequest
	 *            requestVO
	 * @return AuthorProductListResponse
	 */
	public RelatedProductListResponse searchArtistProductList(RelatedProductRequest requestVO)
			throws JsonGenerationException, JsonMappingException, IOException, Exception;

}
