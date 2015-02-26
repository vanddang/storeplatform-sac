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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 결제 결과 Noti VO
 * 
 * Updated on : 2014. 2. 28. Updated by : 이승택, nTels.
 */
public class NotifyPaymentSacReq extends CommonInfo {
	private static final long serialVersionUID = 201402281L;

	@NotBlank
	private String resultCd; // 결제처리결과 코드
	private String resultMsg; // 결제처리결과 메시지
	@NotBlank
	private String prchsId; // 구매 ID
	private String userKey; // 사용자 내부관리 번호
	private String prodId;
	@NotNull
	private double totAmt; // 결제 총 금액
	private String mctSpareParam; // 가맹점 파라미터
	private String offeringYn; // 오퍼링 적용 여부

	private Double targetPaymentAmt;
	private Double saveExpectAmt;
	private Double saveResultAmt;
	private String procSubStatusCd;

	@Valid
	private List<PaymentInfo> paymentInfoList; // 결제수단정보 리스트

	/**
	 * @return the resultCd
	 */
	public String getResultCd() {
		return this.resultCd;
	}

	/**
	 * @param resultCd
	 *            the resultCd to set
	 */
	public void setResultCd(String resultCd) {
		this.resultCd = resultCd;
	}

	/**
	 * @return the resultMsg
	 */
	public String getResultMsg() {
		return this.resultMsg;
	}

	/**
	 * @param resultMsg
	 *            the resultMsg to set
	 */
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
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
	 * @return the userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            the userKey to set
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return the totAmt
	 */
	public double getTotAmt() {
		return this.totAmt;
	}

	/**
	 * @param totAmt
	 *            the totAmt to set
	 */
	public void setTotAmt(double totAmt) {
		this.totAmt = totAmt;
	}

	/**
	 * @return the mctSpareParam
	 */
	public String getMctSpareParam() {
		return this.mctSpareParam;
	}

	/**
	 * @param mctSpareParam
	 *            the mctSpareParam to set
	 */
	public void setMctSpareParam(String mctSpareParam) {
		this.mctSpareParam = mctSpareParam;
	}

	/**
	 * @return the offeringYn
	 */
	public String getOfferingYn() {
		return this.offeringYn;
	}

	/**
	 * @param offeringYn
	 *            the offeringYn to set
	 */
	public void setOfferingYn(String offeringYn) {
		this.offeringYn = offeringYn;
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

	/**
	 * @return the paymentInfoList
	 */
	public List<PaymentInfo> getPaymentInfoList() {
		return this.paymentInfoList;
	}

	/**
	 * @param paymentInfoList
	 *            the paymentInfoList to set
	 */
	public void setPaymentInfoList(List<PaymentInfo> paymentInfoList) {
		this.paymentInfoList = paymentInfoList;
	}

	/**
	 * @return the prodId
	 */
	public String getProdId() {
		return this.prodId;
	}

	/**
	 * @param prodId
	 *            the prodId to set
	 */
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

}
