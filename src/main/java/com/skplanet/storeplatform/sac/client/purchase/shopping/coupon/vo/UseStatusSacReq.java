/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.purchase.shopping.coupon.vo;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.sac.client.purchase.common.vo.PurchaseCommonSacReq;

/**
 * 쇼핑쿠폰 사용유무 체크 요청 VO.
 * 
 * Updated on : 2014. 2. 3. Updated by : nTels_cswoo81, nTels.
 */
public class UseStatusSacReq extends PurchaseCommonSacReq {

	private static final long serialVersionUID = 1L;

	@NotNull
	@NotBlank
	private String prchsId;
	private String cpPublishCd;

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
	 * @return the cpPublishCd
	 */
	public String getCpPublishCd() {
		return this.cpPublishCd;
	}

	/**
	 * @param cpPublishCd
	 *            the cpPublishCd to set
	 */
	public void setCpPublishCd(String cpPublishCd) {
		this.cpPublishCd = cpPublishCd;
	}

}
