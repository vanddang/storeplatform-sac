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

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Updated on : 2015-11-18. Updated by : 양해엽, SK Planet.
 */
@Service
public class AppPermissionServiceImpl implements AppPermissionService {

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Override
	public String getAppPermission(String prodId, String subContentsId) {

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("prodId", prodId);
		param.put("subContentsId", subContentsId);

		return this.commonDAO.queryForObject("AppDetail.getUsePermission", param, String.class);
	}
}
