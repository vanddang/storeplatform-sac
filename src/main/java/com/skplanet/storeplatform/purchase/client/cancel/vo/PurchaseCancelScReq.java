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

import com.skplanet.storeplatform.purchase.client.common.vo.PurchaseCommonScReq;
import com.skplanet.storeplatform.purchase.client.product.count.vo.InsertPurchaseProductCountScReq;

/**
 * 구매 취소 요청 VO.
 * 
 * Updated on : 2014. 1. 10. Updated by : nTels_cswoo81, nTels.
 */
public class PurchaseCancelScReq extends PurchaseCommonScReq {

	private static final long serialVersionUID = 1L;

	private String reqUserId;

	private String prchsId;

	private String cancelReqPathCd;
	private String cancelDt;
	private String prchsStatusCd;
	private String currPrchsStatusCd;

	private String paymentErrorCode;

	private List<PurchaseCancelPaymentDetailScReq> purchaseCancelPaymentDetailScReqList;

	private InsertPurchaseProductCountScReq insertPurchaseProductCountScReq;

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
	 * @return the insertPurchaseProductCountScReq
	 */
	public InsertPurchaseProductCountScReq getInsertPurchaseProductCountScReq() {
		return this.insertPurchaseProductCountScReq;
	}

	/**
	 * @param insertPurchaseProductCountScReq
	 *            the insertPurchaseProductCountScReq to set
	 */
	public void setInsertPurchaseProductCountScReq(InsertPurchaseProductCountScReq insertPurchaseProductCountScReq) {
		this.insertPurchaseProductCountScReq = insertPurchaseProductCountScReq;
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
	 * @return the prchsStatusCd
	 */
	public String getPrchsStatusCd() {
		return this.prchsStatusCd;
	}

	/**
	 * @param prchsStatusCd
	 *            the prchsStatusCd to set
	 */
	public void setPrchsStatusCd(String prchsStatusCd) {
		this.prchsStatusCd = prchsStatusCd;
	}

	/**
	 * @return the purchaseCancelPaymentDetailScReqList
	 */
	public List<PurchaseCancelPaymentDetailScReq> getPurchaseCancelPaymentDetailScReqList() {
		return this.purchaseCancelPaymentDetailScReqList;
	}

	/**
	 * @param purchaseCancelPaymentDetailScReqList
	 *            the purchaseCancelPaymentDetailScReqList to set
	 */
	public void setPurchaseCancelPaymentDetailScReqList(
			List<PurchaseCancelPaymentDetailScReq> purchaseCancelPaymentDetailScReqList) {
		this.purchaseCancelPaymentDetailScReqList = purchaseCancelPaymentDetailScReqList;
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

	/**
	 * @return the paymentErrorCode
	 */
	public String getPaymentErrorCode() {
		return this.paymentErrorCode;
	}

	/**
	 * @param paymentErrorCode
	 *            the paymentErrorCode to set
	 */
	public void setPaymentErrorCode(String paymentErrorCode) {
		this.paymentErrorCode = paymentErrorCode;
	}

}
