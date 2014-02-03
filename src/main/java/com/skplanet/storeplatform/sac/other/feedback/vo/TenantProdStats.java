/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.other.feedback.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * TenantProdStats Value Object
 * 
 * Updated on : 2014. 1. 24. Updated by : 김현일, 인크로스
 */
public class TenantProdStats extends CommonInfo {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String tenantId;
	private String prodId;
	private int strmCnt;
	private String strmClientNm;
	private int dwldCnt;
	private int prchsCnt;
	private int avgEvluScore;
	private int totEvluScore;
	private int paticpersCnt;
	private String regId;
	private String regDt;
	private String updId;
	private String updDt;

	/**
	 * @return String
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * @param tenantId
	 *            tenantId
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return String
	 */
	public String getProdId() {
		return this.prodId;
	}

	/**
	 * @param prodId
	 *            prodId
	 */
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	/**
	 * @return int
	 */
	public int getStrmCnt() {
		return this.strmCnt;
	}

	/**
	 * @param strmCnt
	 *            strmCnt
	 */
	public void setStrmCnt(int strmCnt) {
		this.strmCnt = strmCnt;
	}

	/**
	 * @return String
	 */
	public String getStrmClientNm() {
		return this.strmClientNm;
	}

	/**
	 * @param strmClientNm
	 *            strmClientNm
	 */
	public void setStrmClientNm(String strmClientNm) {
		this.strmClientNm = strmClientNm;
	}

	/**
	 * @return int
	 */
	public int getDwldCnt() {
		return this.dwldCnt;
	}

	/**
	 * @param dwldCnt
	 *            dwldCnt
	 */
	public void setDwldCnt(int dwldCnt) {
		this.dwldCnt = dwldCnt;
	}

	/**
	 * @return int
	 */
	public int getPrchsCnt() {
		return this.prchsCnt;
	}

	/**
	 * @param prchsCnt
	 *            prchsCnt
	 */
	public void setPrchsCnt(int prchsCnt) {
		this.prchsCnt = prchsCnt;
	}

	/**
	 * @return int
	 */
	public int getAvgEvluScore() {
		return this.avgEvluScore;
	}

	/**
	 * @param avgEvluScore
	 *            avgEvluScore
	 */
	public void setAvgEvluScore(int avgEvluScore) {
		this.avgEvluScore = avgEvluScore;
	}

	/**
	 * @return int
	 */
	public int getTotEvluScore() {
		return this.totEvluScore;
	}

	/**
	 * @param totEvluScore
	 *            totEvluScore
	 */
	public void setTotEvluScore(int totEvluScore) {
		this.totEvluScore = totEvluScore;
	}

	/**
	 * @return int
	 */
	public int getPaticpersCnt() {
		return this.paticpersCnt;
	}

	/**
	 * @param paticpersCnt
	 *            paticpersCnt
	 */
	public void setPaticpersCnt(int paticpersCnt) {
		this.paticpersCnt = paticpersCnt;
	}

	/**
	 * @return String
	 */
	public String getRegId() {
		return this.regId;
	}

	/**
	 * @param regId
	 *            regId
	 */
	public void setRegId(String regId) {
		this.regId = regId;
	}

	/**
	 * @return String
	 */
	public String getRegDt() {
		return this.regDt;
	}

	/**
	 * @param regDt
	 *            regDt
	 */
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	/**
	 * @return String
	 */
	public String getUpdId() {
		return this.updId;
	}

	/**
	 * @param updId
	 *            updId
	 */
	public void setUpdId(String updId) {
		this.updId = updId;
	}

	/**
	 * @return String
	 */
	public String getUpdDt() {
		return this.updDt;
	}

	/**
	 * @param updDt
	 *            updDt
	 */
	public void setUpdDt(String updDt) {
		this.updDt = updDt;
	}

}
