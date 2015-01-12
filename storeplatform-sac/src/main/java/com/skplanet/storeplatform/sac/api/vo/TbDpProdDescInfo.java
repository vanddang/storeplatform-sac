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
 * 전처리 상품상태 정보 Value Object.
 * </pre>
 * 
 * Created on : 2014-01-02 Created by : 김형식, SK플래닛 Last Updated on : 2014-01-02 Last Updated by : 김형식, SK플래닛
 */
public class TbDpProdDescInfo extends CommonInfo {
	private static final long serialVersionUID = 1L;
	private String prodId; //
	private String langCd; //
	private String prodNm; //
	private String prodAlias; //
	private String prodBaseDesc; //
	private String prodDtlDesc; //
	private String prodIntrDscr; //
	private String prodUseMtd; //
	private String artist1Nm; //
	private String artist2Nm; //
	private String artist3Nm; //
	private String artist1Id; //
	private String artist2Id; //
	private String artist3Id; //
	private String regId; //
	private String regDt; //
	private String updId; //
	private String updDt; //
	private String cudType; // CUD

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
	 * @return the langCd
	 */
	public String getLangCd() {
		return this.langCd;
	}

	/**
	 * @param langCd
	 *            the langCd to set
	 */
	public void setLangCd(String langCd) {
		this.langCd = langCd;
	}

	/**
	 * @return the prodNm
	 */
	public String getProdNm() {
		return this.prodNm;
	}

	/**
	 * @param prodNm
	 *            the prodNm to set
	 */
	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}

	/**
	 * @return the prodAlias
	 */
	public String getProdAlias() {
		return this.prodAlias;
	}

	/**
	 * @param prodAlias
	 *            the prodAlias to set
	 */
	public void setProdAlias(String prodAlias) {
		this.prodAlias = prodAlias;
	}

	/**
	 * @return the prodBaseDesc
	 */
	public String getProdBaseDesc() {
		return this.prodBaseDesc;
	}

	/**
	 * @param prodBaseDesc
	 *            the prodBaseDesc to set
	 */
	public void setProdBaseDesc(String prodBaseDesc) {
		this.prodBaseDesc = prodBaseDesc;
	}

	/**
	 * @return the prodDtlDesc
	 */
	public String getProdDtlDesc() {
		return this.prodDtlDesc;
	}

	/**
	 * @param prodDtlDesc
	 *            the prodDtlDesc to set
	 */
	public void setProdDtlDesc(String prodDtlDesc) {
		this.prodDtlDesc = prodDtlDesc;
	}

	/**
	 * @return the prodIntrDscr
	 */
	public String getProdIntrDscr() {
		return this.prodIntrDscr;
	}

	/**
	 * @param prodIntrDscr
	 *            the prodIntrDscr to set
	 */
	public void setProdIntrDscr(String prodIntrDscr) {
		this.prodIntrDscr = prodIntrDscr;
	}

	/**
	 * @return the prodUseMtd
	 */
	public String getProdUseMtd() {
		return this.prodUseMtd;
	}

	/**
	 * @param prodUseMtd
	 *            the prodUseMtd to set
	 */
	public void setProdUseMtd(String prodUseMtd) {
		this.prodUseMtd = prodUseMtd;
	}

	/**
	 * @return the artist1Nm
	 */
	public String getArtist1Nm() {
		return this.artist1Nm;
	}

	/**
	 * @param artist1Nm
	 *            the artist1Nm to set
	 */
	public void setArtist1Nm(String artist1Nm) {
		this.artist1Nm = artist1Nm;
	}

	/**
	 * @return the artist2Nm
	 */
	public String getArtist2Nm() {
		return this.artist2Nm;
	}

	/**
	 * @param artist2Nm
	 *            the artist2Nm to set
	 */
	public void setArtist2Nm(String artist2Nm) {
		this.artist2Nm = artist2Nm;
	}

	/**
	 * @return the artist3Nm
	 */
	public String getArtist3Nm() {
		return this.artist3Nm;
	}

	/**
	 * @param artist3Nm
	 *            the artist3Nm to set
	 */
	public void setArtist3Nm(String artist3Nm) {
		this.artist3Nm = artist3Nm;
	}

	/**
	 * @return the artist1Id
	 */
	public String getArtist1Id() {
		return this.artist1Id;
	}

	/**
	 * @param artist1Id
	 *            the artist1Id to set
	 */
	public void setArtist1Id(String artist1Id) {
		this.artist1Id = artist1Id;
	}

	/**
	 * @return the artist2Id
	 */
	public String getArtist2Id() {
		return this.artist2Id;
	}

	/**
	 * @param artist2Id
	 *            the artist2Id to set
	 */
	public void setArtist2Id(String artist2Id) {
		this.artist2Id = artist2Id;
	}

	/**
	 * @return the artist3Id
	 */
	public String getArtist3Id() {
		return this.artist3Id;
	}

	/**
	 * @param artist3Id
	 *            the artist3Id to set
	 */
	public void setArtist3Id(String artist3Id) {
		this.artist3Id = artist3Id;
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
