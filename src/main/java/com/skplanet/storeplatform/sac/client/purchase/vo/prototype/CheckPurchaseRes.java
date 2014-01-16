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

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * [Prototype] 기구매체크 응답 VO
 * 
 * Updated on : 2014. 1. 16. Updated by : 이승택, nTels.
 */
public class CheckPurchaseRes extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private List<CheckPurchase> checkPurchaseList;

	/**
	 * @return the checkPurchaseList
	 */
	public List<CheckPurchase> getCheckPurchaseList() {
		return this.checkPurchaseList;
	}

	/**
	 * @param checkPurchaseList
	 *            the checkPurchaseList to set
	 */
	public void setCheckPurchaseList(List<CheckPurchase> checkPurchaseList) {
		this.checkPurchaseList = checkPurchaseList;
	}

}
