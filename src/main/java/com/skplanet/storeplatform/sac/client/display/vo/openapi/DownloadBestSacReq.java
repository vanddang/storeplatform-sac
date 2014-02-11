/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.openapi;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * OpenApi Download Best 상품 조회 List Value Object.
 * 
 * Updated on : 2014. 02. 10. Updated by : 이석희, SK 플래닛.
 */
public class DownloadBestSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;
	private String tenantId; // tenantId
	private String langCd; // 언어코드
	private String imageCd; // 이미지 코드
	private String stdDt; // 기준일시

	private String listId; // listId
	private String inquiryType; // 조회구분
	private String inquiryValue; // 조회구분에 따른 판매자 ID / 또는 사업자 번호
	private String prodCharge; // 유/무료 구분
	private Integer offset; // 시작점 ROW
	private Integer count; // 페이지당 노출될 ROW 개수

	private String dummy; // dummy check

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return the langCd
	 */
	public String getLangCd() {
		return this.langCd;
	}

	/**
	 * @param langCd
	 *            the langCd to set
	 */
	public void setLangCd(String langCd) {
		this.langCd = langCd;
	}

	/**
	 * @return the imageCd
	 */
	public String getImageCd() {
		return this.imageCd;
	}

	/**
	 * @param imageCd
	 *            the imageCd to set
	 */
	public void setImageCd(String imageCd) {
		this.imageCd = imageCd;
	}

	/**
	 * @return the stdDt
	 */
	public String getStdDt() {
		return this.stdDt;
	}

	/**
	 * @param stdDt
	 *            the stdDt to set
	 */
	public void setStdDt(String stdDt) {
		this.stdDt = stdDt;
	}

	/**
	 * @return the listId
	 */
	public String getListId() {
		return this.listId;
	}

	/**
	 * @param listId
	 *            the listId to set
	 */
	public void setListId(String listId) {
		this.listId = listId;
	}

	/**
	 * @return the inquiryType
	 */
	public String getInquiryType() {
		return this.inquiryType;
	}

	/**
	 * @param inquiryType
	 *            the inquiryType to set
	 */
	public void setInquiryType(String inquiryType) {
		this.inquiryType = inquiryType;
	}

	/**
	 * @return the inquiryValue
	 */
	public String getInquiryValue() {
		return this.inquiryValue;
	}

	/**
	 * @param inquiryValue
	 *            the inquiryValue to set
	 */
	public void setInquiryValue(String inquiryValue) {
		this.inquiryValue = inquiryValue;
	}

	/**
	 * @return the prodCharge
	 */
	public String getProdCharge() {
		return this.prodCharge;
	}

	/**
	 * @param prodCharge
	 *            the prodCharge to set
	 */
	public void setProdCharge(String prodCharge) {
		this.prodCharge = prodCharge;
	}

	/**
	 * @return the offset
	 */
	public Integer getOffset() {
		return this.offset;
	}

	/**
	 * @param offset
	 *            the offset to set
	 */
	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	/**
	 * @return the count
	 */
	public Integer getCount() {
		return this.count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(Integer count) {
		this.count = count;
	}

	/**
	 * @return the dummy
	 */
	public String getDummy() {
		return this.dummy;
	}

	/**
	 * @param dummy
	 *            the dummy to set
	 */
	public void setDummy(String dummy) {
		this.dummy = dummy;
	}

}
