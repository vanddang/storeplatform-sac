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

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 웹툰 DTO Default Value Object.
 * 
 * Updated on : 2013. 12. 22. Updated by : 김형식, SK 플래닛.
 */
public class RecommendOneday extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private Integer totalCount;
	private String tenantId;
	private String menuId;
	private String prodId;
	private String partProdId;
	private String svcGrpCd;
	private String svcGrpNm;
	private String svcTypeCd;
	private String contentsTypeCd;
	private String metaClsfCd;
	private String topMenuId;
	private String expoOrd;

	// 쇼핑 관련
	private String catalogId;
	private String brandId;

	// 하루에 하나 관련
	private String expoDt;
	private String expoStartDt;
	private String expoEndDt;
	private String oneSeq;
	private String partChrgmonyAppYn;
	private Integer freeItemAmt;
	private Integer prodRealreAmt;
	private Integer prodOffrAmt;

	private String notfctWrtgold;
	private String imageUrl;

	public Integer getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public String getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
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

	public String getSvcGrpCd() {
		return this.svcGrpCd;
	}

	public void setSvcGrpCd(String svcGrpCd) {
		this.svcGrpCd = svcGrpCd;
	}

	public String getSvcTypeCd() {
		return this.svcTypeCd;
	}

	public void setSvcTypeCd(String svcTypeCd) {
		this.svcTypeCd = svcTypeCd;
	}

	public String getContentsTypeCd() {
		return this.contentsTypeCd;
	}

	public void setContentsTypeCd(String contentsTypeCd) {
		this.contentsTypeCd = contentsTypeCd;
	}

	public String getMetaClsfCd() {
		return this.metaClsfCd;
	}

	public void setMetaClsfCd(String metaClsfCd) {
		this.metaClsfCd = metaClsfCd;
	}

	public String getTopMenuId() {
		return this.topMenuId;
	}

	public void setTopMenuId(String topMenuId) {
		this.topMenuId = topMenuId;
	}

	public String getExpoOrd() {
		return this.expoOrd;
	}

	public void setExpoOrd(String expoOrd) {
		this.expoOrd = expoOrd;
	}

	public String getCatalogId() {
		return this.catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public String getBrandId() {
		return this.brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getExpoDt() {
		return this.expoDt;
	}

	public void setExpoDt(String expoDt) {
		this.expoDt = expoDt;
	}

	public String getExpoStartDt() {
		return this.expoStartDt;
	}

	public void setExpoStartDt(String expoStartDt) {
		this.expoStartDt = expoStartDt;
	}

	public String getExpoEndDt() {
		return this.expoEndDt;
	}

	public void setExpoEndDt(String expoEndDt) {
		this.expoEndDt = expoEndDt;
	}

	public String getOneSeq() {
		return this.oneSeq;
	}

	public void setOneSeq(String oneSeq) {
		this.oneSeq = oneSeq;
	}

	public String getPartChrgmonyAppYn() {
		return this.partChrgmonyAppYn;
	}

	public void setPartChrgmonyAppYn(String partChrgmonyAppYn) {
		this.partChrgmonyAppYn = partChrgmonyAppYn;
	}

	public Integer getFreeItemAmt() {
		return this.freeItemAmt;
	}

	public void setFreeItemAmt(Integer freeItemAmt) {
		this.freeItemAmt = freeItemAmt;
	}

	/**
	 * @return the prodRealreAmt
	 */
	public Integer getProdRealreAmt() {
		return this.prodRealreAmt;
	}

	/**
	 * @param prodRealreAmt
	 *            the prodRealreAmt to set
	 */
	public void setProdRealreAmt(Integer prodRealreAmt) {
		this.prodRealreAmt = prodRealreAmt;
	}

	/**
	 * @return the prodOffrAmt
	 */
	public Integer getProdOffrAmt() {
		return this.prodOffrAmt;
	}

	/**
	 * @param prodOffrAmt
	 *            the prodOffrAmt to set
	 */
	public void setProdOffrAmt(Integer prodOffrAmt) {
		this.prodOffrAmt = prodOffrAmt;
	}

	/**
	 * @return the svcGrpNm
	 */
	public String getSvcGrpNm() {
		return this.svcGrpNm;
	}

	/**
	 * @param svcGrpNm
	 *            the svcGrpNm to set
	 */
	public void setSvcGrpNm(String svcGrpNm) {
		this.svcGrpNm = svcGrpNm;
	}

	/**
	 * @return the notfctWrtgold
	 */
	public String getNotfctWrtgold() {
		return this.notfctWrtgold;
	}

	/**
	 * @param notfctWrtgold
	 *            the notfctWrtgold to set
	 */
	public void setNotfctWrtgold(String notfctWrtgold) {
		this.notfctWrtgold = notfctWrtgold;
	}

	/**
	 * @return the imageUrl
	 */
	public String getImageUrl() {
		return this.imageUrl;
	}

	/**
	 * @param imageUrl
	 *            the imageUrl to set
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
