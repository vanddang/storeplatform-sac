/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.purchase.vo.prototype;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * [Prototype] 구매내역 조회 - 상품정보VO
 * 
 * Updated on : 2014. 1. 16. Updated by : 이승택, nTels.
 */
public class MyPageProduct extends CommonInfo {
	private static final long serialVersionUID = 201311131L;

	private String prodId;
	private String chnlProdId;
	private String svcGrpCd;
	private String prodGrdCd;
	private String expoSellerNm;
	private String expoSellerTelno;
	private String expoSellerEmail;
	private String prodNm;
	private String artist1Nm;
	private String artist2Nm;
	private String artist3Nm;
	private String imagePath;
	private String categoryNo;
	private String categoryNm;
	private String topCategoryNo;
	private String topCategoryNm;
	private String prodClsfCd;
	private String platClsfCd;
	private String apkPkgNm;
	private String apkVer;
	private String drmYn;
	private String hdcpYn;
	private String dolbySprtYn;
	private String hdvYn;
	private String btvYn;
	private String metaClsfCd;
	private String chapter;
	private String chapterUnit;
	private String regDt;
	private String playTm;
	private String bookClsfCd;
	private String compNm;
	private String svcStartDt;
	private String streamYn;
	private String supportYn;

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
	 * @return the svcGrpCd
	 */
	public String getSvcGrpCd() {
		return this.svcGrpCd;
	}

	/**
	 * @param svcGrpCd
	 *            the svcGrpCd to set
	 */
	public void setSvcGrpCd(String svcGrpCd) {
		this.svcGrpCd = svcGrpCd;
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
	 * @return the expoSellerTelno
	 */
	public String getExpoSellerTelno() {
		return this.expoSellerTelno;
	}

	/**
	 * @param expoSellerTelno
	 *            the expoSellerTelno to set
	 */
	public void setExpoSellerTelno(String expoSellerTelno) {
		this.expoSellerTelno = expoSellerTelno;
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
	 * @return the categoryNo
	 */
	public String getCategoryNo() {
		return this.categoryNo;
	}

	/**
	 * @param categoryNo
	 *            the categoryNo to set
	 */
	public void setCategoryNo(String categoryNo) {
		this.categoryNo = categoryNo;
	}

	/**
	 * @return the categoryNm
	 */
	public String getCategoryNm() {
		return this.categoryNm;
	}

	/**
	 * @param categoryNm
	 *            the categoryNm to set
	 */
	public void setCategoryNm(String categoryNm) {
		this.categoryNm = categoryNm;
	}

	/**
	 * @return the topCategoryNo
	 */
	public String getTopCategoryNo() {
		return this.topCategoryNo;
	}

	/**
	 * @param topCategoryNo
	 *            the topCategoryNo to set
	 */
	public void setTopCategoryNo(String topCategoryNo) {
		this.topCategoryNo = topCategoryNo;
	}

	/**
	 * @return the topCategoryNm
	 */
	public String getTopCategoryNm() {
		return this.topCategoryNm;
	}

	/**
	 * @param topCategoryNm
	 *            the topCategoryNm to set
	 */
	public void setTopCategoryNm(String topCategoryNm) {
		this.topCategoryNm = topCategoryNm;
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
	 * @return the compNm
	 */
	public String getCompNm() {
		return this.compNm;
	}

	/**
	 * @param compNm
	 *            the compNm to set
	 */
	public void setCompNm(String compNm) {
		this.compNm = compNm;
	}

	/**
	 * @return the svcStartDt
	 */
	public String getSvcStartDt() {
		return this.svcStartDt;
	}

	/**
	 * @param svcStartDt
	 *            the svcStartDt to set
	 */
	public void setSvcStartDt(String svcStartDt) {
		this.svcStartDt = svcStartDt;
	}

	/**
	 * @return the streamYn
	 */
	public String getStreamYn() {
		return this.streamYn;
	}

	/**
	 * @param streamYn
	 *            the streamYn to set
	 */
	public void setStreamYn(String streamYn) {
		this.streamYn = streamYn;
	}

	/**
	 * @return the supportYn
	 */
	public String getSupportYn() {
		return this.supportYn;
	}

	/**
	 * @param supportYn
	 *            the supportYn to set
	 */
	public void setSupportYn(String supportYn) {
		this.supportYn = supportYn;
	}

}
