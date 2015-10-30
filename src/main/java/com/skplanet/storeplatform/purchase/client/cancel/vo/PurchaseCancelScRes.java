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

import com.skplanet.storeplatform.purchase.client.common.vo.PurchaseCommonScRes;

/**
 * 구매 취소 DB 업데이트 결과.
 * 
 * Updated on : 2014. 1. 10. Updated by : nTels_cswoo81, nTels.
 */
public class PurchaseCancelScRes extends PurchaseCommonScRes {

	private static final long serialVersionUID = 1L;

	private Integer prchsCancelCnt;
	private Integer prchsDtlCancelCnt;
	private Integer paymentCancelCnt;
	private Integer autoPrchsCancelCnt;
	private Integer prchsProdCntCnt;

	/**
	 * @return the autoPrchsCancelCnt
	 */
	public Integer getAutoPrchsCancelCnt() {
		return this.autoPrchsCancelCnt;
	}

	/**
	 * @param autoPrchsCancelCnt
	 *            the autoPrchsCancelCnt to set
	 */
	public void setAutoPrchsCancelCnt(Integer autoPrchsCancelCnt) {
		this.autoPrchsCancelCnt = autoPrchsCancelCnt;
	}

	/**
	 * @return the prchsProdCntCnt
	 */
	public Integer getPrchsProdCntCnt() {
		return this.prchsProdCntCnt;
	}

	/**
	 * @param prchsProdCntCnt
	 *            the prchsProdCntCnt to set
	 */
	public void setPrchsProdCntCnt(Integer prchsProdCntCnt) {
		this.prchsProdCntCnt = prchsProdCntCnt;
	}

	/**
	 * @return the prchsCancelCnt
	 */
	public Integer getPrchsCancelCnt() {
		return this.prchsCancelCnt;
	}

	/**
	 * @param prchsCancelCnt
	 *            the prchsCancelCnt to set
	 */
	public void setPrchsCancelCnt(Integer prchsCancelCnt) {
		this.prchsCancelCnt = prchsCancelCnt;
	}

	/**
	 * @return the prchsDtlCancelCnt
	 */
	public Integer getPrchsDtlCancelCnt() {
		return this.prchsDtlCancelCnt;
	}

	/**
	 * @param prchsDtlCancelCnt
	 *            the prchsDtlCancelCnt to set
	 */
	public void setPrchsDtlCancelCnt(Integer prchsDtlCancelCnt) {
		this.prchsDtlCancelCnt = prchsDtlCancelCnt;
	}

	/**
	 * @return the paymentCancelCnt
	 */
	public Integer getPaymentCancelCnt() {
		return this.paymentCancelCnt;
	}

	/**
	 * @param paymentCancelCnt
	 *            the paymentCancelCnt to set
	 */
	public void setPaymentCancelCnt(Integer paymentCancelCnt) {
		this.paymentCancelCnt = paymentCancelCnt;
	}

}
