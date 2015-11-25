/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.shopping.sci;

import com.skplanet.storeplatform.framework.core.proxy.SCI;
import com.skplanet.storeplatform.purchase.client.shopping.vo.ShoppingScReq;

/**
 * 쇼핑 Interface
 * 
 * Updated on : 2013. 12. 13. Updated by : ntels_yjw, nTels.
 */
@SCI
public interface ShoppingScSCI {

	public int updatePrchsDtl(ShoppingScReq request);

}
