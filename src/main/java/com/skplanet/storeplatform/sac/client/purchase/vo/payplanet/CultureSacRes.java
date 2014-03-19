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

	private String cdResult;
	private String msgResult;
	private String culturePoint;

	/**
	 * @return the cdResult
	 */
	public String getCdResult() {
		return this.cdResult;
	}

	/**
	 * @param cdResult
	 *            the cdResult to set
	 */
	public void setCdResult(String cdResult) {
		this.cdResult = cdResult;
	}

	/**
	 * @return the msgResult
	 */
	public String getMsgResult() {
		return this.msgResult;
	}

	/**
	 * @param msgResult
	 *            the msgResult to set
	 */
	public void setMsgResult(String msgResult) {
		this.msgResult = msgResult;
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

}
