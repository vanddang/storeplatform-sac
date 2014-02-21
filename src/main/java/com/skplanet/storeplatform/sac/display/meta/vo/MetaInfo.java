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
	private String prodDtlDesc;
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
	private String imageNm;
	private Integer imageSize;
	private String previewImagePath;
	private Integer previewImageSize;
	private Integer previewCurCnt; // preview 이미지 건수
	private String imageCode;
	private String chnlProdId;
	private String sysDate;
	private Integer totalCount;
	private String prodClsfCd;
	private String platClsfCd;
	private String purchaseId;
	private String purchaseProdId;
	private String purchaseDt;
	private String purchaseState;
	private String purchaseDwldExprDt;
	private Integer purchasePrice;
	private String bpJoinFileNo;
	private String bpJoinFileType;
	private String expiredDate;
	private String prodChrg;
	private String useExprDt;
	private String dwldExprDt;
	private String deviceKey;
	private String deviceType;
	private String deviceSubKey;
	private String userKey;
	private String company;
	private String cid;

	// ////////////////////////// App 상품 변수 ////////////////////////////
	private String aid;
	private String drmYn;
	private String prodVer;
	private String apkPkgNm;
	private String apkVer;
	private String fakeYn;
	private String apkVerCd;
	private Integer apkFileSize;
	private String seedProductId;
	private String gameCentrId;
	private String gameCentrVerCd;
	private String seedCaseRefCd;
	private String supportedOs;
	private String seedUseYn;

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
	private String storeProdChrg;
	private String storeUsePeriodUnitCd;
	private String playProdId;
	private Integer playProdAmt;
	private Integer playProdNetAmt;
	private String playDrmYn;
	private String playProdStatusCd;
	private String playProdChrg;
	private String playUsePeriodUnitCd;
	private String usePeriodUnitCd;
	private String usePeriod;
	private String usePeriodNm;
	private String ebookFilePath;
	private Integer ebookFileSize;
	private String hdcpYn;
	private String btvYn;
	private String epsdPlayTm;
	private String chapterUnit;
	private String nmBtvCid;
	private String nmDpPicRatio;
	private String nmDpPixel;
	private String nmSubContsId;
	private Integer nmFileSize;
	private String nmProdVer;
	private String sdBtvCid;
	private String sdDpPicRatio;
	private String sdDpPixel;
	private String sdSubContsId;
	private Integer sdFileSize;
	private String sdProdVer;
	private String hdBtvCid;
	private String hdDpPicRatio;
	private String hdDpPixel;
	private String hdSubContsId;
	private Integer hdFileSize;
	private String hdProdVer;
	private String dwldAreaLimtYn;
	private String strmNetworkCd;
	private String dwldNetworkCd;
	private String nmFilePath;
	private String sdFilePath;
	private String hdFilePath;
	private String chnlProdNm;
	private String genreCd;
	private String genreCd2;
	private Integer bookPageCnt;
	private String colorSprtYn;
	private String isbn;
	private String mgzinCoverYn;
	private String dlmImagePath;

	// ////////////////////////// 웹툰 상품 변수 ////////////////////////////
	private String iconYn;
	private String updDt;

	// ////////////////////////// 음원 상품 변수 ////////////////////////////
	private String musicId;
	private String rankChgCnt;
	private String mp3SprtYn;
	private String bellSprtYn;
	private String colorringSprtYn;
	private String outsdContentsId;
	private Integer fileSizeH;
	private String artistId;
	private String artistNm;
	private String debutDay;
	private String debutMusicNm;
	private String country;

	// ////////////////////////// 쇼핑 상품 변수 ////////////////////////////
	private Double dcRate;
	private String dcAmt;
	private String dlvProdYn;
	private String dwldQty;
	private String prchsQty;
	private String expoOrd;
	private String brandId;
	private String brandNm;
	private String no;
	private String newYn;
	private String regDt;
	private String applyStartDt;
	private String applyEndDt;
	private String prodCaseCd;
	private Integer prodNetAmt;
	private String catalogId;
	private String catalogNm;
	private String specialSale;
	private String soldOut;

	private String planId;
	private String planNm;
	private String subTitlNm;
	private String planStartDt;
	private String planEndDt;
	private String przwnerAnnoDt;
	private String planGiftNm;

	private String allow;
	private String b2bProdYn;
	private String mthMaxCnt; // 월_최대_판매_수량
	private String dlyMaxCnt; // 일_최대_판매_수량
	private String mthUsrMaxCnt; // 월_회원_최대_구매_수량
	private String dlyUsrMaxCnt; // 일_회원_최대_구매_수량
	private String eachMaxCnt; // 1차_최대_구매_수량

	private String usePlac; // 사용_장소
	private String useLimtDesc; // 사용_제한_설명
	private String noticeMatt; // 공지_사항
	private String prchsCancelDrbkReason; // 구매_취소_환불_사유

	private String opt1Nm; // 옵션 기준점
	private String optPdNm; // 옵션 값
	private String subYn; // 옵션 하위 여부
	private String srcContentId; // 쿠폰코드,아이템코드

	// ////////////////////////// 태그 관련 변수 ////////////////////////////
	private String tagCd;
	private String tagNm;
	private String tagTypeCd;

	// //////////////////////////정액제 상품 변수 ////////////////////////////
	private String prodAlias; // 상품별명
	private String cmpxProdClsfCd; // 복함상품구분코드
	private String autoApprYn; // 자동결제여부
	private String maxSaleCnt; // 최대판매수량
	private String salePocCd; // 판매POC코드
	private String dupPrchsLimtYn; // 중복구매제한여부
	private String bannerFilePath; // 배너이미지
	private String thumbnailFilePath; // 썸네일이미지
	private String prodIntrDscr; // 정액제 상품 설명
	private String prodStatusCd; // 정액제 상품 상태

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
	 * @return the prodDtlDesc
	 */
	public String getProdDtlDesc() {
		return this.prodDtlDesc;
	}

	/**
	 * @param prodDtlDesc
	 *            the prodDtlDesc to set
	 */
	public void setProdDtlDesc(String prodDtlDesc) {
		this.prodDtlDesc = prodDtlDesc;
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
	 * @return the imageNm
	 */
	public String getImageNm() {
		return this.imageNm;
	}

	/**
	 * @param imageNm
	 *            the imageNm to set
	 */
	public void setImageNm(String imageNm) {
		this.imageNm = imageNm;
	}

	/**
	 * @return the imageSize
	 */
	public Integer getImageSize() {
		return this.imageSize;
	}

	/**
	 * @param imageSize
	 *            the imageSize to set
	 */
	public void setImageSize(Integer imageSize) {
		this.imageSize = imageSize;
	}

	/**
	 * @return the previewImagePath
	 */
	public String getPreviewImagePath() {
		return this.previewImagePath;
	}

	/**
	 * @param previewImagePath
	 *            the previewImagePath to set
	 */
	public void setPreviewImagePath(String previewImagePath) {
		this.previewImagePath = previewImagePath;
	}

	/**
	 * @return the previewImageSize
	 */
	public Integer getPreviewImageSize() {
		return this.previewImageSize;
	}

	/**
	 * @param previewImageSize
	 *            the previewImageSize to set
	 */
	public void setPreviewImageSize(Integer previewImageSize) {
		this.previewImageSize = previewImageSize;
	}

	/**
	 * @return the previewCurCnt
	 */
	public Integer getPreviewCurCnt() {
		return this.previewCurCnt;
	}

	/**
	 * @param previewCurCnt
	 *            the previewCurCnt to set
	 */
	public void setPreviewCurCnt(Integer previewCurCnt) {
		this.previewCurCnt = previewCurCnt;
	}

	/**
	 * @return the imageCode
	 */
	public String getImageCode() {
		return this.imageCode;
	}

	/**
	 * @param imageCode
	 *            the imageCode to set
	 */
	public void setImageCode(String imageCode) {
		this.imageCode = imageCode;
	}

	/**
	 * @return the chnlProdId
	 */
	public String getChnlProdId() {
		return this.chnlProdId;
	}

	/**
	 * @param chnlProdId
	 *            the chnlProdId to set
	 */
	public void setChnlProdId(String chnlProdId) {
		this.chnlProdId = chnlProdId;
	}

	/**
	 * @return the sysDate
	 */
	public String getSysDate() {
		return this.sysDate;
	}

	/**
	 * @param sysDate
	 *            the sysDate to set
	 */
	public void setSysDate(String sysDate) {
		this.sysDate = sysDate;
	}

	/**
	 * @return the totalCount
	 */
	public Integer getTotalCount() {
		return this.totalCount;
	}

	/**
	 * @param totalCount
	 *            the totalCount to set
	 */
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @return the prodClsfCd
	 */
	public String getProdClsfCd() {
		return this.prodClsfCd;
	}

	/**
	 * @param prodClsfCd
	 *            the prodClsfCd to set
	 */
	public void setProdClsfCd(String prodClsfCd) {
		this.prodClsfCd = prodClsfCd;
	}

	/**
	 * @return the platClsfCd
	 */
	public String getPlatClsfCd() {
		return this.platClsfCd;
	}

	/**
	 * @param platClsfCd
	 *            the platClsfCd to set
	 */
	public void setPlatClsfCd(String platClsfCd) {
		this.platClsfCd = platClsfCd;
	}

	/**
	 * @return the purchaseId
	 */
	public String getPurchaseId() {
		return this.purchaseId;
	}

	/**
	 * @param purchaseId
	 *            the purchaseId to set
	 */
	public void setPurchaseId(String purchaseId) {
		this.purchaseId = purchaseId;
	}

	/**
	 * @return the purchaseProdId
	 */
	public String getPurchaseProdId() {
		return this.purchaseProdId;
	}

	/**
	 * @param purchaseProdId
	 *            the purchaseProdId to set
	 */
	public void setPurchaseProdId(String purchaseProdId) {
		this.purchaseProdId = purchaseProdId;
	}

	/**
	 * @return the purchaseDt
	 */
	public String getPurchaseDt() {
		return this.purchaseDt;
	}

	/**
	 * @param purchaseDt
	 *            the purchaseDt to set
	 */
	public void setPurchaseDt(String purchaseDt) {
		this.purchaseDt = purchaseDt;
	}

	/**
	 * @return the purchaseState
	 */
	public String getPurchaseState() {
		return this.purchaseState;
	}

	/**
	 * @param purchaseState
	 *            the purchaseState to set
	 */
	public void setPurchaseState(String purchaseState) {
		this.purchaseState = purchaseState;
	}

	/**
	 * @return the purchaseDwldExprDt
	 */
	public String getPurchaseDwldExprDt() {
		return this.purchaseDwldExprDt;
	}

	/**
	 * @param purchaseDwldExprDt
	 *            the purchaseDwldExprDt to set
	 */
	public void setPurchaseDwldExprDt(String purchaseDwldExprDt) {
		this.purchaseDwldExprDt = purchaseDwldExprDt;
	}

	/**
	 * @return the purchasePrice
	 */
	public Integer getPurchasePrice() {
		return this.purchasePrice;
	}

	/**
	 * @param purchasePrice
	 *            the purchasePrice to set
	 */
	public void setPurchasePrice(Integer purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	/**
	 * @return the bpJoinFileNo
	 */
	public String getBpJoinFileNo() {
		return this.bpJoinFileNo;
	}

	/**
	 * @param bpJoinFileNo
	 *            the bpJoinFileNo to set
	 */
	public void setBpJoinFileNo(String bpJoinFileNo) {
		this.bpJoinFileNo = bpJoinFileNo;
	}

	/**
	 * @return the bpJoinFileType
	 */
	public String getBpJoinFileType() {
		return this.bpJoinFileType;
	}

	/**
	 * @param bpJoinFileType
	 *            the bpJoinFileType to set
	 */
	public void setBpJoinFileType(String bpJoinFileType) {
		this.bpJoinFileType = bpJoinFileType;
	}

	/**
	 * @return the expiredDate
	 */
	public String getExpiredDate() {
		return this.expiredDate;
	}

	/**
	 * @param expiredDate
	 *            the expiredDate to set
	 */
	public void setExpiredDate(String expiredDate) {
		this.expiredDate = expiredDate;
	}

	/**
	 * @return the prodChrg
	 */
	public String getProdChrg() {
		return this.prodChrg;
	}

	/**
	 * @param prodChrg
	 *            the prodChrg to set
	 */
	public void setProdChrg(String prodChrg) {
		this.prodChrg = prodChrg;
	}

	/**
	 * @return the useExprDt
	 */
	public String getUseExprDt() {
		return this.useExprDt;
	}

	/**
	 * @param useExprDt
	 *            the useExprDt to set
	 */
	public void setUseExprDt(String useExprDt) {
		this.useExprDt = useExprDt;
	}

	/**
	 * @return the dwldExprDt
	 */
	public String getDwldExprDt() {
		return this.dwldExprDt;
	}

	/**
	 * @param dwldExprDt
	 *            the dwldExprDt to set
	 */
	public void setDwldExprDt(String dwldExprDt) {
		this.dwldExprDt = dwldExprDt;
	}

	/**
	 * @return the deviceKey
	 */
	public String getDeviceKey() {
		return this.deviceKey;
	}

	/**
	 * @param deviceKey
	 *            the deviceKey to set
	 */
	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	/**
	 * @return the deviceType
	 */
	public String getDeviceType() {
		return this.deviceType;
	}

	/**
	 * @param deviceType
	 *            the deviceType to set
	 */
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	/**
	 * @return the deviceSubKey
	 */
	public String getDeviceSubKey() {
		return this.deviceSubKey;
	}

	/**
	 * @param deviceSubKey
	 *            the deviceSubKey to set
	 */
	public void setDeviceSubKey(String deviceSubKey) {
		this.deviceSubKey = deviceSubKey;
	}

	/**
	 * @return the userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            the userKey to set
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return the company
	 */
	public String getCompany() {
		return this.company;
	}

	/**
	 * @param company
	 *            the company to set
	 */
	public void setCompany(String company) {
		this.company = company;
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
	 * @return the fakeYn
	 */
	public String getFakeYn() {
		return this.fakeYn;
	}

	/**
	 * @param fakeYn
	 *            the fakeYn to set
	 */
	public void setFakeYn(String fakeYn) {
		this.fakeYn = fakeYn;
	}

	/**
	 * @return the apkVerCd
	 */
	public String getApkVerCd() {
		return this.apkVerCd;
	}

	/**
	 * @param apkVerCd
	 *            the apkVerCd to set
	 */
	public void setApkVerCd(String apkVerCd) {
		this.apkVerCd = apkVerCd;
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
	 * @return the seedProductId
	 */
	public String getSeedProductId() {
		return this.seedProductId;
	}

	/**
	 * @param seedProductId
	 *            the seedProductId to set
	 */
	public void setSeedProductId(String seedProductId) {
		this.seedProductId = seedProductId;
	}

	/**
	 * @return the gameCentrId
	 */
	public String getGameCentrId() {
		return this.gameCentrId;
	}

	/**
	 * @param gameCentrId
	 *            the gameCentrId to set
	 */
	public void setGameCentrId(String gameCentrId) {
		this.gameCentrId = gameCentrId;
	}

	/**
	 * @return the gameCentrVerCd
	 */
	public String getGameCentrVerCd() {
		return this.gameCentrVerCd;
	}

	/**
	 * @param gameCentrVerCd
	 *            the gameCentrVerCd to set
	 */
	public void setGameCentrVerCd(String gameCentrVerCd) {
		this.gameCentrVerCd = gameCentrVerCd;
	}

	/**
	 * @return the seedCaseRefCd
	 */
	public String getSeedCaseRefCd() {
		return this.seedCaseRefCd;
	}

	/**
	 * @param seedCaseRefCd
	 *            the seedCaseRefCd to set
	 */
	public void setSeedCaseRefCd(String seedCaseRefCd) {
		this.seedCaseRefCd = seedCaseRefCd;
	}

	/**
	 * @return the supportedOs
	 */
	public String getSupportedOs() {
		return this.supportedOs;
	}

	/**
	 * @param supportedOs
	 *            the supportedOs to set
	 */
	public void setSupportedOs(String supportedOs) {
		this.supportedOs = supportedOs;
	}

	/**
	 * @return the seedUseYn
	 */
	public String getSeedUseYn() {
		return this.seedUseYn;
	}

	/**
	 * @param seedUseYn
	 *            the seedUseYn to set
	 */
	public void setSeedUseYn(String seedUseYn) {
		this.seedUseYn = seedUseYn;
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
	 * @return the storeProdChrg
	 */
	public String getStoreProdChrg() {
		return this.storeProdChrg;
	}

	/**
	 * @param storeProdChrg
	 *            the storeProdChrg to set
	 */
	public void setStoreProdChrg(String storeProdChrg) {
		this.storeProdChrg = storeProdChrg;
	}

	/**
	 * @return the storeUsePeriodUnitCd
	 */
	public String getStoreUsePeriodUnitCd() {
		return this.storeUsePeriodUnitCd;
	}

	/**
	 * @param storeUsePeriodUnitCd
	 *            the storeUsePeriodUnitCd to set
	 */
	public void setStoreUsePeriodUnitCd(String storeUsePeriodUnitCd) {
		this.storeUsePeriodUnitCd = storeUsePeriodUnitCd;
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
	 * @return the playProdChrg
	 */
	public String getPlayProdChrg() {
		return this.playProdChrg;
	}

	/**
	 * @param playProdChrg
	 *            the playProdChrg to set
	 */
	public void setPlayProdChrg(String playProdChrg) {
		this.playProdChrg = playProdChrg;
	}

	/**
	 * @return the playUsePeriodUnitCd
	 */
	public String getPlayUsePeriodUnitCd() {
		return this.playUsePeriodUnitCd;
	}

	/**
	 * @param playUsePeriodUnitCd
	 *            the playUsePeriodUnitCd to set
	 */
	public void setPlayUsePeriodUnitCd(String playUsePeriodUnitCd) {
		this.playUsePeriodUnitCd = playUsePeriodUnitCd;
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
	 * @return the usePeriodNm
	 */
	public String getUsePeriodNm() {
		return this.usePeriodNm;
	}

	/**
	 * @param usePeriodNm
	 *            the usePeriodNm to set
	 */
	public void setUsePeriodNm(String usePeriodNm) {
		this.usePeriodNm = usePeriodNm;
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
	 * @return the hdcpYn
	 */
	public String getHdcpYn() {
		return this.hdcpYn;
	}

	/**
	 * @param hdcpYn
	 *            the hdcpYn to set
	 */
	public void setHdcpYn(String hdcpYn) {
		this.hdcpYn = hdcpYn;
	}

	/**
	 * @return the btvYn
	 */
	public String getBtvYn() {
		return this.btvYn;
	}

	/**
	 * @param btvYn
	 *            the btvYn to set
	 */
	public void setBtvYn(String btvYn) {
		this.btvYn = btvYn;
	}

	/**
	 * @return the epsdPlayTm
	 */
	public String getEpsdPlayTm() {
		return this.epsdPlayTm;
	}

	/**
	 * @param epsdPlayTm
	 *            the epsdPlayTm to set
	 */
	public void setEpsdPlayTm(String epsdPlayTm) {
		this.epsdPlayTm = epsdPlayTm;
	}

	/**
	 * @return the chapterUnit
	 */
	public String getChapterUnit() {
		return this.chapterUnit;
	}

	/**
	 * @param chapterUnit
	 *            the chapterUnit to set
	 */
	public void setChapterUnit(String chapterUnit) {
		this.chapterUnit = chapterUnit;
	}

	/**
	 * @return the nmBtvCid
	 */
	public String getNmBtvCid() {
		return this.nmBtvCid;
	}

	/**
	 * @param nmBtvCid
	 *            the nmBtvCid to set
	 */
	public void setNmBtvCid(String nmBtvCid) {
		this.nmBtvCid = nmBtvCid;
	}

	/**
	 * @return the nmDpPicRatio
	 */
	public String getNmDpPicRatio() {
		return this.nmDpPicRatio;
	}

	/**
	 * @param nmDpPicRatio
	 *            the nmDpPicRatio to set
	 */
	public void setNmDpPicRatio(String nmDpPicRatio) {
		this.nmDpPicRatio = nmDpPicRatio;
	}

	/**
	 * @return the nmDpPixel
	 */
	public String getNmDpPixel() {
		return this.nmDpPixel;
	}

	/**
	 * @param nmDpPixel
	 *            the nmDpPixel to set
	 */
	public void setNmDpPixel(String nmDpPixel) {
		this.nmDpPixel = nmDpPixel;
	}

	/**
	 * @return the nmSubContsId
	 */
	public String getNmSubContsId() {
		return this.nmSubContsId;
	}

	/**
	 * @param nmSubContsId
	 *            the nmSubContsId to set
	 */
	public void setNmSubContsId(String nmSubContsId) {
		this.nmSubContsId = nmSubContsId;
	}

	/**
	 * @return the nmFileSize
	 */
	public Integer getNmFileSize() {
		return this.nmFileSize;
	}

	/**
	 * @param nmFileSize
	 *            the nmFileSize to set
	 */
	public void setNmFileSize(Integer nmFileSize) {
		this.nmFileSize = nmFileSize;
	}

	/**
	 * @return the nmProdVer
	 */
	public String getNmProdVer() {
		return this.nmProdVer;
	}

	/**
	 * @param nmProdVer
	 *            the nmProdVer to set
	 */
	public void setNmProdVer(String nmProdVer) {
		this.nmProdVer = nmProdVer;
	}

	/**
	 * @return the sdBtvCid
	 */
	public String getSdBtvCid() {
		return this.sdBtvCid;
	}

	/**
	 * @param sdBtvCid
	 *            the sdBtvCid to set
	 */
	public void setSdBtvCid(String sdBtvCid) {
		this.sdBtvCid = sdBtvCid;
	}

	/**
	 * @return the sdDpPicRatio
	 */
	public String getSdDpPicRatio() {
		return this.sdDpPicRatio;
	}

	/**
	 * @param sdDpPicRatio
	 *            the sdDpPicRatio to set
	 */
	public void setSdDpPicRatio(String sdDpPicRatio) {
		this.sdDpPicRatio = sdDpPicRatio;
	}

	/**
	 * @return the sdDpPixel
	 */
	public String getSdDpPixel() {
		return this.sdDpPixel;
	}

	/**
	 * @param sdDpPixel
	 *            the sdDpPixel to set
	 */
	public void setSdDpPixel(String sdDpPixel) {
		this.sdDpPixel = sdDpPixel;
	}

	/**
	 * @return the sdSubContsId
	 */
	public String getSdSubContsId() {
		return this.sdSubContsId;
	}

	/**
	 * @param sdSubContsId
	 *            the sdSubContsId to set
	 */
	public void setSdSubContsId(String sdSubContsId) {
		this.sdSubContsId = sdSubContsId;
	}

	/**
	 * @return the sdFileSize
	 */
	public Integer getSdFileSize() {
		return this.sdFileSize;
	}

	/**
	 * @param sdFileSize
	 *            the sdFileSize to set
	 */
	public void setSdFileSize(Integer sdFileSize) {
		this.sdFileSize = sdFileSize;
	}

	/**
	 * @return the sdProdVer
	 */
	public String getSdProdVer() {
		return this.sdProdVer;
	}

	/**
	 * @param sdProdVer
	 *            the sdProdVer to set
	 */
	public void setSdProdVer(String sdProdVer) {
		this.sdProdVer = sdProdVer;
	}

	/**
	 * @return the hdBtvCid
	 */
	public String getHdBtvCid() {
		return this.hdBtvCid;
	}

	/**
	 * @param hdBtvCid
	 *            the hdBtvCid to set
	 */
	public void setHdBtvCid(String hdBtvCid) {
		this.hdBtvCid = hdBtvCid;
	}

	/**
	 * @return the hdDpPicRatio
	 */
	public String getHdDpPicRatio() {
		return this.hdDpPicRatio;
	}

	/**
	 * @param hdDpPicRatio
	 *            the hdDpPicRatio to set
	 */
	public void setHdDpPicRatio(String hdDpPicRatio) {
		this.hdDpPicRatio = hdDpPicRatio;
	}

	/**
	 * @return the hdDpPixel
	 */
	public String getHdDpPixel() {
		return this.hdDpPixel;
	}

	/**
	 * @param hdDpPixel
	 *            the hdDpPixel to set
	 */
	public void setHdDpPixel(String hdDpPixel) {
		this.hdDpPixel = hdDpPixel;
	}

	/**
	 * @return the hdSubContsId
	 */
	public String getHdSubContsId() {
		return this.hdSubContsId;
	}

	/**
	 * @param hdSubContsId
	 *            the hdSubContsId to set
	 */
	public void setHdSubContsId(String hdSubContsId) {
		this.hdSubContsId = hdSubContsId;
	}

	/**
	 * @return the hdFileSize
	 */
	public Integer getHdFileSize() {
		return this.hdFileSize;
	}

	/**
	 * @param hdFileSize
	 *            the hdFileSize to set
	 */
	public void setHdFileSize(Integer hdFileSize) {
		this.hdFileSize = hdFileSize;
	}

	/**
	 * @return the hdProdVer
	 */
	public String getHdProdVer() {
		return this.hdProdVer;
	}

	/**
	 * @param hdProdVer
	 *            the hdProdVer to set
	 */
	public void setHdProdVer(String hdProdVer) {
		this.hdProdVer = hdProdVer;
	}

	/**
	 * @return the dwldAreaLimtYn
	 */
	public String getDwldAreaLimtYn() {
		return this.dwldAreaLimtYn;
	}

	/**
	 * @param dwldAreaLimtYn
	 *            the dwldAreaLimtYn to set
	 */
	public void setDwldAreaLimtYn(String dwldAreaLimtYn) {
		this.dwldAreaLimtYn = dwldAreaLimtYn;
	}

	/**
	 * @return the strmNetworkCd
	 */
	public String getStrmNetworkCd() {
		return this.strmNetworkCd;
	}

	/**
	 * @param strmNetworkCd
	 *            the strmNetworkCd to set
	 */
	public void setStrmNetworkCd(String strmNetworkCd) {
		this.strmNetworkCd = strmNetworkCd;
	}

	/**
	 * @return the dwldNetworkCd
	 */
	public String getDwldNetworkCd() {
		return this.dwldNetworkCd;
	}

	/**
	 * @param dwldNetworkCd
	 *            the dwldNetworkCd to set
	 */
	public void setDwldNetworkCd(String dwldNetworkCd) {
		this.dwldNetworkCd = dwldNetworkCd;
	}

	/**
	 * @return the nmFilePath
	 */
	public String getNmFilePath() {
		return this.nmFilePath;
	}

	/**
	 * @param nmFilePath
	 *            the nmFilePath to set
	 */
	public void setNmFilePath(String nmFilePath) {
		this.nmFilePath = nmFilePath;
	}

	/**
	 * @return the sdFilePath
	 */
	public String getSdFilePath() {
		return this.sdFilePath;
	}

	/**
	 * @param sdFilePath
	 *            the sdFilePath to set
	 */
	public void setSdFilePath(String sdFilePath) {
		this.sdFilePath = sdFilePath;
	}

	/**
	 * @return the hdFilePath
	 */
	public String getHdFilePath() {
		return this.hdFilePath;
	}

	/**
	 * @param hdFilePath
	 *            the hdFilePath to set
	 */
	public void setHdFilePath(String hdFilePath) {
		this.hdFilePath = hdFilePath;
	}

	/**
	 * @return the chnlProdNm
	 */
	public String getChnlProdNm() {
		return this.chnlProdNm;
	}

	/**
	 * @param chnlProdNm
	 *            the chnlProdNm to set
	 */
	public void setChnlProdNm(String chnlProdNm) {
		this.chnlProdNm = chnlProdNm;
	}

	/**
	 * @return the genreCd
	 */
	public String getGenreCd() {
		return this.genreCd;
	}

	/**
	 * @param genreCd
	 *            the genreCd to set
	 */
	public void setGenreCd(String genreCd) {
		this.genreCd = genreCd;
	}

	/**
	 * @return the genreCd2
	 */
	public String getGenreCd2() {
		return this.genreCd2;
	}

	/**
	 * @param genreCd2
	 *            the genreCd2 to set
	 */
	public void setGenreCd2(String genreCd2) {
		this.genreCd2 = genreCd2;
	}

	/**
	 * @return the bookPageCnt
	 */
	public Integer getBookPageCnt() {
		return this.bookPageCnt;
	}

	/**
	 * @param bookPageCnt
	 *            the bookPageCnt to set
	 */
	public void setBookPageCnt(Integer bookPageCnt) {
		this.bookPageCnt = bookPageCnt;
	}

	/**
	 * @return the colorSprtYn
	 */
	public String getColorSprtYn() {
		return this.colorSprtYn;
	}

	/**
	 * @param colorSprtYn
	 *            the colorSprtYn to set
	 */
	public void setColorSprtYn(String colorSprtYn) {
		this.colorSprtYn = colorSprtYn;
	}

	/**
	 * @return the isbn
	 */
	public String getIsbn() {
		return this.isbn;
	}

	/**
	 * @param isbn
	 *            the isbn to set
	 */
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	/**
	 * @return the mgzinCoverYn
	 */
	public String getMgzinCoverYn() {
		return this.mgzinCoverYn;
	}

	/**
	 * @param mgzinCoverYn
	 *            the mgzinCoverYn to set
	 */
	public void setMgzinCoverYn(String mgzinCoverYn) {
		this.mgzinCoverYn = mgzinCoverYn;
	}

	/**
	 * @return the dlmImagePath
	 */
	public String getDlmImagePath() {
		return this.dlmImagePath;
	}

	/**
	 * @param dlmImagePath
	 *            the dlmImagePath to set
	 */
	public void setDlmImagePath(String dlmImagePath) {
		this.dlmImagePath = dlmImagePath;
	}

	/**
	 * @return the iconYn
	 */
	public String getIconYn() {
		return this.iconYn;
	}

	/**
	 * @param iconYn
	 *            the iconYn to set
	 */
	public void setIconYn(String iconYn) {
		this.iconYn = iconYn;
	}

	/**
	 * @return the updDt
	 */
	public String getUpdDt() {
		return this.updDt;
	}

	/**
	 * @param updDt
	 *            the updDt to set
	 */
	public void setUpdDt(String updDt) {
		this.updDt = updDt;
	}

	/**
	 * @return the musicId
	 */
	public String getMusicId() {
		return this.musicId;
	}

	/**
	 * @param musicId
	 *            the musicId to set
	 */
	public void setMusicId(String musicId) {
		this.musicId = musicId;
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
	 * @return the outsdContentsId
	 */
	public String getOutsdContentsId() {
		return this.outsdContentsId;
	}

	/**
	 * @param outsdContentsId
	 *            the outsdContentsId to set
	 */
	public void setOutsdContentsId(String outsdContentsId) {
		this.outsdContentsId = outsdContentsId;
	}

	/**
	 * @return the fileSizeH
	 */
	public Integer getFileSizeH() {
		return this.fileSizeH;
	}

	/**
	 * @param fileSizeH
	 *            the fileSizeH to set
	 */
	public void setFileSizeH(Integer fileSizeH) {
		this.fileSizeH = fileSizeH;
	}

	/**
	 * @return the artistId
	 */
	public String getArtistId() {
		return this.artistId;
	}

	/**
	 * @param artistId
	 *            the artistId to set
	 */
	public void setArtistId(String artistId) {
		this.artistId = artistId;
	}

	/**
	 * @return the artistNm
	 */
	public String getArtistNm() {
		return this.artistNm;
	}

	/**
	 * @param artistNm
	 *            the artistNm to set
	 */
	public void setArtistNm(String artistNm) {
		this.artistNm = artistNm;
	}

	/**
	 * @return the debutDay
	 */
	public String getDebutDay() {
		return this.debutDay;
	}

	/**
	 * @param debutDay
	 *            the debutDay to set
	 */
	public void setDebutDay(String debutDay) {
		this.debutDay = debutDay;
	}

	/**
	 * @return the debutMusicNm
	 */
	public String getDebutMusicNm() {
		return this.debutMusicNm;
	}

	/**
	 * @param debutMusicNm
	 *            the debutMusicNm to set
	 */
	public void setDebutMusicNm(String debutMusicNm) {
		this.debutMusicNm = debutMusicNm;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return this.country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
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
	 * @return the brandNm
	 */
	public String getBrandNm() {
		return this.brandNm;
	}

	/**
	 * @param brandNm
	 *            the brandNm to set
	 */
	public void setBrandNm(String brandNm) {
		this.brandNm = brandNm;
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

	/**
	 * @return the catalogNm
	 */
	public String getCatalogNm() {
		return this.catalogNm;
	}

	/**
	 * @param catalogNm
	 *            the catalogNm to set
	 */
	public void setCatalogNm(String catalogNm) {
		this.catalogNm = catalogNm;
	}

	/**
	 * @return the specialSale
	 */
	public String getSpecialSale() {
		return this.specialSale;
	}

	/**
	 * @param specialSale
	 *            the specialSale to set
	 */
	public void setSpecialSale(String specialSale) {
		this.specialSale = specialSale;
	}

	/**
	 * @return the soldOut
	 */
	public String getSoldOut() {
		return this.soldOut;
	}

	/**
	 * @param soldOut
	 *            the soldOut to set
	 */
	public void setSoldOut(String soldOut) {
		this.soldOut = soldOut;
	}

	/**
	 * @return the planId
	 */
	public String getPlanId() {
		return this.planId;
	}

	/**
	 * @param planId
	 *            the planId to set
	 */
	public void setPlanId(String planId) {
		this.planId = planId;
	}

	/**
	 * @return the planNm
	 */
	public String getPlanNm() {
		return this.planNm;
	}

	/**
	 * @param planNm
	 *            the planNm to set
	 */
	public void setPlanNm(String planNm) {
		this.planNm = planNm;
	}

	/**
	 * @return the subTitlNm
	 */
	public String getSubTitlNm() {
		return this.subTitlNm;
	}

	/**
	 * @param subTitlNm
	 *            the subTitlNm to set
	 */
	public void setSubTitlNm(String subTitlNm) {
		this.subTitlNm = subTitlNm;
	}

	/**
	 * @return the planStartDt
	 */
	public String getPlanStartDt() {
		return this.planStartDt;
	}

	/**
	 * @param planStartDt
	 *            the planStartDt to set
	 */
	public void setPlanStartDt(String planStartDt) {
		this.planStartDt = planStartDt;
	}

	/**
	 * @return the planEndDt
	 */
	public String getPlanEndDt() {
		return this.planEndDt;
	}

	/**
	 * @param planEndDt
	 *            the planEndDt to set
	 */
	public void setPlanEndDt(String planEndDt) {
		this.planEndDt = planEndDt;
	}

	/**
	 * @return the przwnerAnnoDt
	 */
	public String getPrzwnerAnnoDt() {
		return this.przwnerAnnoDt;
	}

	/**
	 * @param przwnerAnnoDt
	 *            the przwnerAnnoDt to set
	 */
	public void setPrzwnerAnnoDt(String przwnerAnnoDt) {
		this.przwnerAnnoDt = przwnerAnnoDt;
	}

	/**
	 * @return the planGiftNm
	 */
	public String getPlanGiftNm() {
		return this.planGiftNm;
	}

	/**
	 * @param planGiftNm
	 *            the planGiftNm to set
	 */
	public void setPlanGiftNm(String planGiftNm) {
		this.planGiftNm = planGiftNm;
	}

	/**
	 * @return the allow
	 */
	public String getAllow() {
		return this.allow;
	}

	/**
	 * @param allow
	 *            the allow to set
	 */
	public void setAllow(String allow) {
		this.allow = allow;
	}

	/**
	 * @return the b2bProdYn
	 */
	public String getB2bProdYn() {
		return this.b2bProdYn;
	}

	/**
	 * @param b2bProdYn
	 *            the b2bProdYn to set
	 */
	public void setB2bProdYn(String b2bProdYn) {
		this.b2bProdYn = b2bProdYn;
	}

	/**
	 * @return the mthMaxCnt
	 */
	public String getMthMaxCnt() {
		return this.mthMaxCnt;
	}

	/**
	 * @param mthMaxCnt
	 *            the mthMaxCnt to set
	 */
	public void setMthMaxCnt(String mthMaxCnt) {
		this.mthMaxCnt = mthMaxCnt;
	}

	/**
	 * @return the dlyMaxCnt
	 */
	public String getDlyMaxCnt() {
		return this.dlyMaxCnt;
	}

	/**
	 * @param dlyMaxCnt
	 *            the dlyMaxCnt to set
	 */
	public void setDlyMaxCnt(String dlyMaxCnt) {
		this.dlyMaxCnt = dlyMaxCnt;
	}

	/**
	 * @return the mthUsrMaxCnt
	 */
	public String getMthUsrMaxCnt() {
		return this.mthUsrMaxCnt;
	}

	/**
	 * @param mthUsrMaxCnt
	 *            the mthUsrMaxCnt to set
	 */
	public void setMthUsrMaxCnt(String mthUsrMaxCnt) {
		this.mthUsrMaxCnt = mthUsrMaxCnt;
	}

	/**
	 * @return the dlyUsrMaxCnt
	 */
	public String getDlyUsrMaxCnt() {
		return this.dlyUsrMaxCnt;
	}

	/**
	 * @param dlyUsrMaxCnt
	 *            the dlyUsrMaxCnt to set
	 */
	public void setDlyUsrMaxCnt(String dlyUsrMaxCnt) {
		this.dlyUsrMaxCnt = dlyUsrMaxCnt;
	}

	/**
	 * @return the eachMaxCnt
	 */
	public String getEachMaxCnt() {
		return this.eachMaxCnt;
	}

	/**
	 * @param eachMaxCnt
	 *            the eachMaxCnt to set
	 */
	public void setEachMaxCnt(String eachMaxCnt) {
		this.eachMaxCnt = eachMaxCnt;
	}

	/**
	 * @return the usePlac
	 */
	public String getUsePlac() {
		return this.usePlac;
	}

	/**
	 * @param usePlac
	 *            the usePlac to set
	 */
	public void setUsePlac(String usePlac) {
		this.usePlac = usePlac;
	}

	/**
	 * @return the useLimtDesc
	 */
	public String getUseLimtDesc() {
		return this.useLimtDesc;
	}

	/**
	 * @param useLimtDesc
	 *            the useLimtDesc to set
	 */
	public void setUseLimtDesc(String useLimtDesc) {
		this.useLimtDesc = useLimtDesc;
	}

	/**
	 * @return the noticeMatt
	 */
	public String getNoticeMatt() {
		return this.noticeMatt;
	}

	/**
	 * @param noticeMatt
	 *            the noticeMatt to set
	 */
	public void setNoticeMatt(String noticeMatt) {
		this.noticeMatt = noticeMatt;
	}

	/**
	 * @return the prchsCancelDrbkReason
	 */
	public String getPrchsCancelDrbkReason() {
		return this.prchsCancelDrbkReason;
	}

	/**
	 * @param prchsCancelDrbkReason
	 *            the prchsCancelDrbkReason to set
	 */
	public void setPrchsCancelDrbkReason(String prchsCancelDrbkReason) {
		this.prchsCancelDrbkReason = prchsCancelDrbkReason;
	}

	/**
	 * @return the opt1Nm
	 */
	public String getOpt1Nm() {
		return this.opt1Nm;
	}

	/**
	 * @param opt1Nm
	 *            the opt1Nm to set
	 */
	public void setOpt1Nm(String opt1Nm) {
		this.opt1Nm = opt1Nm;
	}

	/**
	 * @return the optPdNm
	 */
	public String getOptPdNm() {
		return this.optPdNm;
	}

	/**
	 * @param optPdNm
	 *            the optPdNm to set
	 */
	public void setOptPdNm(String optPdNm) {
		this.optPdNm = optPdNm;
	}

	/**
	 * @return the subYn
	 */
	public String getSubYn() {
		return this.subYn;
	}

	/**
	 * @param subYn
	 *            the subYn to set
	 */
	public void setSubYn(String subYn) {
		this.subYn = subYn;
	}

	/**
	 * @return the srcContentId
	 */
	public String getSrcContentId() {
		return this.srcContentId;
	}

	/**
	 * @param srcContentId
	 *            the srcContentId to set
	 */
	public void setSrcContentId(String srcContentId) {
		this.srcContentId = srcContentId;
	}

	/**
	 * @return the tagCd
	 */
	public String getTagCd() {
		return this.tagCd;
	}

	/**
	 * @param tagCd
	 *            the tagCd to set
	 */
	public void setTagCd(String tagCd) {
		this.tagCd = tagCd;
	}

	/**
	 * @return the tagNm
	 */
	public String getTagNm() {
		return this.tagNm;
	}

	/**
	 * @param tagNm
	 *            the tagNm to set
	 */
	public void setTagNm(String tagNm) {
		this.tagNm = tagNm;
	}

	/**
	 * @return the tagTypeCd
	 */
	public String getTagTypeCd() {
		return this.tagTypeCd;
	}

	/**
	 * @param tagTypeCd
	 *            the tagTypeCd to set
	 */
	public void setTagTypeCd(String tagTypeCd) {
		this.tagTypeCd = tagTypeCd;
	}

	/**
	 * @return the prodAlias
	 */
	public String getProdAlias() {
		return this.prodAlias;
	}

	/**
	 * @param prodAlias
	 *            the prodAlias to set
	 */
	public void setProdAlias(String prodAlias) {
		this.prodAlias = prodAlias;
	}

	/**
	 * @return the cmpxProdClsfCd
	 */
	public String getCmpxProdClsfCd() {
		return this.cmpxProdClsfCd;
	}

	/**
	 * @param cmpxProdClsfCd
	 *            the cmpxProdClsfCd to set
	 */
	public void setCmpxProdClsfCd(String cmpxProdClsfCd) {
		this.cmpxProdClsfCd = cmpxProdClsfCd;
	}

	/**
	 * @return the autoApprYn
	 */
	public String getAutoApprYn() {
		return this.autoApprYn;
	}

	/**
	 * @param autoApprYn
	 *            the autoApprYn to set
	 */
	public void setAutoApprYn(String autoApprYn) {
		this.autoApprYn = autoApprYn;
	}

	/**
	 * @return the maxSaleCnt
	 */
	public String getMaxSaleCnt() {
		return this.maxSaleCnt;
	}

	/**
	 * @param maxSaleCnt
	 *            the maxSaleCnt to set
	 */
	public void setMaxSaleCnt(String maxSaleCnt) {
		this.maxSaleCnt = maxSaleCnt;
	}

	/**
	 * @return the salePocCd
	 */
	public String getSalePocCd() {
		return this.salePocCd;
	}

	/**
	 * @param salePocCd
	 *            the salePocCd to set
	 */
	public void setSalePocCd(String salePocCd) {
		this.salePocCd = salePocCd;
	}

	/**
	 * @return the dupPrchsLimtYn
	 */
	public String getDupPrchsLimtYn() {
		return this.dupPrchsLimtYn;
	}

	/**
	 * @param dupPrchsLimtYn
	 *            the dupPrchsLimtYn to set
	 */
	public void setDupPrchsLimtYn(String dupPrchsLimtYn) {
		this.dupPrchsLimtYn = dupPrchsLimtYn;
	}

	/**
	 * @return the bannerFilePath
	 */
	public String getBannerFilePath() {
		return this.bannerFilePath;
	}

	/**
	 * @param bannerFilePath
	 *            the bannerFilePath to set
	 */
	public void setBannerFilePath(String bannerFilePath) {
		this.bannerFilePath = bannerFilePath;
	}

	/**
	 * @return the thumbnailFilePath
	 */
	public String getThumbnailFilePath() {
		return this.thumbnailFilePath;
	}

	/**
	 * @param thumbnailFilePath
	 *            the thumbnailFilePath to set
	 */
	public void setThumbnailFilePath(String thumbnailFilePath) {
		this.thumbnailFilePath = thumbnailFilePath;
	}

	/**
	 * @return the prodIntrDscr
	 */
	public String getProdIntrDscr() {
		return this.prodIntrDscr;
	}

	/**
	 * @param prodIntrDscr
	 *            the prodIntrDscr to set
	 */
	public void setProdIntrDscr(String prodIntrDscr) {
		this.prodIntrDscr = prodIntrDscr;
	}

	/**
	 * @return the prodStatusCd
	 */
	public String getProdStatusCd() {
		return this.prodStatusCd;
	}

	/**
	 * @param prodStatusCd
	 *            the prodStatusCd to set
	 */
	public void setProdStatusCd(String prodStatusCd) {
		this.prodStatusCd = prodStatusCd;
	}

}
