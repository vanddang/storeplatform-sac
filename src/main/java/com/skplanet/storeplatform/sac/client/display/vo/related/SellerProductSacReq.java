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
 * 특정 판매자별 상품 조회 Input Value Object.
 * 
 * Updated on : 2014. 02. 19. Updated by : 유시혁.
 */
public class SellerProductSacReq extends CommonInfo implements Serializable {

	private static final long serialVersionUID = 11123123143L;
	private String sellerNo; // 판매자회원번호
	private String exceptId; // 제외 상품 아이디
	private String[] arrayExceptId; // 제외 상품 아이디 배열
	private Integer offset; // 시작점 ROW
	private Integer count; // 페이지당 노출 ROW 수
	private String tenantId; // 테넌트 ID
	private String systemId; // 시스템Id
	private String deviceModelCd; // 단말모델코드
	private String langCd; // 언어코드

	public String getSellerNo() {
		return this.sellerNo;
	}

	public void setSellerNo(String sellerNo) {
		this.sellerNo = sellerNo;
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

}
