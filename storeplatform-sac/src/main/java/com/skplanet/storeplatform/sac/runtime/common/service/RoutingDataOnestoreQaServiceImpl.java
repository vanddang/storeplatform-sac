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

import com.google.common.collect.Maps;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.runtime.acl.vo.Interface;
import com.skplanet.storeplatform.sac.runtime.common.vo.Bypass;
import com.skplanet.storeplatform.sac.runtime.common.vo.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
* RouteDataService 클래스
* FIXME : 고도화QA, Onestore QA 서버 병행 운영을 위한 EC URL 제공 서비스 (하드 코딩), 사용하지 않을 경우 삭제
*
* Created on 2014. 06. 13. by 서대영, SK 플래닛
* Updated on 2015. 02. 25. by 서대영, SK 플래닛 : 캐시 v2 추가
*/
@Service
public class RoutingDataOnestoreQaServiceImpl implements RoutingDataService {

	private static Logger logger = LoggerFactory.getLogger(RoutingDataOnestoreQaServiceImpl.class);

	@Autowired @Qualifier("sac")
	private CommonDAO dao;

	public void setDao(CommonDAO dao) {
		this.dao = dao;
	}

	static final Map<String, String> ecHostMap = Maps.newHashMap();
	static {
		ecHostMap.put("S01", "qa-ec-store.sungsu.onestore.co.kr");
		ecHostMap.put("S02", "qa-ec-kstore.sungsu.onestore.co.kr");
		ecHostMap.put("S03", "qa-ec-ustore.sungsu.onestore.co.kr");
	}

	private void setEcUrl(Component component, String tenantId) {
		if(component == null) return;
		String origHost = component.getHost();
		String newHost = ecHostMap.get(tenantId);
		logger.debug("tenantId:{}, origHost:{}, newHost:{}", tenantId, origHost, newHost);
		component.setHost(newHost);
	}

	@Override
	public Bypass selectBypassByInterface(String interfaceId, String tenantId) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("interfaceId", interfaceId);
		paramMap.put("tenantId", tenantId);
		Bypass bypass = this.dao.queryForObject("Route.selectBypassByInterface", paramMap, Bypass.class);
		if(bypass != null) setEcUrl(bypass.getComponent(), tenantId);
		return bypass;
	}

	@Override
	public Bypass selectBypassByInterface(Interface interfac) {
		Bypass bypass =  this.dao.queryForObject("Route.selectBypassByInterface", interfac, Bypass.class);
		if(bypass != null) setEcUrl(bypass.getComponent(), "S01");
		return bypass;
	}
	
	@Override
	public Component selectComponent(String componentId, String tenantId) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("componentId", componentId);
		paramMap.put("tenantId", tenantId);
		Component component = this.dao.queryForObject("Route.selectComponent", paramMap, Component.class);
		setEcUrl(component, tenantId);
		return component;
	}

}
