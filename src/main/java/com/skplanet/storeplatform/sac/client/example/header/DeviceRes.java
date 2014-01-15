package com.skplanet.storeplatform.sac.client.example.header;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class DeviceRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String model;
	private String dpi;
	private String resolution;
	private String osVersion;
	private String pkgVersion;

	public DeviceRes() {
		super();
	}

	public String getModel() {
		return this.model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getDpi() {
		return this.dpi;
	}
	public void setDpi(String dpi) {
		this.dpi = dpi;
	}
	public String getResolution() {
		return this.resolution;
	}
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}
	public String getOsVersion() {
		return this.osVersion;
	}
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	public String getPkgVersion() {
		return this.pkgVersion;
	}
	public void setPkgVersion(String pkgVersion) {
		this.pkgVersion = pkgVersion;
	}

}
