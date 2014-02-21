/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.theme;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 브렌드샵 테마 상품 조회 Input Value Object.
 * 
 * Updated on : 2014. 2. 6. Updated by : 이승훈, 엔텔스.
 */
public class ThemeBrandshopSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "필수 파라미터 입니다.")
	private String channelId;
	private String menuId;
	private String filteredBy; // 조회유형
	private Integer offset = 1; // offset
	private Integer count = 20; // count
	private String tenantId; // 테넌트ID
	private String langCd; // 언어코드
	private String deviceModelCd; // 디바이스 모델 코드
	private String topMenuId; // TOP 메뉴 아이디
	private String bnrMenuId; // 배너 메뉴 아이디

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

	public String getBnrMenuId() {
		return this.bnrMenuId;
	}

	public void setBnrMenuId(String bnrMenuId) {
		this.bnrMenuId = bnrMenuId;
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

}
