package com.skplanet.storeplatform.sac.display.meta.vo;

import java.util.Map;

/**
 * Ebook/Comic Meta Info.
 * 
 * Updated on : 2014. 1. 27. Updated by : 오승민, 인크로스
 */
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
	private String sellerMbrNo;
	private String expoSellerEmail;
	private String expoSellerNm;
	private String expoSellerTelNo;
	private String subContentsId;
	private String imagePath;

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
	private String bookClsfCd;
	private String comptYn;
	private String storeProdId;
	private Integer storeProdAmt;
	private Integer storeProdNetAmt;
	private String storeDrmYn;
	private String storeProdStatusCd;
	private String playProdId;
	private Integer playProdAmt;
	private Integer playProdNetAmt;
	private String playDrmYn;
	private String playProdStatusCd;
	private String usePeriodUnitCd;
	private String usePeriod;
	private String ebookFilePath;
	private Integer ebookFileSize;
	private String cid;

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

	/**
	 * @return the upMenuNm
	 */
	public String getUpMenuNm() {
		return this.upMenuNm;
	}

	/**
	 * @param upMenuNm
	 *            the upMenuNm to set
	 */
	public void setUpMenuNm(String upMenuNm) {
		this.upMenuNm = upMenuNm;
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
	 * @return the menuDesc
	 */
	public String getMenuDesc() {
		return this.menuDesc;
	}

	/**
	 * @param menuDesc
	 *            the menuDesc to set
	 */
	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}

	/**
	 * @return the metaClsfCd
	 */
	public String getMetaClsfCd() {
		return this.metaClsfCd;
	}

	/**
	 * @param metaClsfCd
	 *            the metaClsfCd to set
	 */
	public void setMetaClsfCd(String metaClsfCd) {
		this.metaClsfCd = metaClsfCd;
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
	 * @return the partProdId
	 */
	public String getPartProdId() {
		return this.partProdId;
	}

	/**
	 * @param partProdId
	 *            the partProdId to set
	 */
	public void setPartProdId(String partProdId) {
		this.partProdId = partProdId;
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
	public Integer getProdAmt() {
		return this.prodAmt;
	}

	/**
	 * @param prodAmt
	 *            the prodAmt to set
	 */
	public void setProdAmt(Integer prodAmt) {
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
	 * @return the filePath
	 */
	public String getFilePath() {
		return this.filePath;
	}

	/**
	 * @param filePath
	 *            the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * @return the fileNm
	 */
	public String getFileNm() {
		return this.fileNm;
	}

	/**
	 * @param fileNm
	 *            the fileNm to set
	 */
	public void setFileNm(String fileNm) {
		this.fileNm = fileNm;
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
	 * @return the artist1Nm
	 */
	public String getArtist1Nm() {
		return this.artist1Nm;
	}

	/**
	 * @param artist1Nm
	 *            the artist1Nm to set
	 */
	public void setArtist1Nm(String artist1Nm) {
		this.artist1Nm = artist1Nm;
	}

	/**
	 * @return the artist2Nm
	 */
	public String getArtist2Nm() {
		return this.artist2Nm;
	}

	/**
	 * @param artist2Nm
	 *            the artist2Nm to set
	 */
	public void setArtist2Nm(String artist2Nm) {
		this.artist2Nm = artist2Nm;
	}

	/**
	 * @return the artist3Nm
	 */
	public String getArtist3Nm() {
		return this.artist3Nm;
	}

	/**
	 * @param artist3Nm
	 *            the artist3Nm to set
	 */
	public void setArtist3Nm(String artist3Nm) {
		this.artist3Nm = artist3Nm;
	}

	/**
	 * @return the fileSize
	 */
	public Integer getFileSize() {
		return this.fileSize;
	}

	/**
	 * @param fileSize
	 *            the fileSize to set
	 */
	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * @return the contentsTypeCd
	 */
	public String getContentsTypeCd() {
		return this.contentsTypeCd;
	}

	/**
	 * @param contentsTypeCd
	 *            the contentsTypeCd to set
	 */
	public void setContentsTypeCd(String contentsTypeCd) {
		this.contentsTypeCd = contentsTypeCd;
	}

	/**
	 * @return the supportList
	 */
	public Map<String, Object> getSupportList() {
		return this.supportList;
	}

	/**
	 * @param supportList
	 *            the supportList to set
	 */
	public void setSupportList(Map<String, Object> supportList) {
		this.supportList = supportList;
	}

	/**
	 * @return the sellerMbrNo
	 */
	public String getSellerMbrNo() {
		return this.sellerMbrNo;
	}

	/**
	 * @param sellerMbrNo
	 *            the sellerMbrNo to set
	 */
	public void setSellerMbrNo(String sellerMbrNo) {
		this.sellerMbrNo = sellerMbrNo;
	}

	/**
	 * @return the expoSellerEmail
	 */
	public String getExpoSellerEmail() {
		return this.expoSellerEmail;
	}

	/**
	 * @param expoSellerEmail
	 *            the expoSellerEmail to set
	 */
	public void setExpoSellerEmail(String expoSellerEmail) {
		this.expoSellerEmail = expoSellerEmail;
	}

	/**
	 * @return the expoSellerNm
	 */
	public String getExpoSellerNm() {
		return this.expoSellerNm;
	}

	/**
	 * @param expoSellerNm
	 *            the expoSellerNm to set
	 */
	public void setExpoSellerNm(String expoSellerNm) {
		this.expoSellerNm = expoSellerNm;
	}

	/**
	 * @return the expoSellerTelNo
	 */
	public String getExpoSellerTelNo() {
		return this.expoSellerTelNo;
	}

	/**
	 * @param expoSellerTelNo
	 *            the expoSellerTelNo to set
	 */
	public void setExpoSellerTelNo(String expoSellerTelNo) {
		this.expoSellerTelNo = expoSellerTelNo;
	}

	/**
	 * @return the subContentsId
	 */
	public String getSubContentsId() {
		return this.subContentsId;
	}

	/**
	 * @param subContentsId
	 *            the subContentsId to set
	 */
	public void setSubContentsId(String subContentsId) {
		this.subContentsId = subContentsId;
	}

	/**
	 * @return the imagePath
	 */
	public String getImagePath() {
		return this.imagePath;
	}

	/**
	 * @param imagePath
	 *            the imagePath to set
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
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
	 * @return the vodTitlNm
	 */
	public String getVodTitlNm() {
		return this.vodTitlNm;
	}

	/**
	 * @param vodTitlNm
	 *            the vodTitlNm to set
	 */
	public void setVodTitlNm(String vodTitlNm) {
		this.vodTitlNm = vodTitlNm;
	}

	/**
	 * @return the chapter
	 */
	public String getChapter() {
		return this.chapter;
	}

	/**
	 * @param chapter
	 *            the chapter to set
	 */
	public void setChapter(String chapter) {
		this.chapter = chapter;
	}

	/**
	 * @return the issueDay
	 */
	public String getIssueDay() {
		return this.issueDay;
	}

	/**
	 * @param issueDay
	 *            the issueDay to set
	 */
	public void setIssueDay(String issueDay) {
		this.issueDay = issueDay;
	}

	/**
	 * @return the chnlCompNm
	 */
	public String getChnlCompNm() {
		return this.chnlCompNm;
	}

	/**
	 * @param chnlCompNm
	 *            the chnlCompNm to set
	 */
	public void setChnlCompNm(String chnlCompNm) {
		this.chnlCompNm = chnlCompNm;
	}

	/**
	 * @return the agencyNm
	 */
	public String getAgencyNm() {
		return this.agencyNm;
	}

	/**
	 * @param agencyNm
	 *            the agencyNm to set
	 */
	public void setAgencyNm(String agencyNm) {
		this.agencyNm = agencyNm;
	}

	/**
	 * @return the hdvYn
	 */
	public String getHdvYn() {
		return this.hdvYn;
	}

	/**
	 * @param hdvYn
	 *            the hdvYn to set
	 */
	public void setHdvYn(String hdvYn) {
		this.hdvYn = hdvYn;
	}

	/**
	 * @return the dolbySprtYn
	 */
	public String getDolbySprtYn() {
		return this.dolbySprtYn;
	}

	/**
	 * @param dolbySprtYn
	 *            the dolbySprtYn to set
	 */
	public void setDolbySprtYn(String dolbySprtYn) {
		this.dolbySprtYn = dolbySprtYn;
	}

	/**
	 * @return the epsdCnt
	 */
	public Integer getEpsdCnt() {
		return this.epsdCnt;
	}

	/**
	 * @param epsdCnt
	 *            the epsdCnt to set
	 */
	public void setEpsdCnt(Integer epsdCnt) {
		this.epsdCnt = epsdCnt;
	}

	/**
	 * @return the strmEpsdCnt
	 */
	public Integer getStrmEpsdCnt() {
		return this.strmEpsdCnt;
	}

	/**
	 * @param strmEpsdCnt
	 *            the strmEpsdCnt to set
	 */
	public void setStrmEpsdCnt(Integer strmEpsdCnt) {
		this.strmEpsdCnt = strmEpsdCnt;
	}

	/**
	 * @return the supportStore
	 */
	public String getSupportStore() {
		return this.supportStore;
	}

	/**
	 * @param supportStore
	 *            the supportStore to set
	 */
	public void setSupportStore(String supportStore) {
		this.supportStore = supportStore;
	}

	/**
	 * @return the supportPlay
	 */
	public String getSupportPlay() {
		return this.supportPlay;
	}

	/**
	 * @param supportPlay
	 *            the supportPlay to set
	 */
	public void setSupportPlay(String supportPlay) {
		this.supportPlay = supportPlay;
	}

	/**
	 * @return the bookType
	 */
	public String getBookType() {
		return this.bookType;
	}

	/**
	 * @param bookType
	 *            the bookType to set
	 */
	public void setBookType(String bookType) {
		this.bookType = bookType;
	}

	/**
	 * @return the bookCount
	 */
	public Integer getBookCount() {
		return this.bookCount;
	}

	/**
	 * @param bookCount
	 *            the bookCount to set
	 */
	public void setBookCount(Integer bookCount) {
		this.bookCount = bookCount;
	}

	/**
	 * @return the bookStatus
	 */
	public String getBookStatus() {
		return this.bookStatus;
	}

	/**
	 * @param bookStatus
	 *            the bookStatus to set
	 */
	public void setBookStatus(String bookStatus) {
		this.bookStatus = bookStatus;
	}

	/**
	 * @return the bookClsfCd
	 */
	public String getBookClsfCd() {
		return this.bookClsfCd;
	}

	/**
	 * @param bookClsfCd
	 *            the bookClsfCd to set
	 */
	public void setBookClsfCd(String bookClsfCd) {
		this.bookClsfCd = bookClsfCd;
	}

	/**
	 * @return the comptYn
	 */
	public String getComptYn() {
		return this.comptYn;
	}

	/**
	 * @param comptYn
	 *            the comptYn to set
	 */
	public void setComptYn(String comptYn) {
		this.comptYn = comptYn;
	}

	/**
	 * @return the storeProdId
	 */
	public String getStoreProdId() {
		return this.storeProdId;
	}

	/**
	 * @param storeProdId
	 *            the storeProdId to set
	 */
	public void setStoreProdId(String storeProdId) {
		this.storeProdId = storeProdId;
	}

	/**
	 * @return the storeProdAmt
	 */
	public Integer getStoreProdAmt() {
		return this.storeProdAmt;
	}

	/**
	 * @param storeProdAmt
	 *            the storeProdAmt to set
	 */
	public void setStoreProdAmt(Integer storeProdAmt) {
		this.storeProdAmt = storeProdAmt;
	}

	/**
	 * @return the storeProdNetAmt
	 */
	public Integer getStoreProdNetAmt() {
		return this.storeProdNetAmt;
	}

	/**
	 * @param storeProdNetAmt
	 *            the storeProdNetAmt to set
	 */
	public void setStoreProdNetAmt(Integer storeProdNetAmt) {
		this.storeProdNetAmt = storeProdNetAmt;
	}

	/**
	 * @return the storeDrmYn
	 */
	public String getStoreDrmYn() {
		return this.storeDrmYn;
	}

	/**
	 * @param storeDrmYn
	 *            the storeDrmYn to set
	 */
	public void setStoreDrmYn(String storeDrmYn) {
		this.storeDrmYn = storeDrmYn;
	}

	/**
	 * @return the storeProdStatusCd
	 */
	public String getStoreProdStatusCd() {
		return this.storeProdStatusCd;
	}

	/**
	 * @param storeProdStatusCd
	 *            the storeProdStatusCd to set
	 */
	public void setStoreProdStatusCd(String storeProdStatusCd) {
		this.storeProdStatusCd = storeProdStatusCd;
	}

	/**
	 * @return the playProdId
	 */
	public String getPlayProdId() {
		return this.playProdId;
	}

	/**
	 * @param playProdId
	 *            the playProdId to set
	 */
	public void setPlayProdId(String playProdId) {
		this.playProdId = playProdId;
	}

	/**
	 * @return the playProdAmt
	 */
	public Integer getPlayProdAmt() {
		return this.playProdAmt;
	}

	/**
	 * @param playProdAmt
	 *            the playProdAmt to set
	 */
	public void setPlayProdAmt(Integer playProdAmt) {
		this.playProdAmt = playProdAmt;
	}

	/**
	 * @return the playProdNetAmt
	 */
	public Integer getPlayProdNetAmt() {
		return this.playProdNetAmt;
	}

	/**
	 * @param playProdNetAmt
	 *            the playProdNetAmt to set
	 */
	public void setPlayProdNetAmt(Integer playProdNetAmt) {
		this.playProdNetAmt = playProdNetAmt;
	}

	/**
	 * @return the playDrmYn
	 */
	public String getPlayDrmYn() {
		return this.playDrmYn;
	}

	/**
	 * @param playDrmYn
	 *            the playDrmYn to set
	 */
	public void setPlayDrmYn(String playDrmYn) {
		this.playDrmYn = playDrmYn;
	}

	/**
	 * @return the playProdStatusCd
	 */
	public String getPlayProdStatusCd() {
		return this.playProdStatusCd;
	}

	/**
	 * @param playProdStatusCd
	 *            the playProdStatusCd to set
	 */
	public void setPlayProdStatusCd(String playProdStatusCd) {
		this.playProdStatusCd = playProdStatusCd;
	}

	/**
	 * @return the usePeriodUnitCd
	 */
	public String getUsePeriodUnitCd() {
		return this.usePeriodUnitCd;
	}

	/**
	 * @param usePeriodUnitCd
	 *            the usePeriodUnitCd to set
	 */
	public void setUsePeriodUnitCd(String usePeriodUnitCd) {
		this.usePeriodUnitCd = usePeriodUnitCd;
	}

	/**
	 * @return the usePeriod
	 */
	public String getUsePeriod() {
		return this.usePeriod;
	}

	/**
	 * @param usePeriod
	 *            the usePeriod to set
	 */
	public void setUsePeriod(String usePeriod) {
		this.usePeriod = usePeriod;
	}

	/**
	 * @return the ebookFilePath
	 */
	public String getEbookFilePath() {
		return this.ebookFilePath;
	}

	/**
	 * @param ebookFilePath
	 *            the ebookFilePath to set
	 */
	public void setEbookFilePath(String ebookFilePath) {
		this.ebookFilePath = ebookFilePath;
	}

	/**
	 * @return the ebookFileSize
	 */
	public Integer getEbookFileSize() {
		return this.ebookFileSize;
	}

	/**
	 * @param ebookFileSize
	 *            the ebookFileSize to set
	 */
	public void setEbookFileSize(Integer ebookFileSize) {
		this.ebookFileSize = ebookFileSize;
	}

	/**
	 * @return the cid
	 */
	public String getCid() {
		return this.cid;
	}

	/**
	 * @param cid
	 *            the cid to set
	 */
	public void setCid(String cid) {
		this.cid = cid;
	}

	/**
	 * @return the rankChgCnt
	 */
	public String getRankChgCnt() {
		return this.rankChgCnt;
	}

	/**
	 * @param rankChgCnt
	 *            the rankChgCnt to set
	 */
	public void setRankChgCnt(String rankChgCnt) {
		this.rankChgCnt = rankChgCnt;
	}

	/**
	 * @return the mp3SprtYn
	 */
	public String getMp3SprtYn() {
		return this.mp3SprtYn;
	}

	/**
	 * @param mp3SprtYn
	 *            the mp3SprtYn to set
	 */
	public void setMp3SprtYn(String mp3SprtYn) {
		this.mp3SprtYn = mp3SprtYn;
	}

	/**
	 * @return the bellSprtYn
	 */
	public String getBellSprtYn() {
		return this.bellSprtYn;
	}

	/**
	 * @param bellSprtYn
	 *            the bellSprtYn to set
	 */
	public void setBellSprtYn(String bellSprtYn) {
		this.bellSprtYn = bellSprtYn;
	}

	/**
	 * @return the colorringSprtYn
	 */
	public String getColorringSprtYn() {
		return this.colorringSprtYn;
	}

	/**
	 * @param colorringSprtYn
	 *            the colorringSprtYn to set
	 */
	public void setColorringSprtYn(String colorringSprtYn) {
		this.colorringSprtYn = colorringSprtYn;
	}

	/**
	 * @return the dcRate
	 */
	public Double getDcRate() {
		return this.dcRate;
	}

	/**
	 * @param dcRate
	 *            the dcRate to set
	 */
	public void setDcRate(Double dcRate) {
		this.dcRate = dcRate;
	}

	/**
	 * @return the dcAmt
	 */
	public String getDcAmt() {
		return this.dcAmt;
	}

	/**
	 * @param dcAmt
	 *            the dcAmt to set
	 */
	public void setDcAmt(String dcAmt) {
		this.dcAmt = dcAmt;
	}

	/**
	 * @return the dlvProdYn
	 */
	public String getDlvProdYn() {
		return this.dlvProdYn;
	}

	/**
	 * @param dlvProdYn
	 *            the dlvProdYn to set
	 */
	public void setDlvProdYn(String dlvProdYn) {
		this.dlvProdYn = dlvProdYn;
	}

	/**
	 * @return the dwldQty
	 */
	public String getDwldQty() {
		return this.dwldQty;
	}

	/**
	 * @param dwldQty
	 *            the dwldQty to set
	 */
	public void setDwldQty(String dwldQty) {
		this.dwldQty = dwldQty;
	}

	/**
	 * @return the prchsQty
	 */
	public String getPrchsQty() {
		return this.prchsQty;
	}

	/**
	 * @param prchsQty
	 *            the prchsQty to set
	 */
	public void setPrchsQty(String prchsQty) {
		this.prchsQty = prchsQty;
	}

	/**
	 * @return the expoOrd
	 */
	public String getExpoOrd() {
		return this.expoOrd;
	}

	/**
	 * @param expoOrd
	 *            the expoOrd to set
	 */
	public void setExpoOrd(String expoOrd) {
		this.expoOrd = expoOrd;
	}

	/**
	 * @return the brandId
	 */
	public String getBrandId() {
		return this.brandId;
	}

	/**
	 * @param brandId
	 *            the brandId to set
	 */
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	/**
	 * @return the brandName
	 */
	public String getBrandName() {
		return this.brandName;
	}

	/**
	 * @param brandName
	 *            the brandName to set
	 */
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	/**
	 * @return the no
	 */
	public String getNo() {
		return this.no;
	}

	/**
	 * @param no
	 *            the no to set
	 */
	public void setNo(String no) {
		this.no = no;
	}

	/**
	 * @return the newYn
	 */
	public String getNewYn() {
		return this.newYn;
	}

	/**
	 * @param newYn
	 *            the newYn to set
	 */
	public void setNewYn(String newYn) {
		this.newYn = newYn;
	}

	/**
	 * @return the regDt
	 */
	public String getRegDt() {
		return this.regDt;
	}

	/**
	 * @param regDt
	 *            the regDt to set
	 */
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	/**
	 * @return the applyStartDt
	 */
	public String getApplyStartDt() {
		return this.applyStartDt;
	}

	/**
	 * @param applyStartDt
	 *            the applyStartDt to set
	 */
	public void setApplyStartDt(String applyStartDt) {
		this.applyStartDt = applyStartDt;
	}

	/**
	 * @return the applyEndDt
	 */
	public String getApplyEndDt() {
		return this.applyEndDt;
	}

	/**
	 * @param applyEndDt
	 *            the applyEndDt to set
	 */
	public void setApplyEndDt(String applyEndDt) {
		this.applyEndDt = applyEndDt;
	}

	/**
	 * @return the prodCaseCd
	 */
	public String getProdCaseCd() {
		return this.prodCaseCd;
	}

	/**
	 * @param prodCaseCd
	 *            the prodCaseCd to set
	 */
	public void setProdCaseCd(String prodCaseCd) {
		this.prodCaseCd = prodCaseCd;
	}

	/**
	 * @return the prodNetAmt
	 */
	public Integer getProdNetAmt() {
		return this.prodNetAmt;
	}

	/**
	 * @param prodNetAmt
	 *            the prodNetAmt to set
	 */
	public void setProdNetAmt(Integer prodNetAmt) {
		this.prodNetAmt = prodNetAmt;
	}

	/**
	 * @return the catalogId
	 */
	public String getCatalogId() {
		return this.catalogId;
	}

	/**
	 * @param catalogId
	 *            the catalogId to set
	 */
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
}
