/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.shopping.vo;

import com.skplanet.storeplatform.sac.purchase.common.vo.PurchaseCommonSacResult;

/**
 * 쿠폰 발급가능여부 조회 Result VO.
 * 
 * Updated on : 2014. 2. 6. Updated by : nTels_cswoo81, nTels.
 */
public class CouponPublishAvailableSacResult extends PurchaseCommonSacResult {

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
