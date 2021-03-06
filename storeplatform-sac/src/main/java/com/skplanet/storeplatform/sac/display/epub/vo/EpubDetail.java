/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.epub.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

import java.util.Date;

/**
 * 이북/코믹 상세조회 Value Object
 *
 * Created on 2014. 02. 02. by 임근대, SK플래닛.
 * Updated on 2014. 07. 24. by 서대영, SK플래닛 : 미리보기 관련 필드 1개 추가
 */
public class EpubDetail extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String tenantId;
	private String langCd;

	/** TopMenu ID */
	private String topMenuId;
	/** TopMenu 명 */
	private String topMenuNm;
	/** Menu ID */
	private String menuId;
	/** Menu 명 */
	private String menuNm;
	/** 메뉴 설명 */
	private String menuDesc;
	/** 메타 구분코드 */
	private String metaClsfCd;
	/** CID */
	private String cid;
	/** 상품ID */
	private String prodId;
	// private String espdProdId;//에피소드 상품ID
    /** 채널상품 ID */
    private String chnlProdId;
	/** 상품 상태 코드 */
	private String prodStatusCd;

    private String regId;
	/** 등록일시 */
	private Date regDt;

	/** 상품명 */
	private String prodNm;
	/** 상품 기본 설명 */
	private String prodBaseDesc;
	/** 상품 상세 설명 */
	private String prodDtlDesc;
	/** 상품 소개 내용 */
	private String prodIntrDscr;
	/** 아티스트1 */
	private String artist1Nm;
	/** 아티스트2 */
	private String artist2Nm;
	/** 아티스트3 */
	private String artist3Nm;

	/** 상품 가격 */
	private Integer prodAmt;
	/** 상품 정가 가격 */
	private Integer prodNetAmt;

	/** 도서 목차 */
	private String bookTbctns;

    private String bookClsfCd;

    private String bookPageCnt;

    /** 다운로드 지역제한 */
	private String dwldAreaLimtYn;

    private String subContentsId;
    private Long fileSize;
    private String prodVer;

    private String usePeriodNm;


	// 소장 상품
	/** 소장 상품ID */
	private String storeProdId;
	/** 소장 상품 가격 */
	private Integer storeProdAmt;
	private Integer storeProdNetAmt;
	/** 소장 DRM */
	private String storeDrmYn;
    private String storeProdStatusCd;
    private String storeUsePeriodUnitCd;


	// 대여 상품
	/** 대여 상품ID */
	private String playProdId;
	/** 대여 상품 가격 */
	private Integer playProdAmt;
	private Integer playProdNetAmt;
	/** 대여 DRM */
	private String playDrmYn;
    private String playProdStatusCd;
    private Integer playUsePeriod;
    private String playUsePeriodUnitCd;
    private String playUsePeriodUnitCdNm;

	/** 챕터 */
	private String chapter;
	private String chapterUnit;

    private String usePeriod;
    private String usePeriodUnitCd;
	private String usagePeriod;


	/** 상품 등급 코드 */
	private String prodGrdCd;
	/** 장르코드 */
	private String genreCd;
	/** 발매일 */
	private String issueDay;

	// Thumbnail
	/** thumbnail filePath */
	private String imgPath;
	/** thumbnail fileName */
	private String imgNm;
	/** thumbnail fileSize */
	private Long imgSize;

	// Accrual
	/** 참여자수 */
	private Integer paticpersCnt;
	/** 구매수 */
	private Integer prchsCnt;
	/** 평균 평가 점수 */
	private Double avgEvluScore;

	/** 채널 회사명 */
	private String chnlCompNm;

	// 판매자 정보
	/** 판매자 회원 번호 */
	private String sellerMbrNo;
	/** 노출 판매자 명 */
	private String expoSellerNm;
	/** 노출 판매자 전화번호 */
	private String expoSellerTelno;
	/** 노출 판매자 이메일 */
	private String expoSellerEmail;

	// 시리즈 조회
	private Integer totalCount;

	// Book
	private String bookStatus;
	private String bookType;
	private String supportStore;
	private String supportPlay;
	private String mgzinSubscripCd;
	private String svcGrpCd;
	private String chnlClsfCd;
	private String comptYn;

	private Integer bookCnt;
	private Integer serialCnt;
	private Integer magazineCnt;
	private Integer bookFreeCnt;
	private Integer serialFreeCnt;
	private Integer magazineFreeCnt;
	
	/** 19+ 상품여부 */
	private String plus19Yn;	
	
	// Prefix Title, Badge 정보 추가
	/** PREFIX 명 */
	private String prefixTitle;
	/** BADGE 코드 */
	private String badgeCd;
	/** BADGE 설명 */
	private String badgeOptText;

	// 미리보기
	private String samplUrl;

	// 좋아요 선택 여부
	private String likeYn;
	
	private String storeDlStrmCd; // 소장 다운로드 스트리밍 값
	private String playDlStrmCd;  // 대여 다운로드 스트리밍 값
	
	private String verticalYn;	// Y : 웹툰 원고 타입

	private String deviceSprtYn;	// 단말 지원 여부
	
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

	/**
	 * 상품명
	 *
	 * @return 상품명
	 */
	public String getProdNm() {
		return this.prodNm;
	}

	/**
	 * 상품명
	 *
	 * @param prodNm
	 *            상품명
	 */
	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}

	/**
	 * 상품 기본 설명
	 *
	 * @return 상품 기본 설명
	 */
	public String getProdBaseDesc() {
		return this.prodBaseDesc;
	}

	/**
	 * 상품 기본 설명
	 *
	 * @param prodBaseDesc
	 *            상품 기본 설명
	 */
	public void setProdBaseDesc(String prodBaseDesc) {
		this.prodBaseDesc = prodBaseDesc;
	}

	/**
	 * 상품 상세 설명
	 *
	 * @return 상품 상세 설명
	 */
	public String getProdDtlDesc() {
		return this.prodDtlDesc;
	}

	/**
	 * 상품 상세 설명
	 *
	 * @param prodDtlDesc
	 *            상품 상세 설명
	 */
	public void setProdDtlDesc(String prodDtlDesc) {
		this.prodDtlDesc = prodDtlDesc;
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

	/**
	 * @return the artist3Nm
	 */
	public String getArtist3Nm() {
		return this.artist3Nm;
	}

	/**
	 * @param artist3Nm the artist3Nm to set
	 */
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

	/**
	 * 참여자수
	 *
	 * @return 참여자수
	 */
	public Integer getPaticpersCnt() {
		return this.paticpersCnt;
	}

	/**
	 * 참여자수
	 *
	 * @param paticpersCnt
	 *            참여자수
	 */
	public void setPaticpersCnt(Integer paticpersCnt) {
		this.paticpersCnt = paticpersCnt;
	}

	/**
	 * 구매수
	 *
	 * @return 구매수
	 */
	public Integer getPrchsCnt() {
		return this.prchsCnt;
	}

	/**
	 * 구매수
	 *
	 * @param prchsCnt
	 *            구매수
	 */
	public void setPrchsCnt(Integer prchsCnt) {
		this.prchsCnt = prchsCnt;
	}

	/**
	 * 평균 평가 점수
	 *
	 * @return 평균 평가 점수
	 */
	public Double getAvgEvluScore() {
		return this.avgEvluScore;
	}

	/**
	 * 평균 평가 점수
	 *
	 * @param avgEvluScore
	 *            평균 평가 점수
	 */
	public void setAvgEvluScore(Double avgEvluScore) {
		this.avgEvluScore = avgEvluScore;
	}

	public String getTopMenuNm() {
		return this.topMenuNm;
	}

	public void setTopMenuNm(String topMenuNm) {
		this.topMenuNm = topMenuNm;
	}

	/**
	 * thumbnail filePath
	 *
	 * @return
	 */
	public String getImgPath() {
		return this.imgPath;
	}

	/**
	 * thumbnail filePath
	 *
	 * @param imgPath
	 */
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	/**
	 * thumbnail fileNm
	 *
	 * @return thumbnail fileNm
	 */
	public String getImgNm() {
		return this.imgNm;
	}

	/**
	 * thumbnail fileNm
	 *
	 * @param imgNm
	 */
	public void setImgNm(String imgNm) {
		this.imgNm = imgNm;
	}

	/**
	 * thumbnail fileSize
	 *
	 * @return thumbnail fileSize
	 */
	public Long getImgSize() {
		return this.imgSize;
	}

	/**
	 * thumbnail fileSize
	 *
	 * @param imgSize
	 *            thumbnail fileSize
	 */
	public void setImgSize(Long imgSize) {
		this.imgSize = imgSize;
	}

	/**
	 * 등록 일시
	 *
	 * @return 등록 일시
	 */
	public Date getRegDt() {
		return this.regDt;
	}

	/**
	 * 등록 일시
	 *
	 * @param regDt
	 *            등록 일시
	 */
	public void setRegDt(Date regDt) {
		this.regDt = regDt;
	}

	/**
	 * 상품 소개 내용
	 *
	 * @return 상품 소개 내용
	 */
	public String getProdIntrDscr() {
		return this.prodIntrDscr;
	}

	/**
	 * 상품 소개 내용
	 *
	 * @param prodIntrDscr
	 *            상품 소개 내용
	 */
	public void setProdIntrDscr(String prodIntrDscr) {
		this.prodIntrDscr = prodIntrDscr;
	}

	public String getGenreCd() {
		return this.genreCd;
	}

	public void setGenreCd(String genreCd) {
		this.genreCd = genreCd;
	}

	public String getSellerMbrNo() {
		return this.sellerMbrNo;
	}

	public void setSellerMbrNo(String sellerMbrNo) {
		this.sellerMbrNo = sellerMbrNo;
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

	public String getExpoSellerEmail() {
		return this.expoSellerEmail;
	}

	public void setExpoSellerEmail(String expoSellerEmail) {
		this.expoSellerEmail = expoSellerEmail;
	}

	public String getCid() {
		return this.cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getStoreProdId() {
		return this.storeProdId;
	}

	public void setStoreProdId(String storeProdId) {
		this.storeProdId = storeProdId;
	}

	public Integer getStoreProdAmt() {
		return this.storeProdAmt;
	}

	public void setStoreProdAmt(Integer storeProdAmt) {
		this.storeProdAmt = storeProdAmt;
	}

	public String getStoreDrmYn() {
		return this.storeDrmYn;
	}

	public void setStoreDrmYn(String storeDrmYn) {
		this.storeDrmYn = storeDrmYn;
	}

	public String getPlayProdId() {
		return this.playProdId;
	}

	public void setPlayProdId(String playProdId) {
		this.playProdId = playProdId;
	}

	public Integer getPlayProdAmt() {
		return this.playProdAmt;
	}

	public void setPlayProdAmt(Integer playProdAmt) {
		this.playProdAmt = playProdAmt;
	}

	/**
	 * @return the storeProdNetAmt
	 */
	public Integer getStoreProdNetAmt() {
		return this.storeProdNetAmt;
	}

	/**
	 * @param storeProdNetAmt the storeProdNetAmt to set
	 */
	public void setStoreProdNetAmt(Integer storeProdNetAmt) {
		this.storeProdNetAmt = storeProdNetAmt;
	}

	/**
	 * @return the playProdNetAmt
	 */
	public Integer getPlayProdNetAmt() {
		return this.playProdNetAmt;
	}

	/**
	 * @param playProdNetAmt the playProdNetAmt to set
	 */
	public void setPlayProdNetAmt(Integer playProdNetAmt) {
		this.playProdNetAmt = playProdNetAmt;
	}

	public String getPlayDrmYn() {
		return this.playDrmYn;
	}

	public void setPlayDrmYn(String playDrmYn) {
		this.playDrmYn = playDrmYn;
	}

	public String getChapter() {
		return this.chapter;
	}

	public void setChapter(String chapter) {
		this.chapter = chapter;
	}

	public String getChapterUnit() {
		return this.chapterUnit;
	}

	public void setChapterUnit(String chapterUnit) {
		this.chapterUnit = chapterUnit;
	}

	public String getUsagePeriod() {
		return this.usagePeriod;
	}

	public void setUsagePeriod(String usagePeriod) {
		this.usagePeriod = usagePeriod;
	}

	public String getProdStatusCd() {
		return this.prodStatusCd;
	}

	public void setProdStatusCd(String prodStatusCd) {
		this.prodStatusCd = prodStatusCd;
	}

	public Integer getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getProdNetAmt() {
		return this.prodNetAmt;
	}

	public void setProdNetAmt(Integer prodNetAmt) {
		this.prodNetAmt = prodNetAmt;
	}

	public Integer getProdAmt() {
		return this.prodAmt;
	}

	public void setProdAmt(Integer prodAmt) {
		this.prodAmt = prodAmt;
	}

	public String getBookStatus() {
		return this.bookStatus;
	}

	public void setBookStatus(String bookStatus) {
		this.bookStatus = bookStatus;
	}

	public String getBookType() {
		return this.bookType;
	}

	public void setBookType(String bookType) {
		this.bookType = bookType;
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

	public String getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getLangCd() {
		return this.langCd;
	}

	public void setLangCd(String langCd) {
		this.langCd = langCd;
	}

	public String getMgzinSubscripCd() {
		return this.mgzinSubscripCd;
	}

	public void setMgzinSubscripCd(String mgzinSubscripCd) {
		this.mgzinSubscripCd = mgzinSubscripCd;
	}

	public String getSvcGrpCd() {
		return this.svcGrpCd;
	}

	public void setSvcGrpCd(String svcGrpCd) {
		this.svcGrpCd = svcGrpCd;
	}

	public String getChnlClsfCd() {
		return this.chnlClsfCd;
	}

	public void setChnlClsfCd(String chnlClsfCd) {
		this.chnlClsfCd = chnlClsfCd;
	}

	/**
	 * @return the bookCnt
	 */
	public Integer getBookCnt() {
		return this.bookCnt;
	}

	/**
	 * @param bookCnt the bookCnt to set
	 */
	public void setBookCnt(Integer bookCnt) {
		this.bookCnt = bookCnt;
	}

	/**
	 * @return the serialCnt
	 */
	public Integer getSerialCnt() {
		return this.serialCnt;
	}

	/**
	 * @param serialCnt the serialCnt to set
	 */
	public void setSerialCnt(Integer serialCnt) {
		this.serialCnt = serialCnt;
	}

	/**
	 * @return the magazineCnt
	 */
	public Integer getMagazineCnt() {
		return this.magazineCnt;
	}

	/**
	 * @param magazineCnt the magazineCnt to set
	 */
	public void setMagazineCnt(Integer magazineCnt) {
		this.magazineCnt = magazineCnt;
	}

	/**
	 * @return the bookFreeCnt
	 */
	public Integer getBookFreeCnt() {
		return this.bookFreeCnt;
	}

	/**
	 * @param bookFreeCnt the bookFreeCnt to set
	 */
	public void setBookFreeCnt(Integer bookFreeCnt) {
		this.bookFreeCnt = bookFreeCnt;
	}

	/**
	 * @return the serialFreeCnt
	 */
	public Integer getSerialFreeCnt() {
		return this.serialFreeCnt;
	}

	/**
	 * @param serialFreeCnt the serialFreeCnt to set
	 */
	public void setSerialFreeCnt(Integer serialFreeCnt) {
		this.serialFreeCnt = serialFreeCnt;
	}

	/**
	 * @return the magazineFreeCnt
	 */
	public Integer getMagazineFreeCnt() {
		return this.magazineFreeCnt;
	}

	/**
	 * @param magazineFreeCnt the magazineFreeCnt to set
	 */
	public void setMagazineFreeCnt(Integer magazineFreeCnt) {
		this.magazineFreeCnt = magazineFreeCnt;
	}

	public String getBookTbctns() {
		return this.bookTbctns;
	}

	public void setBookTbctns(String bookTbctns) {
		this.bookTbctns = bookTbctns;
	}

	public String getDwldAreaLimtYn() {
		return this.dwldAreaLimtYn;
	}

	public void setDwldAreaLimtYn(String dwldAreaLimtYn) {
		this.dwldAreaLimtYn = dwldAreaLimtYn;
	}


    public String getChnlProdId() {
        return this.chnlProdId;
    }

    public void setChnlProdId(String chnlProdId) {
        this.chnlProdId = chnlProdId;
    }

    public String getRegId() {
        return this.regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getBookClsfCd() {
        return this.bookClsfCd;
    }

    public void setBookClsfCd(String bookClsfCd) {
        this.bookClsfCd = bookClsfCd;
    }

    public String getBookPageCnt() {
        return this.bookPageCnt;
    }

    public void setBookPageCnt(String bookPageCnt) {
        this.bookPageCnt = bookPageCnt;
    }

    public String getSubContentsId() {
        return this.subContentsId;
    }

    public void setSubContentsId(String subContentsId) {
        this.subContentsId = subContentsId;
    }

    public Long getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getProdVer() {
        return this.prodVer;
    }

    public void setProdVer(String prodVer) {
        this.prodVer = prodVer;
    }

    public String getUsePeriod() {
        return this.usePeriod;
    }

    public void setUsePeriod(String usePeriod) {
        this.usePeriod = usePeriod;
    }

    public String getUsePeriodUnitCd() {
        return this.usePeriodUnitCd;
    }

    public void setUsePeriodUnitCd(String usePeriodUnitCd) {
        this.usePeriodUnitCd = usePeriodUnitCd;
    }

	/**
	 * @return the comptYn
	 */
	public String getComptYn() {
		return this.comptYn;
	}

	/**
	 * @param comptYn the comptYn to set
	 */
	public void setComptYn(String comptYn) {
		this.comptYn = comptYn;
	}

	/**
	 * @return the usePeriodNm
	 */
	public String getUsePeriodNm() {
		return this.usePeriodNm;
	}

	/**
	 * @param usePeriodNm the usePeriodNm to set
	 */
	public void setUsePeriodNm(String usePeriodNm) {
		this.usePeriodNm = usePeriodNm;
	}

	/**
	 * @return the storeUsePeriodUnitCd
	 */
	public String getStoreUsePeriodUnitCd() {
		return this.storeUsePeriodUnitCd;
	}

	/**
	 * @param storeUsePeriodUnitCd the storeUsePeriodUnitCd to set
	 */
	public void setStoreUsePeriodUnitCd(String storeUsePeriodUnitCd) {
		this.storeUsePeriodUnitCd = storeUsePeriodUnitCd;
	}

	/**
	 * @return the playUsePeriodUnitCd
	 */
	public String getPlayUsePeriodUnitCd() {
		return this.playUsePeriodUnitCd;
	}

	/**
	 * @param playUsePeriodUnitCd the playUsePeriodUnitCd to set
	 */
	public void setPlayUsePeriodUnitCd(String playUsePeriodUnitCd) {
		this.playUsePeriodUnitCd = playUsePeriodUnitCd;
	}

	/**
	 * @return the playUsePeriod
	 */
	public Integer getPlayUsePeriod() {
		return this.playUsePeriod;
	}

	/**
	 * @param playUsePeriod the playUsePeriod to set
	 */
	public void setPlayUsePeriod(Integer playUsePeriod) {
		this.playUsePeriod = playUsePeriod;
	}

	/**
	 * @return the playUsePeriodUnitCdNm
	 */
	public String getPlayUsePeriodUnitCdNm() {
		return this.playUsePeriodUnitCdNm;
	}

	/**
	 * @param playUsePeriodUnitCdNm the playUsePeriodUnitCdNm to set
	 */
	public void setPlayUsePeriodUnitCdNm(String playUsePeriodUnitCdNm) {
		this.playUsePeriodUnitCdNm = playUsePeriodUnitCdNm;
	}

	/**
	 * @return the storeProdStatusCd
	 */
	public String getStoreProdStatusCd() {
		return this.storeProdStatusCd;
	}

	/**
	 * @param storeProdStatusCd the storeProdStatusCd to set
	 */
	public void setStoreProdStatusCd(String storeProdStatusCd) {
		this.storeProdStatusCd = storeProdStatusCd;
	}

	/**
	 * @return the playProdStatusCd
	 */
	public String getPlayProdStatusCd() {
		return this.playProdStatusCd;
	}

	/**
	 * @param playProdStatusCd the playProdStatusCd to set
	 */
	public void setPlayProdStatusCd(String playProdStatusCd) {
		this.playProdStatusCd = playProdStatusCd;
	}

	public String getSamplUrl() {
		return this.samplUrl;
	}

	public void setSamplUrl(String samplUrl) {
		this.samplUrl = samplUrl;
	}

	public String getLikeYn() {
		return likeYn;
	}

	public void setLikeYn(String likeYn) {
		this.likeYn = likeYn;
	}
	
	public String getPlus19Yn() {
		return plus19Yn;
	}

	public void setPlus19Yn(String plus19Yn) {
		this.plus19Yn = plus19Yn;
	}
	
	public String getPrefixTitle() {
		return prefixTitle;
	}

	public void setPrefixTitle(String prefixTitle) {
		this.prefixTitle = prefixTitle;
	}
	
	public String getBadgeCd() {
		return badgeCd;
	}

	public void setBadgeCd(String badgeCd) {
		this.badgeCd = badgeCd;
	}
	
	public String getBadgeOptText() {
		return badgeOptText;
	}

	public void setBadgeOptText(String badgeOptText) {
		this.badgeOptText = badgeOptText;
	}

	/**
	 * @return the storeDlStrmCd
	 */
	public String getStoreDlStrmCd() {
		return storeDlStrmCd;
	}

	/**
	 * @param storeDlStrmCd the storeDlStrmCd to set
	 */
	public void setStoreDlStrmCd(String storeDlStrmCd) {
		this.storeDlStrmCd = storeDlStrmCd;
	}

	/**
	 * @return the playDlStrmCd
	 */
	public String getPlayDlStrmCd() {
		return playDlStrmCd;
	}

	/**
	 * @param playDlStrmCd the playDlStrmCd to set
	 */
	public void setPlayDlStrmCd(String playDlStrmCd) {
		this.playDlStrmCd = playDlStrmCd;
	}

	public String getVerticalYn() {
		return verticalYn;
	}

	public void setVerticalYn(String verticalYn) {
		this.verticalYn = verticalYn;
	}

	public String getDeviceSprtYn() {
		return deviceSprtYn;
	}

	public void setDeviceSprtYn(String deviceSprtYn) {
		this.deviceSprtYn = deviceSprtYn;
	}
}
