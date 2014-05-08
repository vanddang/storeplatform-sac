/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.brandshop;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 브랜드샵 리스트 조회 Input Value Object.
 * 
 * Updated on : 2014. 4. 16. Updated by : 이승훈, 엔텔스.
 */
public class BrandshopListSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String channelId;
	private String menuId;
	private String filteredBy; // 조회유형
	private Integer offset = 1; // offset
	private Integer count = 20; // count
	private String tenantId; // 테넌트ID
	private String langCd; // 언어코드
	private String deviceModelCd; // 디바이스 모델 코드
	private String topMenuId; // TOP 메뉴 아이디

	@NotBlank(message = "필수 파라미터 입니다.")
	private String brandshopId; // 브랜드샵 메뉴 아이디
	@Pattern(regexp = "|DP000701|DP000709")
	private String orderedBy; // orderedBy

	private String anyDeviceModelCd; // 가상단말명
	// TB_CM_DEVICE 가능여부
	private String ebookSprtYn; // eBook 상품 지원여부
	private String comicSprtYn; // Comic 상품 지원여부
	private String musicSprtYn; // 음악 상품 지원여부
	private String videoDrmSprtYn; // VOD 상품 DRM 지원 여부
	private String sdVideoSprtYn; // VOD 상품 SD 지원 여부

	// TODO osm1021 dummy data가 필요없어지면 삭제할것
	private String dummy;

	public String getChannelId() {
		return this.channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getFilteredBy() {
		return this.filteredBy;
	}

	public void setFilteredBy(String filteredBy) {
		this.filteredBy = filteredBy;
	}

	public String getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getLangCd() {
		return this.langCd;
	}

	public void setLangCd(String langCd) {
		this.langCd = langCd;
	}

	public String getDeviceModelCd() {
		return this.deviceModelCd;
	}

	public void setDeviceModelCd(String deviceModelCd) {
		this.deviceModelCd = deviceModelCd;
	}

	public String getTopMenuId() {
		return this.topMenuId;
	}

	public void setTopMenuId(String topMenuId) {
		this.topMenuId = topMenuId;
	}

	public String getDummy() {
		return this.dummy;
	}

	public void setDummy(String dummy) {
		this.dummy = dummy;
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

	public String getBrandshopId() {
		return this.brandshopId;
	}

	public void setBrandshopId(String brandshopId) {
		this.brandshopId = brandshopId;
	}

	public String getOrderedBy() {
		return this.orderedBy;
	}

	public void setOrderedBy(String orderedBy) {
		this.orderedBy = orderedBy;
	}

	/**
	 * @return the anyDeviceModelCd
	 */
	public String getAnyDeviceModelCd() {
		return this.anyDeviceModelCd;
	}

	/**
	 * @param anyDeviceModelCd
	 *            the anyDeviceModelCd to set
	 */
	public void setAnyDeviceModelCd(String anyDeviceModelCd) {
		this.anyDeviceModelCd = anyDeviceModelCd;
	}

	/**
	 * @return the ebookSprtYn
	 */
	public String getEbookSprtYn() {
		return this.ebookSprtYn;
	}

	/**
	 * @param ebookSprtYn
	 *            the ebookSprtYn to set
	 */
	public void setEbookSprtYn(String ebookSprtYn) {
		this.ebookSprtYn = ebookSprtYn;
	}

	/**
	 * @return the comicSprtYn
	 */
	public String getComicSprtYn() {
		return this.comicSprtYn;
	}

	/**
	 * @param comicSprtYn
	 *            the comicSprtYn to set
	 */
	public void setComicSprtYn(String comicSprtYn) {
		this.comicSprtYn = comicSprtYn;
	}

	/**
	 * @return the musicSprtYn
	 */
	public String getMusicSprtYn() {
		return this.musicSprtYn;
	}

	/**
	 * @param musicSprtYn
	 *            the musicSprtYn to set
	 */
	public void setMusicSprtYn(String musicSprtYn) {
		this.musicSprtYn = musicSprtYn;
	}

	/**
	 * @return the videoDrmSprtYn
	 */
	public String getVideoDrmSprtYn() {
		return this.videoDrmSprtYn;
	}

	/**
	 * @param videoDrmSprtYn
	 *            the videoDrmSprtYn to set
	 */
	public void setVideoDrmSprtYn(String videoDrmSprtYn) {
		this.videoDrmSprtYn = videoDrmSprtYn;
	}

	/**
	 * @return the sdVideoSprtYn
	 */
	public String getSdVideoSprtYn() {
		return this.sdVideoSprtYn;
	}

	/**
	 * @param sdVideoSprtYn
	 *            the sdVideoSprtYn to set
	 */
	public void setSdVideoSprtYn(String sdVideoSprtYn) {
		this.sdVideoSprtYn = sdVideoSprtYn;
	}

}
