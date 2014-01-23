/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.cancel.vo;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.purchase.cancel.vo.PurchaseCancelReqDetail;

/**
 * 구매 취소 요청 상세 Parameter.
 * 
 * Updated on : 2014. 1. 17. Updated by : nTels_cswoo81, nTels.
 */
public class PurchaseCancelParamDetail extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String prchsId;
	private String cancelReqPathCd;

	private List<PurchaseDetail> purchaseDetailList;

	/*
	 * 구매 내역 DB 취소 결과 개수.
	 */
	private Integer prchsCancelCnt;
	private Integer prchsDtlCancelCnt;
	private Integer prchsSendDtlCancelCnt;
	private Integer paymentCancelCnt;

	public PurchaseCancelParamDetail() {

	}

	public PurchaseCancelParamDetail(PurchaseCancelReqDetail purchaseCancelReqDetail) {
		this.prchsId = purchaseCancelReqDetail.getPrchsId();
		this.cancelReqPathCd = purchaseCancelReqDetail.getCancelReqPathCd();
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
	 * @return the purchaseDetailList
	 */
	public List<PurchaseDetail> getPurchaseDetailList() {
		return this.purchaseDetailList;
	}

	/**
	 * @param purchaseDetailList
	 *            the purchaseDetailList to set
	 */
	public void setPurchaseDetailList(List<PurchaseDetail> purchaseDetailList) {
		this.purchaseDetailList = purchaseDetailList;
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
	 * @return the prchsSendDtlCancelCnt
	 */
	public Integer getPrchsSendDtlCancelCnt() {
		return this.prchsSendDtlCancelCnt;
	}

	/**
	 * @param prchsSendDtlCancelCnt
	 *            the prchsSendDtlCancelCnt to set
	 */
	public void setPrchsSendDtlCancelCnt(Integer prchsSendDtlCancelCnt) {
		this.prchsSendDtlCancelCnt = prchsSendDtlCancelCnt;
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
