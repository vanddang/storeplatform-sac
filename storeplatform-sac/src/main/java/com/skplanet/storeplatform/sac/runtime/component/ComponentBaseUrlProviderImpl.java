/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.runtime.component;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.proxy.ComponentBaseUrlProvider;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.util.SacRequestHeaderHolder;
import com.skplanet.storeplatform.sac.runtime.common.service.RoutingDataService;
import com.skplanet.storeplatform.sac.runtime.common.service.RoutingDataServiceSelector;
import com.skplanet.storeplatform.sac.runtime.common.vo.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;

/**
* ComponentBaseUrlProviderImpl 클래스
* : Component의 Base URL을 DB로 부터 제공함
*
* Created on 2015-03-13. by 서대영, SK플래닛
*/
@Service
public class ComponentBaseUrlProviderImpl implements ComponentBaseUrlProvider {
	
	private static final String EC_COMPONENT_ID = "005";

	@Autowired
	RoutingDataServiceSelector routingDataServiceSelector;

	@PostConstruct
	public void initRoutingDataService() {
		dataSvc = routingDataServiceSelector.getRoutingDataService();
	}

	private RoutingDataService dataSvc;
	
	@Override
	public String provideBaseUrl() {
		String componentId = EC_COMPONENT_ID; // 현재는 EC 외에는 컴포넌트가 없음
		SacRequestHeader requestHeader = SacRequestHeaderHolder.getValue();
		String tenantId = requestHeader.getTenantHeader().getTenantId();
		
		if (dataSvc == null) {
			throw new StorePlatformException("No Data Service.");
		}
		
		Component component = dataSvc.selectComponent(componentId, tenantId);
		
		if (component == null) {
			throw new StorePlatformException("No component found. (componentId : " + componentId + ", tenantId : " + tenantId + ")");
		}
		
		UriComponentsBuilder builder = UriComponentsBuilder.newInstance();
		builder.scheme(component.getScheme()).host(component.getHost()).port(component.getPort());
		String baseUrl = builder.build().toString();
		
		return baseUrl;
	}

}
