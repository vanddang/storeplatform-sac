/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.other.feedback.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * MbrAvg Value Object
 * 
 * Updated on : 2014. 1. 16. Updated by : 김현일, 인크로스.
 */
public class MbrAvg extends CommonInfo {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String tenantId;
	private String prodId;
	private String mbrNo;
	private int avgScore;
	private String regId;
	private String regDt;

	/**
	 * @return String
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * @param tenantId
	 *            tenantId
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return String
	 */
	public String getProdId() {
		return this.prodId;
	}

	/**
	 * @param prodId
	 *            prodId
	 */
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	/**
	 * @return String
	 */
	public String getMbrNo() {
		return this.mbrNo;
	}

	/**
	 * @param mbrNo
	 *            mbrNo
	 */
	public void setMbrNo(String mbrNo) {
		this.mbrNo = mbrNo;
	}

	/**
	 * @return int
	 */
	public int getAvgScore() {
		return this.avgScore;
	}

	/**
	 * @param avgScore
	 *            avgScore
	 */
	public void setAvgScore(int avgScore) {
		this.avgScore = avgScore;
	}

	/**
	 * @return String
	 */
	public String getRegId() {
		return this.regId;
	}

	/**
	 * @param regId
	 *            regId
	 */
	public void setRegId(String regId) {
		this.regId = regId;
	}

	/**
	 * @return String
	 */
	public String getRegDt() {
		return this.regDt;
	}

	/**
	 * @param regDt
	 *            regDt
	 */
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

}
