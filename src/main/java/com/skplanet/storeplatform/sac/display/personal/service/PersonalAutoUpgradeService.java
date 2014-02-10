/**
 * 
 */
package com.skplanet.storeplatform.sac.display.personal.service;

import com.skplanet.storeplatform.sac.client.display.vo.personal.PersonalAutoUpgradeReq;
import com.skplanet.storeplatform.sac.client.display.vo.personal.PersonalAutoUpgradeRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 자동 Update 목록 조회 Service.
 * 
 * Updated on : 2014. 2. 10. Updated by : 오승민, 인크로스.
 */
public interface PersonalAutoUpgradeService {
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
	public PersonalAutoUpgradeRes searchAutoUpgradeList(PersonalAutoUpgradeReq req, SacRequestHeader header);

}
