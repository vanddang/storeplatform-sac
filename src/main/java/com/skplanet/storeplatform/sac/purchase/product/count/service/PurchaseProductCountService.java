/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.product.count.service;

import com.skplanet.storeplatform.sac.purchase.product.count.vo.PurchaseProductCountSacParam;
import com.skplanet.storeplatform.sac.purchase.product.count.vo.PurchaseProductCountSacResult;

/**
 * Class 설명
 * 
 * Updated on : 2014. 3. 27. Updated by : nTels_cswoo81, nTels.
 */
public interface PurchaseProductCountService {

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param purchaseProductCountSacParam
	 *            purchaseProductCountSacParam
	 * @return PurchaseProductCountSacResult
	 */
	PurchaseProductCountSacResult executePurchaseProductCount(PurchaseProductCountSacParam purchaseProductCountSacParam);

}
