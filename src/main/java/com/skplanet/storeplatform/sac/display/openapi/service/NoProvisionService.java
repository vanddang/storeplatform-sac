/**
 * 
 */
package com.skplanet.storeplatform.sac.display.openapi.service;

import com.skplanet.storeplatform.sac.client.display.vo.openapi.NoProvisionSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.NoProvisionSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * OpenApi 상품 검색 요청(BY 상품명) - No Provisioning Service
 * 
 * Updated on : 2014. 3. 3. Updated by : 백승현, 인크로스.
 */
public interface NoProvisionService {

	/**
	 * 
	 * <pre>
	 * 상품 검색 요청(BY 상품명) - No Provisioning(OpenApi).
	 * </pre>
	 * 
	 * @param requestheader
	 *            requestheader
	 * @param noProvisionSacReq
	 *            noProvisionSacReq
	 * @return NoProvisionSacRes
	 */
	NoProvisionSacRes searchProductByNameNoProvisioningList(NoProvisionSacReq noProvisionSacReq,
			SacRequestHeader requestheader);
}
