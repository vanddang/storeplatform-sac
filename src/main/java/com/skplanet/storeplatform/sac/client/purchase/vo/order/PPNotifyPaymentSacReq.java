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

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 *
 * 결제 결과 Noti VO
 *
 * Updated on : 2014. 4. 8. Updated by : 이승택, nTels.
 */
public class PPNotifyPaymentSacReq extends CommonInfo {
	private static final long serialVersionUID = 201402281L;

	@NotBlank
	private String code; // 결과코드: 0000-정상, 이 외-실패
	private String msg; // 결과메세지
	@NotBlank
	private String tid; // 거래번호
	private String orderId; // 가맹점 주문번호
	private String prchsId; // 가맹점 주문번호
	private String userKey; // 사용자 내부관리 번호
	private String prodId; // 구매한 상품 ID (소장/대여 TAB)
	@NotNull
	private String amtPurchase; // 최종결제금액 (실패시 0)
	private String infoAmtPurchase; // 결제사용정보 (결제수단:결제금액;결제수단:결제금액;<반복>)
	private String infoAmtPurchaseSub; // 보조결제사용정보 (결제수단:보조결제수단:결제금액;<반복>)
	private String mctSpareParam; // 가맹점용 파라미터
	private String billingInfo; // 자동결제용 빌링정보
	private String couponInfo; // 결제에 사용된 쿠폰정보: 쿠폰번호:쿠폰명:쿠폰금액:쿠폰생성주체:쿠폰타입
	private String flgSktTestDevice; // SKT후불 결제 시, 시험폰 결제 여부
	private String offeringYn; // 오퍼링 적용 여부
	private String svcMangNo; // SKT 서비스 관리 번호

	private Double targetPaymentAmt;
	private Double saveExpectAmt;
	private Double saveResultAmt;
	private String procSubStatusCd;

	private String paymentTelecomCd; // 휴대폰 결제 통신사 코드
	private String flgLimitUser; // 한도가입자 여부 Y/N

	/**
	 * @return the code
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return this.msg;
	}

	/**
	 * @param msg
	 *            the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return the tid
	 */
	public String getTid() {
		return this.tid;
	}

	/**
	 * @param tid
	 *            the tid to set
	 */
	public void setTid(String tid) {
		this.tid = tid;
	}

	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return this.orderId;
	}

	/**
	 * @param orderId
	 *            the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	/**
	 * @return the amtPurchase
	 */
	public String getAmtPurchase() {
		return this.amtPurchase;
	}

	/**
	 * @param amtPurchase
	 *            the amtPurchase to set
	 */
	public void setAmtPurchase(String amtPurchase) {
		this.amtPurchase = amtPurchase;
	}

	/**
	 * @return the infoAmtPurchase
	 */
	public String getInfoAmtPurchase() {
		return this.infoAmtPurchase;
	}

	/**
	 * @param infoAmtPurchase
	 *            the infoAmtPurchase to set
	 */
	public void setInfoAmtPurchase(String infoAmtPurchase) {
		this.infoAmtPurchase = infoAmtPurchase;
	}

	/**
	 * @return the infoAmtPurchaseSub
	 */
	public String getInfoAmtPurchaseSub() {
		return this.infoAmtPurchaseSub;
	}

	/**
	 * @param infoAmtPurchaseSub
	 *            the infoAmtPurchaseSub to set
	 */
	public void setInfoAmtPurchaseSub(String infoAmtPurchaseSub) {
		this.infoAmtPurchaseSub = infoAmtPurchaseSub;
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
	 * @return the billingInfo
	 */
	public String getBillingInfo() {
		return this.billingInfo;
	}

	/**
	 * @param billingInfo
	 *            the billingInfo to set
	 */
	public void setBillingInfo(String billingInfo) {
		this.billingInfo = billingInfo;
	}

	/**
	 * @return the couponInfo
	 */
	public String getCouponInfo() {
		return this.couponInfo;
	}

	/**
	 * @param couponInfo
	 *            the couponInfo to set
	 */
	public void setCouponInfo(String couponInfo) {
		this.couponInfo = couponInfo;
	}

	/**
	 * @return the flgSktTestDevice
	 */
	public String getFlgSktTestDevice() {
		return this.flgSktTestDevice;
	}

	/**
	 * @param flgSktTestDevice
	 *            the flgSktTestDevice to set
	 */
	public void setFlgSktTestDevice(String flgSktTestDevice) {
		this.flgSktTestDevice = flgSktTestDevice;
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
	 * @return the svcMangNo
	 */
	public String getSvcMangNo() {
		return this.svcMangNo;
	}

	/**
	 * @param svcMangNo
	 *            the svcMangNo to set
	 */
	public void setSvcMangNo(String svcMangNo) {
		this.svcMangNo = svcMangNo;
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
	 * @return the paymentTelecomCd
	 */
	public String getPaymentTelecomCd() {
		return this.paymentTelecomCd;
	}

	/**
	 * @param paymentTelecomCd
	 *            the paymentTelecomCd to set
	 */
	public void setPaymentTelecomCd(String paymentTelecomCd) {
		this.paymentTelecomCd = paymentTelecomCd;
	}

	/**
	 * @return the flgLimitUser
	 */
	public String getFlgLimitUser() {
		return this.flgLimitUser;
	}

	/**
	 * @param flgLimitUser
	 *            the flgLimitUser to set
	 */
	public void setFlgLimitUser(String flgLimitUser) {
		this.flgLimitUser = flgLimitUser;
	}

}
