/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.other.vo;

/**
 * 
 * 
 * Updated on : 2014. 1. 28. Updated by : 이승훈, 엔텔스.
 */
public class OtherServiceGroup {
	private int totalCount;
	private String prodId;
	private String contentsTypeCd;
	private String contentsTypeNm;
	private String topMenuId;
	private String topMenuNm;
	private String menuId;
	private String menuNm;
	private String svcGrpCd;
	private String metaClsfCd;
	private String prodGrdCd;
	private String prodChrgYn;

	public String getTopMenuNm() {
		return this.topMenuNm;
	}

	public void setTopMenuNm(String topMenuNm) {
		this.topMenuNm = topMenuNm;
	}

	public int getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public String getProdId() {
		return this.prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getContentsTypeCd() {
		return this.contentsTypeCd;
	}

	public void setContentsTypeCd(String contentsTypeCd) {
		this.contentsTypeCd = contentsTypeCd;
	}

	public String getContentsTypeNm() {
		return this.contentsTypeNm;
	}

	public void setContentsTypeNm(String contentsTypeNm) {
		this.contentsTypeNm = contentsTypeNm;
	}

	public String getTopMenuId() {
		return this.topMenuId;
	}

	public void setTopMenuId(String topMenuId) {
		this.topMenuId = topMenuId;
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
	 * @return the menuNm
	 */
	public String getMenuNm() {
		return this.menuNm;
	}

	/**
	 * @param menuNm
	 *            the menuNm to set
	 */
	public void setMenuNm(String menuNm) {
		this.menuNm = menuNm;
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
	 * @return the prodChrgYn
	 */
	public String getProdChrgYn() {
		return this.prodChrgYn;
	}

	/**
	 * @param prodChrgYn
	 *            the prodChrgYn to set
	 */
	public void setProdChrgYn(String prodChrgYn) {
		this.prodChrgYn = prodChrgYn;
	}

}
