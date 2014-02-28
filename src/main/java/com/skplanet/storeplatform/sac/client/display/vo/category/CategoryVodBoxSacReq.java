/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.category;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Vod 보관함 조회 Input Value Object.
 * 
 * Updated on : 2014. 02. 12. Updated by : 유시혁.
 */
public class CategoryVodBoxSacReq extends CommonInfo implements Serializable {

	private static final long serialVersionUID = 11123123143L;
	@NotBlank
	@Pattern(regexp = "^duration$|^chapter$|^regDate$")
	private String filteredBy; // 차트 구분 코드
	@NotBlank
	private String channelId; // 채널 ID
	private Integer offset; // 시작점 ROW
	private Integer count; // 페이지당 노출 ROW 수
	private Integer duration; // 기간
	private Integer chapter; // 회차
	private String regDate; // 등록일
	private String tenantId; // 테넌트 ID
	private String systemId; // 시스템Id
	private String deviceModelCd; // 단말모델코드
	private String langCd; // 언어코드
	private String mmDeviceModelCd; // 가상 프로비저닝 모델명 (멀티미디어).

	/**
	 * 
	 * <pre>
	 * 차트 구분 코드.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getFilteredBy() {
		return this.filteredBy;
	}

	/**
	 * 
	 * <pre>
	 * 차트 구분 코드.
	 * </pre>
	 * 
	 * @param filteredBy
	 *            String
	 */
	public void setFilteredBy(String filteredBy) {
		this.filteredBy = filteredBy;
	}

	/**
	 * 
	 * <pre>
	 * 채널 ID.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getChannelId() {
		return this.channelId;
	}

	/**
	 * 
	 * <pre>
	 * 채널 ID.
	 * </pre>
	 * 
	 * @param channelId
	 *            String
	 */
	public void setChannelId(String channelId) {
		this.channelId = channelId;
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
	 * 기간.
	 * </pre>
	 * 
	 * @return Integer
	 */
	public Integer getDuration() {
		return this.duration;
	}

	/**
	 * 
	 * <pre>
	 * 기간.
	 * </pre>
	 * 
	 * @param duration
	 *            Integer
	 */
	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	/**
	 * 
	 * <pre>
	 * 회차.
	 * </pre>
	 * 
	 * @return Integer
	 */
	public Integer getChapter() {
		return this.chapter;
	}

	/**
	 * 
	 * <pre>
	 * 회차.
	 * </pre>
	 * 
	 * @param chapter
	 *            Integer
	 */
	public void setChapter(Integer chapter) {
		this.chapter = chapter;
	}

	/**
	 * 
	 * <pre>
	 * 등록일.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getRegDate() {
		return this.regDate;
	}

	/**
	 * 
	 * <pre>
	 * 등록일.
	 * </pre>
	 * 
	 * @param regDate
	 *            String
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
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

}
