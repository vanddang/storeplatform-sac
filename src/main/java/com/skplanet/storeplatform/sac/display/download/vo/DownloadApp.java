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
 * 다운로드 앱 상품정보 Default Value Object.
 * 
 * Updated on : 2014. 1.23. Updated by : 이석희, 아이에스플러스.
 */
public class DownloadApp {
	private int totalCount; // 전체건수
	private String topMenuId; // top Menu Id
	private String topMenuNm; // top Menu 명
	private String menuId; // Menu Id
	private String menuNm; // Menu 명
	private String prodId; // 상품 Id
	private String aid; // 앱 Id
	private String prodNm; // 상품명
	private String prodGrdCd; // 상품 등급 코드
	private String prodClsfCd; // 상품 구분 코드
	private String sellerMbrNo; // 판매자 회원 번호
	private String expoSellerNm; // 노출 판매자명
	private String expoSellerTelNo; // 노출 판매자 번호
	private String expoSellerEmail; // 노출 판매다 이메일
	private String prodVer; // 상품 버전
	private String apkPkgNm; // 앱 패키지명
	private String apkVerCd; // 앱 버전 코드
	private String supportedOs; // 지원 OS
	private Integer apkFileSize; // 앱 용량
	private String filePath; // 앱 파일 경로
	private String imagePath; // 앱 이미지 경로
	private String sysDate;
	private String subContentsId; // sub contents Id

	// Seed 정보
	private String seedProductId; // Seed 상품 Id
	private String seedCaseRefCd; // Seed 유형 참조 코드
	private String seedUseYn; // 사용여부
	private String platClsfCd; // 플랫폼 구분
	private String gameCentrVerCd; // 게임센터 버전코드
	private String gameCentrId; // 게임센터 Id

	/**
	 * 
	 * <pre>
	 * 전체건수.
	 * </pre>
	 * 
	 * @return int
	 */
	public int getTotalCount() {
		return this.totalCount;
	}

	/**
	 * 
	 * <pre>
	 * 전체건수.
	 * </pre>
	 * 
	 * @param totalCount
	 *            totalCount
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
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
	 * 상품 Id.
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
	 * 상품 Id.
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
	 * 앱 Id.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getAid() {
		return this.aid;
	}

	/**
	 * 
	 * <pre>
	 * 앱 Id.
	 * </pre>
	 * 
	 * @param aid
	 *            aid
	 */
	public void setAid(String aid) {
		this.aid = aid;
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
	 * 상품명.
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
	 * 상품 등급 코드.
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
	 * 상품 등급 코드.
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
	 * 상품 구분 코드.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getProdClsfCd() {
		return this.prodClsfCd;
	}

	/**
	 * 
	 * <pre>
	 * 상품 구분 코드.
	 * </pre>
	 * 
	 * @param prodClsfCd
	 *            prodClsfCd
	 */
	public void setProdClsfCd(String prodClsfCd) {
		this.prodClsfCd = prodClsfCd;
	}

	/**
	 * 
	 * <pre>
	 * 판매자 회원 번호.
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
	 * 판매자 회원 번호.
	 * </pre>
	 * 
	 * @param prodClsfCd
	 *            prodClsfCd
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
	 * 노출 판매자 명.
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
	 * 노출 판매자 전화 번호.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getExpoSellerTelNo() {
		return this.expoSellerTelNo;
	}

	/**
	 * 
	 * <pre>
	 * 노출 판매자 전화 번호.
	 * </pre>
	 * 
	 * @param expoSellerTelNo
	 *            expoSellerTelNo
	 */
	public void setExpoSellerTelNo(String expoSellerTelNo) {
		this.expoSellerTelNo = expoSellerTelNo;
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
	 * 노출 판매자 이메일.
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
	 * 상품 버전.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getProdVer() {
		return this.prodVer;
	}

	/**
	 * 
	 * <pre>
	 * 상품 버전.
	 * </pre>
	 * 
	 * @param prodVer
	 *            prodVer
	 */
	public void setProdVer(String prodVer) {
		this.prodVer = prodVer;
	}

	/**
	 * 
	 * <pre>
	 * 앱 패키지 명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getApkPkgNm() {
		return this.apkPkgNm;
	}

	/**
	 * 
	 * <pre>
	 * 앱 패키지 명.
	 * </pre>
	 * 
	 * @param apkPkgNm
	 *            apkPkgNm
	 */
	public void setApkPkgNm(String apkPkgNm) {
		this.apkPkgNm = apkPkgNm;
	}

	/**
	 * 
	 * <pre>
	 * 앱 버전 코드.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getApkVerCd() {
		return this.apkVerCd;
	}

	/**
	 * 
	 * <pre>
	 * 앱 버전 코드.
	 * </pre>
	 * 
	 * @param apkVerCd
	 *            apkVerCd
	 */
	public void setApkVerCd(String apkVerCd) {
		this.apkVerCd = apkVerCd;
	}

	/**
	 * 
	 * <pre>
	 * 지원 OS.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getSupportedOs() {
		return this.supportedOs;
	}

	/**
	 * 
	 * <pre>
	 * 지원 OS.
	 * </pre>
	 * 
	 * @param supportedOs
	 *            supportedOs
	 */
	public void setSupportedOs(String supportedOs) {
		this.supportedOs = supportedOs;
	}

	/**
	 * 
	 * <pre>
	 * 앱 용량.
	 * </pre>
	 * 
	 * @return Integer
	 */
	public Integer getApkFileSize() {
		return this.apkFileSize;
	}

	/**
	 * 
	 * <pre>
	 * 앱 용량.
	 * </pre>
	 * 
	 * @param apkFileSize
	 *            apkFileSize
	 */
	public void setApkFileSize(Integer apkFileSize) {
		this.apkFileSize = apkFileSize;
	}

	/**
	 * 
	 * <pre>
	 * 앱 파일 경로.
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
	 * 앱 파일 경로.
	 * </pre>
	 * 
	 * @param filePath
	 *            filePath
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * 
	 * <pre>
	 * 앱 이미지 경로.
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
	 * 앱 이미지 경로.
	 * </pre>
	 * 
	 * @param imgPath
	 *            imgPath
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
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
	 * 
	 * <pre>
	 * Seed 상품 Id.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getSeedProductId() {
		return this.seedProductId;
	}

	/**
	 * 
	 * <pre>
	 * Seed 상품 Id.
	 * </pre>
	 * 
	 * @param seedProductId
	 *            seedProductId
	 */
	public void setSeedProductId(String seedProductId) {
		this.seedProductId = seedProductId;
	}

	/**
	 * 
	 * <pre>
	 * seed 유형 참조 코드.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getSeedCaseRefCd() {
		return this.seedCaseRefCd;
	}

	/**
	 * 
	 * <pre>
	 * Seed 유형 참조 코드.
	 * </pre>
	 * 
	 * @param seedCaseRefCd
	 *            seedCaseRefCd
	 */
	public void setSeedCaseRefCd(String seedCaseRefCd) {
		this.seedCaseRefCd = seedCaseRefCd;
	}

	/**
	 * 
	 * <pre>
	 * 사용여부.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getSeedUseYn() {
		return this.seedUseYn;
	}

	/**
	 * 
	 * <pre>
	 * 사용여부.
	 * </pre>
	 * 
	 * @param seedUseYn
	 *            seedUseYn
	 */
	public void setSeedUseYn(String seedUseYn) {
		this.seedUseYn = seedUseYn;
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
}
