package com.skplanet.storeplatform.sac.api.service;

import com.skplanet.storeplatform.sac.api.vo.DpBrandInfo;
import com.skplanet.storeplatform.sac.api.vo.DpCatalogInfo;

public interface ShoppingCouponService {
	/**
	 * <pre>
	 * 브랜드 정보 입력
	 * </pre>
	 * 
	 * @
	 * 
	 */
	public boolean insertBrandInfo(DpBrandInfo brandInfo);

	/**
	 * <pre>
	 * 카탈로그 정보 입력
	 * </pre>
	 * 
	 * @
	 * 
	 */
	public boolean insertCatalogInfo(DpCatalogInfo dpCatalogInfo);
}
