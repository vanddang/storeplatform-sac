package com.skplanet.storeplatform.sac.runtime.common.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class Component extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String componentId;
	private String componentNm;
	private String componentDesc;
	private String protocolCd;
	private String protocolNm;
	private String domain;
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
	public String getComponentNm() {
		return this.componentNm;
	}
	public void setComponentNm(String componentNm) {
		this.componentNm = componentNm;
	}
	public String getComponentDesc() {
		return this.componentDesc;
	}
	public void setComponentDesc(String componentDesc) {
		this.componentDesc = componentDesc;
	}
	public String getProtocolCd() {
		return this.protocolCd;
	}
	public void setProtocolCd(String protocolCd) {
		this.protocolCd = protocolCd;
	}
	public String getProtocolNm() {
		return this.protocolNm;
	}
	public void setProtocolNm(String protocolNm) {
		this.protocolNm = protocolNm;
	}
	public String getDomain() {
		return this.domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public int getPort() {
		return this.port;
	}
	public void setPort(int port) {
		this.port = port;
	}

}
