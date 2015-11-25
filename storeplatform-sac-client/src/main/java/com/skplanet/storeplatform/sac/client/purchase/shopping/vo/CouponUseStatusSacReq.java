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

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.sac.client.purchase.common.vo.PurchaseCommonSacReq;

/**
 * 쇼핑쿠폰 사용유무 체크 요청 VO.
 * 
 * Updated on : 2014. 2. 3. Updated by : nTels_cswoo81, nTels.
 */
public class CouponUseStatusSacReq extends PurchaseCommonSacReq {

	private static final long serialVersionUID = 1L;

	@NotBlank
	private String prchsId;
	private String cpnPublishCd;

	/**
	 * @return the prchsId
	 */
	public String getPrchsId() {
		return this.prchsId;
	}

	/**
	 * @param prchsId
	 *            the prchsId to set
	 */
	public void setPrchsId(String prchsId) {
		this.prchsId = prchsId;
	}

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

}
