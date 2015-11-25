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

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * <pre>
 * 쇼핑 (브랜드 다른상품) Value Object.
 * </pre>
 * 
 * Created on : 2014-01-02 Created by : 김형식, SK플래닛 Last Updated on : 2014-01-02 Last Updated by : 김형식, SK플래닛
 */

public class ShoppingDetailReq extends CommonInfo {

	private static final long serialVersionUID = 1L;
	@NotNull
	@NotBlank
	@Pattern(regexp = "^catalog|^episode")
	private String type; // type: catalog, episode
	@NotNull
	@NotBlank
	private String productId; // 상품 ID (카탈로그)
	private String partProdId; // 상품 ID( 에피소드)
	private String specialProdId; // 특가상품 ID
	private String deviceKey; // 디바이스키
	private String userKey; // 사용자고유키
	private String saleDtUseYn; // 판매 기간이 넘어갈 경우 조회시
	private String specialType;
	private String includeProdStopStatus;// 판매 상태 여부
	private String imageCd; // 이미지코드
	private String langCd; // 언어코드
	private String virtualDeviceModelNo; // android_standard2
	private String prodRshpCd; // 채널 에피소드 관계
	private String specialTypeCd; // 쇼핑 특가 타입 코드
	private Integer offset; // offset
	private Integer count; // count

	/**
	 * @return the type
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the productId
	 */
	public String getProductId() {
		return this.productId;
	}

	/**
	 * @param productId
	 *            the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * @return the partProdId
	 */
	public String getPartProdId() {
		return this.partProdId;
	}

	/**
	 * @param partProdId
	 *            the partProdId to set
	 */
	public void setPartProdId(String partProdId) {
		this.partProdId = partProdId;
	}

	/**
	 * @return the specialProdId
	 */
	public String getSpecialProdId() {
		return this.specialProdId;
	}

	/**
	 * @param specialProdId
	 *            the specialProdId to set
	 */
	public void setSpecialProdId(String specialProdId) {
		this.specialProdId = specialProdId;
	}

	/**
	 * @return the deviceKey
	 */
	public String getDeviceKey() {
		return this.deviceKey;
	}

	/**
	 * @param deviceKey
	 *            the deviceKey to set
	 */
	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	/**
	 * @return the userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            the userKey to set
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return the specialType
	 */
	public String getSpecialType() {
		return this.specialType;
	}

	/**
	 * @param specialType
	 *            the specialType to set
	 */
	public void setSpecialType(String specialType) {
		this.specialType = specialType;
	}

	/**
	 * @return the includeProdStopStatus
	 */
	public String getIncludeProdStopStatus() {
		return this.includeProdStopStatus;
	}

	/**
	 * @param includeProdStopStatus
	 *            the includeProdStopStatus to set
	 */
	public void setIncludeProdStopStatus(String includeProdStopStatus) {
		this.includeProdStopStatus = includeProdStopStatus;
	}

	/**
	 * @return the saleDtUseYn
	 */
	public String getSaleDtUseYn() {
		return this.saleDtUseYn;
	}

	/**
	 * @param saleDtUseYn
	 *            the saleDtUseYn to set
	 */
	public void setSaleDtUseYn(String saleDtUseYn) {
		this.saleDtUseYn = saleDtUseYn;
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
	 * @return the virtualDeviceModelNo
	 */
	public String getVirtualDeviceModelNo() {
		return this.virtualDeviceModelNo;
	}

	/**
	 * @param virtualDeviceModelNo
	 *            the virtualDeviceModelNo to set
	 */
	public void setVirtualDeviceModelNo(String virtualDeviceModelNo) {
		this.virtualDeviceModelNo = virtualDeviceModelNo;
	}

	/**
	 * @return the prodRshpCd
	 */
	public String getProdRshpCd() {
		return this.prodRshpCd;
	}

	/**
	 * @param prodRshpCd
	 *            the prodRshpCd to set
	 */
	public void setProdRshpCd(String prodRshpCd) {
		this.prodRshpCd = prodRshpCd;
	}

	/**
	 * @return the specialTypeCd
	 */
	public String getSpecialTypeCd() {
		return this.specialTypeCd;
	}

	/**
	 * @param specialTypeCd
	 *            the specialTypeCd to set
	 */
	public void setSpecialTypeCd(String specialTypeCd) {
		this.specialTypeCd = specialTypeCd;
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

}
