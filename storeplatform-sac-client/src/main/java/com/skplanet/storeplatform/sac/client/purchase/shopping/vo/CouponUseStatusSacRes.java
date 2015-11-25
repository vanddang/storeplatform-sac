/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.purchase.shopping.vo;

import java.util.List;

import com.skplanet.storeplatform.sac.client.purchase.common.vo.PurchaseCommonSacRes;

/**
 * 쇼핑쿠폰 사용유무 체크 응답 VO.
 * 
 * Updated on : 2014. 2. 3. Updated by : nTels_cswoo81, nTels.
 */
public class CouponUseStatusSacRes extends PurchaseCommonSacRes {

	private static final long serialVersionUID = 1L;

	private List<CouponUseStatusDetailSacRes> cpnUseStatusList;

	/**
	 * @return the cpnUseStatusList
	 */
	public List<CouponUseStatusDetailSacRes> getCpnUseStatusList() {
		return this.cpnUseStatusList;
	}

	/**
	 * @param cpnUseStatusList
	 *            the cpnUseStatusList to set
	 */
	public void setCpnUseStatusList(List<CouponUseStatusDetailSacRes> cpnUseStatusList) {
		this.cpnUseStatusList = cpnUseStatusList;
	}

}
