/**
 * 
 */
package com.skplanet.storeplatform.sac.display.stat.service;

import com.skplanet.storeplatform.sac.client.display.vo.stat.StatActionRes;
import com.skplanet.storeplatform.sac.client.display.vo.stat.StatDetailRes;


public interface StatService {
	StatDetailRes getStatDetail(String tenantId, String clsf, String key);
	StatActionRes createStat(String tenantId, String userKey, String key, String clsf, String regCaseCd);
	StatActionRes removeStat(String tenantId, String userKey, String key, String clsf, String regCaseCd);
}
