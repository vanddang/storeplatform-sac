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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.purchase.client.history.vo.AutoUpdateAlarmSc;
import com.skplanet.storeplatform.purchase.client.history.vo.AutoUpdateAlarmScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.AutoUpdateAlarmScRes;
import com.skplanet.storeplatform.purchase.history.vo.AutoUpdateAlarm;

/**
 * 자동업데이트 거부/거부취소 구현클래스.
 * 
 * Updated on : 2014. 2. 25. Updated by : 조용진, NTELS.
 */
@Service
public class AutoUpdateAlarmScServiceImpl implements AutoUpdateAlarmScService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("scPurchase")
	private CommonDAO commonDAO;

	/**
	 * 자동업데이트 거부/거부취소 요청.
	 * 
	 * @param autoUpdateAlarmScReq
	 *            요청정보
	 * @return AutoUpdateAlarmScRes
	 */
	@Override
	public AutoUpdateAlarmScRes updateAlarm(AutoUpdateAlarmScReq autoUpdateAlarmScReq) {

		int count = 0;

		AutoUpdateAlarm autoUpdateAlarm = new AutoUpdateAlarm();
		autoUpdateAlarm.setTenantId(autoUpdateAlarmScReq.getTenantId());
		autoUpdateAlarm.setSystemId(autoUpdateAlarmScReq.getSystemId());
		autoUpdateAlarm.setUserKey(autoUpdateAlarmScReq.getUserKey());

		for (AutoUpdateAlarmSc autoUpdateAlarmSc : autoUpdateAlarmScReq.getProductList()) {
			autoUpdateAlarm.setProdId(autoUpdateAlarmSc.getProdId());
			autoUpdateAlarm.setAlarmYn(autoUpdateAlarmSc.getAlarmYn());
			count += this.commonDAO.update("PurchaseAlarm.updateAlarm", autoUpdateAlarm);
		}

		AutoUpdateAlarmScRes autoUpdateScRes = new AutoUpdateAlarmScRes();
		if (count > 0) {
			autoUpdateScRes.setResultYn("Y");
		} else {
			autoUpdateScRes.setResultYn("N");
		}

		return autoUpdateScRes;
	}

}
