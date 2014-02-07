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
 * 일반 카테고리 앱 상품 조회 Request Value Object.
 * 
 * Updated on : 2014. 01. 06. Updated by : 이태희, SK 플래닛.
 */
public class CategoryAppSacReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private String prodCharge; // 상품 유무료 구분

	private String prodGradeCd; // 상품등급코드

	private String[] arrayProdGradeCd; // 상품등급코드 Array

	private String menuId; // 메뉴ID

	private String orderedBy; // 상품정렬순서

	private Integer offset; // 시작점 ROW

	private Integer count; // 페이지당 노출될 ROW 개수

	private String deviceModelCd; // 단말 모델 코드

	private String tenantId; // 테넌트ID

	private String imageCd; // 이미지 코드

	private String langCd; // 언어 코드

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

	public String[] getArrayProdGradeCd() {
		return this.arrayProdGradeCd;
	}

	public void setArrayProdGradeCd(String[] arrayProdGradeCd) {
		this.arrayProdGradeCd = arrayProdGradeCd;
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

	public String getDeviceModelCd() {
		return this.deviceModelCd;
	}

	public void setDeviceModelCd(String deviceModelCd) {
		this.deviceModelCd = deviceModelCd;
	}

	public String getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getImageCd() {
		return this.imageCd;
	}

	public void setImageCd(String imageCd) {
		this.imageCd = imageCd;
	}

	public String getLangCd() {
		return this.langCd;
	}

	public void setLangCd(String langCd) {
		this.langCd = langCd;
	}

}
