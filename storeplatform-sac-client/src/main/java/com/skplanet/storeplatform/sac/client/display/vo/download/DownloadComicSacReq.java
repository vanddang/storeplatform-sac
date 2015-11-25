/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.download;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Comic 상품 정보 조회(for download) Request Value Object.
 *
 * Updated on : 2014. 02. 03. Updated by : 이태희.
 */
public class DownloadComicSacReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/* interface values */
	@NotBlank
	private String productId; // 상품ID
	private String deviceKey; // 디바이스키
	private String userKey; // 사용자고유키
	private String visitPathNm; // 다운로드 요청 경로
	private String additionalMsisdn; // 989로 시작하는 자번호

	/* internal values */
	private String prchsDt; // 구매일시
	private String dwldStartDt; // 다운로드 시작일시
	private String dwldExprDt; // 다운로드 만료일시
	private String tenantId; // 테넌트ID
	private String langCd; // 언어코드
	private String deviceModelCd; // 디바이스모델코드
	private String imageCd; // 이미지코드
	private String anyDeviceModelCd; // 가상 프로비저닝 단말모델코드


	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * @param productId
	 *            the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * @return the deviceKey
	 */
	public String getDeviceKey() {
		return deviceKey;
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
		return userKey;
	}

	/**
	 * @param userKey
	 *            the userKey to set
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return the prchsDt
	 */
	public String getPrchsDt() {
		return prchsDt;
	}

	/**
	 * @param prchsDt
	 *            the prchsDt to set
	 */
	public void setPrchsDt(String prchsDt) {
		this.prchsDt = prchsDt;
	}

	/**
	 * @return the dwldStartDt
	 */
	public String getDwldStartDt() {
		return dwldStartDt;
	}

	/**
	 * @param dwldStartDt
	 *            the dwldStartDt to set
	 */
	public void setDwldStartDt(String dwldStartDt) {
		this.dwldStartDt = dwldStartDt;
	}

	/**
	 * @return the dwldExprDt
	 */
	public String getDwldExprDt() {
		return dwldExprDt;
	}

	/**
	 * @param dwldExprDt
	 *            the dwldExprDt to set
	 */
	public void setDwldExprDt(String dwldExprDt) {
		this.dwldExprDt = dwldExprDt;
	}

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return tenantId;
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
		return langCd;
	}

	/**
	 * @param langCd
	 *            the langCd to set
	 */
	public void setLangCd(String langCd) {
		this.langCd = langCd;
	}

	/**
	 * @return the deviceModelCd
	 */
	public String getDeviceModelCd() {
		return deviceModelCd;
	}

	/**
	 * @param deviceModelCd
	 *            the deviceModelCd to set
	 */
	public void setDeviceModelCd(String deviceModelCd) {
		this.deviceModelCd = deviceModelCd;
	}

	/**
	 * @return the imageCd
	 */
	public String getImageCd() {
		return imageCd;
	}

	/**
	 * @param imageCd
	 *            the imageCd to set
	 */
	public void setImageCd(String imageCd) {
		this.imageCd = imageCd;
	}

	/**
	 * @return the anyDeviceModelCd
	 */
	public String getAnyDeviceModelCd() {
		return anyDeviceModelCd;
	}

	/**
	 * @param anyDeviceModelCd
	 *            the anyDeviceModelCd to set
	 */
	public void setAnyDeviceModelCd(String anyDeviceModelCd) {
		this.anyDeviceModelCd = anyDeviceModelCd;
	}

	public String getVisitPathNm() {
		return visitPathNm;
	}

	public void setVisitPathNm(String visitPathNm) {
		this.visitPathNm = visitPathNm;
	}

	public String getAdditionalMsisdn() {
		return additionalMsisdn;
	}

	public void setAdditionalMsisdn(String additionalMsisdn) {
		this.additionalMsisdn = additionalMsisdn;
	}




}
