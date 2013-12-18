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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 
 * Calss 설명
 * 
 * Updated on : 2013. 11. 26. Updated by : 김현일, 인크로스.
 */
/**
 * Calss 설명
 * 
 * Updated on : 2013. 11. 28. Updated by : 김현일, 인크로스.
 */
public class AclAuthKeyVO {
	private String tenantId;
	private String systemId;
	private String authKeyUseYn;
	private String authKey;
	private String authKeyCd;
	private String issuDt;
	private String exprDt;
	private String tenStatusCd;
	private String sysStatusCd;
	private String athStatusCd;

	/**
	 * 
	 * @return String
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * 
	 * @param tenantId
	 *            tenantId
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * 
	 * @return String
	 */
	public String getSystemId() {
		return this.systemId;
	}

	/**
	 * 
	 * @param systemId
	 *            systemId
	 */
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	/**
	 * 
	 * @return String
	 */
	public String getAuthKeyUseYn() {
		return this.authKeyUseYn;
	}

	/**
	 * 
	 * @param authKeyUseYn
	 *            authKeyUseYn
	 */
	public void setAuthKeyUseYn(String authKeyUseYn) {
		this.authKeyUseYn = authKeyUseYn;
	}

	/**
	 * 
	 * @return String
	 */
	public String getAuthKey() {
		return this.authKey;
	}

	/**
	 * 
	 * @param authKey
	 *            authKey
	 */
	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}

	/**
	 * 
	 * @return String
	 */
	public String getAuthKeyCd() {
		return this.authKeyCd;
	}

	/**
	 * 
	 * @param authKeyCd
	 *            authKeyCd
	 */
	public void setAuthKeyCd(String authKeyCd) {
		this.authKeyCd = authKeyCd;
	}

	/**
	 * 
	 * @return String
	 */
	public String getIssuDt() {
		return this.issuDt;
	}

	/**
	 * 
	 * @param issuDt
	 *            issuDt
	 */
	public void setIssuDt(String issuDt) {
		this.issuDt = issuDt;
	}

	/**
	 * 
	 * @return String
	 */
	public String getExprDt() {
		return this.exprDt;
	}

	/**
	 * 
	 * @param exprDt
	 *            exprDt
	 */
	public void setExprDt(String exprDt) {
		this.exprDt = exprDt;
	}

	/**
	 * 
	 * @return String
	 */
	public String getTenStatusCd() {
		return this.tenStatusCd;
	}

	/**
	 * 
	 * @param tenStatusCd
	 *            tenStatusCd
	 */
	public void setTenStatusCd(String tenStatusCd) {
		this.tenStatusCd = tenStatusCd;
	}

	/**
	 * 
	 * @return String
	 */
	public String getSysStatusCd() {
		return this.sysStatusCd;
	}

	/**
	 * 
	 * @param sysStatusCd
	 *            sysStatusCd
	 */
	public void setSysStatusCd(String sysStatusCd) {
		this.sysStatusCd = sysStatusCd;
	}

	/**
	 * 
	 * @return String
	 */
	public String getAthStatusCd() {
		return this.athStatusCd;
	}

	/**
	 * 
	 * @param athStatusCd
	 *            athStatusCd
	 */
	public void setAthStatusCd(String athStatusCd) {
		this.athStatusCd = athStatusCd;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
