/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.common.header.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * x-sac-network-info 헤더를 컨트롤러에서 제공하기 위한 Value Object
 *
 * Updated on : 2014. 1. 13.
 * Updated by : 서대영, SK 플래닛.
 */
public class DeviceHeader extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String model;
	private String dpi;
	private String resolution;
	private String os;
	private String pkg;
	private String svc;

	public DeviceHeader() {
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
	public String getOs() {
		return this.os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public String getPkg() {
		return this.pkg;
	}
	public void setPkg(String pkg) {
		this.pkg = pkg;
	}
	public String getSvc() {
		return this.svc;
	}
	public void setSvc(String svc) {
		this.svc = svc;
	}

}
