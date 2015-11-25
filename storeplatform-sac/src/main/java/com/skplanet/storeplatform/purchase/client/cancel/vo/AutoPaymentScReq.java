/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.cancel.vo;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.purchase.client.common.vo.PurchaseCommonScReq;

/**
 * 자동결제상태정보 요청 VO.
 * 
 * Updated on : 2014. 5. 15. Updated by : nTels_cswoo81, nTels.
 */
public class AutoPaymentScReq extends PurchaseCommonScReq {

	private static final long serialVersionUID = 1L;

	@NotBlank
	private String userKey;

	@NotBlank
	private String prchsId;
	@NotBlank
	private Integer prchsDtlId;

	/**
	 * @return the userKey
	 */
	@Override
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            the userKey to set
	 */
	@Override
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

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
	 * @return the prchsDtlId
	 */
	public Integer getPrchsDtlId() {
		return this.prchsDtlId;
	}

	/**
	 * @param prchsDtlId
	 *            the prchsDtlId to set
	 */
	public void setPrchsDtlId(Integer prchsDtlId) {
		this.prchsDtlId = prchsDtlId;
	}

}
