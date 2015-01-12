/**
 * 
 */
package com.skplanet.storeplatform.sac.display.openapi.service;

import com.skplanet.storeplatform.sac.client.display.vo.openapi.AppDetailByProductIdSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.AppDetailByProductIdSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * OpenApi 상품 상세 정보 요청(Product Id) Service
 * 
 * Updated on : 2014. 3. 11. Updated by : 백승현, 인크로스.
 */
public interface AppDetailByProdIdService {

	/**
	 * 
	 * <pre>
	 * 상품 상세 정보 요청(Package Name)(OpenApi).
	 * </pre>
	 * 
	 * @param appDetailByProductIdSacReq
	 *            appDetailByProductIdSacReq
	 * @param requestheader
	 *            requestheader
	 * @return AppDetailByProductIdSacRes
	 */
	AppDetailByProductIdSacRes searchProductByProductId(AppDetailByProductIdSacReq appDetailByProductIdSacReq,
			SacRequestHeader requestheader);
}
