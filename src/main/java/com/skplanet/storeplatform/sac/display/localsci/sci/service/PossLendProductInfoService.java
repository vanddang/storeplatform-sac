package com.skplanet.storeplatform.sac.display.localsci.sci.service;

import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PossLendProductInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PossLendProductInfoSacRes;

/**
 * 
 * PossLendProductInfoService Service
 * 
 * 소장/대여 상품 정보 조회 서비스.
 * 
 * Updated on : 2014. 4. 15. Updated by : 홍지호, 엔텔스
 */
public interface PossLendProductInfoService {

	/**
	 * <pre>
	 * 소장/대여 상품 정보 조회.
	 * </pre>
	 * 
	 * @param req
	 *            파라미터
	 * @return PossLendProductInfoSacRes 소장/대여 상품 정보 리스트
	 */
	PossLendProductInfoSacRes searchPossLendProductInfo(PossLendProductInfoSacReq req);
}
