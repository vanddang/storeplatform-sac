package com.skplanet.storeplatform.sac.display.product.service;

import com.skplanet.icms.devicemapping.DeviceMappingQueueVO;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;


/**
 * 
 * CmsDeviceService
 * 
 * CMS DeviceRemapping 서비스.
 * 
 * Updated on : 2014. 2. 13. Updated by : 차명호, ANB
 */

public interface CmsDeviceService {

	public void remappingDeviceProcess(DeviceMappingQueueVO message) throws StorePlatformException;
	
}
