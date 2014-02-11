/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * EncryptionUsagePolicy Value Object.
 * 
 * Updated on : 2014. 02. 11. Updated by : 이태희, SK 플래닛.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class EncryptionUsagePolicy extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String applyDrm; // DRM 지원여부
	private String expirationDate; // DRM 만료일
	private String bpCode; // 인터파크 DRM키 여부
	private String certKey; // 인터파크 DRM키

	/**
	 * @return the applyDrm
	 */
	public String getApplyDrm() {
		return this.applyDrm;
	}

	/**
	 * @param applyDrm
	 *            the applyDrm to set
	 */
	public void setApplyDrm(String applyDrm) {
		this.applyDrm = applyDrm;
	}

	/**
	 * @return the expirationDate
	 */
	public String getExpirationDate() {
		return this.expirationDate;
	}

	/**
	 * @param expirationDate
	 *            the expirationDate to set
	 */
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	/**
	 * @return the bpCode
	 */
	public String getBpCode() {
		return this.bpCode;
	}

	/**
	 * @param bpCode
	 *            the bpCode to set
	 */
	public void setBpCode(String bpCode) {
		this.bpCode = bpCode;
	}

	/**
	 * @return the certKey
	 */
	public String getCertKey() {
		return this.certKey;
	}

	/**
	 * @param certKey
	 *            the certKey to set
	 */
	public void setCertKey(String certKey) {
		this.certKey = certKey;
	}
}
