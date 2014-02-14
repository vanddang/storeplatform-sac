/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.menu;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 일반 상품 카테고리 리스트 조회 Input Value Object.
 * 
 * Updated on : 2014. 02. 12. Updated by : 유시혁.
 */
public class MenuSacReq extends CommonInfo {

	private static final long serialVersionUID = 11123123129L;

	private String tenantId; // 테넌트ID

	private String menuId; // 메뉴ID

	private String topMenuId; // 메뉴ID

	private String systemId; // 시스템ID

	private String deviceModelCd; // device model code

	private String langCd; // language code

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

	public String getTopMenuId() {
		return this.topMenuId;
	}

	public void setTopMenuId(String topMenuId) {
		this.topMenuId = topMenuId;
	}
}
