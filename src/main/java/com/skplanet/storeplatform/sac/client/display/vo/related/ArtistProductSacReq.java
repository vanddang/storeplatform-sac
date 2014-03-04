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

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 특정 아티스트별 상품 조회 Input Value Object.
 * 
 * Updated on : 2014. 03. 03. Updated by : 유시혁.
 */
public class ArtistProductSacReq extends CommonInfo implements Serializable {

	private static final long serialVersionUID = 11123123143L;
	@NotBlank
	private String artistId; // 아티스트ID
	@Pattern(regexp = "|issuedDate|popular")
	private String orderedBy; // 상품 정렬 순서
	private Integer offset; // 시작점 ROW
	private Integer count; // 페이지당 노출 ROW 수
	private String tenantId; // 테넌트 ID
	private String systemId; // 시스템Id
	private String deviceModelCd; // 단말모델코드
	private String langCd; // 언어코드
	private String mmDeviceModelCd; // 가상 프로비저닝 모델명 (멀티미디어).
	private String imageCd; // 이미지 코드

	/**
	 * 
	 * <pre>
	 * 아티스트ID.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getArtistId() {
		return this.artistId;
	}

	/**
	 * 
	 * <pre>
	 * 아티스트ID.
	 * </pre>
	 * 
	 * @param artistId
	 *            String
	 */
	public void setArtistId(String artistId) {
		this.artistId = artistId;
	}

	/**
	 * 
	 * <pre>
	 * 상품 정렬 순서.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getOrderedBy() {
		return this.orderedBy;
	}

	/**
	 * 
	 * <pre>
	 * 상품 정렬 순서.
	 * </pre>
	 * 
	 * @param orderedBy
	 *            String
	 */
	public void setOrderedBy(String orderedBy) {
		this.orderedBy = orderedBy;
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
	 * 이미지 코드.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getImageCd() {
		return this.imageCd;
	}

	/**
	 * 
	 * <pre>
	 * 이미지 코드.
	 * </pre>
	 * 
	 * @param imageCd
	 *            String
	 */
	public void setImageCd(String imageCd) {
		this.imageCd = imageCd;
	}
}
