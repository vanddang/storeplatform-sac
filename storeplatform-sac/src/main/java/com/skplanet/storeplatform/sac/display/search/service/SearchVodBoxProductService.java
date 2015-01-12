package com.skplanet.storeplatform.sac.display.search.service;

import com.skplanet.storeplatform.sac.client.display.vo.search.SearchProductReq;
import com.skplanet.storeplatform.sac.client.display.vo.search.SearchProductRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * Vod 상품 조회 Serivce.
 * 
 * Updated on : 2014. 1. 27. Updated by : 오승민, 인크로스.
 */
public interface SearchVodBoxProductService {
	/**
	 * <pre>
	 * Vod 상품 조회.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return SearchProductRes
	 */
	public SearchProductRes searchVodBoxProduct(SearchProductReq req, SacRequestHeader header);
}
