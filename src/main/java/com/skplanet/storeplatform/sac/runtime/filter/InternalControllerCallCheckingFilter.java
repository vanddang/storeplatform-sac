/**
 * 
 */
package com.skplanet.storeplatform.sac.runtime.filter;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skplanet.storeplatform.framework.core.util.StringUtils;

/**
 * Calss 설명
 * 
 * Updated on : 2014. 4. 14. Updated by : 홍占썸동, SK 占시뤄옙占쏙옙.
 */
public class InternalControllerCallCheckingFilter implements Filter {
	private static Logger LOGGER = LoggerFactory.getLogger(RequestFilter.class);

	private Set<String> localhostAddresses;

	private Integer serverPort;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.localhostAddresses = this.obtainLocalHostAddress();

		this.serverPort = this.getHttpConnectorPort();

		LOGGER.info(String.format("INIT ALLOWED VALUE [allowedHost : %s, allowedPort : %d]", this.localhostAddresses,
				this.serverPort));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse,
	 * javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {

		HttpServletRequest servletRequest = (HttpServletRequest) request;

		String remoteAddress = servletRequest.getRemoteAddr();

		String remoteHost = servletRequest.getRemoteHost();

		Integer remotePort = servletRequest.getRemotePort();

		this.localhostAddresses.contains(remoteHost);

		boolean isMatched = this.localhostAddresses.contains(remoteHost);

		LOGGER.info(String
				.format("ALLOWED VALUE [allowedHost : %s, allowedPort : %d], [remoteAddress : %s, remoteHost : %s, remotePort : %d]",
						this.localhostAddresses, this.serverPort, remoteAddress, remoteHost, remotePort));

		if (!isMatched) {
			LOGGER.info(String.format(
					"HOST NOT MATCHED!!! [allowedHost : %s][remoteAddress : %s, remoteHost : %s, remotePort : %d]",
					this.localhostAddresses, remoteAddress, remoteHost, remotePort));
		}

		if (this.serverPort != remotePort) {
			LOGGER.info(String.format(
					"PORT NOT MATCHED!!! [allowedPort : %d][remoteAddress : %s, remoteHost : %s, remotePort : %d]",
					this.serverPort, remoteAddress, remoteHost, remotePort));
		}

		chain.doFilter(request, response);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	private Set<String> obtainLocalHostAddress() {
		Set<String> localhostAddresses = null;

		try {
			localhostAddresses = new HashSet<String>();

			localhostAddresses.add(InetAddress.getLocalHost().getHostAddress());

			for (InetAddress address : InetAddress.getAllByName("localhost")) {
				localhostAddresses.add(address.getHostAddress());
			}

		} catch (Exception ex) {

		}

		return CollectionUtils.isEmpty(localhostAddresses) ? Collections.<String> emptySet() : localhostAddresses;

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

		if (port == 8010 || port == 8020) {
			LOGGER.warn("####SAC-PORT-TEST#### 정상 > 포트 : " + port);
			return port;
		} else {
			LOGGER.warn("####SAC-PORT-TEST#### 비정상 > 포트 : " + port + " 예상포트(8010, 8020)가 아님");
			return 8010;
		}

	}
}
