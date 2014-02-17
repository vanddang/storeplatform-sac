/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.vod.vo;

import java.util.Date;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * VOD 상세조회 Value Object
 *
 * Updated on : 2014. 1. 28. Updated by : 임근대, SK플래닛.
 */
public class VodDetail extends CommonInfo {

	private static final long serialVersionUID = 1L;

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
	/** 에피소드 상품ID */
	private String espdProdId;
	/** 상품 상태 코드 */
	private String prodStatusCd;


	/** 등록일시 */
	private Date regDt;

	// private String vodTitlNm;
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

	// ---------------------------------------------------
	// 다운로드 상품
	// ---------------------------------------------------
	/** 다운로드 상품ID */
	private String storeProdId;
	/** 다운로드 상품 가격 */
	private Integer storeProdAmt;
	/** 다운로드 DRM */
	private String storeDrmYn;

	// ---------------------------------------------------
	// 바로보기 상품
	// ---------------------------------------------------
	/** 바로보기 상품ID */
	private String playProdId;
	/** 바로보기 상품 가격 */
	private Integer playProdAmt;
	/** 바로보기 DRM */
	private String playDrmYn;

	/** 챕터 */
	private String chapter;
	private String chapterUnit;

	private String btvYn;
	/** HDCP 여부 */
	private String hdcpYn;
	/** HDV여부 */
	private String hdvYn;
	/** 돌비 지원여부 */
	private String dolbySprtYn;

	/** */
	private String usagePeriod;

	/** 다운로드 지역제한 */
	private String dwldAreaLimtYn;

	/** 에피소드 재생 시간 */
	private Integer epsdPlayTm;
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
	/** 방송사 코드 */
	private String brdcCompCd;
	/** 방송사명 */
	private String brdcCompCdNm;
	/** 채널 회사명 */
	private String chnlCompNm;

	/** 기획사 */
	 private String agencyNm;

	/** */
	// private Integer prodAmt;

	/** DRM 여부 */
	// private String drmYn;

	/** 재생시간 */
	// private String playTm;

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
	// Preview
	// ----------------------------------------
	/** 샵클_샘플_URL */
	private String scSamplUrl;
	/** 샘플_URL */
	private String samplUrl;

	// ----------------------------------------
	// Episode Count
	// ----------------------------------------
	/** 에피소드 수 */
	private Integer epsdCnt;
	/** 바로보기 에피소드 수 */
	private Integer strmEpsdCnt;

	// ----------------------------------------
	// 사용기간 코드, 명
	// ----------------------------------------
	/** 사용기간 단위 코드 (PD00310 == 무제한), 무제한인 경우 Store, 그외 Play */
	private String usePeriodUnitCd;
	/** 사용기간 (사용기간 단위가 무제한이 아닌 경우 사용) */
	// FIXME:
	private Integer usePeriod;

	// ----------------------------------------
	// 망코드
	// ----------------------------------------
	/** 다운로드 망 코드 */
	private String dwldNetworkCd;
	/** 바로보기 망 코드 */
	private String strmNetworkCd;


	// ----------------------------------------
	// 시리즈 조회
	// ----------------------------------------
	private Integer totalCount;

	// ----------------------------------------
	// 일반/SD/HD 화질 정보
	// ----------------------------------------
	/** 일반화질 sub 컨텐츠 Id. */
	private String nmSubContsId;
	/** 일반화질 상품버전. */
	private String nmProdVer;
	/** 일반화질 파일용량.  */
	private Integer nmFileSize;
	/** 일반화질 해상도. */
	private String nmDpPixel;
	/** 일반화질 화면비율. */
	private String nmDpPicRatio;

	/** SD화질 sub 컨텐츠 Id. */
	private String sdSubContsId;
	/** SD화질 상품버전. */
	private String sdProdVer;
	/** SD화질 파일용량.  */
	private Integer sdFileSize;
	/** SD화질 해상도. */
	private String sdDpPixel;
	/** SD화질 화면비율. */
	private String sdDpPicRatio;

	/** HD화질 sub 컨텐츠 Id. */
	private String hdSubContsId;
	/** HD화질 상품버전. */
	private String hdProdVer;
	/** HD화질 파일용량.  */
	private Integer hdFileSize;
	/** HD화질 해상도. */
	private String hdDpPixel;
	/** HD화질 화면비율. */
	private String hdDpPicRatio;


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

	//public String getVodTitlNm() { return this.vodTitlNm; }
	//public void setVodTitlNm(String vodTitlNm) { this.vodTitlNm = vodTitlNm;}

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
	 * HDV여부
	 *
	 * @return HDV여부
	 */
	public String getHdvYn() {
		return this.hdvYn;
	}

	/**
	 * HDV여부
	 *
	 * @param hdvYn
	 *            HDV여부
	 */
	public void setHdvYn(String hdvYn) {
		this.hdvYn = hdvYn;
	}

	/**
	 * 돌비 지원여부
	 *
	 * @return 돌비 지원여부
	 */
	public String getDolbySprtYn() {
		return this.dolbySprtYn;
	}

	/**
	 * 돌비 지원여부
	 *
	 * @param dolbySprtYn
	 *            돌비 지원여부
	 */
	public void setDolbySprtYn(String dolbySprtYn) {
		this.dolbySprtYn = dolbySprtYn;
	}

	/*
	 * public Integer getProdAmt() { return this.prodAmt; }
	 *
	 * public void setProdAmt(Integer prodAmt) { this.prodAmt = prodAmt; }
	 */

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

	public String getBrdcCompCd() {
		return this.brdcCompCd;
	}

	public void setBrdcCompCd(String brdcCompCd) {
		this.brdcCompCd = brdcCompCd;
	}

	public String getBrdcCompCdNm() {
		return this.brdcCompCdNm;
	}

	public void setBrdcCompCdNm(String brdcCompCdNm) {
		this.brdcCompCdNm = brdcCompCdNm;
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

	public String getDwldAreaLimtYn() {
		return this.dwldAreaLimtYn;
	}

	public void setDwldAreaLimtYn(String dwldAreaLimtYn) {
		this.dwldAreaLimtYn = dwldAreaLimtYn;
	}

	public String getScSamplUrl() {
		return this.scSamplUrl;
	}

	public void setScSamplUrl(String scSamplUrl) {
		this.scSamplUrl = scSamplUrl;
	}

	public String getSamplUrl() {
		return this.samplUrl;
	}

	public void setSamplUrl(String samplUrl) {
		this.samplUrl = samplUrl;
	}

	/**
	 * 에피소드 수
	 *
	 * @return 에피소드 수
	 */
	public Integer getEpsdCnt() {
		return this.epsdCnt;
	}

	/**
	 * 에피소드 수
	 *
	 * @param epsdCnt
	 *            바로보기 에피소드 수
	 */
	public void setEpsdCnt(Integer epsdCnt) {
		this.epsdCnt = epsdCnt;
	}

	/**
	 * 바로보기 에피소드 수
	 *
	 * @return 바로보기 에피소드 수
	 */
	public Integer getStrmEpsdCnt() {
		return this.strmEpsdCnt;
	}

	/**
	 * 바로보기 에피소드 수
	 *
	 * @param strmEpsdCnt
	 *            바로보기 에피소드 수
	 */
	public void setStrmEpsdCnt(Integer strmEpsdCnt) {
		this.strmEpsdCnt = strmEpsdCnt;
	}

	/**
	 * 사용기간 단위 코드 (PD00310 == 무제한), 무제한인 경우 Store, 그외 Play
	 *
	 * @return
	 */
	public String getUsePeriodUnitCd() {
		return this.usePeriodUnitCd;
	}

	/**
	 * 사용기간 단위 코드 (PD00310 == 무제한), 무제한인 경우 Store, 그외 Play
	 *
	 * @param usePeriodUnitCd
	 */
	public void setUsePeriodUnitCd(String usePeriodUnitCd) {
		this.usePeriodUnitCd = usePeriodUnitCd;
	}

	/**
	 * 사용기간 (사용기간 단위가 무제한이 아닌 경우 사용)
	 *
	 * @return
	 */
	public Integer getUsePeriod() {
		return this.usePeriod;
	}

	/**
	 * 사용기간 (사용기간 단위가 무제한이 아닌 경우 사용)
	 *
	 * @param usePeriod
	 */
	public void setUsePeriod(Integer usePeriod) {
		this.usePeriod = usePeriod;
	}

	/**
	 * 다운로드 망 코드
	 *
	 * @return 다운로드 망 코드
	 */
	public String getDwldNetworkCd() {
		return this.dwldNetworkCd;
	}

	/**
	 * 다운로드 망 코드
	 *
	 * @param dwldNetworkCd
	 *            다운로드 망 코드
	 */
	public void setDwldNetworkCd(String dwldNetworkCd) {
		this.dwldNetworkCd = dwldNetworkCd;
	}

	/**
	 * 바로보기 망 코드
	 *
	 * @return 바로보기 망 코드
	 */
	public String getStrmNetworkCd() {
		return this.strmNetworkCd;
	}

	/**
	 * 바로보기 망 코드
	 *
	 * @param strmNetworkCd
	 *            바로보기 망 코드
	 */
	public void setStrmNetworkCd(String strmNetworkCd) {
		this.strmNetworkCd = strmNetworkCd;
	}

	public String getCid() {
		return this.cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getEspdProdId() {
		return this.espdProdId;
	}

	public void setEspdProdId(String espdProdId) {
		this.espdProdId = espdProdId;
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

	public String getBtvYn() {
		return this.btvYn;
	}

	public void setBtvYn(String btvYn) {
		this.btvYn = btvYn;
	}

	public String getHdcpYn() {
		return this.hdcpYn;
	}

	public void setHdcpYn(String hdcpYn) {
		this.hdcpYn = hdcpYn;
	}

	public String getUsagePeriod() {
		return this.usagePeriod;
	}

	public void setUsagePeriod(String usagePeriod) {
		this.usagePeriod = usagePeriod;
	}

	public Integer getEpsdPlayTm() {
		return this.epsdPlayTm;
	}

	public void setEpsdPlayTm(Integer epsdPlayTm) {
		this.epsdPlayTm = epsdPlayTm;
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

	/**
	 * 일반화질 sub 컨텐츠 Id.
	 * @return
	 * 		일반화질 sub 컨텐츠 Id.
	 */
	public String getNmSubContsId() {
		return this.nmSubContsId;
	}

	/**
	 * 일반화질 sub 컨텐츠 Id.
	 * @param nmSubContsId
	 * 			일반화질 sub 컨텐츠 Id.
	 */
	public void setNmSubContsId(String nmSubContsId) {
		this.nmSubContsId = nmSubContsId;
	}

	/**
	 * 일반화질 상품버전.
	 * @return
	 * 		일반화질 상품버전.
	 */
	public String getNmProdVer() {
		return this.nmProdVer;
	}

	/**
	 * 일반화질 상품버전.
	 * @param nmProdVer
	 * 			일반화질 상품버전.
	 */
	public void setNmProdVer(String nmProdVer) {
		this.nmProdVer = nmProdVer;
	}

	/**
	 * 일반화질 파일용량.
	 * @return
	 * 		일반화질 파일용량.
	 */
	public Integer getNmFileSize() {
		return this.nmFileSize;
	}

	/**
	 * 일반화질 파일용량.
	 * @param nmFileSize
	 * 			일반화질 파일용량.
	 */
	public void setNmFileSize(Integer nmFileSize) {
		this.nmFileSize = nmFileSize;
	}

	/**
	 * 일반화질 해상도.
	 * @return
	 * 		일반화질 해상도.
	 */
	public String getNmDpPixel() {
		return this.nmDpPixel;
	}
	/**
	 * 일반화질 해상도.
	 * @param nmDpPixel
	 * 		일반화질 해상도.
	 */
	public void setNmDpPixel(String nmDpPixel) {
		this.nmDpPixel = nmDpPixel;
	}

	/**
	 * 일반화질 화면비율.
	 * @return
	 * 		일반화질 화면비율.
	 */
	public String getNmDpPicRatio() {
		return this.nmDpPicRatio;
	}

	/**
	 * 일반화질 화면비율.
	 * @param nmDpPicRatio
	 * 				일반화질 화면비율.
	 */
	public void setNmDpPicRatio(String nmDpPicRatio) {
		this.nmDpPicRatio = nmDpPicRatio;
	}

	/**
	 * SD화질 sub 컨텐츠 Id.
	 * @return
	 * 		SD화질 sub 컨텐츠 Id.
	 */
	public String getSdSubContsId() {
		return this.sdSubContsId;
	}

	/**
	 * SD화질 sub 컨텐츠 Id.
	 * @param sdSubContsId
	 * 			SD화질 sub 컨텐츠 Id.
	 */
	public void setSdSubContsId(String sdSubContsId) {
		this.sdSubContsId = sdSubContsId;
	}

	/**
	 * SD화질 상품버전.
	 * @return
	 * 		SD화질 상품버전.
	 */
	public String getSdProdVer() {
		return this.sdProdVer;
	}

	/**
	 * SD화질 상품버전.
	 * @param sdProdVer
	 * 		SD화질 상품버전.
	 */
	public void setSdProdVer(String sdProdVer) {
		this.sdProdVer = sdProdVer;
	}

	/**
	 * SD화질 파일용량.
	 * @return
	 * 		SD화질 파일용량.
	 */
	public Integer getSdFileSize() {
		return this.sdFileSize;
	}

	/**
	 * SD화질 파일용량.
	 * @param sdFileSize
	 * 			SD화질 파일용량.
	 */
	public void setSdFileSize(Integer sdFileSize) {
		this.sdFileSize = sdFileSize;
	}

	/**
	 * SD화질 해상도.
	 * @return
	 * 		SD화질 해상도.
	 */
	public String getSdDpPixel() {
		return this.sdDpPixel;
	}

	/**
	 * SD화질 해상도.
	 * @param sdDpPixel
	 * 			SD화질 해상도.
	 */
	public void setSdDpPixel(String sdDpPixel) {
		this.sdDpPixel = sdDpPixel;
	}

	/**
	 * SD화질 화면비율.
	 * @return
	 * 		SD화질 화면비율.
	 */
	public String getSdDpPicRatio() {
		return this.sdDpPicRatio;
	}

	/**
	 * SD화질 화면비율.
	 * @param sdDpPicRatio
	 * 			SD화질 화면비율.
	 */
	public void setSdDpPicRatio(String sdDpPicRatio) {
		this.sdDpPicRatio = sdDpPicRatio;
	}

	/**
	 * HD화질 sub 컨텐츠 Id.
	 * @return
	 * 		HD화질 sub 컨텐츠 Id.
	 */
	public String getHdSubContsId() {
		return this.hdSubContsId;
	}

	/**
	 * HD화질 sub 컨텐츠 Id.
	 * @param hdSubContsId
	 * 			HD화질 sub 컨텐츠 Id.
	 */
	public void setHdSubContsId(String hdSubContsId) {
		this.hdSubContsId = hdSubContsId;
	}

	/**
	 * HD화질 상품버전.
	 * @return
	 * 		HD화질 상품버전.
	 */
	public String getHdProdVer() {
		return this.hdProdVer;
	}

	/**
	 * HD화질 상품버전.
	 * @param hdProdVer
	 * 			HD화질 상품버전.
	 */
	public void setHdProdVer(String hdProdVer) {
		this.hdProdVer = hdProdVer;
	}

	/**
	 * HD화질 파일용량.
	 * @return
	 * 		HD화질 파일용량.
	 */
	public Integer getHdFileSize() {
		return this.hdFileSize;
	}

	/**
	 * HD화질 파일용량.
	 * @param hdFileSize
	 * 		HD화질 파일용량.
	 */
	public void setHdFileSize(Integer hdFileSize) {
		this.hdFileSize = hdFileSize;
	}

	/**
	 * HD화질 해상도.
	 * @return
	 * 		HD화질 해상도.
	 */
	public String getHdDpPixel() {
		return this.hdDpPixel;
	}

	/**
	 * HD화질 해상도.
	 * @param hdDpPixel
	 * 			HD화질 해상도.
	 */
	public void setHdDpPixel(String hdDpPixel) {
		this.hdDpPixel = hdDpPixel;
	}

	/**
	 * HD화질 화면비율.
	 * @return
	 * 		HD화질 화면비율.
	 */
	public String getHdDpPicRatio() {
		return this.hdDpPicRatio;
	}

	/**
	 * HD화질 화면비율.
	 * @param hdDpPicRatio
	 * 				HD화질 화면비율.
	 */
	public void setHdDpPicRatio(String hdDpPicRatio) {
		this.hdDpPicRatio = hdDpPicRatio;
	}

	public String getAgencyNm() {
		return this.agencyNm;
	}

	public void setAgencyNm(String agencyNm) {
		this.agencyNm = agencyNm;
	}

	//public String getPlayTm() { return this.playTm; }
	//public void setPlayTm(String playTm) { this.playTm = playTm; }


	//public String getDrmYn() { return this.drmYn; }
	//public void setDrmYn(String drmYn) { this.drmYn = drmYn; }
}
