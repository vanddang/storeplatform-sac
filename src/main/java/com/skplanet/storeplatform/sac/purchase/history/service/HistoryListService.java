/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.history.service;

import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryCountRes;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryListReq;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryListRes;

/**
 * 구매내역 Interface
 * 
 * Updated on : 2014-01-10 Updated by : 양주원, 엔텔스.
 */
public interface HistoryListService {

	/**
	 * 구매내역조회
	 * 
	 * @param request
	 * @return
	 */
	public HistoryListRes list(HistoryListReq request);

	/**
	 * 구매건수조회
	 * 
	 * @param request
	 * @return
	 */
	public HistoryCountRes count(HistoryListReq request);
}
