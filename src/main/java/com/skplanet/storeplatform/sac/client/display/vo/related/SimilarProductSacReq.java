/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.related;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 이 상품과 유사 상품 조회 Input Value Object.
 * 
 * Updated on : 2014. 02. 18. Updated by : 유시혁.
 */
public class SimilarProductSacReq extends CommonInfo implements Serializable {

	private static final long serialVersionUID = 11123123143L;
	private String productId; // 상품 아이디
	private String exceptId; // 제외 상품 아이디
	private String[] arrayExceptId; // 제외 상품 아이디 배열
	private Integer offset; // 시작점 ROW
	private Integer count; // 페이지당 노출 ROW 수
	private String tenantId; // 테넌트 ID
	private String systemId; // 시스템Id
	private String deviceModelCd; // 단말모델코드
	private String langCd; // 언어코드
	private String testYn; // 테스트 여부

	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getExceptId() {
		return this.exceptId;
	}

	public void setExceptId(String exceptId) {
		this.exceptId = exceptId;
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

	public String[] getArrayExceptId() {
		return this.arrayExceptId;
	}

	public void setArrayExceptId(String[] arrayExceptId) {
		this.arrayExceptId = arrayExceptId;
	}

	public String getTestYn() {
		return this.testYn;
	}

	public void setTestYn(String testYn) {
		this.testYn = testYn;
	}

}
