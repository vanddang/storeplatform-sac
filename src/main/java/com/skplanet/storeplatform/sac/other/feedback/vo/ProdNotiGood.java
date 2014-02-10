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
 * ProdNotiGood Value Object
 * 
 * Updated on : 2014. 1. 16. Updated by : 김현일, 인크로스.
 */
public class ProdNotiGood extends CommonInfo {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String tenantId;
	private String notiSeq;
	private String mbrNo;
	private String regId;
	private String regDt;

	//
	private String action;

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
	 * @return the notiSeq
	 */
	public String getNotiSeq() {
		return this.notiSeq;
	}

	/**
	 * @param notiSeq
	 *            the notiSeq to set
	 */
	public void setNotiSeq(String notiSeq) {
		this.notiSeq = notiSeq;
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

	/**
	 * @return String
	 */
	public String getAction() {
		return this.action;
	}

	/**
	 * @param action
	 *            action
	 */
	public void setAction(String action) {
		this.action = action;
	}

}
