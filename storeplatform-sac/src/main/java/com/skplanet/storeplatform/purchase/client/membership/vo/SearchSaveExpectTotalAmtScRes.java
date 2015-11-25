/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.membership.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 마일리지 적립 예상 총 금액 조회 응답 VO
 * 
 * Updated on : 2014. 8. 8. Updated by : 이승택, nTels.
 */
public class SearchSaveExpectTotalAmtScRes extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private Integer amt;

	public SearchSaveExpectTotalAmtScRes() {
	}

	public SearchSaveExpectTotalAmtScRes(int amt) {
		this.amt = amt;
	}

	/**
	 * @return the amt
	 */
	public Integer getAmt() {
		return this.amt;
	}

	/**
	 * @param amt
	 *            the amt to set
	 */
	public void setAmt(Integer amt) {
		this.amt = amt;
	}

}
