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

public class DpCatalogTagInfo {

	private String tagInfoSeq = ""; // 태그 정보 SEQUENCE
	private String cId = ""; // CID
	private String tagType = ""; // 태그 타입
	private String tagNm = ""; // 태그 코드
	private String insDttm = ""; // 태그 명
	private String insBy = "";
	private String updDttm = "";
	private String updBy = "";

	public String getTagInfoSeq() {
		return this.tagInfoSeq;
	}

	public void setTagInfoSeq(String tagInfoSeq) {
		this.tagInfoSeq = tagInfoSeq;
	}

	public String getCId() {
		return this.cId;
	}

	public void setCId(String id) {
		this.cId = id;
	}

	public String getTagType() {
		return this.tagType;
	}

	public void setTagType(String tagType) {
		this.tagType = tagType;
	}

	public String getTagNm() {
		return this.tagNm;
	}

	public void setTagNm(String tagNm) {
		this.tagNm = tagNm;
	}

	public String getInsDttm() {
		return this.insDttm;
	}

	public void setInsDttm(String insDttm) {
		this.insDttm = insDttm;
	}

	public String getInsBy() {
		return this.insBy;
	}

	public void setInsBy(String insBy) {
		this.insBy = insBy;
	}

	public String getUpdDttm() {
		return this.updDttm;
	}

	public void setUpdDttm(String updDttm) {
		this.updDttm = updDttm;
	}

	public String getUpdBy() {
		return this.updBy;
	}

	public void setUpdBy(String updBy) {
		this.updBy = updBy;
	}
}
