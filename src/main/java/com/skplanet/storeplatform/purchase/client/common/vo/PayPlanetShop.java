/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.common.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * Pay Planet 가맹점 정보
 * 
 * Updated on : 2015. 3. 2. Updated by : 이승택, nTels.
 */
public class PayPlanetShop extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private String tenantId; // 테넌트 ID
	private String mid; // 가맹점 ID
	private String authKey; // 가맹점 인증키
	private String encKey; // 암호화 키
	private String url; // 결제Page URL

	private String apiTypeCd;
	private String reqPathCd;

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return the mid
	 */
	public String getMid() {
		return this.mid;
	}

	/**
	 * @param mid
	 *            the mid to set
	 */
	public void setMid(String mid) {
		this.mid = mid;
	}

	/**
	 * @return the authKey
	 */
	public String getAuthKey() {
		return this.authKey;
	}

	/**
	 * @param authKey
	 *            the authKey to set
	 */
	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}

	/**
	 * @return the encKey
	 */
	public String getEncKey() {
		return this.encKey;
	}

	/**
	 * @param encKey
	 *            the encKey to set
	 */
	public void setEncKey(String encKey) {
		this.encKey = encKey;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return this.url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the apiTypeCd
	 */
	public String getApiTypeCd() {
		return this.apiTypeCd;
	}

	/**
	 * @param apiTypeCd
	 *            the apiTypeCd to set
	 */
	public void setApiTypeCd(String apiTypeCd) {
		this.apiTypeCd = apiTypeCd;
	}

	/**
	 * @return the reqPathCd
	 */
	public String getReqPathCd() {
		return this.reqPathCd;
	}

	/**
	 * @param reqPathCd
	 *            the reqPathCd to set
	 */
	public void setReqPathCd(String reqPathCd) {
		this.reqPathCd = reqPathCd;
	}

}
