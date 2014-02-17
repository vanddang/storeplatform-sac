package com.skplanet.storeplatform.sac.display.product.inf;

import java.util.List;
import java.util.Map;

import com.skplanet.icms.refactoring.deploy.NotificationRefactoringSac;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;

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
	 * 
	 * @param notification
	 */
	void build(NotificationRefactoringSac notification,List<Map<String, Object>> tempList) throws StorePlatformException;

}
