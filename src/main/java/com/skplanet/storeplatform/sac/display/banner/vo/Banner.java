/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.banner.vo;

/**
 * 배너 조회 Default Value Object.
 * 
 * Updated on : 2014. 01. 23. Updated by : 유시혁.
 */
public class Banner {

	private int totalCount;
	private int rnum;
	private String bnrSeq;// 배너_SEQ
	private String imgCaseCd;// 이미지_유형_코드
	private String bnrTypeCd;// 베너_타입_코드
	private String bnrNm;// 배너_명
	private String bnrInfo;// 배너_정보
	private String prodId;// 상품_ID
	private String imgPath;// 이미지_경로
	private String imgNm;// 이미지_명
	private String imgTypeCd;// 이미지_타입_코드
	private String regDt;// 등록_일시
	private String menuId;// 메뉴_ID
	private String menuNm;// 메뉴_명
	private String topMenuId;// TOP_메뉴_ID
	private String topMenuNm;// TOP_메뉴_명
	private String metaClsfCd;// 메타_구분_코드
	private String scSamplUrl;// 샵클_샘플_URL
	private String samplUrl;// 샘플_URL
	private String prodCase;// 상품_유형
	private String outsdContentsId;
	private int fileSizeH;// 192Kbps 사이즈
	private int fileSize;// 128Kbps 사이즈
	private String bnrTitle;// 배너 제목

	private String bnrType;
	private String type;
	private String descName;
	private String external;
	private String base;

	public int getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public String getBnrSeq() {
		return this.bnrSeq;
	}

	public void setBnrSeq(String bnrSeq) {
		this.bnrSeq = bnrSeq;
	}

	public String getImgCaseCd() {
		return this.imgCaseCd;
	}

	public void setImgCaseCd(String imgCaseCd) {
		this.imgCaseCd = imgCaseCd;
	}

	public String getBnrTypeCd() {
		return this.bnrTypeCd;
	}

	public void setBnrTypeCd(String bnrTypeCd) {
		this.bnrTypeCd = bnrTypeCd;
	}

	public String getBnrNm() {
		return this.bnrNm;
	}

	public void setBnrNm(String bnrNm) {
		this.bnrNm = bnrNm;
	}

	public String getImgPath() {
		return this.imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getImgNm() {
		return this.imgNm;
	}

	public void setImgNm(String imgNm) {
		this.imgNm = imgNm;
	}

	public String getImgTypeCd() {
		return this.imgTypeCd;
	}

	public void setImgTypeCd(String imgTypeCd) {
		this.imgTypeCd = imgTypeCd;
	}

	public String getRegDt() {
		return this.regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getTopMenuId() {
		return this.topMenuId;
	}

	public void setTopMenuId(String topMenuId) {
		this.topMenuId = topMenuId;
	}

	public String getMetaClsfCd() {
		return this.metaClsfCd;
	}

	public void setMetaClsfCd(String metaClsfCd) {
		this.metaClsfCd = metaClsfCd;
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

	public String getProdCase() {
		return this.prodCase;
	}

	public void setProdCase(String prodCase) {
		this.prodCase = prodCase;
	}

	public String getBnrInfo() {
		return this.bnrInfo;
	}

	public void setBnrInfo(String bnrInfo) {
		this.bnrInfo = bnrInfo;
	}

	public String getProdId() {
		return this.prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getMenuNm() {
		return this.menuNm;
	}

	public void setMenuNm(String menuNm) {
		this.menuNm = menuNm;
	}

	public String getTopMenuNm() {
		return this.topMenuNm;
	}

	public void setTopMenuNm(String topMenuNm) {
		this.topMenuNm = topMenuNm;
	}

	public int getRnum() {
		return this.rnum;
	}

	public void setRnum(int rnum) {
		this.rnum = rnum;
	}

	public String getOutsdContentsId() {
		return this.outsdContentsId;
	}

	public void setOutsdContentsId(String outsdContentsId) {
		this.outsdContentsId = outsdContentsId;
	}

	public int getFileSizeH() {
		return this.fileSizeH;
	}

	public void setFileSizeH(int fileSizeH) {
		this.fileSizeH = fileSizeH;
	}

	public int getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	public String getBnrType() {
		return this.bnrType;
	}

	public void setBnrType(String bnrType) {
		this.bnrType = bnrType;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescName() {
		return this.descName;
	}

	public void setDescName(String descName) {
		this.descName = descName;
	}

	public String getExternal() {
		return this.external;
	}

	public void setExternal(String external) {
		this.external = external;
	}

	public String getBase() {
		return this.base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getBnrTitle() {
		return this.bnrTitle;
	}

	public void setBnrTitle(String bnrTitle) {
		this.bnrTitle = bnrTitle;
	}

}
