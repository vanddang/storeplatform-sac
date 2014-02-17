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
 * T_CN_INTERFACE 디비 맵핑용 Value Object
 *
 * Updated on : 2014. 2. 5.
 * Updated by : 서대영, SK 플래닛.
 */
public class Interface extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String interfaceId;
	private String url;
	private String statusCd;

	public Interface() {
	}

	public Interface(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	public String getInterfaceId() {
		return this.interfaceId;
	}
	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}
	public String getUrl() {
		return this.url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getStatusCd() {
		return this.statusCd;
	}
	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}

}
