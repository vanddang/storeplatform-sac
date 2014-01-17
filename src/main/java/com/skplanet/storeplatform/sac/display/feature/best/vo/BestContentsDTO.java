/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.feature.best.vo;

/**
 * BEST 컨텐츠 DTO Default Value Object.
 * 
 * Updated on : 2014. 1. 14. Updated by : 이석희, 아이에스플러스.
 */
public class BestContentsDTO {
	private int totalCount;
	private String upMenuId;
	private String topMenuId;
	private String menuId;
	private String menuNm;
	private String menuDesc;
	private String metaClsfCd;
	private String prodId;
	private String partProdId;
	private String vodTitlNm;
	private String prodNm;
	private String chapter;
	private String concatProdNm;
	private String prodDtlNm;
	private String prodBaseDesc;
	private String prodGrdCd;
	private String artist1Nm;
	private String artist2Nm;
	private String artist3Nm;
	private String issueDay;
	private String chnlCompNm;
	private String agencyNm;
	private String hdvYn;
	private String dolbySprtYn;
	private int prodAmt;
	private Integer paticpersCnt;
	private Integer dwldCnt;
	private int avgEvluScore;
	private String imgPath;
	private String upMenuNm;

	/*
	 * ebook, comic element 추가
	 */
	private String bookType;
	private int bookCount;
	private String bookStatus;
	private String supportStore;
	private String supportPlay;

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

	public String getTopMenuId() {
		return this.topMenuId;
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

	public String getMenuNm() {
		return this.menuNm;
	}

	public void setMenuNm(String menuNm) {
		this.menuNm = menuNm;
	}

	public String getMenuDesc() {
		return this.menuDesc;
	}

	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
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

	public String getPartProdId() {
		return this.partProdId;
	}

	public void setPartProdId(String partProdId) {
		this.partProdId = partProdId;
	}

	public String getVodTitlNm() {
		return this.vodTitlNm;
	}

	public void setVodTitlNm(String vodTitlNm) {
		this.vodTitlNm = vodTitlNm;
	}

	public String getProdNm() {
		return this.prodNm;
	}

	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}

	public String getChapter() {
		return this.chapter;
	}

	public void setChapter(String chapter) {
		this.chapter = chapter;
	}

	public String getConcatProdNm() {
		return this.concatProdNm;
	}

	public void setConcatProdNm(String concatProdNm) {
		this.concatProdNm = concatProdNm;
	}

	public String getProdDtlNm() {
		return this.prodDtlNm;
	}

	public void setProdDtlNm(String prodDtlNm) {
		this.prodDtlNm = prodDtlNm;
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

	public String getArtist3Nm() {
		return this.artist3Nm;
	}

	public void setArtist3Nm(String artist3Nm) {
		this.artist3Nm = artist3Nm;
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

	public int getProdAmt() {
		return this.prodAmt;
	}

	public void setProdAmt(int prodAmt) {
		this.prodAmt = prodAmt;
	}

	public Integer getPaticpersCnt() {
		return this.paticpersCnt;
	}

	public void setPaticpersCnt(Integer paticpersCnt) {
		this.paticpersCnt = paticpersCnt;
	}

	public Integer getDwldCnt() {
		return this.dwldCnt;
	}

	public void setDwldCnt(Integer dwldCnt) {
		this.dwldCnt = dwldCnt;
	}

	public int getAvgEvluScore() {
		return this.avgEvluScore;
	}

	public void setAvgEvluScore(int avgEvluScore) {
		this.avgEvluScore = avgEvluScore;
	}

	public String getImgPath() {
		return this.imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getUpMenuNm() {
		return this.upMenuNm;
	}

	public void setUpMenuNm(String upMenuNm) {
		this.upMenuNm = upMenuNm;
	}

	public String getBookType() {
		return this.bookType;
	}

	public void setBookType(String bookType) {
		this.bookType = bookType;
	}

	public int getBookCount() {
		return this.bookCount;
	}

	public void setBookCount(int bookCount) {
		this.bookCount = bookCount;
	}

	public String getBookStatus() {
		return this.bookStatus;
	}

	public void setBookStatus(String bookStatus) {
		this.bookStatus = bookStatus;
	}

	public String getSupportStore() {
		return this.supportStore;
	}

	public void setSupportStore(String supportStore) {
		this.supportStore = supportStore;
	}

	public String getSupportPlay() {
		return this.supportPlay;
	}

	public void setSupportPlay(String supportPlay) {
		this.supportPlay = supportPlay;
	}

}
