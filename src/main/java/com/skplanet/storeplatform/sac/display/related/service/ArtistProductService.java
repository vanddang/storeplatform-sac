package com.skplanet.storeplatform.sac.display.related.service;

import com.skplanet.storeplatform.sac.client.display.vo.related.ArtistProductSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.related.ArtistProductSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * ArtistProductService 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 02. 24. Updated by : 유시혁.
 */
public interface ArtistProductService {

	/**
	 * 
	 * <pre>
	 * 특정 아티스트별 상품(곡).
	 * </pre>
	 * 
	 * @param requestVO
	 *            ArtistProductSacReq
	 * @param requestHeader
	 *            SacRequestHeader
	 * @return ArtistProductSacRes
	 */
	public ArtistProductSacRes searchArtistProductList(ArtistProductSacReq requestVO, SacRequestHeader requestHeader);

}
