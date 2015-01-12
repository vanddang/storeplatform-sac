/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.feature.recommend.vo;

/**
 * 
 * 
 * Updated on : 2014. 1. 6. Updated by : 서영배, GTSOFT.
 */
/**
 * Calss 설명
 * 
 * Updated on : 2014. 2. 21. Updated by : 홍길동, SK 플래닛.
 */
public class RecommendAdmin {

	private int totalCount;
	private Integer dwldCnt;
	private Integer prchsCnt;
	private String topMenuId;
	private String menuId;
	private String topMenuNm;
	private String menuNm;
	private String prodId;
	private String aid;
	private String prodNm;
	private String prodBaseDesc;
	private Integer prodAmt;
	private String prodGrdCd;
	private String partParentClsfCd;
	private String drmYn;
	private String apkPkgNm;
	private String apkVer;
	private Integer fileSize;
	private String prodVer;
	private String filePath;
	private String verMajor;
	private String verMinor;
	private String fileNm;
	private Double avgEvluScore;

	/**
	 * <pre>
	 * 총 리스트수.
	 * </pre>
	 * 
	 * @return int
	 */
	public int getTotalCount() {
		return this.totalCount;
	}

	/**
	 * <pre>
	 * 총 리스트수.
	 * </pre>
	 * 
	 * @param totalCount
	 *            totalCount
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * <pre>
	 * 탑메뉴 아이디.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getTopMenuId() {
		return this.topMenuId;
	}

	/**
	 * <pre>
	 * 다운로드수.
	 * </pre>
	 * 
	 * @return Integer
	 */
	public Integer getDwldCnt() {
		return this.dwldCnt;
	}

	/**
	 * <pre>
	 * 다운로드수.
	 * </pre>
	 * 
	 * @param dwldCnt
	 *            dwldCnt
	 */
	public void setDwldCnt(Integer dwldCnt) {
		this.dwldCnt = dwldCnt;
	}

	/**
	 * <pre>
	 * 구매수.
	 * </pre>
	 * 
	 * @return Integer
	 */
	public Integer getPrchsCnt() {
		return this.prchsCnt;
	}

	/**
	 * <pre>
	 * 구매수.
	 * </pre>
	 * 
	 * @param prchsCnt
	 *            prchsCnt
	 */
	public void setPrchsCnt(Integer prchsCnt) {
		this.prchsCnt = prchsCnt;
	}

	/**
	 * <pre>
	 * 탑메뉴 아이디.
	 * </pre>
	 * 
	 * @param topMenuId
	 *            topMenuId
	 */
	public void setTopMenuId(String topMenuId) {
		this.topMenuId = topMenuId;
	}

	/**
	 * <pre>
	 * 메뉴 아이디.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getMenuId() {
		return this.menuId;
	}

	/**
	 * <pre>
	 * 메뉴 아이디.
	 * </pre>
	 * 
	 * @param menuId
	 *            menuId
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/**
	 * <pre>
	 * 상품 아이디.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getProdId() {
		return this.prodId;
	}

	/**
	 * <pre>
	 * 상품 아이디.
	 * </pre>
	 * 
	 * @param prodId
	 *            prodId
	 */
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	/**
	 * <pre>
	 * aid.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getAid() {
		return this.aid;
	}

	/**
	 * <pre>
	 * aid.
	 * </pre>
	 * 
	 * @param aid
	 *            aid
	 */
	public void setAid(String aid) {
		this.aid = aid;
	}

	/**
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
	 * <pre>
	 * 상품설명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getProdBaseDesc() {
		return this.prodBaseDesc;
	}

	/**
	 * <pre>
	 * 상품설명.
	 * </pre>
	 * 
	 * @param prodBaseDesc
	 *            prodBaseDesc
	 */
	public void setProdBaseDesc(String prodBaseDesc) {
		this.prodBaseDesc = prodBaseDesc;
	}

	/**
	 * <pre>
	 * 상품등급.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getProdGrdCd() {
		return this.prodGrdCd;
	}

	/**
	 * <pre>
	 * 상품등급.
	 * </pre>
	 * 
	 * @param prodGrdCd
	 *            prodGrdCd
	 */
	public void setProdGrdCd(String prodGrdCd) {
		this.prodGrdCd = prodGrdCd;
	}

	/**
	 * <pre>
	 * partParentClsfCd.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getPartParentClsfCd() {
		return this.partParentClsfCd;
	}

	/**
	 * <pre>
	 * partParentClsfCd.
	 * </pre>
	 * 
	 * @param partParentClsfCd
	 *            partParentClsfCd
	 */
	public void setPartParentClsfCd(String partParentClsfCd) {
		this.partParentClsfCd = partParentClsfCd;
	}

	/**
	 * <pre>
	 * DRM 여부.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getDrmYn() {
		return this.drmYn;
	}

	/**
	 * <pre>
	 * DRM 여부.
	 * </pre>
	 * 
	 * @param drmYn
	 *            drmYn
	 */
	public void setDrmYn(String drmYn) {
		this.drmYn = drmYn;
	}

	/**
	 * <pre>
	 * 패키지명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getApkPkgNm() {
		return this.apkPkgNm;
	}

	/**
	 * <pre>
	 * 패키지명.
	 * </pre>
	 * 
	 * @param apkPkgNm
	 *            apkPkgNm
	 */
	public void setApkPkgNm(String apkPkgNm) {
		this.apkPkgNm = apkPkgNm;
	}

	/**
	 * <pre>
	 * apkVer.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getApkVer() {
		return this.apkVer;
	}

	/**
	 * <pre>
	 * apkVer.
	 * </pre>
	 * 
	 * @param apkVer
	 *            apkVer
	 */
	public void setApkVer(String apkVer) {
		this.apkVer = apkVer;
	}

	/**
	 * <pre>
	 * 상품가격.
	 * </pre>
	 * 
	 * @return Integer
	 */
	public Integer getProdAmt() {
		return this.prodAmt;
	}

	/**
	 * <pre>
	 * 상품가격.
	 * </pre>
	 * 
	 * @param prodAmt
	 *            prodAmt
	 */
	public void setProdAmt(Integer prodAmt) {
		this.prodAmt = prodAmt;
	}

	/**
	 * <pre>
	 * 파일사이즈.
	 * </pre>
	 * 
	 * @return Integer
	 */
	public Integer getFileSize() {
		return this.fileSize;
	}

	/**
	 * <pre>
	 * 파일사이즈.
	 * </pre>
	 * 
	 * @param fileSize
	 *            fileSize
	 */
	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * <pre>
	 * prodVer.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getProdVer() {
		return this.prodVer;
	}

	/**
	 * <pre>
	 * prodVer.
	 * </pre>
	 * 
	 * @param prodVer
	 *            prodVer
	 */
	public void setProdVer(String prodVer) {
		this.prodVer = prodVer;
	}

	/**
	 * <pre>
	 * 파일경로.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getFilePath() {
		return this.filePath;
	}

	/**
	 * <pre>
	 * 파일경로.
	 * </pre>
	 * 
	 * @param filePath
	 *            filePath
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * <pre>
	 * 탑메뉴명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getTopMenuNm() {
		return this.topMenuNm;
	}

	/**
	 * <pre>
	 * 탑메뉴명.
	 * </pre>
	 * 
	 * @param topMenuNm
	 *            topMenuNm
	 */
	public void setTopMenuNm(String topMenuNm) {
		this.topMenuNm = topMenuNm;
	}

	/**
	 * <pre>
	 * 메뉴명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getMenuNm() {
		return this.menuNm;
	}

	/**
	 * <pre>
	 * 메뉴명.
	 * </pre>
	 * 
	 * @param menuNm
	 *            menuNm
	 */
	public void setMenuNm(String menuNm) {
		this.menuNm = menuNm;
	}

	/**
	 * <pre>
	 * verMajor.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getVerMajor() {
		return this.verMajor;
	}

	/**
	 * <pre>
	 * verMajor.
	 * </pre>
	 * 
	 * @param verMajor
	 *            verMajor
	 */
	public void setVerMajor(String verMajor) {
		this.verMajor = verMajor;
	}

	/**
	 * <pre>
	 * verMinor.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getVerMinor() {
		return this.verMinor;
	}

	/**
	 * <pre>
	 * verMinor.
	 * </pre>
	 * 
	 * @param verMinor
	 *            verMinor
	 */
	public void setVerMinor(String verMinor) {
		this.verMinor = verMinor;
	}

	/**
	 * <pre>
	 * 파일명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getFileNm() {
		return this.fileNm;
	}

	/**
	 * <pre>
	 * 파일명.
	 * </pre>
	 * 
	 * @param fileNm
	 *            fileNm
	 */
	public void setFileNm(String fileNm) {
		this.fileNm = fileNm;
	}

	/**
	 * <pre>
	 * 평점.
	 * </pre>
	 * 
	 * @return Double
	 */
	public Double getAvgEvluScore() {
		return this.avgEvluScore;
	}

	/**
	 * <pre>
	 * 평점.
	 * </pre>
	 * 
	 * @param avgEvluScore
	 *            avgEvluScore
	 */
	public void setAvgEvluScore(Double avgEvluScore) {
		this.avgEvluScore = avgEvluScore;
	}

}
