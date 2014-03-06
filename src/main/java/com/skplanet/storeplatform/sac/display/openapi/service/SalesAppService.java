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
	 * 
	 * <pre>
	 * Download Best 상품 조회(OpenApi).
	 * </pre>
	 * 
	 * @param header
	 *            header
	 * @param salesAppSacReq
	 *            salesAppSacReq
	 * @return SalesAppSacRes
	 */
	SalesAppSacRes searchSalesAppList(SacRequestHeader header, SalesAppSacReq salesAppSacReq);
}
