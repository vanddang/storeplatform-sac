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
	private String bnrImgSize;
	private String expoOrd;
	private String mbrNo;
	private String menuNm;

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

	public String getBnrImgSize() {
		return this.bnrImgSize;
	}

	public void setBnrImgSize(String bnrImgSize) {
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

}
