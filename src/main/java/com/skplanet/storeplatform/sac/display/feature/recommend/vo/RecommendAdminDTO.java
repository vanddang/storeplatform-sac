/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.feature.recommend.vo;

/**
 * 
 * 
 * Updated on : 2014. 1. 6. Updated by : 서영배, GTSOFT.
 */
public class RecommendAdminDTO {

	private int totalCount;
	private Integer dwldCnt;
	private Integer prchsCnt;
	private String topMenuId;
	private String menuId;
	private String topMenuNm;
	private String menuNm;
	private String prodId;
	private String aid;
	private String prodNm;
	private String prodBaseDesc;
	private Integer prodAmt;
	private String prodGrdCd;
	private String partParentClsfCd;
	private String drmYn;
	private String apkPkgNm;
	private String apkVer;
	private Integer fileSize;
	private String prodVer;
	private String filePath;
	private String verMajor;
	private String verMinor;
	private String fileNm;
	private Double avgEvluScore;

	public int getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public String getTopMenuId() {
		return this.topMenuId;
	}

	public Integer getDwldCnt() {
		return this.dwldCnt;
	}

	public void setDwldCnt(Integer dwldCnt) {
		this.dwldCnt = dwldCnt;
	}

	public Integer getPrchsCnt() {
		return this.prchsCnt;
	}

	public void setPrchsCnt(Integer prchsCnt) {
		this.prchsCnt = prchsCnt;
	}

	public void setTopMenuId(String topMenuId) {
		this.topMenuId = topMenuId;
	}

	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
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

	public Integer getProdAmt() {
		return this.prodAmt;
	}

	public void setProdAmt(Integer prodAmt) {
		this.prodAmt = prodAmt;
	}

	public Integer getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}

	public String getProdVer() {
		return this.prodVer;
	}

	public void setProdVer(String prodVer) {
		this.prodVer = prodVer;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getTopMenuNm() {
		return this.topMenuNm;
	}

	public void setTopMenuNm(String topMenuNm) {
		this.topMenuNm = topMenuNm;
	}

	public String getMenuNm() {
		return this.menuNm;
	}

	public void setMenuNm(String menuNm) {
		this.menuNm = menuNm;
	}

	public String getVerMajor() {
		return this.verMajor;
	}

	public void setVerMajor(String verMajor) {
		this.verMajor = verMajor;
	}

	public String getVerMinor() {
		return this.verMinor;
	}

	public void setVerMinor(String verMinor) {
		this.verMinor = verMinor;
	}

	public String getFileNm() {
		return this.fileNm;
	}

	public void setFileNm(String fileNm) {
		this.fileNm = fileNm;
	}

	public Double getAvgEvluScore() {
		return this.avgEvluScore;
	}

	public void setAvgEvluScore(Double avgEvluScore) {
		this.avgEvluScore = avgEvluScore;
	}

}
