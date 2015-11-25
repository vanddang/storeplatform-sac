/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */

package com.skplanet.storeplatform.sac.display.app.service;

import com.skplanet.storeplatform.sac.client.display.vo.app.AppPermissionReq;
import com.skplanet.storeplatform.sac.client.display.vo.app.AppPermissionRes;

/**
 * Updated on : 2015-11-18. Updated by : 양해엽, SK Planet.
 */
public interface AppPermissionService {

	String getAppPermission(String prodId, String subContentsId);
}
