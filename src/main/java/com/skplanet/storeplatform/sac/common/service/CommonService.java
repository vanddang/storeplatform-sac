/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.common.service;

import java.util.Map;

import com.skplanet.storeplatform.sac.common.vo.Category;
import com.skplanet.storeplatform.sac.common.vo.Device;

public interface CommonService {

	public Device getDevice(String deviceModelCd);

	public Map<String, Category> getCategory();
}
