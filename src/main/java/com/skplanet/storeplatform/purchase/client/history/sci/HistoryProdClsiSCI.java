/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.history.sci;

import com.skplanet.storeplatform.framework.core.proxy.SCI;
import com.skplanet.storeplatform.purchase.client.history.vo.HistoryProdClsiScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.HistoryProdClsiScRes;

/**
 * Class 상품별 구매내역 기능을 제공하는 Interface
 * 
 * Updated on : 2013. 12. 13. Updated by : ntels_yjw
 */
@SCI
public interface HistoryProdClsiSCI {

	/**
	 * 상품별 구매내역 조회 기능을 제공한다.
	 * 
	 * @param request
	 *            상품별구매내역요청
	 * @return AdminHistoryScRes
	 */
	public HistoryProdClsiScRes searchHistoryProdClsiList(HistoryProdClsiScReq request);

}
