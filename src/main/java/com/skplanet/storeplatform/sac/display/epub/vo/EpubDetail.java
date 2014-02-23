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

import java.util.Date;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 이북/코믹 상세조회 Value Object
 *
 * Updated on : 2014. 2. 2. Updated by : 임근대, SK플래닛.
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

	/** 상품 정가 가격 */
	private Integer prodNetAmt;
	/** 상품 가격 */
	private Integer prodAmt;

	/** 도서 목차 */
	private String bookTbctns;

    private String bookClsfCd;

    private Integer bookPageCnt;

    /** 다운로드 지역제한 */
	private String dwldAreaLimtYn;

    private String subContentsId;
    private Integer fileSize;
    private String prodVer;
	// ---------------------------------------------------
	// 다운로드 상품
	// ---------------------------------------------------
	/** 다운로드 상품ID */
	private String storeProdId;
	/** 다운로드 상품 가격 */
	private Integer storeProdAmt;
	/** 다운로드 DRM */
	private String storeDrmYn;
    private String storeStatusCd;

	// ---------------------------------------------------
	// 바로보기 상품
	// ---------------------------------------------------
	/** 바로보기 상품ID */
	private String playProdId;
	/** 바로보기 상품 가격 */
	private Integer playProdAmt;
	/** 바로보기 DRM */
	private String playDrmYn;
    private String playStatusCd;

	/** 챕터 */
	private String chapter;
	private String chapterUnit;

    private String usePeriod;
    private String usePeriodUnitCd;
	private String usagePeriod;

	/** 다운로드 지역제한 */
	// private String dwldAreaLimtYn;

	/** 상품 등급 코드 */
	private String prodGrdCd;
	/** 장르코드 */
	private String genreCd;
	/** 발매일 */
	private Date issueDay;

	// ---------------------------------------------------
	// Thumbnail
	// ---------------------------------------------------
	/** thumbnail filePath */
	private String imgPath;
	/** thumbnail fileName */
	private String imgNm;
	/** thumbnail fileSize */
	private Integer imgSize;

	// ---------------------------------------------------
	// Accrual
	// ---------------------------------------------------
	/** 참여자수 */
	private Integer paticpersCnt;
	/** 구매수 */
	private Integer prchsCnt;
	/** 평균 평가 점수 */
	private Double avgEvluScore;

	/** 서비스시작 일시 */
	private Date svcStartDt;
	/** 채널 회사명 */
	private String chnlCompNm;

	// ----------------------------------------
	// 판매자 정보
	// ----------------------------------------
	/** 판매자 회원 번호 */
	private String sellerMbrNo;
	/** 노출 판매자 명 */
	private String expoSellerNm;
	/** 노출 판매자 전화번호 */
	private String expoSellerTelno;
	/** 노출 판매자 이메일 */
	private String expoSellerEmail;

	// ----------------------------------------
	// 시리즈 조회
	// ----------------------------------------
	private Integer totalCount;

	// ----------------------------------------
	// Book
	// ----------------------------------------
	private String bookStatus;
	private String bookType;
	private Integer bookCount;
	private String supportStore;
	private String supportPlay;
	private String mgzinSubscripCd;
	private String svcGrpCd;
	private String chnlClsfCd;

	private String bookCnt;
	private String bookFreeCnt;
	private String serialFreeCnt;

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

	public Date getIssueDay() {
		return this.issueDay;
	}

	public void setIssueDay(Date issueDay) {
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
	public Integer getImgSize() {
		return this.imgSize;
	}

	/**
	 * thumbnail fileSize
	 *
	 * @param imgSize
	 *            thumbnail fileSize
	 */
	public void setImgSize(Integer imgSize) {
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

	public Date getSvcStartDt() {
		return this.svcStartDt;
	}

	public void setSvcStartDt(Date svcStartDt) {
		this.svcStartDt = svcStartDt;
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

	public String getBookCnt() {
		return this.bookCnt;
	}

	public void setBookCnt(String bookCnt) {
		this.bookCnt = bookCnt;
	}

	public String getBookFreeCnt() {
		return this.bookFreeCnt;
	}

	public void setBookFreeCnt(String bookFreeCnt) {
		this.bookFreeCnt = bookFreeCnt;
	}

	public String getSerialFreeCnt() {
		return this.serialFreeCnt;
	}

	public void setSerialFreeCnt(String serialFreeCnt) {
		this.serialFreeCnt = serialFreeCnt;
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

    public Integer getBookPageCnt() {
        return this.bookPageCnt;
    }

    public void setBookPageCnt(Integer bookPageCnt) {
        this.bookPageCnt = bookPageCnt;
    }

    public String getSubContentsId() {
        return this.subContentsId;
    }

    public void setSubContentsId(String subContentsId) {
        this.subContentsId = subContentsId;
    }

    public Integer getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    public String getProdVer() {
        return this.prodVer;
    }

    public void setProdVer(String prodVer) {
        this.prodVer = prodVer;
    }

    public String getStoreStatusCd() {
        return this.storeStatusCd;
    }

    public void setStoreStatusCd(String storeStatusCd) {
        this.storeStatusCd = storeStatusCd;
    }

    public String getPlayStatusCd() {
        return this.playStatusCd;
    }

    public void setPlayStatusCd(String playStatusCd) {
        this.playStatusCd = playStatusCd;
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
}
