/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.product.vo.category;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 웹툰 request Input Value Object.
 * 
 * Updated on : 2013. 12. 23. Updated by : 김형식, SK 플래닛.
 */
public class WebtoonRequest extends CommonInfo {

	private static final long serialVersionUID = 1L;
	private String tenantId; // 테넌트ID
	private String systemId; // 시스템ID
	private String imageCd; // 이미지코드
	private String menuId; // 메뉴아이디
	private String prodCharge; // 유료/무료 구분
	private String prodGradeCd; // 상품등급코드
	private String drm; // DRM 지원 구분
	private String weekDayCd; // 요일별 구분 코드
	private int offset = 1; // offset
	private int count = 20; // count

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

	public String getImageCd() {
		return this.imageCd;
	}

	public void setImageCd(String imageCd) {
		this.imageCd = imageCd;
	}

	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

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

	public String getWeekDayCd() {
		return this.weekDayCd;
	}

	public void setWeekDayCd(String weekDayCd) {
		this.weekDayCd = weekDayCd;
	}

	public int getOffset() {
		return this.offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getCount() {
		return this.count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
