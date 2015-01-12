package com.skplanet.storeplatform.sac.runtime.common.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class Bypass extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String bypassId;
	private String componentId;
	private String bypassNm;
	private String bypassDesc;
	private String bypassTypeCd;
	private String bypassTypeNm;
	private String path;
	private String version;

	private Component component;

	public Bypass() {
	}

	public Bypass(String bypassId) {
		this.bypassId = bypassId;
	}

	public String getBypassId() {
		return this.bypassId;
	}

	public void setBypassId(String bypassId) {
		this.bypassId = bypassId;
	}

	public String getComponentId() {
		return this.componentId;
	}

	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}

	public String getBypassNm() {
		return this.bypassNm;
	}

	public void setBypassNm(String bypassNm) {
		this.bypassNm = bypassNm;
	}

	public String getBypassDesc() {
		return this.bypassDesc;
	}

	public void setBypassDesc(String bypassDesc) {
		this.bypassDesc = bypassDesc;
	}

	public String getBypassTypeCd() {
		return this.bypassTypeCd;
	}

	public void setBypassTypeCd(String bypassTypeCd) {
		this.bypassTypeCd = bypassTypeCd;
	}

	public String getBypassTypeNm() {
		return this.bypassTypeNm;
	}

	public void setBypassTypeNm(String bypassTypeNm) {
		this.bypassTypeNm = bypassTypeNm;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Component getComponent() {
		return this.component;
	}

	public void setComponent(Component component) {
		this.component = component;
	}

}
