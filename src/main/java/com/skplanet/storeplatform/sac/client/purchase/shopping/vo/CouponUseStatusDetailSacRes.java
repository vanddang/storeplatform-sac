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

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 쇼핑쿠폰 사용유무 체크 응답 상세 VO.
 * 
 * Updated on : 2014. 2. 3. Updated by : nTels_cswoo81, nTels.
 */
public class CouponUseStatusDetailSacRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String cpnPublishCd;
	private String cpnUseStatusCd;

	/**
	 * @return the cpnPublishCd
	 */
	public String getCpnPublishCd() {
		return this.cpnPublishCd;
	}

	/**
	 * @param cpnPublishCd
	 *            the cpnPublishCd to set
	 */
	public void setCpnPublishCd(String cpnPublishCd) {
		this.cpnPublishCd = cpnPublishCd;
	}

	/**
	 * @return the cpnUseStatusCd
	 */
	public String getCpnUseStatusCd() {
		return this.cpnUseStatusCd;
	}

	/**
	 * @param cpnUseStatusCd
	 *            the cpnUseStatusCd to set
	 */
	public void setCpnUseStatusCd(String cpnUseStatusCd) {
		this.cpnUseStatusCd = cpnUseStatusCd;
	}

}
