/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.purchase.vo.history;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 구매내역 회원정보 변경 응답.
 * 
 * Updated on : 2014. 2. 26. Updated by :Updated by : 조용진, 엔텔스.
 */
public class PurchaseUserInfoSacRes extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer count; // 변경된 건수

	/**
	 * @return the count
	 */
	public Integer getCount() {
		return this.count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(Integer count) {
		this.count = count;
	}

}
