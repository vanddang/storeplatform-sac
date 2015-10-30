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

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Class 설명
 * 
 * Updated on : 2014. 3. 14. Updated by : nTels_cswoo81, nTels.
 */
public class PurchaseCancelPaymentDetailScReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String tenantId;
	private String systemId;
	private String prchsId;
	private Integer paymentDtlId;
	private String paymentStatusCd;
	private String tStorePaymentStatusCd;

	private String cancelDt;

	private String currPrchsStatusCd;

	/**
	 * @return the currPrchsStatusCd
	 */
	public String getCurrPrchsStatusCd() {
		return this.currPrchsStatusCd;
	}

	/**
	 * @param currPrchsStatusCd
	 *            the currPrchsStatusCd to set
	 */
	public void setCurrPrchsStatusCd(String currPrchsStatusCd) {
		this.currPrchsStatusCd = currPrchsStatusCd;
	}

	/**
	 * @return the tStorePaymentStatusCd
	 */
	public String gettStorePaymentStatusCd() {
		return this.tStorePaymentStatusCd;
	}

	/**
	 * @param tStorePaymentStatusCd
	 *            the tStorePaymentStatusCd to set
	 */
	public void settStorePaymentStatusCd(String tStorePaymentStatusCd) {
		this.tStorePaymentStatusCd = tStorePaymentStatusCd;
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
	 * @return the systemId
	 */
	public String getSystemId() {
		return this.systemId;
	}

	/**
	 * @param systemId
	 *            the systemId to set
	 */
	public void setSystemId(String systemId) {
		this.systemId = systemId;
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
	 * @return the paymentDtlId
	 */
	public Integer getPaymentDtlId() {
		return this.paymentDtlId;
	}

	/**
	 * @param paymentDtlId
	 *            the paymentDtlId to set
	 */
	public void setPaymentDtlId(Integer paymentDtlId) {
		this.paymentDtlId = paymentDtlId;
	}

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
	 * @return the cancelDt
	 */
	public String getCancelDt() {
		return this.cancelDt;
	}

	/**
	 * @param cancelDt
	 *            the cancelDt to set
	 */
	public void setCancelDt(String cancelDt) {
		this.cancelDt = cancelDt;
	}

}
