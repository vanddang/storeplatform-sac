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

import java.util.Date;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 *
 * Updated on : 2014. 02. 14. Updated by : 임근대, SKP.
 */
public class AuthKey extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String tenantId;
	private String authKey;
	private String authTypeCd;
	private Date issuDt;
	private Date exprDt;
	private String statusCd;
	private String tenantStatusCd;
	private String secret;

	public String getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getAuthKey() {
		return this.authKey;
	}

	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}

	public String getAuthTypeCd() {
		return this.authTypeCd;
	}

	public void setAuthTypeCd(String authTypeCd) {
		this.authTypeCd = authTypeCd;
	}

	public Date getIssuDt() {
		return this.issuDt;
	}

	public void setIssuDt(Date issuDt) {
		this.issuDt = issuDt;
	}

	public Date getExprDt() {
		return this.exprDt;
	}

	public void setExprDt(Date exprDt) {
		this.exprDt = exprDt;
	}

	public String getStatusCd() {
		return this.statusCd;
	}

	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}

	public String getTenantStatusCd() {
		return this.tenantStatusCd;
	}

	public void setTenantStatusCd(String tenantStatusCd) {
		this.tenantStatusCd = tenantStatusCd;
	}

	public String getSecret() {
		return this.secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

}
