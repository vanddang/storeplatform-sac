/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.cancel.service;

import java.util.List;

import com.skplanet.storeplatform.purchase.client.cancel.vo.ImmediatelyUseStopScReq;
import com.skplanet.storeplatform.purchase.client.cancel.vo.ImmediatelyUseStopScRes;
import com.skplanet.storeplatform.purchase.client.common.vo.PrchsDtl;

/**
 * 구매내역 Interface
 * 
 * Updated on : 2014-01-10 Updated by : 양주원, 엔텔스.
 */
public interface ImmediatelyUseStopScService {

	/**
	 * 구매이력을 조회한다.
	 * 
	 * @param request
	 *            즉시이용정지 요청
	 * @return PrchsDtl
	 */
	public List<PrchsDtl> searchPrchsDtl(ImmediatelyUseStopScReq request);

	/**
	 * 구매내역 조회 기능을 제공한다.
	 * 
	 * @param request
	 *            구매내역요청
	 * @return HistoryListScRes
	 */
	public ImmediatelyUseStopScRes updateUseStop(ImmediatelyUseStopScReq request);

}
