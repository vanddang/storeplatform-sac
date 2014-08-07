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

import com.skplanet.storeplatform.sac.client.purchase.history.vo.MileageSaveSacReq;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.MileageSaveSacRes;

/**
 * T마일리지 Interface
 * 
 * Updated on : 2014-01-10 Updated by : 양주원, 엔텔스.
 */
public interface MileageSaveService {

	/**
	 * T마일리지 조회 기능을 제공한다.
	 * 
	 * @param request
	 *            T마일리지요청
	 * @param requestHeader
	 *            공통헤더정보
	 * @return MileageSaveSacRes
	 */
	public MileageSaveSacRes searchMileageSave(MileageSaveSacReq request);

}
