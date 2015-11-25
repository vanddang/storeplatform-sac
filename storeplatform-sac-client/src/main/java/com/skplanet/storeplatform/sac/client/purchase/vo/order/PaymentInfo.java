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
 * 결제 처리된 결제수단 정보
 * 
 * Updated on : 2014. 2. 28. Updated by : 이승택, nTels.
 */
public class PaymentInfo extends CommonInfo {
	private static final long serialVersionUID = 201402281L;

	@NotBlank
	private String tid; // PG사 거래번호
	@NotBlank
	private String paymentMtdCd; // 결제수단 코드
	@NotNull
	private double paymentAmt; // 결제 금액
	private String paymentDt; // 결제 일시 (승인 일시)
	private String apprNo; // 승인번호
	private String billKey; // 빌링키
	private String cpnId; // 이용한 쿠폰 ID
	private String cpnType; // 이용한 쿠폰 타입
	private String cpnMakeHost; // 이용한 쿠폰 생성주체
	private String ocbType; // 신OCB 처리타입 : ID-ONE ID, CARD-OCB 카드번호
	private String moid; // 테넌트 결제수단 관리 정보
	private String sktTestDeviceYn; // SKT 시험폰 결제 여부
	private String mnoCd; // DCB 결제 통신사 코드
	private String limitMemberYn; // 한도가입자 여부 Y/N

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
	 * @return the paymentMtdCd
	 */
	public String getPaymentMtdCd() {
		return this.paymentMtdCd;
	}

	/**
	 * @param paymentMtdCd
	 *            the paymentMtdCd to set
	 */
	public void setPaymentMtdCd(String paymentMtdCd) {
		this.paymentMtdCd = paymentMtdCd;
	}

	/**
	 * @return the paymentAmt
	 */
	public double getPaymentAmt() {
		return this.paymentAmt;
	}

	/**
	 * @param paymentAmt
	 *            the paymentAmt to set
	 */
	public void setPaymentAmt(double paymentAmt) {
		this.paymentAmt = paymentAmt;
	}

	/**
	 * @return the paymentDt
	 */
	public String getPaymentDt() {
		return this.paymentDt;
	}

	/**
	 * @param paymentDt
	 *            the paymentDt to set
	 */
	public void setPaymentDt(String paymentDt) {
		this.paymentDt = paymentDt;
	}

	/**
	 * @return the apprNo
	 */
	public String getApprNo() {
		return this.apprNo;
	}

	/**
	 * @param apprNo
	 *            the apprNo to set
	 */
	public void setApprNo(String apprNo) {
		this.apprNo = apprNo;
	}

	/**
	 * @return the billKey
	 */
	public String getBillKey() {
		return this.billKey;
	}

	/**
	 * @param billKey
	 *            the billKey to set
	 */
	public void setBillKey(String billKey) {
		this.billKey = billKey;
	}

	/**
	 * @return the cpnId
	 */
	public String getCpnId() {
		return this.cpnId;
	}

	/**
	 * @param cpnId
	 *            the cpnId to set
	 */
	public void setCpnId(String cpnId) {
		this.cpnId = cpnId;
	}

	/**
	 * @return the cpnType
	 */
	public String getCpnType() {
		return this.cpnType;
	}

	/**
	 * @param cpnType
	 *            the cpnType to set
	 */
	public void setCpnType(String cpnType) {
		this.cpnType = cpnType;
	}

	/**
	 * @return the cpnMakeHost
	 */
	public String getCpnMakeHost() {
		return this.cpnMakeHost;
	}

	/**
	 * @param cpnMakeHost
	 *            the cpnMakeHost to set
	 */
	public void setCpnMakeHost(String cpnMakeHost) {
		this.cpnMakeHost = cpnMakeHost;
	}

	/**
	 * @return the ocbType
	 */
	public String getOcbType() {
		return this.ocbType;
	}

	/**
	 * @param ocbType
	 *            the ocbType to set
	 */
	public void setOcbType(String ocbType) {
		this.ocbType = ocbType;
	}

	/**
	 * @return the moid
	 */
	public String getMoid() {
		return this.moid;
	}

	/**
	 * @param moid
	 *            the moid to set
	 */
	public void setMoid(String moid) {
		this.moid = moid;
	}

	/**
	 * @return the sktTestDeviceYn
	 */
	public String getSktTestDeviceYn() {
		return this.sktTestDeviceYn;
	}

	/**
	 * @param sktTestDeviceYn
	 *            the sktTestDeviceYn to set
	 */
	public void setSktTestDeviceYn(String sktTestDeviceYn) {
		this.sktTestDeviceYn = sktTestDeviceYn;
	}

	/**
	 * @return the mnoCd
	 */
	public String getMnoCd() {
		return this.mnoCd;
	}

	/**
	 * @param mnoCd
	 *            the mnoCd to set
	 */
	public void setMnoCd(String mnoCd) {
		this.mnoCd = mnoCd;
	}

	/**
	 * @return the limitMemberYn
	 */
	public String getLimitMemberYn() {
		return this.limitMemberYn;
	}

	/**
	 * @param limitMemberYn
	 *            the limitMemberYn to set
	 */
	public void setLimitMemberYn(String limitMemberYn) {
		this.limitMemberYn = limitMemberYn;
	}

}
