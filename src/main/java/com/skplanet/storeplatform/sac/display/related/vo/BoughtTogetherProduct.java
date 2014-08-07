/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.related.vo;

import org.apache.commons.lang3.StringUtils;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * BoughtTogetherProduct VO
 * <pre>
 * Updated on 2014. 08. 07. by 서대영, SK 플래닛
 * </pre>
 */
public class BoughtTogetherProduct extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String productId;
	private String topMenuId;
	private String menuId;
	private String tenantId;
	private String deviceModelCd;
	private String mmDeviceModelCd;
	private String exceptId;
	private String ebookSprtYn;
	private String comicSprtYn;
	private String musicSprtYn;
	private String videoDrmSprtYn;
	private String sdVideoSprtYn;
	private Integer offset;
	private Integer count;
	private String testYn;

	public String getProductId() {
		return this.productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	/**
	 * @return the topMenuId
	 */
	public String getTopMenuId() {
		return topMenuId;
	}
	/**
	 * @param topMenuId the topMenuId to set
	 */
	public void setTopMenuId(String topMenuId) {
		this.topMenuId = topMenuId;
	}
	public String getMenuId() {
		return this.menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getTenantId() {
		return this.tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getDeviceModelCd() {
		return this.deviceModelCd;
	}
	public void setDeviceModelCd(String deviceModelCd) {
		this.deviceModelCd = deviceModelCd;
	}
	public String getMmDeviceModelCd() {
		return this.mmDeviceModelCd;
	}
	public void setMmDeviceModelCd(String mmDeviceModelCd) {
		this.mmDeviceModelCd = mmDeviceModelCd;
	}
	public String getExceptId() {
		return this.exceptId;
	}
	public void setExceptId(String exceptId) {
		this.exceptId = exceptId;
	}
	public String getEbookSprtYn() {
		return this.ebookSprtYn;
	}
	public void setEbookSprtYn(String ebookSprtYn) {
		this.ebookSprtYn = ebookSprtYn;
	}
	public String getComicSprtYn() {
		return this.comicSprtYn;
	}
	public void setComicSprtYn(String comicSprtYn) {
		this.comicSprtYn = comicSprtYn;
	}
	public String getMusicSprtYn() {
		return this.musicSprtYn;
	}
	public void setMusicSprtYn(String musicSprtYn) {
		this.musicSprtYn = musicSprtYn;
	}
	public String getVideoDrmSprtYn() {
		return this.videoDrmSprtYn;
	}
	public void setVideoDrmSprtYn(String videoDrmSprtYn) {
		this.videoDrmSprtYn = videoDrmSprtYn;
	}
	public String getSdVideoSprtYn() {
		return this.sdVideoSprtYn;
	}
	public void setSdVideoSprtYn(String sdVideoSprtYn) {
		this.sdVideoSprtYn = sdVideoSprtYn;
	}
	public Integer getOffset() {
		return this.offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	public Integer getCount() {
		return this.count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getTestYn() {
		return this.testYn;
	}
	public void setTestYn(String testYn) {
		this.testYn = testYn;
	}
	public String[] getArrayExceptId() {
		if (StringUtils.isNotEmpty(this.exceptId)) {
			return StringUtils.split(this.exceptId, "+");
		} else {
			return null;
		}
	}
}
