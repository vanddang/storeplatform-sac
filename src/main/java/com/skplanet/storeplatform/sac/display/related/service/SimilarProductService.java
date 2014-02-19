package com.skplanet.storeplatform.sac.display.related.service;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.skplanet.storeplatform.sac.client.display.vo.related.SimilarProductSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.related.SimilarProductSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * Similar Product List Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 02. 18. Updated by : 유시혁.
 */
public interface SimilarProductService {

	/**
	 * <pre>
	 * 유사 상품 리스트 조회.
	 * </pre>
	 * 
	 * @param SimilarProductSacReq
	 *            requestVO
	 * @return SimilarProductSacRes
	 */
	public SimilarProductSacRes searchSimilarProductList(SimilarProductSacReq requestVO, SacRequestHeader requestHeader)
			throws JsonGenerationException, JsonMappingException, IOException, Exception;

}
