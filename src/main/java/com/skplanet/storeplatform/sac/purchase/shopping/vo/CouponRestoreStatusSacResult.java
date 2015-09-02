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
 * 쇼핑 쿠폰 초기화 응답 VO.
 *
 * Updated on : 2015. 9. 2. Updated by : skp1002448, SK플래닛.
 */
public class CouponRestoreStatusSacResult extends PurchaseCommonSacResult {

	private static final long serialVersionUID = 1L;

	private String resultCd; // 결과 코드
	private String resultMsg; // 결과 메시지

	/**
	 * @return the resultCd
	 */
	public String getResultCd() {
		return resultCd;
	}

	/**
	 * @param resultCd
	 *            the resultCd to set
	 */
	public void setResultCd(String resultCd) {
		this.resultCd = resultCd;
	}

	/**
	 * @return the resultMsg
	 */
	public String getResultMsg() {
		return resultMsg;
	}

	/**
	 * @param resultMsg
	 *            the resultMsg to set
	 */
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
}
