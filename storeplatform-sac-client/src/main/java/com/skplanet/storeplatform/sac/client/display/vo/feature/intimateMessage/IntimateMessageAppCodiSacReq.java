/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.feature.intimateMessage;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Intimate Message 앱코디 상품 리스트 조회 Request Value Object.
 * 
 * Updated on : 2014. 03. 26. Updated by : 이태희.
 */
public class IntimateMessageAppCodiSacReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	@NotBlank
	private String daCode; // appCodi DA정보

	private Integer offset; // 시작점 ROW

	private Integer count; // 페이지당 노출될 ROW 개수

	private String stdDt; // 배치완료 기준일시

	private String tenantId; // 테넌트ID

	private String langCd; // 언어코드

	private String deviceModelCd; // 디바이스모델코드

	private String anyDeviceModelCd; // 가상 프로비저닝 단말모델코드

	private String ebookSprtYn; // eBook 상품 지원여부

	private String comicSprtYn; // Comic 상품 지원여부

	private String musicSprtYn; // 음악 상품 지원여부

	private String videoDrmSprtYn; // VOD 상품 DRM 지원 여부

	private String sdVideoSprtYn; // VOD 상품 SD 지원 여부

	/**
	 * @return the daCode
	 */
	public String getDaCode() {
		return this.daCode;
	}

	/**
	 * @param daCode
	 *            the daCode to set
	 */
	public void setDaCode(String daCode) {
		this.daCode = daCode;
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
	 * @return the anyDeviceModelCd
	 */
	public String getAnyDeviceModelCd() {
		return this.anyDeviceModelCd;
	}

	/**
	 * @param anyDeviceModelCd
	 *            the anyDeviceModelCd to set
	 */
	public void setAnyDeviceModelCd(String anyDeviceModelCd) {
		this.anyDeviceModelCd = anyDeviceModelCd;
	}

	/**
	 * @return the ebookSprtYn
	 */
	public String getEbookSprtYn() {
		return this.ebookSprtYn;
	}

	/**
	 * @param ebookSprtYn
	 *            the ebookSprtYn to set
	 */
	public void setEbookSprtYn(String ebookSprtYn) {
		this.ebookSprtYn = ebookSprtYn;
	}

	/**
	 * @return the comicSprtYn
	 */
	public String getComicSprtYn() {
		return this.comicSprtYn;
	}

	/**
	 * @param comicSprtYn
	 *            the comicSprtYn to set
	 */
	public void setComicSprtYn(String comicSprtYn) {
		this.comicSprtYn = comicSprtYn;
	}

	/**
	 * @return the musicSprtYn
	 */
	public String getMusicSprtYn() {
		return this.musicSprtYn;
	}

	/**
	 * @param musicSprtYn
	 *            the musicSprtYn to set
	 */
	public void setMusicSprtYn(String musicSprtYn) {
		this.musicSprtYn = musicSprtYn;
	}

	/**
	 * @return the videoDrmSprtYn
	 */
	public String getVideoDrmSprtYn() {
		return this.videoDrmSprtYn;
	}

	/**
	 * @param videoDrmSprtYn
	 *            the videoDrmSprtYn to set
	 */
	public void setVideoDrmSprtYn(String videoDrmSprtYn) {
		this.videoDrmSprtYn = videoDrmSprtYn;
	}

	/**
	 * @return the sdVideoSprtYn
	 */
	public String getSdVideoSprtYn() {
		return this.sdVideoSprtYn;
	}

	/**
	 * @param sdVideoSprtYn
	 *            the sdVideoSprtYn to set
	 */
	public void setSdVideoSprtYn(String sdVideoSprtYn) {
		this.sdVideoSprtYn = sdVideoSprtYn;
	}
}
