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

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * OpenApi 상품 상세 정보 요청(By ProductId) Request Value Object.
 * 
 * Updated on : 2014. 03. 12. Updated by : 백승현, 인크로스.
 */
public class AppDetailByProductIdSacReq extends CommonInfo {
	private static final long serialVersionUID = 1L;
	private String tenantId; // tenantId
	private String langCd; // 언어코드
	private String imageCd; // 원본 이미지 코드

	@NotBlank
	private String productId; // 상품ID

	private String webPocUrl; // T Store WEB POC URL
	private String scUrl; // T Store WEB POC URL

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
	 * @return the webPocUrl
	 */
	public String getWebPocUrl() {
		return this.webPocUrl;
	}

	/**
	 * @param webPocUrl
	 *            the webPocUrl to set
	 */
	public void setWebPocUrl(String webPocUrl) {
		this.webPocUrl = webPocUrl;
	}

	/**
	 * @return the scUrl
	 */
	public String getScUrl() {
		return this.scUrl;
	}

	/**
	 * @param scUrl
	 *            the scUrl to set
	 */
	public void setScUrl(String scUrl) {
		this.scUrl = scUrl;
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

}
