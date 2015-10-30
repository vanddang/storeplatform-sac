/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.internal.purchase.shopping.vo;

import java.util.List;

import com.skplanet.storeplatform.sac.client.internal.purchase.common.vo.PurchaseCommonSacInRes;

/**
 * 쇼핑쿠폰 사용유무 체크 응답 VO.
 * 
 * Updated on : 2014. 2. 3. Updated by : nTels_cswoo81, nTels.
 */
public class CouponUseStatusSacInRes extends PurchaseCommonSacInRes {

	private static final long serialVersionUID = 1L;

	private List<CouponUseStatusDetailSacInRes> cpnUseStatusList;

	/**
	 * @return the cpnUseStatusList
	 */
	public List<CouponUseStatusDetailSacInRes> getCpnUseStatusList() {
		return this.cpnUseStatusList;
	}

	/**
	 * @param cpnUseStatusList
	 *            the cpnUseStatusList to set
	 */
	public void setCpnUseStatusList(List<CouponUseStatusDetailSacInRes> cpnUseStatusList) {
		this.cpnUseStatusList = cpnUseStatusList;
	}

}
