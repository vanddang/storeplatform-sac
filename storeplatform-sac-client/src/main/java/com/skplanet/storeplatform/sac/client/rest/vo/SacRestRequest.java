/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.rest.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * SacRestClient를 호출할 때 사용되는 요청 객체
 *
 * Created on 2014. 6. 30. by 서대영, SK플래닛.
 */
public class SacRestRequest extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private SacRestScheme scheme;
	private String host;
	private String path;
	private String query;
	private SacRestMethod method;
	private Object body;
	private Object param;

	private String interfaceId; // x-sac-interface-id
	private String tenantId; // x-sac-tenant-id
	private String systemId; // x-sac-system-id

	private String authKey; // x-sac-auth-key
	private String secret; // x-sac-auth-signature를 만드는데 사용됨

	private String device; // x-sac-device-info
	private String network; // x-sac-network-info

	public SacRestRequest() {
		this.scheme = SacRestScheme.http;
		this.method = SacRestMethod.GET;
	}

	public SacRestScheme getScheme() {
		return this.scheme;
	}
	public void setScheme(SacRestScheme scheme) {
		this.scheme = scheme;
	}
	public String getHost() {
		return this.host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPath() {
		return this.path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getQuery() {
		return this.query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public SacRestMethod getMethod() {
		return this.method;
	}
	public void setMethod(SacRestMethod method) {
		this.method = method;
	}
	public Object getBody() {
		return this.body;
	}
	public void setBody(Object body) {
		this.body = body;
	}
	public String getInterfaceId() {
		return this.interfaceId;
	}
	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
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
	public String getAuthKey() {
		return this.authKey;
	}
	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}
	public String getSecret() {
		return this.secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public String getDevice() {
		return this.device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public String getNetwork() {
		return this.network;
	}
	public void setNetwork(String network) {
		this.network = network;
	}
	public Object getParam() {
		return this.param;
	}
	public void setParam(Object param) {
		this.param = param;
	}

}
