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
/**
 * <pre>
 * 전처리 상품 테넌트 상품 가격 Value Object
 * </pre>
 *
 * Created on : 2014-01-02
 * Created by : 김형식, SK플래닛
 * Last Updated on : 2014-01-02
 * Last Updated by : 김형식, SK플래닛
 */
public class TbDpTenantProdPriceInfo {
	private String tenantId; // 테넌트_ID
	private String prodId; // 상품_ID
	private String applyStartDt; // 적용_시작_일시
	private long seq; // SEQ
	private String applyEndDt; // 적용_종료_일시
	private long prodAmt; // 상품_금액
	private long chnlUnlmtAmt; // 채널_무제한_금액
	private long chnlPeriodAmt; // 채널_기간_금액
	private long prodNetAmt; // 상품_정찰_금액
	private long dcRate; // 할인_율
	private long dcAmt; // 할인_금액
	private String taxClsf; // 세금_구분
	private String regId; // 등록_ID
	private String regDt; // 등록_일시
	private String updId; // 수정_ID
	private String updDt; // 수정_일시

	public String getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getProdId() {
		return this.prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
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

	public String getTaxClsf() {
		return this.taxClsf;
	}

	public void setTaxClsf(String taxClsf) {
		this.taxClsf = taxClsf;
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

	public String getUpdId() {
		return this.updId;
	}

	public void setUpdId(String updId) {
		this.updId = updId;
	}

	public String getUpdDt() {
		return this.updDt;
	}

	public void setUpdDt(String updDt) {
		this.updDt = updDt;
	}

}
