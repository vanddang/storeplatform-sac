/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.internal.purchase.history.sci;

import com.skplanet.storeplatform.framework.core.proxy.SCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryCountSacInReq;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryCountSacInRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryListSacInReq;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryListSacInRes;

/**
 * Class 구매내역 기능을 제공하는 Interface
 * 
 * Updated on : 2013. 12. 13. Updated by : ntels_yjw
 */
@SCI
public interface HistoryInternalSCI {

	/**
	 * 구매내역 조회 기능을 제공한다(보유상품).
	 * 
	 * @param request
	 *            구매내역요청
	 * @return HistoryListScRes
	 */
	public HistoryListSacInRes searchHistoryList(HistoryListSacInReq request);

	/**
	 * 구매내역 건수 조회(보유상품).
	 * 
	 * @param request
	 *            구매내역요청
	 * @return HistoryCountScRes
	 */
	public HistoryCountSacInRes searchHistoryCount(HistoryCountSacInReq request);

}
