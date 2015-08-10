package com.skplanet.storeplatform.sac.runtime.bypass.service;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.runtime.extend.url.SacExternalUrlService;

@Component
public class BypassUriBuilder {
	
	private static Logger LOGGER = LoggerFactory.getLogger(BypassUriBuilder.class);

	@Autowired
	private SacExternalUrlService extUrlBuilder;
	
	public URI build(String interfaceId, String tenantId, String queryString) {
		UriComponentsBuilder uriBuilder = extUrlBuilder.buildUrl(null, interfaceId, tenantId);
		if (StringUtils.isNotBlank(queryString)) {
			uriBuilder.query(queryString);
		}
		
		UriComponents uriComponents = uriBuilder.build();
		LOGGER.debug("# Bypass URI : " + uriComponents.toUriString());
		
		return uriComponents.toUri(); // do not encode
	}

}
