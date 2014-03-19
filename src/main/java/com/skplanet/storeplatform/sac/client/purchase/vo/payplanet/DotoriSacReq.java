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

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Dotori 조회 요청
 * 
 * Updated on : 2014. 3. 13. Updated by : 조용진, NTELS.
 */
public class DotoriSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;
	@NotNull
	@NotEmpty
	private String token;
	@NotNull
	@NotEmpty
	private String timeReq;
	@NotNull
	@NotEmpty
	private String mid;
	@NotNull
	@NotEmpty
	private String mdn;

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
	 * @return the mdn
	 */
	public String getMdn() {
		return this.mdn;
	}

	/**
	 * @param mdn
	 *            the mdn to set
	 */
	public void setMdn(String mdn) {
		this.mdn = mdn;
	}
}
