/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.product.count.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Class 설명
 * 
 * Updated on : 2014. 3. 27. Updated by : nTels_cswoo81, nTels.
 */
public class PurchaseProductCountSacParam extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String updId;
	private Integer disPerCount; // 전시에 한번 요청할 카운트.

	/**
	 * @return the disPerCount
	 */
	public Integer getDisPerCount() {
		return this.disPerCount;
	}

	/**
	 * @param disPerCount
	 *            the disPerCount to set
	 */
	public void setDisPerCount(Integer disPerCount) {
		this.disPerCount = disPerCount;
	}

	/**
	 * @return the updId
	 */
	public String getUpdId() {
		return this.updId;
	}

	/**
	 * @param updId
	 *            the updId to set
	 */
	public void setUpdId(String updId) {
		this.updId = updId;
	}

}
