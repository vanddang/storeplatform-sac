/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.feature.category.vo;

/**
 * 
 * 
 * Updated on : 2014. 1. 7. Updated by : 서영배, GTSOFT.
 */
public class CategoryEpubDTO {

	private String topMenuId;
	private String menuId;
	private String metaClsfCd;
	private String prodId;
	private String prodNm;
	private String prodBaseDesc;
	private Integer prodAmt;
	private Integer prodNetAmt;
	private String prodGrdCd;
	private String artist1Nm;
	private String artist2Nm;
	private String artist3Nm;
	private String chnlCompNm;
	private String issueDay;
	private String bookType;
	private Integer bookCount;
	private String supportStore;
	private String supportPlay;
	private String bookStatus;
	private int totalCount;
	private Integer paticpersCnt;
	private Integer prchsCnt;
	private Double avgEvluScore;
	private Integer dwldCnt;
	private String topMenuNm;
	private String menuNm;
	private String bookPageCnt;
	private String epsdCnt;
	private String strmEpsdCnt;
	private String captionYn;
	private String drmYn;
	private Integer fileSize;
	private String filePath;

	public int getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
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

	public String getProdId() {
		return this.prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getMetaClsfCd() {
		return this.metaClsfCd;
	}

	public void setMetaClsfCd(String metaClsfCd) {
		this.metaClsfCd = metaClsfCd;
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

	public Integer getProdAmt() {
		return this.prodAmt;
	}

	public void setProdAmt(Integer prodAmt) {
		this.prodAmt = prodAmt;
	}

	public Integer getProdNetAmt() {
		return this.prodNetAmt;
	}

	public void setProdNetAmt(Integer prodNetAmt) {
		this.prodNetAmt = prodNetAmt;
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

	public String getProdGrdCd() {
		return this.prodGrdCd;
	}

	public void setProdGrdCd(String prodGrdCd) {
		this.prodGrdCd = prodGrdCd;
	}

	public String getChnlCompNm() {
		return this.chnlCompNm;
	}

	public void setChnlCompNm(String chnlCompNm) {
		this.chnlCompNm = chnlCompNm;
	}

	public String getIssueDay() {
		return this.issueDay;
	}

	public void setIssueDay(String issueDay) {
		this.issueDay = issueDay;
	}

	public String getBookPageCnt() {
		return this.bookPageCnt;
	}

	public void setBookPageCnt(String bookPageCnt) {
		this.bookPageCnt = bookPageCnt;
	}

	public String getEpsdCnt() {
		return this.epsdCnt;
	}

	public void setEpsdCnt(String epsdCnt) {
		this.epsdCnt = epsdCnt;
	}

	public String getStrmEpsdCnt() {
		return this.strmEpsdCnt;
	}

	public void setStrmEpsdCnt(String strmEpsdCnt) {
		this.strmEpsdCnt = strmEpsdCnt;
	}

	public String getCaptionYn() {
		return this.captionYn;
	}

	public void setCaptionYn(String captionYn) {
		this.captionYn = captionYn;
	}

	public String getDrmYn() {
		return this.drmYn;
	}

	public void setDrmYn(String drmYn) {
		this.drmYn = drmYn;
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

	public Integer getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
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

	public Integer getPaticpersCnt() {
		return this.paticpersCnt;
	}

	public void setPaticpersCnt(Integer paticpersCnt) {
		this.paticpersCnt = paticpersCnt;
	}

	public String getBookType() {
		return this.bookType;
	}

	public void setBookType(String bookType) {
		this.bookType = bookType;
	}

	public Integer getBookCount() {
		return this.bookCount;
	}

	public void setBookCount(Integer bookCount) {
		this.bookCount = bookCount;
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

	public String getBookStatus() {
		return this.bookStatus;
	}

	public void setBookStatus(String bookStatus) {
		this.bookStatus = bookStatus;
	}

	public Double getAvgEvluScore() {
		return this.avgEvluScore;
	}

	public void setAvgEvluScore(Double avgEvluScore) {
		this.avgEvluScore = avgEvluScore;
	}

}
