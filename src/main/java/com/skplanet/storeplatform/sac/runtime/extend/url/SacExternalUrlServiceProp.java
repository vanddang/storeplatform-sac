/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.runtime.extend.url;

import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * SAC 외부 호출용 URL 생성기 (EC 호출)
 *
 * Created on 2014. 05. 29. by 서대영, SK 플래닛.
 */
@Service
public class SacExternalUrlServiceProp implements SacExternalUrlService {

	/**
	 * 임시 external URL -> 추후 Component 테이블의 URL 조합이 됨.
	 */
	@Value("#{propertiesForSac['component.external.baseUrl']}")
	private String externalBaseUrl;

	@Resource(name = "propertiesForSac")
	private Properties properties;

	public void setExternalBaseUrl(String externalBaseUrl) {
		this.externalBaseUrl = externalBaseUrl;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	@Override
	public UriComponentsBuilder buildUrl(String innerRequestURI, String interfaceId) {



		String bypassPath = this.properties.getProperty(innerRequestURI, "");
		return UriComponentsBuilder.fromHttpUrl(this.externalBaseUrl).path(bypassPath);
	}

}
