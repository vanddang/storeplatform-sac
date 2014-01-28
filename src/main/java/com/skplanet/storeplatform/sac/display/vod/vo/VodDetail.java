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
	/** */
	private String menuId;
	/** */
	private String menuNm;
	/** */
	private String menuDesc;
	/** */
	private String metaClsfCd;
	/** */
	private String prodId;
	/** */
	private String vodTitlNm;
	/** 상품명 */
	private String prodNm;
	/** 상품 기본 설명 */
	private String prodBaseDesc;
	/** 상품 상세 설명 */
	private String prodDtlDesc;
	/** */
	private String prodGrdCd;
	/** */
	private String artist1Nm;
	/** */
	private String artist2Nm;
	/** */
	private String issueDay;
	/** */
	private String chnlCompNm;
	/** */
	private String agencyNm;
	/** HDV여부 */
	private String hdvYn;
	/** 돌비 지원여부 */
	private String dolbySprtYn;
	/** */
	private Integer prodAmt;
	/** 참여자수 */
	private Integer paticpersCnt;
	/** 구매수 */
	private Integer prchsCnt;
	/** 평균 평가 점수 */
	private Double avgEvluScore;

	/** thumbnail filePath */
	private String imgPath;
	/** thumbnail fileName */
	private String imgNm;
	/** thumbnail fileSize */
	private Integer imgSize;

	/** DRM 여부 */
	private String drmYn;
	/** 상품 소개 내용 */
	private String prodIntrDscr;
	/** 등록일시 */
	private Date regDt;
	/** 서비스시작 일시 */
	private Date svcStartDt;
	/** 방송사 코드 */
	private String brdcCompCd;
	/** 방송사명 */
	private String brdcCompCdNm;
	/** 장르코드 */
	private String genreCd;
	/** 재생시간 */
	private String playTm;

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

	public String getVodTitlNm() {
		return this.vodTitlNm;
	}

	public void setVodTitlNm(String vodTitlNm) {
		this.vodTitlNm = vodTitlNm;
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

	public String getAgencyNm() {
		return this.agencyNm;
	}

	public void setAgencyNm(String agencyNm) {
		this.agencyNm = agencyNm;
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

	public Integer getProdAmt() {
		return this.prodAmt;
	}

	public void setProdAmt(Integer prodAmt) {
		this.prodAmt = prodAmt;
	}

	/**
	 * 참여자수
	 * @return
	 * 		참여자수
	 */
	public Integer getPaticpersCnt() {
		return this.paticpersCnt;
	}

	/**
	 * 참여자수
	 * @param paticpersCnt
	 * 			참여자수
	 */
	public void setPaticpersCnt(Integer paticpersCnt) {
		this.paticpersCnt = paticpersCnt;
	}

	/**
	 * 구매수
	 * @return
	 * 		구매수
	 */
	public Integer getPrchsCnt() {
		return this.prchsCnt;
	}

	/**
	 * 구매수
	 * @param prchsCnt
	 * 			구매수
	 */
	public void setPrchsCnt(Integer prchsCnt) {
		this.prchsCnt = prchsCnt;
	}

	/**
	 * 평균 평가 점수
	 * @return
	 * 		평균 평가 점수
	 */
	public Double getAvgEvluScore() {
		return this.avgEvluScore;
	}

	/**
	 * 평균 평가 점수
	 * @param avgEvluScore
	 * 			평균 평가 점수
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

	public String getDrmYn() {
		return this.drmYn;
	}

	public void setDrmYn(String drmYn) {
		this.drmYn = drmYn;
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
	 * @return
	 * 		등록 일시
	 */
	public Date getRegDt() {
		return this.regDt;
	}

	/**
	 * 등록 일시
	 * @param regDt
	 * 		등록 일시
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
	 * @param prodIntrDscr
	 * 		상품 소개 내용
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

	public String getPlayTm() {
		return this.playTm;
	}

	public void setPlayTm(String playTm) {
		this.playTm = playTm;
	}




}
