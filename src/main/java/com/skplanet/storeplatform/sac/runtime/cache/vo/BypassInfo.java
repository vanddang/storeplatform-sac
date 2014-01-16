package com.skplanet.storeplatform.sac.runtime.cache.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * Bypass Value Object
 * 
 * Updated on : 2014. 1. 16. Updated by : 김현일, 인크로스.
 */
public class BypassInfo extends CommonInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String protocolNm;
	private String domain;
	private String port;
	private String path;

	/**
	 * @return String
	 */
	public String getProtocolNm() {
		return this.protocolNm;
	}

	/**
	 * @return String
	 */
	public String getDomain() {
		return this.domain;
	}

	/**
	 * @return String
	 */
	public String getPort() {
		return this.port;
	}

	/**
	 * @return String
	 */
	public String getPath() {
		return this.path;
	}

	/**
	 * @param protocolNm
	 *            protocolNm
	 */
	public void setProtocolNm(String protocolNm) {
		this.protocolNm = protocolNm;
	}

	/**
	 * @param domain
	 *            domain
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}

	/**
	 * @param port
	 *            port
	 */
	public void setPort(String port) {
		this.port = port;
	}

	/**
	 * @param path
	 *            path
	 */
	public void setPath(String path) {
		this.path = path;
	}

}
