/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.feature.best.vo;

/**
 * BEST 앱 DTO Default Value Object.
 * 
 * Updated on : 2014. 1. 8. Updated by : 이석희, 아이에스플러스.
 */
public class BestApp {
	private int totalCount;
	private String prodId;
	private String prodNm;
	private String menuId;
	private String upMenuid;
	private String upMenuNm;
	private String topMenuId;
	private String prodGrdCd;
	private String prodBaseDesc;
	private String prodDtlDesc;
	private String partParentClsfCd;
	private String drmYn;
	private String apkVer;
	private String aid;
	private String apkPkgNm;
	private String menuNm;
	private String menuNesc;
	private int prodAmt;
	private String apkVerCd;
	private Double avgEvluScore;
	private Integer dwldCnt;
	private Integer paticpersCnt;
	private String imgPath;
	private String imgNm;
	private Integer imgSize;

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
	 * 상품 ID.
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
	 * 상품 ID.
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
	 * 상위 Menu Id.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getUpMenuid() {
		return this.upMenuid;
	}

	/**
	 * 
	 * <pre>
	 * 상위 Menu Id.
	 * </pre>
	 * 
	 * @param upMenuid
	 *            upMenuid
	 */
	public void setUpMenuid(String upMenuid) {
		this.upMenuid = upMenuid;
	}

	/**
	 * 
	 * <pre>
	 * 상위 Menu 명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getUpMenuNm() {
		return this.upMenuNm;
	}

	/**
	 * 
	 * <pre>
	 * 상위 Menu 명.
	 * </pre>
	 * 
	 * @param upMenuNm
	 *            upMenuNm
	 */
	public void setUpMenuNm(String upMenuNm) {
		this.upMenuNm = upMenuNm;
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
	 * 상품 기본설명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getProdBaseDesc() {
		return this.prodBaseDesc;
	}

	/**
	 * 
	 * <pre>
	 * 상품 기본설명.
	 * </pre>
	 * 
	 * @param prodBaseDesc
	 *            prodBaseDesc
	 */
	public void setProdBaseDesc(String prodBaseDesc) {
		this.prodBaseDesc = prodBaseDesc;
	}

	/**
	 * 
	 * <pre>
	 * 상품 상세설명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getProdDtlDesc() {
		return this.prodDtlDesc;
	}

	/**
	 * 
	 * <pre>
	 * 상품 상세설명.
	 * </pre>
	 * 
	 * @param prodDtlDesc
	 *            prodDtlDesc
	 */
	public void setProdDtlDesc(String prodDtlDesc) {
		this.prodDtlDesc = prodDtlDesc;
	}

	/**
	 * 
	 * <pre>
	 * 부분 부모 구분 코드.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getPartParentClsfCd() {
		return this.partParentClsfCd;
	}

	/**
	 * 
	 * <pre>
	 * 부분 부모 구분 코드.
	 * </pre>
	 * 
	 * @param partParentClsfCd
	 *            partParentClsfCd
	 */
	public void setPartParentClsfCd(String partParentClsfCd) {
		this.partParentClsfCd = partParentClsfCd;
	}

	/**
	 * 
	 * <pre>
	 * Drm 여부.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getDrmYn() {
		return this.drmYn;
	}

	/**
	 * 
	 * <pre>
	 * Drm 여부.
	 * </pre>
	 * 
	 * @param drmYn
	 *            drmYn
	 */
	public void setDrmYn(String drmYn) {
		this.drmYn = drmYn;
	}

	/**
	 * 
	 * <pre>
	 * App 버전.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getApkVer() {
		return this.apkVer;
	}

	/**
	 * 
	 * <pre>
	 * App 버전.
	 * </pre>
	 * 
	 * @param apkVer
	 *            apkVer
	 */
	public void setApkVer(String apkVer) {
		this.apkVer = apkVer;
	}

	/**
	 * 
	 * <pre>
	 * App Id.
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
	 * App Id.
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
	 * App 패키지 명.
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
	 * App 패키지 명.
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
	 * Menu 설명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getMenuNesc() {
		return this.menuNesc;
	}

	/**
	 * 
	 * <pre>
	 * Menu 설명.
	 * </pre>
	 * 
	 * @param menuNesc
	 *            menuNesc
	 */
	public void setMenuNesc(String menuNesc) {
		this.menuNesc = menuNesc;
	}

	/**
	 * 
	 * <pre>
	 * 상품가격.
	 * </pre>
	 * 
	 * @return int
	 */
	public int getProdAmt() {
		return this.prodAmt;
	}

	/**
	 * 
	 * <pre>
	 * 상품가격.
	 * </pre>
	 * 
	 * @param prodAmt
	 *            prodAmt
	 */
	public void setProdAmt(int prodAmt) {
		this.prodAmt = prodAmt;
	}

	/**
	 * 
	 * <pre>
	 * App 버전 코드.
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
	 * App 버전 코드.
	 * </pre>
	 * 
	 * @param apkVerCd
	 *            apkVerCd
	 */
	public void setApkVerCd(String apkVerCd) {
		this.apkVerCd = apkVerCd;
	}

	public Double getAvgEvluScore() {
		return this.avgEvluScore;
	}

	public void setAvgEvluScore(Double avgEvluScore) {
		this.avgEvluScore = avgEvluScore;
	}

	/**
	 * 
	 * <pre>
	 * 이미지(파일) 경로.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getImgPath() {
		return this.imgPath;
	}

	/**
	 * 
	 * <pre>
	 * 이미지(파일) 경로.
	 * </pre>
	 * 
	 * @param imgPath
	 *            imgPath
	 */
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	/**
	 * 
	 * <pre>
	 * 이미지(파일) 명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getImgNm() {
		return this.imgNm;
	}

	/**
	 * 
	 * <pre>
	 * 이미지(파일) 명.
	 * </pre>
	 * 
	 * @param imgNm
	 *            imgNm
	 */
	public void setImgNm(String imgNm) {
		this.imgNm = imgNm;
	}

	/**
	 * 
	 * <pre>
	 * 다운로드 수.
	 * </pre>
	 * 
	 * @return Integer
	 */
	public Integer getDwldCnt() {
		return this.dwldCnt;
	}

	/**
	 * 
	 * <pre>
	 * 다운로드 수.
	 * </pre>
	 * 
	 * @param dwldCnt
	 *            dwldCnt
	 */
	public void setDwldCnt(Integer dwldCnt) {
		this.dwldCnt = dwldCnt;
	}

	/**
	 * 
	 * <pre>
	 * 참여자 수.
	 * </pre>
	 * 
	 * @return Integer
	 */
	public Integer getPaticpersCnt() {
		return this.paticpersCnt;
	}

	/**
	 * 
	 * <pre>
	 * 참여자 수.
	 * </pre>
	 * 
	 * @param paticpersCnt
	 *            paticpersCnt
	 */
	public void setPaticpersCnt(Integer paticpersCnt) {
		this.paticpersCnt = paticpersCnt;
	}

	/**
	 * 
	 * <pre>
	 * 이미지(파일) 크기.
	 * </pre>
	 * 
	 * @return Integer
	 */
	public Integer getImgSize() {
		return this.imgSize;
	}

	/**
	 * 
	 * <pre>
	 * 이미지(파일) 크기.
	 * </pre>
	 * 
	 * @param imgSize
	 *            imgSize
	 */
	public void setImgSize(Integer imgSize) {
		this.imgSize = imgSize;
	}

}
