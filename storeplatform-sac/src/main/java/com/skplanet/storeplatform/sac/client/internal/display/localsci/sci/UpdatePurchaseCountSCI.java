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

import java.util.List;

import com.skplanet.storeplatform.framework.core.proxy.SCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.UpdatePurchaseCountSacReq;

/**
 * 상품 구매수 업데이트
 * 
 * Updated on : 2014. 02. 14. Updated by : 이석희
 */
@SCI
public interface UpdatePurchaseCountSCI {

	/**
	 * 입력받은 상품의 구매수를 업데이트 한다
	 * 
	 * @param productId
	 *            productId.
	 */
	void updatePurchaseCount(List<UpdatePurchaseCountSacReq> reqList);

}
