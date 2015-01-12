package com.skplanet.storeplatform.sac.display.localsci.sci.service;

import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.DcdSupportProductRes;

/**
 * 
 * SearchDcdSupportProductService Service
 * 
 * DCD 지원 상품 조회.
 * 
 * Updated on : 2014. 2. 27. Updated by : 이석희, 아이에스플러스
 */
public interface SearchDcdSupportProductService {

	/**
	 * <pre>
	 * DCD 지원 상품 조회.
	 * </pre>
	 * 
	 * @param null null
	 * @return DcdSupportProductRes
	 */
	DcdSupportProductRes getDcdSupportProductList();
}
