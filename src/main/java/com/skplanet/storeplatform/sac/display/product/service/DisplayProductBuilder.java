package com.skplanet.storeplatform.sac.display.product.service;

import com.skplanet.icms.refactoring.deploy.NotificationRefactoringSac;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * DisplayProductBuilder
 * 
 * CMS 전시 배포 정보 등록 서비스.
 * 
 * Updated on : 2014. 2. 13. Updated by : 차명호, ANB
 */
public interface DisplayProductBuilder {

	/**
     * @param notification
     * @param tempList
     * @param prodExistTenant
     * @param oldRegId 이전 판매자ID
     */
	void insertProdInfo(NotificationRefactoringSac notification, List<Map<String, Object>> tempList, Set<String> prodExistTenant, String oldRegId);

}
