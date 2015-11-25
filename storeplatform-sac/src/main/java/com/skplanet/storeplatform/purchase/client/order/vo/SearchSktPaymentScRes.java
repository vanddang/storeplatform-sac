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
 * SKT 후불 결제 관련 조회 응답 VO
 * 
 * Updated on : 2014. 2. 12. Updated by : 이승택, nTels.
 */
public class SearchSktPaymentScRes extends CommonInfo {
	private static final long serialVersionUID = 201402121L;

	private Object val;

	/**
	 */
	public SearchSktPaymentScRes() {
	}

	/**
	 */
	public SearchSktPaymentScRes(Object val) {
		this.val = val;
	}

	/**
	 * @return the val
	 */
	public Object getVal() {
		return this.val;
	}

	/**
	 * @param val
	 *            the val to set
	 */
	public void setVal(Object val) {
		this.val = val;
	}

}
