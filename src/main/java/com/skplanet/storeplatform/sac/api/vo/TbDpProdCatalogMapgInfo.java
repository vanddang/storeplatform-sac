package com.skplanet.storeplatform.sac.api.vo;

/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */

public class TbDpProdCatalogMapgInfo {
	private String prodId; // 상품_ID
	private String catalogId; // 카탈로그_ID
	private String baseYn; // 기본_여부
	private String useYn; // 사용_여부
	private String regId; // 등록_ID
	private String regDt; // 등록_일시
	private String updId; // 수정_ID
	private String updDt; // 수정_일시

	public String getProdId() {
		return this.prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getCatalogId() {
		return this.catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public String getBaseYn() {
		return this.baseYn;
	}

	public void setBaseYn(String baseYn) {
		this.baseYn = baseYn;
	}

	public String getUseYn() {
		return this.useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
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
