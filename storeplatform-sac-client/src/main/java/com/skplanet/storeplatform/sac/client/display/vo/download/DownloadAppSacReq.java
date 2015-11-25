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
 * Download 앱 상품 정보 조회 Value Object.
 *
 * Updated on : 2014. 01. 21. Updated by : 이석희, 아이에스플러스.
 */
public class DownloadAppSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/* interface values */
	@NotBlank
	private String filteredBy; // 조회유형
	private String productId; // 상품Id
	private String packageName; // 패키지명
	private String deviceKey; // device key
	private String userKey; // 구매자 회원번호
	private String parentBunchId;
	private Integer apkVerCd; // Pre package versionCode
	private String visitPathNm; // 다운로드 요청 경로
	private String dwldTypeCd; // 다운로드 유형 코드 (DP012701:다운로드, DP012702:자동 업데이트, DP012703:수동 업데이트, DP012704:이어받기)
    private String mdn;
    private String updateAlarmYn; // 업데이트 알람 수신 여부
    private String packetFreeYn; // packet 비과금 처리 여부

    public String getMdn() {
        return mdn;
    }

    public void setMdn(String mdn) {
        this.mdn = mdn;
    }

    public String getPacketFreeYn() {
        return packetFreeYn;
    }

    public void setPacketFreeYn(String packetFreeYn) {
        this.packetFreeYn = packetFreeYn;
    }

    /* internal values */
	private String tenantId; // tenantId
	private String systemId; // 시스템Id
	private String deviceModelCd; // 단말모델코드
	private String anyDeviceModelCd; // 가상 프로비저닝 단말모델코드
	private String langCd; // 언어코드
	private String osVersion; // os 버전
	private String lcdSize; // lcd 크기
	private String category; // 상품 유형
	private String imageCd; // 이미지 코드
	private String prchsDt; // 구매일시
	private String dwldStartDt; // 다운로드 시작일시
	private String dwldExprDt; // 다운로드 만료일시
	private String bnchProdId;


	/**
	 *
	 * <pre>
	 * tenantId.
	 * </pre>
	 *
	 * @return String
	 */
	public String getTenantId() {
		return tenantId;
	}

	/**
	 *
	 * <pre>
	 * tenantId.
	 * </pre>
	 *
	 * @param tenantId
	 *            tenantId
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 *
	 * <pre>
	 * 시스템Id.
	 * </pre>
	 *
	 * @return String
	 */
	public String getSystemId() {
		return systemId;
	}

	/**
	 *
	 * <pre>
	 * 시스템Id.
	 * </pre>
	 *
	 * @param systemId
	 *            systemId
	 */
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	/**
	 *
	 * <pre>
	 * 단말모델코드.
	 * </pre>
	 *
	 * @return String
	 */
	public String getDeviceModelCd() {
		return deviceModelCd;
	}

	/**
	 *
	 * <pre>
	 * 단말모델코드.
	 * </pre>
	 *
	 * @param deviceModelCd
	 *            deviceModelCd
	 */
	public void setDeviceModelCd(String deviceModelCd) {
		this.deviceModelCd = deviceModelCd;
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

	/**
	 *
	 * <pre>
	 * 언어코드.
	 * </pre>
	 *
	 * @return String
	 */
	public String getLangCd() {
		return langCd;
	}

	/**
	 *
	 * <pre>
	 * 언어코드.
	 * </pre>
	 *
	 * @param langCd
	 *            langCd
	 */
	public void setLangCd(String langCd) {
		this.langCd = langCd;
	}

	/**
	 * @return the lcdSize
	 */
	public String getLcdSize() {
		return lcdSize;
	}

	/**
	 * @param lcdSize
	 *            the lcdSize to set
	 */
	public void setLcdSize(String lcdSize) {
		this.lcdSize = lcdSize;
	}

	/**
	 *
	 * <pre>
	 * os 버전.
	 * </pre>
	 *
	 * @return String
	 */
	public String getOsVersion() {
		return osVersion;
	}

	/**
	 *
	 * <pre>
	 * 언어코드.
	 * </pre>
	 *
	 * @param osVersion
	 *            osVersion
	 */
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	/**
	 *
	 * <pre>
	 * 상품 유형.
	 * </pre>
	 *
	 * @return String
	 */
	public String getCategory() {
		return category;
	}

	/**
	 *
	 * <pre>
	 * 상품 유형.
	 * </pre>
	 *
	 * @param category
	 *            category
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 *
	 * <pre>
	 * 조회 유형.
	 * </pre>
	 *
	 * @return String
	 */
	public String getFilteredBy() {
		return filteredBy;
	}

	/**
	 *
	 * <pre>
	 * 조회 유형.
	 * </pre>
	 *
	 * @param filteredBy
	 *            filteredBy
	 */
	public void setFilteredBy(String filteredBy) {
		this.filteredBy = filteredBy;
	}

	/**
	 *
	 * <pre>
	 * 상품 ID.
	 * </pre>
	 *
	 * @return String
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 *
	 * <pre>
	 * 상품 ID.
	 * </pre>
	 *
	 * @param productId
	 *            productId
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 *
	 * <pre>
	 * 패키지 명.
	 * </pre>
	 *
	 * @return String
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 *
	 * <pre>
	 * 패키지 명.
	 * </pre>
	 *
	 * @param packageName
	 *            packageName
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	/**
	 *
	 * <pre>
	 * device key.
	 * </pre>
	 *
	 * @return String
	 */
	public String getDeviceKey() {
		return deviceKey;
	}

	/**
	 *
	 * <pre>
	 * device key.
	 * </pre>
	 *
	 * @param deviceKey
	 *            deviceKey
	 */
	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	/**
	 *
	 * <pre>
	 * 사용자 Key.
	 * </pre>
	 *
	 * @return String
	 */
	public String getUserKey() {
		return userKey;
	}

	/**
	 *
	 * <pre>
	 * 사용자 Key.
	 * </pre>
	 *
	 * @param userKey
	 *            userKey
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return the imgCd
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

	public String getParentBunchId() {
		return parentBunchId;
	}

	public void setParentBunchId(String parentBunchId) {
		this.parentBunchId = parentBunchId;
	}

	public String getBnchProdId() {
		return bnchProdId;
	}

	public void setBnchProdId(String bnchProdId) {
		this.bnchProdId = bnchProdId;
	}

	public Integer getApkVerCd() {
		return apkVerCd;
	}

	public void setApkVerCd(Integer apkVerCd) {
		this.apkVerCd = apkVerCd;
	}

	public String getVisitPathNm() {
		return visitPathNm;
	}

	public void setVisitPathNm(String visitPathNm) {
		this.visitPathNm = visitPathNm;
	}

	public String getDwldTypeCd() {
		return dwldTypeCd;
	}

	public void setDwldTypeCd(String dwldTypeCd) {
		this.dwldTypeCd = dwldTypeCd;
	}

    public String getUpdateAlarmYn() {
        return updateAlarmYn;
    }

    public void setUpdateAlarmYn(String updateAlarmYn) {
        this.updateAlarmYn = updateAlarmYn;
    }
}
