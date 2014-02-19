package com.skplanet.storeplatform.sac.display.related.service;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.skplanet.storeplatform.sac.client.display.vo.related.SellerProductSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.related.SellerProductSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * Seller Product List Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 02. 19. Updated by : 유시혁.
 */
public interface SellerProductService {

	/**
	 * <pre>
	 * 특정 판매자별 상품 조회.
	 * </pre>
	 * 
	 * @param SellerProductSacReq
	 *            requestVO
	 * @return SellerProductSacRes
	 */
	public SellerProductSacRes searchSellerProductList(SellerProductSacReq requestVO, SacRequestHeader requestHeader)
			throws JsonGenerationException, JsonMappingException, IOException, Exception;

}
