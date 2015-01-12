/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.common.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 결제수단 프로모션 VO
 * 
 * Updated on : 2014. 12. 9. Updated by : 이승택, nTels.
 */
public class PaymentPromotion extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private String tenantId;

	private Integer promSeq;
	private String paymentMtdCd;
	private String promNm;
	private String promDesc;
	private String promUrl;
	private String startDt;
	private String endDt;
	private String regId;
	private String regDt;
	private String updId;
	private String updDt;

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
	 * @return the promSeq
	 */
	public Integer getPromSeq() {
		return this.promSeq;
	}

	/**
	 * @param promSeq
	 *            the promSeq to set
	 */
	public void setPromSeq(Integer promSeq) {
		this.promSeq = promSeq;
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
	 * @return the promNm
	 */
	public String getPromNm() {
		return this.promNm;
	}

	/**
	 * @param promNm
	 *            the promNm to set
	 */
	public void setPromNm(String promNm) {
		this.promNm = promNm;
	}

	/**
	 * @return the promDesc
	 */
	public String getPromDesc() {
		return this.promDesc;
	}

	/**
	 * @param promDesc
	 *            the promDesc to set
	 */
	public void setPromDesc(String promDesc) {
		this.promDesc = promDesc;
	}

	/**
	 * @return the promUrl
	 */
	public String getPromUrl() {
		return this.promUrl;
	}

	/**
	 * @param promUrl
	 *            the promUrl to set
	 */
	public void setPromUrl(String promUrl) {
		this.promUrl = promUrl;
	}

	/**
	 * @return the startDt
	 */
	public String getStartDt() {
		return this.startDt;
	}

	/**
	 * @param startDt
	 *            the startDt to set
	 */
	public void setStartDt(String startDt) {
		this.startDt = startDt;
	}

	/**
	 * @return the endDt
	 */
	public String getEndDt() {
		return this.endDt;
	}

	/**
	 * @param endDt
	 *            the endDt to set
	 */
	public void setEndDt(String endDt) {
		this.endDt = endDt;
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

}
