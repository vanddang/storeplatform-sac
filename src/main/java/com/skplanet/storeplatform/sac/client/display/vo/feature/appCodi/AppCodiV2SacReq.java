/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.feature.appCodi;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * App Codi V2 조회 Request Value Object.
 * 
 * Updated on : 2014. 11. 04. Updated by : 이태희, 부르칸.
 */
public class AppCodiV2SacReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	@NotBlank
	@Pattern(regexp = "100|101")
	private String preference; // 선호타입

	@NotBlank
	private String userKey; // 사용자키

	private String prodGradeCd; // 상품이용등급코드

	/**
	 * @return the preference
	 */
	public String getPreference() {
		return this.preference;
	}

	/**
	 * @param preference
	 *            the preference to set
	 */
	public void setPreference(String preference) {
		this.preference = preference;
	}

	/**
	 * @return the userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            the userKey to set
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return the prodGradeCd
	 */
	public String getProdGradeCd() {
		return this.prodGradeCd;
	}

	/**
	 * @param prodGradeCd
	 *            the prodGradeCd to set
	 */
	public void setProdGradeCd(String prodGradeCd) {
		this.prodGradeCd = prodGradeCd;
	}
}
