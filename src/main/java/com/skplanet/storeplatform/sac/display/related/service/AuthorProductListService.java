package com.skplanet.storeplatform.sac.display.related.service;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.skplanet.storeplatform.sac.client.display.vo.related.AuthorProductListRes;
import com.skplanet.storeplatform.sac.client.display.vo.related.RelatedProductReq;

/**
 * 특정 작가별 Product List Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2013. 12. 24. Updated by : 윤주영, SK 플래닛.
 */
public interface AuthorProductListService {

	/**
	 * <pre>
	 * 특정 작가별 상품 리스트 조회.
	 * </pre>
	 * 
	 * @param RelatedProductReq
	 *            requestVO
	 * @return AuthorProductListResponse
	 */
	public AuthorProductListRes searchAuthorProductList(RelatedProductReq requestVO)
			throws JsonGenerationException, JsonMappingException, IOException, Exception;

}
