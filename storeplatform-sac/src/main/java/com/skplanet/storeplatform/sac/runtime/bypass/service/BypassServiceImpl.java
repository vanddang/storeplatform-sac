package com.skplanet.storeplatform.sac.runtime.bypass.service;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.sac.common.constant.CommonConstants;
import com.skplanet.storeplatform.sac.runtime.acl.service.common.AclDataAccessService;
import com.skplanet.storeplatform.sac.runtime.acl.vo.Interface;

@Component
public class BypassServiceImpl implements BypassService {
	
	@Autowired
	private AclDataAccessService aclDataService;
	
	@Autowired
	private BypassUriBuilder bypassUriBilder;
	
	@Autowired
	private BypassInvoker bypassInvoker;

	@Override
	public boolean isBypass(String interfaceId) {
		// 인터페이스 아이디가 없으면 바이패스일리가 없음
		if (StringUtils.isBlank(interfaceId)) {
			return false;
		}
		
		Interface intf = aclDataService.selectInterfaceById(interfaceId);
		if (intf != null) {
			return intf.isBypass();
		} else {
			return false;
		}
	}
	
	@Override
	public ResponseEntity<String> bypass(String method, String queryString, HttpHeaders requestHeaders, String requestBody, HttpServletResponse response) {
		String interfaceId = requestHeaders.getFirst(CommonConstants.HEADER_INTERFACE_ID);
		String tenantId = requestHeaders.getFirst(CommonConstants.HEADER_TENANT_ID);
		URI uri = bypassUriBilder.build(interfaceId, tenantId, queryString);
		HttpMethod httpMethod = HttpMethod.valueOf(method);
		ResponseEntity<String> responseEntity = bypassInvoker.invoke(uri, httpMethod, requestHeaders, requestBody, response);
		return responseEntity;
	}

}
