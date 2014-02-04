/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.download.vo;

/**
 * 다운로드 VOD 상품정보 Default Value Object.
 * 
 * Updated on : 2014. 1.25. Updated by : 이석희, 아이에스플러스.
 */
public class DownloadVod {
	private String menuId; // 메뉴 Id
	private String menuNm; // 메뉴 명
	private String cid; // cid
	private String prodId; // 상품 Id(channel)
	private String espdProdId; // 상품 Id(episode)
	private String topMenuId; // Top Menu Id
	private String topMenuNm; // Top Menu 명
	private String metaClsfCd; // meta 구분 코드
	private String storeProdId; // 다운로드 상품 url(현재는 영문명이 없어서 Id만 내려줌)
	private String playProdId; // 바로보기 상품 url(현재는 영문명이 없어서 Id만 내려줌)
	private Integer storeProdAmt; // 다운로드 상품 금액
	private Integer playProdAmt; // 바로보기 상품 금액
	private String storeDrmYn; // 다운로드 drm 여부
	private String playDrmYn; // 바로보기 drm 여부
	private String btvYn; // btv 지원여부
	private String hdcpYn; // hdcp 지원여부
	private String hdvYn; // hdv 지원여부
	private String prodNm; // 상품명
	private String chapter; // 회차
	private String chapterUnit; // 회차 단위
	private String usagePeriod; // 사용가능기간
	private String dwldAreaLimtYn; // 다운로드 지역제한
	private String epsdPlayTm; // 에피소드 재생시간
	private String prodGrdCd; // 상품이용등급
	private String sellerMbrNo; // 판매자 회원번호
	private String expoSellerNm; // 노출 판매자 명
	private String expoSellerTelno; // 노출 판매자 전화번호
	private String expoSellerEmail; // 노출 판매자 Email
	private String dolbySprtYn; // dolby 지원여부
	private String dolbyYn; // dolby 지원여부
	private String nmSubContsId; // 일반화질 sub 컨텐츠 Id
	private String sdSubContsid; // SD화질 sub 컨텐츠 Id
	private String hdSubContsid; // HD화질 sub 컨텐츠 Id
	private String nmProdVer; // 일반화질 상품버전
	private String sdProdVer; // SD화질 상품버전
	private String hdProdVer; // HD화질 상품버전
	private Integer nmFileSize; // 일반화질 파일 용량
	private Integer sdFileSize; // SD화질 파일용량
	private Integer hdFileSize; // HD화질 파일용량
	private String nmDpPixel; // 일반화질 해상도
	private String sdDpPixel; // SD화질 해상도
	private String hdDpPixel; // HD화질 해상도
	private String nmDpPicRatio; // 일반화질 화면비율
	private String sdDpPicRatio; // SD화질 화면비율
	private String hdDpPicRatio; // HD화질 화면비율
	private String nmBtvCid; // 일반화질 BTV CID
	private String sdBtvCid; // SD화질 BTV CID
	private String hdBtvCid; // HD화질 BTV CID
	private String dwldNetworkCd; // 다운로드 망 코드
	private String strmNetworkCd; // 바로보기 망 코드
	private String imagePath; // 이미지 경로
	private String imageNm; // 이미지 명
	private Integer imageSize; // 이미지 사이즈
	private String filePath; // 파일 경로

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
	 * cid.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getCid() {
		return this.cid;
	}

	/**
	 * 
	 * <pre>
	 * cid.
	 * </pre>
	 * 
	 * @param cid
	 *            cid
	 */
	public void setCid(String cid) {
		this.cid = cid;
	}

	/**
	 * 
	 * <pre>
	 * 상품 Id(채널).
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
	 * 상품 Id(채널).
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
	 * 상품 Id(에피소드).
	 * </pre>
	 * 
	 * @return String
	 */
	public String getEspdProdId() {
		return this.espdProdId;
	}

	/**
	 * 
	 * <pre>
	 * 상품 Id(에피소드).
	 * </pre>
	 * 
	 * @param espdProdId
	 *            espdProdId
	 */
	public void setEspdProdId(String espdProdId) {
		this.espdProdId = espdProdId;
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
	 * Top Menu 명.
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
	 * Top Menu 명.
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
	 * Meta 구분 코드.
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
	 * Meta 구분 코드.
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
	 * 다운로드 상품 url(현재는 영문명이 없어서 Id만 내려줌).
	 * </pre>
	 * 
	 * @return String
	 */
	public String getStoreProdId() {
		return this.storeProdId;
	}

	/**
	 * 
	 * <pre>
	 * 다운로드 상품 url(현재는 영문명이 없어서 Id만 내려줌).
	 * </pre>
	 * 
	 * @param storeProdId
	 *            storeProdId
	 */
	public void setStoreProdId(String storeProdId) {
		this.storeProdId = storeProdId;
	}

	/**
	 * 
	 * <pre>
	 * 바로보기 상품 (현재는 영문명이 없어서 Id만 내려줌).
	 * </pre>
	 * 
	 * @return String
	 */
	public String getPlayProdId() {
		return this.playProdId;
	}

	/**
	 * 
	 * <pre>
	 * 바로보기 상품 (현재는 영문명이 없어서 Id만 내려줌).
	 * </pre>
	 * 
	 * @param playProdId
	 *            playProdId
	 */
	public void setPlayProdId(String playProdId) {
		this.playProdId = playProdId;
	}

	/**
	 * 
	 * <pre>
	 * 다운로드 상품 금액.
	 * </pre>
	 * 
	 * @return Integer
	 */
	public Integer getStoreProdAmt() {
		return this.storeProdAmt;
	}

	/**
	 * 
	 * <pre>
	 * 다운로드 상품 금액.
	 * </pre>
	 * 
	 * @param storeProdAmt
	 *            storeProdAmt
	 */
	public void setStoreProdAmt(Integer storeProdAmt) {
		this.storeProdAmt = storeProdAmt;
	}

	/**
	 * 
	 * <pre>
	 * 바로보기 상품 금액.
	 * </pre>
	 * 
	 * @return Integer
	 */
	public Integer getPlayProdAmt() {
		return this.playProdAmt;
	}

	/**
	 * 
	 * <pre>
	 * 바로보기 상품 금액.
	 * </pre>
	 * 
	 * @param playProdAmt
	 *            playProdAmt
	 */
	public void setPlayProdAmt(Integer playProdAmt) {
		this.playProdAmt = playProdAmt;
	}

	/**
	 * 
	 * <pre>
	 * 다운로드 Drm 여부.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getStoreDrmYn() {
		return this.storeDrmYn;
	}

	/**
	 * 
	 * <pre>
	 * 다운로드 Drm 여부.
	 * </pre>
	 * 
	 * @param storeDrmYn
	 *            storeDrmYn
	 */
	public void setStoreDrmYn(String storeDrmYn) {
		this.storeDrmYn = storeDrmYn;
	}

	/**
	 * 
	 * <pre>
	 * 바로보기 Drm 여부.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getPlayDrmYn() {
		return this.playDrmYn;
	}

	/**
	 * 
	 * <pre>
	 * 바로보기 Drm 여부.
	 * </pre>
	 * 
	 * @param playDrmYn
	 *            playDrmYn
	 */
	public void setPlayDrmYn(String playDrmYn) {
		this.playDrmYn = playDrmYn;
	}

	/**
	 * 
	 * <pre>
	 * Btv 지원여부.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getBtvYn() {
		return this.btvYn;
	}

	/**
	 * 
	 * <pre>
	 *  Btv 지원여부.
	 * </pre>
	 * 
	 * @param btvYn
	 *            btvYn
	 */
	public void setBtvYn(String btvYn) {
		this.btvYn = btvYn;
	}

	/**
	 * 
	 * <pre>
	 * Hdcp 지원여부.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getHdcpYn() {
		return this.hdcpYn;
	}

	/**
	 * 
	 * <pre>
	 *  Hdcp 지원여부.
	 * </pre>
	 * 
	 * @param hdcpYn
	 *            hdcpYn
	 */
	public void setHdcpYn(String hdcpYn) {
		this.hdcpYn = hdcpYn;
	}

	/**
	 * 
	 * <pre>
	 * Hdv 지원여부.
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
	 *  Hdv 지원여부.
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
	 *  상품명.
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
	 *  회차.
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
	 * 회차 단위.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getChapterUnit() {
		return this.chapterUnit;
	}

	/**
	 * 
	 * <pre>
	 *  회차 단위.
	 * </pre>
	 * 
	 * @param chapterUnit
	 *            chapterUnit
	 */
	public void setChapterUnit(String chapterUnit) {
		this.chapterUnit = chapterUnit;
	}

	/**
	 * 
	 * <pre>
	 * 사용기간.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getUsagePeriod() {
		return this.usagePeriod;
	}

	/**
	 * 
	 * <pre>
	 *  사용기간.
	 * </pre>
	 * 
	 * @param usagePeriod
	 *            usagePeriod
	 */
	public void setUsagePeriod(String usagePeriod) {
		this.usagePeriod = usagePeriod;
	}

	/**
	 * 
	 * <pre>
	 * 다운로드 지역제한.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getDwldAreaLimtYn() {
		return this.dwldAreaLimtYn;
	}

	/**
	 * 
	 * <pre>
	 *  다운로드 지역제한.
	 * </pre>
	 * 
	 * @param dwldAreaLimtYn
	 *            dwldAreaLimtYn
	 */
	public void setDwldAreaLimtYn(String dwldAreaLimtYn) {
		this.dwldAreaLimtYn = dwldAreaLimtYn;
	}

	/**
	 * 
	 * <pre>
	 * 에피소드 재생시간.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getEpsdPlayTm() {
		return this.epsdPlayTm;
	}

	/**
	 * 
	 * <pre>
	 *  에피소드 재생시간.
	 * </pre>
	 * 
	 * @param dwldAreaLimtYn
	 *            dwldAreaLimtYn
	 */
	public void setEpsdPlayTm(String epsdPlayTm) {
		this.epsdPlayTm = epsdPlayTm;
	}

	/**
	 * 
	 * <pre>
	 * 상품 이용 등급.
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
	 *  상품 이용 등급.
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
	 * 판매자 회원번호.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getSellerMbrNo() {
		return this.sellerMbrNo;
	}

	/**
	 * 
	 * <pre>
	 *  판매자 회원번호.
	 * </pre>
	 * 
	 * @param sellerMbrNo
	 *            sellerMbrNo
	 */
	public void setSellerMbrNo(String sellerMbrNo) {
		this.sellerMbrNo = sellerMbrNo;
	}

	/**
	 * 
	 * <pre>
	 * 노출 판매자 명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getExpoSellerNm() {
		return this.expoSellerNm;
	}

	/**
	 * 
	 * <pre>
	 *  노출 판매자 명.
	 * </pre>
	 * 
	 * @param expoSellerNm
	 *            expoSellerNm
	 */
	public void setExpoSellerNm(String expoSellerNm) {
		this.expoSellerNm = expoSellerNm;
	}

	/**
	 * 
	 * <pre>
	 * 노출 판매자 전화번호.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getExpoSellerTelno() {
		return this.expoSellerTelno;
	}

	/**
	 * 
	 * <pre>
	 *  노출 판매자 전화번호.
	 * </pre>
	 * 
	 * @param expoSellerTelno
	 *            expoSellerTelno
	 */
	public void setExpoSellerTelno(String expoSellerTelno) {
		this.expoSellerTelno = expoSellerTelno;
	}

	/**
	 * 
	 * <pre>
	 * 노출 판매자 이메일.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getExpoSellerEmail() {
		return this.expoSellerEmail;
	}

	/**
	 * 
	 * <pre>
	 *  노출 판매자 이메일.
	 * </pre>
	 * 
	 * @param expoSellerEmail
	 *            expoSellerEmail
	 */
	public void setExpoSellerEmail(String expoSellerEmail) {
		this.expoSellerEmail = expoSellerEmail;
	}

	/**
	 * 
	 * <pre>
	 * Dolby 지원여부.
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
	 *  Dolby 지원여부.
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
	 * Dolby 지원여부.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getDolbyYn() {
		return this.dolbyYn;
	}

	/**
	 * 
	 * <pre>
	 *  Dolby 지원여부.
	 * </pre>
	 * 
	 * @param dolbyYn
	 *            dolbyYn
	 */
	public void setDolbyYn(String dolbyYn) {
		this.dolbyYn = dolbyYn;
	}

	/**
	 * 
	 * <pre>
	 * 일반화질 sub 컨텐츠 Id.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getNmSubContsId() {
		return this.nmSubContsId;
	}

	/**
	 * 
	 * <pre>
	 * 일반화질 sub 컨텐츠 Id.
	 * </pre>
	 * 
	 * @param nmSubContsId
	 *            nmSubContsId
	 */
	public void setNmSubContsId(String nmSubContsId) {
		this.nmSubContsId = nmSubContsId;
	}

	/**
	 * 
	 * <pre>
	 * SD화질 sub 컨텐츠 Id.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getSdSubContsid() {
		return this.sdSubContsid;
	}

	/**
	 * 
	 * <pre>
	 * SD화질 sub 컨텐츠 Id.
	 * </pre>
	 * 
	 * @param sdSubContsid
	 *            sdSubContsid
	 */
	public void setSdSubContsid(String sdSubContsid) {
		this.sdSubContsid = sdSubContsid;
	}

	/**
	 * 
	 * <pre>
	 * HD화질 sub 컨텐츠 Id.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getHdSubContsid() {
		return this.hdSubContsid;
	}

	/**
	 * 
	 * <pre>
	 * HD화질 sub 컨텐츠 Id.
	 * </pre>
	 * 
	 * @param hdSubContsid
	 *            hdSubContsid
	 */
	public void setHdSubContsid(String hdSubContsid) {
		this.hdSubContsid = hdSubContsid;
	}

	/**
	 * 
	 * <pre>
	 * 일반화질 상품버전.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getNmProdVer() {
		return this.nmProdVer;
	}

	/**
	 * 
	 * <pre>
	 * 일반화질 상품버전.
	 * </pre>
	 * 
	 * @param nmProdVer
	 *            nmProdVer
	 */
	public void setNmProdVer(String nmProdVer) {
		this.nmProdVer = nmProdVer;
	}

	/**
	 * 
	 * <pre>
	 * SD화질 상품버전.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getSdProdVer() {
		return this.sdProdVer;
	}

	/**
	 * 
	 * <pre>
	 * SD화질 상품버전.
	 * </pre>
	 * 
	 * @param sdProdVer
	 *            sdProdVer
	 */
	public void setSdProdVer(String sdProdVer) {
		this.sdProdVer = sdProdVer;
	}

	/**
	 * 
	 * <pre>
	 * HD화질 상품버전.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getHdProdVer() {
		return this.hdProdVer;
	}

	/**
	 * 
	 * <pre>
	 * HD화질 상품버전.
	 * </pre>
	 * 
	 * @param hdProdVer
	 *            hdProdVer
	 */
	public void setHdProdVer(String hdProdVer) {
		this.hdProdVer = hdProdVer;
	}

	/**
	 * 
	 * <pre>
	 * 일반화질 파일용량.
	 * </pre>
	 * 
	 * @return Integer
	 */
	public Integer getNmFileSize() {
		return this.nmFileSize;
	}

	/**
	 * 
	 * <pre>
	 * 일반화질 파일용량.
	 * </pre>
	 * 
	 * @param nmFileSize
	 *            nmFileSize
	 */
	public void setNmFileSize(Integer nmFileSize) {
		this.nmFileSize = nmFileSize;
	}

	/**
	 * 
	 * <pre>
	 * SD화질 파일용량.
	 * </pre>
	 * 
	 * @return Integer
	 */
	public Integer getSdFileSize() {
		return this.sdFileSize;
	}

	/**
	 * 
	 * <pre>
	 * SD화질 파일용량.
	 * </pre>
	 * 
	 * @param sdFileSize
	 *            sdFileSize
	 */
	public void setSdFileSize(Integer sdFileSize) {
		this.sdFileSize = sdFileSize;
	}

	/**
	 * 
	 * <pre>
	 * HD화질 파일용량.
	 * </pre>
	 * 
	 * @return Integer
	 */
	public Integer getHdFileSize() {
		return this.hdFileSize;
	}

	/**
	 * 
	 * <pre>
	 * HD화질 파일용량.
	 * </pre>
	 * 
	 * @param hdFileSize
	 *            hdFileSize
	 */
	public void setHdFileSize(Integer hdFileSize) {
		this.hdFileSize = hdFileSize;
	}

	/**
	 * 
	 * <pre>
	 * 일반화질 해상도.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getNmDpPixel() {
		return this.nmDpPixel;
	}

	/**
	 * 
	 * <pre>
	 * 일반화질 해상도.
	 * </pre>
	 * 
	 * @param nmDpPixel
	 *            nmDpPixel
	 */
	public void setNmDpPixel(String nmDpPixel) {
		this.nmDpPixel = nmDpPixel;
	}

	/**
	 * 
	 * <pre>
	 * SD화질 해상도.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getSdDpPixel() {
		return this.sdDpPixel;
	}

	/**
	 * 
	 * <pre>
	 * SD화질 해상도.
	 * </pre>
	 * 
	 * @param sdDpPixel
	 *            sdDpPixel
	 */
	public void setSdDpPixel(String sdDpPixel) {
		this.sdDpPixel = sdDpPixel;
	}

	/**
	 * 
	 * <pre>
	 * HD화질 해상도.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getHdDpPixel() {
		return this.hdDpPixel;
	}

	/**
	 * 
	 * <pre>
	 * HD화질 해상도.
	 * </pre>
	 * 
	 * @param hdDpPixel
	 *            hdDpPixel
	 */
	public void setHdDpPixel(String hdDpPixel) {
		this.hdDpPixel = hdDpPixel;
	}

	/**
	 * 
	 * <pre>
	 * 일반화질 화면비율.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getNmDpPicRatio() {
		return this.nmDpPicRatio;
	}

	/**
	 * 
	 * <pre>
	 * 일반화질 화면비율.
	 * </pre>
	 * 
	 * @param nmDpPicRatio
	 *            nmDpPicRatio
	 */
	public void setNmDpPicRatio(String nmDpPicRatio) {
		this.nmDpPicRatio = nmDpPicRatio;
	}

	/**
	 * 
	 * <pre>
	 * SD화질 화면비율.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getSdDpPicRatio() {
		return this.sdDpPicRatio;
	}

	/**
	 * 
	 * <pre>
	 * SD화질 화면비율.
	 * </pre>
	 * 
	 * @param sdDpPicRatio
	 *            sdDpPicRatio
	 */
	public void setSdDpPicRatio(String sdDpPicRatio) {
		this.sdDpPicRatio = sdDpPicRatio;
	}

	/**
	 * 
	 * <pre>
	 * HD화질 화면비율.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getHdDpPicRatio() {
		return this.hdDpPicRatio;
	}

	/**
	 * 
	 * <pre>
	 * HD화질 화면비율.
	 * </pre>
	 * 
	 * @param hdDpPicRatio
	 *            hdDpPicRatio
	 */
	public void setHdDpPicRatio(String hdDpPicRatio) {
		this.hdDpPicRatio = hdDpPicRatio;
	}

	/**
	 * 
	 * <pre>
	 * 일반화질 BTV CID.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getNmBtvCid() {
		return this.nmBtvCid;
	}

	/**
	 * 
	 * <pre>
	 * 일반화질 BTV CID.
	 * </pre>
	 * 
	 * @param nmBtvCid
	 *            nmBtvCid
	 */
	public void setNmBtvCid(String nmBtvCid) {
		this.nmBtvCid = nmBtvCid;
	}

	/**
	 * 
	 * <pre>
	 * SD화질 BTV CID.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getSdBtvCid() {
		return this.sdBtvCid;
	}

	/**
	 * 
	 * <pre>
	 * SD화질 BTV CID.
	 * </pre>
	 * 
	 * @param sdBtvCid
	 *            sdBtvCid
	 */
	public void setSdBtvCid(String sdBtvCid) {
		this.sdBtvCid = sdBtvCid;
	}

	/**
	 * 
	 * <pre>
	 * HD화질 BTV CID.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getHdBtvCid() {
		return this.hdBtvCid;
	}

	/**
	 * 
	 * <pre>
	 * HD화질 BTV CID.
	 * </pre>
	 * 
	 * @param hdBtvCid
	 *            hdBtvCid
	 */
	public void setHdBtvCid(String hdBtvCid) {
		this.hdBtvCid = hdBtvCid;
	}

	/**
	 * 
	 * <pre>
	 * 다운로드 망 코드.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getDwldNetworkCd() {
		return this.dwldNetworkCd;
	}

	/**
	 * 
	 * <pre>
	 * 다운로드 망 코드.
	 * </pre>
	 * 
	 * @param dwldNetworkCd
	 *            dwldNetworkCd
	 */
	public void setDwldNetworkCd(String dwldNetworkCd) {
		this.dwldNetworkCd = dwldNetworkCd;
	}

	/**
	 * 
	 * <pre>
	 * 바로보기 망 코드.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getStrmNetworkCd() {
		return this.strmNetworkCd;
	}

	/**
	 * 
	 * <pre>
	 * 바로보기 망 코드.
	 * </pre>
	 * 
	 * @param strmNetworkCd
	 *            strmNetworkCd
	 */
	public void setStrmNetworkCd(String strmNetworkCd) {
		this.strmNetworkCd = strmNetworkCd;
	}

	/**
	 * 
	 * <pre>
	 * 이미지 경로.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getImagePath() {
		return this.imagePath;
	}

	/**
	 * 
	 * <pre>
	 * 이미지 경로.
	 * </pre>
	 * 
	 * @param imagePath
	 *            imagePath
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	/**
	 * 
	 * <pre>
	 * 이미지 명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getImageNm() {
		return this.imageNm;
	}

	/**
	 * 
	 * <pre>
	 * 이미지 명.
	 * </pre>
	 * 
	 * @param imageNm
	 *            imageNm
	 */
	public void setImageNm(String imageNm) {
		this.imageNm = imageNm;
	}

	/**
	 * 
	 * <pre>
	 * 이미지 용량.
	 * </pre>
	 * 
	 * @return String
	 */
	public Integer getImageSize() {
		return this.imageSize;
	}

	/**
	 * 
	 * <pre>
	 * 이미지 용량.
	 * </pre>
	 * 
	 * @param filePath
	 *            filePath
	 */
	public void setImageSize(Integer imageSize) {
		this.imageSize = imageSize;
	}

	/**
	 * 
	 * <pre>
	 * 파일 경로.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getFilePath() {
		return this.filePath;
	}

	/**
	 * 
	 * <pre>
	 * 파일 경로.
	 * </pre>
	 * 
	 * @param filePath
	 *            filePath
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
