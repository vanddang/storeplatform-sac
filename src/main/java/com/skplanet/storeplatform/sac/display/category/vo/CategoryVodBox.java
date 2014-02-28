/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.category.vo;

/**
 * Vod 보관함 Value Object.
 * 
 * Updated on : 2014. 02. 12. Updated by : 유시혁.
 */
public class CategoryVodBox {
	private Integer totalCount;
	private String topMenuId;
	private String topMenuNm;
	private String menuId;
	private String menuNm;
	private String metaClsfCd;
	private String cid;
	private String chnlProdId;
	private String prodId;
	private String nmSubContentsId;
	private String sdSubContentsId;
	private String hdSubContentsId;
	private String nmProdVer;
	private String sdProdVer;
	private String hdProdVer;
	private String nmFileSize;
	private String sdFileSize;
	private String hdFileSize;
	private String nmRsltnCd;
	private String nmRsltnNm;
	private String sdRsltnCd;
	private String sdRsltnNm;
	private String hdRsltnCd;
	private String hdRsltnNm;
	private String nmDpPgRatioCd;
	private String nmDpPgRatioNm;
	private String sdDpPgRatioCd;
	private String sdDpPgRatioNm;
	private String hdDpPgRatioCd;
	private String hdDpPgRatioNm;
	private String nmBtvCid;
	private String sdBtvCid;
	private String hdBtvCid;
	private Integer prodAmt;
	private Integer prodNetAmt;
	private String drmYn;
	private String prodStatusCd;
	private String btvYn;
	private String prodNm;
	private Integer chapter;
	private String chapterUnit;
	private String issueDay;
	private String prodBaseDesc;
	private String usePeriodUnitCd;
	private String usePeriodUnitNm;
	private String usePeriod;
	private String samplUrl;
	private String scSamplUrl;
	private String epsdPlayTm;
	private String prodGrdCd;
	private String hdvYn;
	private String artist1Nm;
	private String artist2Nm;
	private String brdcCompCd;
	private String brdcCompNm;
	private String chnlCompNm;
	private String agencyNm;
	private String regDt;

	/**
	 * 
	 * <pre>
	 * 총 건수.
	 * </pre>
	 * 
	 * @return Integer
	 */
	public Integer getTotalCount() {
		return this.totalCount;
	}

	/**
	 * 
	 * <pre>
	 * 총 건수.
	 * </pre>
	 * 
	 * @param totalCount
	 *            Integer
	 */
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 
	 * <pre>
	 * 탑 메뉴 ID.
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
	 * 탑 메뉴 ID.
	 * </pre>
	 * 
	 * @param topMenuId
	 *            String
	 */
	public void setTopMenuId(String topMenuId) {
		this.topMenuId = topMenuId;
	}

	/**
	 * 
	 * <pre>
	 * 탑 메뉴 명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getTopMenuNm() {
		return this.topMenuNm;
	}

	/**
	 * <pre>
	 * 탑 메뉴 명.
	 * </pre>
	 * 
	 * @param topMenuNm
	 *            String
	 */
	public void setTopMenuNm(String topMenuNm) {
		this.topMenuNm = topMenuNm;
	}

	/**
	 * <pre>
	 * 메뉴 ID.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getMenuId() {
		return this.menuId;
	}

	/**
	 * <pre>
	 * 메뉴 ID.
	 * </pre>
	 * 
	 * @param menuId
	 *            String
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/**
	 * 
	 * <pre>
	 * 메뉴 명.
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
	 * 메뉴 명.
	 * </pre>
	 * 
	 * @param menuNm
	 *            String
	 */
	public void setMenuNm(String menuNm) {
		this.menuNm = menuNm;
	}

	/**
	 * 
	 * <pre>
	 * 메타_구분_코드.
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
	 * 메타_구분_코드.
	 * </pre>
	 * 
	 * @param metaClsfCd
	 *            String
	 */
	public void setMetaClsfCd(String metaClsfCd) {
		this.metaClsfCd = metaClsfCd;
	}

	/**
	 * 
	 * <pre>
	 * CID.
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
	 * CID.
	 * </pre>
	 * 
	 * @param cid
	 *            String
	 */
	public void setCid(String cid) {
		this.cid = cid;
	}

	/**
	 * 
	 * <pre>
	 * 채널 상품 ID.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getChnlProdId() {
		return this.chnlProdId;
	}

	/**
	 * 
	 * <pre>
	 * 채널 상품 ID.
	 * </pre>
	 * 
	 * @param chnlProdId
	 *            String
	 */
	public void setChnlProdId(String chnlProdId) {
		this.chnlProdId = chnlProdId;
	}

	/**
	 * 
	 * <pre>
	 * 상품_ID.
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
	 * 상품_ID.
	 * </pre>
	 * 
	 * @param prodId
	 *            String
	 */
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	/**
	 * 
	 * <pre>
	 * NM 컨텐츠 ID.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getNmSubContentsId() {
		return this.nmSubContentsId;
	}

	/**
	 * 
	 * <pre>
	 * NM 컨텐츠 ID.
	 * </pre>
	 * 
	 * @param nmSubContentsId
	 *            String
	 */
	public void setNmSubContentsId(String nmSubContentsId) {
		this.nmSubContentsId = nmSubContentsId;
	}

	/**
	 * 
	 * <pre>
	 * SD 컨텐츠 ID.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getSdSubContentsId() {
		return this.sdSubContentsId;
	}

	/**
	 * 
	 * <pre>
	 * SD 컨텐츠 ID.
	 * </pre>
	 * 
	 * @param sdSubContentsId
	 *            String
	 */
	public void setSdSubContentsId(String sdSubContentsId) {
		this.sdSubContentsId = sdSubContentsId;
	}

	/**
	 * 
	 * <pre>
	 * HD 컨텐츠 ID.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getHdSubContentsId() {
		return this.hdSubContentsId;
	}

	/**
	 * 
	 * <pre>
	 * HD 컨텐츠 ID.
	 * </pre>
	 * 
	 * @param hdSubContentsId
	 *            String
	 */
	public void setHdSubContentsId(String hdSubContentsId) {
		this.hdSubContentsId = hdSubContentsId;
	}

	/**
	 * 
	 * <pre>
	 * NM 상품_버전.
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
	 * NM 상품_버전.
	 * </pre>
	 * 
	 * @param nmProdVer
	 *            String
	 */
	public void setNmProdVer(String nmProdVer) {
		this.nmProdVer = nmProdVer;
	}

	/**
	 * 
	 * <pre>
	 * SD 상품_버전.
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
	 * SD 상품_버전.
	 * </pre>
	 * 
	 * @param sdProdVer
	 *            String
	 */
	public void setSdProdVer(String sdProdVer) {
		this.sdProdVer = sdProdVer;
	}

	/**
	 * 
	 * <pre>
	 * HD 상품_버전.
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
	 * HD 상품_버전.
	 * </pre>
	 * 
	 * @param hdProdVer
	 *            String
	 */
	public void setHdProdVer(String hdProdVer) {
		this.hdProdVer = hdProdVer;
	}

	/**
	 * 
	 * <pre>
	 * NM 파일_크기.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getNmFileSize() {
		return this.nmFileSize;
	}

	/**
	 * 
	 * <pre>
	 * NM 파일_크기.
	 * </pre>
	 * 
	 * @param nmFileSize
	 *            String
	 */
	public void setNmFileSize(String nmFileSize) {
		this.nmFileSize = nmFileSize;
	}

	/**
	 * 
	 * <pre>
	 * SD 파일_크기.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getSdFileSize() {
		return this.sdFileSize;
	}

	/**
	 * 
	 * <pre>
	 * SD 파일_크기.
	 * </pre>
	 * 
	 * @param sdFileSize
	 *            String
	 */
	public void setSdFileSize(String sdFileSize) {
		this.sdFileSize = sdFileSize;
	}

	/**
	 * 
	 * <pre>
	 * HD 파일_크기.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getHdFileSize() {
		return this.hdFileSize;
	}

	/**
	 * 
	 * <pre>
	 * HD 파일_크기.
	 * </pre>
	 * 
	 * @param hdFileSize
	 *            String
	 */
	public void setHdFileSize(String hdFileSize) {
		this.hdFileSize = hdFileSize;
	}

	/**
	 * 
	 * <pre>
	 * NM 해상도_코드.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getNmRsltnCd() {
		return this.nmRsltnCd;
	}

	/**
	 * 
	 * <pre>
	 * NM 해상도_코드.
	 * </pre>
	 * 
	 * @param nmRsltnCd
	 *            String
	 */
	public void setNmRsltnCd(String nmRsltnCd) {
		this.nmRsltnCd = nmRsltnCd;
	}

	/**
	 * 
	 * <pre>
	 * NM 해상도_명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getNmRsltnNm() {
		return this.nmRsltnNm;
	}

	/**
	 * 
	 * <pre>
	 * NM 해상도_명.
	 * </pre>
	 * 
	 * @param nmRsltnNm
	 *            String
	 */
	public void setNmRsltnNm(String nmRsltnNm) {
		this.nmRsltnNm = nmRsltnNm;
	}

	/**
	 * 
	 * <pre>
	 * SD 해상도_코드.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getSdRsltnCd() {
		return this.sdRsltnCd;
	}

	/**
	 * 
	 * <pre>
	 * SD 해상도_코드.
	 * </pre>
	 * 
	 * @param sdRsltnCd
	 *            String
	 */
	public void setSdRsltnCd(String sdRsltnCd) {
		this.sdRsltnCd = sdRsltnCd;
	}

	/**
	 * 
	 * <pre>
	 * SD 해상도_명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getSdRsltnNm() {
		return this.sdRsltnNm;
	}

	/**
	 * 
	 * <pre>
	 * SD 해상도_명.
	 * </pre>
	 * 
	 * @param sdRsltnNm
	 *            String
	 */
	public void setSdRsltnNm(String sdRsltnNm) {
		this.sdRsltnNm = sdRsltnNm;
	}

	/**
	 * 
	 * <pre>
	 * HD 해상도_코드.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getHdRsltnCd() {
		return this.hdRsltnCd;
	}

	/**
	 * 
	 * <pre>
	 * HD 해상도_코드.
	 * </pre>
	 * 
	 * @param hdRsltnCd
	 *            String
	 */
	public void setHdRsltnCd(String hdRsltnCd) {
		this.hdRsltnCd = hdRsltnCd;
	}

	/**
	 * 
	 * <pre>
	 * HD 해상도_명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getHdRsltnNm() {
		return this.hdRsltnNm;
	}

	/**
	 * 
	 * <pre>
	 * HD 해상도_명.
	 * </pre>
	 * 
	 * @param hdRsltnNm
	 *            String
	 */
	public void setHdRsltnNm(String hdRsltnNm) {
		this.hdRsltnNm = hdRsltnNm;
	}

	/**
	 * 
	 * <pre>
	 * NM 전시_화면_비율_코드.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getNmDpPgRatioCd() {
		return this.nmDpPgRatioCd;
	}

	/**
	 * 
	 * <pre>
	 * NM 전시_화면_비율_코드.
	 * </pre>
	 * 
	 * @param nmDpPgRatioCd
	 *            String
	 */
	public void setNmDpPgRatioCd(String nmDpPgRatioCd) {
		this.nmDpPgRatioCd = nmDpPgRatioCd;
	}

	/**
	 * 
	 * <pre>
	 * NM 전시_화면_비율_명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getNmDpPgRatioNm() {
		return this.nmDpPgRatioNm;
	}

	/**
	 * 
	 * <pre>
	 * NM 전시_화면_비율_명.
	 * </pre>
	 * 
	 * @param nmDpPgRatioNm
	 *            String
	 */
	public void setNmDpPgRatioNm(String nmDpPgRatioNm) {
		this.nmDpPgRatioNm = nmDpPgRatioNm;
	}

	/**
	 * 
	 * <pre>
	 * SD 전시_화면_비율_코드.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getSdDpPgRatioCd() {
		return this.sdDpPgRatioCd;
	}

	/**
	 * 
	 * <pre>
	 * SD 전시_화면_비율_코드.
	 * </pre>
	 * 
	 * @param sdDpPgRatioCd
	 *            String
	 */
	public void setSdDpPgRatioCd(String sdDpPgRatioCd) {
		this.sdDpPgRatioCd = sdDpPgRatioCd;
	}

	/**
	 * 
	 * <pre>
	 * SD 전시_화면_비율_명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getSdDpPgRatioNm() {
		return this.sdDpPgRatioNm;
	}

	/**
	 * 
	 * <pre>
	 * SD 전시_화면_비율_명.
	 * </pre>
	 * 
	 * @param sdDpPgRatioNm
	 *            String
	 */
	public void setSdDpPgRatioNm(String sdDpPgRatioNm) {
		this.sdDpPgRatioNm = sdDpPgRatioNm;
	}

	/**
	 * 
	 * <pre>
	 * HD 전시_화면_비율_코드.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getHdDpPgRatioCd() {
		return this.hdDpPgRatioCd;
	}

	/**
	 * 
	 * <pre>
	 * HD 전시_화면_비율_코드.
	 * </pre>
	 * 
	 * @param hdDpPgRatioCd
	 *            String
	 */
	public void setHdDpPgRatioCd(String hdDpPgRatioCd) {
		this.hdDpPgRatioCd = hdDpPgRatioCd;
	}

	/**
	 * 
	 * <pre>
	 * HD 전시_화면_비율_명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getHdDpPgRatioNm() {
		return this.hdDpPgRatioNm;
	}

	/**
	 * 
	 * <pre>
	 * HD 전시_화면_비율_명.
	 * </pre>
	 * 
	 * @param hdDpPgRatioNm
	 *            String
	 */
	public void setHdDpPgRatioNm(String hdDpPgRatioNm) {
		this.hdDpPgRatioNm = hdDpPgRatioNm;
	}

	/**
	 * 
	 * <pre>
	 * NM BTV_CID.
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
	 * NM BTV_CID.
	 * </pre>
	 * 
	 * @param nmBtvCid
	 *            String
	 */
	public void setNmBtvCid(String nmBtvCid) {
		this.nmBtvCid = nmBtvCid;
	}

	/**
	 * 
	 * <pre>
	 * SD BTV_CID.
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
	 * SD BTV_CID.
	 * </pre>
	 * 
	 * @param sdBtvCid
	 *            String
	 */
	public void setSdBtvCid(String sdBtvCid) {
		this.sdBtvCid = sdBtvCid;
	}

	/**
	 * 
	 * <pre>
	 * HD BTV_CID.
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
	 * HD BTV_CID.
	 * </pre>
	 * 
	 * @param hdBtvCid
	 *            String
	 */
	public void setHdBtvCid(String hdBtvCid) {
		this.hdBtvCid = hdBtvCid;
	}

	/**
	 * 
	 * <pre>
	 * 상품_금액.
	 * </pre>
	 * 
	 * @return Integer
	 */
	public Integer getProdAmt() {
		return this.prodAmt;
	}

	/**
	 * 
	 * <pre>
	 * 상품_금액.
	 * </pre>
	 * 
	 * @param prodAmt
	 *            Integer
	 */
	public void setProdAmt(Integer prodAmt) {
		this.prodAmt = prodAmt;
	}

	/**
	 * 
	 * <pre>
	 * 상품_정찰_금액.
	 * </pre>
	 * 
	 * @return Integer
	 */
	public Integer getProdNetAmt() {
		return this.prodNetAmt;
	}

	/**
	 * 
	 * <pre>
	 * 상품_정찰_금액.
	 * </pre>
	 * 
	 * @param prodNetAmt
	 *            Integer
	 */
	public void setProdNetAmt(Integer prodNetAmt) {
		this.prodNetAmt = prodNetAmt;
	}

	/**
	 * 
	 * <pre>
	 * DRM_여부.
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
	 * DRM_여부.
	 * </pre>
	 * 
	 * @param drmYn
	 *            String
	 */
	public void setDrmYn(String drmYn) {
		this.drmYn = drmYn;
	}

	/**
	 * 
	 * <pre>
	 * 상품 상태 코드.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getProdStatusCd() {
		return this.prodStatusCd;
	}

	/**
	 * 
	 * <pre>
	 * 상품 상태 코드.
	 * </pre>
	 * 
	 * @param prodStatusCd
	 *            String
	 */
	public void setProdStatusCd(String prodStatusCd) {
		this.prodStatusCd = prodStatusCd;
	}

	/**
	 * <pre>
	 * BTV_여부.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getBtvYn() {
		return this.btvYn;
	}

	/**
	 * <pre>
	 * BTV_여부.
	 * </pre>
	 * 
	 * @param btvYn
	 *            String
	 */
	public void setBtvYn(String btvYn) {
		this.btvYn = btvYn;
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
	 *            String
	 */
	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}

	/**
	 * <pre>
	 * 챕터.
	 * </pre>
	 * 
	 * @return Integer
	 */
	public Integer getChapter() {
		return this.chapter;
	}

	/**
	 * <pre>
	 * 챕터.
	 * </pre>
	 * 
	 * @param chapter
	 *            Integer
	 */
	public void setChapter(Integer chapter) {
		this.chapter = chapter;
	}

	/**
	 * <pre>
	 * 챕터 단위.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getChapterUnit() {
		return this.chapterUnit;
	}

	/**
	 * <pre>
	 * 챕터 단위.
	 * </pre>
	 * 
	 * @param chapterUnit
	 *            String
	 */
	public void setChapterUnit(String chapterUnit) {
		this.chapterUnit = chapterUnit;
	}

	/**
	 * <pre>
	 * 발매_일.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getIssueDay() {
		return this.issueDay;
	}

	/**
	 * <pre>
	 * 발매_일.
	 * </pre>
	 * 
	 * @param issueDay
	 *            String
	 */
	public void setIssueDay(String issueDay) {
		this.issueDay = issueDay;
	}

	/**
	 * <pre>
	 * 상품_기본_설명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getProdBaseDesc() {
		return this.prodBaseDesc;
	}

	/**
	 * <pre>
	 * 상품_기본_설명.
	 * </pre>
	 * 
	 * @param prodBaseDesc
	 *            String
	 */
	public void setProdBaseDesc(String prodBaseDesc) {
		this.prodBaseDesc = prodBaseDesc;
	}

	/**
	 * <pre>
	 * 사용_기간_단위_코드(PD003).
	 * </pre>
	 * 
	 * @return String
	 */
	public String getUsePeriodUnitCd() {
		return this.usePeriodUnitCd;
	}

	/**
	 * <pre>
	 * 사용_기간_단위_코드(PD003).
	 * </pre>
	 * 
	 * @param usePeriodUnitCd
	 *            String
	 */
	public void setUsePeriodUnitCd(String usePeriodUnitCd) {
		this.usePeriodUnitCd = usePeriodUnitCd;
	}

	/**
	 * <pre>
	 * 사용_기간_단위_명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getUsePeriodUnitNm() {
		return this.usePeriodUnitNm;
	}

	/**
	 * <pre>
	 * 사용_기간_단위_명.
	 * </pre>
	 * 
	 * @param usePeriodUnitNm
	 *            String
	 */
	public void setUsePeriodUnitNm(String usePeriodUnitNm) {
		this.usePeriodUnitNm = usePeriodUnitNm;
	}

	/**
	 * <pre>
	 * 사용_기간.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getUsePeriod() {
		return this.usePeriod;
	}

	/**
	 * <pre>
	 * 사용_기간.
	 * </pre>
	 * 
	 * @param usePeriod
	 *            String
	 */
	public void setUsePeriod(String usePeriod) {
		this.usePeriod = usePeriod;
	}

	/**
	 * <pre>
	 * 샘플_URL.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getSamplUrl() {
		return this.samplUrl;
	}

	/**
	 * <pre>
	 * 샘플_URL.
	 * </pre>
	 * 
	 * @param samplUrl
	 *            String
	 */
	public void setSamplUrl(String samplUrl) {
		this.samplUrl = samplUrl;
	}

	/**
	 * 
	 * <pre>
	 * 샵클_샘플_URL.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getScSamplUrl() {
		return this.scSamplUrl;
	}

	/**
	 * 
	 * <pre>
	 * 샵클_샘플_URL.
	 * </pre>
	 * 
	 * @param scSamplUrl
	 *            String
	 */
	public void setScSamplUrl(String scSamplUrl) {
		this.scSamplUrl = scSamplUrl;
	}

	/**
	 * 
	 * <pre>
	 * 에피소드_재생_시간.
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
	 * 에피소드_재생_시간.
	 * </pre>
	 * 
	 * @param epsdPlayTm
	 *            String
	 */
	public void setEpsdPlayTm(String epsdPlayTm) {
		this.epsdPlayTm = epsdPlayTm;
	}

	/**
	 * 
	 * <pre>
	 * 상품_등급_코드(PD0044).
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
	 * 상품_등급_코드(PD0044).
	 * </pre>
	 * 
	 * @param prodGrdCd
	 *            String
	 */
	public void setProdGrdCd(String prodGrdCd) {
		this.prodGrdCd = prodGrdCd;
	}

	/**
	 * 
	 * <pre>
	 * HDV_여부.
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
	 * HDV_여부.
	 * </pre>
	 * 
	 * @param hdvYn
	 *            String
	 */
	public void setHdvYn(String hdvYn) {
		this.hdvYn = hdvYn;
	}

	/**
	 * 
	 * <pre>
	 * 아티스트1_명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getArtist1Nm() {
		return this.artist1Nm;
	}

	/**
	 * 
	 * <pre>
	 * 아티스트1_명.
	 * </pre>
	 * 
	 * @param artist1Nm
	 *            String
	 */
	public void setArtist1Nm(String artist1Nm) {
		this.artist1Nm = artist1Nm;
	}

	/**
	 * 
	 * <pre>
	 * 아티스트2_명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getArtist2Nm() {
		return this.artist2Nm;
	}

	/**
	 * 
	 * <pre>
	 * 아티스트2_명.
	 * </pre>
	 * 
	 * @param artist2Nm
	 *            String
	 */
	public void setArtist2Nm(String artist2Nm) {
		this.artist2Nm = artist2Nm;
	}

	/**
	 * 
	 * <pre>
	 * 방송_회사_코드(DP0046).
	 * </pre>
	 * 
	 * @return String
	 */
	public String getBrdcCompCd() {
		return this.brdcCompCd;
	}

	/**
	 * 
	 * <pre>
	 * 방송_회사_코드(DP0046).
	 * </pre>
	 * 
	 * @param brdcCompCd
	 *            String
	 */
	public void setBrdcCompCd(String brdcCompCd) {
		this.brdcCompCd = brdcCompCd;
	}

	/**
	 * 
	 * <pre>
	 * 방송_회사_명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getBrdcCompNm() {
		return this.brdcCompNm;
	}

	/**
	 * 
	 * <pre>
	 * 방송_회사_명.
	 * </pre>
	 * 
	 * @param brdcCompNm
	 *            String
	 */
	public void setBrdcCompNm(String brdcCompNm) {
		this.brdcCompNm = brdcCompNm;
	}

	/**
	 * 
	 * <pre>
	 * 채널_회사_명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getChnlCompNm() {
		return this.chnlCompNm;
	}

	/**
	 * 
	 * <pre>
	 * 채널_회사_명.
	 * </pre>
	 * 
	 * @param chnlCompNm
	 *            String
	 */
	public void setChnlCompNm(String chnlCompNm) {
		this.chnlCompNm = chnlCompNm;
	}

	/**
	 * 
	 * <pre>
	 * AGENCY_명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getAgencyNm() {
		return this.agencyNm;
	}

	/**
	 * 
	 * <pre>
	 * AGENCY_명.
	 * </pre>
	 * 
	 * @param agencyNm
	 *            String
	 */
	public void setAgencyNm(String agencyNm) {
		this.agencyNm = agencyNm;
	}

	/**
	 * 
	 * <pre>
	 * 등록_일시.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getRegDt() {
		return this.regDt;
	}

	/**
	 * 
	 * <pre>
	 * 등록_일시.
	 * </pre>
	 * 
	 * @param regDt
	 *            String
	 */
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

}
