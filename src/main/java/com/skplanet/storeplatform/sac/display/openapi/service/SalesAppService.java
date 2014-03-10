package com.skplanet.storeplatform.sac.display.openapi.service;

import com.skplanet.storeplatform.sac.client.display.vo.openapi.SalesAppSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.SalesAppSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * OpenApi SalesApp Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 03. 06. Updated by : 이태희
 */

public interface SalesAppService {
	/**
	 * <pre>
	 * PKG Name 기반 상품 정보 조회.
	 * </pre>
	 * 
	 * @param header
	 *            header
	 * @param salesAppReq
	 *            salesAppReq
	 * @return SalesAppSacRes
	 */
	SalesAppSacRes searchSalesAppList(SacRequestHeader header, SalesAppSacReq salesAppReq);
}
