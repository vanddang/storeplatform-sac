package com.skplanet.storeplatform.sac.display.related.service;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.skplanet.storeplatform.sac.client.display.vo.related.BoughtTogetherProductSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.related.BoughtTogetherProductSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * BoughtTogether Product List Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 02. 18. Updated by : 유시혁.
 */
public interface BoughtTogetherProductService {

	/**
	 * <pre>
	 * 함게 구매한 상품 리스트 조회.
	 * </pre>
	 * 
	 * @param BoughtTogetherProductSacReq
	 *            requestVO
	 * @return BoughtTogetherProductSacRes
	 */
	public BoughtTogetherProductSacRes searchBoughtTogetherProductList(BoughtTogetherProductSacReq requestVO,
			SacRequestHeader requestHeader) throws JsonGenerationException, JsonMappingException, IOException,
			Exception;

}
