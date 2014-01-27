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

import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryCountReq;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryCountRes;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryListReq;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryListRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 구매내역 Interface
 * 
 * Updated on : 2014-01-10 Updated by : 양주원, 엔텔스.
 */
public interface HistoryListService {

	/**
	 * 구매내역 조회 기능을 제공한다.
	 * 
	 * @param request
	 *            구매내역요청
	 * @param requestHeader
	 *            공통헤더정보
	 * @return HistoryListRes
	 */
	public HistoryListRes list(HistoryListReq request, SacRequestHeader requestHeader);

	/**
	 * 구매내역건수 조회 기능을 제공한다.
	 * 
	 * @param request
	 *            구매내역요청
	 * @return HistoryCountRes
	 */
	public HistoryCountRes count(HistoryCountReq request);

}
