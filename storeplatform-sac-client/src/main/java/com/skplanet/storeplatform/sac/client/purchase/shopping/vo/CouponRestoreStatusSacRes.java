/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.purchase.shopping.vo;

import com.skplanet.storeplatform.sac.client.purchase.common.vo.PurchaseCommonSacRes;

/**
 * 쿠폰 사용여부 초기화 응답 VO.
 * 
 * Updated on : 2015. 9. 2. Updated by : skp1002448, SK플래닛.
 */
public class CouponRestoreStatusSacRes extends PurchaseCommonSacRes {

	private static final long serialVersionUID = 1L;

	private String statusCd;
	private String statusMsg;

	/**
	 * @return the statusCd
	 */
	public String getStatusCd() {
		return this.statusCd;
	}

	/**
	 * @param statusCd
	 *            the statusCd to set
	 */
	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}

	/**
	 * @return the statusMsg
	 */
	public String getStatusMsg() {
		return this.statusMsg;
	}

	/**
	 * @param statusMsg
	 *            the statusMsg to set
	 */
	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}

}
