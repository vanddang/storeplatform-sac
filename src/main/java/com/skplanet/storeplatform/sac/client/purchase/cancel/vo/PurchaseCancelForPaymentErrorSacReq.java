/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.purchase.cancel.vo;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.sac.client.purchase.common.vo.PurchaseCommonSacReq;

/**
 * Class 설명
 * 
 * Updated on : 2014. 4. 24. Updated by : nTels_cswoo81, nTels.
 */
public class PurchaseCancelForPaymentErrorSacReq extends PurchaseCommonSacReq {

	private static final long serialVersionUID = 1L;

	private String reqUserId;

	@NotBlank
	private String cancelReqPathCd;

	@NotBlank
	private String prchsId;

	/**
	 * @return the reqUserId
	 */
	public String getReqUserId() {
		return this.reqUserId;
	}

	/**
	 * @param reqUserId
	 *            the reqUserId to set
	 */
	public void setReqUserId(String reqUserId) {
		this.reqUserId = reqUserId;
	}

	public String getCancelReqPathCd() {
		return this.cancelReqPathCd;
	}

	public void setCancelReqPathCd(String cancelReqPathCd) {
		this.cancelReqPathCd = cancelReqPathCd;
	}

	public String getPrchsId() {
		return this.prchsId;
	}

	public void setPrchsId(String prchsId) {
		this.prchsId = prchsId;
	}

}
