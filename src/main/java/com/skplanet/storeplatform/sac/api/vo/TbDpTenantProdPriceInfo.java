/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.api.vo;

public class TbDpTenantProdPriceInfo {
	private String taxClsf; //
	private String prodId; //
	private String tenantId; //
	private String applyStartDt; //
	private long seq; //
	private String applyEndDt; //
	private long settRate; //
	private long prodAmt; //
	private long chnlUnlmtAmt; //
	private long chnlPeriodAmt; //
	private long prodNetAmt; //
	private long dcRate; //
	private long dcAmt; //
	private String regId; //
	private String regDt; //

	public String getTaxClsf() {
		return this.taxClsf;
	}

	public void setTaxClsf(String taxClsf) {
		this.taxClsf = taxClsf;
	}

	public String getProdId() {
		return this.prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getApplyStartDt() {
		return this.applyStartDt;
	}

	public void setApplyStartDt(String applyStartDt) {
		this.applyStartDt = applyStartDt;
	}

	public long getSeq() {
		return this.seq;
	}

	public void setSeq(long seq) {
		this.seq = seq;
	}

	public String getApplyEndDt() {
		return this.applyEndDt;
	}

	public void setApplyEndDt(String applyEndDt) {
		this.applyEndDt = applyEndDt;
	}

	public long getSettRate() {
		return this.settRate;
	}

	public void setSettRate(long settRate) {
		this.settRate = settRate;
	}

	public long getProdAmt() {
		return this.prodAmt;
	}

	public void setProdAmt(long prodAmt) {
		this.prodAmt = prodAmt;
	}

	public long getChnlUnlmtAmt() {
		return this.chnlUnlmtAmt;
	}

	public void setChnlUnlmtAmt(long chnlUnlmtAmt) {
		this.chnlUnlmtAmt = chnlUnlmtAmt;
	}

	public long getChnlPeriodAmt() {
		return this.chnlPeriodAmt;
	}

	public void setChnlPeriodAmt(long chnlPeriodAmt) {
		this.chnlPeriodAmt = chnlPeriodAmt;
	}

	public long getProdNetAmt() {
		return this.prodNetAmt;
	}

	public void setProdNetAmt(long prodNetAmt) {
		this.prodNetAmt = prodNetAmt;
	}

	public long getDcRate() {
		return this.dcRate;
	}

	public void setDcRate(long dcRate) {
		this.dcRate = dcRate;
	}

	public long getDcAmt() {
		return this.dcAmt;
	}

	public void setDcAmt(long dcAmt) {
		this.dcAmt = dcAmt;
	}

	public String getRegId() {
		return this.regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getRegDt() {
		return this.regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

}
