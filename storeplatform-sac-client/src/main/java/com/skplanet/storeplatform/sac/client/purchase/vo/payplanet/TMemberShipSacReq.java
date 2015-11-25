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
 * TMemberShip point 조회 요청
 * 
 * Updated on : 2014. 3. 13. Updated by : 조용진, NTELS.
 */
public class TMemberShipSacReq extends CommonInfo {

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
	private String noTmsCard;
	@NotNull
	@NotEmpty
	private String dtBirth;

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
	 * @return the noTmsCard
	 */
	public String getNoTmsCard() {
		return this.noTmsCard;
	}

	/**
	 * @param noTmsCard
	 *            the noTmsCard to set
	 */
	public void setNoTmsCard(String noTmsCard) {
		this.noTmsCard = noTmsCard;
	}

	/**
	 * @return the dtBirth
	 */
	public String getDtBirth() {
		return this.dtBirth;
	}

	/**
	 * @param dtBirth
	 *            the dtBirth to set
	 */
	public void setDtBirth(String dtBirth) {
		this.dtBirth = dtBirth;
	}

}
