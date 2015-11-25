package com.skplanet.storeplatform.sac.display.localsci.sci.service;

import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.SpecialPurchaseCountSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.SpecialPurchaseCountSacRes;


/**
 * 특가 상품 구매수 업데이트
 * 
 * Updated on : 2015. 03. 26. Updated by : 김형식
 */

public interface UpdateSpecialPurchaseCountService {
	/**
	 * 입력받은 상품의 구매수를 업데이트 한다
	 * 
	 * @param req
	 *            req
	 * @return SpecialPurchaseCountSacRes  상품 정보 .            
	 */
	SpecialPurchaseCountSacRes updateSpecialPurchaseCount(SpecialPurchaseCountSacReq req);
}
