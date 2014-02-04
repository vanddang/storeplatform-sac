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
 * 쇼핑 Value Object
 * </pre>
 * 
 * Created on : 2014-01-02 Created by : 김형식, SK플래닛 Last Updated on : 2014-01-02 Last Updated by : 김형식, SK플래닛
 */
public class Shopping extends CommonInfo {

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
	private String brandName;
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
	private String PlanName;
	private String subTitleName;
	private String planStartDt;
	private String planEndDt;
	private String przwnerAnnoDt;
	private String planGiftName;

	private String specialSale;
	private String soldOut;

	public String getUpMenuId() {
		return this.upMenuId;
	}

	public void setUpMenuId(String upMenuId) {
		this.upMenuId = upMenuId;
	}

	public String getUpMenuName() {
		return this.upMenuName;
	}

	public void setUpMenuName(String upMenuName) {
		this.upMenuName = upMenuName;
	}

	public int getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public String getCatalogId() {
		return this.catalogId;
	}

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

	public Integer getProdNetAmt() {
		return this.prodNetAmt;
	}

	public void setProdNetAmt(Integer prodNetAmt) {
		this.prodNetAmt = prodNetAmt;
	}

	public Integer getProdAmt() {
		return this.prodAmt;
	}

	public void setProdAmt(Integer prodAmt) {
		this.prodAmt = prodAmt;
	}

	public Double getDcRate() {
		return this.dcRate;
	}

	public void setDcRate(Double dcRate) {
		this.dcRate = dcRate;
	}

	public Integer getDcAmt() {
		return this.dcAmt;
	}

	public void setDcAmt(Integer dcAmt) {
		this.dcAmt = dcAmt;
	}

	public String getDlvProdYn() {
		return this.dlvProdYn;
	}

	public void setDlvProdYn(String dlvProdYn) {
		this.dlvProdYn = dlvProdYn;
	}

	public Integer getDwldQty() {
		return this.dwldQty;
	}

	public void setDwldQty(Integer dwldQty) {
		this.dwldQty = dwldQty;
	}

	public Integer getPrchsQty() {
		return this.prchsQty;
	}

	public void setPrchsQty(Integer prchsQty) {
		this.prchsQty = prchsQty;
	}

	public String getExpoOrd() {
		return this.expoOrd;
	}

	public void setExpoOrd(String expoOrd) {
		this.expoOrd = expoOrd;
	}

	public String getBrandId() {
		return this.brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return this.brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getNo() {
		return this.no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getNewYn() {
		return this.newYn;
	}

	public void setNewYn(String newYn) {
		this.newYn = newYn;
	}

	public String getRegDt() {
		return this.regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	public String getTenentId() {
		return this.tenentId;
	}

	public void setTenentId(String tenentId) {
		this.tenentId = tenentId;
	}

	public String getProdId() {
		return this.prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getPartProdId() {
		return this.partProdId;
	}

	public void setPartProdId(String partProdId) {
		this.partProdId = partProdId;
	}

	public String getFilePos() {
		return this.filePos;
	}

	public void setFilePos(String filePos) {
		this.filePos = filePos;
	}

	public String getProdGrdCd() {
		return this.prodGrdCd;
	}

	public void setProdGrdCd(String prodGrdCd) {
		this.prodGrdCd = prodGrdCd;
	}

	public String getApplyStartDt() {
		return this.applyStartDt;
	}

	public void setApplyStartDt(String applyStartDt) {
		this.applyStartDt = applyStartDt;
	}

	public String getApplyEndDt() {
		return this.applyEndDt;
	}

	public void setApplyEndDt(String applyEndDt) {
		this.applyEndDt = applyEndDt;
	}

	public String getProdCaseCd() {
		return this.prodCaseCd;
	}

	public void setProdCaseCd(String prodCaseCd) {
		this.prodCaseCd = prodCaseCd;
	}

	public String getThemeId() {
		return this.themeId;
	}

	public void setThemeId(String themeId) {
		this.themeId = themeId;
	}

	public String getThemeName() {
		return this.themeName;
	}

	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}

	public String getLinkUrl() {
		return this.linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getPlanId() {
		return this.planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getPlanName() {
		return this.PlanName;
	}

	public void setPlanName(String planName) {
		this.PlanName = planName;
	}

	public String getSubTitleName() {
		return this.subTitleName;
	}

	public void setSubTitleName(String subTitleName) {
		this.subTitleName = subTitleName;
	}

	public String getPlanStartDt() {
		return this.planStartDt;
	}

	public void setPlanStartDt(String planStartDt) {
		this.planStartDt = planStartDt;
	}

	public String getPlanEndDt() {
		return this.planEndDt;
	}

	public void setPlanEndDt(String planEndDt) {
		this.planEndDt = planEndDt;
	}

	public String getPrzwnerAnnoDt() {
		return this.przwnerAnnoDt;
	}

	public void setPrzwnerAnnoDt(String przwnerAnnoDt) {
		this.przwnerAnnoDt = przwnerAnnoDt;
	}

	public String getPlanGiftName() {
		return this.planGiftName;
	}

	public void setPlanGiftName(String planGiftName) {
		this.planGiftName = planGiftName;
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

}
