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

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Class 설명
 * 
 * Updated on : 2014. 3. 17. Updated by : nTels_cswoo81, nTels.
 */
public class PaymentSacParam extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String tenantId;
	private String prchsId;
	private Integer paymentDtlId;
	private String insdUsermbrNo;
	private String insdDeviceId;
	private String prchsDt;
	private Double totAmt;
	private String paymentMtdCd;
	private String statusCd;
	private String paymentDt;
	private Double paymentAmt;
	private String paymentCancelDt;
	private String tid;
	private String regId;
	private String regDt;
	private String updId;
	private String updDt;
	private String resvCol01;
	private String resvCol02;
	private String resvCol03;
	private String resvCol04;
	private String resvCol05;
	private String apprNo;
	private String billKey;
	private String cpnId;
	private String moid;

	private String mid;
	private String authKey;

	private String cpnType;

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
	 * @return the mid
	 */
	public String getMid() {
		return this.mid;
	}

	/**
	 * @param mid
	 *            the mid to set
	 */
	public void setMid(String mid) {
		this.mid = mid;
	}

	/**
	 * @return the authKey
	 */
	public String getAuthKey() {
		return this.authKey;
	}

	/**
	 * @param authKey
	 *            the authKey to set
	 */
	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
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
	 * @return the paymentDtlId
	 */
	public Integer getPaymentDtlId() {
		return this.paymentDtlId;
	}

	/**
	 * @param paymentDtlId
	 *            the paymentDtlId to set
	 */
	public void setPaymentDtlId(Integer paymentDtlId) {
		this.paymentDtlId = paymentDtlId;
	}

	/**
	 * @return the insdUsermbrNo
	 */
	public String getInsdUsermbrNo() {
		return this.insdUsermbrNo;
	}

	/**
	 * @param insdUsermbrNo
	 *            the insdUsermbrNo to set
	 */
	public void setInsdUsermbrNo(String insdUsermbrNo) {
		this.insdUsermbrNo = insdUsermbrNo;
	}

	/**
	 * @return the insdDeviceId
	 */
	public String getInsdDeviceId() {
		return this.insdDeviceId;
	}

	/**
	 * @param insdDeviceId
	 *            the insdDeviceId to set
	 */
	public void setInsdDeviceId(String insdDeviceId) {
		this.insdDeviceId = insdDeviceId;
	}

	/**
	 * @return the prchsDt
	 */
	public String getPrchsDt() {
		return this.prchsDt;
	}

	/**
	 * @param prchsDt
	 *            the prchsDt to set
	 */
	public void setPrchsDt(String prchsDt) {
		this.prchsDt = prchsDt;
	}

	/**
	 * @return the totAmt
	 */
	public Double getTotAmt() {
		return this.totAmt;
	}

	/**
	 * @param totAmt
	 *            the totAmt to set
	 */
	public void setTotAmt(Double totAmt) {
		this.totAmt = totAmt;
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
	 * @return the statusCd
	 */
	public String getStatusCd() {
		return this.statusCd;
	}

	/**
	 * @param statusCd
	 *            the statusCd to set
	 */
	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
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
	 * @return the paymentAmt
	 */
	public Double getPaymentAmt() {
		return this.paymentAmt;
	}

	/**
	 * @param paymentAmt
	 *            the paymentAmt to set
	 */
	public void setPaymentAmt(Double paymentAmt) {
		this.paymentAmt = paymentAmt;
	}

	/**
	 * @return the paymentCancelDt
	 */
	public String getPaymentCancelDt() {
		return this.paymentCancelDt;
	}

	/**
	 * @param paymentCancelDt
	 *            the paymentCancelDt to set
	 */
	public void setPaymentCancelDt(String paymentCancelDt) {
		this.paymentCancelDt = paymentCancelDt;
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
	 * @return the regId
	 */
	public String getRegId() {
		return this.regId;
	}

	/**
	 * @param regId
	 *            the regId to set
	 */
	public void setRegId(String regId) {
		this.regId = regId;
	}

	/**
	 * @return the regDt
	 */
	public String getRegDt() {
		return this.regDt;
	}

	/**
	 * @param regDt
	 *            the regDt to set
	 */
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	/**
	 * @return the updId
	 */
	public String getUpdId() {
		return this.updId;
	}

	/**
	 * @param updId
	 *            the updId to set
	 */
	public void setUpdId(String updId) {
		this.updId = updId;
	}

	/**
	 * @return the updDt
	 */
	public String getUpdDt() {
		return this.updDt;
	}

	/**
	 * @param updDt
	 *            the updDt to set
	 */
	public void setUpdDt(String updDt) {
		this.updDt = updDt;
	}

	/**
	 * @return the resvCol01
	 */
	public String getResvCol01() {
		return this.resvCol01;
	}

	/**
	 * @param resvCol01
	 *            the resvCol01 to set
	 */
	public void setResvCol01(String resvCol01) {
		this.resvCol01 = resvCol01;
	}

	/**
	 * @return the resvCol02
	 */
	public String getResvCol02() {
		return this.resvCol02;
	}

	/**
	 * @param resvCol02
	 *            the resvCol02 to set
	 */
	public void setResvCol02(String resvCol02) {
		this.resvCol02 = resvCol02;
	}

	/**
	 * @return the resvCol03
	 */
	public String getResvCol03() {
		return this.resvCol03;
	}

	/**
	 * @param resvCol03
	 *            the resvCol03 to set
	 */
	public void setResvCol03(String resvCol03) {
		this.resvCol03 = resvCol03;
	}

	/**
	 * @return the resvCol04
	 */
	public String getResvCol04() {
		return this.resvCol04;
	}

	/**
	 * @param resvCol04
	 *            the resvCol04 to set
	 */
	public void setResvCol04(String resvCol04) {
		this.resvCol04 = resvCol04;
	}

	/**
	 * @return the resvCol05
	 */
	public String getResvCol05() {
		return this.resvCol05;
	}

	/**
	 * @param resvCol05
	 *            the resvCol05 to set
	 */
	public void setResvCol05(String resvCol05) {
		this.resvCol05 = resvCol05;
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

}
