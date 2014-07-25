/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.cancel.service;

import com.skplanet.storeplatform.sac.client.purchase.cancel.vo.ImmediatelyUseStopSacReq;
import com.skplanet.storeplatform.sac.client.purchase.cancel.vo.ImmediatelyUseStopSacRes;

/**
 * 즉시이용정지 Interface
 * 
 * Updated on : 2014-01-10 Updated by : 양주원, 엔텔스.
 */
public interface ImmediatelyUseStopService {

	/**
	 * 즉시이용정지 기능을 제공한다.
	 * 
	 * @param request
	 *            즉시이용정지요청
	 * @return ImmediatelyUseStopSacRes
	 */
	public ImmediatelyUseStopSacRes updateUseStop(ImmediatelyUseStopSacReq request);

}
