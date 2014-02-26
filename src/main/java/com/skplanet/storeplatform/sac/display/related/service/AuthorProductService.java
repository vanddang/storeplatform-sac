package com.skplanet.storeplatform.sac.display.related.service;

import com.skplanet.storeplatform.sac.client.display.vo.related.AuthorProductSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.related.AuthorProductSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * AuthorProductService 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 02. 24. Updated by : 유시혁.
 */
public interface AuthorProductService {

	/**
	 * 
	 * <pre>
	 * 특정 작가별 리스트 조회.
	 * </pre>
	 * 
	 * @param requestVO
	 *            AuthorProductSacReq
	 * @param requestHeader
	 *            SacRequestHeader
	 * @return AuthorProductSacRes
	 */
	public AuthorProductSacRes searchAuthorProductList(AuthorProductSacReq requestVO, SacRequestHeader requestHeader);

}
