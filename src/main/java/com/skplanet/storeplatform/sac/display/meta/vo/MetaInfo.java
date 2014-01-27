package com.skplanet.storeplatform.sac.display.meta.vo;

import java.util.Map;

public class MetaInfo {
	// ////////////////////////// 공통 변수 ////////////////////////////
	private String topMenuId;
	private String topMenuNm;
	private String upMenuNm;
	private String menuId;
	private String menuNm;
	private String menuDesc;
	private String metaClsfCd;
	private String prodId;
	private String partProdId;
	private String prodNm;
	private String prodBaseDesc;
	private Integer prodAmt;
	private String prodGrdCd;
	private String partParentClsfCd;
	private String filePath;
	private String fileNm;
	private Integer paticpersCnt;
	private Integer prchsCnt;
	private Double avgEvluScore;
	private String artist1Nm;
	private String artist2Nm;
	private String artist3Nm;
	private Integer fileSize;
	private String contentsTypeCd;
	private Map<String, Object> supportList;
	private String expoSellerEmail;
	private String expoSellerNm;
	private String expoSellerTelNo;

	// ////////////////////////// App 상품 변수 ////////////////////////////
	private String aid;
	private String drmYn;
	private String prodVer;
	private String apkPkgNm;
	private String apkVer;

	// ////////////////////////// 멀티미디어 상품 변수 ////////////////////////////
	private String vodTitlNm;
	private String chapter;
	private String issueDay;
	private String chnlCompNm;
	private String agencyNm;
	private String hdvYn;
	private String dolbySprtYn;
	private Integer epsdCnt;
	private Integer strmEpsdCnt;
	private String supportStore;
	private String supportPlay;
	private String bookType;
	private Integer bookCount;
	private String bookStatus;
	/*
	 * 완료 여부
	 */
	private String comptYn;

	// ////////////////////////// 음원 상품 변수 ////////////////////////////
	private String rankChgCnt;
	private String mp3SprtYn;
	private String bellSprtYn;
	private String colorringSprtYn;

	// ////////////////////////// 쇼핑 상품 변수 ////////////////////////////
	private Double dcRate;
	private String dcAmt;
	private String dlvProdYn;
	private String dwldQty;
	private String prchsQty;
	private String expoOrd;
	private String brandId;
	private String brandName;
	private String no;
	private String newYn;
	private String regDt;
	private String applyStartDt;
	private String applyEndDt;
	private String prodCaseCd;
	private Integer prodNetAmt;
	private String catalogId;

	public String getTopMenuId() {
		return this.topMenuId;
	}

	public void setTopMenuId(String topMenuId) {
		this.topMenuId = topMenuId;
	}

	public String getTopMenuNm() {
		return this.topMenuNm;
	}

	public void setTopMenuNm(String topMenuNm) {
		this.topMenuNm = topMenuNm;
	}

	public String getUpMenuNm() {
		return this.upMenuNm;
	}

	public void setUpMenuNm(String upMenuNm) {
		this.upMenuNm = upMenuNm;
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

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileNm() {
		return this.fileNm;
	}

	public void setFileNm(String fileNm) {
		this.fileNm = fileNm;
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

	public Integer getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}

	public String getContentsTypeCd() {
		return this.contentsTypeCd;
	}

	public void setContentsTypeCd(String contentsTypeCd) {
		this.contentsTypeCd = contentsTypeCd;
	}

	public Map<String, Object> getSupportList() {
		return this.supportList;
	}

	public void setSupportList(Map<String, Object> supportList) {
		this.supportList = supportList;
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

	public String getExpoSellerTelNo() {
		return this.expoSellerTelNo;
	}

	public void setExpoSellerTelNo(String expoSellerTelNo) {
		this.expoSellerTelNo = expoSellerTelNo;
	}

	public String getAid() {
		return this.aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
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

	public String getVodTitlNm() {
		return this.vodTitlNm;
	}

	public void setVodTitlNm(String vodTitlNm) {
		this.vodTitlNm = vodTitlNm;
	}

	public String getChapter() {
		return this.chapter;
	}

	public void setChapter(String chapter) {
		this.chapter = chapter;
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

	public String getRankChgCnt() {
		return this.rankChgCnt;
	}

	public void setRankChgCnt(String rankChgCnt) {
		this.rankChgCnt = rankChgCnt;
	}

	public String getMp3SprtYn() {
		return this.mp3SprtYn;
	}

	public void setMp3SprtYn(String mp3SprtYn) {
		this.mp3SprtYn = mp3SprtYn;
	}

	public String getBellSprtYn() {
		return this.bellSprtYn;
	}

	public void setBellSprtYn(String bellSprtYn) {
		this.bellSprtYn = bellSprtYn;
	}

	public String getColorringSprtYn() {
		return this.colorringSprtYn;
	}

	public void setColorringSprtYn(String colorringSprtYn) {
		this.colorringSprtYn = colorringSprtYn;
	}

	public Double getDcRate() {
		return this.dcRate;
	}

	public void setDcRate(Double dcRate) {
		this.dcRate = dcRate;
	}

	public String getDcAmt() {
		return this.dcAmt;
	}

	public void setDcAmt(String dcAmt) {
		this.dcAmt = dcAmt;
	}

	public String getDlvProdYn() {
		return this.dlvProdYn;
	}

	public void setDlvProdYn(String dlvProdYn) {
		this.dlvProdYn = dlvProdYn;
	}

	public String getDwldQty() {
		return this.dwldQty;
	}

	public void setDwldQty(String dwldQty) {
		this.dwldQty = dwldQty;
	}

	public String getPrchsQty() {
		return this.prchsQty;
	}

	public void setPrchsQty(String prchsQty) {
		this.prchsQty = prchsQty;
	}

	public String getExpoOrd() {
		return this.expoOrd;
	}

	public void setExpoOrd(String expoOrd) {
		this.expoOrd = expoOrd;
	}

	public String getBrandId() {
		return this.brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return this.brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getNo() {
		return this.no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getNewYn() {
		return this.newYn;
	}

	public void setNewYn(String newYn) {
		this.newYn = newYn;
	}

	public String getRegDt() {
		return this.regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	public String getApplyStartDt() {
		return this.applyStartDt;
	}

	public void setApplyStartDt(String applyStartDt) {
		this.applyStartDt = applyStartDt;
	}

	public String getApplyEndDt() {
		return this.applyEndDt;
	}

	public void setApplyEndDt(String applyEndDt) {
		this.applyEndDt = applyEndDt;
	}

	public String getProdCaseCd() {
		return this.prodCaseCd;
	}

	public void setProdCaseCd(String prodCaseCd) {
		this.prodCaseCd = prodCaseCd;
	}

	public Integer getProdNetAmt() {
		return this.prodNetAmt;
	}

	public void setProdNetAmt(Integer prodNetAmt) {
		this.prodNetAmt = prodNetAmt;
	}

	public String getCatalogId() {
		return this.catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public Integer getEpsdCnt() {
		return this.epsdCnt;
	}

	public void setEpsdCnt(Integer epsdCnt) {
		this.epsdCnt = epsdCnt;
	}

	public Integer getStrmEpsdCnt() {
		return this.strmEpsdCnt;
	}

	public void setStrmEpsdCnt(Integer strmEpsdCnt) {
		this.strmEpsdCnt = strmEpsdCnt;
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

	public String getBookType() {
		return this.bookType;
	}

	public void setBookType(String bookType) {
		this.bookType = bookType;
	}

	public Integer getBookCount() {
		return this.bookCount;
	}

	public String getBookStatus() {
		return this.bookStatus;
	}

	public void setBookStatus(String bookStatus) {
		this.bookStatus = bookStatus;
	}

	public String getComptYn() {
		return this.comptYn;
	}

	public void setComptYn(String comptYn) {
		this.comptYn = comptYn;
	}

}
