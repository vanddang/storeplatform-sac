/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.runtime.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.runtime.acl.vo.Interface;
import com.skplanet.storeplatform.sac.runtime.common.vo.Bypass;

/**
* RouteDataService 클래스
*
* Created on 2014. 06. 13. by 서대영, SK 플래닛
*/
@Service
public class RoutingDataServiceImpl implements RoutingDataService {

	@Autowired @Qualifier("sac")
	private CommonDAO dao;

	public void setDao(CommonDAO dao) {
		this.dao = dao;
	}

	@Cacheable(value = "sac:runtime:selectBypassByInterface")
	@Override
	public Bypass selectBypassByInterface(Interface interfac) {
		return this.dao.queryForObject("Route.selectBypassByInterface", interfac, Bypass.class);
	}

}
