/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.product.service;

import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Device;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.ProvisionDeviceProfile;

/**
 * DeviceProfileService Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2013-12-18 Updated by : 오승민, Incross
 */
@Component
public class DeviceProfileServiceImpl implements DeviceProfileService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.product.service.DeviceProfileService#searchDeviceProfile(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public ProvisionDeviceProfile searchDeviceProfile(String manufacturer, String model) {
		// TODO Auto-generated method stub
		ProvisionDeviceProfile provisionDeviceProfile = new ProvisionDeviceProfile();
		CommonResponse commonRes = new CommonResponse();
		Device device = new Device();
		device.setManufacturer("samsung");
		device.setModel("SHV-E210S");
		device.setIdentifier("samsung/SHV-E210S");
		device.setType("omd");
		provisionDeviceProfile.setDevice(device);
		provisionDeviceProfile.setCommonRes(commonRes);
		return provisionDeviceProfile;
	}
}
