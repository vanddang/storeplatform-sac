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

import com.skplanet.storeplatform.sac.client.product.vo.provision.DeviceProfileRequest;
import com.skplanet.storeplatform.sac.client.product.vo.provision.DeviceProfileResponse;

/**
 * 특정 단말 정보 조회(CoreStoreBusiness)
 * 
 * Updated on : 2013-12-18 Updated by : 오승민, Incross.
 */
public interface DeviceProfileService {

	/**
	 * <pre>
	 * 특정 상품 조회 API
	 * </pre>
	 * 
	 * @param manufacturer
	 *            제조사 명
	 * @param model
	 *            모델명
	 * @return
	 */
	public DeviceProfileResponse searchDeviceProfile(DeviceProfileRequest requestVO);
}
