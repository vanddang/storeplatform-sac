/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.runtime.cache.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * Calss 설명
 * 
 * Updated on : 2013. 11. 26. Updated by : 김현일, 인크로스.
 */
public class InterfaceInfo extends CommonInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String interfaceId;
	private String interfaceTypeCd;
	private String url;
	private String version;
	private String interfaceNm;
	private String interfaceDesc;
	private String bypassYn;
	private String statusCd;

	/**
	 * @return String
	 */
	public String getInterfaceId() {
		return this.interfaceId;
	}

	/**
	 * @return String
	 */
	public String getInterfaceTypeCd() {
		return this.interfaceTypeCd;
	}

	/**
	 * @return String
	 */
	public String getUrl() {
		return this.url;
	}

	/**
	 * @return String
	 */
	public String getVersion() {
		return this.version;
	}

	/**
	 * @return String
	 */
	public String getInterfaceNm() {
		return this.interfaceNm;
	}

	/**
	 * @return String
	 */
	public String getInterfaceDesc() {
		return this.interfaceDesc;
	}

	/**
	 * @return String
	 */
	public String getBypassYn() {
		return this.bypassYn;
	}

	/**
	 * @return String
	 */
	public String getStatusCd() {
		return this.statusCd;
	}

	/**
	 * @param interfaceId
	 *            interfaceId
	 */
	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	/**
	 * @param interfaceTypeCd
	 *            interfaceTypeCd
	 */
	public void setInterfaceTypeCd(String interfaceTypeCd) {
		this.interfaceTypeCd = interfaceTypeCd;
	}

	/**
	 * @param url
	 *            url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @param version
	 *            version
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @param interfaceNm
	 *            interfaceNm
	 */
	public void setInterfaceNm(String interfaceNm) {
		this.interfaceNm = interfaceNm;
	}

	/**
	 * @param interfaceDesc
	 *            interfaceDesc
	 */
	public void setInterfaceDesc(String interfaceDesc) {
		this.interfaceDesc = interfaceDesc;
	}

	/**
	 * @param bypassYn
	 *            bypassYn
	 */
	public void setBypassYn(String bypassYn) {
		this.bypassYn = bypassYn;
	}

	/**
	 * @param statusCd
	 *            statusCd
	 */
	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}

}
