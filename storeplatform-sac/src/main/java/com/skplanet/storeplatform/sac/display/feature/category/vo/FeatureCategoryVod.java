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
 * Feature VOD 카테고리 상품 조회 DTO Value Object.
 * 
 * Updated on : 2014. 01. 06. Updated by : 이태희, SK 플래닛.
 */
public class FeatureCategoryVod {
	private int totalCount;
	private String topMenuId;
	private String menuId;
	private String menuNm;
	private String menuDesc;
	private String metaClsfCd;
	private String prodId;
	private String vodTitlNm;
	private String prodNm;
	private String chapter;
	private String prodBaseDesc;
	private String prodGrdCd;
	private String artist1Nm;
	private String artist2Nm;
	private String issueDay;
	private String chnlCompNm;
	private String agencyNm;
	private String hdvYn;
	private String dolbySprtYn;
	private Integer prodAmt;
	private Integer paticpersCnt;
	private Integer prchsCnt;
	private Double avgEvluScore;
	private String imgPath;
	private String topMenuNm;
	private String samplUrl;
	private String prodChrgYn;

	/**
	 * <pre>
	 * totalCount.
	 * </pre>
	 * 
	 * @return int
	 */
	public int getTotalCount() {
		return this.totalCount;
	}

	/**
	 * <pre>
	 * totalCount.
	 * </pre>
	 * 
	 * @param totalCount
	 *            totalCount
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * <pre>
	 * topMenuId.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getTopMenuId() {
		return this.topMenuId;
	}

	/**
	 * <pre>
	 * topMenuId.
	 * </pre>
	 * 
	 * @param topMenuId
	 *            topMenuId
	 */
	public void setTopMenuId(String topMenuId) {
		this.topMenuId = topMenuId;
	}

	/**
	 * <pre>
	 * menuId.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getMenuId() {
		return this.menuId;
	}

	/**
	 * <pre>
	 * menuId.
	 * </pre>
	 * 
	 * @param menuId
	 *            menuId
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/**
	 * <pre>
	 * menuNm.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getMenuNm() {
		return this.menuNm;
	}

	/**
	 * <pre>
	 * menuNm.
	 * </pre>
	 * 
	 * @param menuNm
	 *            menuNm
	 */
	public void setMenuNm(String menuNm) {
		this.menuNm = menuNm;
	}

	/**
	 * <pre>
	 * menuDesc.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getMenuDesc() {
		return this.menuDesc;
	}

	/**
	 * <pre>
	 * menuDesc.
	 * </pre>
	 * 
	 * @param menuDesc
	 *            menuDesc
	 */
	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}

	/**
	 * <pre>
	 * metaClsfCd.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getMetaClsfCd() {
		return this.metaClsfCd;
	}

	/**
	 * <pre>
	 * metaClsfCd.
	 * </pre>
	 * 
	 * @param metaClsfCd
	 *            metaClsfCd
	 */
	public void setMetaClsfCd(String metaClsfCd) {
		this.metaClsfCd = metaClsfCd;
	}

	/**
	 * <pre>
	 * prodId.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getProdId() {
		return this.prodId;
	}

	/**
	 * <pre>
	 * prodId.
	 * </pre>
	 * 
	 * @param prodId
	 *            prodId
	 */
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	/**
	 * <pre>
	 * vodTitlNm.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getVodTitlNm() {
		return this.vodTitlNm;
	}

	/**
	 * <pre>
	 * vodTitlNm.
	 * </pre>
	 * 
	 * @param vodTitlNm
	 *            vodTitlNm
	 */
	public void setVodTitlNm(String vodTitlNm) {
		this.vodTitlNm = vodTitlNm;
	}

	/**
	 * <pre>
	 * prodNm.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getProdNm() {
		return this.prodNm;
	}

	/**
	 * <pre>
	 * prodNm.
	 * </pre>
	 * 
	 * @param prodNm
	 *            prodNm
	 */
	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}

	/**
	 * <pre>
	 * chapter.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getChapter() {
		return this.chapter;
	}

	/**
	 * <pre>
	 * chapter.
	 * </pre>
	 * 
	 * @param chapter
	 *            chapter
	 */
	public void setChapter(String chapter) {
		this.chapter = chapter;
	}

	/**
	 * <pre>
	 * prodBaseDesc.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getProdBaseDesc() {
		return this.prodBaseDesc;
	}

	/**
	 * <pre>
	 * prodBaseDesc.
	 * </pre>
	 * 
	 * @param prodBaseDesc
	 *            prodBaseDesc
	 */
	public void setProdBaseDesc(String prodBaseDesc) {
		this.prodBaseDesc = prodBaseDesc;
	}

	/**
	 * <pre>
	 * prodGrdCd.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getProdGrdCd() {
		return this.prodGrdCd;
	}

	/**
	 * <pre>
	 * prodGrdCd.
	 * </pre>
	 * 
	 * @param prodGrdCd
	 *            prodGrdCd
	 */
	public void setProdGrdCd(String prodGrdCd) {
		this.prodGrdCd = prodGrdCd;
	}

	/**
	 * <pre>
	 * artist1Nm.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getArtist1Nm() {
		return this.artist1Nm;
	}

	/**
	 * <pre>
	 * artist1Nm.
	 * </pre>
	 * 
	 * @param artist1Nm
	 *            artist1Nm
	 */
	public void setArtist1Nm(String artist1Nm) {
		this.artist1Nm = artist1Nm;
	}

	/**
	 * <pre>
	 * artist2Nm.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getArtist2Nm() {
		return this.artist2Nm;
	}

	/**
	 * <pre>
	 * artist2Nm.
	 * </pre>
	 * 
	 * @param artist2Nm
	 *            artist2Nm
	 */
	public void setArtist2Nm(String artist2Nm) {
		this.artist2Nm = artist2Nm;
	}

	/**
	 * <pre>
	 * issueDay.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getIssueDay() {
		return this.issueDay;
	}

	/**
	 * <pre>
	 * issueDay.
	 * </pre>
	 * 
	 * @param issueDay
	 *            issueDay
	 */
	public void setIssueDay(String issueDay) {
		this.issueDay = issueDay;
	}

	/**
	 * <pre>
	 * chnlCompNm.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getChnlCompNm() {
		return this.chnlCompNm;
	}

	/**
	 * <pre>
	 * chnlCompNm.
	 * </pre>
	 * 
	 * @param chnlCompNm
	 *            chnlCompNm
	 */
	public void setChnlCompNm(String chnlCompNm) {
		this.chnlCompNm = chnlCompNm;
	}

	/**
	 * <pre>
	 * agencyNm.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getAgencyNm() {
		return this.agencyNm;
	}

	/**
	 * <pre>
	 * agencyNm.
	 * </pre>
	 * 
	 * @param agencyNm
	 *            agencyNm
	 */
	public void setAgencyNm(String agencyNm) {
		this.agencyNm = agencyNm;
	}

	/**
	 * <pre>
	 * hdvYn.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getHdvYn() {
		return this.hdvYn;
	}

	/**
	 * <pre>
	 * hdvYn.
	 * </pre>
	 * 
	 * @param hdvYn
	 *            hdvYn
	 */
	public void setHdvYn(String hdvYn) {
		this.hdvYn = hdvYn;
	}

	/**
	 * <pre>
	 * dolbySprtYn.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getDolbySprtYn() {
		return this.dolbySprtYn;
	}

	/**
	 * <pre>
	 * dolbySprtYn.
	 * </pre>
	 * 
	 * @param dolbySprtYn
	 *            dolbySprtYn
	 */
	public void setDolbySprtYn(String dolbySprtYn) {
		this.dolbySprtYn = dolbySprtYn;
	}

	/**
	 * <pre>
	 * prodAmt.
	 * </pre>
	 * 
	 * @return Integer
	 */
	public Integer getProdAmt() {
		return this.prodAmt;
	}

	/**
	 * <pre>
	 * prodAmt.
	 * </pre>
	 * 
	 * @param prodAmt
	 *            prodAmt
	 */
	public void setProdAmt(Integer prodAmt) {
		this.prodAmt = prodAmt;
	}

	/**
	 * <pre>
	 * paticpersCnt.
	 * </pre>
	 * 
	 * @return Integer
	 */
	public Integer getPaticpersCnt() {
		return this.paticpersCnt;
	}

	/**
	 * <pre>
	 * paticpersCnt.
	 * </pre>
	 * 
	 * @param paticpersCnt
	 *            paticpersCnt
	 */
	public void setPaticpersCnt(Integer paticpersCnt) {
		this.paticpersCnt = paticpersCnt;
	}

	/**
	 * <pre>
	 * prchsCnt.
	 * </pre>
	 * 
	 * @return Integer
	 */
	public Integer getPrchsCnt() {
		return this.prchsCnt;
	}

	/**
	 * <pre>
	 * prchsCnt.
	 * </pre>
	 * 
	 * @param prchsCnt
	 *            prchsCnt
	 */
	public void setPrchsCnt(Integer prchsCnt) {
		this.prchsCnt = prchsCnt;
	}

	/**
	 * <pre>
	 * avgEvluScore.
	 * </pre>
	 * 
	 * @return Double
	 */
	public Double getAvgEvluScore() {
		return this.avgEvluScore;
	}

	/**
	 * <pre>
	 * avgEvluScore.
	 * </pre>
	 * 
	 * @param avgEvluScore
	 *            avgEvluScore
	 */
	public void setAvgEvluScore(Double avgEvluScore) {
		this.avgEvluScore = avgEvluScore;
	}

	/**
	 * <pre>
	 * imgPath.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getImgPath() {
		return this.imgPath;
	}

	/**
	 * <pre>
	 * imgPath.
	 * </pre>
	 * 
	 * @param imgPath
	 *            imgPath
	 */
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	/**
	 * <pre>
	 * topMenuNm.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getTopMenuNm() {
		return this.topMenuNm;
	}

	/**
	 * <pre>
	 * topMenuNm.
	 * </pre>
	 * 
	 * @param topMenuNm
	 *            topMenuNm
	 */
	public void setTopMenuNm(String topMenuNm) {
		this.topMenuNm = topMenuNm;
	}

	/**
	 * <pre>
	 * samplUrl.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getSamplUrl() {
		return this.samplUrl;
	}

	/**
	 * <pre>
	 * samplUrl.
	 * </pre>
	 * 
	 * @param samplUrl
	 *            samplUrl
	 */
	public void setSamplUrl(String samplUrl) {
		this.samplUrl = samplUrl;
	}

	/**
	 * <pre>
	 * prodChrgYn.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getProdChrgYn() {
		return this.prodChrgYn;
	}

	/**
	 * <pre>
	 * prodChrgYn.
	 * </pre>
	 * 
	 * @param prodChrgYn
	 *            prodChrgYn
	 */
	public void setProdChrgYn(String prodChrgYn) {
		this.prodChrgYn = prodChrgYn;
	}

}
