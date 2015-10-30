/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.order.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 구매확정 응답 VO
 * 
 * Updated on : 2014. 1. 20. Updated by : 이승택, nTels.
 */
public class ConfirmPurchaseScRes extends CommonInfo {
	private static final long serialVersionUID = 201401201L;

	private int count;

	/**
	 */
	public ConfirmPurchaseScRes() {
	}

	/**
	 * @param count
	 *            구매확정된 건수
	 */
	public ConfirmPurchaseScRes(int count) {
		this.count = count;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return this.count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

}
