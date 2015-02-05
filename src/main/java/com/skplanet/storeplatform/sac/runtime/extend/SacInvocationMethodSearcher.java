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

import com.skplanet.storeplatform.framework.integration.serviceactivator.InvocationMethod;
import com.skplanet.storeplatform.framework.integration.serviceactivator.InvocationMethodSearcher;
import com.skplanet.storeplatform.sac.runtime.cache.service.InterfaceService;
import com.skplanet.storeplatform.sac.runtime.cache.vo.ServiceInfo;
import org.springframework.util.StringUtils;

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
	public InvocationMethod searchId(String interfaceId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("interfaceId", interfaceId);

		ServiceInfo serviceInfo = this.interfaceService.searchServiceMethod(params);

		if (serviceInfo == null)
			throw new RuntimeException("service not found");

		String pkgNm = serviceInfo.getPkgNm();
		String classNm = serviceInfo.getClassNm();
		String mtdNm = serviceInfo.getMethodNm();
		String serviceNm = new StringBuilder().append(pkgNm).append(".").append(classNm).toString();
		InvocationMethod invocationMethodVO = new InvocationMethod(serviceNm, mtdNm);

		return invocationMethodVO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.framework.integration.serviceactivator.InvocationMethodSearcher#searchName(java.lang
	 * .String)
	 */
	@Override
	public InvocationMethod searchName(String messageQueueName) {
        String serviceNm, mtdNm;

        if(StringUtils.isEmpty(messageQueueName))
            throw new RuntimeException("Service not found.");

        if(messageQueueName.contains("icms-app-refactoring-deploy.sac.deploy.async")) {
            serviceNm = "com.skplanet.storeplatform.sac.display.product.service.ProductDeployCompositeServiceImpl";
            mtdNm = "executeProcess";
        }
        else if(messageQueueName.contains("icms-app-admin.sac.device-mapping.async")) {
            serviceNm = "com.skplanet.storeplatform.sac.display.product.service.DeviceMappingCompositeServiceImpl";
            mtdNm = "executeProcess";
        }
        else if(messageQueueName.contains("icms-app-admin.sac.device-remapping.async")) {
            serviceNm = "com.skplanet.storeplatform.sac.display.product.service.DeviceRemappingCompositeServiceImpl";
            mtdNm = "executeProcess";
        }
        else
            throw new RuntimeException();

        return new InvocationMethod(serviceNm, mtdNm);
	}
}
