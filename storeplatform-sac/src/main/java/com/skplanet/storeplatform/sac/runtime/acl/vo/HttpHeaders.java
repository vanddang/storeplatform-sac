/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.runtime.acl.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
*
* 요청 헤더 맵핑용 Value Object
*
* Updated on : 2014. 2. 10.
* Updated by : 서대영, SK 플래닛.
*/
public class HttpHeaders extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String requestUrl;
	private String servletPath;
	private String queryString;

	private String authKey;
	private String signature;
	private String timestamp;
	private String nonce;

	private String tenantId;
	private String systemId;
	private String interfaceId;

	private String guid;

	private String remoteHost;
	private String remotePort;

	public String getRequestUrl() {
		return this.requestUrl;
	}

	public String getServletPath() {
		return this.servletPath;
	}

	public void setServletPath(String servletPath) {
		this.servletPath = servletPath;
	}

	public String getQueryString() {
		return this.queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String getAuthKey() {
		return this.authKey;
	}

	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}

	public String getSignature() {
		return this.signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getNonce() {
		return this.nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public String getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getSystemId() {
		return this.systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getInterfaceId() {
		return this.interfaceId;
	}

	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

    public String getRemoteHost() { return this.remoteHost; }

    public void setRemoteHost(String remoteHost) { this.remoteHost = remoteHost; }

    public String getRemotePort() { return this.remotePort; }

    public void setRemotePort(String remotePort) { this.remotePort = remotePort; }
}
