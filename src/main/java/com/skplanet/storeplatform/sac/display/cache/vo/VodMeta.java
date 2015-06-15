/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.cache.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * <p>
 * VodMeta
 * 
 * Changes on v2: - fileSize를 Long형으로 변경 Changes on v3: - add epsdPlayTm (재생시간) Changes on v4: - add epsdUnlmtAmt,
 * epsdPeriodAmt
 * </p>
 * Updated on : 2014. 04. 15 Updated by : 정희원, SK 플래닛.
 */
public class VodMeta extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private String upMenuId;
	private String topMenuId;
	private String menuId;
	private String menuNm;
	private String menuDesc;
	private String metaClsfCd;
	private String comptYn;
	private String prodId;
	private String partProdId;
	private String vodTitlNm;
	private String prodNm;
	private String chapter;
	private String samplUrl;
	private String scSamplUrl;
	private String prodBaseDesc;
	private String prodGrdCd;
	private String artist1Nm;
	private String artist2Nm;
	private String issueDay;
	private String chnlCompNm;
	private String agencyNm;
	private String hdvYn;
	private String dolbySprtYn;
	private String expoSellerEmail;
	private String expoSellerNm;
	private String expoSellerTelno;
	private String supportStore;
	private String supportPlay;
	private Integer chnlProdAmt;
	private Integer epsdProdAmt;
	private Integer prodNetAmt;
	private String prodChrg;
	private Integer paticpersCnt;
	private Integer prchsCnt;
	private Double avgEvluScore;
	private String filePath;
	private String topMenuNm;
	private Long fileSize;
	private String prodDtlDesc;
	private String prodStatusCd;
	private String brdcCompNm;
	private Integer chnlUnlmtAmt;
	private Integer chnlPeriodAmt;
	private Integer epsdUnlmtAmt;
	private Integer epsdPeriodAmt;
	private String epsdPlayTm;
	private String plus19Yn;

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

	public String getComptYn() {
		return this.comptYn;
	}

	public void setComptYn(String comptYn) {
		this.comptYn = comptYn;
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

	public String getSamplUrl() {
		return this.samplUrl;
	}

	public void setSamplUrl(String samplUrl) {
		this.samplUrl = samplUrl;
	}

	public String getScSamplUrl() {
		return this.scSamplUrl;
	}

	public void setScSamplUrl(String scSamplUrl) {
		this.scSamplUrl = scSamplUrl;
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

	public String getExpoSellerEmail() {
		return this.expoSellerEmail;
	}

	public void setExpoSellerEmail(String expoSellerEmail) {
		this.expoSellerEmail = expoSellerEmail;
	}

	public String getExpoSellerNm() {
		return this.expoSellerNm;
	}

	public void setExpoSellerNm(String expoSellerNm) {
		this.expoSellerNm = expoSellerNm;
	}

	public String getExpoSellerTelno() {
		return this.expoSellerTelno;
	}

	public void setExpoSellerTelno(String expoSellerTelno) {
		this.expoSellerTelno = expoSellerTelno;
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

	public Integer getChnlProdAmt() {
		return this.chnlProdAmt;
	}

	public void setChnlProdAmt(Integer chnlProdAmt) {
		this.chnlProdAmt = chnlProdAmt;
	}

	public Integer getEpsdProdAmt() {
		return this.epsdProdAmt;
	}

	public void setEpsdProdAmt(Integer epsdProdAmt) {
		this.epsdProdAmt = epsdProdAmt;
	}

	public Integer getProdNetAmt() {
		return this.prodNetAmt;
	}

	public void setProdNetAmt(Integer prodNetAmt) {
		this.prodNetAmt = prodNetAmt;
	}

	public String getProdChrg() {
		return this.prodChrg;
	}

	public void setProdChrg(String prodChrg) {
		this.prodChrg = prodChrg;
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

	public Long getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getProdDtlDesc() {
		return this.prodDtlDesc;
	}

	public void setProdDtlDesc(String prodDtlDesc) {
		this.prodDtlDesc = prodDtlDesc;
	}

	public String getProdStatusCd() {
		return this.prodStatusCd;
	}

	public void setProdStatusCd(String prodStatusCd) {
		this.prodStatusCd = prodStatusCd;
	}

	public String getBrdcCompNm() {
		return this.brdcCompNm;
	}

	public void setBrdcCompNm(String brdcCompNm) {
		this.brdcCompNm = brdcCompNm;
	}

	public Integer getChnlUnlmtAmt() {
		return this.chnlUnlmtAmt;
	}

	public void setChnlUnlmtAmt(Integer chnlUnlmtAmt) {
		this.chnlUnlmtAmt = chnlUnlmtAmt;
	}

	public Integer getChnlPeriodAmt() {
		return this.chnlPeriodAmt;
	}

	public void setChnlPeriodAmt(Integer chnlPeriodAmt) {
		this.chnlPeriodAmt = chnlPeriodAmt;
	}

	public String getEpsdPlayTm() {
		return this.epsdPlayTm;
	}

	public void setEpsdPlayTm(String epsdPlayTm) {
		this.epsdPlayTm = epsdPlayTm;
	}

	public Integer getEpsdUnlmtAmt() {
		return this.epsdUnlmtAmt;
	}

	public void setEpsdUnlmtAmt(Integer epsdUnlmtAmt) {
		this.epsdUnlmtAmt = epsdUnlmtAmt;
	}

	public Integer getEpsdPeriodAmt() {
		return this.epsdPeriodAmt;
	}

	public void setEpsdPeriodAmt(Integer epsdPeriodAmt) {
		this.epsdPeriodAmt = epsdPeriodAmt;
	}

	/**
	 * @return the plus19Yn
	 */
	public String getPlus19Yn() {
		return this.plus19Yn;
	}

	/**
	 * @param plus19Yn
	 *            the plus19Yn to set
	 */
	public void setPlus19Yn(String plus19Yn) {
		this.plus19Yn = plus19Yn;
	}

}
