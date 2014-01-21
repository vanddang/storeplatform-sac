/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.shopping;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * <pre>
 * 쇼핑 Value Object
 * </pre>
 *
 * Created on : 2014-01-02
 * Created by : 김형식, SK플래닛
 * Last Updated on : 2014-01-02
 * Last Updated by : 김형식, SK플래닛
 */

public class ShoppingReq extends CommonInfo {

	private static final long serialVersionUID = 1L;
	private String tenantId; // 테넌트ID
	private String systemId; // 시스템ID
	private String listId; // 리스트아이디
	private String imageCd; // 이미지코드
	private String menuId; // 메뉴아이디
	private String prodCharge; // 유료/무료 구분
	private String prodGradeCd; // 상품등급코드
	private String b2bProd; // B2B 상품 구분
	private String orderedBy; // 정렬순서
	private String stdDt; // 기준일자
	private String langCd; // 언어코드
	private String deviceModelCd; // 디바이스 모델 코드
	private String dummy; // 더비 구분값
	private Integer offset; // offset
	private Integer count; // count

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

	public String getListId() {
		return this.listId;
	}

	public void setListId(String listId) {
		this.listId = listId;
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

	public String getB2bProd() {
		return this.b2bProd;
	}

	public void setB2bProd(String b2bProd) {
		this.b2bProd = b2bProd;
	}

	public String getOrderedBy() {
		return this.orderedBy;
	}

	public void setOrderedBy(String orderedBy) {
		this.orderedBy = orderedBy;
	}

	public String getStdDt() {
		return this.stdDt;
	}

	public void setStdDt(String stdDt) {
		this.stdDt = stdDt;
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
