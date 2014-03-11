/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.feature.brandshop.vo;

/**
 * 
 * 
 * Updated on : 2014. 2. 25. Updated by : 이승훈, 엔텔스.
 */
public class BrandshopInfo {
	private int totalCount;
	private String brandId;
	private String brandShopNm;
	private String categoryNo;
	private String logImgPos;
	private int bnrImgSize;
	private String expoOrd;
	private String mbrNo;
	private String menuNm;
	private String topMenuId;
	private String bnrImgNm;

	public int getTotalCount() {
		return this.totalCount;
	}

	public String getBrandId() {
		return this.brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getBrandShopNm() {
		return this.brandShopNm;
	}

	public void setBrandShopNm(String brandShopNm) {
		this.brandShopNm = brandShopNm;
	}

	public String getCategoryNo() {
		return this.categoryNo;
	}

	public void setCategoryNo(String categoryNo) {
		this.categoryNo = categoryNo;
	}

	public String getLogImgPos() {
		return this.logImgPos;
	}

	public void setLogImgPos(String logImgPos) {
		this.logImgPos = logImgPos;
	}

	/**
	 * @return the bnrImgSize
	 */
	public int getBnrImgSize() {
		return this.bnrImgSize;
	}

	/**
	 * @param bnrImgSize
	 *            the bnrImgSize to set
	 */
	public void setBnrImgSize(int bnrImgSize) {
		this.bnrImgSize = bnrImgSize;
	}

	public String getExpoOrd() {
		return this.expoOrd;
	}

	public void setExpoOrd(String expoOrd) {
		this.expoOrd = expoOrd;
	}

	public String getMbrNo() {
		return this.mbrNo;
	}

	public void setMbrNo(String mbrNo) {
		this.mbrNo = mbrNo;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public String getMenuNm() {
		return this.menuNm;
	}

	public void setMenuNm(String menuNm) {
		this.menuNm = menuNm;
	}

	public String getTopMenuId() {
		return this.topMenuId;
	}

	public void setTopMenuId(String topMenuId) {
		this.topMenuId = topMenuId;
	}

	/**
	 * @return the bnrImgNm
	 */
	public String getBnrImgNm() {
		return this.bnrImgNm;
	}

	/**
	 * @param bnrImgNm
	 *            the bnrImgNm to set
	 */
	public void setBnrImgNm(String bnrImgNm) {
		this.bnrImgNm = bnrImgNm;
	}

}
