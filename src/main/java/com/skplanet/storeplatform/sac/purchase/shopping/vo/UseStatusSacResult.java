/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.shopping.vo;

import java.util.List;

import com.skplanet.storeplatform.sac.purchase.common.vo.PurchaseCommonSacResult;

/**
 * 쇼핑 쿠폰 사용여부 응답 VO.
 * 
 * Updated on : 2014. 2. 3. Updated by : nTels_cswoo81, nTels.
 */
public class UseStatusSacResult extends PurchaseCommonSacResult {

	private static final long serialVersionUID = 1L;

	private List<UseStatusDetailSacResult> useStatusList;

	/**
	 * @return the useStatusList
	 */
	public List<UseStatusDetailSacResult> getUseStatusList() {
		return this.useStatusList;
	}

	/**
	 * @param useStatusList
	 *            the useStatusList to set
	 */
	public void setUseStatusList(List<UseStatusDetailSacResult> useStatusList) {
		this.useStatusList = useStatusList;
	}

}
