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

import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.sac.client.purchase.common.vo.PurchaseCommonSacReq;

/**
 * Class 설명
 * 
 * Updated on : 2014. 3. 20. Updated by : nTels_cswoo81, nTels.
 */
public class PurchaseCancelForTCashSacReq extends PurchaseCommonSacReq {

	private static final long serialVersionUID = 1L;

	private String reqUserId;

	@NotBlank
	private String userKey;
	@NotBlank
	private String deviceKey;

	@NotBlank
	private String cancelReqPathCd;

	@NotEmpty
	@Valid
	private List<PurchaseCancelDetailSacReq> prchsCancelList;

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
	 * @return the deviceKey
	 */
	@Override
	public String getDeviceKey() {
		return this.deviceKey;
	}

	/**
	 * @param deviceKey
	 *            the deviceKey to set
	 */
	@Override
	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	/**
	 * @return the cancelReqPathCd
	 */
	public String getCancelReqPathCd() {
		return this.cancelReqPathCd;
	}

	/**
	 * @param cancelReqPathCd
	 *            the cancelReqPathCd to set
	 */
	public void setCancelReqPathCd(String cancelReqPathCd) {
		this.cancelReqPathCd = cancelReqPathCd;
	}

	/**
	 * @return the prchsCancelList
	 */
	public List<PurchaseCancelDetailSacReq> getPrchsCancelList() {
		return this.prchsCancelList;
	}

	/**
	 * @param prchsCancelList
	 *            the prchsCancelList to set
	 */
	public void setPrchsCancelList(List<PurchaseCancelDetailSacReq> prchsCancelList) {
		this.prchsCancelList = prchsCancelList;
	}

}
