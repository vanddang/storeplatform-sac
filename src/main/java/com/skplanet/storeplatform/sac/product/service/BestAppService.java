package com.skplanet.storeplatform.sac.product.service;

import com.skplanet.storeplatform.sac.client.product.vo.best.BestAppRequestVO;
import com.skplanet.storeplatform.sac.client.product.vo.best.BestAppResponseVO;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2013. 12. 18. Updated by : 이석희, SK 플래닛.
 */

public interface BestAppService {
	/**
	 * <pre>
	 * BEST 앱 리스트 조회
	 * </pre>
	 * 
	 * @param BestAppRequestVO
	 *            BEST 앱 상품 리스트 요청 Value Object
	 * @return BEST 앱 상품 리스트
	 */
	BestAppResponseVO searchBestAppList(BestAppRequestVO bestAppRequestVO);
}
