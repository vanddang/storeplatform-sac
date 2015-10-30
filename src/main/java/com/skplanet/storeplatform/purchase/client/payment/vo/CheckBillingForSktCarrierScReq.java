/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.payment.vo;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.purchase.client.common.vo.PurchaseCommonScReq;

/**
 * Class 설명
 * 
 * Updated on : 2014. 2. 26. Updated by : nTels_cswoo81, nTels.
 */
public class CheckBillingForSktCarrierScReq extends PurchaseCommonScReq {

	private static final long serialVersionUID = 1L;

	@NotBlank
	private String userKey;
	@NotBlank
	private String deviceKey;

	private String svcMangNo;

	/** 요 아래부터는 넘기지 마세요. */
	private String paymentStatusCd;
	private String paymentMtdCd;
	private String inAppProdCd;

	/**
	 * @return the paymentStatusCd
	 */
	public String getPaymentStatusCd() {
		return this.paymentStatusCd;
	}

	/**
	 * @param paymentStatusCd
	 *            the paymentStatusCd to set
	 */
	public void setPaymentStatusCd(String paymentStatusCd) {
		this.paymentStatusCd = paymentStatusCd;
	}

	/**
	 * @return the paymentMtdCd
	 */
	public String getPaymentMtdCd() {
		return this.paymentMtdCd;
	}

	/**
	 * @param paymentMtdCd
	 *            the paymentMtdCd to set
	 */
	public void setPaymentMtdCd(String paymentMtdCd) {
		this.paymentMtdCd = paymentMtdCd;
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
	 * @return the inAppProdCd
	 */
	public String getInAppProdCd() {
		return this.inAppProdCd;
	}

	/**
	 * @param inAppProdCd
	 *            the inAppProdCd to set
	 */
	public void setInAppProdCd(String inAppProdCd) {
		this.inAppProdCd = inAppProdCd;
	}

	/**
	 * @return the svcMangNo
	 */
	public String getSvcMangNo() {
		return this.svcMangNo;
	}

	/**
	 * @param svcMangNo
	 *            the svcMangNo to set
	 */
	public void setSvcMangNo(String svcMangNo) {
		this.svcMangNo = svcMangNo;
	}

}
