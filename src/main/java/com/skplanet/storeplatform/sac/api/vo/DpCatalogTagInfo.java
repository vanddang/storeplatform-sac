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
 * 전처리 카탈로그 태그 Value Object.
 * </pre>
 * 
 * Created on : 2014-01-02 Created by : 김형식, SK플래닛 Last Updated on : 2014-01-02 Last Updated by : 김형식, SK플래닛
 */
public class DpCatalogTagInfo extends CommonInfo {
	private static final long serialVersionUID = 1L;
	private long tagSeq; //
	private String cid; //
	private String tagTypeCd; //
	private String tagCd; //
	private String tagNm; //
	private String regId; //
	private String regDt; //
	private String updId; //
	private String updDt; //

	/**
	 * @return the tagSeq
	 */
	public long getTagSeq() {
		return this.tagSeq;
	}

	/**
	 * @param tagSeq
	 *            the tagSeq to set
	 */
	public void setTagSeq(long tagSeq) {
		this.tagSeq = tagSeq;
	}

	/**
	 * @return the cid
	 */
	public String getCid() {
		return this.cid;
	}

	/**
	 * @param cid
	 *            the cid to set
	 */
	public void setCid(String cid) {
		this.cid = cid;
	}

	/**
	 * @return the tagTypeCd
	 */
	public String getTagTypeCd() {
		return this.tagTypeCd;
	}

	/**
	 * @param tagTypeCd
	 *            the tagTypeCd to set
	 */
	public void setTagTypeCd(String tagTypeCd) {
		this.tagTypeCd = tagTypeCd;
	}

	/**
	 * @return the tagCd
	 */
	public String getTagCd() {
		return this.tagCd;
	}

	/**
	 * @param tagCd
	 *            the tagCd to set
	 */
	public void setTagCd(String tagCd) {
		this.tagCd = tagCd;
	}

	/**
	 * @return the tagNm
	 */
	public String getTagNm() {
		return this.tagNm;
	}

	/**
	 * @param tagNm
	 *            the tagNm to set
	 */
	public void setTagNm(String tagNm) {
		this.tagNm = tagNm;
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
