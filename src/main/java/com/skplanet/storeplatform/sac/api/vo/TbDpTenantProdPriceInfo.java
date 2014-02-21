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

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * <pre>
 * 전처리 상품 테넌트 상품 가격 Value Object.
 * </pre>
 * 
 * Created on : 2014-01-02 Created by : 김형식, SK플래닛 Last Updated on : 2014-01-02 Last Updated by : 김형식, SK플래닛
 */
public class TbDpTenantProdPriceInfo extends CommonInfo {
	private static final long serialVersionUID = 1L;
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
	private String cudType; // CUD

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
	 * @return the applyStartDt
	 */
	public String getApplyStartDt() {
		return this.applyStartDt;
	}

	/**
	 * @param applyStartDt
	 *            the applyStartDt to set
	 */
	public void setApplyStartDt(String applyStartDt) {
		this.applyStartDt = applyStartDt;
	}

	/**
	 * @return the seq
	 */
	public long getSeq() {
		return this.seq;
	}

	/**
	 * @param seq
	 *            the seq to set
	 */
	public void setSeq(long seq) {
		this.seq = seq;
	}

	/**
	 * @return the applyEndDt
	 */
	public String getApplyEndDt() {
		return this.applyEndDt;
	}

	/**
	 * @param applyEndDt
	 *            the applyEndDt to set
	 */
	public void setApplyEndDt(String applyEndDt) {
		this.applyEndDt = applyEndDt;
	}

	/**
	 * @return the prodAmt
	 */
	public long getProdAmt() {
		return this.prodAmt;
	}

	/**
	 * @param prodAmt
	 *            the prodAmt to set
	 */
	public void setProdAmt(long prodAmt) {
		this.prodAmt = prodAmt;
	}

	/**
	 * @return the chnlUnlmtAmt
	 */
	public long getChnlUnlmtAmt() {
		return this.chnlUnlmtAmt;
	}

	/**
	 * @param chnlUnlmtAmt
	 *            the chnlUnlmtAmt to set
	 */
	public void setChnlUnlmtAmt(long chnlUnlmtAmt) {
		this.chnlUnlmtAmt = chnlUnlmtAmt;
	}

	/**
	 * @return the chnlPeriodAmt
	 */
	public long getChnlPeriodAmt() {
		return this.chnlPeriodAmt;
	}

	/**
	 * @param chnlPeriodAmt
	 *            the chnlPeriodAmt to set
	 */
	public void setChnlPeriodAmt(long chnlPeriodAmt) {
		this.chnlPeriodAmt = chnlPeriodAmt;
	}

	/**
	 * @return the prodNetAmt
	 */
	public long getProdNetAmt() {
		return this.prodNetAmt;
	}

	/**
	 * @param prodNetAmt
	 *            the prodNetAmt to set
	 */
	public void setProdNetAmt(long prodNetAmt) {
		this.prodNetAmt = prodNetAmt;
	}

	/**
	 * @return the dcRate
	 */
	public long getDcRate() {
		return this.dcRate;
	}

	/**
	 * @param dcRate
	 *            the dcRate to set
	 */
	public void setDcRate(long dcRate) {
		this.dcRate = dcRate;
	}

	/**
	 * @return the dcAmt
	 */
	public long getDcAmt() {
		return this.dcAmt;
	}

	/**
	 * @param dcAmt
	 *            the dcAmt to set
	 */
	public void setDcAmt(long dcAmt) {
		this.dcAmt = dcAmt;
	}

	/**
	 * @return the taxClsf
	 */
	public String getTaxClsf() {
		return this.taxClsf;
	}

	/**
	 * @param taxClsf
	 *            the taxClsf to set
	 */
	public void setTaxClsf(String taxClsf) {
		this.taxClsf = taxClsf;
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
	 * @return the cudType
	 */
	public String getCudType() {
		return this.cudType;
	}

	/**
	 * @param cudType
	 *            the cudType to set
	 */
	public void setCudType(String cudType) {
		this.cudType = cudType;
	}

}
