/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.purchase.vo.order;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 구매/결제 통합 구매이력 생성 요청 VO
 * 
 * Updated on : 2014. 6. 24. Updated by : 이승택, nTels.
 */
public class CreateCompletePurchaseSacReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private CreateCompletePurchaseInfoSac purchase;
	private List<PaymentInfo> paymentList;
	private Double targetPaymentAmt;
	private Double saveExpectAmt;
	private Double saveResultAmt;
	private String procSubStatusCd;

	/**
	 * @return the purchase
	 */
	public CreateCompletePurchaseInfoSac getPurchase() {
		return this.purchase;
	}

	/**
	 * @param purchase
	 *            the purchase to set
	 */
	public void setPurchase(CreateCompletePurchaseInfoSac purchase) {
		this.purchase = purchase;
	}

	/**
	 * @return the paymentList
	 */
	public List<PaymentInfo> getPaymentList() {
		return this.paymentList;
	}

	/**
	 * @param paymentList
	 *            the paymentList to set
	 */
	public void setPaymentList(List<PaymentInfo> paymentList) {
		this.paymentList = paymentList;
	}

	/**
	 * @return the targetPaymentAmt
	 */
	public Double getTargetPaymentAmt() {
		return this.targetPaymentAmt;
	}

	/**
	 * @param targetPaymentAmt
	 *            the targetPaymentAmt to set
	 */
	public void setTargetPaymentAmt(Double targetPaymentAmt) {
		this.targetPaymentAmt = targetPaymentAmt;
	}

	/**
	 * @return the saveExpectAmt
	 */
	public Double getSaveExpectAmt() {
		return this.saveExpectAmt;
	}

	/**
	 * @param saveExpectAmt
	 *            the saveExpectAmt to set
	 */
	public void setSaveExpectAmt(Double saveExpectAmt) {
		this.saveExpectAmt = saveExpectAmt;
	}

	/**
	 * @return the saveResultAmt
	 */
	public Double getSaveResultAmt() {
		return this.saveResultAmt;
	}

	/**
	 * @param saveResultAmt
	 *            the saveResultAmt to set
	 */
	public void setSaveResultAmt(Double saveResultAmt) {
		this.saveResultAmt = saveResultAmt;
	}

	/**
	 * @return the procSubStatusCd
	 */
	public String getProcSubStatusCd() {
		return this.procSubStatusCd;
	}

	/**
	 * @param procSubStatusCd
	 *            the procSubStatusCd to set
	 */
	public void setProcSubStatusCd(String procSubStatusCd) {
		this.procSubStatusCd = procSubStatusCd;
	}

}
