package com.skplanet.storeplatform.sac.display.localsci.sci.service;

import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.FreePassInfo;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.FreePassInfoSacReq;

/**
 * 
 * 정액제 상품 DRM 정보 조회.
 * 
 * Updated on : 2014. 2. 28. Updated by : 김형식 , 지티소프트
 */
public interface FreePassInfoService {

	/**
	 * <pre>
	 * 정액제 상품 DRM 정보 조회.
	 * </pre>
	 * 
	 * @param req
	 *            파라미터
	 * @return FreepassDrmInfo 정액제 상품 DRM 정보
	 */
	FreePassInfo searchFreePassDrmInfo(FreePassInfoSacReq req);
}
