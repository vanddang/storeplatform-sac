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

import com.skplanet.storeplatform.external.client.payplanet.vo.CancelEcRes;
import com.skplanet.storeplatform.external.client.tstore.vo.PayCancelResult;
import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 구매 취소(사용자) 요청 상세 VO.
 * 
 * Updated on : 2014. 2. 12. Updated by : nTels_cswoo81, nTels.
 */
public class PurchaseCancelDetailSacParam extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String prchsId;
	private String cancelMdn;

	/** 구매 내역. */
	private PrchsSacParam prchsSacParam;

	/** 구매 상세 내역 리스트. */
	private List<PrchsDtlSacParam> prchsDtlSacParamList;

	/** 결제 내역 리스트. */
	private List<PaymentSacParam> paymentSacParamList;

	/** PayPlanet 취소 결과 리스트. */
	CancelEcRes payPlanetCancelEcRes;

	/** TStore 결제 취소 결과 리스트. */
	List<PayCancelResult> tStorePayCancelResultList;

	/** 구매 내역 DB 취소 결과 개수. */
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
	 * @return the prchsId
	 */
	public String getPrchsId() {
		return this.prchsId;
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
	 * @param prchsId
	 *            the prchsId to set
	 */
	public void setPrchsId(String prchsId) {
		this.prchsId = prchsId;
	}

	/**
	 * @return the prchsSacParam
	 */
	public PrchsSacParam getPrchsSacParam() {
		return this.prchsSacParam;
	}

	/**
	 * @param prchsSacParam
	 *            the prchsSacParam to set
	 */
	public void setPrchsSacParam(PrchsSacParam prchsSacParam) {
		this.prchsSacParam = prchsSacParam;
	}

	/**
	 * @return the prchsDtlSacParamList
	 */
	public List<PrchsDtlSacParam> getPrchsDtlSacParamList() {
		return this.prchsDtlSacParamList;
	}

	/**
	 * @param prchsDtlSacParamList
	 *            the prchsDtlSacParamList to set
	 */
	public void setPrchsDtlSacParamList(List<PrchsDtlSacParam> prchsDtlSacParamList) {
		this.prchsDtlSacParamList = prchsDtlSacParamList;
	}

	/**
	 * @return the paymentSacParamList
	 */
	public List<PaymentSacParam> getPaymentSacParamList() {
		return this.paymentSacParamList;
	}

	/**
	 * @param paymentSacParamList
	 *            the paymentSacParamList to set
	 */
	public void setPaymentSacParamList(List<PaymentSacParam> paymentSacParamList) {
		this.paymentSacParamList = paymentSacParamList;
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

	/**
	 * @return the tStorePayCancelResultList
	 */
	public List<PayCancelResult> gettStorePayCancelResultList() {
		return this.tStorePayCancelResultList;
	}

	/**
	 * @param tStorePayCancelResultList
	 *            the tStorePayCancelResultList to set
	 */
	public void settStorePayCancelResultList(List<PayCancelResult> tStorePayCancelResultList) {
		this.tStorePayCancelResultList = tStorePayCancelResultList;
	}

	/**
	 * @return the payPlanetCancelEcRes
	 */
	public CancelEcRes getPayPlanetCancelEcRes() {
		return this.payPlanetCancelEcRes;
	}

	/**
	 * @param payPlanetCancelEcRes
	 *            the payPlanetCancelEcRes to set
	 */
	public void setPayPlanetCancelEcRes(CancelEcRes payPlanetCancelEcRes) {
		this.payPlanetCancelEcRes = payPlanetCancelEcRes;
	}

	/**
	 * @return the cancelMdn
	 */
	public String getCancelMdn() {
		return this.cancelMdn;
	}

	/**
	 * @param cancelMdn
	 *            the cancelMdn to set
	 */
	public void setCancelMdn(String cancelMdn) {
		this.cancelMdn = cancelMdn;
	}

}
