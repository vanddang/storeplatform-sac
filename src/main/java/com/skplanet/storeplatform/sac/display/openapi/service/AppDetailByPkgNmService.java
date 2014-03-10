/**
 * 
 */
package com.skplanet.storeplatform.sac.display.openapi.service;

import com.skplanet.storeplatform.sac.client.display.vo.openapi.AppDetailByPackageNameSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.AppDetailByPackageNameSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * OpenApi 상품 상세 정보 요청(Package Name) Service
 * 
 * Updated on : 2014. 3. 6. Updated by : 백승현, 인크로스.
 */
public interface AppDetailByPkgNmService {

	/**
	 * 
	 * <pre>
	 * 상품 상세 정보 요청(Package Name)(OpenApi).
	 * </pre>
	 * 
	 * @param appDetailByPackageNameSacReq
	 *            appDetailByPackageNameSacReq
	 * @param requestheader
	 *            requestheader
	 * @return AppDetailByPackageNameSacRes
	 */
	AppDetailByPackageNameSacRes searchProductByPackageName(AppDetailByPackageNameSacReq appDetailByPackageNameSacReq,
			SacRequestHeader requestheader);
}
