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
 * BEST 컨텐츠 Default Value Object.
 * 
 * Updated on : 2014. 1. 14. Updated by : 이석희, 아이에스플러스.
 */
public class BestContents {
	private int totalCount;
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
	private Integer prodAmt;
	private Integer paticpersCnt;
	private Integer dwldCnt;
	private Double avgEvluScore;
	private String imgPath;
	private String topMenuNm;
	private Integer imgSize;

	/*
	 * ebook, comic element 추가
	 */
	private String bookType;
	private int bookCount;
	private String bookStatus;
	private String supportStore;
	private String supportPlay;

	/**
	 * 
	 * <pre>
	 * 전체건수.
	 * </pre>
	 * 
	 * @return int
	 */
	public int getTotalCount() {
		return this.totalCount;
	}

	/**
	 * 
	 * <pre>
	 * 전체건수.
	 * </pre>
	 * 
	 * @param totalCount
	 *            totalCount
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 
	 * <pre>
	 * Top Menu Id.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getTopMenuId() {
		return this.topMenuId;
	}

	/**
	 * 
	 * <pre>
	 * Top Menu Id.
	 * </pre>
	 * 
	 * @param topMenuId
	 *            topMenuId
	 */
	public void setTopMenuId(String topMenuId) {
		this.topMenuId = topMenuId;
	}

	/**
	 * 
	 * <pre>
	 * Menu Id.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getMenuId() {
		return this.menuId;
	}

	/**
	 * 
	 * <pre>
	 * Menu Id.
	 * </pre>
	 * 
	 * @param menuId
	 *            menuId
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/**
	 * 
	 * <pre>
	 * Menu 명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getMenuNm() {
		return this.menuNm;
	}

	/**
	 * 
	 * <pre>
	 * Menu 명.
	 * </pre>
	 * 
	 * @param menuNm
	 *            menuNm
	 */
	public void setMenuNm(String menuNm) {
		this.menuNm = menuNm;
	}

	/**
	 * 
	 * <pre>
	 * Menu 설명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getMenuDesc() {
		return this.menuDesc;
	}

	/**
	 * 
	 * <pre>
	 * Menu 설명.
	 * </pre>
	 * 
	 * @param menuDesc
	 *            menuDesc
	 */
	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}

	/**
	 * 
	 * <pre>
	 * 메타 구분 코드.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getMetaClsfCd() {
		return this.metaClsfCd;
	}

	/**
	 * 
	 * <pre>
	 * 메타 구분 코드.
	 * </pre>
	 * 
	 * @param metaClsfCd
	 *            metaClsfCd
	 */
	public void setMetaClsfCd(String metaClsfCd) {
		this.metaClsfCd = metaClsfCd;
	}

	/**
	 * 
	 * <pre>
	 * 상품 Id.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getProdId() {
		return this.prodId;
	}

	/**
	 * 
	 * <pre>
	 * 상품 Id.
	 * </pre>
	 * 
	 * @param prodId
	 *            prodId
	 */
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	/**
	 * 
	 * <pre>
	 * Episode Id.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getPartProdId() {
		return this.partProdId;
	}

	/**
	 * 
	 * <pre>
	 * Episode Id.
	 * </pre>
	 * 
	 * @param partProdId
	 *            partProdId
	 */
	public void setPartProdId(String partProdId) {
		this.partProdId = partProdId;
	}

	/**
	 * 
	 * <pre>
	 * Vod 타이틀 명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getVodTitlNm() {
		return this.vodTitlNm;
	}

	/**
	 * 
	 * <pre>
	 * Vod 타이틀 명.
	 * </pre>
	 * 
	 * @param vodTitlNm
	 *            vodTitlNm
	 */
	public void setVodTitlNm(String vodTitlNm) {
		this.vodTitlNm = vodTitlNm;
	}

	/**
	 * 
	 * <pre>
	 * 상품명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getProdNm() {
		return this.prodNm;
	}

	/**
	 * 
	 * <pre>
	 * 상품명.
	 * </pre>
	 * 
	 * @param prodNm
	 *            prodNm
	 */
	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}

	/**
	 * 
	 * <pre>
	 * 회차.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getChapter() {
		return this.chapter;
	}

	/**
	 * 
	 * <pre>
	 * 회차.
	 * </pre>
	 * 
	 * @param chapter
	 *            chapter
	 */
	public void setChapter(String chapter) {
		this.chapter = chapter;
	}

	/**
	 * 
	 * <pre>
	 * Vod 타이틀명 + 상품명 + 회차.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getConcatProdNm() {
		return this.concatProdNm;
	}

	/**
	 * 
	 * <pre>
	 * Vod 타이틀명 + 상품명 + 회차.
	 * </pre>
	 * 
	 * @param concatProdNm
	 *            concatProdNm
	 */
	public void setConcatProdNm(String concatProdNm) {
		this.concatProdNm = concatProdNm;
	}

	/**
	 * 
	 * <pre>
	 * 상품 기본 설명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getProdBaseDesc() {
		return this.prodBaseDesc;
	}

	/**
	 * 
	 * <pre>
	 * 상품 기본 설명.
	 * </pre>
	 * 
	 * @param prodBaseDesc
	 *            prodBaseDesc
	 */
	public void setProdBaseDesc(String prodBaseDesc) {
		this.prodBaseDesc = prodBaseDesc;
	}

	/**
	 * 
	 * <pre>
	 * 상품 이용등급 코드.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getProdGrdCd() {
		return this.prodGrdCd;
	}

	/**
	 * 
	 * <pre>
	 * 상품 이용등급 코드.
	 * </pre>
	 * 
	 * @param prodGrdCd
	 *            prodGrdCd
	 */
	public void setProdGrdCd(String prodGrdCd) {
		this.prodGrdCd = prodGrdCd;
	}

	/**
	 * 
	 * <pre>
	 * 아티스트명1.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getArtist1Nm() {
		return this.artist1Nm;
	}

	/**
	 * 
	 * <pre>
	 * 아티스트명1.
	 * </pre>
	 * 
	 * @param artist1Nm
	 *            artist1Nm
	 */
	public void setArtist1Nm(String artist1Nm) {
		this.artist1Nm = artist1Nm;
	}

	/**
	 * 
	 * <pre>
	 * 아티스트명2.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getArtist2Nm() {
		return this.artist2Nm;
	}

	/**
	 * 
	 * <pre>
	 * 아티스트명2.
	 * </pre>
	 * 
	 * @param artist2Nm
	 *            artist2Nm
	 */
	public void setArtist2Nm(String artist2Nm) {
		this.artist2Nm = artist2Nm;
	}

	/**
	 * 
	 * <pre>
	 * 아티스트명3.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getArtist3Nm() {
		return this.artist3Nm;
	}

	/**
	 * 
	 * <pre>
	 * 아티스트명3.
	 * </pre>
	 * 
	 * @param artist3Nm
	 *            artist3Nm
	 */
	public void setArtist3Nm(String artist3Nm) {
		this.artist3Nm = artist3Nm;
	}

	/**
	 * 
	 * <pre>
	 * 발매일자.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getIssueDay() {
		return this.issueDay;
	}

	/**
	 * 
	 * <pre>
	 * 발매일자.
	 * </pre>
	 * 
	 * @param issueDay
	 *            issueDay
	 */
	public void setIssueDay(String issueDay) {
		this.issueDay = issueDay;
	}

	/**
	 * 
	 * <pre>
	 * 채널 회사명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getChnlCompNm() {
		return this.chnlCompNm;
	}

	/**
	 * 
	 * <pre>
	 * 채널 회사명.
	 * </pre>
	 * 
	 * @param chnlCompNm
	 *            chnlCompNm
	 */
	public void setChnlCompNm(String chnlCompNm) {
		this.chnlCompNm = chnlCompNm;
	}

	/**
	 * 
	 * <pre>
	 * Agency 명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getAgencyNm() {
		return this.agencyNm;
	}

	/**
	 * 
	 * <pre>
	 * Agency 명.
	 * </pre>
	 * 
	 * @param agencyNm
	 *            agencyNm
	 */
	public void setAgencyNm(String agencyNm) {
		this.agencyNm = agencyNm;
	}

	/**
	 * 
	 * <pre>
	 * hdv 지원여부.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getHdvYn() {
		return this.hdvYn;
	}

	/**
	 * 
	 * <pre>
	 * hdv 지원여부.
	 * </pre>
	 * 
	 * @param hdvYn
	 *            hdvYn
	 */
	public void setHdvYn(String hdvYn) {
		this.hdvYn = hdvYn;
	}

	/**
	 * 
	 * <pre>
	 * dolby 지원여부.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getDolbySprtYn() {
		return this.dolbySprtYn;
	}

	/**
	 * 
	 * <pre>
	 * dolby 지원여부.
	 * </pre>
	 * 
	 * @param dolbySprtYn
	 *            dolbySprtYn
	 */
	public void setDolbySprtYn(String dolbySprtYn) {
		this.dolbySprtYn = dolbySprtYn;
	}

	/**
	 * 
	 * <pre>
	 * 상품 가격.
	 * </pre>
	 * 
	 * @return int
	 */
	public Integer getProdAmt() {
		return this.prodAmt;
	}

	/**
	 * 
	 * <pre>
	 * 상품 가격.
	 * </pre>
	 * 
	 * @param prodAmt
	 *            prodAmt
	 */
	public void setProdAmt(Integer prodAmt) {
		this.prodAmt = prodAmt;
	}

	/**
	 * 
	 * <pre>
	 * 참여자 수.
	 * </pre>
	 * 
	 * @return int
	 */
	public Integer getPaticpersCnt() {
		return this.paticpersCnt;
	}

	/**
	 * 
	 * <pre>
	 * 참여자 수.
	 * </pre>
	 * 
	 * @param paticpersCnt
	 *            paticpersCnt
	 */
	public void setPaticpersCnt(Integer paticpersCnt) {
		this.paticpersCnt = paticpersCnt;
	}

	/**
	 * 
	 * <pre>
	 * 다운로드 수.
	 * </pre>
	 * 
	 * @return Integer
	 */
	public Integer getDwldCnt() {
		return this.dwldCnt;
	}

	/**
	 * 
	 * <pre>
	 * 다운로드 수.
	 * </pre>
	 * 
	 * @param dwldCnt
	 *            dwldCnt
	 */
	public void setDwldCnt(Integer dwldCnt) {
		this.dwldCnt = dwldCnt;
	}

	/**
	 * 
	 * <pre>
	 * 평점.
	 * </pre>
	 * 
	 * @return Double
	 */
	public Double getAvgEvluScore() {
		return this.avgEvluScore;
	}

	/**
	 * 
	 * <pre>
	 * 평점.
	 * </pre>
	 * 
	 * @param avgEvluScore
	 *            avgEvluScore
	 */
	public void setAvgEvluScore(Double avgEvluScore) {
		this.avgEvluScore = avgEvluScore;
	}

	/**
	 * 
	 * <pre>
	 * 이미지(파일) 경로.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getImgPath() {
		return this.imgPath;
	}

	/**
	 * 
	 * <pre>
	 * 이미지(파일) 경로.
	 * </pre>
	 * 
	 * @param imgPath
	 *            imgPath
	 */
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	/**
	 * 
	 * <pre>
	 * 이미지(파일) 사이즈.
	 * </pre>
	 * 
	 * @return the imgSize
	 */
	public Integer getImgSize() {
		return this.imgSize;
	}

	/**
	 * 
	 * <pre>
	 * 이미지(파일) 사이즈.
	 * </pre>
	 * 
	 * @param imgSize
	 *            the imgSize to set
	 */
	public void setImgSize(Integer imgSize) {
		this.imgSize = imgSize;
	}

	/**
	 * 
	 * <pre>
	 * 상위 Menu 명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getTopMenuNm() {
		return this.topMenuNm;
	}

	/**
	 * 
	 * <pre>
	 * 상위 Menu 명.
	 * </pre>
	 * 
	 * @param topMenuNm
	 *            topMenuNm
	 */
	public void setTopMenuNm(String topMenuNm) {
		this.topMenuNm = topMenuNm;
	}

	/**
	 * 
	 * <pre>
	 * Book 유형.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getBookType() {
		return this.bookType;
	}

	/**
	 * 
	 * <pre>
	 * Book 유형.
	 * </pre>
	 * 
	 * @param bookType
	 *            bookType
	 */
	public void setBookType(String bookType) {
		this.bookType = bookType;
	}

	/**
	 * 
	 * <pre>
	 * Book Count.
	 * </pre>
	 * 
	 * @return String
	 */
	public int getBookCount() {
		return this.bookCount;
	}

	/**
	 * 
	 * <pre>
	 * Book Count.
	 * </pre>
	 * 
	 * @param bookCount
	 *            bookCount
	 */
	public void setBookCount(int bookCount) {
		this.bookCount = bookCount;
	}

	/**
	 * 
	 * <pre>
	 * Book 상태.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getBookStatus() {
		return this.bookStatus;
	}

	/**
	 * 
	 * <pre>
	 * Book 상태.
	 * </pre>
	 * 
	 * @param bookStatus
	 *            bookStatus
	 */
	public void setBookStatus(String bookStatus) {
		this.bookStatus = bookStatus;
	}

	/**
	 * 
	 * <pre>
	 * 지원 상점.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getSupportStore() {
		return this.supportStore;
	}

	/**
	 * 
	 * <pre>
	 * Book 상태.
	 * </pre>
	 * 
	 * @param supportStore
	 *            supportStore
	 */
	public void setSupportStore(String supportStore) {
		this.supportStore = supportStore;
	}

	/**
	 * 
	 * <pre>
	 * Support play.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getSupportPlay() {
		return this.supportPlay;
	}

	/**
	 * 
	 * <pre>
	 * Support play.
	 * </pre>
	 * 
	 * @param supportPlay
	 *            supportPlay
	 */
	public void setSupportPlay(String supportPlay) {
		this.supportPlay = supportPlay;
	}

}
