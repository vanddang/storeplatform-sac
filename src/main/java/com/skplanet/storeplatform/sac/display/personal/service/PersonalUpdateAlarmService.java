/**
 * 
 */
package com.skplanet.storeplatform.sac.display.personal.service;

import com.skplanet.storeplatform.sac.client.display.vo.personal.PersonalUpdateAlarmReq;
import com.skplanet.storeplatform.sac.client.display.vo.personal.PersonalUpdateAlarmRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * Application Update 알림 설정 Service.
 * 
 * Updated on : 2014. 2. 10. Updated by : 오승민, 인크로스.
 */
public interface PersonalUpdateAlarmService {
	/**
	 * <pre>
	 * Application Update 알림 설정.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return PersonalUpdateAlarmRes
	 */
	public PersonalUpdateAlarmRes updateAlarm(PersonalUpdateAlarmReq req, SacRequestHeader header);

}
