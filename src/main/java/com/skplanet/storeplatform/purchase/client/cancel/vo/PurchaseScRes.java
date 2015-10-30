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

import java.util.List;

import com.skplanet.storeplatform.purchase.client.common.vo.Payment;
import com.skplanet.storeplatform.purchase.client.common.vo.Prchs;
import com.skplanet.storeplatform.purchase.client.common.vo.PrchsDtl;
import com.skplanet.storeplatform.purchase.client.common.vo.PurchaseCommonScRes;

/**
 * 구매 정보 조회 응답 VO.
 * 
 * Updated on : 2014. 2. 12. Updated by : nTels_cswoo81, nTels.
 */
public class PurchaseScRes extends PurchaseCommonScRes {

	private static final long serialVersionUID = 1L;

	private Prchs prchs;
	private List<PrchsDtl> prchsDtlList;
	private List<Payment> paymentList;

	/**
	 * @return the prchs
	 */
	public Prchs getPrchs() {
		return this.prchs;
	}

	/**
	 * @param prchs
	 *            the prchs to set
	 */
	public void setPrchs(Prchs prchs) {
		this.prchs = prchs;
	}

	/**
	 * @return the prchsDtlList
	 */
	public List<PrchsDtl> getPrchsDtlList() {
		return this.prchsDtlList;
	}

	/**
	 * @param prchsDtlList
	 *            the prchsDtlList to set
	 */
	public void setPrchsDtlList(List<PrchsDtl> prchsDtlList) {
		this.prchsDtlList = prchsDtlList;
	}

	/**
	 * @return the paymentList
	 */
	public List<Payment> getPaymentList() {
		return this.paymentList;
	}

	/**
	 * @param paymentList
	 *            the paymentList to set
	 */
	public void setPaymentList(List<Payment> paymentList) {
		this.paymentList = paymentList;
	}

}
