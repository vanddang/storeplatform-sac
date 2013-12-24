package com.skplanet.storeplatform.sac.product.service;

import com.skplanet.storeplatform.sac.client.product.vo.common.ProductCommonResponse;

/**
 * ProductCommon Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2013. 12. 24. Updated by : 이태희, SK 플래닛.
 */
public interface ProductCommonService {
	/**
	 * <pre>
	 * 메뉴 정보 조회.
	 * </pre>
	 * 
	 * @param Object
	 *            업무별 VO
	 * @return 메뉴 정보
	 */
	ProductCommonResponse searchMenuInfo(Object ob);
}
