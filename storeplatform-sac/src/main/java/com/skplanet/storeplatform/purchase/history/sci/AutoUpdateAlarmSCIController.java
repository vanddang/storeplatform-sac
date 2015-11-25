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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.purchase.client.history.sci.AutoUpdateAlarmSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.AutoUpdateAlarmScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.AutoUpdateAlarmScRes;
import com.skplanet.storeplatform.purchase.history.service.AutoUpdateAlarmScService;

/**
 * 자동업데이트 거부/거부취소 구현클래스.
 * 
 * Updated on : 2014. 2. 25. Updated by : 조용진, NTELS.
 */
@LocalSCI
public class AutoUpdateAlarmSCIController implements AutoUpdateAlarmSCI {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private AutoUpdateAlarmScService autoUpdateAlarmScService;

	/**
	 * 자동업데이트 거부/거부취소 요청.
	 * 
	 * @param autoUpdateAlarmScReq
	 *            요청정보
	 * @return AutoUpdateAlarmScRes
	 */
	@Override
	public AutoUpdateAlarmScRes updateAlarm(AutoUpdateAlarmScReq autoUpdateAlarmScReq) {
		this.logger.debug("PRCHS,AutoUpdateAlarmSCIController,SC,REQ,{}", autoUpdateAlarmScReq);
		return this.autoUpdateAlarmScService.updateAlarm(autoUpdateAlarmScReq);
	}
}
