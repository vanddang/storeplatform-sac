package com.skplanet.storeplatform.sac.client.internal.display.localsci.vo;

import java.util.List;

import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationListForProductSacRes.SellerMbrInfoSac;

/**
 * ProductInfo
 * 
 * Updated on : 2014. 2. 27. Updated by : 오승민, 인크로스
 */
public class ProductInfo {
	// ////////////////////////// 공통 변수 ////////////////////////////
	private String prodId;
	private String partProdId;
	private String prodNm;
	private String fileType = "thumbnail";
	private String filePath;
	private String topMenuId;
	private String topMenuNm;
	private String topMenuType;
	private String menuId;
	private String menuNm;
	private String metaClsfCd;
	private String regDt;
	private String sellerMbrNo;
	private String expoSellerNm;
	private String expoSellerTelNo;
	private String expoSellerEmail;
	private String chapterUnit;
	private String chapter;
	private String prodClsfCd;
	private String prodGrdCd;
	private String dwldStrmClsfCd; // DWLD_STRM_CLSF_CD
	private String possLendClsfCd;
	private String usePeriodUnitCd;
	private String usePeriod;
	private String drmYn;
	private String iabYn;
	private String dcdSprtCd;
	private String chnlProdStatusCd;
	private String cid;
	private String prodStatusCd;
	private String seriesYn;
	private String contentsTypeCd;
	private String plus19Yn;

	// ////////////////////////// App 전용 변수 ////////////////////////////
	private String aid;
	private String pkgNm;
	private String pkgVerCd;
	private String apkInfo;
	private String partParentClsfCd;
	private String platClsfCd;
	private String seedCaseRefCd;
	private String launcherYn;

	// ////////////////////////// Music 전용 변수 ////////////////////////////
	private String artistNm;
	private String album;

	// ////////////////////////// VOD 전용 변수 ////////////////////////////
	private String playTm;
	private String hdcpYn;
	private String hdvYn;
	private String btvYn;
	private String epsdImgPath; // episode 이미지 패스
    private String chnlCompNm;
    private String agencyNm;
    private String availablePlayer;

	// ////////////////////////// Ebook/Comic 전용 변수 ////////////////////////////
	private String author;
	private String painter;
	private String translator;
	private String publisher;
	private String issueDay;
	private String bookVersion;
	private String scid;
	private String bookType;
	private String bookPageCnt;
	private String bookClsfCd;
	private String verticalYn;		// 웹툰 원고 타입
	private String bookStatus;
	private String bookContentsCnt; // 단행 건수
	private String seriesContentsCnt; // 연재 건수
	private String mgzinContentsCnt; // 잡지 건수

	// ////////////////////////// Shopping 전용 변수 ////////////////////////////
	private String catalogId;
	private String brandId;
	private String brandNm;
	private String phoneSprtYn;
	private String couponType;
	private String couponId;
	private String prodCaseCd;
	private String b2bProdYn;
	private String opt1Nm;
	private String opt2Nm;
	private String couponSendType;

	// ////////////////////////// Inapp 전용 변수 ////////////////////////////
	/**
	 * Inapp ID.
	 */
	private String inAppId;

	/**
	 * 부분 유료화 상품 유형.
	 */
	private String prodCase;
	/**
	 * 부분 유료화 상품 종류.
	 */
	private String prodKind;

	/**
	 * 모상품 명.
	 */
	private String parentProdNm;

	// ////////////////////////// 컬러링/벨상품 전용 변수 ////////////////////////////
	private String songId;
	private String ringType;
	private String quality;

	// ////////////////////////// 정액권 상품 전용 변수 ////////////////////////////
	private String autoApprYn;
	private String kind;
	private String planType;
	private String cmpxProdBookClsfCd;
	private List<MapgProdMeta> mapgProdIdList;

	// ////////////////////////// 회원 판매자 정보 전용 변수 ////////////////////////////
	private SellerMbrInfoSac sellerMbrInfoSac;

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

	public String getEpsdImgPath() {
		return this.epsdImgPath;
	}

	public void setEpsdImgPath(String epsdImgPath) {
		this.epsdImgPath = epsdImgPath;
	}

	/**
	 * @return the fileType
	 */
	public String getFileType() {
		return this.fileType;
	}

	/**
	 * @param fileType
	 *            the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
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
	 * @return the topMenuType
	 */
	public String getTopMenuType() {
		return this.topMenuType;
	}

	/**
	 * @param topMenuType
	 *            the topMenuType to set
	 */
	public void setTopMenuType(String topMenuType) {
		this.topMenuType = topMenuType;
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

	public String getDwldStrmClsfCd() {
		return this.dwldStrmClsfCd;
	}

	public void setDwldStrmClsfCd(String dwldStrmClsfCd) {
		this.dwldStrmClsfCd = dwldStrmClsfCd;
	}

	/**
	 * @return the possLendClsfCd
	 */
	public String getPossLendClsfCd() {
		return this.possLendClsfCd;
	}

	/**
	 * @param possLendClsfCd
	 *            the possLendClsfCd to set
	 */
	public void setPossLendClsfCd(String possLendClsfCd) {
		this.possLendClsfCd = possLendClsfCd;
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
	 * @return the iabYn
	 */
	public String getIabYn() {
		return this.iabYn;
	}

	/**
	 * @param iabYn
	 *            the iabYn to set
	 */
	public void setIabYn(String iabYn) {
		this.iabYn = iabYn;
	}

	/**
	 * @return the dcdSprtCd
	 */
	public String getDcdSprtCd() {
		return this.dcdSprtCd;
	}

	/**
	 * @param dcdSprtCd
	 *            the dcdSprtCd to set
	 */
	public void setDcdSprtCd(String dcdSprtCd) {
		this.dcdSprtCd = dcdSprtCd;
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
	 * @return the pkgNm
	 */
	public String getPkgNm() {
		return this.pkgNm;
	}

	/**
	 * @param pkgNm
	 *            the pkgNm to set
	 */
	public void setPkgNm(String pkgNm) {
		this.pkgNm = pkgNm;
	}

	/**
	 * @return the pkgVerCd
	 */
	public String getPkgVerCd() {
		return this.pkgVerCd;
	}

	/**
	 * @param pkgVerCd
	 *            the pkgVerCd to set
	 */
	public void setPkgVerCd(String pkgVerCd) {
		this.pkgVerCd = pkgVerCd;
	}

	/**
	 * @return the apkInfo
	 */
	public String getApkInfo() {
		return this.apkInfo;
	}

	/**
	 * @param apkInfo
	 *            the apkInfo to set
	 */
	public void setApkInfo(String apkInfo) {
		if (apkInfo != null) {
			String[] apkInfoArr = apkInfo.split(",");
			if (apkInfoArr.length == 2) {
				this.pkgNm = apkInfoArr[0];
				this.pkgVerCd = apkInfoArr[1];
			}
		}
		this.apkInfo = apkInfo;
	}

	public String getLauncherYn() {
		return this.launcherYn;
	}

	public void setLauncherYn(String launcherYn) {
		this.launcherYn = launcherYn;
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
	 * @return the album
	 */
	public String getAlbum() {
		return this.album;
	}

	/**
	 * @param album
	 *            the album to set
	 */
	public void setAlbum(String album) {
		this.album = album;
	}

	/**
	 * @return the playTm
	 */
	public String getPlayTm() {
		return this.playTm;
	}

	/**
	 * @param playTm
	 *            the playTm to set
	 */
	public void setPlayTm(String playTm) {
		this.playTm = playTm;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return this.author;
	}

	/**
	 * @param author
	 *            the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return the painter
	 */
	public String getPainter() {
		return this.painter;
	}

	/**
	 * @param painter
	 *            the painter to set
	 */
	public void setPainter(String painter) {
		this.painter = painter;
	}

	/**
	 * @return the translator
	 */
	public String getTranslator() {
		return this.translator;
	}

	/**
	 * @param translator
	 *            the translator to set
	 */
	public void setTranslator(String translator) {
		this.translator = translator;
	}

	/**
	 * @return the publisher
	 */
	public String getPublisher() {
		return this.publisher;
	}

	/**
	 * @param publisher
	 *            the publisher to set
	 */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
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
	 * @return the bookVersion
	 */
	public String getBookVersion() {
		return this.bookVersion;
	}

	/**
	 * @param bookVersion
	 *            the bookVersion to set
	 */
	public void setBookVersion(String bookVersion) {
		this.bookVersion = bookVersion;
	}

	/**
	 * @return the scid
	 */
	public String getScid() {
		return this.scid;
	}

	/**
	 * @param scid
	 *            the scid to set
	 */
	public void setScid(String scid) {
		this.scid = scid;
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
	 * @return the bookPageCnt
	 */
	public String getBookPageCnt() {
		return this.bookPageCnt;
	}

	/**
	 * @param bookPageCnt
	 *            the bookPageCnt to set
	 */
	public void setBookPageCnt(String bookPageCnt) {
		this.bookPageCnt = bookPageCnt;
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

	public String getBookStatus() {
		return this.bookStatus;
	}

	public void setBookStatus(String bookStatus) {
		this.bookStatus = bookStatus;
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
	 * @return the phoneSprtYn
	 */
	public String getPhoneSprtYn() {
		return this.phoneSprtYn;
	}

	/**
	 * @param phoneSprtYn
	 *            the phoneSprtYn to set
	 */
	public void setPhoneSprtYn(String phoneSprtYn) {
		this.phoneSprtYn = phoneSprtYn;
	}

	/**
	 * @return the couponType
	 */
	public String getCouponType() {
		return this.couponType;
	}

	/**
	 * @param couponType
	 *            the couponType to set
	 */
	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}

	/**
	 * @return the couponId
	 */
	public String getCouponId() {
		return this.couponId;
	}

	/**
	 * @param couponId
	 *            the couponId to set
	 */
	public void setCouponId(String couponId) {
		this.couponId = couponId;
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
	 * @return the inAppId
	 */
	public String getInAppId() {
		return this.inAppId;
	}

	/**
	 * @param inAppId
	 *            the inAppId to set
	 */
	public void setInAppId(String inAppId) {
		this.inAppId = inAppId;
	}

	/**
	 * @return the prodCase
	 */
	public String getProdCase() {
		return this.prodCase;
	}

	/**
	 * @param prodCase
	 *            the prodCase to set
	 */
	public void setProdCase(String prodCase) {
		this.prodCase = prodCase;
	}

	/**
	 * @return the prodKind
	 */
	public String getProdKind() {
		return this.prodKind;
	}

	/**
	 * @param prodKind
	 *            the prodKind to set
	 */
	public void setProdKind(String prodKind) {
		this.prodKind = prodKind;
	}

	/**
	 * @return the parentProdNm
	 */
	public String getParentProdNm() {
		return this.parentProdNm;
	}

	/**
	 * @param parentProdNm
	 *            the parentProdNm to set
	 */
	public void setParentProdNm(String parentProdNm) {
		this.parentProdNm = parentProdNm;
	}

	/**
	 * @return the songId
	 */
	public String getSongId() {
		return this.songId;
	}

	/**
	 * @param songId
	 *            the songId to set
	 */
	public void setSongId(String songId) {
		this.songId = songId;
	}

	/**
	 * @return the ringType
	 */
	public String getRingType() {
		return this.ringType;
	}

	/**
	 * @param ringType
	 *            the ringType to set
	 */
	public void setRingType(String ringType) {
		this.ringType = ringType;
	}

	/**
	 * @return the quality
	 */
	public String getQuality() {
		return this.quality;
	}

	/**
	 * @param quality
	 *            the quality to set
	 */
	public void setQuality(String quality) {
		this.quality = quality;
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
	 * @return the chnlProdStatusCd
	 */
	public String getChnlProdStatusCd() {
		return this.chnlProdStatusCd;
	}

	/**
	 * @param chnlProdStatusCd
	 *            the chnlProdStatusCd to set
	 */
	public void setChnlProdStatusCd(String chnlProdStatusCd) {
		this.chnlProdStatusCd = chnlProdStatusCd;
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
	 * @return the opt2Nm
	 */
	public String getOpt2Nm() {
		return this.opt2Nm;
	}

	/**
	 * @param opt2Nm
	 *            the opt2Nm to set
	 */
	public void setOpt2Nm(String opt2Nm) {
		this.opt2Nm = opt2Nm;
	}

	/**
	 * @return the couponSendType
	 */
	public String getCouponSendType() {
		return this.couponSendType;
	}

	/**
	 * @param couponSendType
	 *            the couponSendType to set
	 */
	public void setCouponSendType(String couponSendType) {
		this.couponSendType = couponSendType;
	}

	/**
	 * @return the kind
	 */
	public String getKind() {
		return this.kind;
	}

	/**
	 * @param kind
	 *            the kind to set
	 */
	public void setKind(String kind) {
		this.kind = kind;
	}

	/**
	 * @return the planType
	 */
	public String getPlanType() {
		return this.planType;
	}

	/**
	 * @param planType
	 *            the planType to set
	 */
	public void setPlanType(String planType) {
		this.planType = planType;
	}

	/**
	 * @return the cmpxProdBookClsfCd
	 */
	public String getCmpxProdBookClsfCd() {
		return this.cmpxProdBookClsfCd;
	}

	/**
	 * @param cmpxProdBookClsfCd
	 *            the cmpxProdBookClsfCd to set
	 */
	public void setCmpxProdBookClsfCd(String cmpxProdBookClsfCd) {
		this.cmpxProdBookClsfCd = cmpxProdBookClsfCd;
	}

	/**
	 * @return the mapgProdIdList
	 */
	public List<MapgProdMeta> getMapgProdIdList() {
		return this.mapgProdIdList;
	}

	/**
	 * @param mapgProdIdList
	 *            the mapgProdIdList to set
	 */
	public void setMapgProdIdList(List<MapgProdMeta> mapgProdIdList) {
		this.mapgProdIdList = mapgProdIdList;
	}

	/**
	 * @return the sellerMbrInfoSac
	 */
	public SellerMbrInfoSac getSellerMbrInfoSac() {
		return this.sellerMbrInfoSac;
	}

	/**
	 * @param sellerMbrInfoSac
	 *            the sellerMbrInfoSac to set
	 */
	public void setSellerMbrInfoSac(SellerMbrInfoSac sellerMbrInfoSac) {
		this.sellerMbrInfoSac = sellerMbrInfoSac;
	}

	public String getSeriesYn() {
		return this.seriesYn;
	}

	public void setSeriesYn(String seriesYn) {
		this.seriesYn = seriesYn;
	}

	public String getSeedCaseRefCd() {
		return this.seedCaseRefCd;
	}

	public void setSeedCaseRefCd(String seedCaseRefCd) {
		this.seedCaseRefCd = seedCaseRefCd;
	}

	public String getBookContentsCnt() {
		return this.bookContentsCnt;
	}

	public void setBookContentsCnt(String bookContentsCnt) {
		this.bookContentsCnt = bookContentsCnt;
	}

	public String getSeriesContentsCnt() {
		return this.seriesContentsCnt;
	}

	public void setSeriesContentsCnt(String seriesContentsCnt) {
		this.seriesContentsCnt = seriesContentsCnt;
	}

	public String getMgzinContentsCnt() {
		return this.mgzinContentsCnt;
	}

	public void setMgzinContentsCnt(String mgzinContentsCnt) {
		this.mgzinContentsCnt = mgzinContentsCnt;
	}

	public String getVerticalYn() {
		return verticalYn;
	}

	public void setVerticalYn(String verticalYn) {
		this.verticalYn = verticalYn;
	}

	public String getContentsTypeCd() {
		return this.contentsTypeCd;
	}

	public void setContentsTypeCd(String contentsTypeCd) {
		this.contentsTypeCd = contentsTypeCd;
	}

	public String getPlus19Yn() {
		return this.plus19Yn;
	}

	public void setPlus19Yn(String plus19Yn) {
		this.plus19Yn = plus19Yn;
	}

    public String getChnlCompNm() {
        return chnlCompNm;
    }

    public void setChnlCompNm(String chnlCompNm) {
        this.chnlCompNm = chnlCompNm;
    }

    public String getAgencyNm() {
        return agencyNm;
    }

    public void setAgencyNm(String agencyNm) {
        this.agencyNm = agencyNm;
    }

	public String getAvailablePlayer() {
		return availablePlayer;
	}

	public void setAvailablePlayer(String availablePlayer) {
		this.availablePlayer = availablePlayer;
	}
}
