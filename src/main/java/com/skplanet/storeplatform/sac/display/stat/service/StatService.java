/**
 * 
 */
package com.skplanet.storeplatform.sac.display.stat.service;

import com.skplanet.storeplatform.sac.client.display.vo.stat.StatActionRes;
import com.skplanet.storeplatform.sac.client.display.vo.stat.StatDetailRes;


/**
 * Calss 설명
 * 
 * Updated on : 2014. 10. 15.
 * Updated by : 1002177
 */
public interface StatService {
	StatDetailRes getStatDetail(String tenantId, String key);
	StatActionRes createStat(String tenantId, String userKey, String key, String regCaseCd);
	StatActionRes removeStat(String tenantId, String userKey, String key, String regCaseCd);
}
