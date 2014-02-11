/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.appzine;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Appzine 회차별 목록 조회 Input Value Object.
 * 
 * Updated on : 2014. 02. 10. Updated by : 유시혁.
 */
public class AppzineVolListSacReq extends CommonInfo {

	private static final long serialVersionUID = 11123123129L;

	private String tenantId; // 테넌트ID

	private String menuId; // 메뉴ID

	private String systemId; // 시스템ID

	private String deviceModelCd; // device model code

	private String langCd; // language code

	private Integer offset; // 시작점 ROW

	private Integer count; // 페이지당 노출될 ROW 개수

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

	public String getSystemId() {
		return this.systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getDeviceModelCd() {
		return this.deviceModelCd;
	}

	public void setDeviceModelCd(String deviceModelCd) {
		this.deviceModelCd = deviceModelCd;
	}

	public String getLangCd() {
		return this.langCd;
	}

	public void setLangCd(String langCd) {
		this.langCd = langCd;
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
