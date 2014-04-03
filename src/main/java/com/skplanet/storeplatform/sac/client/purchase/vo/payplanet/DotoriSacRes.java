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
 * Dotori 조회 응답
 * 
 * Updated on : 2014. 3. 13. Updated by : 조용진, NTELS.
 */
public class DotoriSacRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String code;
	private String msg;
	private String cntDotori;

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
	 * @return the cntDotori
	 */
	public String getCntDotori() {
		return this.cntDotori;
	}

	/**
	 * @param cntDotori
	 *            the cntDotori to set
	 */
	public void setCntDotori(String cntDotori) {
		this.cntDotori = cntDotori;
	}

}
