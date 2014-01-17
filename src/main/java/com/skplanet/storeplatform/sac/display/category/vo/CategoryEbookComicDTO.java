/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.category.vo;

/**
 * 일반 카테고리 ebook/만화 상품 조회 DTO Value Object.
 * 
 * Updated on : 2014. 01. 06. Updated by : 이태희, SK 플래닛.
 */
public class CategoryEbookComicDTO {
	private int totalCount;
	private String upMenuId;
	private String menuId;
	private String menuNm;
	private String prodId;
	private String aid;
	private String prodNm;
	private String prodBaseDesc;
	private String prodAmt;
	private String prodGrdCd;
	private String partParentClsfCd;
	private String drmYn;
	private String prodVer;
	private String apkPkgNm;
	private String apkVer;
	private Integer apkFileSize;
	private String imgPath;
	private Integer paticpersCnt;
	private Integer prchsCnt;
	private Double avgEvluScore;
	private String upMenuNm;

	public int getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public String getUpMenuId() {
		return this.upMenuId;
	}

	public void setUpMenuId(String upMenuId) {
		this.upMenuId = upMenuId;
	}

	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuNm() {
		return this.menuNm;
	}

	public void setMenuNm(String menuNm) {
		this.menuNm = menuNm;
	}

	public String getProdId() {
		return this.prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getAid() {
		return this.aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getProdNm() {
		return this.prodNm;
	}

	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}

	public String getProdBaseDesc() {
		return this.prodBaseDesc;
	}

	public void setProdBaseDesc(String prodBaseDesc) {
		this.prodBaseDesc = prodBaseDesc;
	}

	public String getProdAmt() {
		return this.prodAmt;
	}

	public void setProdAmt(String prodAmt) {
		this.prodAmt = prodAmt;
	}

	public String getProdGrdCd() {
		return this.prodGrdCd;
	}

	public void setProdGrdCd(String prodGrdCd) {
		this.prodGrdCd = prodGrdCd;
	}

	public String getPartParentClsfCd() {
		return this.partParentClsfCd;
	}

	public void setPartParentClsfCd(String partParentClsfCd) {
		this.partParentClsfCd = partParentClsfCd;
	}

	public String getDrmYn() {
		return this.drmYn;
	}

	public void setDrmYn(String drmYn) {
		this.drmYn = drmYn;
	}

	public String getProdVer() {
		return this.prodVer;
	}

	public void setProdVer(String prodVer) {
		this.prodVer = prodVer;
	}

	public String getApkPkgNm() {
		return this.apkPkgNm;
	}

	public void setApkPkgNm(String apkPkgNm) {
		this.apkPkgNm = apkPkgNm;
	}

	public String getApkVer() {
		return this.apkVer;
	}

	public void setApkVer(String apkVer) {
		this.apkVer = apkVer;
	}

	public String getImgPath() {
		return this.imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public Integer getApkFileSize() {
		return this.apkFileSize;
	}

	public void setApkFileSize(Integer apkFileSize) {
		this.apkFileSize = apkFileSize;
	}

	public Integer getPaticpersCnt() {
		return this.paticpersCnt;
	}

	public void setPaticpersCnt(Integer paticpersCnt) {
		this.paticpersCnt = paticpersCnt;
	}

	public Integer getPrchsCnt() {
		return this.prchsCnt;
	}

	public void setPrchsCnt(Integer prchsCnt) {
		this.prchsCnt = prchsCnt;
	}

	public Double getAvgEvluScore() {
		return this.avgEvluScore;
	}

	public void setAvgEvluScore(Double avgEvluScore) {
		this.avgEvluScore = avgEvluScore;
	}

	public String getUpMenuNm() {
		return this.upMenuNm;
	}

	public void setUpMenuNm(String upMenuNm) {
		this.upMenuNm = upMenuNm;
	}
}
