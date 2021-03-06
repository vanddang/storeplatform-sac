/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.history.service;

import com.skplanet.storeplatform.purchase.client.history.vo.AutoUpdateAlarmScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.AutoUpdateAlarmScRes;

/**
 * 자동업데이트 거부/거부취소 service interface.
 * 
 * Updated on : 2014. 2. 25. Updated by : 조용진, NTELS.
 */
public interface AutoUpdateAlarmScService {

	/**
	 * 자동업데이트 거부/거부취소 .
	 * 
	 * @param autoUpdateAlarmScReq
	 *            요청.
	 * @return AutoUpdateAlarmScRes
	 */
	public AutoUpdateAlarmScRes updateAlarm(AutoUpdateAlarmScReq autoUpdateAlarmScReq);
}
