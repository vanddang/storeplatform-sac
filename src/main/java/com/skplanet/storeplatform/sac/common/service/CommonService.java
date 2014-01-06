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

import com.skplanet.storeplatform.sac.client.common.vo.CmCategory;
import com.skplanet.storeplatform.sac.client.common.vo.CmDevice;

public interface CommonService {

	public CmDevice getCmDevice(String deviceModelCd);

	public Map<String, CmCategory> getCmCategory();
}
