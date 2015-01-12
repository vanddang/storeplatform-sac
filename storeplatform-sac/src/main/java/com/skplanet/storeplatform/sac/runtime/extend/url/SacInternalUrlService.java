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

import java.lang.management.ManagementFactory;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.management.MBeanServer;
import javax.management.ObjectInstance;
import javax.management.ObjectName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.skplanet.storeplatform.framework.core.util.StringUtils;

/**
*
* SAC 내부 호출용 URL 생성기 (SAC 회원/구매/전시/기타 호출)
*
* Created on 2014. 05. 29. by 서대영, SK 플래닛.
*/
@Service
public class SacInternalUrlService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SacInternalUrlService.class);

	/**
	 * 내부 호출 서블릿 Host. ("http://localhost")
	 */
	@Value("#{propertiesForSac['skp.common.inner.servlet.host']}")
	private String innerServletHost;

	private Integer innerServletPort;

	/**
	 * 내부 요청 서블릿 Path. ("/internal")
	 */
	@Value("#{propertiesForSac['skp.common.inner.servlet.path']}")
	private String innerServletPath;

	public void setInnerServletHost(String innerServletHost) {
		this.innerServletHost = innerServletHost;
	}

	public void setInnerServletPath(String innerServletPath) {
		this.innerServletPath = innerServletPath;
	}

	public UriComponentsBuilder buildUrl(String innerRequestURI, String contextPath) {

		return UriComponentsBuilder
				.fromHttpUrl(this.innerServletHost)
				.port(this.innerServletPort)
				.path(contextPath)
				.path(this.innerServletPath)
				.path(innerRequestURI);
	}

	@PostConstruct
	public void init() {
		this.innerServletPort = this.getHttpConnectorPort();
	}

	private Integer getHttpConnectorPort() {
		Integer port = null;

		Exception ex = null;

		try {
			MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

			Set<ObjectInstance> connectors = mBeanServer.queryMBeans(new ObjectName("Catalina:type=Connector,port=*"),
					null);

			for (ObjectInstance objectInstance : connectors) {
				if (StringUtils.equals("HTTP/1.1",
						(String) mBeanServer.getAttribute(objectInstance.getObjectName(), "protocol"))) {
					port = (Integer) mBeanServer.getAttribute(objectInstance.getObjectName(), "port");
				}
			}

		} catch (Exception e) {
			ex = e;
		}

		if (ex != null) {
			LOGGER.warn("####SAC-PORT-TEST#### 비정상 > 예외발생 : " + ex.getMessage());
			return 8010;
		}

		if (port == null) {
			LOGGER.warn("####SAC-PORT-TEST#### 비정상 > PORT가 존재하지 않음");
			return 8010;
		}

		return port;

	}

}
