/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.purchase.vo.payplanet;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 문화상품권 조회 요청.
 * 
 * Updated on : 2014. 3. 13. Updated by : 조용진, NTELS.
 */
public class CultureSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;
	@NotBlank
	private String token;
	@NotBlank
	private String timeReq;
	@NotBlank
	private String mid;
	@NotBlank
	private String cultureId;
	@NotBlank
	private String culturePwd;
	@NotBlank
	private String ipReq;

	/**
	 * @return the token
	 */
	public String getToken() {
		return this.token;
	}

	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the timeReq
	 */
	public String getTimeReq() {
		return this.timeReq;
	}

	/**
	 * @param timeReq
	 *            the timeReq to set
	 */
	public void setTimeReq(String timeReq) {
		this.timeReq = timeReq;
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
	 * @return the cultureId
	 */
	public String getCultureId() {
		return this.cultureId;
	}

	/**
	 * @param cultureId
	 *            the cultureId to set
	 */
	public void setCultureId(String cultureId) {
		this.cultureId = cultureId;
	}

	/**
	 * @return the culturePwd
	 */
	public String getCulturePwd() {
		return this.culturePwd;
	}

	/**
	 * @param culturePwd
	 *            the culturePwd to set
	 */
	public void setCulturePwd(String culturePwd) {
		this.culturePwd = culturePwd;
	}

	/**
	 * @return the ipReq
	 */
	public String getIpReq() {
		return this.ipReq;
	}

	/**
	 * @param ipReq
	 *            the ipReq to set
	 */
	public void setIpReq(String ipReq) {
		this.ipReq = ipReq;
	}

}
