/**
 * 
 */
package com.skplanet.storeplatform.sac.purchase.history.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.purchase.client.history.sci.AutoUpdateAlarmSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.AutoUpdateAlarmScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.AutoUpdateAlarmScRes;

/**
 * 자동업데이트 거부/거부취소 service 구현클래스.
 * 
 * Updated on : 2014. 2. 25. Updated by : 조용진, NTELS.
 */
@Service
public class AutoUpdateAlarmSacServiceImpl implements AutoUpdateAlarmSacService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AutoUpdateAlarmSCI autoUpdateAlarmSCI;

	/**
	 * 자동업데이트 거부/거부취소 .
	 * 
	 * @param AutoUpdateAlarmScReq
	 *            요청.
	 * @return AutoUpdateAlarmScRes
	 */
	@Override
	public AutoUpdateAlarmScRes updateAlarm(AutoUpdateAlarmScReq autoUpdateAlarmScReq) {

		return this.autoUpdateAlarmSCI.updateAlarm(autoUpdateAlarmScReq);
	}

}
