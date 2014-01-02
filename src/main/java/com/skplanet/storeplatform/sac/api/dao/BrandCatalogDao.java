/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.api.dao;

import com.skplanet.storeplatform.sac.api.except.CouponException;
import com.skplanet.storeplatform.sac.api.vo.DpBrandInfo;

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
