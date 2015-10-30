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
 * 구매ID 생성을 위한 (Next) 시퀀스 값 및 현재일시 조회 응답 VO
 * 
 * Updated on : 2014. 5. 2. Updated by : 이승택, nTels.
 */
public class SearchPurchaseSequenceAndDateRes extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private String nextSequence;
	private String nowDate;

	/**
	 * @return the nextSequence
	 */
	public String getNextSequence() {
		return this.nextSequence;
	}

	/**
	 * @param nextSequence
	 *            the nextSequence to set
	 */
	public void setNextSequence(String nextSequence) {
		this.nextSequence = nextSequence;
	}

	/**
	 * @return the nowDate
	 */
	public String getNowDate() {
		return this.nowDate;
	}

	/**
	 * @param nowDate
	 *            the nowDate to set
	 */
	public void setNowDate(String nowDate) {
		this.nowDate = nowDate;
	}

}
