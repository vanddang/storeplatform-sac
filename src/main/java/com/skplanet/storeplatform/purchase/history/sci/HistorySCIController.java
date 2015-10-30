/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.history.sci;

import org.springframework.beans.factory.annotation.Autowired;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.purchase.client.history.sci.HistorySCI;
import com.skplanet.storeplatform.purchase.client.history.vo.HistoryCountScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.HistoryCountScRes;
import com.skplanet.storeplatform.purchase.client.history.vo.HistoryListScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.HistoryListScRes;
import com.skplanet.storeplatform.purchase.history.service.HistoryListScService;

/**
 * 구매내역 Controller
 * 
 * Updated on : 2013. 12. 10. Updated by : wisestone_mikepark
 */
@LocalSCI
public class HistorySCIController implements HistorySCI {

	@Autowired
	private HistoryListScService service;

	/**
	 * 구매내역 조회 기능을 제공한다.
	 * 
	 * @param request
	 *            구매내역요청
	 * @return HistoryListScRes
	 */
	@Override
	public HistoryListScRes searchHistoryList(HistoryListScReq request) {
		return this.service.searchHistoryList(request);

	}

	/**
	 * 구매내역 조회 기능을 제공한다.
	 * 
	 * @param request
	 *            구매내역요청
	 * @return HistoryListScRes
	 */
	@Override
	public HistoryListScRes searchHistoryListV2(HistoryListScReq request) {
		return this.service.searchHistoryListV2(request);

	}

	/**
	 * 구매건수 조회 기능을 제공한다.
	 * 
	 * @param request
	 *            구매내역요청
	 * @return HistoryCountScRes
	 */
	@Override
	public HistoryCountScRes searchHistoryCount(HistoryCountScReq request) {
		return this.service.searchHistoryCount(request);
	}
}
