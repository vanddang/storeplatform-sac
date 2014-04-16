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

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 문화상품권 조회 응답.
 * 
 * Updated on : 2014. 3. 13. Updated by : 조용진, NTELS.
 */
public class CultureSacRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String code;
	private String msg;
	private String culturePoint;
	private String cultureCustId;
	private String cultureCertNo;
	private String cultureAuthType;

	/**
	 * @return the code
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return this.msg;
	}

	/**
	 * @param msg
	 *            the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return the culturePoint
	 */
	public String getCulturePoint() {
		return this.culturePoint;
	}

	/**
	 * @param culturePoint
	 *            the culturePoint to set
	 */
	public void setCulturePoint(String culturePoint) {
		this.culturePoint = culturePoint;
	}

	/**
	 * @return the cultureCustId
	 */
	public String getCultureCustId() {
		return this.cultureCustId;
	}

	/**
	 * @param cultureCustId
	 *            the cultureCustId to set
	 */
	public void setCultureCustId(String cultureCustId) {
		this.cultureCustId = cultureCustId;
	}

	/**
	 * @return the cultureCertNo
	 */
	public String getCultureCertNo() {
		return this.cultureCertNo;
	}

	/**
	 * @param cultureCertNo
	 *            the cultureCertNo to set
	 */
	public void setCultureCertNo(String cultureCertNo) {
		this.cultureCertNo = cultureCertNo;
	}

	/**
	 * @return the cultureAuthType
	 */
	public String getCultureAuthType() {
		return this.cultureAuthType;
	}

	/**
	 * @param cultureAuthType
	 *            the cultureAuthType to set
	 */
	public void setCultureAuthType(String cultureAuthType) {
		this.cultureAuthType = cultureAuthType;
	}

}
