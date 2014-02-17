package com.skplanet.storeplatform.sac.display.product.inf;

import com.skplanet.icms.refactoring.deploy.NotificationRefactoringSac;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;

/**
 * 
 * DisplayProductInitializer
 * 
 * CMS 전시 배포 기존 정보 초기화 서비스.
 * 
 * Updated on : 2014. 2. 13. Updated by : 차명호, ANB
 */
public interface DisplayProductInitializer {

	/**
	 * 
	 * @param pid
	 */
	void clear(NotificationRefactoringSac notification) throws StorePlatformException;
	
}
