package com.skplanet.storeplatform.sac.display.localsci.sci.service;

import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.SpecialPriceSoldOutReq;

/**
 * 쇼핑 특가 상품에 대한 품절 여부(Sold Out) 등록
 * 
 * Updated on : 2014. 12. 04. Updated by : 김형식
 */

public interface UpdateSpecialPriceSoldOutService {
	/**
	 * 
	 * <pre>
	 * 쇼핑 특가 상품에 대한 품절 여부(Sold Out) 등록 한다.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @return
	 */
	public void updateSpecialPriceSoldOut(SpecialPriceSoldOutReq req);
}
