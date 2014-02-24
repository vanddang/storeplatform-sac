/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.shopping.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * <pre>
 * 쇼핑 Value Object.
 * </pre>
 * 
 * Created on : 2014-01-02 Created by : 김형식, SK플래닛 Last Updated on : 2014-01-02 Last Updated by : 김형식, SK플래닛
 */
public class Shopping extends CommonInfo {

	private static final long serialVersionUID = 1L;
	private int totalCount;
	private String catalogId;
	private String catalogName;
	private Integer prodNetAmt;
	private Integer prodAmt;
	private Double dcRate;
	private Integer dcAmt;
	private String dlvProdYn;
	private Integer dwldQty;
	private Integer prchsQty;
	private String expoOrd;

	private String brandId;
	private String brandNm;
	private String menuId;
	private String menuName;
	private String upMenuId;
	private String upMenuName;
	private String no;
	private String newYn;
	private String regDt;
	private String tenentId;
	private String prodId;
	private String partProdId;
	private String filePos;
	private String prodGrdCd;
	private String applyStartDt;
	private String applyEndDt;
	private String prodCaseCd;
	private String themeId;
	private String themeName;
	private String linkUrl;

	private String planId;
	private String planName;
	private String subTitleName;
	private String planStartDt;
	private String planEndDt;
	private String przwnerAnnoDt;
	private String planGiftName;

	private String sysDate;
	private String specialSale;
	private String sellerMbrNo;
	private String soldOut;
	private String usePeriod;
	private String allow;
	private String b2bProdYn;
	private String mthMaxCnt; /* 월_최대_판매_수량 */
	private String dlyMaxCnt; // 일_최대_판매_수량
	private String mthUsrMaxCnt; // 월_회원_최대_구매_수량
	private String dlyUsrMaxCnt; // 일_회원_최대_구매_수량
	private String eachMaxCnt; // 1차_최대_구매_수량

	private String usePlac; // 사용_장소
	private String useLimtDesc; // 사용_제한_설명
	private String noticeMatt; // 공지_사항
	private String prchsCancelDrbkReason; // 구매_취소_환불_사유

	private String opt1Nm; // 옵션 기준점
	private String optPdNm; // 옵션 값
	private String subYn; // 옵션 하위 여부

	private String filePos1; // 프로모션 이미지

	/**
	 * @return the totalCount
	 */
	public int getTotalCount() {
		return this.totalCount;
	}

	/**
	 * @param totalCount
	 *            the totalCount to set
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @return the catalogId
	 */
	public String getCatalogId() {
		return this.catalogId;
	}

	/**
	 * @param catalogId
	 *            the catalogId to set
	 */
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	/**
	 * @return the catalogName
	 */
	public String getCatalogName() {
		return this.catalogName;
	}

	/**
	 * @param catalogName
	 *            the catalogName to set
	 */
	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	/**
	 * @return the prodNetAmt
	 */
	public Integer getProdNetAmt() {
		return this.prodNetAmt;
	}

	/**
	 * @param prodNetAmt
	 *            the prodNetAmt to set
	 */
	public void setProdNetAmt(Integer prodNetAmt) {
		this.prodNetAmt = prodNetAmt;
	}

	/**
	 * @return the prodAmt
	 */
	public Integer getProdAmt() {
		return this.prodAmt;
	}

	/**
	 * @param prodAmt
	 *            the prodAmt to set
	 */
	public void setProdAmt(Integer prodAmt) {
		this.prodAmt = prodAmt;
	}

	/**
	 * @return the dcRate
	 */
	public Double getDcRate() {
		return this.dcRate;
	}

	/**
	 * @param dcRate
	 *            the dcRate to set
	 */
	public void setDcRate(Double dcRate) {
		this.dcRate = dcRate;
	}

	/**
	 * @return the dcAmt
	 */
	public Integer getDcAmt() {
		return this.dcAmt;
	}

	/**
	 * @param dcAmt
	 *            the dcAmt to set
	 */
	public void setDcAmt(Integer dcAmt) {
		this.dcAmt = dcAmt;
	}

	/**
	 * @return the dlvProdYn
	 */
	public String getDlvProdYn() {
		return this.dlvProdYn;
	}

	/**
	 * @param dlvProdYn
	 *            the dlvProdYn to set
	 */
	public void setDlvProdYn(String dlvProdYn) {
		this.dlvProdYn = dlvProdYn;
	}

	/**
	 * @return the dwldQty
	 */
	public Integer getDwldQty() {
		return this.dwldQty;
	}

	/**
	 * @param dwldQty
	 *            the dwldQty to set
	 */
	public void setDwldQty(Integer dwldQty) {
		this.dwldQty = dwldQty;
	}

	/**
	 * @return the prchsQty
	 */
	public Integer getPrchsQty() {
		return this.prchsQty;
	}

	/**
	 * @param prchsQty
	 *            the prchsQty to set
	 */
	public void setPrchsQty(Integer prchsQty) {
		this.prchsQty = prchsQty;
	}

	/**
	 * @return the expoOrd
	 */
	public String getExpoOrd() {
		return this.expoOrd;
	}

	/**
	 * @param expoOrd
	 *            the expoOrd to set
	 */
	public void setExpoOrd(String expoOrd) {
		this.expoOrd = expoOrd;
	}

	/**
	 * @return the brandId
	 */
	public String getBrandId() {
		return this.brandId;
	}

	/**
	 * @param brandId
	 *            the brandId to set
	 */
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	/**
	 * @return the brandNm
	 */
	public String getBrandNm() {
		return this.brandNm;
	}

	/**
	 * @param brandNm
	 *            the brandNm to set
	 */
	public void setBrandNm(String brandNm) {
		this.brandNm = brandNm;
	}

	/**
	 * @return the menuId
	 */
	public String getMenuId() {
		return this.menuId;
	}

	/**
	 * @param menuId
	 *            the menuId to set
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/**
	 * @return the menuName
	 */
	public String getMenuName() {
		return this.menuName;
	}

	/**
	 * @param menuName
	 *            the menuName to set
	 */
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	/**
	 * @return the upMenuId
	 */
	public String getUpMenuId() {
		return this.upMenuId;
	}

	/**
	 * @param upMenuId
	 *            the upMenuId to set
	 */
	public void setUpMenuId(String upMenuId) {
		this.upMenuId = upMenuId;
	}

	/**
	 * @return the upMenuName
	 */
	public String getUpMenuName() {
		return this.upMenuName;
	}

	/**
	 * @param upMenuName
	 *            the upMenuName to set
	 */
	public void setUpMenuName(String upMenuName) {
		this.upMenuName = upMenuName;
	}

	/**
	 * @return the no
	 */
	public String getNo() {
		return this.no;
	}

	/**
	 * @param no
	 *            the no to set
	 */
	public void setNo(String no) {
		this.no = no;
	}

	/**
	 * @return the newYn
	 */
	public String getNewYn() {
		return this.newYn;
	}

	/**
	 * @param newYn
	 *            the newYn to set
	 */
	public void setNewYn(String newYn) {
		this.newYn = newYn;
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
	 * @return the tenentId
	 */
	public String getTenentId() {
		return this.tenentId;
	}

	/**
	 * @param tenentId
	 *            the tenentId to set
	 */
	public void setTenentId(String tenentId) {
		this.tenentId = tenentId;
	}

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
	 * @return the partProdId
	 */
	public String getPartProdId() {
		return this.partProdId;
	}

	/**
	 * @param partProdId
	 *            the partProdId to set
	 */
	public void setPartProdId(String partProdId) {
		this.partProdId = partProdId;
	}

	/**
	 * @return the filePos
	 */
	public String getFilePos() {
		return this.filePos;
	}

	/**
	 * @param filePos
	 *            the filePos to set
	 */
	public void setFilePos(String filePos) {
		this.filePos = filePos;
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
	 * @return the applyStartDt
	 */
	public String getApplyStartDt() {
		return this.applyStartDt;
	}

	/**
	 * @param applyStartDt
	 *            the applyStartDt to set
	 */
	public void setApplyStartDt(String applyStartDt) {
		this.applyStartDt = applyStartDt;
	}

	/**
	 * @return the applyEndDt
	 */
	public String getApplyEndDt() {
		return this.applyEndDt;
	}

	/**
	 * @param applyEndDt
	 *            the applyEndDt to set
	 */
	public void setApplyEndDt(String applyEndDt) {
		this.applyEndDt = applyEndDt;
	}

	/**
	 * @return the prodCaseCd
	 */
	public String getProdCaseCd() {
		return this.prodCaseCd;
	}

	/**
	 * @param prodCaseCd
	 *            the prodCaseCd to set
	 */
	public void setProdCaseCd(String prodCaseCd) {
		this.prodCaseCd = prodCaseCd;
	}

	/**
	 * @return the themeId
	 */
	public String getThemeId() {
		return this.themeId;
	}

	/**
	 * @param themeId
	 *            the themeId to set
	 */
	public void setThemeId(String themeId) {
		this.themeId = themeId;
	}

	/**
	 * @return the themeName
	 */
	public String getThemeName() {
		return this.themeName;
	}

	/**
	 * @param themeName
	 *            the themeName to set
	 */
	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}

	/**
	 * @return the linkUrl
	 */
	public String getLinkUrl() {
		return this.linkUrl;
	}

	/**
	 * @param linkUrl
	 *            the linkUrl to set
	 */
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	/**
	 * @return the planId
	 */
	public String getPlanId() {
		return this.planId;
	}

	/**
	 * @param planId
	 *            the planId to set
	 */
	public void setPlanId(String planId) {
		this.planId = planId;
	}

	/**
	 * @return the planName
	 */
	public String getPlanName() {
		return this.planName;
	}

	/**
	 * @param planName
	 *            the planName to set
	 */
	public void setPlanName(String planName) {
		this.planName = planName;
	}

	/**
	 * @return the subTitleName
	 */
	public String getSubTitleName() {
		return this.subTitleName;
	}

	/**
	 * @param subTitleName
	 *            the subTitleName to set
	 */
	public void setSubTitleName(String subTitleName) {
		this.subTitleName = subTitleName;
	}

	/**
	 * @return the planStartDt
	 */
	public String getPlanStartDt() {
		return this.planStartDt;
	}

	/**
	 * @param planStartDt
	 *            the planStartDt to set
	 */
	public void setPlanStartDt(String planStartDt) {
		this.planStartDt = planStartDt;
	}

	/**
	 * @return the planEndDt
	 */
	public String getPlanEndDt() {
		return this.planEndDt;
	}

	/**
	 * @param planEndDt
	 *            the planEndDt to set
	 */
	public void setPlanEndDt(String planEndDt) {
		this.planEndDt = planEndDt;
	}

	/**
	 * @return the przwnerAnnoDt
	 */
	public String getPrzwnerAnnoDt() {
		return this.przwnerAnnoDt;
	}

	/**
	 * @param przwnerAnnoDt
	 *            the przwnerAnnoDt to set
	 */
	public void setPrzwnerAnnoDt(String przwnerAnnoDt) {
		this.przwnerAnnoDt = przwnerAnnoDt;
	}

	/**
	 * @return the planGiftName
	 */
	public String getPlanGiftName() {
		return this.planGiftName;
	}

	/**
	 * @param planGiftName
	 *            the planGiftName to set
	 */
	public void setPlanGiftName(String planGiftName) {
		this.planGiftName = planGiftName;
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
	 * @return the specialSale
	 */
	public String getSpecialSale() {
		return this.specialSale;
	}

	/**
	 * @param specialSale
	 *            the specialSale to set
	 */
	public void setSpecialSale(String specialSale) {
		this.specialSale = specialSale;
	}

	/**
	 * @return the sellerMbrNo
	 */
	public String getSellerMbrNo() {
		return this.sellerMbrNo;
	}

	/**
	 * @param sellerMbrNo
	 *            the sellerMbrNo to set
	 */
	public void setSellerMbrNo(String sellerMbrNo) {
		this.sellerMbrNo = sellerMbrNo;
	}

	/**
	 * @return the soldOut
	 */
	public String getSoldOut() {
		return this.soldOut;
	}

	/**
	 * @param soldOut
	 *            the soldOut to set
	 */
	public void setSoldOut(String soldOut) {
		this.soldOut = soldOut;
	}

	/**
	 * @return the usePeriod
	 */
	public String getUsePeriod() {
		return this.usePeriod;
	}

	/**
	 * @param usePeriod
	 *            the usePeriod to set
	 */
	public void setUsePeriod(String usePeriod) {
		this.usePeriod = usePeriod;
	}

	/**
	 * @return the allow
	 */
	public String getAllow() {
		return this.allow;
	}

	/**
	 * @param allow
	 *            the allow to set
	 */
	public void setAllow(String allow) {
		this.allow = allow;
	}

	/**
	 * @return the b2bProdYn
	 */
	public String getB2bProdYn() {
		return this.b2bProdYn;
	}

	/**
	 * @param b2bProdYn
	 *            the b2bProdYn to set
	 */
	public void setB2bProdYn(String b2bProdYn) {
		this.b2bProdYn = b2bProdYn;
	}

	/**
	 * @return the mthMaxCnt
	 */
	public String getMthMaxCnt() {
		return this.mthMaxCnt;
	}

	/**
	 * @param mthMaxCnt
	 *            the mthMaxCnt to set
	 */
	public void setMthMaxCnt(String mthMaxCnt) {
		this.mthMaxCnt = mthMaxCnt;
	}

	/**
	 * @return the dlyMaxCnt
	 */
	public String getDlyMaxCnt() {
		return this.dlyMaxCnt;
	}

	/**
	 * @param dlyMaxCnt
	 *            the dlyMaxCnt to set
	 */
	public void setDlyMaxCnt(String dlyMaxCnt) {
		this.dlyMaxCnt = dlyMaxCnt;
	}

	/**
	 * @return the mthUsrMaxCnt
	 */
	public String getMthUsrMaxCnt() {
		return this.mthUsrMaxCnt;
	}

	/**
	 * @param mthUsrMaxCnt
	 *            the mthUsrMaxCnt to set
	 */
	public void setMthUsrMaxCnt(String mthUsrMaxCnt) {
		this.mthUsrMaxCnt = mthUsrMaxCnt;
	}

	/**
	 * @return the dlyUsrMaxCnt
	 */
	public String getDlyUsrMaxCnt() {
		return this.dlyUsrMaxCnt;
	}

	/**
	 * @param dlyUsrMaxCnt
	 *            the dlyUsrMaxCnt to set
	 */
	public void setDlyUsrMaxCnt(String dlyUsrMaxCnt) {
		this.dlyUsrMaxCnt = dlyUsrMaxCnt;
	}

	/**
	 * @return the eachMaxCnt
	 */
	public String getEachMaxCnt() {
		return this.eachMaxCnt;
	}

	/**
	 * @param eachMaxCnt
	 *            the eachMaxCnt to set
	 */
	public void setEachMaxCnt(String eachMaxCnt) {
		this.eachMaxCnt = eachMaxCnt;
	}

	/**
	 * @return the usePlac
	 */
	public String getUsePlac() {
		return this.usePlac;
	}

	/**
	 * @param usePlac
	 *            the usePlac to set
	 */
	public void setUsePlac(String usePlac) {
		this.usePlac = usePlac;
	}

	/**
	 * @return the useLimtDesc
	 */
	public String getUseLimtDesc() {
		return this.useLimtDesc;
	}

	/**
	 * @param useLimtDesc
	 *            the useLimtDesc to set
	 */
	public void setUseLimtDesc(String useLimtDesc) {
		this.useLimtDesc = useLimtDesc;
	}

	/**
	 * @return the noticeMatt
	 */
	public String getNoticeMatt() {
		return this.noticeMatt;
	}

	/**
	 * @param noticeMatt
	 *            the noticeMatt to set
	 */
	public void setNoticeMatt(String noticeMatt) {
		this.noticeMatt = noticeMatt;
	}

	/**
	 * @return the prchsCancelDrbkReason
	 */
	public String getPrchsCancelDrbkReason() {
		return this.prchsCancelDrbkReason;
	}

	/**
	 * @param prchsCancelDrbkReason
	 *            the prchsCancelDrbkReason to set
	 */
	public void setPrchsCancelDrbkReason(String prchsCancelDrbkReason) {
		this.prchsCancelDrbkReason = prchsCancelDrbkReason;
	}

	/**
	 * @return the opt1Nm
	 */
	public String getOpt1Nm() {
		return this.opt1Nm;
	}

	/**
	 * @param opt1Nm
	 *            the opt1Nm to set
	 */
	public void setOpt1Nm(String opt1Nm) {
		this.opt1Nm = opt1Nm;
	}

	/**
	 * @return the optPdNm
	 */
	public String getOptPdNm() {
		return this.optPdNm;
	}

	/**
	 * @param optPdNm
	 *            the optPdNm to set
	 */
	public void setOptPdNm(String optPdNm) {
		this.optPdNm = optPdNm;
	}

	/**
	 * @return the subYn
	 */
	public String getSubYn() {
		return this.subYn;
	}

	/**
	 * @param subYn
	 *            the subYn to set
	 */
	public void setSubYn(String subYn) {
		this.subYn = subYn;
	}

	/**
	 * @return the filePos1
	 */
	public String getFilePos1() {
		return this.filePos1;
	}

	/**
	 * @param filePos1
	 *            the filePos1 to set
	 */
	public void setFilePos1(String filePos1) {
		this.filePos1 = filePos1;
	}

}
