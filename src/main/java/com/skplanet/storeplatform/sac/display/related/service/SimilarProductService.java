package com.skplanet.storeplatform.sac.display.related.service;

import com.skplanet.storeplatform.sac.client.display.vo.related.SimilarProductSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.related.SimilarProductSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * Similar Product List Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 10. 24. Updated by : SP Tek 백승현
 */
public interface SimilarProductService {

	/**
	 * 
	 * <pre>
	 * 유사 상품 리스트 조회.
	 * </pre>
	 * 
	 * @param requestVO
	 *            SimilarProductSacReq
	 * @param requestHeader
	 *            SacRequestHeader
	 * @return SimilarProductSacRes
	 */
	public SimilarProductSacRes searchSimilarProductList(SimilarProductSacReq requestVO, SacRequestHeader requestHeader);

	/**
	 * 
	 * <pre>
	 * 유사 상품 리스트 조회 V2.
	 * </pre>
	 * 
	 * @param requestVO
	 *            SimilarProductSacReq
	 * @param requestHeader
	 *            SacRequestHeader
	 * @return SimilarProductSacRes
	 */
	public SimilarProductSacRes searchSimilarProductListV2(SimilarProductSacReq requestVO,
			SacRequestHeader requestHeader);
}
