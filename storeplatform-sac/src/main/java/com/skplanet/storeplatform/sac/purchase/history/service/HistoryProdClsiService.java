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

import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryProdClsiSacReq;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryProdClsiSacRes;

/**
 * 상품별 구매내역 Interface
 * 
 * Updated on : 2014-01-10 Updated by : 양주원, 엔텔스.
 */
public interface HistoryProdClsiService {

	/**
	 * 상품별 구매내역 조회 기능을 제공한다.
	 * 
	 * @param request
	 *            상품별 구매내역요청
	 * @return HistoryProdClsiSacRes
	 */
	public HistoryProdClsiSacRes searchHistoryProdClsiList(HistoryProdClsiSacReq request);

}
