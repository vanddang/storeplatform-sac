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

import com.skplanet.storeplatform.framework.core.proxy.SCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.SpecialPriceSoldOutReq;

/**
 * 쇼핑 특가 상품에 대한 품절 여부(Sold Out) 등록
 * 
 * Updated on : 2014. 12. 04. Updated by : 김형식
 */
@SCI
public interface UpdateSpecialPriceSoldOutSCI {

	/**
	 * 쇼핑 특가 상품에 대한 품절 여부(Sold Out) 등록 한다
	 * 
	 * @param req
	 *            req.
	 */
	void updateSpecialPriceSoldOut(SpecialPriceSoldOutReq req);

}
