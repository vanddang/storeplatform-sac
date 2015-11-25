/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.history.vo;

import java.io.Serializable;
import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 구매내역숨김처리 컴포넌트 응답.
 * 
 * Updated on : 2014. 01. 21. Updated by : 조용진, 엔텔스.
 */
public class PaymentListScRes extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String prchsId; // 구매ID
	private String tenantId; // Tenant ID
	private List<PaymentScRes> paymentList; // 결제방법코드

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
	 * @return the tenantId
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return the paymentList
	 */
	public List<PaymentScRes> getPaymentList() {
		return this.paymentList;
	}

	/**
	 * @param paymentList
	 *            the paymentList to set
	 */
	public void setPaymentList(List<PaymentScRes> paymentList) {
		this.paymentList = paymentList;
	}

}
