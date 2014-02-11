/**
 * 
 */
package com.skplanet.storeplatform.sac.display.personal.service;

import com.skplanet.storeplatform.sac.client.display.vo.personal.PersonalAutoUpdateReq;
import com.skplanet.storeplatform.sac.client.display.vo.personal.PersonalAutoUpdateRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 자동 Update 목록 조회 Service.
 * 
 * Updated on : 2014. 2. 10. Updated by : 오승민, 인크로스.
 */
public interface PersonalAutoUpdateService {
	/**
	 * <pre>
	 * 자동 Update 목록 조회.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return PersonalAutoUpgradeRes
	 */
	public PersonalAutoUpdateRes updateAutoUpdateList(PersonalAutoUpdateReq req, SacRequestHeader header);

}
