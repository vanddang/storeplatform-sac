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

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 웹툰 request Input Value Object.
 * 
 * Updated on : 2013. 12. 23. Updated by : 김형식, SK 플래닛.
 */
public class CategoryWebtoonSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;
	private String tenantId; // 테넌트ID
	private String systemId; // 시스템ID
	private String imageCd; // 이미지코드
	private String topMenuId; // TOP 메뉴 아이디
	@Pattern(regexp = "|^DP26[0-9]*")
	private String menuId; // 메뉴아이디
	private String listId; // 리스트아이디
	@Pattern(regexp = "|DP010101|DP010102|DP010103|DP010104|DP010105|DP010106|DP010107")
	private String weekDayCd; // 요일별 구분 코드
	private String langCd; // 언어코드
	private String deviceModelCd; // 디바이스 모델 코드
	@Valid
	private Integer offset = 1; // offset
	@Valid
	private Integer count = 20; // count
	private String prodGradeCd; // 상품등급코드
	private String[] arrayProdGradeCd; // 상품등급코드 Array

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

	public String getTopMenuId() {
		return this.topMenuId;
	}

	public void setTopMenuId(String topMenuId) {
		this.topMenuId = topMenuId;
	}

	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getListId() {
		return this.listId;
	}

	public void setListId(String listId) {
		this.listId = listId;
	}

	public String getWeekDayCd() {
		return this.weekDayCd;
	}

	public void setWeekDayCd(String weekDayCd) {
		this.weekDayCd = weekDayCd;
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

}
