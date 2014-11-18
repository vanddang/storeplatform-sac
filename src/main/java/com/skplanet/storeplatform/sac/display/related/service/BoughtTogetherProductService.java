package com.skplanet.storeplatform.sac.display.related.service;

import com.skplanet.storeplatform.sac.client.display.vo.related.BoughtTogetherProductSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.related.BoughtTogetherProductSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * BoughtTogether Product List Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 10. 24. Updated by : SP Tek. 백승현
 */
public interface BoughtTogetherProductService {

	/**
	 * 
	 * <pre>
	 * 함께 구매한 상품 리스트 조회.
	 * </pre>
	 * 
	 * @param requestVO
	 *            BoughtTogetherProductSacReq
	 * @param requestHeader
	 *            SacRequestHeader
	 * @return BoughtTogetherProductSacRes
	 */
	public BoughtTogetherProductSacRes searchBoughtTogetherProductList(BoughtTogetherProductSacReq requestVO,
			SacRequestHeader requestHeader);

	/**
	 * 
	 * <pre>
	 * 함께 구매한 상품 리스트 조회.
	 * </pre>
	 * 
	 * @param requestVO
	 *            BoughtTogetherProductSacReq
	 * @param requestHeader
	 *            SacRequestHeader
	 * @return BoughtTogetherProductSacRes
	 */
	public BoughtTogetherProductSacRes searchBoughtTogetherProductListV3(BoughtTogetherProductSacReq requestVO,
			SacRequestHeader requestHeader);

}
