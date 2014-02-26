/**
 * 
 */
package com.skplanet.storeplatform.sac.purchase.history.service;

import com.skplanet.storeplatform.purchase.client.history.vo.AutoUpdateAlarmScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.AutoUpdateAlarmScRes;

/**
 * 자동업데이트 거부/거부취소 service interface.
 * 
 * Updated on : 2014. 2. 25. Updated by : 조용진, NTELS.
 */
public interface AutoUpdateAlarmSacService {

	/**
	 * 자동업데이트 거부/거부취소 .
	 * 
	 * @param autoUpdateAlarmScReq
	 *            요청.
	 * @return AutoUpdateAlarmScRes
	 */
	public AutoUpdateAlarmScRes updateAlarm(AutoUpdateAlarmScReq autoUpdateAlarmScReq);
}
