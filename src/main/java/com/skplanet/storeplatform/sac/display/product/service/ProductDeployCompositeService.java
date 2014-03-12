package com.skplanet.storeplatform.sac.display.product.service;

import com.skplanet.icms.refactoring.deploy.NotificationRefactoringSac;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;

/**
 * ProductDeployCompositeService
 * CMS 전시 배포 서비스.
 * Updated on : 2014. 2. 13. Updated by : 차명호, ANB
 */

public interface ProductDeployCompositeService {
	
	public void executeProcess(NotificationRefactoringSac message);
	
}
