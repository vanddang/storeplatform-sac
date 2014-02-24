package com.skplanet.storeplatform.sac.api.service;

import com.skplanet.storeplatform.sac.api.vo.DpBrandInfo;
import com.skplanet.storeplatform.sac.api.vo.DpCatalogInfo;

/**
 * <pre>
 * 브랜드 카탈로그 서비스 인터페이스.
 * </pre>
 * 
 * Created on : 2014-01-03 Created by : 김형식, SK 플래닛. Last Updated on : 2014-01-03 Last Updated by : 김형식, SK 플래닛
 */
public interface ShoppingCouponService {
	/**
	 * <pre>
	 * 브랜드 정보 입력.
	 * </pre>
	 * 
	 * @param brandInfo
	 *            brandInfo
	 * @return boolean
	 */
	public boolean insertBrandInfo(DpBrandInfo brandInfo);

	/**
	 * <pre>
	 * 카탈로그 정보 입력.
	 * </pre>
	 * 
	 * @param dpCatalogInfo
	 *            dpCatalogInfo
	 * @return boolean
	 */
	public boolean insertCatalogInfo(DpCatalogInfo dpCatalogInfo);
}
