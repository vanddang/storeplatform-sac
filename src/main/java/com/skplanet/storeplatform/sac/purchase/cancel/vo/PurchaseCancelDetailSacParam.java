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
import com.skplanet.storeplatform.purchase.client.common.vo.Payment;
import com.skplanet.storeplatform.purchase.client.common.vo.Prchs;
import com.skplanet.storeplatform.purchase.client.common.vo.PrchsDtl;

/**
 * 구매 취소(사용자) 요청 상세 VO.
 * 
 * Updated on : 2014. 2. 12. Updated by : nTels_cswoo81, nTels.
 */
public class PurchaseCancelDetailSacParam extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String prchsId;
	private String cancelReqPathCd;

	private Integer prchsCancelByType; // 구매 취소 요청자 구분(사용자 / 운영자).

	/** 구매 내역. */
	private Prchs prchs;

	/** 구매 상세 내역 리스트. */
	private List<PrchsDtl> prchsDtl;

	/** 결제 내역 리스트. */
	private List<Payment> paymentList;

	/** PayPlanet 내역 리스트. */

	/** 구매 내역 DB 취소 결과 개수. */
	private Integer prchsCancelCnt;
	private Integer prchsDtlCancelCnt;
	private Integer paymentCancelCnt;

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
	 * @return the prchsCancelByType
	 */
	public Integer getPrchsCancelByType() {
		return this.prchsCancelByType;
	}

	/**
	 * @param prchsCancelByType
	 *            the prchsCancelByType to set
	 */
	public void setPrchsCancelByType(Integer prchsCancelByType) {
		this.prchsCancelByType = prchsCancelByType;
	}

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
	 * @return the prchsDtl
	 */
	public List<PrchsDtl> getPrchsDtl() {
		return this.prchsDtl;
	}

	/**
	 * @param prchsDtl
	 *            the prchsDtl to set
	 */
	public void setPrchsDtl(List<PrchsDtl> prchsDtl) {
		this.prchsDtl = prchsDtl;
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
