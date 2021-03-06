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
 * 무료구매 응답 VO
 * 
 * Updated on : 2014. 4. 2. Updated by : 이승택, nTels.
 */
public class MakeFreePurchaseScRes extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * 생성된 건수
	 */
	private int count;

	/**
	 */
	public MakeFreePurchaseScRes() {
	}

	/**
	 * @param count
	 *            생성된 건수
	 */
	public MakeFreePurchaseScRes(int count) {
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
