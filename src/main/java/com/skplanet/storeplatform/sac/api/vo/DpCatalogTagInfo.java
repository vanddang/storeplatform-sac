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
 * 전처리 카탈로그 태그 Value Object
 * </pre>
 *
 * Created on : 2014-01-02
 * Created by : 김형식, SK플래닛
 * Last Updated on : 2014-01-02
 * Last Updated by : 김형식, SK플래닛
 */
public class DpCatalogTagInfo {

	private long tagSeq; //
	private String cid; //
	private String tagTypeCd; //
	private String tagCd; //
	private String tagNm; //
	private String regId; //
	private String regDt; //
	private String updId; //
	private String updDt; //

	public long getTagSeq() {
		return this.tagSeq;
	}

	public void setTagSeq(long tagSeq) {
		this.tagSeq = tagSeq;
	}

	public String getCid() {
		return this.cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getTagTypeCd() {
		return this.tagTypeCd;
	}

	public void setTagTypeCd(String tagTypeCd) {
		this.tagTypeCd = tagTypeCd;
	}

	public String getTagCd() {
		return this.tagCd;
	}

	public void setTagCd(String tagCd) {
		this.tagCd = tagCd;
	}

	public String getTagNm() {
		return this.tagNm;
	}

	public void setTagNm(String tagNm) {
		this.tagNm = tagNm;
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
