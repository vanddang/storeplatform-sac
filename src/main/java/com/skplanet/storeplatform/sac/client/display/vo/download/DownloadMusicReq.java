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

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Download 앱 상품 정보 조회 Value Object.
 * 
 * Updated on : 2014. 01. 21. Updated by : 이석희, 인크로스.
 */
public class DownloadMusicReq extends CommonInfo {

	private static final long serialVersionUID = 1L;
	private String tenantId; // tenantId
	private String systemId; // 시스템Id
	private String deviceModelCd; // 단말모델코드
	private String langCd; // 언어코드
	private String category; // 상품 유형
	private String productId; // 상품Id
	private String userHpNo; // 사용자 전화번호
	private String sellerMemberNo; // 판매자 회원번호
	private String dummy; // dummy data check

	/**
	 * 
	 * <pre>
	 * tenantId.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getTenantId() {
		return this.tenantId;
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
		return this.systemId;
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
		return this.deviceModelCd;
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
	 * 
	 * <pre>
	 * 언어코드.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getLangCd() {
		return this.langCd;
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
	 * 
	 * <pre>
	 * 상품 유형.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getCategory() {
		return this.category;
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
	 * 상품 ID.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getProductId() {
		return this.productId;
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
	 * 사용자 전화번호.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getUserHpNo() {
		return this.userHpNo;
	}

	/**
	 * 
	 * <pre>
	 * 사용자 전화번호.
	 * </pre>
	 * 
	 * @param userHpNo
	 *            userHpNo
	 */
	public void setUserHpNo(String userHpNo) {
		this.userHpNo = userHpNo;
	}

	/**
	 * 
	 * <pre>
	 * 판매자 회원 번호.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getSellerMemberNo() {
		return this.sellerMemberNo;
	}

	/**
	 * 
	 * <pre>
	 * 판매자 회원 번호.
	 * </pre>
	 * 
	 * @param sellerMemberNo
	 *            sellerMemberNo
	 */
	public void setSellerMemberNo(String sellerMemberNo) {
		this.sellerMemberNo = sellerMemberNo;
	}

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getDummy() {
		return this.dummy;
	}

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param dummy
	 *            dummy
	 */
	public void setDummy(String dummy) {
		this.dummy = dummy;
	}

}
