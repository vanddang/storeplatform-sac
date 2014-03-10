/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.openapi.vo;

/**
 * 상품 상세 정보 요청(Package Name) - 타겟 단말 리스트
 * 
 * Updated on : 2014. 3. 8. Updated by : 백승현, 인크로스.
 */
public class SupportDeviceInfo {
	private String deviceModelCd;
	private String modelNm;
	private String apkVer;

	/**
	 * @return the deviceModelCd
	 */
	public String getDeviceModelCd() {
		return this.deviceModelCd;
	}

	/**
	 * @param deviceModelCd
	 *            the deviceModelCd to set
	 */
	public void setDeviceModelCd(String deviceModelCd) {
		this.deviceModelCd = deviceModelCd;
	}

	/**
	 * @return the modelNm
	 */
	public String getModelNm() {
		return this.modelNm;
	}

	/**
	 * @param modelNm
	 *            the modelNm to set
	 */
	public void setModelNm(String modelNm) {
		this.modelNm = modelNm;
	}

	/**
	 * @return the apkVer
	 */
	public String getApkVer() {
		return this.apkVer;
	}

	/**
	 * @param apkVer
	 *            the apkVer to set
	 */
	public void setApkVer(String apkVer) {
		this.apkVer = apkVer;
	}
}
