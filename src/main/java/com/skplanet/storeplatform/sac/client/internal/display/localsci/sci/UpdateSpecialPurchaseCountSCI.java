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
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.SpecialPurchaseCountSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.SpecialPurchaseCountSacRes;

/**
 * 특가 상품 구매수 업데이트
 * 
 * Updated on : 2015. 03. 26. Updated by : 김형식
 */
@SCI
public interface UpdateSpecialPurchaseCountSCI {

	/**
	 * 입력받은 상품의 구매수를 업데이트 한다
	 * 
	 * @param req
	 *            req
	 * @return SpecialPurchaseCountSacRes  상품 정보 .            
	 */
	SpecialPurchaseCountSacRes updateSpecialPurchaseCount(SpecialPurchaseCountSacReq req);

}
