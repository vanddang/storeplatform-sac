/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.product.vo;

/**
 * Feature 상품 (영화, 방송) 카테고리 조회 DTO Value Object.
 * 
 * Updated on : 2013. 11. 28. Updated by : 이태희, SK 플래닛.
 */
public class FeatureVodProductDTO {
	private int totalCount;
	private String upMenuId;
	private String menuId;
	private String menuNm;
	private String metaClsfCd;
	private String prodId;
	private String prodBaseDesc;
	private String prodGrdCd;
	private String artist1Nm;
	private String artist2Nm;
	private String issueDay;
	private String chnlCompNm;
	private String agencyNm;
	private String hdvYn;
	private String dolbySprtYn;
	private String prchsQty;
	private String imgFilePath;
	private String avgEvluScore;
	private String paticpersCnt;
	private String prodAmt;
	private String prodNm;
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

	public String getMetaClsfCd() {
		return this.metaClsfCd;
	}

	public void setMetaClsfCd(String metaClsfCd) {
		this.metaClsfCd = metaClsfCd;
	}

	public String getProdId() {
		return this.prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
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

	public String getArtist1Nm() {
		return this.artist1Nm;
	}

	public void setArtist1Nm(String artist1Nm) {
		this.artist1Nm = artist1Nm;
	}

	public String getArtist2Nm() {
		return this.artist2Nm;
	}

	public void setArtist2Nm(String artist2Nm) {
		this.artist2Nm = artist2Nm;
	}

	public String getIssueDay() {
		return this.issueDay;
	}

	public void setIssueDay(String issueDay) {
		this.issueDay = issueDay;
	}

	public String getChnlCompNm() {
		return this.chnlCompNm;
	}

	public void setChnlCompNm(String chnlCompNm) {
		this.chnlCompNm = chnlCompNm;
	}

	public String getAgencyNm() {
		return this.agencyNm;
	}

	public void setAgencyNm(String agencyNm) {
		this.agencyNm = agencyNm;
	}

	public String getHdvYn() {
		return this.hdvYn;
	}

	public void setHdvYn(String hdvYn) {
		this.hdvYn = hdvYn;
	}

	public String getDolbySprtYn() {
		return this.dolbySprtYn;
	}

	public void setDolbySprtYn(String dolbySprtYn) {
		this.dolbySprtYn = dolbySprtYn;
	}

	public String getPrchsQty() {
		return this.prchsQty;
	}

	public void setPrchsQty(String prchsQty) {
		this.prchsQty = prchsQty;
	}

	public String getImgFilePath() {
		return this.imgFilePath;
	}

	public void setImgFilePath(String imgFilePath) {
		this.imgFilePath = imgFilePath;
	}

	public String getAvgEvluScore() {
		return this.avgEvluScore;
	}

	public void setAvgEvluScore(String avgEvluScore) {
		this.avgEvluScore = avgEvluScore;
	}

	public String getPaticpersCnt() {
		return this.paticpersCnt;
	}

	public void setPaticpersCnt(String paticpersCnt) {
		this.paticpersCnt = paticpersCnt;
	}

	public String getProdAmt() {
		return this.prodAmt;
	}

	public void setProdAmt(String prodAmt) {
		this.prodAmt = prodAmt;
	}

	public String getProdNm() {
		return this.prodNm;
	}

	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}

	public String getUpMenuNm() {
		return this.upMenuNm;
	}

	public void setUpMenuNm(String upMenuNm) {
		this.upMenuNm = upMenuNm;
	}
}
