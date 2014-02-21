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
 * 일반 카테고리 앱 상품 조회 Value Object.
 * 
 * Updated on : 2014. 01. 06. Updated by : 이태희, SK 플래닛.
 */
public class CategoryApp {
	private Integer totalCount;
	private String topMenuId;
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
	private Integer imgSize;
	private Integer paticpersCnt;
	private Integer prchsCnt;
	private Double avgEvluScore;
	private String topMenuNm;

	/**
	 * @return the totalCount
	 */
	public int getTotalCount() {
		return this.totalCount;
	}

	/**
	 * @param totalCount
	 *            the totalCount to set
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @return the topMenuId
	 */
	public String getTopMenuId() {
		return this.topMenuId;
	}

	/**
	 * @param topMenuId
	 *            the topMenuId to set
	 */
	public void setTopMenuId(String topMenuId) {
		this.topMenuId = topMenuId;
	}

	/**
	 * @return the menuId
	 */
	public String getMenuId() {
		return this.menuId;
	}

	/**
	 * @param menuId
	 *            the menuId to set
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/**
	 * @return the menuNm
	 */
	public String getMenuNm() {
		return this.menuNm;
	}

	/**
	 * @param menuNm
	 *            the menuNm to set
	 */
	public void setMenuNm(String menuNm) {
		this.menuNm = menuNm;
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
	 * @return the aid
	 */
	public String getAid() {
		return this.aid;
	}

	/**
	 * @param aid
	 *            the aid to set
	 */
	public void setAid(String aid) {
		this.aid = aid;
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
	 * @return the prodAmt
	 */
	public String getProdAmt() {
		return this.prodAmt;
	}

	/**
	 * @param prodAmt
	 *            the prodAmt to set
	 */
	public void setProdAmt(String prodAmt) {
		this.prodAmt = prodAmt;
	}

	/**
	 * @return the prodGrdCd
	 */
	public String getProdGrdCd() {
		return this.prodGrdCd;
	}

	/**
	 * @param prodGrdCd
	 *            the prodGrdCd to set
	 */
	public void setProdGrdCd(String prodGrdCd) {
		this.prodGrdCd = prodGrdCd;
	}

	/**
	 * @return the partParentClsfCd
	 */
	public String getPartParentClsfCd() {
		return this.partParentClsfCd;
	}

	/**
	 * @param partParentClsfCd
	 *            the partParentClsfCd to set
	 */
	public void setPartParentClsfCd(String partParentClsfCd) {
		this.partParentClsfCd = partParentClsfCd;
	}

	/**
	 * @return the drmYn
	 */
	public String getDrmYn() {
		return this.drmYn;
	}

	/**
	 * @param drmYn
	 *            the drmYn to set
	 */
	public void setDrmYn(String drmYn) {
		this.drmYn = drmYn;
	}

	/**
	 * @return the prodVer
	 */
	public String getProdVer() {
		return this.prodVer;
	}

	/**
	 * @param prodVer
	 *            the prodVer to set
	 */
	public void setProdVer(String prodVer) {
		this.prodVer = prodVer;
	}

	/**
	 * @return the apkPkgNm
	 */
	public String getApkPkgNm() {
		return this.apkPkgNm;
	}

	/**
	 * @param apkPkgNm
	 *            the apkPkgNm to set
	 */
	public void setApkPkgNm(String apkPkgNm) {
		this.apkPkgNm = apkPkgNm;
	}

	/**
	 * @return the apkVer
	 */
	public String getApkVer() {
		return this.apkVer;
	}

	/**
	 * @param apkVer
	 *            the apkVer to set
	 */
	public void setApkVer(String apkVer) {
		this.apkVer = apkVer;
	}

	/**
	 * @return the apkFileSize
	 */
	public Integer getApkFileSize() {
		return this.apkFileSize;
	}

	/**
	 * @param apkFileSize
	 *            the apkFileSize to set
	 */
	public void setApkFileSize(Integer apkFileSize) {
		this.apkFileSize = apkFileSize;
	}

	/**
	 * @return the imgPath
	 */
	public String getImgPath() {
		return this.imgPath;
	}

	/**
	 * @param imgPath
	 *            the imgPath to set
	 */
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	/**
	 * @return the imgSize
	 */
	public Integer getImgSize() {
		return this.imgSize;
	}

	/**
	 * @param imgSize
	 *            the imgSize to set
	 */
	public void setImgSize(Integer imgSize) {
		this.imgSize = imgSize;
	}

	/**
	 * @return the paticpersCnt
	 */
	public Integer getPaticpersCnt() {
		return this.paticpersCnt;
	}

	/**
	 * @param paticpersCnt
	 *            the paticpersCnt to set
	 */
	public void setPaticpersCnt(Integer paticpersCnt) {
		this.paticpersCnt = paticpersCnt;
	}

	/**
	 * @return the prchsCnt
	 */
	public Integer getPrchsCnt() {
		return this.prchsCnt;
	}

	/**
	 * @param prchsCnt
	 *            the prchsCnt to set
	 */
	public void setPrchsCnt(Integer prchsCnt) {
		this.prchsCnt = prchsCnt;
	}

	/**
	 * @return the avgEvluScore
	 */
	public Double getAvgEvluScore() {
		return this.avgEvluScore;
	}

	/**
	 * @param avgEvluScore
	 *            the avgEvluScore to set
	 */
	public void setAvgEvluScore(Double avgEvluScore) {
		this.avgEvluScore = avgEvluScore;
	}

	/**
	 * @return the topMenuNm
	 */
	public String getTopMenuNm() {
		return this.topMenuNm;
	}

	/**
	 * @param topMenuNm
	 *            the topMenuNm to set
	 */
	public void setTopMenuNm(String topMenuNm) {
		this.topMenuNm = topMenuNm;
	}

}
