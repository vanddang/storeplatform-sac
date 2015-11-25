/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.product.count.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 상품 건수 저장 처리 응답 VO
 * 
 * Updated on : 2014. 3. 25. Updated by : 이승택, nTels.
 */
public class InsertPurchaseProductCountScRes extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private int count;

	/**
	 */
	public InsertPurchaseProductCountScRes() {
	}

	/**
	 * @param count
	 *            저장 갯수
	 */
	public InsertPurchaseProductCountScRes(int count) {
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
