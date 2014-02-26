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

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 테마존 상품 조회 Input Value Object.
 * 
 * Updated on : 2014. 2. 21. Updated by : 이승훈, 엔텔스.
 */
public class ThemeThemeZoneSacReq extends CommonInfo {

	private Integer offset = 1; // offset
	private Integer count = 20; // count
	private String tenantId; // 테넌트ID
	private String listId; // 리스트ID
	private String langCd; // 언어코드
	private String deviceModelCd; // 디바이스 모델 코드
	private String topMenuId; // TOP 메뉴 아이디
	private String bnrMenuId; // 배너 메뉴 아이디
	private String prodCharge; // 상품의 유료/무료 구분
	private String prodGradeCd; // 상품 등급 코드
	private String[] arrayProdGradeCd; // 상품등급코드 Array
	private String b2bProd; // B2B 상품 구분

	// TODO osm1021 dummy data가 필요없어지면 삭제할것
	private String dummy;

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

	public String getListId() {
		return this.listId;
	}

	public void setListId(String listId) {
		this.listId = listId;
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

	public String getB2bProd() {
		return this.b2bProd;
	}

	public void setB2bProd(String b2bProd) {
		this.b2bProd = b2bProd;
	}

	public String getDummy() {
		return this.dummy;
	}

	public void setDummy(String dummy) {
		this.dummy = dummy;
	}

	public String getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String[] getArrayProdGradeCd() {
		return this.arrayProdGradeCd;
	}

	public void setArrayProdGradeCd(String[] arrayProdGradeCd) {
		this.arrayProdGradeCd = arrayProdGradeCd;
	}

}
