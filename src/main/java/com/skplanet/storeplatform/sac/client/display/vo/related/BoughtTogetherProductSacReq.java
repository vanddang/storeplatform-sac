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

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 이 상품과 함께 구매한 상품 조회 Input Value Object.
 * 
 * Updated on : 2014. 02. 18. Updated by : 유시혁.
 */
public class BoughtTogetherProductSacReq extends CommonInfo implements Serializable {

	private static final long serialVersionUID = 11123123143L;
	@NotBlank
	private String productId; // 상품 아이디
	private String exceptId; // 제외 상품 아이디
	private String[] arrayExceptId; // 제외 상품 아이디 배열
	@Valid
	private Integer offset; // 시작점 ROW
	@Valid
	private Integer count; // 페이지당 노출 ROW 수
	private String tenantId; // 테넌트 ID
	private String systemId; // 시스템Id
	private String deviceModelCd; // 단말모델코드
	private String langCd; // 언어코드
	private String testYn; // 테스트 여부
	private String mmDeviceModelCd; // 가상 프로비저닝 모델명 (멀티미디어).
	private String ebookSprtYn; // eBook 상품 지원여부
	private String comicSprtYn; // Comic 상품 지원여부
	private String musicSprtYn; // 음악 상품 지원여부
	private String videoDrmSprtYn; // VOD 상품 DRM 지원 여부
	private String sdVideoSprtYn; // VOD 상품 SD 지원 여부

	/**
	 * 
	 * <pre>
	 * 상품 아이디.
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
	 * 상품 아이디.
	 * </pre>
	 * 
	 * @param productId
	 *            String
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * 
	 * <pre>
	 * 제외 상품 아이디.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getExceptId() {
		return this.exceptId;
	}

	/**
	 * 
	 * <pre>
	 * 제외 상품 아이디.
	 * </pre>
	 * 
	 * @param exceptId
	 *            String
	 */
	public void setExceptId(String exceptId) {
		this.exceptId = exceptId;
	}

	/**
	 * 
	 * <pre>
	 * 시작점 ROW.
	 * </pre>
	 * 
	 * @return Integer
	 */
	public Integer getOffset() {
		return this.offset;
	}

	/**
	 * 
	 * <pre>
	 * 시작점 ROW.
	 * </pre>
	 * 
	 * @param offset
	 *            Integer
	 */
	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	/**
	 * 
	 * <pre>
	 * 페이지당 노출 ROW 수.
	 * </pre>
	 * 
	 * @return Integer
	 */
	public Integer getCount() {
		return this.count;
	}

	/**
	 * 
	 * <pre>
	 * 페이지당 노출 ROW 수.
	 * </pre>
	 * 
	 * @param count
	 *            Integer
	 */
	public void setCount(Integer count) {
		this.count = count;
	}

	/**
	 * 
	 * <pre>
	 * 테넌트 ID.
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
	 * 테넌트 ID.
	 * </pre>
	 * 
	 * @param tenantId
	 *            String
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
	 *            String
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
	 *            String
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
	 *            String
	 */
	public void setLangCd(String langCd) {
		this.langCd = langCd;
	}

	/**
	 * 
	 * <pre>
	 * 제외 상품 아이디 배열.
	 * </pre>
	 * 
	 * @return String[]
	 */
	public String[] getArrayExceptId() {
		return this.arrayExceptId == null ? null : this.arrayExceptId.clone();

	}

	/**
	 * 
	 * <pre>
	 * 제외 상품 아이디 배열.
	 * </pre>
	 * 
	 * @param arrayExceptId
	 *            String[]
	 */
	public void setArrayExceptId(String[] arrayExceptId) {
		this.arrayExceptId = arrayExceptId == null ? null : arrayExceptId.clone();
	}

	/**
	 * 
	 * <pre>
	 * 테스트 여부.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getTestYn() {
		return this.testYn;
	}

	/**
	 * 
	 * <pre>
	 * 테스트 여부.
	 * </pre>
	 * 
	 * @param testYn
	 *            String
	 */
	public void setTestYn(String testYn) {
		this.testYn = testYn;
	}

	/**
	 * 
	 * <pre>
	 * 가상 프로비저닝 모델명 (멀티미디어).
	 * </pre>
	 * 
	 * @return String
	 */
	public String getMmDeviceModelCd() {
		return this.mmDeviceModelCd;
	}

	/**
	 * 
	 * <pre>
	 * 가상 프로비저닝 모델명 (멀티미디어).
	 * </pre>
	 * 
	 * @param mmDeviceModelCd
	 *            String
	 */
	public void setMmDeviceModelCd(String mmDeviceModelCd) {
		this.mmDeviceModelCd = mmDeviceModelCd;
	}

	/**
	 * 
	 * <pre>
	 * eBook 상품 지원여부.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getEbookSprtYn() {
		return this.ebookSprtYn;
	}

	/**
	 * 
	 * <pre>
	 * eBook 상품 지원여부.
	 * </pre>
	 * 
	 * @param ebookSprtYn
	 *            String
	 */
	public void setEbookSprtYn(String ebookSprtYn) {
		this.ebookSprtYn = ebookSprtYn;
	}

	/**
	 * 
	 * <pre>
	 * Comic 상품 지원여부.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getComicSprtYn() {
		return this.comicSprtYn;
	}

	/**
	 * 
	 * <pre>
	 * Comic 상품 지원여부.
	 * </pre>
	 * 
	 * @param comicSprtYn
	 *            String
	 */
	public void setComicSprtYn(String comicSprtYn) {
		this.comicSprtYn = comicSprtYn;
	}

	/**
	 * 
	 * <pre>
	 * 음악 상품 지원여부.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getMusicSprtYn() {
		return this.musicSprtYn;
	}

	/**
	 * 
	 * <pre>
	 * 음악 상품 지원여부.
	 * </pre>
	 * 
	 * @param musicSprtYn
	 *            String
	 */
	public void setMusicSprtYn(String musicSprtYn) {
		this.musicSprtYn = musicSprtYn;
	}

	/**
	 * 
	 * <pre>
	 * VOD 상품 DRM 지원 여부.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getVideoDrmSprtYn() {
		return this.videoDrmSprtYn;
	}

	/**
	 * 
	 * <pre>
	 * VOD 상품 DRM 지원 여부.
	 * </pre>
	 * 
	 * @param videoDrmSprtYn
	 *            String
	 */
	public void setVideoDrmSprtYn(String videoDrmSprtYn) {
		this.videoDrmSprtYn = videoDrmSprtYn;
	}

	/**
	 * 
	 * <pre>
	 * VOD 상품 SD 지원 여부.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getSdVideoSprtYn() {
		return this.sdVideoSprtYn;
	}

	/**
	 * 
	 * <pre>
	 * VOD 상품 SD 지원 여부.
	 * </pre>
	 * 
	 * @param sdVideoSprtYn
	 *            String
	 */
	public void setSdVideoSprtYn(String sdVideoSprtYn) {
		this.sdVideoSprtYn = sdVideoSprtYn;
	}
}
