package com.skplanet.storeplatform.sac.display.localsci.sci.service;

import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.ProductInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.ProductInfoSacRes;

/**
 * 
 * ProductInfoService Service
 * 
 * 구매 내역 조회 시 필요한 상품 메타 정보 조회 서비스.
 * 
 * Updated on : 2014. 2. 24. Updated by : 오승민, 인크로스
 */
public interface ProductInfoService {

	/**
	 * <pre>
	 * 구매 내역 조회 시 필요한 상품 메타 정보 조회.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @return List<Map<String, Object>>
	 */
	ProductInfoSacRes getProductList(ProductInfoSacReq req);
}
