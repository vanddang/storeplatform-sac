/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.internal.display.localsci.sci;

import org.springframework.validation.annotation.Validated;

import com.skplanet.storeplatform.framework.core.proxy.SCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.ProductInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.ProductInfoSacRes;

/**
 * 구매 내역 조회 시 필요한 상품 메타 정보 조회
 * 
 * Updated on : 2014. 02. 24. Updated by : 오승민, 인크로스
 */
@SCI
public interface ProductInfoSCI {

	/**
	 * <pre>
	 * 구매 내역 조회 시 필요한 상품 메타 정보 조회.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @return List<Map>
	 */
	ProductInfoSacRes getProductList(@Validated ProductInfoSacReq req);

}
