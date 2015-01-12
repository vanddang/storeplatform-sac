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
 * 공통코드 VO
 * 
 * Updated on : 2014. 12. 3. Updated by : 이승택, nTels.
 */
public class PurchaseCommonCode extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/** 코드. */
	private String cdId;
	/** 언어코드. */
	private String langCd;
	/** 그룹코드. */
	private String grpCdId;
	/** 상세코드. */
	private String dtlCd;
	/** 코드이름. */
	private String cdNm;
	/** 추가필드01. */
	private String addField01;
	/** 추가필드02. */
	private String addField02;
	/** 설명. */
	private String cdDesc;
	/** 사용유무. */
	private String useYn;
	/** Rank. */
	private Integer indcRank;
	/** 등록자. */
	private String regId;
	/** 등록일자. */
	private String regDt;
	/** 수정자. */
	private String updId;
	/** 수정일자. */
	private String updDt;

	/**
	 * @return the cdId
	 */
	public String getCdId() {
		return this.cdId;
	}

	/**
	 * @param cdId
	 *            the cdId to set
	 */
	public void setCdId(String cdId) {
		this.cdId = cdId;
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
	 * @return the grpCdId
	 */
	public String getGrpCdId() {
		return this.grpCdId;
	}

	/**
	 * @param grpCdId
	 *            the grpCdId to set
	 */
	public void setGrpCdId(String grpCdId) {
		this.grpCdId = grpCdId;
	}

	/**
	 * @return the dtlCd
	 */
	public String getDtlCd() {
		return this.dtlCd;
	}

	/**
	 * @param dtlCd
	 *            the dtlCd to set
	 */
	public void setDtlCd(String dtlCd) {
		this.dtlCd = dtlCd;
	}

	/**
	 * @return the cdNm
	 */
	public String getCdNm() {
		return this.cdNm;
	}

	/**
	 * @param cdNm
	 *            the cdNm to set
	 */
	public void setCdNm(String cdNm) {
		this.cdNm = cdNm;
	}

	/**
	 * @return the addField01
	 */
	public String getAddField01() {
		return this.addField01;
	}

	/**
	 * @param addField01
	 *            the addField01 to set
	 */
	public void setAddField01(String addField01) {
		this.addField01 = addField01;
	}

	/**
	 * @return the addField02
	 */
	public String getAddField02() {
		return this.addField02;
	}

	/**
	 * @param addField02
	 *            the addField02 to set
	 */
	public void setAddField02(String addField02) {
		this.addField02 = addField02;
	}

	/**
	 * @return the cdDesc
	 */
	public String getCdDesc() {
		return this.cdDesc;
	}

	/**
	 * @param cdDesc
	 *            the cdDesc to set
	 */
	public void setCdDesc(String cdDesc) {
		this.cdDesc = cdDesc;
	}

	/**
	 * @return the useYn
	 */
	public String getUseYn() {
		return this.useYn;
	}

	/**
	 * @param useYn
	 *            the useYn to set
	 */
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	/**
	 * @return the indcRank
	 */
	public Integer getIndcRank() {
		return this.indcRank;
	}

	/**
	 * @param indcRank
	 *            the indcRank to set
	 */
	public void setIndcRank(Integer indcRank) {
		this.indcRank = indcRank;
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
