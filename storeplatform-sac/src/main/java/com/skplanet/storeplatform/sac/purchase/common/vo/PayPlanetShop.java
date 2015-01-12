/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.common.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * Pay Planet 가맹점 정보
 * 
 * Updated on : 2014. 3. 25. Updated by : 이승택, nTels.
 */
public class PayPlanetShop extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private String tenantId; // 테넌트 ID
	private String mid; // 가맹점 ID
	private String authKey; // 가맹점 인증키
	private String encKey; // 암호화 키
	private String paymentUrl; // 결제Page URL

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
	 * @return the paymentUrl
	 */
	public String getPaymentUrl() {
		return this.paymentUrl;
	}

	/**
	 * @param paymentUrl
	 *            the paymentUrl to set
	 */
	public void setPaymentUrl(String paymentUrl) {
		this.paymentUrl = paymentUrl;
	}

}
