/**
 * 
 */
package com.skplanet.storeplatform.sac.display.personal.service;

import com.skplanet.storeplatform.sac.client.display.vo.personal.PersonalUpgradeProductReq;
import com.skplanet.storeplatform.sac.client.display.vo.personal.PersonalUpgradeProductRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 업데이트 대상 목록 조회 Service 구현체
 * 
 * Updated on : 2014. 2. 10. Updated by : 오승민, 인크로스.
 */
public interface PersonalUpgradeProductService {
	public PersonalUpgradeProductRes searchUpdateProductList(PersonalUpgradeProductReq req, SacRequestHeader header);
}
