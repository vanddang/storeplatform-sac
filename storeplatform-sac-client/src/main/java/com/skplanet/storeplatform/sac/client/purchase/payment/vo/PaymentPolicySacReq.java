/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.purchase.payment.vo;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.sac.client.purchase.common.vo.PurchaseHeaderSacReq;

/**
 * Class 설명
 * 
 * Updated on : 2014. 2. 26. Updated by : nTels_yjw, nTels.
 */
public class PaymentPolicySacReq extends PurchaseHeaderSacReq {

	private static final long serialVersionUID = 1L;

	@NotBlank
	private String tenantId;

	@NotBlank
	private String policyType;

	/**
	 * @return the tenantId
	 */
	@Override
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	@Override
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return the policyType
	 */
	public String getPolicyType() {
		return this.policyType;
	}

	/**
	 * @param policyType
	 *            the policyType to set
	 */
	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

}
