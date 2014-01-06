package com.skplanet.storeplatform.sac.api.service;

import com.skplanet.storeplatform.sac.api.except.CouponException;
import com.skplanet.storeplatform.sac.api.vo.DpBrandInfo;
import com.skplanet.storeplatform.sac.api.vo.DpCatalogInfo;

public interface BrandCatalogService {
	/**
	 * <pre>
	 * 브랜드 정보 입력
	 * </pre>
	 * 
	 * @throws CouponException
	 * 
	 */
	public void insertBrandInfo(DpBrandInfo brandInfo) throws CouponException;

	/**
	 * <pre>
	 * 카탈로그 정보 입력
	 * </pre>
	 * 
	 * @throws CouponException
	 * 
	 */
	public void insertCatalogInfo(DpCatalogInfo catalogInfo) throws CouponException;

}
