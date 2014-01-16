/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.purchase.vo.prototype;

import java.util.ArrayList;
import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * [Prototype] 구매내역 조회 결과 VO
 * 
 * Updated on : 2014. 1. 16. Updated by : 이승택, nTels.
 */
public class MyPagePurchaseHistory extends CommonInfo {
	private static final long serialVersionUID = 201311131L;

	private List<MyPagePurchaseProduct> history;
	private List<TestTime> testTimeList = new ArrayList<TestTime>();

	/**
	 * @return the history
	 */
	public List<MyPagePurchaseProduct> getHistory() {
		return this.history;
	}

	/**
	 * @param history
	 *            the history to set
	 */
	public void setHistory(List<MyPagePurchaseProduct> history) {
		this.history = history;
	}

	/**
	 * @return the testTimeList
	 */
	public List<TestTime> getTestTimeList() {
		return this.testTimeList;
	}

	/**
	 * @param testTimeList
	 *            the testTimeList to set
	 */
	public void setTestTimeList(List<TestTime> testTimeList) {
		this.testTimeList = testTimeList;
	}

}
