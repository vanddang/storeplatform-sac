package com.skplanet.storeplatform.sac.runtime.common.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class Component extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String componentId;
	private String componentDesc;
	private String scheme;
	private String host;
	private int port;

	public Component() {
	}

	public Component(String componentId) {
		this.componentId = componentId;
	}
	public String getComponentId() {
		return this.componentId;
	}
	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}
	public String getComponentDesc() {
		return this.componentDesc;
	}
	public void setComponentDesc(String componentDesc) {
		this.componentDesc = componentDesc;
	}
	public String getScheme() {
		return this.scheme;
	}
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
	public String getHost() {
		return this.host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return this.port;
	}
	public void setPort(int port) {
		this.port = port;
	}

}
