/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.category;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 일반 카테고리 상품 조회 Request Value Object.
 * 
 * Updated on : 2013. 12. 23. Updated by : 이태희, SK 플래닛.
 */
public class CategoryAppReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private String prodCharge; // 상품 유/무료 구분

	private String prodGradeCd; // 상품등급코드

	private String drm; // DRM 지원 구분

	private String imageSizeCd; // 이미지코드

	private String menuId; // 메뉴ID

	private String orderedBy; // 정렬순서

	private String offset; // offset

	private String count; // count

	private String tenantId; // 테넌트ID

	private String systemId; // 시스템ID

	public String getProdCharge() {
		return this.prodCharge;
	}

	public void setProdCharge(String prodCharge) {
		this.prodCharge = prodCharge;
	}

	public String getProdGradeCd() {
		return this.prodGradeCd;
	}

	public void setProdGradeCd(String prodGradeCd) {
		this.prodGradeCd = prodGradeCd;
	}

	public String getDrm() {
		return this.drm;
	}

	public void setDrm(String drm) {
		this.drm = drm;
	}

	public String getImageSizeCd() {
		return this.imageSizeCd;
	}

	public void setImageSizeCd(String imageSizeCd) {
		this.imageSizeCd = imageSizeCd;
	}

	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getOrderedBy() {
		return this.orderedBy;
	}

	public void setOrderedBy(String orderedBy) {
		this.orderedBy = orderedBy;
	}

	public String getOffset() {
		return this.offset;
	}

	public void setOffset(String offset) {
		this.offset = offset;
	}

	public String getCount() {
		return this.count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getSystemId() {
		return this.systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
}
