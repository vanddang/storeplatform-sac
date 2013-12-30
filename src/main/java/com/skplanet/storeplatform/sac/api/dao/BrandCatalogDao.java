package com.skplanet.storeplatform.sac.api.dao;

import com.skplanet.storeplatform.sac.api.except.CouponException;
import com.skplanet.storeplatform.sac.api.vo.DpBrandInfo;

/**
 * 웹툰 Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2013. 12. 23. Updated by : 김형식, SK 플래닛.
 */
public interface BrandCatalogDao {

	/**
	 * <pre>
	 * 일반 카테고리 웹툰 리스트 조회.
	 * </pre>
	 * 
	 * @param WebtoonRequest
	 * @return WebtoonResponse 리스트
	 */
	public void insertBrandInfo(DpBrandInfo dpBrandInfo) throws CouponException;

}
