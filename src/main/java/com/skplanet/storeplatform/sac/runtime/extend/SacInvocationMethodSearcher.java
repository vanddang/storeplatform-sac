/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.runtime.extend;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.framework.integration.serviceactivator.InvocationMethodSearcher;
import com.skplanet.storeplatform.framework.integration.serviceactivator.InvocationMethod;
import com.skplanet.storeplatform.sac.runtime.cache.service.InterfaceService;
import com.skplanet.storeplatform.sac.runtime.cache.vo.ServiceInfo;

/**
 * 실행메소드정보를 가져오는 구현클래스
 * 
 * Updated on : 2013-09-01 Updated by : 최현식, 에이엔비.
 */
@Component
public class SacInvocationMethodSearcher implements InvocationMethodSearcher {

	@Autowired
	private InterfaceService interfaceService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.framework.integration.serviceactivator.InvocationMethodSearcher#search(java.lang.String
	 * )
	 */
	@Override
	public InvocationMethod search(String interfaceId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("interfaceId", interfaceId);

		ServiceInfo serviceVO = this.interfaceService.searchServiceMethod(params);

		if (serviceVO == null)
			throw new RuntimeException("service not found");

		String pkgNm = serviceVO.getPkgNm();
		String classNm = serviceVO.getClassNm();
		String mtdNm = serviceVO.getMethodNm();
		String serviceNm = new StringBuilder().append(pkgNm).append(".").append(classNm).toString();
		InvocationMethod invocationMethodVO = new InvocationMethod(serviceNm, mtdNm);

		return invocationMethodVO;
	}
}
